package com.zogik.cinema.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.TvShowData
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
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ActivityDetailTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var viewModel: DetailViewModel

    @Mock
    private lateinit var observerMovie: Observer<MovieData.ResultsItem?>

    @Mock
    private lateinit var observerTv: Observer<TvShowData.ResultsItem?>

    @Captor
    private lateinit var argumentCaptorMovie: ArgumentCaptor<MovieData.ResultsItem?>

    @Captor
    private lateinit var argumentCaptorTv: ArgumentCaptor<TvShowData.ResultsItem?>

    @Before
    fun setUp() {
        viewModel = DetailViewModel()
    }

    @Test
    fun testMovieData() {
        val movieData = MovieData.ResultsItem(
            title = "eternal",
            posterPath = "foto",
            releaseDate = "12-12-2022",
            overview = "Mantap",
            voteAverage = 9.0
        )

        viewModel.setDataDetailMovie(movieData)
        viewModel.getDataDetailMovie()
        viewModel.dataDetailMovie.observeForever(observerMovie)
        verify(observerMovie).onChanged(argumentCaptorMovie.capture())

        Assert.assertNotNull(argumentCaptorMovie.value)
    }

    @Test
    fun testTvData() {
        val tvData = TvShowData.ResultsItem(
            name = "Hawkeye",
            posterPath = "foto",
            firstAirDate = "12-12-2022",
            overview = "Mantap",
            voteAverage = 9.0
        )

        viewModel.setDataDetailTv(tvData)
        viewModel.getDataDetailTv()
        viewModel.dataDetailTv.observeForever(observerTv)
        verify(observerTv).onChanged(argumentCaptorTv.capture())

        Assert.assertNotNull(argumentCaptorTv.value)
    }
}
