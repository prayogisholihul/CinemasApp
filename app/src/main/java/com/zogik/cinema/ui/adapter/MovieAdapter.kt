package com.zogik.cinema.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zogik.cinema.R
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.databinding.ListItemAdapterBinding

class MovieAdapter(
    private var movieArrayList: ArrayList<MovieData.ResultsItem>,
    private val context: Context,
    private val listener: OnClickListener
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

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
        val movie = movieArrayList[position]
        val imageLink = "https://image.tmdb.org/t/p/w500"

        holder.binding.name.text = movie.title
        holder.binding.name.isSelected = true
        holder.binding.date.text = movie.releaseDate
        Glide.with(context)
            .load(imageLink + movie.posterPath)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imageView)

        holder.binding.containerRoot.setOnClickListener { listener.setonClick(movie) }
    }

    override fun getItemCount() = movieArrayList.size

    class ViewHolder(val binding: ListItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<MovieData.ResultsItem>?) {
        movieArrayList.clear()
        if (data != null) {
            movieArrayList.addAll(data)
        }
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun setonClick(data: MovieData.ResultsItem)
    }
}
