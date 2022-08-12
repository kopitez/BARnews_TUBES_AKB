package com.alv1n.barnewsalvin10119187.ui.donasi

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alv1n.barnewsalvin10119187.R

class RekeningAdapter (
    var data : ArrayList<RekeningModel>, var context: Activity?
        ): RecyclerView.Adapter<RekeningAdapter.MyViewHolder>(){

            class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
                val namaRekning = view.findViewById<TextView>(R.id.tv_nama_rek)
                val nomerrekening = view.findViewById<TextView>(R.id.tv_no_rek)
                val gambarRekening = view.findViewById<ImageView>(R.id.img_bank)
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_rekening, parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.namaRekning.text = data[position].namaRekening
        holder.nomerrekening.text = data[position].nomerRekening
        holder.gambarRekening.setImageResource(data[position].gambarRekening)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}