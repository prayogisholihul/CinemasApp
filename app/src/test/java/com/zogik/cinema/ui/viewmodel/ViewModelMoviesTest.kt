package com.zogik.cinema.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.RepositoryMovie
import com.zogik.cinema.data.room.local.MovieEntity
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ViewModelMoviesTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private val repository = Mockito.mock(RepositoryMovie::class.java)

    @Mock
    private lateinit var observerMovie: Observer<Result<PagingData<MovieEntity>>>

    @Mock
    private lateinit var viewModel: ViewModelMovies

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Result<PagingData<MovieEntity>>>

    @Before
    fun setup() {
        viewModel = ViewModelMovies(repository)
    }

    @Test
    fun getMovie() {
        testCoroutineRule.runBlockingTest {
            @Suppress("UNCHECKED_CAST")
            val pagingData = Mockito.mock(PagingData::class.java) as PagingData<MovieEntity>
            val dummyMovies = MutableLiveData<Result<PagingData<MovieEntity>>>()
            dummyMovies.value = Result.success(pagingData)

            `when`(repository.pagingMovie())
                .thenReturn(dummyMovies)
            viewModel.moviesPagingData()
            verify(repository).pagingMovie()

            // Test ViewModel
            viewModel.moviesPagingData().observeForever(observerMovie)
            verify(observerMovie).onChanged(argumentCaptor.capture())
            viewModel.moviesPagingData().removeObserver(observerMovie)

            // test data yang di-return
            val data = argumentCaptor.value.data
            Assert.assertNotNull(data)
        }
    }
}
