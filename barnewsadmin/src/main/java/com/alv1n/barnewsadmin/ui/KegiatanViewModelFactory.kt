package com.alv1n.barnewsadmin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alv1n.barnewsadmin.data.KegiatanDao

class KegiatanViewModelFactory(private val dataSource: KegiatanDao) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KegiatanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return KegiatanViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}