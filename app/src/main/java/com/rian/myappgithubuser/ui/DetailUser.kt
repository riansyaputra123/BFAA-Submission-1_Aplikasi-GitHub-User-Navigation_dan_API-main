package com.rian.myappgithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rian.myappgithubuser.R
import com.rian.myappgithubuser.databinding.ActivityDetailUserBinding

class DetailUser : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()




    override fun onCreate(savedInstanceState: Bundle?) {
        val getUsername = intent.getStringExtra(EXTRA_USERNAME)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.btnBack.setOnClickListener {
            finish()
        }

        if (getUsername != null) {
            val sectionPagerAdapter = SectionsPagerAdapter(this, getUsername)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tab)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        if (getUsername != null) {
            detailViewModel.getDetailUser(getUsername)
        }


        detailViewModel.detailUser.observe(this) {
            if (it != null) {
                Glide.with(this@DetailUser)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.imgUserDetail)
                binding.tvName.text = it.name
                binding.tvUsername.text = it.login
                binding.tvFollower.text = "${it.followers} Follower"
                binding.tvFollowing.text = "${it.following} Following"
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
        const val EXTRA_USERNAME = "USERNAME"
    }



}