package com.example.taligado.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.taligado.R
import com.example.taligado.fragments.FiliaisFragment
import com.example.taligado.fragments.DispositivosFragment
import com.example.taligado.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Esconde a ActionBar
        supportActionBar?.hide()

        // Configura o BottomNavigationView
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Remove qualquer fragmento e exibe o layout da DashboardActivity
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
