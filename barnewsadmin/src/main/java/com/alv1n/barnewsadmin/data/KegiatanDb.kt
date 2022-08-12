package com.alv1n.barnewsadmin.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.FirebaseDatabase


class KegiatanDb private constructor(): KegiatanDao{

    companion object {
        private const val PATH = "Activity"

        @Volatile
        private var INSTANCE: KegiatanDb? = null
        fun getInstance(): KegiatanDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = KegiatanDb()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private val database = FirebaseDatabase.getInstance().getReference(PATH)

    override fun insertData(kegiatan: Kegiatan) {
        database.push().setValue(kegiatan)
    }

    override fun getData(): LiveData<List<Kegiatan>> {
        TODO("Not yet implemented")
    }
}