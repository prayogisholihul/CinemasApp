package com.zogik.cinema.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zogik.cinema.coroutines.CoroutinesTest
import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.DetailTvData
import com.zogik.cinema.data.RepositoryMovie
import com.zogik.cinema.data.RepositoryTv
import com.zogik.cinema.data.room.favorite.MovieFavoriteEntity
import com.zogik.cinema.data.room.favorite.TvFavoriteEntity
import com.zogik.cinema.utils.Result
import com.zogik.cinema.utils.dataDetailMovie
import com.zogik.cinema.utils.dataDetailTv
import com.zogik.cinema.utils.dataFavMovie
import com.zogik.cinema.utils.dataFavTv
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
class DetailViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTest()

    @Mock
    private lateinit var viewModel: DetailViewModel

    @Mock
    private val repositoryMovie = Mockito.mock(RepositoryMovie::class.java)

    @Mock
    private val repositoryTv = Mockito.mock(RepositoryTv::class.java)

    @Mock
    private lateinit var observerMovie: Observer<Result<DetailMovieData?>>

    @Mock
    private lateinit var observerTv: Observer<Result<DetailTvData?>>

    @Mock
    private lateinit var observerGetFavMovieByID: Observer<MovieFavoriteEntity>

    @Mock
    private lateinit var observerGetFavTvByID: Observer<TvFavoriteEntity>

    @Captor
    private lateinit var argumentCaptorMovie: ArgumentCaptor<Result<DetailMovieData?>>

    @Captor
    private lateinit var argumentCaptorTv: ArgumentCaptor<Result<DetailTvData?>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repositoryMovie, repositoryTv)
    }

    @Test
    fun getDetailMovie() {
        // id test 634649

        val idTest = 634649
        testCoroutineRule.runBlockingTest {
            `when`(repositoryMovie.getDetailsMovie(idTest))
                .thenReturn(Result.success(DetailMovieData()))
            viewModel.getDetailMovie(idTest)
            verify(repositoryMovie).getDetailsMovie(idTest)
            viewModel.dataDetailMovie.observeForever(observerMovie)
            verify(observerMovie).onChanged(argumentCaptorMovie.capture())

            Assert.assertNotNull(argumentCaptorMovie.value)
        }
    }

    @Test
    fun getDetailTv() {
        // id test 110492

        val idTest = 110492
        testCoroutineRule.runBlockingTest {
            `when`(repositoryTv.getDetailsTv(idTest))
                .thenReturn(Result.success(DetailTvData()))
            viewModel.getDetailTv(idTest)
            verify(repositoryTv).getDetailsTv(idTest)
            viewModel.dataDetailTv.observeForever(observerTv)
            verify(observerTv).onChanged(argumentCaptorTv.capture())

            Assert.assertNotNull(argumentCaptorTv.value)
        }
    }

    @Test
    fun insertToFavoriteMovie() {
        testCoroutineRule.runBlockingTest {
            `when`(repositoryMovie.insertFavoriteMovie(dataFavMovie()))
                .thenReturn(dataFavMovie().id?.toLong())
            viewModel.insertToFavoriteMovie(dataDetailMovie())
            verify(repositoryMovie).insertFavoriteMovie(dataFavMovie())

            Assert.assertNotNull(dataFavMovie())
            Assert.assertEquals(dataDetailMovie().id, dataFavMovie().id)
        }
    }

    @Test
    fun getFavoriteMovieById() {
        val dummy: MutableLiveData<MovieFavoriteEntity> = MutableLiveData()
        val dummyMovies: LiveData<MovieFavoriteEntity> = dummy
        dummy.value = dataFavMovie()

        `when`(repositoryMovie.findFavoriteMovie(634649))
            .thenReturn(dummyMovies)
        viewModel.getFavoriteMovieById(634649)
        verify(repositoryMovie).findFavoriteMovie(634649)

        viewModel.getFavoriteMovieById(634649).observeForever(observerGetFavMovieByID)
        verify(observerGetFavMovieByID).onChanged(dataFavMovie())
        viewModel.getFavoriteMovieById(634649).removeObserver(observerGetFavMovieByID)

        Assert.assertEquals(dummyMovies.value, dataFavMovie())
    }

    @Test
    fun deleteFavoriteMovie() {
        testCoroutineRule.runBlockingTest {
            `when`(repositoryMovie.deleteFavoriteMovie(dataFavMovie()))
                .thenReturn(dataFavMovie().id)
            viewModel.deleteFavoriteMovie(dataFavMovie())
            verify(repositoryMovie).deleteFavoriteMovie(dataFavMovie())
        }
    }

    @Test
    fun insertToFavoriteTv() {
        testCoroutineRule.runBlockingTest {
            `when`(repositoryTv.insertFavoriteTv(dataFavTv()))
                .thenReturn(dataFavTv().id?.toLong())
            viewModel.insertToFavoriteTv(dataDetailTv())
            verify(repositoryTv).insertFavoriteTv(dataFavTv())

            Assert.assertNotNull(dataFavTv())
            Assert.assertEquals(dataDetailTv().id, dataFavTv().id)
        }
    }

    @Test
    fun getFavoriteTvById() {
        val dummy: MutableLiveData<TvFavoriteEntity> = MutableLiveData()
        val dummyTv: LiveData<TvFavoriteEntity> = dummy
        dummy.value = dataFavTv()

        dataDetailTv().id?.let { idTv ->
            `when`(repositoryTv.findFavoriteTv(idTv))
                .thenReturn(dummyTv)
            viewModel.getFavoriteTvById(idTv)
            verify(repositoryTv).findFavoriteTv(idTv)

            viewModel.getFavoriteTvById(idTv).observeForever(observerGetFavTvByID)
            verify(observerGetFavTvByID).onChanged(dataFavTv())
            viewModel.getFavoriteTvById(idTv).removeObserver(observerGetFavTvByID)
        }

        Assert.assertEquals(dummyTv.value, dataFavTv())
    }

    @Test
    fun deleteFavoriteTv() {
        testCoroutineRule.runBlockingTest {
            `when`(repositoryTv.deleteFavoriteTv(dataFavTv()))
                .thenReturn(dataFavTv().id)
            viewModel.deleteFavoriteTv(dataFavTv())
            verify(repositoryTv).deleteFavoriteTv(dataFavTv())
        }
    }
}
