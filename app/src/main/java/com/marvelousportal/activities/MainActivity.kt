package com.marvelousportal.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.marvelousportal.R
import com.marvelousportal.base.BaseActivity
import com.marvelousportal.fragments.characters.CharactersFragment
import com.marvelousportal.fragments.comic.ComicsFragment
import com.marvelousportal.fragments.events.EventsFragment
import com.marvelousportal.fragments.series.SeriesFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        /**
         * call this method to launch the Main Activity
         */
        fun launchActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //Default Fragment for home
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_frame, CharactersFragment().newInstance())
        ft.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.action_search -> {
                SearchResultActivity.launchActivity(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displaySelectedScreen(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displaySelectedScreen(itemId: Int) {
        //creating fragment object
        var fragment: Fragment? = null
        //initializing the fragment object which is selected
        when (itemId) {
            R.id.nav_characters -> fragment = CharactersFragment()
            R.id.nav_comics -> fragment = ComicsFragment()
            R.id.nav_series -> fragment = SeriesFragment()
            R.id.nav_events -> fragment = EventsFragment()
        }
        //replacing the fragment
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment)
            ft.commit()
        }
    }
}
