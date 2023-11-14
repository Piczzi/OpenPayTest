package com.example.openpaytest.ui.view.pictures.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openpaytest.R
import com.example.openpaytest.databinding.AdpSavedImagesBinding

class AdpSavedImages : RecyclerView.Adapter<AdpSavedImages.ViewHolder>() {

    private var movieList: List<String> = listOf()

    var onClickOption: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdpSavedImagesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie, onClickOption)
    }

    override fun getItemCount() = movieList.size

    class ViewHolder(private val binding: AdpSavedImagesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: String, onClickOption: ((String) -> Unit)?) {
            Glide.with(binding.ivImage)
                .load(movie)
                .centerCrop()
                .placeholder(R.drawable.ic_lost_signal)
                .into(binding.ivImage)

            binding.cvImage.setOnClickListener { onClickOption?.invoke(movie) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(movieList: List<String>?) {
        if (!movieList.isNullOrEmpty()) {
            this.movieList = movieList
            notifyDataSetChanged()
        }
    }
}
