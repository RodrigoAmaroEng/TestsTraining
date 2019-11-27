package br.eng.rodrigoamaro.treinamentotestes

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.doubleClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.After
import org.junit.Test

class EspressoViewInteraction {

    @Test
    fun onViewAndCheckFunction() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withText("Hello World!")).check(matches(isDisplayed()))
    }

    @Test
    fun onViewFailureViewNotExists() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withText("Hello World")).check(matches(isDisplayed()))
        // Se o matcher passado para onView não for encontrado ele falha
    }

    @Test
    fun checkCaptureOnViewError() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withText("Hello World")).check { view, error -> println("TEST_STEP: Houston we have a '${error.message?.firstLine}' on view $view") }
        // Se o matcher passado para onView não for encontrado o erro é capturado e o teste não falha
    }


    @Test
    fun failureHandler() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withText("Hello World"))
            .withFailureHandler { error, viewMatcher -> println("TEST_STEP: Houston we have a '${error.message?.firstLine}' on matcher $viewMatcher") }
            .check(matches(isDisplayed()))
    }

    @Test
    fun multipleFailuresWithoutDefaultHandler() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withText("Hello World"))
            .withFailureHandler { error, viewMatcher -> println("TEST_STEP: Houston we have a '${error.message?.firstLine}' on matcher $viewMatcher") }
            .check(matches(isDisplayed()))
        onView(withText("Hello World")).check(matches(isDisplayed()))
    }

    @Test
    fun multipleFailuresWithDefaultHandler() {
        ActivityScenario.launch(ElementsActivity::class.java)
        Espresso.setFailureHandler { error, viewMatcher -> println("TEST_STEP: Houston we have a '${error.message}' on matcher $viewMatcher") }
        onView(withText("Hello World")).check(matches(isDisplayed()))
        onView(withText("Hello World")).check(matches(isDisplayed()))
    }

    @Test
    fun performAction() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withText("Boring")).perform(click())
    }

    @Test
    fun performMultipleActions() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withText("Boring")).perform(click(), doubleClick())
    }

    @Test
    fun inRootSample() {
        ActivityScenario.launch(BasicActivity::class.java)
        onView(withText("Touch me")).perform(click())
        onView(withText("Hey i'm here!")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
    }

}