package com.zogik.cinema.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.Repository
import com.zogik.cinema.data.TvShowData
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
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ViewModelTvShowTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private val repository = Repository(ApiNetwork.getClient())

    @Mock
    private lateinit var viewModel: ViewModelTvShow

    @Mock
    private lateinit var apiMoviesObserver: Observer<Result<TvShowData?>>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Result<TvShowData?>>

    @Before
    fun setup() {
        viewModel = ViewModelTvShow(repository)
    }

    @Test
    fun successTest() {
        testCoroutineRule.runBlockingTest {
            `when`(repository.getTvShow())
                .thenReturn(Response.success(TvShowData()))
            viewModel.getTvShow()
            viewModel.tvShowData.observeForever(apiMoviesObserver)
            verify(repository).getTvShow()
            verify(apiMoviesObserver).onChanged(argumentCaptor.capture())
            viewModel.tvShowData.removeObserver(apiMoviesObserver)

            val data: TvShowData? = argumentCaptor.value.data
            Assert.assertNotNull(data)
        }
    }

    @Test
    fun errorTest() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Data Can't Be Loaded"
            doThrow(RuntimeException(errorMessage))
                .`when`(repository)
                .getTvShow()
            viewModel.getTvShow()
            viewModel.tvShowData.observeForever(apiMoviesObserver)
            verify(repository).getTvShow()
            verify(apiMoviesObserver).onChanged(argumentCaptor.capture())
            viewModel.tvShowData.removeObserver(apiMoviesObserver)

            val data: TvShowData? = argumentCaptor.value.data
            Assert.assertNull(data)
        }
    }
}
