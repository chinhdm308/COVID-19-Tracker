package com.d.covid_19tracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.d.covid_19tracker.R
import com.d.covid_19tracker.fragments.CountryFragment
import com.d.covid_19tracker.fragments.HomeFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity(), ChipNavigationBar.OnItemSelectedListener {

    private var navigationBar: ChipNavigationBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapping()

        navigationBar?.setOnItemSelectedListener(this)

        if (savedInstanceState == null) {
            navigationBar?.setItemSelected(R.id.home, true)
        }
    }

    override fun onItemSelected(id: Int) {
        if (id == R.id.home) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
        }

        if (id == R.id.countries) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CountryFragment()).commit()
        }
    }

    private fun mapping() {
        navigationBar = findViewById(R.id.navigation_bar)
    }
}
