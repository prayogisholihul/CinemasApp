package com.zogik.cinema.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.Repository
import com.zogik.cinema.network.ApiNetwork
import com.zogik.cinema.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ViewModelMoviesTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private val repository = Repository(ApiNetwork.getClient())

    @Mock
    private lateinit var apiMoviesObserver: Observer<Resource<MovieData?>>

    @Test
    fun successTest() {
        testCoroutineRule.runBlockingTest {
            doReturn(
                MovieData()
            )
                .`when`(repository)
                .getMovieList()
            val viewModel = ViewModelMovies(repository)
            viewModel.moviesData.observeForever(apiMoviesObserver)
            verify(repository).getMovieList()
            verify(apiMoviesObserver).onChanged(Resource.Success(MovieData(any())))
            viewModel.moviesData.removeObserver(apiMoviesObserver)
        }
    }

    @Test
    fun errorTest() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Data Can't Be Loaded"
            doThrow(RuntimeException(errorMessage))
                .`when`(repository)
                .getMovieList()
            val viewModel = ViewModelMovies(repository)
            viewModel.moviesData.observeForever(apiMoviesObserver)
            verify(repository).getMovieList()
            verify(apiMoviesObserver).onChanged(
                Resource.Error(
                    RuntimeException(errorMessage).toString(),
                    MovieData(any())
                )
            )
            viewModel.moviesData.removeObserver(apiMoviesObserver)
        }
    }
}
