package com.alv1n.barnewsadmin.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.FirebaseDatabase

class ArtikelDb private constructor(): ArtikelDao {
    companion object{
        private const val PATH = "Article"

        @Volatile
        private var INSTANCE: ArtikelDb?=null
        fun getInstance(): ArtikelDb {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = ArtikelDb()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
    private val database = FirebaseDatabase.getInstance().getReference(PATH)

    override fun insertData(artikel: Artikel){
        database.push().setValue(artikel)
    }
    override fun getData(): LiveData<List<Artikel>> {
        TODO("Not yet Implemented")
    }
}