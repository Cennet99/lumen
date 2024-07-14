package com.cennetnadir.lumen.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cennetnadir.lumen.R
import com.cennetnadir.lumen.databinding.ActivityMainBinding
import com.cennetnadir.lumen.feature.add.AddFragment
import com.cennetnadir.lumen.feature.browse.BrowseFragment
import com.cennetnadir.lumen.feature.home.HomeFragment
import com.cennetnadir.lumen.feature.library.LibraryFragment
import com.cennetnadir.lumen.feature.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the default fragment
        loadFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> loadFragment(HomeFragment())
                R.id.navigation_browse -> loadFragment(BrowseFragment())
                R.id.navigation_add -> loadFragment(AddFragment())
                R.id.navigation_library -> loadFragment(LibraryFragment())
                R.id.navigation_settings -> loadFragment(SettingsFragment())
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        return true
    }
}
