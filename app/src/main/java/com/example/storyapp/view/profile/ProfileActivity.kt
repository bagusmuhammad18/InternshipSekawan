package com.example.storyapp.view.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityProfileBinding
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.upload.UploadActivity


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.rvPorto

        val itemList = listOf(
            Item("Github User App", R.drawable.porto_bangkit, "https://www.linkedin.com/posts/bagusmuhammadfr_lifeatbangkit-bangkit2023-androiddevelopment-activity-7120610895512137728-ko_2?utm_source=share&utm_medium=member_desktop"),
            Item("Sobat Feses", R.drawable.sobat_feses, "https://sobatfeses.tech"),
            Item("Portfolio Website", R.drawable.porto_website, "https://portfoliobybagus.site/")
        )

        val adapter = PortoAdapter(itemList) { item -> onItemClicked(item) }
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.buttonGithub.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/bagusmuhammad18"))
            startActivity(browserIntent)
        }
        binding.ibBack.setOnClickListener{
            finish()
        }
        binding.btnHome.setOnClickListener{
            finish()
        }
        binding.ibMenu.setOnClickListener { showPopupMenu(it) }
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }

    }

    private fun onItemClicked(item: Item) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        startActivity(intent)
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