package com.zogik.cinema.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.DetailTvData
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.Repository
import com.zogik.cinema.data.TvShowData
import com.zogik.cinema.data.room.RoomDb
import com.zogik.cinema.network.ApiNetwork
import com.zogik.cinema.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private var context: Context = Mockito.mock(Context::class.java)

    @Mock
    private val repository = Repository(ApiNetwork.getClient(), RoomDb.invoke(context))

    @Test
    fun repoTestDetailMovies() {
        // id test 634649
        val idTest = 634649

        testCoroutineRule.runBlockingTest {
            `when`(repository.getDetailsMovie(idTest))
                .thenReturn(Response.success(DetailMovieData()))
            repository.getDetailsMovie(idTest)
            verify(repository).getDetailsMovie(idTest)

            Assert.assertNotNull(Result.success(DetailMovieData()))
        }
    }

    @Test
    fun repoTestDetailTv() {
        // id test 110492
        val idTest = 110492

        testCoroutineRule.runBlockingTest {
            `when`(repository.getDetailsTv(idTest))
                .thenReturn(Response.success(DetailTvData()))
            repository.getDetailsTv(idTest)
            verify(repository).getDetailsTv(idTest)

            Assert.assertNotNull(Result.success(DetailTvData()))
        }
    }

    @Test
    fun repoTestDataTv() {

        testCoroutineRule.runBlockingTest {
            `when`(repository.getTvShow())
                .thenReturn(Response.success(TvShowData()))
            repository.getTvShow()
            verify(repository).getTvShow()

            Assert.assertNotNull(Result.success(TvShowData()))
        }
    }

    @Test
    fun repoTestDataMovie() {

        testCoroutineRule.runBlockingTest {
            `when`(repository.getMovieList())
                .thenReturn(Result.success(MovieData()))
            repository.getMovieList()
            verify(repository).getMovieList()

            Assert.assertNotNull(Result.success(MovieData()))
        }
    }
}
