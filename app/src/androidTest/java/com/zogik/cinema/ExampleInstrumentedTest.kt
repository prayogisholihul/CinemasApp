package com.zogik.cinema

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.zogik.cinema.ui.activity.MainActivity
import com.zogik.cinema.ui.adapter.MovieAdapter
import com.zogik.cinema.ui.adapter.TvShowAdapter
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun moviesTest() {
        onView(withId(R.id.loading)).check(matches((isDisplayed())))
        onView(isRoot()).perform(waitFor(5000))
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
        onView(withId(R.id.fabShare)).perform(click())
    }

    @Test
    fun tvShowTest() {
        onView(withId(R.id.loading)).check(matches((isDisplayed())))
        onView(allOf(withText(R.string.tv_show_banner))).perform(click())
        onView(isRoot()).perform(waitFor(5000))
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
        onView(withId(R.id.fabShare)).perform(click())
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
