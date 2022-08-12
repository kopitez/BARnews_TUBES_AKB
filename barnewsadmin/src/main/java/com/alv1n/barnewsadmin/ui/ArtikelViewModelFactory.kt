package com.alv1n.barnewsadmin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alv1n.barnewsadmin.data.ArtikelDao

class ArtikelViewModelFactory (private val dataSource: ArtikelDao) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtikelViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtikelViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}