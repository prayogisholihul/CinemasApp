package com.zogik.cinema.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zogik.cinema.data.TvShowData
import com.zogik.cinema.databinding.ListItemAdapterBinding

class TvShowAdapter(
    private var tvArrayList: ArrayList<TvShowData.ResultsItem>,
    private val context: Context
) :
    RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {

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
        val tvShow = tvArrayList[position]

        holder.binding.name.text = tvShow.name
        holder.binding.date.text = tvShow.firstAirDate
    }

    override fun getItemCount() = tvArrayList.size

    class ViewHolder(val binding: ListItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<TvShowData.ResultsItem>?) {
        tvArrayList.clear()
        if (data != null) {
            tvArrayList.addAll(data)
        }
        notifyDataSetChanged()
    }
}
