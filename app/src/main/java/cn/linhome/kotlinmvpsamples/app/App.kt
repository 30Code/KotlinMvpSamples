package cn.linhome.kotlinmvpsamples.app

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import cn.linhome.kotlinmvpsamples.utils.SettingUtil
import cn.linhome.lib.utils.extend.FActivityStack
import cn.linhome.library.app.FApplication
import com.squareup.leakcanary.RefWatcher
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
        TODO("Not yet implemented")
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

}