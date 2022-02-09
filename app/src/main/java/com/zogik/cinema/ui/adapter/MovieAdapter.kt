package com.zogik.cinema.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zogik.cinema.R
import com.zogik.cinema.data.room.MovieEntity
import com.zogik.cinema.databinding.ListItemAdapterBinding

class MovieAdapter(
    private val context: Context,
    private val listener: OnClickListener
) :
    PagingDataAdapter<MovieEntity, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        val imageLink = "https://image.tmdb.org/t/p/w500"

        holder.binding.name.text = movie?.title
        holder.binding.name.isSelected = true
        holder.binding.date.text = movie?.releaseDate
        Glide.with(context)
            .load(imageLink + movie?.posterPath)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imageView)

        holder.binding.containerRoot.setOnClickListener {
            if (movie != null) {
                listener.setonClick(movie)
            }
        }
    }

    class ViewHolder(val binding: ListItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun setonClick(data: MovieEntity)
    }
}
