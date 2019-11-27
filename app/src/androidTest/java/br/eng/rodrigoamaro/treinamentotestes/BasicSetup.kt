package br.eng.rodrigoamaro.treinamentotestes


import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class EspressoBasic {
    @Test
    fun startApplication() {
        val myApp = InstrumentationRegistry.getInstrumentation().targetContext.packageName
        println("TEST_STEP: Package is $myApp")
    }
}

class EspressoActivityWithRuleAutoStart {

    @get:Rule
    val activityRule = ActivityTestRule(BasicActivity::class.java)

    @Test
    fun startActivity() {
        println("TEST_STEP: Test started")
    }
}

class EspressoActivityWithRuleManualStart {

    @get:Rule
    val activityRule = ActivityTestRule(BasicActivity::class.java, true, false)

    @Test
    fun startActivity() {
        println("TEST_STEP: Test started")
        activityRule.launchActivity(Intent())
    }

    @Test
    fun startActivityWithParameters() {
        println("TEST_STEP: Test started")
        val intent = Intent()
        intent.putExtra("SOME_PARAM", "Testing")
        activityRule.launchActivity(intent)
        Thread.sleep(2000)
    }
}


class EspressoActivityWithScenario {

    @Test
    fun startActivity() {
        println("TEST_STEP: Test started")
        ActivityScenario.launch(BasicActivity::class.java)
    }

    @Test
    fun startActivityWithParameters() {
        println("TEST_STEP: Test started")
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, BasicActivity::class.java)
        intent.putExtra("SOME_PARAM", "Testing")
        ActivityScenario.launch<BasicActivity>(intent)
    }
}


class EspressoFirstAttempt {

    @Test
    fun touchButtonAndCheckAnswer() {
        ActivityScenario.launch(BasicActivity::class.java)
        onView(withText("Touch me")).perform(click())
        onView(withText("Hey i'm here!")).inRoot(isDialog()).check(matches(isDisplayed()))
    }

}