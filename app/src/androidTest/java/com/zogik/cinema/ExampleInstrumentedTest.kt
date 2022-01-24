package com.zogik.cinema

import android.view.View
import androidx.test.espresso.Espresso.onIdle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.zogik.cinema.ui.activity.MainActivity
import com.zogik.cinema.ui.fragment.EspressoTestingIdlingResource
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
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
    fun loadingState() {
        onView(withId(R.id.loading)).check(matches(isDisplayed()))
    }

    @Before
    fun registerIdlingResource() {
        // let espresso know to synchronize with background tasks
        IdlingRegistry.getInstance().register(EspressoTestingIdlingResource().getIdlingResource())
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoTestingIdlingResource().getIdlingResource())
    }

    @Test
    fun successState() {
        onView(withId(R.id.loading)).check(matches((isDisplayed())))
        onView(allOf(withId(R.id.rvContent), isDisplayed())).perform(
            ViewActions.swipeUp()
        )

//        onView(withId(R.id.rvContent)).check(matches(isDisplayed()))
//        onView(withId(R.id.loading)).check(matches(not(isDisplayed())))
//        onView(withId(R.id.rvContent)).perform(ViewActions.swipeUp())
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
