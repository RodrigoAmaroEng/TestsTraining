package br.eng.rodrigoamaro.treinamentotestes

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager


class ViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        findViewById<ViewPager>(R.id.my_view_pager).adapter = MyAdapter(this)
    }

}

class MyAdapter(private val context: Context) : PagerAdapter() {
    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val customPagerEnum = CustomPagerEnum.values()[position]
        val layout = FrameLayout(context)
        layout.background = ColorDrawable().apply { color = customPagerEnum.color }
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return CustomPagerEnum.values()[position].pageName
    }

    override fun getCount(): Int = CustomPagerEnum.values().size
}

enum class CustomPagerEnum constructor(val pageName: String, val color: Int) {

    RED("Red", Color.RED),
    GREEN("Green", Color.GREEN),
    BLUE("Blue", Color.BLUE)

}