package com.zogik.cinema.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zogik.cinema.R
import com.zogik.cinema.data.room.favorite.TvFavoriteEntity
import com.zogik.cinema.databinding.ListItemAdapterBinding

class TvFavoriteAdapter(private val context: Context, private val listener: OnClickListener) :
    PagingDataAdapter<TvFavoriteEntity, TvFavoriteAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvFavoriteEntity>() {
            override fun areItemsTheSame(
                oldItem: TvFavoriteEntity,
                newItem: TvFavoriteEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TvFavoriteEntity,
                newItem: TvFavoriteEntity
            ): Boolean {
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

        holder.binding.name.text = movie?.name
        holder.binding.name.isSelected = true
        holder.binding.date.text = movie?.firstAirDate
        Glide.with(context)
            .load(imageLink + movie?.posterPath)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imageView)

        holder.binding.containerRoot.setOnClickListener {
            if (movie != null) {
                listener.onClick(movie)
            }
        }
    }

    class ViewHolder(val binding: ListItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun onClick(data: TvFavoriteEntity)
    }
}
