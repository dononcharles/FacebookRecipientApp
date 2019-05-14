package com.chaldrac.facebookrecipientapp.views

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.SdkSuppress
import androidx.test.rule.ActivityTestRule
import com.chaldrac.facebookrecipientapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@SdkSuppress(minSdkVersion = 21)
class MainEspressoActivityTest{

    companion object {
        const val BILL_AMOUNT = "100"
    }

    @Rule
    var mActivityRule = ActivityTestRule<MainEspressoActivity>(MainEspressoActivity::class.java)
    var mIntentsRule = IntentsTestRule<MainEspressoActivity>(MainEspressoActivity::class.java)

    @Test
    fun tipTotalAmount15percent(){
        onView(withId(R.id.input_billAmount)).perform(typeText(BILL_AMOUNT), closeSoftKeyboard())

        onView(withText("15% Tip")).perform(click())

        val expertectText = "125.0"
        onView(withId(R.id.totalAmount)).check(matches(withText(expertectText)))

        // testing intent
        //onView(withId(R.id.viewTotalButton)).perform(click())

       /* intended(allOf(
            hasComponent(hasShortClassName(".TotalActivity")),
            toPackage("com.chaldrac.facebookrecipientapp.views"),
            hasExtra("totalExtra", "115.0")))*/
    }
    @Test
    fun tipTotalAmount20percent(){
        onView(withId(R.id.input_billAmount)).perform(typeText(BILL_AMOUNT), closeSoftKeyboard())

        onView(withText("20% Tip")).perform(click())

        val expertectText = "120.0"
        onView(withId(R.id.totalAmount)).check(matches(withText(expertectText)))
    }

    @Test
    fun tipTotalAmount30percent(){
        onView(withId(R.id.input_billAmount)).perform(typeText(BILL_AMOUNT), closeSoftKeyboard())

        onView(withText("30% Tip")).perform(click())

        val expertectText = "130.0"
        onView(withId(R.id.totalAmount)).check(matches(withText(expertectText)))
    }

    @Test
    fun tipTotalAmount40percent(){
        onView(withId(R.id.input_billAmount)).perform(typeText(BILL_AMOUNT), closeSoftKeyboard())

        onView(withText("40% Tip")).perform(click())

        val expertectText = "140.0"
        onView(withId(R.id.totalAmount)).check(matches(withText(expertectText)))
    }
}