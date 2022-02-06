package com.zogik.cinema.di

import android.app.Application
import com.zogik.cinema.data.Repository
import com.zogik.cinema.data.room.RoomDb
import com.zogik.cinema.network.ApiNetwork
import com.zogik.cinema.ui.viewmodel.DetailViewModel
import com.zogik.cinema.ui.viewmodel.ViewModelMovies
import com.zogik.cinema.ui.viewmodel.ViewModelTvShow
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Application : Application() {
    private val viewModelModule = module {
        viewModel {
            ViewModelMovies(get())
        }
        viewModel {
            ViewModelTvShow(get())
        }
        viewModel {
            DetailViewModel()
        }
    }

    private val single = module {
        single { ApiNetwork.getClient() }

        single { Repository(get(), get()) }

        single { RoomDb(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidContext(this@Application)
            modules(listOf(viewModelModule, single))
        }
    }
}
