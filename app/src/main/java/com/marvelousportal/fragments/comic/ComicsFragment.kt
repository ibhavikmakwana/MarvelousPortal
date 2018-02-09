package com.marvelousportal.fragments.comic

import android.os.Bundle
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
import com.marvelousportal.fragments.characters.CharactersFragment
import com.marvelousportal.models.Model
import com.marvelousportal.models.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_comics.*

class ComicsFragment : BaseFragment() {

    private var mAdapter: ComicsAdapter? = null
    private var comicsList: MutableList<Result>? = null
    private var comicsSearchList: MutableList<Result>? = null
    private val comicsListViewModel = AppController.injectComicsListViewModel()
    /**
     * This method is used to instantiate the fragment.
     *
     * @return the instance of this fragment.
     */
    fun newInstance(): CharactersFragment {
        return CharactersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupRecyclerView()
        search_comics.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchComic(query)
                search_comics.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun init() {
        comicsList = ArrayList()
        comicsSearchList = ArrayList()
        mAdapter = ComicsAdapter(mContext, comicsList!!)
        search_comics.clearFocus()
        iv_search_comics.setOnClickListener {
            if (comicsList?.size!! > 0) {
                mAdapter?.setUserList(comicsList)
                comics_view_flipper.displayedChild = 1
            } else {
                fetchComicList()
            }
        }
    }

    private fun setupRecyclerView() {
        rv_comics.layoutManager = GridLayoutManager(mContext, 2)
        rv_comics.adapter = mAdapter
        fetchComicList()
    }

    private fun fetchComicList() {
        comics_view_flipper.displayedChild = 0
        subscribe(comicsListViewModel.getComics()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            Log.d("Success", "Received UIModel with ${it.data?.count} characters.")
            showComics(it)
        }, {
            Log.w("throws", it.localizedMessage)
            comics_view_flipper.displayedChild = 2
        })!!)
    }

    private fun showComics(it: Model) {
        if (it.status?.contains("Ok")!!) {
            if (it.data!!.results.isNotEmpty()) {
                comics_view_flipper.displayedChild = 1
                comicsList?.clear()
                comicsList?.addAll(it.data.results)
                mAdapter?.setUserList(comicsList)
            } else {
                comics_view_flipper.displayedChild = 2
            }
        } else {
            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchComic(query: String) {
        comics_view_flipper.displayedChild = 0
        subscribe(comicsListViewModel.getSearchedComics(query)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            Log.d("Success", "Received UIModel with ${it.data?.count} characters.")
            showSearchedComics(it)
        }, {
            Log.w("throws", it)
            comics_view_flipper.displayedChild = 2
        })!!)
    }

    private fun showSearchedComics(it: Model) {
        if (it.status?.contains("Ok")!!) {
            if (it.data!!.results.isNotEmpty()) {
                comics_view_flipper.displayedChild = 1
                comicsSearchList?.clear()
                comicsSearchList?.addAll(it.data.results)
                mAdapter?.setUserList(comicsSearchList)
            } else {
                comics_view_flipper.displayedChild = 2
            }
        } else {
            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
        }
    }
}
