package com.marvelousportal.app

import android.app.Application
import com.marvelousportal.network.ApiFactory
import com.marvelousportal.repository.MarvelRepository
import com.marvelousportal.viewmodels.*

class AppController : Application() {

    companion object {
        private lateinit var sMarvelRepository: MarvelRepository
        private lateinit var charactersViewModel: CharactersViewModel
        private lateinit var comicsViewModel: ComicsViewModel
        private lateinit var seriesViewModel: SeriesViewModel
        private lateinit var eventsViewModel: EventsViewModel
        private lateinit var detailViewModel: DetailViewModel
        fun injectCharacterListViewModel() = charactersViewModel
        fun injectComicsListViewModel() = comicsViewModel
        fun injectSeriesListViewModel() = seriesViewModel
        fun injectEventsListViewModel() = eventsViewModel
        fun injectDetailViewModel() = detailViewModel
    }

    override fun onCreate() {
        super.onCreate()
        sMarvelRepository = MarvelRepository(ApiFactory.create(), applicationContext)
        charactersViewModel = CharactersViewModel(sMarvelRepository)
        comicsViewModel = ComicsViewModel(sMarvelRepository)
        eventsViewModel = EventsViewModel(sMarvelRepository)
        seriesViewModel = SeriesViewModel(sMarvelRepository)
        detailViewModel = DetailViewModel(sMarvelRepository)
    }
}