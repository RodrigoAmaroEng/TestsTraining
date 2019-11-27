package br.eng.rodrigoamaro.treinamentotestes

import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Ignore
import org.junit.Test
import java.lang.Thread.sleep

class CommonProblems {

    @Test
    @Ignore
    fun toast() {
        ActivityScenario.launch(ElementsActivity::class.java).onActivity {
            println(it.baseContext.packageManager.getLaunchIntentForPackage(it.baseContext.packageName)?.component?.className)
        }
        onView(withText("Hello!")).inRoot(RootMatchers.isSystemAlertWindow())
            .check(matches(isDisplayed()))
    }

    @Test
    @Ignore
    fun keyboard() {
        ActivityScenario.launch(KeyBoardActivity::class.java)
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.field_name)).perform(typeText("Somebody"))
//        onView(withId(R.id.field_name)).perform(replaceText("Somebody"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button_hi)).perform(click())
        onView(withText("HI Somebody!")).check(matches(isDisplayed()))
    }


    @Test
    fun multipleMatches1() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(instanceOf(TextView::class.java)).check(matches(isDisplayed()))
    }

    @Test
    @Ignore
    fun multipleMatches2() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(instanceOf(AppCompatTextView::class.java)).check(matches(isDisplayed()))
    }

    @Test
    fun multipleMatchesSolution() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(
            allOf(
                instanceOf(AppCompatTextView::class.java),
                withParent(instanceOf(ConstraintLayout::class.java))
            )
        )
            .check(matches(isDisplayed()))
    }




}
