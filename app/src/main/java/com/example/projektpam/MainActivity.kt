package com.example.projektpam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projektpam.fragments.events.EventsViewFragment
import com.example.projektpam.fragments.favourites.FavouritesViewFragment
import com.example.projektpam.fragments.more.MoreFragment
import com.example.projektpam.fragments.notifications.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val eventsViewFragment = EventsViewFragment()
    private val favouritesViewFragment = FavouritesViewFragment()
    private val notificationsFragment = NotificationsFragment()
    private val moreFragment = MoreFragment()
    private var mainViewSelected : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(1500)
        setTheme(R.style.Theme_ProjektPAM)

        setContentView(R.layout.activity_main)

        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_navigation)

        replaceFragment(eventsViewFragment)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_events -> { replaceFragment(eventsViewFragment); mainViewSelected = true }
                R.id.nav_favourites -> { replaceFragment(favouritesViewFragment); mainViewSelected = false }
                R.id.nav_notifications -> { replaceFragment(notificationsFragment); mainViewSelected = false }
                R.id.nav_more -> { replaceFragment(moreFragment); mainViewSelected = false }
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