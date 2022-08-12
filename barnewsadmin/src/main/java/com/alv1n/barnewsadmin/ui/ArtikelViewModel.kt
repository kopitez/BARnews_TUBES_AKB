package com.alv1n.barnewsadmin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.alv1n.barnewsadmin.data.Artikel
import com.alv1n.barnewsadmin.data.ArtikelDao

class ArtikelViewModel(private val db: ArtikelDao):ViewModel() {

    fun insertData(artikel: Artikel){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.insertData(artikel)
            }
        }
    }
}