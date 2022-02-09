package com.zogik.cinema.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.Repository
import com.zogik.cinema.data.room.RoomDb
import com.zogik.cinema.network.ApiNetwork
import com.zogik.cinema.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ViewModelMoviesTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private var context: Context = Mockito.mock(Context::class.java)

    @Mock
    private val repository = Repository(ApiNetwork.getClient(), RoomDb.invoke(context))

    @Mock
    private lateinit var observerMovie: Observer<Result<DetailMovieData?>>

    @Mock
    private lateinit var viewModel: ViewModelMovies

    @Mock
    private lateinit var apiMoviesObserver: Observer<Result<MovieData?>>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Result<MovieData?>>

    @Before
    fun setup() {
        viewModel = ViewModelMovies(repository)
    }

//    @Test
//    fun successTest() {
//        testCoroutineRule.runBlockingTest {
//            `when`(repository.getMovieList())
//                .thenReturn(Result.success(MovieData()))
//            viewModel.moviesPagingData
//            verify(repository).getMovieList()
//
//            // Test ViewModel
//            viewModel.moviesData.observeForever(apiMoviesObserver)
//            verify(apiMoviesObserver).onChanged(argumentCaptor.capture())
//            viewModel.moviesData.removeObserver(apiMoviesObserver)
//
//            // test data yang direturn dari repo
//            val data: MovieData? = argumentCaptor.value.data
//            Assert.assertNotNull(data)
//        }
//    }
//
//    @Test
//    fun errorTest() {
//        testCoroutineRule.runBlockingTest {
//            val errorMessage = "Data Can't Be Loaded"
//            `when`(repository.getMovieList())
//                .thenThrow(RuntimeException(errorMessage))
//            viewModel.getMovies()
//            verify(repository).getMovieList()
//
//            // Test ViewModel
//            viewModel.moviesData.observeForever(apiMoviesObserver)
//            verify(apiMoviesObserver).onChanged(argumentCaptor.capture())
//            viewModel.moviesData.removeObserver(apiMoviesObserver)
//
//            // test data yang direturn
//            val data: MovieData? = argumentCaptor.value.data
//            Assert.assertNull(data)
//        }
//    }
}
