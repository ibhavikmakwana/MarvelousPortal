package com.marvelousportal.viewmodels

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.marvelousportal.models.Result
import java.util.*


class CharactersViewModel : ViewModel() {


    private var characterList: MutableList<Result>? = null

    init {
        this.characterList = ArrayList()
    }

    private fun updateCharactersDataList(characterResult: List<Result>) {
    }

    fun getCharacterList(context: Context): List<Result>? {
        return characterList
    }

    private fun unSubscribeFromObservable() {

    }
}

