package com.alv1n.barnewsalvin10119187.ui.donasi

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.item_rekening.*
import com.alv1n.barnewsalvin10119187.R
import com.alv1n.barnewsalvin10119187.databinding.FragmentDonasiBinding


class DonasiFragment : Fragment() {

    private var _binding: FragmentDonasiBinding? = null

    private var isFilterShow: Boolean = true


    val number = "6285157011693"

    private val binding get() = _binding!!
    lateinit var rvRekening : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentDonasiBinding.inflate(inflater, container, false)
        val roots: View = binding.root

        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.VERTICAL
        rvRekening = roots.findViewById(R.id.recycleViewRekening)

        val adapterRekening = RekeningAdapter(ArrayRekening,activity)
        rvRekening.setHasFixedSize(true)
        rvRekening.layoutManager = lm
        rvRekening.adapter = adapterRekening


        btnContact()

        return roots



    }

    private fun btnContact() {
        binding.btnWhatsapp.setOnClickListener {
            openWhatsapp()
        }
    }

    private fun openWhatsapp(){
        try {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Assalamualaikum Wr. Wb. Barnoy Media, saya ingin menanyakan tentang perihal donasi ...")
                putExtra("jid", "${number}@s.whatsapp.net")
                type = "text/plain"
                setPackage("com.whatsapp")
            }
            startActivity(sendIntent)
        }catch (e: Exception){
            e.printStackTrace()
            val appPackageName = "com.whatsapp"
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
            } catch (e: android.content.ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
            }
        }
    }


    val ArrayRekening : ArrayList<RekeningModel>get(){

        val arrayrekening = ArrayList<RekeningModel>()



        val rekening1 = RekeningModel()
        val rekening2 = RekeningModel()
        val rekening3 = RekeningModel()
        val rekening4 = RekeningModel()
        val rekening5 = RekeningModel()

        rekening1.namaRekening = "A/N Barnoy Media Peduli"
        rekening1.nomerRekening = "233455125"
        rekening1.gambarRekening = R.drawable.bca

        rekening2.namaRekening = "A/N Barnoy Media Peduli"
        rekening2.nomerRekening = "(kode 008): 224.000.821.4875 "
        rekening2.gambarRekening = R.drawable.mandiri

        rekening3.namaRekening = "A/N Barnoy Media Peduli"
        rekening3.nomerRekening = " 0245.33.000.53.2388"
        rekening3.gambarRekening = R.drawable.bri

        rekening4.namaRekening = "A/N Barnoy Media Peduli"
        rekening4.nomerRekening = "245 888 26 49"
        rekening4.gambarRekening = R.drawable.bsi

        rekening5.namaRekening = "A/N Barnoy Media Peduli"
        rekening5.nomerRekening = "241251256"
        rekening5.gambarRekening = R.drawable.muamalat

        arrayrekening.add(rekening1)
        arrayrekening.add(rekening2)
        arrayrekening.add(rekening3)
        arrayrekening.add(rekening4)
        arrayrekening.add(rekening5)


        return arrayrekening



    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}