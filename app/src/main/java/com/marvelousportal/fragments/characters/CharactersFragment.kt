package com.marvelousportal.fragments.characters


import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.Toast
import com.marvelousportal.R
import com.marvelousportal.app.AppController
import com.marvelousportal.base.BaseFragment
import com.marvelousportal.models.Model
import com.marvelousportal.models.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_characters.*


/**
 * A simple [Fragment] subclass.
 */
class CharactersFragment : BaseFragment() {

    private var mAdapter: CharactersAdapter? = null
    private var characterList: MutableList<Result>? = null
    private var searchCharacterList: MutableList<Result>? = null
    private val charactersListViewModel = AppController.injectCharacterListViewModel()
    private val offset: Int = 0
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
        search_character.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchCharacter(query)
                search_character.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun init() {
        characterList = ArrayList()
        searchCharacterList = ArrayList()
        mAdapter = CharactersAdapter(mContext, characterList!!)
        search_character.clearFocus()
        iv_search_character.setOnClickListener {
            if (characterList?.size!! > 0) {
                mAdapter?.setUserList(characterList)
                characters_view_flipper.displayedChild = 1
            } else {
                fetchCharactersList()
            }
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(mContext, 2)
        rv_characters.layoutManager = layoutManager
        rv_characters.adapter = mAdapter
        fetchCharactersList()
    }

    private fun fetchCharactersList() {
        characters_view_flipper.displayedChild = 0
        subscribe(charactersListViewModel.getCharacters(offset)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            Log.d("Success", "Received UIModel with ${it.data?.count} characters.")
            showCharacters(it)
        }, {
            Log.w("throws", it.localizedMessage)
            characters_view_flipper.displayedChild = 2
        })!!)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main, menu)
    }

    private fun searchCharacter(query: String) {
        characters_view_flipper.displayedChild = 0
        subscribe(charactersListViewModel.getSearchedCharacters(query)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            Log.d("Success", "Received UIModel with ${it.data?.count} characters.")
            showSearchedCharacters(it)
        }, {
            Log.w("throws", it)
            characters_view_flipper.displayedChild = 2
        })!!)
    }

    /**
     * show the characters user searched for.
     */
    private fun showSearchedCharacters(it: Model) {
        if (it.status?.contains("Ok")!!) {
            if (it.data!!.results.isNotEmpty()) {
                searchCharacterList?.size
                searchCharacterList?.clear()
                searchCharacterList?.addAll(it.data.results)
                mAdapter?.setUserList(searchCharacterList)
                characters_view_flipper.displayedChild = 1
            } else {
                characters_view_flipper.displayedChild = 2
            }
        } else {
            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCharacters(it: Model) {
        if (it.status?.contains("Ok")!!) {
            if (it.data!!.results.isNotEmpty()) {
                characters_view_flipper.displayedChild = 1
                characterList?.clear()
                characterList?.addAll(it.data.results)
                mAdapter?.setUserList(characterList)
            } else {
                characters_view_flipper.displayedChild = 2
            }
        } else {
            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
        }
    }
}