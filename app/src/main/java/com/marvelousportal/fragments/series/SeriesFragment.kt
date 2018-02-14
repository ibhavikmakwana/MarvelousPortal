package com.marvelousportal.fragments.series

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
import kotlinx.android.synthetic.main.fragment_series.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SeriesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class SeriesFragment : BaseFragment() {
    private var mAdapter: SeriesAdapter? = null
    private var seriesList: MutableList<Result>? = null
    private var seriesSearchList: MutableList<Result>? = null
    private var seriesListViewModel = AppController.injectSeriesListViewModel()

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
        seriesList = ArrayList()
        seriesSearchList = ArrayList()
        mAdapter = SeriesAdapter(mContext, seriesList!!)
        search_series.clearFocus()
        iv_search_series.setOnClickListener {
            if (seriesList?.size!! > 0) {
                mAdapter?.setUserList(seriesList)
                series_view_flipper.displayedChild = 1
            } else {
                fetchSeriesList()
            }
        }
    }

    private fun setupRecyclerView() {
        rv_series.layoutManager = GridLayoutManager(mContext, 2)
        rv_series.adapter = mAdapter
        fetchSeriesList()
    }

    private fun fetchSeriesList() {
        series_view_flipper.displayedChild = 0
        subscribe(seriesListViewModel.getSeries()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            Log.d("Success", "Received UIModel with ${it.data?.count} characters.")
            showSeries(it)
        }, {
            Log.w("throws", it.localizedMessage)
            series_view_flipper.displayedChild = 2
        })!!)
    }

    private fun showSeries(it: Model) {
        if (it.status?.contains("Ok")!!) {
            if (it.data!!.results.isNotEmpty()) {
                seriesList?.clear()
                seriesList?.addAll(it.data.results)
                mAdapter?.setUserList(seriesList)
                series_view_flipper.displayedChild = 1
            } else {
                series_view_flipper.displayedChild = 2
            }
        } else {
            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchSeries(query: String) {
        series_view_flipper.displayedChild = 0
        subscribe(seriesListViewModel.getSearchedSeries(query)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            Log.d("Success", "Received UIModel with ${it.data?.count} characters.")
            showSearchedSeries(it)
        }, {
            Log.w("throws", it.localizedMessage)
            series_view_flipper.displayedChild = 2
        })!!)
    }

    private fun showSearchedSeries(it: Model) {
        if (it.status?.contains("Ok")!!) {
            if (it.data!!.results.isNotEmpty()) {
                seriesSearchList?.clear()
                seriesSearchList?.addAll(it.data.results)
                mAdapter?.setUserList(seriesSearchList)
                series_view_flipper.displayedChild = 1
            } else {
                series_view_flipper.displayedChild = 2
            }
        } else {
            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
        }
    }
}
