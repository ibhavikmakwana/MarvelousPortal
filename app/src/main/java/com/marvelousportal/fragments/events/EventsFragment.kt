package com.marvelousportal.fragments.events

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.marvelousportal.R
import com.marvelousportal.app.AppController
import com.marvelousportal.base.BaseFragment
import com.marvelousportal.models.Result
import com.marvelousportal.utils.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_events.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class EventsFragment : BaseFragment() {
    private var mAdapter: EventsAdapter? = null
    private var eventList: MutableList<Result>? = null
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
        /*charactersViewModel = CharactersViewModel(mContext)*/
        eventList = ArrayList()
        mAdapter = EventsAdapter(mContext, eventList!!)
        search_events.clearFocus()
    }

    private fun setupRecyclerView() {
        rv_events.layoutManager = GridLayoutManager(mContext, 2)
        rv_events.adapter = mAdapter
        fetchEventsList()
    }

    private fun fetchEventsList() {
        events_view_flipper.displayedChild = 0
        val appController = AppController.create(mContext)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.fetchEvents(timeStamp, Constant.PUBLIC_KEY, getHash(timeStamp))?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_events_attribution_html.text = Html.fromHtml(userResponse.attributionHTML, Html.FROM_HTML_MODE_LEGACY)
            } else {
                tv_events_attribution_html.text = Html.fromHtml(userResponse.attributionHTML)
            }
            eventList?.clear()
            eventList?.addAll(userResponse.data.results)
            mAdapter?.setUserList(eventList)
            events_view_flipper.displayedChild = 1
        }, {
            events_view_flipper.displayedChild = 1
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
    }

    private fun searchEvent(query: String) {
        events_view_flipper.displayedChild = 0
        val appController = AppController.create(mContext)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.searchEvents(timeStamp, Constant.PUBLIC_KEY, getHash(timeStamp),query)?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_events_attribution_html.text = Html.fromHtml(userResponse.attributionHTML, Html.FROM_HTML_MODE_LEGACY)
            } else {
                tv_events_attribution_html.text = Html.fromHtml(userResponse.attributionHTML)
            }
            eventList?.clear()
            eventList?.addAll(userResponse.data.results)
            mAdapter?.setUserList(eventList)
            events_view_flipper.displayedChild = 1
        }, {
            events_view_flipper.displayedChild = 1
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
    }
}