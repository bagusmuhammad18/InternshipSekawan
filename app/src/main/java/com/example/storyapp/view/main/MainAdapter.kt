package com.example.storyapp.view.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.api.ListStoryItem
import com.example.storyapp.databinding.EachStoryBinding
import com.example.storyapp.view.DetailActivity

class MainAdapter(private val itemClickListener: OnItemClickListener) :
    PagingDataAdapter<ListStoryItem, MainAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(story: ListStoryItem, itemView: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EachStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    inner class ViewHolder(private val binding: EachStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: ListStoryItem?) {
            binding.tvName.text = story?.name
            binding.tvDesc.text = story?.description

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    if (story != null) {
                        itemClickListener.onItemClick(story, binding.root)
                    }
                }
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivStory, "image"),
                        Pair(binding.tvName, "name"),
                        Pair(binding.tvDesc, "desc")
                    )

                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("story_image", story?.photoUrl)
                intent.putExtra("story_name", story?.name)
                intent.putExtra("story_desc", story?.description)
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }

            // Menggunakan Glide untuk memuat gambar
            Glide.with(binding.ivStory)
                .load(story?.photoUrl)
                .into(binding.ivStory)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
