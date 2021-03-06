package com.proyectoandroid.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.proyectoandroid.fragments.loginFragment
import com.proyectoandroid.safety.R


class loginAdapter(manager: FragmentManager,
                   private val context : Context) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // Returns total number of pages
    override fun getCount(): Int {
        return NUM_ITEMS
    }

    // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> loginFragment.newInstance(
                context.resources.getString(R.string.tituloSafety),
                context.resources.getString(R.string.description_safetylogin),
                R.raw.robot_alerta
            )
            1 -> loginFragment.newInstance(
                context.resources.getString(R.string.titulosafety1),
                context.resources.getString(R.string.description_safetylogin1),
                R.raw.lottie_developer
            )
            2 -> loginFragment.newInstance(
                context.resources.getString(R.string.titulosafety2),
                context.resources.getString(R.string.description_safetylogin2),
                R.raw.salud
            )
            else -> null
        }!!
    }

    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }

    companion object {
        private const val NUM_ITEMS = 3
    }

}