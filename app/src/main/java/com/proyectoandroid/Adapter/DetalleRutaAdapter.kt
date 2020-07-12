package com.proyectoandroid.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.proyectoandroid.fragments.loginFragment
import com.proyectoandroid.safety.R


class DetalleRutaAdapter(manager: FragmentManager,
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
                    context.resources.getString(R.string.title_safetyReto),
                    context.resources.getString(R.string.description_safetyReto),
                    R.raw.safety

            )
            1 -> loginFragment.newInstance(
                    context.resources.getString(R.string.title_GoogleMap),
                    context.resources.getString(R.string.description_googlemap),
                    R.raw.googlemaps
            )
            else -> null
        }!!
    }

    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }

    companion object {
        private const val NUM_ITEMS = 2
    }

}