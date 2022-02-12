package com.zogik.cinema.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.RepositoryMovie
import com.zogik.cinema.data.room.favorite.MovieFavoriteEntity
import com.zogik.cinema.data.room.local.MovieEntity
import com.zogik.cinema.utils.Result
import com.zogik.cinema.utils.dataDetailMovie
import com.zogik.cinema.utils.dataFavMovie
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryMovieTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private val repository = Mockito.mock(RepositoryMovie::class.java)

    @Test
    fun getDetailsMovie() {
        testCoroutineRule.runBlockingTest {
            dataDetailMovie().id?.let { idMovie ->

                `when`(repository.getDetailsMovie(idMovie))
                    .thenReturn(Result.success(DetailMovieData()))
                repository.getDetailsMovie(idMovie)
                verify(repository).getDetailsMovie(idMovie)

                Assert.assertNotNull(Result.success(DetailMovieData()))
            }
        }
    }

    @Test
    fun pagingMovie() {
        @Suppress("UNCHECKED_CAST")
        val pagingData = Mockito.mock(PagingData::class.java) as PagingData<MovieEntity>
        val dummyMovies = MutableLiveData<Result<PagingData<MovieEntity>>>()
        dummyMovies.value = Result.success(pagingData)

        `when`(repository.pagingMovie())
            .thenReturn(dummyMovies)
        repository.pagingMovie()
        verify(repository).pagingMovie()

        Assert.assertNotNull(dummyMovies.value)
    }

    @Test
    fun insertFavoriteMovie() {
        testCoroutineRule.runBlockingTest {
            `when`(repository.insertFavoriteMovie(dataFavMovie()))
                .thenReturn(dataFavMovie().id?.toLong())
            repository.insertFavoriteMovie(dataFavMovie())
            verify(repository).insertFavoriteMovie(dataFavMovie())

            Assert.assertNotNull(dataFavMovie())
            Assert.assertEquals(dataDetailMovie().id, dataFavMovie().id)
        }
    }

    @Test
    fun deleteFavoriteMovie() {
        testCoroutineRule.runBlockingTest {
            `when`(repository.deleteFavoriteMovie(dataFavMovie()))
                .thenReturn(dataFavMovie().id)
            repository.deleteFavoriteMovie(dataFavMovie())
            verify(repository).deleteFavoriteMovie(dataFavMovie())
        }
    }

    @Test
    fun getFavoriteMovieById() {
        val dummy: MutableLiveData<MovieFavoriteEntity> = MutableLiveData()
        val dummyMovies: LiveData<MovieFavoriteEntity> = dummy
        dummy.value = dataFavMovie()

        dataDetailMovie().id?.let { idMovie ->
            `when`(repository.findFavoriteMovie(idMovie))
                .thenReturn(dummyMovies)
            repository.findFavoriteMovie(idMovie)
            verify(repository).findFavoriteMovie(idMovie)
        }
        Assert.assertEquals(dummyMovies.value, dataFavMovie())
    }

    @Test
    fun pagingFavMovie() {
        @Suppress("UNCHECKED_CAST")
        val pagingData = Mockito.mock(PagingData::class.java) as PagingData<MovieFavoriteEntity>
        val dummyMovies = MutableLiveData<PagingData<MovieFavoriteEntity>>()
        val dummy: LiveData<PagingData<MovieFavoriteEntity>> = dummyMovies
        dummyMovies.value = pagingData

        `when`(repository.pagingFavMovie())
            .thenReturn(dummy)
        repository.pagingFavMovie()
        verify(repository).pagingFavMovie()

        Assert.assertNotNull(dummyMovies.value)
    }
}
