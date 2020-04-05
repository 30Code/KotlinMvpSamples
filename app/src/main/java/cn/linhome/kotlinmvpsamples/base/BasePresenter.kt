package cn.linhome.kotlinmvpsamples.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *  des : 具体的逻辑业务处理类
 *  Created by 30Code
 *  date : 2020/4/5
 */
abstract class BasePresenter<M : IModel, V : IView> : IPresenter<V>, LifecycleObserver{

    protected var mModel : M? = null
    protected var mView : V? = null

    private val mIsViewAttached : Boolean
        get() = mView != null

    private var mCompositeDisposable : CompositeDisposable? = null

    open fun createModel() : M? = null

    override fun attachView(view: V) {
        mView = view
        mModel = createModel()
        if (mView is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)
            if (mModel != null && mModel is LifecycleObserver) {
                (mView as LifecycleOwner).lifecycle.addObserver(mModel as LifecycleObserver)
            }
        }
    }

    override fun detachView() {
        // 保证activity结束时取消所有正在执行的订阅
        unDispose()
        mModel = null
        mView = null
        mCompositeDisposable = null
    }

    private fun unDispose() {
        mCompositeDisposable?.clear()  // 保证Activity结束时取消
        mCompositeDisposable = null
    }

    open fun checkViewAttached() {
        if (!mIsViewAttached) throw MvpViewNotAttachedException()
    }

    @Deprecated("")
    open fun addSubscription(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let { mCompositeDisposable?.add(it) }
    }

    open fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let { mCompositeDisposable?.add(it) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        // detachView()
        owner.lifecycle.removeObserver(this)
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
}