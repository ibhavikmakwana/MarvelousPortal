package com.marvelousportal.fragments.comic

import android.os.Build
import android.os.Bundle
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
import com.marvelousportal.fragments.characters.CharactersFragment
import com.marvelousportal.models.Result
import com.marvelousportal.utils.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_comics.*
import java.util.*

class ComicsFragment : BaseFragment() {

    /*var mAdapter: ComicsAdapter? = null*/
    public var mAdapter: ComicsAdapter? = null
    private var comicsList: MutableList<Result>? = null
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
        /*charactersViewModel = CharactersViewModel(mContext)*/
        comicsList = ArrayList()
        mAdapter = ComicsAdapter(mContext, comicsList!!)
        search_comics.clearFocus()
        //inisalize view model factory
        /*viewModelFactory = Injection.provideViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CharactersViewModel::class.java)*/
    }

    private fun setupRecyclerView() {
        rv_comics.layoutManager = GridLayoutManager(mContext, 2)
        rv_comics.adapter = mAdapter
        fetchComicList()
    }

    private fun fetchComicList() {
        comics_view_flipper.displayedChild = 0
        val appController = AppController.create(mContext)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.fetchComics(timeStamp, Constant.PUBLIC_KEY, getHash(timeStamp))?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_attribution_html.text = Html.fromHtml(userResponse.attributionHTML, Html.FROM_HTML_MODE_LEGACY)
            } else {
                tv_attribution_html.text = Html.fromHtml(userResponse.attributionHTML)
            }
            comicsList?.clear()
            comicsList?.addAll(userResponse.data.results)
            mAdapter?.setUserList(comicsList)
            comics_view_flipper.displayedChild = 1
        }, {
            comics_view_flipper.displayedChild = 1
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)

        /*val timeStamp = getTimestamp()
        val observable = ApiFactory.getApiService()
                .fetchComics(timeStamp, Constant.PUBLIC_KEY, getHash(timeStamp))
        val listener = object : ResponseListener {
            override fun onSuccess(jsonResult: JSONObject) {
                val comicsResult = Gson().fromJson<Model>(jsonResult.toString(), Model::class.java)
                comicsList?.addAll(comicsResult.data.results)
                if (comicsList?.size!! > 0) {
                    comics_view_flipper.displayedChild = 1
                    mAdapter?.setUserList(comicsList)
                } else {
                    comics_view_flipper.displayedChild = 1
                }
            }

            override fun onError(errorCode: Int, message: String) {
                Toast.makeText(mContext,
                        message,
                        Toast.LENGTH_SHORT)
                        .show()
            }
        }
        addSubscription(ApiFactory.makeApiCall(observable, listener))*/
    }

    private fun searchComic(query: String) {
        comics_view_flipper.displayedChild = 0
        val appController = AppController.create(mContext)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.searchComics(timeStamp, Constant.PUBLIC_KEY, getHash(timeStamp),query)?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_attribution_html.text = Html.fromHtml(userResponse.attributionHTML, Html.FROM_HTML_MODE_LEGACY)
            } else {
                tv_attribution_html.text = Html.fromHtml(userResponse.attributionHTML)
            }
            comicsList?.clear()
            comicsList?.addAll(userResponse.data.results)
            mAdapter?.setUserList(comicsList)
            comics_view_flipper.displayedChild = 1
        }, {
            comics_view_flipper.displayedChild = 1
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
    }
}
