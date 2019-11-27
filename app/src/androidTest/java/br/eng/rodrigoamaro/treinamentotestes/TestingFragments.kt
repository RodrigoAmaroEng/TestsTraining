package br.eng.rodrigoamaro.treinamentotestes

import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.assertEquals
import org.junit.Test

class TestingFragments {
    @Test
    fun checkAnotherFragmentHasOpen() {
        var activity: AppCompatActivity? = null
        ActivityScenario.launch(FragmentsActivity::class.java).onActivity { activity = it }
        onView(withId(R.id.button_goto_b)).perform(click())
        assertEquals(
            activity!!.supportFragmentManager.fragments.last().javaClass, FragmentB::class.java
        )
    }

//    @Test
//    fun checkFragment() {
//        launchFragmentInContainer<FragmentA>()
//        onView(withId(R.id.button_goto_b)).check(matches(isDisplayed()))
//    }

}

