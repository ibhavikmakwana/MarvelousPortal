package com.marvelousportal.app

import android.app.Application
import android.content.Context
import com.marvelousportal.network.APIService

import com.marvelousportal.network.ApiFactory

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class AppController : Application() {

    var apiService: APIService? = null
        get() {
            if (field == null) {
                apiService = ApiFactory.create()
            }

            return field
        }
    private var scheduler: Scheduler? = null

    fun subscribeScheduler(): Scheduler {
        if (scheduler == null) {
            scheduler = Schedulers.io()
        }

        return scheduler as Scheduler
    }

    fun setScheduler(scheduler: Scheduler) {
        this.scheduler = scheduler
    }

    companion object {

        private operator fun get(context: Context): AppController {
            return context.applicationContext as AppController
        }

        fun create(context: Context): AppController {
            return AppController[context]
        }
    }

}