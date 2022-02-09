package com.zogik.cinema.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.DetailTvData
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
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private lateinit var viewModel: DetailViewModel

    @Mock
    private var context: Context = mock(Context::class.java)

    @Mock
    private val repository = Repository(ApiNetwork.getClient(), RoomDb.invoke(context))

    @Mock
    private lateinit var observerMovie: Observer<Result<DetailMovieData?>>

    @Mock
    private lateinit var observerTv: Observer<Result<DetailTvData?>>

    @Captor
    private lateinit var argumentCaptorMovie: ArgumentCaptor<Result<DetailMovieData?>>

    @Captor
    private lateinit var argumentCaptorTv: ArgumentCaptor<Result<DetailTvData?>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun testMovieData() {
        // id test 634649

        val idTest = 634649
        testCoroutineRule.runBlockingTest {
            `when`(repository.getDetailsMovie(idTest))
                .thenReturn(Response.success(DetailMovieData()))
            viewModel.getDetailMovie(idTest)
            verify(repository).getDetailsMovie(idTest)
            viewModel.dataDetailMovie.observeForever(observerMovie)
            verify(observerMovie).onChanged(argumentCaptorMovie.capture())

            Assert.assertNotNull(argumentCaptorMovie.value)
        }
    }

    @Test
    fun testTvData() {
        // id test 110492

        val idTest = 110492
        testCoroutineRule.runBlockingTest {
            `when`(repository.getDetailsTv(idTest))
                .thenReturn(Response.success(DetailTvData()))
            viewModel.getDetailTv(idTest)
            verify(repository).getDetailsTv(idTest)
            viewModel.dataDetailTv.observeForever(observerTv)
            verify(observerTv).onChanged(argumentCaptorTv.capture())

            Assert.assertNotNull(argumentCaptorTv.value)
        }
    }
}
