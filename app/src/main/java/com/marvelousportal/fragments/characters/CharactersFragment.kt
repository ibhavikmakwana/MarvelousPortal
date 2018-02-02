package com.marvelousportal.fragments.characters


import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.marvelousportal.R
import com.marvelousportal.R.id.tv_character_attribution_html
import com.marvelousportal.ViewModelFactory
import com.marvelousportal.app.AppController
import com.marvelousportal.base.BaseFragment
import com.marvelousportal.models.Result
import com.marvelousportal.utils.Constant
import com.marvelousportal.viewmodels.CharactersViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_characters.*
import kotlinx.android.synthetic.main.fragment_comics.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class CharactersFragment : BaseFragment() {

    private var charactersViewModel: CharactersViewModel? = null
    public var mAdapter: CharactersAdapter? = null
    private var characterList: MutableList<Result>? = null
    private lateinit var viewModel: CharactersViewModel
    private lateinit var viewModelFactory: ViewModelFactory

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
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupRecyclerView()
    }

    private fun init() {
        /*charactersViewModel = CharactersViewModel(mContext)*/
        characterList = ArrayList()
        mAdapter = CharactersAdapter(mContext, characterList!!)
        //inisalize view model factory
        /*viewModelFactory = Injection.provideViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CharactersViewModel::class.java)*/
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(mContext, 2)
        rv_characters.layoutManager = layoutManager
        rv_characters.adapter = mAdapter
        //Divider
        /*val dividerItemDecoration = DividerItemDecoration(rv_characters.context, layoutManager.orientation)
        rv_characters.addItemDecoration(dividerItemDecoration)*/
        fetchCharactersList()
    }

    private fun fetchCharactersList() {
        characters_view_flipper.displayedChild = 0
        val appController = AppController.create(mContext)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.fetchCharacters(timeStamp, Constant.PUBLIC_KEY, getHash(timeStamp))?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_character_attribution_html.text = Html.fromHtml(userResponse.attributionHTML, Html.FROM_HTML_MODE_LEGACY)
            } else {
                tv_character_attribution_html.text = Html.fromHtml(userResponse.attributionHTML)
            }
            characterList?.addAll(userResponse.data.results)
            mAdapter?.setUserList(characterList)
            characters_view_flipper.displayedChild = 1
        }, {
            characters_view_flipper.displayedChild = 1
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        })
        addSubscription(disposable)
    }
}
