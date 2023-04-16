package com.example.closetcompanion.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.closetcompanion.R
import com.example.closetcompanion.fragments.FeedFragment
import com.example.closetcompanion.fragments.ProfileFragment
import com.example.closetcompanion.fragments.SettingsFragement
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        findViewById<BottomNavigationView>(R.id.home_page_bottom_nav).setOnItemSelectedListener { item ->
            when(item.itemId){
                R.drawable.baseline_person_24 -> {
                    switchToNewFragment(ProfileFragment())
                    true
                }
                R.drawable.baseline_feed_24 -> {
                    switchToNewFragment(FeedFragment())
                    true
                }
                R.drawable.baseline_settings_24 -> {
                    switchToNewFragment(SettingsFragement())
                    true
                }
                else -> false
            }
        }
    }

    fun switchToNewFragment(frag: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.landing_fragment_container, frag)
            commit()
        }
    }
}