package com.alv1n.barnewsalvin10119187.data

class BeritaData {
    var judul :String? = null
    var deskripsi :String? = null
    var gambar :String? = null
    constructor(){}

    constructor(
        judul:String?,
        deskripsi:String?,
        gambar:String
    ){
        this.judul = judul
        this.deskripsi = deskripsi
        this.gambar = gambar
    }
}