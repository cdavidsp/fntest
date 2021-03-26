package com.csosa.fntest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.csosa.fntest.activities.ListVehiclesActivity
import com.csosa.fntest.adapters.VehicleViewHolder
import com.csosa.fntest.helpers.ViewAction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
internal class ListVehiclesActivityIntegrationTest : BaseTest() {

    @get:Rule
    var activityRule: ActivityTestRule<ListVehiclesActivity> =
        ActivityTestRule(ListVehiclesActivity::class.java, false, false)

    @get:Rule
    val intentsTestRule = IntentsTestRule(ListVehiclesActivity::class.java)

    @Test
    fun shouldNavigateToMapOnItemClickFromList() {

        onView(withId(R.id.vehicles_recycler_view)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        onView(withId(R.id.vehicles_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<VehicleViewHolder>(
                0, ViewAction.clickChildViewWithId(R.id.layout_vehicle_item)
            )
        )
        intended(hasComponent(MAPS_ACTIVITY_COMPONENT))
    }

    companion object {
        private const val MAPS_ACTIVITY_COMPONENT =
            "com.csosa.fntest.activities.MapsActivity"
    }

}
