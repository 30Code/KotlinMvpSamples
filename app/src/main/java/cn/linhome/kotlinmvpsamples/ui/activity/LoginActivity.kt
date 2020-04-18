package cn.linhome.kotlinmvpsamples.ui.activity

import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseMvpActivity
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.event.ELogin
import cn.linhome.kotlinmvpsamples.model.bean.LoginData
import cn.linhome.kotlinmvpsamples.mvp.contract.LoginContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.LoginPresenter
import cn.linhome.lib.title.FTitleItem
import cn.linhome.lib.utils.context.FPreferencesUtil
import cn.linhome.lib.utils.context.FToast
import com.sunday.eventbus.SDEventManager
import kotlinx.android.synthetic.main.act_login.*

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/18
 */
class LoginActivity : BaseMvpActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View{

    private var mUserName : String = ""

    private var mPwd : String = ""

    override fun createdPresenter(): LoginContract.Presenter = LoginPresenter()

    override fun onCreateContentView(): Int = R.layout.act_login

    override fun initView() {
        super.initView()
        showTitle(true)
        getTitleView().itemMiddle.setTextTop(getString(R.string.login))
        btn_login.setOnClickListener {
            login()
        }
        tv_register.setOnClickListener {

        }
    }

    private fun validate() : Boolean {
        var valid = true
        mUserName = et_user_name.text.toString()
        mPwd = et_pwd.text.toString()
        if (mUserName.isEmpty()) {
            et_user_name.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (mPwd.isEmpty()) {
            et_pwd.error = getString(R.string.password_not_empty)
            valid = false
        }
        return valid
    }

    private fun login() {
        if (validate()) {
            mPresenter?.login(mUserName, mPwd)
        }
    }

    override fun start() {
    }

    override fun loginSuccess(data: LoginData) {
        FToast.show(getString(R.string.login_success))
        FPreferencesUtil.putBoolean(Constant.LOGIN_KEY, true)
        FPreferencesUtil.putString(Constant.USERNAME_KEY, data.username)
        FPreferencesUtil.putString(Constant.PASSWORD_KEY, data.password)
        FPreferencesUtil.putString(Constant.TOKEN_KEY, data.token)

        SDEventManager.post(ELogin(true))
        finish()
    }

    override fun loginFail() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showDefaultMsg(msg: String) {
    }

    override fun showMsg(msg: String) {
    }

    override fun showError(errorMsg: String) {
    }

    override fun onClickItemLeftTitleBar(index: Int, item: FTitleItem?) {
        super.onClickItemLeftTitleBar(index, item)
        finish()
    }
}