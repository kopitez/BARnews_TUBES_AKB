package com.alv1n.barnewsadmin.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Artikel (
    @get:Exclude
    var id: String? = "",
    var title: String? = "",
    var content: String? = "",
    var image: String? = "",
    var date: String? = "",
    var source: String? = "",
    @get:Exclude
    var timestamp: Long? = System.currentTimeMillis()
) : Parcelable