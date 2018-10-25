package com.example.artem_lazurenko.shootapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_main, container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vpPagerView.adapter = ShootAppPagerAdapter(fragmentManager!!)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        vpPagerView.addOnPageChangeListener(mOnPageChangedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
//                message.setText(R.string.title_home)
                vpPagerView.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_gallery -> {
//                message.setText(R.string.title_gallery)
                vpPagerView.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
//                message.setText(R.string.title_settings)
                vpPagerView.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val mOnPageChangedListener = object: ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) = Unit

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

        override fun onPageSelected(position: Int) = navigation.setSelectedItemId(navigation.menu.getItem(position).itemId)
    }

}