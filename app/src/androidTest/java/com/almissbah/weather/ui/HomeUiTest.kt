package com.almissbah.weather.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.almissbah.weather.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeUiTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testNavigateToSearch() {
        onView(ViewMatchers.withId(R.id.tvSearch)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.etSearch)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.rvSearchResults)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.rvSearchResults)).check(matches(isDisplayed()))
    }

    @Test
    fun testForecastSearch() {
        onView(ViewMatchers.withId(R.id.tvForecast)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.tvCurrentCity)).check(matches(isDisplayed()))
    }

}


