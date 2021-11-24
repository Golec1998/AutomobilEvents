package com.example.projektpam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val eventsFragment = EventsFragment()
    private val favouritesFragmentFragment = FavouritesFragment()
    private val notificationsFragment = NotificationsFragment()
    private val moreFragment = MoreFragment()

    //var eventsLayoutManager : RecyclerView.LayoutManager? = null
    //var adapter : RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder>? = null

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

        //val eventsRecyclerView = findViewById<RecyclerView>(R.id.eventsList)
        //eventsLayoutManager = LinearLayoutManager(this)

        // Ponizej sie wywala i nie mam pojecia dlaczego

        //eventsRecyclerView.layoutManager = eventsLayoutManager
        //adapter = EventsRecyclerAdapter()
        //eventsRecyclerView.adapter = adapter
    }

    private fun replaceFragment(fragment : Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment).commit()
        }
    }
}