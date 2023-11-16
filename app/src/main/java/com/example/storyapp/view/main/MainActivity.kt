package com.example.storyapp.view.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.databinding.EachStoryBinding
import com.example.storyapp.view.DetailActivity
import com.example.storyapp.view.upload.UploadActivity
import com.example.storyapp.view.welcome.WelcomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.core.util.Pair
import com.example.storyapp.repository.StoryRepository
import com.example.storyapp.api.ApiService
import com.example.storyapp.api.ListStoryItem
import com.example.storyapp.api.StoryViewModelFactory
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.profile.ProfileActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var storyViewModel: StoryViewModel
    private lateinit var apiService: ApiService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val eachStoryBinding = EachStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        apiService = ApiConfig().getApiService()

        val storyRepository = StoryRepository(apiService)
        // Inisialisasi ViewModel
        storyViewModel = ViewModelProvider(this, StoryViewModelFactory(storyRepository)).get(StoryViewModel::class.java)

        // Mengambil token dari preferensi
        val token = "Bearer ${PreferenceManager.getToken(this)}"
        // Inisialisasi RecyclerView
        recyclerView = binding.rvStory
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(object : MainAdapter.OnItemClickListener {
            override fun onItemClick(story: ListStoryItem, itemView: View) {
            }
        })

        // Set the adapter for the recyclerView
        recyclerView.adapter = adapter



        binding.btnAdd.setOnClickListener {
            // Start the UploadActivity
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
        binding.btnProfile.setOnClickListener {
            // Start the UploadActivity
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        storyViewModel.getStory(token).observe(this) {
            adapter.submitData(lifecycle, it)
        }


        // Cek apakah ada data cerita di ViewModel
//        val savedStories = storyViewModel.getStories()
//        if (savedStories.isNotEmpty()) {
//            adapter.setData(savedStories)
//        }

//        // Mengambil cerita dari API
//        if (savedStories.isEmpty()) {
//            fetchStories(token)
//        }
        binding.ibMenu.setOnClickListener { showPopupMenu(it) }
    }


//    private fun fetchStories(token: String) {
//
//
//        if (token.isNullOrBlank()) {
//            // Token tidak tersedia, mungkin pengguna belum login
//            Log.e("StoryAPI", "Token not available. User may not be logged in.")
//            return
//        }
//        // Mengambil instance Api
//        val api = ApiConfig().getApiService()
//
//
//        // Melakukan panggilan API dengan Retrofit
//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                val storyResponse = api.getAllStories(token)
//                if (storyResponse.error == false) {
//                    val stories = storyResponse.listStory
//
//                    // Create a list of MainModel objects from the fetched data
//                    val mainModels = stories?.mapNotNull { storyItem ->
//                        val storyPhoto = storyItem?.photoUrl ?: ""
//                        val storyName = storyItem?.name ?: ""
//                        val storyDesc = storyItem?.description ?: ""
//
//                        // Tambahkan log untuk mencetak nama cerita ke Logcat
//                        Log.d("StoryAPI", "Story Name: $storyName")
//                        if (storyPhoto.isNotEmpty() && storyName.isNotEmpty() && storyDesc.isNotEmpty()) {
//
//                            MainModel(storyPhoto, storyName, storyDesc)
//
//                        } else {
//                            null
//                        }
//                    } ?: emptyList()
//
//                    runOnUiThread {
//                        // Simpan data cerita ke ViewModel
//                        storyViewModel.setStories(mainModels)
//                        adapter.setData(mainModels)
//                        // Observe the LiveData after the adapter is initialized
////                        storyViewModel.getStory(token).observe(this@MainActivity) {
////                            adapter.submitData(lifecycle, it)
////
////                        }
//
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.e("StoryAPI", "Exception: ${e.message}")
//            }
//        }
//    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                PreferenceManager.clearToken(this)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                finish() // Optional: Menutup MainActivity
                return true
            }


            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)

        // Set item click listener
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.item_logout -> {
                    PreferenceManager.clearToken(this)

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                    finish() // Optional: Menutup MainActivity
                    true
                }

                // Add more menu items as needed
                else -> false
            }
        }

        // Show the popup menu
        popupMenu.show()
    }

}
