package com.zogik.cinema.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zogik.cinema.R
import com.zogik.cinema.data.room.local.TvEntity
import com.zogik.cinema.databinding.ListItemAdapterBinding

class TvShowAdapter(
    private val context: Context,
    private val listener: OnClickListener
) :
    PagingDataAdapter<TvEntity, TvShowAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
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
        val tvShow = getItem(position)
        val imageLink = "https://image.tmdb.org/t/p/w500"

        holder.binding.name.text = tvShow?.name
        holder.binding.name.isSelected = true
        holder.binding.date.text = tvShow?.firstAirDate
        Glide.with(context)
            .load(imageLink + tvShow?.posterPath)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imageView)

        holder.binding.containerRoot.setOnClickListener {
            if (tvShow != null) {
                listener.setonClick(tvShow)
            }
        }
    }

    class ViewHolder(val binding: ListItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun setonClick(data: TvEntity)
    }
}
