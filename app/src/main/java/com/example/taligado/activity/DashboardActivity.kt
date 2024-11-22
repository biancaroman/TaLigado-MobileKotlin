package com.example.taligado.activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ListView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.taligado.R
import com.example.taligado.fragments.FiliaisFragment
import com.example.taligado.fragments.DispositivosFragment
import com.example.taligado.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        supportActionBar?.hide()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                    findViewById<ScrollView>(R.id.dashboard_content).visibility = View.VISIBLE
                    findViewById<FrameLayout>(R.id.fragment_container).visibility = View.GONE
                }
                R.id.nav_filiais -> {
                    loadFragment(FiliaisFragment())
                }
                R.id.nav_dispositivos -> {
                    loadFragment(DispositivosFragment())
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                }
            }
            true
        }

        drawerLayout = findViewById(R.id.drawer_layout)
        drawerList = findViewById(R.id.drawer_list)

        val menuItems = arrayOf("Home", "Filiais", "Dispositivos", "Perfil")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menuItems)
        drawerList.adapter = adapter

        drawerList.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
                1 -> loadFragment(FiliaisFragment())
                2 -> loadFragment(DispositivosFragment())
                3 -> loadFragment(ProfileFragment())
            }
            drawerLayout.closeDrawers()
        }

        findViewById<ImageView>(R.id.menu_icon).setOnClickListener {
            drawerLayout.openDrawer(drawerList)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        findViewById<FrameLayout>(R.id.fragment_container).visibility = View.VISIBLE
        findViewById<ScrollView>(R.id.dashboard_content).visibility = View.GONE

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
