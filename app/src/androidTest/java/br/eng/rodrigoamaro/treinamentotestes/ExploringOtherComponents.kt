package br.eng.rodrigoamaro.treinamentotestes

import android.view.View
import android.widget.CheckedTextView
import android.widget.DatePicker
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions.close
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.contrib.PickerActions.setDate
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Test


class ExploringOtherComponents {

    @Test
    fun drawerMatchersAndActions() {
        ActivityScenario.launch(ComponentsActivity::class.java)
        onView(instanceOf(DrawerLayout::class.java)).check(matches(isClosed()))
        onView(instanceOf(DrawerLayout::class.java)).perform(open())
        onView(instanceOf(DrawerLayout::class.java)).check(matches(isOpen()))
        onView(instanceOf(DrawerLayout::class.java)).perform(close())
    }

    @Test
    fun recyclerViewActions() {
        ActivityScenario.launch(ComponentsActivity::class.java)
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition<MyAdapter2.MyHolder>(5, click()))
        onView(withId(R.id.recyclerView)).check(
            matches(hasDescendant(allOf(withText("Item 5"), isChecked())))
        )
        onView(withId(R.id.recyclerView)).perform(actionOnItem<MyAdapter2.MyHolder>(withText("Item 8"), click()))
        onView(withId(R.id.recyclerView)).check(
            matches(hasDescendant(allOf(withText("Item 8"), isChecked())))
        )
        onView(withId(R.id.recyclerView)).perform(
            actionOnHolderItem<RecyclerView.ViewHolder>(
                withInfo("Item 10"),
                click()
            )
        )
        onView(withId(R.id.recyclerView)).check(
            matches(hasDescendant(allOf(withText("Item 10"), isChecked())))
        )
    }

    @Test
    fun recyclerViewScrolling() {
        ActivityScenario.launch(ComponentsActivity::class.java)
        onView(withId(R.id.recyclerView)).perform(scrollToHolder<RecyclerView.ViewHolder>(withInfo("Item 15")))
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<MyAdapter2.MyHolder>(1))
        onView(withId(R.id.recyclerView)).perform(scrollTo<MyAdapter2.MyHolder>(withText("Item 15")))
    }

    @Test
    fun recyclerViewChecking() {
        ActivityScenario.launch(ComponentsActivity::class.java)
        onView(withId(R.id.recyclerView)).check(matches(atPosition(5, withText("Item 5"))))
    }

    @Test
    fun navigationView() {
        ActivityScenario.launch(ComponentsActivity::class.java)
        onView(instanceOf(DrawerLayout::class.java)).perform(open())
        onView(withId(R.id.navView)).perform(navigateTo(R.id.nav_item_four))
    }

    @Test
    fun pickerActions() {
        ActivityScenario.launch(ComponentsActivity::class.java)
        onView(instanceOf(DrawerLayout::class.java)).perform(open())
        onView(withId(R.id.navView)).perform(navigateTo(R.id.nav_item_five))
        onView(instanceOf(DatePicker::class.java)).perform(setDate(1983, 3, 6))
        onView(withId(android.R.id.button1)).perform(click())
    }

}


fun withInfo(info: String) = HolderInfoMatcher(info)

class HolderInfoMatcher(private val info: String) :
    BoundedMatcher<RecyclerView.ViewHolder, MyAdapter2.MyHolder>(MyAdapter2.MyHolder::class.java) {
    override fun describeTo(description: Description?) {
        description?.appendText("view holder with info: $info")
    }

    override fun matchesSafely(item: MyAdapter2.MyHolder?): Boolean {
        val view = item?.itemView?.findViewById(android.R.id.text1) as? CheckedTextView
        return view?.text == info
    }
}

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView?): Boolean {
            val viewHolder = view?.findViewHolderForAdapterPosition(position) ?: return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}