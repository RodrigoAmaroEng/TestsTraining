package br.eng.rodrigoamaro.treinamentotestes

import android.app.Activity
import android.app.Instrumentation
import android.content.ComponentName
import android.content.Intent
import android.provider.MediaStore
import androidx.drawerlayout.widget.DrawerLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test


class ExploringInteractions {

    @get:Rule
    val rule = IntentsTestRule(ComponentsActivity::class.java)

    @Test
    fun intentTestsOK() {
        ActivityScenario.launch(ComponentsActivity::class.java)
        val resultOk = Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(resultOk)
        onView(instanceOf(DrawerLayout::class.java)).perform(open())
        onView(withId(R.id.navView)).perform(navigateTo(R.id.nav_item_two))
    }

    @Test
    fun intentTestsCancel() {
        ActivityScenario.launch(ComponentsActivity::class.java)
        val result = Instrumentation.ActivityResult(Activity.RESULT_CANCELED, Intent())
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result)
        onView(instanceOf(DrawerLayout::class.java)).perform(open())
        onView(withId(R.id.navView)).perform(navigateTo(R.id.nav_item_two))
    }

    @Test
    fun intentTestsCheck() {
        ActivityScenario.launch(ComponentsActivity::class.java)
        onView(instanceOf(DrawerLayout::class.java)).perform(open())
        onView(withId(R.id.navView)).perform(navigateTo(R.id.nav_item_one))
        intended(hasComponent(ComponentName("com.android.settings",
                "com.android.settings.bluetooth.BluetoothSettings")))
    }
}