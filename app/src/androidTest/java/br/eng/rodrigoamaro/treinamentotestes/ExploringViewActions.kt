package br.eng.rodrigoamaro.treinamentotestes

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test

class ExploringViewActions {

    @Test
    fun typeText() {
        ActivityScenario.launch(ElementsActivity::class.java)
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.fieldSample)).perform(typeText("Sample"))
        onView(withId(R.id.fieldSample)).perform(clearText())
    }

    @Test
    fun changeText() {
        ActivityScenario.launch(ElementsActivity::class.java)
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withId(R.id.fieldSample)).perform(replaceText("Sample"))
        Thread.sleep(1000)
        onView(withId(R.id.fieldSample)).perform(clearText())
        Thread.sleep(1000)
    }

    @Test
    fun pressBackWithError() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun pressBackNoError() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(isRoot()).perform(pressBackUnconditionally())
    }

    @Test
    fun pressActionButton() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withId(R.id.fieldSample)).perform(typeText("Sample"))
        onView(withId(R.id.fieldSample)).perform(pressImeActionButton())
    }

    @Test
    fun pressKey() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withId(R.id.fieldSample)).perform(typeText("Sample"))
        onView(withId(R.id.fieldSample)).perform(pressKey(KeyEvent.KEYCODE_DEL))
    }

    @Test
    fun performActionUntil() {
        ActivityScenario.launch(ElementsActivity::class.java)
        onView(withId(R.id.fieldSample)).perform(typeText("Sample"))
        Thread.sleep(1000)
        onView(withId(R.id.fieldSample)).perform(repeatedlyUntil(pressKey(KeyEvent.KEYCODE_DEL), withText(""), 7))
        Thread.sleep(1000)
    }
}