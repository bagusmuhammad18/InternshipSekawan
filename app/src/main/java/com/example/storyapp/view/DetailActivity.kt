package com.example.storyapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ActivityDetailBinding
import com.example.storyapp.view.profile.ProfileActivity
import com.example.storyapp.view.upload.UploadActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
//        supportPostponeEnterTransition()
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from the Intent
        val storyImage = intent.getStringExtra("story_image")
        val storyName = intent.getStringExtra("story_name")
        val storyDesc = intent.getStringExtra("story_desc")

        binding.tvName.text = storyName
        binding.tvDesc.text = storyDesc

        // Menggunakan Glide untuk memuat gambar
        Glide.with(binding.ivStory)
            .load(storyImage)
            .into(binding.ivStory)
        binding.ibBack.setOnClickListener{
            finish()
        }
        binding.btnHome.setOnClickListener{
            finish()
        }
        binding.btnAdd.setOnClickListener {
            // Start the UploadActivity
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnProfile.setOnClickListener {
            // Start the UploadActivity
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}
