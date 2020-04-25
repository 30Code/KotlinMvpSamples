package cn.linhome.kotlinmvpsamples.ui.activity

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateInterpolator
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseActivity
import cn.linhome.kotlinmvpsamples.dialog.common.AppDialogConfirm
import cn.linhome.kotlinmvpsamples.granted.PermissionCompat
import cn.linhome.lib.dialog.FIDialogConfirm
import cn.linhome.lib.dialog.impl.FDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.act_splash.*
import org.jetbrains.anko.startActivity


/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/5
 */
class SplashActivity : BaseActivity() {

    private var mFlag : Boolean = false

    override fun onResume() {
        super.onResume()
        if (mFlag) {
            requestExternalStoragePermission()
            mFlag = false
        }
    }

    override fun onCreateContentView(): Int {
        return R.layout.act_splash
    }

    override fun initView() {
        requestExternalStoragePermission()
    }

    private fun requestExternalStoragePermission() {
        RxPermissions(this).requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            .subscribe { permission ->
                if (permission.granted) {
                    animator()
                } else {
                    showPermissionRefuseDialog(getString(R.string.text_permission_external_storage_tip))
                }
            }
    }

    private fun showPermissionRefuseDialog(tips : String) {
        val dialogConfirm = AppDialogConfirm(this)
        dialogConfirm.setTextContent(tips)
        dialogConfirm.setCallback(object : FIDialogConfirm.Callback {
            override fun onClickCancel(v: View?, dialog: FDialog?) {
                finish()
            }

            override fun onClickConfirm(v: View?, dialog: FDialog?) {
                mFlag = true
                PermissionCompat.start(activity, false)
            }

        }).show()
    }

    private fun animator() {
        val alpha = PropertyValuesHolder.ofFloat("alpha", 0.3f, 1f)
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f)
        val ivObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(iv_logo, alpha, scaleX, scaleY)
        val tvObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(tv_name, alpha, scaleX, scaleY)
        val animatorSet = AnimatorSet()
        animatorSet.run {
            playTogether(ivObjectAnimator, tvObjectAnimator)
            interpolator = AccelerateInterpolator()
            duration = 2000
            addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    startActivity<MainActivity>()
                    finish()
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }

            })
            start()
        }
    }

    override fun start() {

    }
}