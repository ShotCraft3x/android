package com.proyectoandroid.safety

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.e.union.adapters.OnboardingViewPagerAdapter3
import com.jaeger.library.StatusBarUtil

import kotlinx.android.synthetic.main.activity_onboarding_example3.*


class DetalleReto : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var textSkip: TextView
    private lateinit var textEnd: TextView
    private lateinit var btnNextStep : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_example3)
        mViewPager = viewPager
        textSkip = text_skip
        textEnd = text_end
        btnNextStep = btn_next_step
        mViewPager.adapter = OnboardingViewPagerAdapter3(supportFragmentManager, this)
        mViewPager.offscreenPageLimit = 1
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                if(position == 2){
                    btnNextStep.visibility = View.GONE
                    textEnd.visibility = View.VISIBLE
                    textSkip.visibility = View.GONE
                }else{
                    btnNextStep.visibility = View.VISIBLE
                    textEnd.visibility = View.GONE
                    textSkip.visibility = View.VISIBLE
                }
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        })


        StatusBarUtil.setTranslucentForImageViewInFragment(this, null)

        textSkip.setOnClickListener {
            finish()
        }

        textEnd.setOnClickListener {
            val intent = Intent(this.applicationContext, Cronometro::class.java)
            startActivity(intent)
        }

        val btnNextStep: ImageButton = btn_next_step
        btnNextStep.setOnClickListener {
            if (getItem(+1) > mViewPager.childCount-1) {
              finish()
            } else {
                mViewPager.setCurrentItem(getItem(+1), true)
            }
        }



    }

    private fun getItem(i: Int): Int {
        return mViewPager.currentItem + i
    }
}
