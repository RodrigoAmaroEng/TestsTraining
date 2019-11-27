package br.eng.rodrigoamaro.treinamentotestes

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasFocus
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Test

class ExploringMatchersAndViewAssertions {

    /**
     * ASSERTIONS
     */

    @Test
    fun matchesAssertion() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withText("Hello World!")).check(matches(isDisplayed()))
    }

    @Test
    fun doesNotExistAssertion() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withText("Hello World")).check(doesNotExist())
    }

    @Test
    fun selectedDescendantsMatchAssertion() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(isRoot()).check(selectedDescendantsMatch(withText("Hello World!"), isDisplayed()))
    }

    /**
     * MATCHERS
     */

    @Test
    fun someIdentifiers() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withId(R.id.textCounter)).check(matches(isDisplayed()))
        onView(withText("Hello World!")).check(matches(isDisplayed()))
        onView(withHint("Touch")).check(matches(isDisplayed()))
        onView(withContentDescription("Touch me to count")).check(matches(isDisplayed()))
    }

    @Test
    fun someStates() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withId(R.id.textCounter)).check(matches(withText("Hello World!")))
        onView(withId(R.id.textCounter)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonBoring)).check(matches(isClickable()))
        onView(withId(R.id.buttonBoring)).check(matches(hasFocus()))
    }

    // https://android.github.io/android-test/downloads/espresso-cheat-sheet-2.1.0.pdf

    @Test
    fun groupingMatchers() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withId(R.id.textCounter)).check(matches(allOf(withText("Hello World!"), isDisplayed())))
        onView(allOf(withId(R.id.buttonBoring), withText("Boring"))).check(matches(isClickable()))
        onView(withId(R.id.textCounter)).check(matches(anyOf(isDisplayed(), isClickable())))
        onView(withId(R.id.textCounter)).check(matches(not(hasFocus())))
    }

    @Test
    fun hierarchy() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(isRoot()).check(matches(hasDescendant(withText("Child View"))))
        onView(allOf(withParent(instanceOf(LinearLayout::class.java)), instanceOf(TextView::class.java)))
                .check(matches(withText("Child View")))
        onView(withId(R.id.buttonBoring)).check(matches(hasSibling(withChild(withText("Child View")))))
    }

    @Test
    fun customMatchers() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withTextInsideBox()).check(matches(withText("Child View")))
        onView(withTextInsideBox()).check(matches(withTextLength(10)))
    }

}

fun withTextInsideBox(): Matcher<View> =
        allOf(withParent(instanceOf(LinearLayout::class.java)), instanceOf(TextView::class.java))

fun withTextLength(length: Int) = WithTextLengthMatcher(length)

class WithTextLengthMatcher(private val length: Int) : BaseMatcher<View>() {

    override fun describeTo(description: Description?) {
        description?.appendText(" With text length is $length ")
    }

    override fun matches(item: Any?): Boolean {
        val view = item as? TextView ?: return false
        return view.text.length == length
    }
}