package com.marvelousportal.fragments.events

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.marvelousportal.R
import com.marvelousportal.app.AppController
import com.marvelousportal.base.BaseFragment
import com.marvelousportal.models.Model
import com.marvelousportal.models.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_events.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class EventsFragment : BaseFragment() {
    private var mAdapter: EventsAdapter? = null
    private var eventList: MutableList<Result>? = null
    private var eventSearchList: MutableList<Result>? = null
    private var eventsListViewModel = AppController.injectEventsListViewModel()
    /**
     * This method is used to instantiate the fragment.
     *
     * @return the instance of this fragment.
     */
    fun newInstance(): EventsFragment {
        return EventsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupRecyclerView()
        search_events.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchEvent(query)
                search_events.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun init() {
        eventList = ArrayList()
        eventSearchList = ArrayList()
        mAdapter = EventsAdapter(mContext, eventList!!)
        search_events.clearFocus()
        iv_search_events.setOnClickListener {
            if (eventList?.size!! > 0) {
                mAdapter?.setUserList(eventList)
                events_view_flipper.displayedChild = 1
            } else {
                fetchEventsList()
            }
        }
    }

    private fun setupRecyclerView() {
        rv_events.layoutManager = GridLayoutManager(mContext, 2)
        rv_events.adapter = mAdapter
        fetchEventsList()
    }

    private fun fetchEventsList() {
        events_view_flipper.displayedChild = 0
        subscribe(eventsListViewModel.getEvents()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            Log.d("Success", "Received UIModel with ${it.data?.count} characters.")
            showEvents(it)
        }, {
            Log.w("throws", it.localizedMessage)
            events_view_flipper.displayedChild = 2
        })!!)
    }

    private fun showEvents(it: Model) {
        if (it.status?.contains("Ok")!!) {
            if (it.data!!.results.isNotEmpty()) {
                events_view_flipper.displayedChild = 1
                eventList?.clear()
                eventList?.addAll(it.data.results)
                mAdapter?.setUserList(eventList)
            } else {
                events_view_flipper.displayedChild = 2
            }
        } else {
            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchEvent(query: String) {
        events_view_flipper.displayedChild = 0
        subscribe(eventsListViewModel.getSearchedEvents(query)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            Log.d("Success", "Received UIModel with ${it.data?.count} characters.")
            showSearchedEvents(it)
        }, {
            Log.w("throws", it.localizedMessage)
            events_view_flipper.displayedChild = 2
        })!!)
    }

    private fun showSearchedEvents(it: Model) {
        if (it.status?.contains("Ok")!!) {
            if (it.data!!.results.isNotEmpty()) {
                events_view_flipper.displayedChild = 1
                eventSearchList?.clear()
                eventSearchList?.addAll(it.data.results)
                mAdapter?.setUserList(eventSearchList)
            } else {
                events_view_flipper.displayedChild = 2
            }
        } else {
            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
        }
    }
}