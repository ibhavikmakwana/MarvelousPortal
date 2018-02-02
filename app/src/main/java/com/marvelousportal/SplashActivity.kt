package com.marvelousportal

import android.os.Bundle
import android.os.Handler
import com.marvelousportal.activities.MainActivity
import com.marvelousportal.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            MainActivity.launchActivity(this)
            finish()
        }, 1500)
    }
}