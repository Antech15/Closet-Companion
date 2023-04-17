package com.example.closetcompanion.activities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.closetcompanion.R
import com.example.closetcompanion.fragments.FeedFragment
import com.example.closetcompanion.fragments.ProfileFragment
import com.example.closetcompanion.fragments.SettingsFragement
import com.example.closetcompanion.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val user = intent.getSerializableExtra("user", User::class.java)
        findViewById<BottomNavigationView>(R.id.home_page_bottom_nav).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.drawable.baseline_person_24 -> {
                    switchToNewFragment(ProfileFragment(), user)
                    true
                }
                R.drawable.baseline_feed_24 -> {
                    switchToNewFragment(FeedFragment(), user)
                    true
                }
                R.drawable.baseline_settings_24 -> {
                    switchToNewFragment(SettingsFragement(), user)
                    true
                }
                else -> false
            }
        }
    }

    fun switchToNewFragment(frag: Fragment, user: User?){
        supportFragmentManager.beginTransaction().apply {
            frag.requireArguments().putSerializable("user", user)
            replace(R.id.landing_fragment_container, frag)
            commit()
        }
    }
}