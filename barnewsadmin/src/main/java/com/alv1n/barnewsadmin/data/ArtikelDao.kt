package com.alv1n.barnewsadmin.data

import androidx.lifecycle.LiveData

interface ArtikelDao {

    fun insertData(artikel: Artikel)

    fun getData(): LiveData<List<Artikel>>
}