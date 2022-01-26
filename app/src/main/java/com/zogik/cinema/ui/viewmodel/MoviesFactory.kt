package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zogik.cinema.data.Repository

@Suppress("UNCHECKED_CAST")
class MoviesFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModelMovies(repository) as T
    }
}
