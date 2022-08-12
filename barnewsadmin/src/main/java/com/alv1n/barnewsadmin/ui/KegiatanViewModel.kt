package com.alv1n.barnewsadmin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.alv1n.barnewsadmin.data.Kegiatan
import com.alv1n.barnewsadmin.data.KegiatanDao

class KegiatanViewModel(private val db: KegiatanDao) : ViewModel() {

    //val data = db.getData()

    fun insertData(kegiatan: Kegiatan) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insertData(kegiatan)
            }
        }
    }
}