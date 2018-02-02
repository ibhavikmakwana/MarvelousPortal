package com.marvelousportal.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.marvelousportal.R
import com.marvelousportal.base.BaseActivity


class SearchResultActivity : BaseActivity() {

    companion object {
        /**
         * call this method to launch the Main Activity
         */
        fun launchActivity(context: Context) {
            val intent = Intent(context, SearchResultActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
