package cn.linhome.kotlinmvpsamples.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.animation.AccelerateInterpolator
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseActivity
import kotlinx.android.synthetic.main.act_splash.*
import org.jetbrains.anko.startActivity


/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/5
 */
class SplashActivity : BaseActivity() {

    override fun onCreateContentView(): Int {
        return R.layout.act_splash
    }

    override fun initView() {
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