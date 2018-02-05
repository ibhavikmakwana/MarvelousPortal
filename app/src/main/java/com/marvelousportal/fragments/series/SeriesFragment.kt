package com.marvelousportal.fragments.series

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
import kotlinx.android.synthetic.main.fragment_series.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SeriesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class SeriesFragment : BaseFragment() {
    private var mAdapter: SeriesAdapter? = null
    private var seriesList: MutableList<Result>? = null
    /**
     * This method is used to instantiate the fragment.
     *
     * @return the instance of this fragment.
     */
    fun newInstance(): SeriesFragment {
        return SeriesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupRecyclerView()
        search_series.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchSeries(query)
                search_series.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun init() {
        /*charactersViewModel = CharactersViewModel(mContext)*/
        seriesList = ArrayList()
        mAdapter = SeriesAdapter(mContext, seriesList!!)
        search_series.clearFocus()
    }

    private fun setupRecyclerView() {
        rv_series.layoutManager = GridLayoutManager(mContext, 2)
        rv_series.adapter = mAdapter
        fetchSeriesList()
    }

    private fun fetchSeriesList() {
        series_view_flipper.displayedChild = 0
        val appController = AppController.create(mContext)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.fetchSeries(timeStamp, Constant.PUBLIC_KEY, getHash(timeStamp))?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_series_attribution_html.text = Html.fromHtml(userResponse.attributionHTML, Html.FROM_HTML_MODE_LEGACY)
            } else {
                tv_series_attribution_html.text = Html.fromHtml(userResponse.attributionHTML)
            }
            seriesList?.clear()
            seriesList?.addAll(userResponse.data.results)
            mAdapter?.setUserList(seriesList)
            series_view_flipper.displayedChild = 1
        }, {
            series_view_flipper.displayedChild = 1
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
    }

    private fun searchSeries(query: String) {
        series_view_flipper.displayedChild = 0
        val appController = AppController.create(mContext)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.searchSeries(timeStamp, Constant.PUBLIC_KEY, getHash(timeStamp),query)?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_series_attribution_html.text = Html.fromHtml(userResponse.attributionHTML, Html.FROM_HTML_MODE_LEGACY)
            } else {
                tv_series_attribution_html.text = Html.fromHtml(userResponse.attributionHTML)
            }
            seriesList?.clear()
            seriesList?.addAll(userResponse.data.results)
            mAdapter?.setUserList(seriesList)
            series_view_flipper.displayedChild = 1
        }, {
            series_view_flipper.displayedChild = 1
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
    }
}
