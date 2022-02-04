package com.example.projektpam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projektpam.fragments.events.EventsViewFragment
import com.example.projektpam.fragments.favourites.FavouritesFragment
import com.example.projektpam.fragments.more.MoreFragment
import com.example.projektpam.fragments.notifications.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val eventsViewFragment = EventsViewFragment()
    private val favouritesFragmentFragment = FavouritesFragment()
    private val notificationsFragment = NotificationsFragment()
    private val moreFragment = MoreFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(eventsViewFragment)

        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_events -> replaceFragment(eventsViewFragment)
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