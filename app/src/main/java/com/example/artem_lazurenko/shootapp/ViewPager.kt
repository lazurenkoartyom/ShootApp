package com.example.artem_lazurenko.shootapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.*
import android.widget.TextView
import com.example.artem_lazurenko.shootapp.camera2basic.Camera2BasicFragment

class ShootAppPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment = instance(position)

    override fun getCount(): Int = 3
}

class SettingsFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = TextView(inflater.context)
        view.text = "Settings"
        return view
    }

}

fun instance(position: Int):Fragment = when(position) {
    0 -> Camera2BasicFragment()
    1 -> GalleryFragment()
    2 -> SettingsFragment()
    else -> throw IllegalArgumentException("Wrong page number")
}

