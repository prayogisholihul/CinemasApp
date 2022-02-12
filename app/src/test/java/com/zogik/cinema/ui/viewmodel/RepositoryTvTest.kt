package com.zogik.cinema.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.DetailTvData
import com.zogik.cinema.data.RepositoryTv
import com.zogik.cinema.data.room.favorite.TvFavoriteEntity
import com.zogik.cinema.data.room.local.TvEntity
import com.zogik.cinema.utils.Result
import com.zogik.cinema.utils.dataDetailTv
import com.zogik.cinema.utils.dataFavTv
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryTvTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private val repository = Mockito.mock(RepositoryTv::class.java)

    @Test
    fun getDetailsTv() {
        testCoroutineRule.runBlockingTest {
            dataDetailTv().id?.let { idTv ->

                Mockito.`when`(repository.getDetailsTv(idTv))
                    .thenReturn(Result.success(DetailTvData()))
                repository.getDetailsTv(idTv)
                Mockito.verify(repository).getDetailsTv(idTv)

                Assert.assertNotNull(Result.success(DetailTvData()))
            }
        }
    }

    @Test
    fun pagingMovie() {
        @Suppress("UNCHECKED_CAST")
        val pagingData = Mockito.mock(PagingData::class.java) as PagingData<TvEntity>
        val dummyTv = MutableLiveData<Result<PagingData<TvEntity>>>()
        dummyTv.value = Result.success(pagingData)

        Mockito.`when`(repository.pagingTv())
            .thenReturn(dummyTv)
        repository.pagingTv()
        Mockito.verify(repository).pagingTv()

        Assert.assertNotNull(dummyTv.value)
    }

    @Test
    fun getFavoriteMovieById() {
        val dummy: MutableLiveData<TvFavoriteEntity> = MutableLiveData()
        val dummyTv: LiveData<TvFavoriteEntity> = dummy
        dummy.value = dataFavTv()

        dataFavTv().id?.let { idTv ->
            Mockito.`when`(repository.findFavoriteTv(idTv))
                .thenReturn(dummyTv)
            repository.findFavoriteTv(idTv)
            Mockito.verify(repository).findFavoriteTv(idTv)
        }
        Assert.assertEquals(dummyTv.value, dataFavTv())
    }

    @Test
    fun insertFavoriteMovie() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.insertFavoriteTv(dataFavTv()))
                .thenReturn(dataFavTv().id?.toLong())
            repository.insertFavoriteTv(dataFavTv())
            Mockito.verify(repository).insertFavoriteTv(dataFavTv())

            Assert.assertNotNull(dataFavTv())
            Assert.assertEquals(dataDetailTv().id, dataFavTv().id)
        }
    }

    @Test
    fun deleteFavoriteMovie() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.deleteFavoriteTv(dataFavTv()))
                .thenReturn(dataFavTv().id)
            repository.deleteFavoriteTv(dataFavTv())
            Mockito.verify(repository).deleteFavoriteTv(dataFavTv())
        }
    }

    @Test
    fun pagingFavMovie() {
        @Suppress("UNCHECKED_CAST")
        val pagingData = Mockito.mock(PagingData::class.java) as PagingData<TvFavoriteEntity>
        val dummyTv = MutableLiveData<PagingData<TvFavoriteEntity>>()
        val dummy: LiveData<PagingData<TvFavoriteEntity>> = dummyTv
        dummyTv.value = pagingData

        Mockito.`when`(repository.pagingFavTv())
            .thenReturn(dummy)
        repository.pagingFavTv()
        Mockito.verify(repository).pagingFavTv()

        Assert.assertNotNull(dummyTv.value)
    }
}
