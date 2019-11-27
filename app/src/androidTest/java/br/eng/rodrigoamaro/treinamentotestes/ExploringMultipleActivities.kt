package br.eng.rodrigoamaro.treinamentotestes

import androidx.drawerlayout.widget.DrawerLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test


class ExploringMultipleActivities {

    @get:Rule
    val rule = IntentsTestRule(ComponentsActivity::class.java)

    @Test
    fun checkAnotherActivityHasOpen() {
        ActivityScenario.launch(ComponentsActivity::class.java)
        onView(instanceOf(DrawerLayout::class.java)).perform(open())
        onView(withId(R.id.navView)).perform(navigateTo(R.id.nav_item_three))
        intended(hasComponent(ApiActivity::class.qualifiedName))
    }

}