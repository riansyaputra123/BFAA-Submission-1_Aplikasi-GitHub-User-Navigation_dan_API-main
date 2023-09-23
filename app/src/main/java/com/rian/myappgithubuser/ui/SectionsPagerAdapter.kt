package com.rian.myappgithubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, val username: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowersPage()
        fragment.arguments = Bundle().apply {
            putInt(FollowersPage.ARG_POSITION, position + 1)
            putString(FollowersPage.ARG_USERNAME, username)
        }
        return fragment
    }

}