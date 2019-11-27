package br.eng.rodrigoamaro.treinamentotestes

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView


class ComponentsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_components)
        setUp()
    }

    private fun setUp() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView = findViewById(R.id.navView)
        navigationView.setNavigationItemSelectedListener(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter2(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> openSettings()
            R.id.nav_item_two -> requestPhoto()
            R.id.nav_item_three -> {
                Toast.makeText(this, "Menu 3", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ApiActivity::class.java))
            }
            R.id.nav_item_four ->
                Toast.makeText(this, "Menu 4", Toast.LENGTH_SHORT).show()
            R.id.nav_item_five -> showDateDialog()
            else ->
                Toast.makeText(this, "Menu Default", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openSettings() {
        val intent = Intent().apply {
            component = ComponentName(
                "com.android.settings",
                "com.android.settings.bluetooth.BluetoothSettings"
            )
        }
        startActivity(intent)
    }

    private fun requestPhoto() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(
            this@ComponentsActivity,
            "Result was ${resultCode == Activity.RESULT_OK}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showDateDialog() {
        DatePickerDialog(this).apply {
            setOnDateSetListener { datePicker, _, _, _ ->
                Toast.makeText(
                    this@ComponentsActivity,
                    "Year ${datePicker.year}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }.show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

class MyAdapter2(private val context: Context) : RecyclerView.Adapter<MyAdapter2.MyHolder>() {

    val data = (0..15).asSequence().associateWith { false }.toMutableMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_checked, null, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(data.entries.toList()[position])
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val label: CheckedTextView = view.findViewById(android.R.id.text1)

        fun bind(entry: MutableMap.MutableEntry<Int, Boolean>) {
            label.text = "Item ${entry.key}"
            label.isChecked = entry.value
            label.setOnClickListener {
                data[entry.key] = !entry.value
                notifyItemChanged(entry.key)
            }
        }
    }

}
