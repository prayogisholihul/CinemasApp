package com.zogik.cinema.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.DetailTvData
import com.zogik.cinema.data.Repository
import com.zogik.cinema.network.ApiNetwork
import com.zogik.cinema.utils.State
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
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ActivityDetailTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private lateinit var viewModel: DetailViewModel

    @Mock
    private val repository = Repository(ApiNetwork.getClient())

    @Mock
    private lateinit var observerMovie: Observer<State<DetailMovieData?>>

    @Mock
    private lateinit var observerTv: Observer<State<DetailTvData?>>

    @Captor
    private lateinit var argumentCaptorMovie: ArgumentCaptor<State<DetailMovieData?>>

    @Captor
    private lateinit var argumentCaptorTv: ArgumentCaptor<State<DetailTvData?>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun testMovieData() {

        // TEST REPOSITORY
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

        // TEST REPOSITORY
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
