package cn.linhome.kotlinmvpsamples.app

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import cn.linhome.kotlinmvpsamples.common.GlobalEncryptConverter
import cn.linhome.kotlinmvpsamples.common.GsonObjectConverter
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.utils.SettingUtil
import cn.linhome.lib.cache.FDisk
import cn.linhome.lib.utils.extend.FActivityStack
import cn.linhome.library.app.FApplication
import cn.linhome.library.utils.LogUtil
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.sunday.eventbus.SDEventManager
import de.greenrobot.event.SubscriberExceptionEvent
import java.util.*
import kotlin.properties.Delegates

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/3
 */
class App : FApplication(){

    private var mRefWatcher : RefWatcher? = null

    companion object {
        lateinit var instance : App
        var mContext : Context by Delegates.notNull()

        fun getRefWatcher(context: Context) : RefWatcher?{
            val app = context.applicationContext as App
            return app.mRefWatcher
        }

        fun exitApp(isBackground : Boolean){
            FActivityStack.getInstance().finishAllActivity()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mContext = applicationContext
        initTheme()
    }

    override fun onCreateMainProcess() {
        LogUtil.isDebug = Constant.DEBUG
        LeakCanary.install(this)
        SDEventManager.register(this)
        FActivityStack.getInstance().setDebug(Constant.DEBUG)
        //FDisk
        FDisk.init(this)
        FDisk.setGlobalObjectConverter(GsonObjectConverter())
        FDisk.setGlobalEncryptConverter(GlobalEncryptConverter())
    }

    /**
     * 初始化主题
     */
    private fun initTheme() {

        if (SettingUtil.getIsAutoNightMode()) {
            val nightStartHour = SettingUtil.getNightStartHour().toInt()
            val nightStartMinute = SettingUtil.getNightStartMinute().toInt()
            val dayStartHour = SettingUtil.getDayStartHour().toInt()
            val dayStartMinute = SettingUtil.getDayStartMinute().toInt()

            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val nightValue = nightStartHour * 60 + nightStartMinute
            val dayValue = dayStartHour * 60 + dayStartMinute
            val currentValue = currentHour * 60 + currentMinute

            if (currentValue >= nightValue || currentValue <= dayValue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SettingUtil.setIsNightMode(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                SettingUtil.setIsNightMode(false)
            }
        } else {
            // 获取当前的主题
            if (SettingUtil.getIsNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    fun onEventMainThread(event: SubscriberExceptionEvent) {
        LogUtil.e("onEventMainThread:" + event.throwable.toString())
    }

    override fun onTerminate() {
        SDEventManager.unregister(this)
        super.onTerminate()
    }
}