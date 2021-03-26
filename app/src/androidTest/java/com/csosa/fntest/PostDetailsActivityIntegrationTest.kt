package com.csosa.fntest

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.csosa.fntest.activities.MapsActivity
import com.csosa.fntest.commons.NavigationUtils
import com.csosa.fntest.models.CoordinatePresentation
import com.csosa.fntest.models.VehiclePresentation
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class MapsActivityIntegrationTest : BaseTest() {

    @get:Rule
    var activityRule: ActivityTestRule<MapsActivity> =
        ActivityTestRule(MapsActivity::class.java, false, false)

    @Test
    fun shouldLoadDataOnLaunchWithValidVehicle() {
        val intent = Intent().putExtra(
            NavigationUtils.VEHICLE_PARCEL_KEY,
            VehiclePresentation(
                id = 1,
                fleetType = "TAXI",
                coordinate = CoordinatePresentation(53.63774681071923, 10.077876811952127),
                heading = 353.90969927548304f,
                distance = "16.8 km",
                duration = "23 min",
                imageRes = R.drawable.taxi
            )
        )
        activityRule.launchActivity(intent)
        onView(withId(R.id.bottomSheet)).check(matches(isDisplayed()))
    }

    @After
    override fun tearDown() {
        super.tearDown()
        activityRule.finishActivity()
    }

}
