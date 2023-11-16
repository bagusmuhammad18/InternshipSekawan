package com.example.storyapp.view.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R

class PortoAdapter(
    private val itemList: List<Item>,
    private val clickListener: (Item) -> Unit
) : RecyclerView.Adapter<PortoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_porto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_image_porto)
        private val textView: TextView = itemView.findViewById(R.id.tv_name_porto)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Item) {
            imageView.setImageResource(item.gambarResId)
            textView.text = item.nama
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = itemList[position]
                clickListener(item)
            }
        }
    }
}
