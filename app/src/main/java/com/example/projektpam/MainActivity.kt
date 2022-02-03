package com.example.projektpam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projektpam.fragments.EventsFragment
import com.example.projektpam.fragments.FavouritesFragment
import com.example.projektpam.fragments.MoreFragment
import com.example.projektpam.fragments.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val eventsFragment = EventsFragment()
    private val favouritesFragmentFragment = FavouritesFragment()
    private val notificationsFragment = NotificationsFragment()
    private val moreFragment = MoreFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(eventsFragment)

        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_events -> replaceFragment(eventsFragment)
                R.id.nav_favourites -> replaceFragment(favouritesFragmentFragment)
                R.id.nav_notifications -> replaceFragment(notificationsFragment)
                R.id.nav_more -> replaceFragment(moreFragment)
            }
            true }
    }

    private fun replaceFragment(fragment : Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment).commit()
        }
    }
}