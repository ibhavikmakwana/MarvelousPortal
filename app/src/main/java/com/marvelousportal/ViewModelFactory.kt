package com.marvelousportal

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.marvelousportal.viewmodels.CharactersViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharactersViewModel() as T
    }
}