package com.example.closetcompanion.activities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import com.example.closetcompanion.R
import com.example.closetcompanion.fragments.FeedFragment
import com.example.closetcompanion.fragments.ProfileFragment
import com.example.closetcompanion.fragments.SettingsFragment
import com.example.closetcompanion.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var user: User? = null
        if(intent.hasExtra("user")){
            user = intent.getParcelableExtra("user", User::class.java)
            switchToNewFragmentWithUser("profile", user)
        }
        val fragContainer = findViewById<FragmentContainerView>(R.id.home_page_fragment_container)
        setContentView(R.layout.activity_home_page)




        findViewById<BottomNavigationView>(R.id.home_page_bottom_nav).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_profile -> {
                    switchToNewFragment(ProfileFragment())
                    true
                }
                R.id.ic_feed -> {
                    switchToNewFragment(FeedFragment())
                    true
                }
                R.id.ic_setting -> {
                    switchToNewFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    fun switchToNewFragmentWithUser(frag: String, user: User?){
//        val bundle = Bundle()
//        bundle.putParcelable("user", user)
//        when(frag){
//            "profile" -> {
//                val profileFragment = ProfileFragment()
//                profileFragment.arguments = bundle
//                val transaction = supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.home_page_fragment_container, profileFragment)
//                transaction.commit()
//            }
//            "feed" -> {
//                val feedFragment = FeedFragment()
//                feedFragment.arguments = bundle
//                val transaction = supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.home_page_fragment_container, feedFragment)
//                transaction.commit()
//            }
//            "settings" -> {
//                val settingsFragment = SettingsFragment()
//                settingsFragment.arguments = bundle
//                val transaction = supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.home_page_fragment_container, settingsFragment)
//                transaction.commit()
//            }
//        }


    }

    fun switchToNewFragment(frag: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.home_page_fragment_container, frag)
            commit()
        }
    }
}