package com.alv1n.barnewsalvin10119187.ui.donasi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

    @Parcelize
    class RekeningModel(
        var namaRekening:String? = null,
        var nomerRekening:String? = null,
        var gambarRekening:Int = 0
    ): Parcelable
