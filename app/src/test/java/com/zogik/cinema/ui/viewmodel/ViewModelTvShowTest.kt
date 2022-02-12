package com.zogik.cinema.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.RepositoryTv
import com.zogik.cinema.data.room.local.TvEntity
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
class ViewModelTvShowTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private val repository = Mockito.mock(RepositoryTv::class.java)

    @Mock
    private lateinit var viewModel: ViewModelTvShow

    @Mock
    private lateinit var observerTv: Observer<Result<PagingData<TvEntity>>>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Result<PagingData<TvEntity>>>

    @Before
    fun setup() {
        viewModel = ViewModelTvShow(repository)
    }

    @Test
    fun successTest() {
        testCoroutineRule.runBlockingTest {
            @Suppress("UNCHECKED_CAST")
            val pagingData = Mockito.mock(PagingData::class.java) as PagingData<TvEntity>
            val dummyTv = MutableLiveData<Result<PagingData<TvEntity>>>()
            dummyTv.value = Result.success(pagingData)

            `when`(repository.pagingTv())
                .thenReturn(dummyTv)
            viewModel.tvPagingData()
            verify(repository).pagingTv()

            // Test ViewModel
            viewModel.tvPagingData().observeForever(observerTv)
            verify(observerTv).onChanged(argumentCaptor.capture())
            viewModel.tvPagingData().removeObserver(observerTv)

            // test data yang di-return
            val data = argumentCaptor.value.data
            Assert.assertNotNull(data)
        }
    }
}
