package com.rian.myappgithubuser.ui

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rian.myappgithubuser.data.response.GithubResponse
import com.rian.myappgithubuser.data.response.ItemsItem
import com.rian.myappgithubuser.data.retrofit.ApiConfig
import com.rian.myappgithubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val ViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    ViewModel.findUser(searchView.text.toString())
                    ViewModel.User.observe(this@MainActivity) {
                        if (it.isNullOrEmpty()) {
                            ifUserNotFound(true)
                        } else {
                            ifUserNotFound(false)
                        }
                    }
                    false
                }
        }
        ViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        ViewModel.User.observe(this) {
            if (it != null) {
                setUserData(it)
            }
        }
    }


    private fun setUserData(dataUser: List<ItemsItem>) {
        val adapter = GitUserAdapter()
        adapter.submitList(dataUser)
        binding.rvUser.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun ifUserNotFound(isDataNotFound: Boolean) {
        binding.apply {
            if (isDataNotFound) {
                rvUser.visibility = View.GONE
                tvNotFound.visibility = View.VISIBLE
            } else {
                rvUser.visibility = View.VISIBLE
                tvNotFound.visibility = View.GONE
            }
        }
    }



}