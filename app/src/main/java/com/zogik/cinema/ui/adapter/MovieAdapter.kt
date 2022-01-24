package com.zogik.cinema.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.databinding.ListItemAdapterBinding

class MovieAdapter(
    private var movieArrayList: ArrayList<MovieData.ResultsItem>,
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

        holder.binding.name.text = movie.title
        holder.binding.name.isSelected = true
        holder.binding.date.text = movie.releaseDate

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
