package com.zogik.cinema

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.zogik.cinema.ui.activity.MainActivity
import com.zogik.cinema.ui.adapter.MovieAdapter
import com.zogik.cinema.ui.adapter.TvShowAdapter
import com.zogik.cinema.utils.IdlingResource
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    private lateinit var instrumentalContext: Context

    @Before
    fun register() {
        instrumentalContext = InstrumentationRegistry.getInstrumentation().targetContext
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(IdlingResource.idlingresource)
    }

    @After
    fun unregister() {
        IdlingRegistry.getInstance().unregister(IdlingResource.idlingresource)
    }

    @Test
    fun homeTest() {
        onView(allOf(withId(R.id.rvContent), isDisplayed())).perform(swipeUp()).perform(swipeDown())
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.contentTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPict)).check(matches(isDisplayed()))
        onView(withId(R.id.contentDate)).check(matches(isDisplayed()))
        onView(withId(R.id.contentRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.fabFavorite)).perform(click())

        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withText(R.string.tv_show_banner)).perform(click())
        onView(allOf(withId(R.id.rvContent), isDisplayed())).perform(swipeUp()).perform(swipeDown())
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TvShowAdapter.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.contentTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPict)).check(matches(isDisplayed()))
        onView(withId(R.id.contentDate)).check(matches(isDisplayed()))
        onView(withId(R.id.contentRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.fabFavorite)).perform(click())
    }

    @Test
    fun favoriteTest() {
        onView(withId(R.id.action_favorite)).perform(click())
        onView(allOf(withId(R.id.rvContent), isDisplayed())).perform(swipeUp()).perform(swipeDown())
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TvShowAdapter.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.contentTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPict)).check(matches(isDisplayed()))
        onView(withId(R.id.contentDate)).check(matches(isDisplayed()))
        onView(withId(R.id.contentRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.fabFavorite)).perform(click())

        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withText(R.string.tv_show_banner)).perform(click())
        onView(allOf(withId(R.id.rvContent), isDisplayed())).perform(swipeUp()).perform(swipeDown())
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TvShowAdapter.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.contentTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPict)).check(matches(isDisplayed()))
        onView(withId(R.id.contentDate)).check(matches(isDisplayed()))
        onView(withId(R.id.contentRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.fabFavorite)).perform(click())
    }
}
