package cn.linhome.kotlinmvpsamples.ui.activity

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.FragmentTransaction
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseMvpActivity
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.dialog.common.AppDialogConfirm
import cn.linhome.kotlinmvpsamples.event.EColor
import cn.linhome.kotlinmvpsamples.event.ELogin
import cn.linhome.kotlinmvpsamples.model.bean.UserInfoBody
import cn.linhome.kotlinmvpsamples.mvp.contract.MainContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.MainPresenter
import cn.linhome.kotlinmvpsamples.ui.fragment.*
import cn.linhome.kotlinmvpsamples.utils.SettingUtil
import cn.linhome.lib.dialog.FIDialogConfirm
import cn.linhome.lib.dialog.impl.FDialog
import cn.linhome.lib.utils.context.FPreferencesUtil
import cn.linhome.lib.utils.context.FToast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.sunday.eventbus.SDEventManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View{

    private val BOTTOM_INDEX: String = "bottom_index"

    private var mNavUserName: TextView? = null
    private var mNavUserId: TextView? = null
    private var mNavUserGrade: TextView? = null
    private var mNavUserRank: TextView? = null
    private var mNavUserScore: TextView? = null
    private var mNavRank: ImageView? = null

    private lateinit var mMenuItemLoginOut : MenuItem

    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_SQUARE = 0x02
    private val FRAGMENT_WECHAT = 0x03
    private val FRAGMENT_SYSTEM = 0x04
    private val FRAGMENT_PROJECT = 0x05

    private var mIndex = FRAGMENT_HOME

    private var mHomeFragment: HomeFragment? = null
    private var mSquareFragment: SquareFragment? = null
    private var mWeChatFragment: WeChatFragment? = null
    private var mSystemFragment: SystemFragment? = null
    private var mProjectFragment: ProjectFragment? = null

    private var mUsername: String = FPreferencesUtil.getString(Constant.USERNAME_KEY, "")

    override fun onCreateContentView(): Int {
        return R.layout.activity_main
    }

    override fun createdPresenter(): MainContract.Presenter = MainPresenter()

    override fun initView() {
        super.initView()
        mIsExitApp = true

        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }

        initDrawerLayout()
        initNavView()

        showFragment(mIndex)

        bottom_navigation.run {
//            labelVisibilityMode = 1
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }

        floating_action_btn.run {
            setOnClickListener(onFABClickListener)
        }
    }

    override fun initColor() {
        super.initColor()
        refreshColor(EColor(true))
    }

    /**
     * init DrawerLayout
     */
    private fun initDrawerLayout() {
        drawer_layout.run {
            val toggle = ActionBarDrawerToggle(this@MainActivity, this, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    /**
     * init NavigationView
     */
    private fun initNavView() {
        nav_view.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)

            mNavUserName = getHeaderView(0).findViewById(R.id.tv_username)
            mNavUserId = getHeaderView(0).findViewById(R.id.tv_user_id)
            mNavUserGrade = getHeaderView(0).findViewById(R.id.tv_user_grade)
            mNavUserRank = getHeaderView(0).findViewById(R.id.tv_user_rank)
            mNavRank = getHeaderView(0).findViewById(R.id.iv_rank)
            mNavUserScore = MenuItemCompat.getActionView(nav_view.menu.findItem(R.id.nav_score)) as TextView
            mNavUserScore?.gravity = Gravity.CENTER_VERTICAL

            mMenuItemLoginOut = menu.findItem(R.id.nav_logout)
            mMenuItemLoginOut.isVisible = mIsLogin
        }
        mNavUserName?.run {
            text = if (!mIsLogin) getString(R.string.go_login) else mUsername
            setOnClickListener {
                if (!mIsLogin) {
                    startActivity<LoginActivity>()
                }
            }
        }
        mNavRank?.setOnClickListener {

        }
    }

    fun onEventMainThread(event : ELogin){
        if (event.isLogin) {
            mNavUserName?.text = mUsername
            mMenuItemLoginOut.isVisible = true
            mHomeFragment?.lazyLoad()
            mPresenter?.getUserInfo()
        } else {
            mNavUserName?.text = resources.getString(R.string.go_login)
            mMenuItemLoginOut.isVisible = false
            mHomeFragment?.lazyLoad()
            // 重置用户信息
            mNavUserId?.text = getString(R.string.nav_line_4)
            mNavUserGrade?.text = getString(R.string.nav_line_2)
            mNavUserRank?.text = getString(R.string.nav_line_2)
            mNavUserScore?.text = ""
        }
    }

    /**
     * 展示Fragment
     * @param index
     */
    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        when (index) {
            FRAGMENT_HOME // 首页
            -> {
                toolbar.title = getString(R.string.app_name)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance()
                    transaction.add(R.id.container, mHomeFragment!!, "home")
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
            FRAGMENT_SQUARE  // 广场
            -> {
                toolbar.title = getString(R.string.square)
                if (mSquareFragment == null) {
                    mSquareFragment = SquareFragment.getInstance()
                    transaction.add(R.id.container, mSquareFragment!!, "square")
                } else {
                    transaction.show(mSquareFragment!!)
                }
            }
            FRAGMENT_SYSTEM // 体系
            -> {
                toolbar.title = getString(R.string.knowledge_system)
                if (mSystemFragment == null) {
                    mSystemFragment = SystemFragment.getInstance()
                    transaction.add(R.id.container, mSystemFragment!!, "system")
                } else {
                    transaction.show(mSystemFragment!!)
                }
            }
            FRAGMENT_PROJECT // 项目
            -> {
                toolbar.title = getString(R.string.project)
                if (mProjectFragment == null) {
                    mProjectFragment = ProjectFragment.getInstance()
                    transaction.add(R.id.container, mProjectFragment!!, "project")
                } else {
                    transaction.show(mProjectFragment!!)
                }
            }
            FRAGMENT_WECHAT // 公众号
            -> {
                toolbar.title = getString(R.string.wechat)
                if (mWeChatFragment == null) {
                    mWeChatFragment = WeChatFragment.getInstance()
                    transaction.add(R.id.container, mWeChatFragment!!, "wechat")
                } else {
                    transaction.show(mWeChatFragment!!)
                }
            }
        }
        transaction.commit()
    }

    /**
     * 隐藏所有的Fragment
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mSquareFragment?.let { transaction.hide(it) }
        mSystemFragment?.let { transaction.hide(it) }
        mProjectFragment?.let { transaction.hide(it) }
        mWeChatFragment?.let { transaction.hide(it) }
    }

    /**
     * NavigationItemSelect监听
     */
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            return@OnNavigationItemSelectedListener when (item.itemId) {
                R.id.action_home -> {
                    showFragment(FRAGMENT_HOME)
                    true
                }
                R.id.action_square -> {
                    showFragment(FRAGMENT_SQUARE)
                    true
                }
                R.id.action_system -> {
                    showFragment(FRAGMENT_SYSTEM)
                    true
                }
                R.id.action_project -> {
                    showFragment(FRAGMENT_PROJECT)
                    true
                }
                R.id.action_wechat -> {
                    showFragment(FRAGMENT_WECHAT)
                    true
                }
                else -> {
                    false
                }

            }
        }

    private val onDrawerNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener {
        item: MenuItem ->  when (item.itemId) {
            R.id.nav_score -> {

            }
            R.id.nav_collect -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_setting -> {

            }
            R.id.nav_logout -> {
                logout()
            }
            R.id.nav_night_mode -> {
                if (SettingUtil.getIsNightMode()) {
                    SettingUtil.setIsNightMode(false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    SettingUtil.setIsNightMode(true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
                recreate()
            }
            R.id.nav_todo -> {

            }
        }
        true
    }

    private fun logout() {
        val dialogConfirm = AppDialogConfirm(this)
        dialogConfirm.setTextContent(resources.getString(R.string.confirm_logout))
        dialogConfirm.setCallback(object : FIDialogConfirm.Callback {
            override fun onClickCancel(v: View?, dialog: FDialog?) {

            }

            override fun onClickConfirm(v: View?, dialog: FDialog?) {
                mPresenter?.logout()
            }

        }).show()
    }

    override fun recreate() {
        try {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            if (mHomeFragment != null) {
                fragmentTransaction.remove(mHomeFragment!!)
            }
            if (mSquareFragment != null) {
                fragmentTransaction.remove(mSquareFragment!!)
            }
            if (mSystemFragment != null) {
                fragmentTransaction.remove(mSystemFragment!!)
            }
            if (mProjectFragment != null) {
                fragmentTransaction.remove(mProjectFragment!!)
            }
            if (mWeChatFragment != null) {
                fragmentTransaction.remove(mWeChatFragment!!)
            }
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.recreate()
    }

    private val onFABClickListener = View.OnClickListener {
        when (mIndex) {
            FRAGMENT_HOME -> {
                mHomeFragment?.scrollToTop()
            }
            FRAGMENT_SQUARE -> {
                mSquareFragment?.scrollToTop()
            }
            FRAGMENT_SYSTEM -> {
//                mSystemFragment?.scrollToTop()
            }
            FRAGMENT_PROJECT -> {
                mProjectFragment?.scrollToTop()
            }
            FRAGMENT_WECHAT -> {
                mWeChatFragment?.scrollToTop()
            }
        }
    }

    override fun start() {
        mPresenter?.getUserInfo()
    }

    override fun showUserInfo(bean: UserInfoBody) {
        mNavUserName?.text = bean.username
        mNavUserId?.text = bean.userId.toString()
        mNavUserGrade?.text = (bean.coinCount / 100 + 1).toString()
        mNavUserRank?.text = bean.rank.toString()
        mNavUserScore?.text = bean.coinCount.toString()
    }

    override fun showLogoutSuccess(success: Boolean) {
        if (success) {
            FToast.show(getString(R.string.logout_success))
            FPreferencesUtil.putBoolean(Constant.LOGIN_KEY, false)
            FPreferencesUtil.putString(Constant.USERNAME_KEY, "")
            FPreferencesUtil.putString(Constant.PASSWORD_KEY, "")
            FPreferencesUtil.putString(Constant.TOKEN_KEY, "")

            mIsLogin = false

            SDEventManager.post(ELogin(false))
        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                startActivity<SearchActivity>()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onEventMainThread(event : EColor) {
        refreshColor(event)
    }

    fun refreshColor(event : EColor) {
        if (event.isRefresh) {
            nav_view.getHeaderView(0).setBackgroundColor(mThemeColor)
            floating_action_btn.backgroundTintList = ColorStateList.valueOf(mThemeColor)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHomeFragment = null
        mSquareFragment = null
        mSystemFragment = null
        mProjectFragment = null
        mWeChatFragment = null
    }
}
