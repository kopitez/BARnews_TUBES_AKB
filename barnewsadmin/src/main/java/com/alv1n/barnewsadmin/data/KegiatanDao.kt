package com.alv1n.barnewsadmin.data

import androidx.lifecycle.LiveData

interface KegiatanDao{
    fun insertData(kegiatan: Kegiatan)

    fun getData(): LiveData<List<Kegiatan>>
}