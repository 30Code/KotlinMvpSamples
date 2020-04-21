package cn.linhome.kotlinmvpsamples.ui.activity

import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseMvpActivity
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.event.ELogin
import cn.linhome.kotlinmvpsamples.model.bean.LoginData
import cn.linhome.kotlinmvpsamples.mvp.contract.RegisterContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.RegisterPresenter
import cn.linhome.lib.utils.context.FPreferencesUtil
import cn.linhome.lib.utils.context.FToast
import com.sunday.eventbus.SDEventManager
import kotlinx.android.synthetic.main.act_register.*
import org.jetbrains.anko.startActivity

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/19
 */
class RegisterActivity : BaseMvpActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View{

    private var mUserName : String = ""

    private var mPwd : String = ""

    private var mPwd2 : String = ""

    override fun createdPresenter(): RegisterContract.Presenter = RegisterPresenter()

    override fun onCreateContentView(): Int = R.layout.act_register

    override fun initView() {
        super.initView()
        showTitle(true)
        getTitleView().itemMiddle.setTextTop(getString(R.string.register))
        btn_register.setOnClickListener {
            register()
        }

        tv_login.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun register() {
        if (validate()) {
            mPresenter?.register(mUserName, mPwd, mPwd2)
        }
    }

    private fun validate(): Boolean {
        var valid = true
        mUserName = et_user_name.text.toString()
        mPwd = et_pwd.text.toString()
        mPwd2 = et_pwd_again.text.toString()
        if (mUserName.isEmpty()) {
            FToast.show(getString(R.string.username_not_empty))
            valid = false
            return valid
        }
        if (mPwd.isEmpty()) {
            FToast.show(getString(R.string.password_not_empty))
            valid = false
            return valid
        }
        if (mPwd2.isEmpty()) {
            FToast.show(getString(R.string.confirm_password_not_empty))
            valid = false
            return valid
        }
        if (mPwd != mPwd2) {
            FToast.show(getString(R.string.password_cannot_match))
            valid = false
        }
        return valid
    }

    override fun start() {
    }

    override fun registerSuccess(data: LoginData) {
        FToast.show(getString(R.string.register_success))
        FPreferencesUtil.putBoolean(Constant.LOGIN_KEY, true)
        FPreferencesUtil.putString(Constant.USERNAME_KEY, data.username)
        FPreferencesUtil.putString(Constant.PASSWORD_KEY, data.password)

        SDEventManager.post(ELogin(true))
        finish()
    }

    override fun registerFail() {
        FPreferencesUtil.putBoolean(Constant.LOGIN_KEY, false)
    }

    override fun showLoading() {
        showProgressDialog(getString(R.string.register_ing))
    }

    override fun hideLoading() {
        dismissProgressDialog()
    }

    override fun showDefaultMsg(msg: String) {
        FToast.show(msg)
    }

    override fun showMsg(msg: String) {
    }

    override fun showError(errorMsg: String) {
    }
}