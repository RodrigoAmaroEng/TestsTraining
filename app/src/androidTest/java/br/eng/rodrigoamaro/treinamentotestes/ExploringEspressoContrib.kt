package br.eng.rodrigoamaro.treinamentotestes

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.ViewPagerActions.clickBetweenTwoTitles
import androidx.test.espresso.contrib.ViewPagerActions.scrollLeft
import androidx.test.espresso.contrib.ViewPagerActions.scrollRight
import androidx.test.espresso.contrib.ViewPagerActions.scrollToFirst
import androidx.test.espresso.contrib.ViewPagerActions.scrollToLast
import androidx.test.espresso.contrib.ViewPagerActions.scrollToPage
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.viewpager.widget.PagerTitleStrip
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Test

class ExploringEspressoContrib {

    @Test
    fun titleClickPagerAction() {
        ActivityScenario.launch(ViewPagerActivity::class.java)
        onView(pagerHeaderFor(R.id.my_view_pager)).perform(clickBetweenTwoTitles("Red", "Green"))
    }

    @Test
    fun scrollLeftAndRightPagerAction() {
        ActivityScenario.launch(ViewPagerActivity::class.java)
        onView(withId(R.id.my_view_pager)).perform(scrollRight(true))
        onView(withId(R.id.my_view_pager)).perform(scrollLeft(true))
    }

    @Test
    fun scrollLastAndFirstPagerAction() {
        ActivityScenario.launch(ViewPagerActivity::class.java)
        onView(withId(R.id.my_view_pager)).perform(scrollToLast(true))
        onView(withId(R.id.my_view_pager)).perform(scrollToFirst(true))
    }

    @Test
    fun scrollToPageAction() {
        ActivityScenario.launch(ViewPagerActivity::class.java)
        onView(withId(R.id.my_view_pager)).perform(scrollToPage(3, true))
    }

}

fun pagerHeaderFor(pagerId: Int) = allOf(withParent(withId(pagerId)), instanceOf(PagerTitleStrip::class.java))