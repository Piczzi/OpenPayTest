package com.example.openpaytest.ui.view.movies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openpaytest.data.model.local.UserMovies
import com.example.openpaytest.databinding.AdpLargeMoviesBinding
import com.example.openpaytest.ui.view.movies.adapter.AdpLargeMovies.ViewHolder
import com.example.openpaytest.utils.Constants
import com.example.openpaytest.utils.MethodsHandler

class AdpLargeMovies : RecyclerView.Adapter<ViewHolder>() {

    private var movieList: List<UserMovies> = listOf()

    var onClickOption: ((UserMovies) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdpLargeMoviesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie, onClickOption)
    }

    override fun getItemCount() = movieList.size

    class ViewHolder(private val binding: AdpLargeMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: UserMovies, onClickOption: ((UserMovies) -> Unit)?) {
            Glide.with(binding.ivMovie)
                .load(Constants.URL_IMAGES + movie.posterPath)
                .centerCrop()
                .into(binding.ivMovie)

            binding.mtMovieTitle.text = MethodsHandler.validateEmptyField(movie.title)
            binding.mtMovieRelease.text = MethodsHandler.validateEmptyField(movie.releaseDate)
            binding.cvMovie.setOnClickListener { onClickOption?.invoke(movie) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(movieList: List<UserMovies>?) {
        if (!movieList.isNullOrEmpty()) {
            this.movieList = movieList
            notifyDataSetChanged()
        }
    }
}
