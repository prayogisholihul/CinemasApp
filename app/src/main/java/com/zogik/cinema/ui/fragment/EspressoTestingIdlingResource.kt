package com.zogik.cinema.ui.fragment

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

class EspressoTestingIdlingResource {
    private val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        if (!mCountingIdlingResource.isIdleNow) {
            mCountingIdlingResource.decrement()
        }
    }

    fun getIdlingResource(): IdlingResource {
        return mCountingIdlingResource
    }
}
