package com.empresa.myapplication.marvelapp.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class MyViewPagerAdapter(
    list : ArrayList<Fragment>,
    manager: FragmentManager,
    lifecycle: Lifecycle)
    : FragmentStateAdapter(manager, lifecycle) {

    private val fragmentList = list

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}