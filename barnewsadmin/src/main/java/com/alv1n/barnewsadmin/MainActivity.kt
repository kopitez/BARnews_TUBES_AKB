package com.alv1n.barnewsadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alv1n.barnewsadmin.databinding.ActivityMainBinding
import com.alv1n.barnewsadmin.ui.ArtikelActivity
import com.alv1n.barnewsadmin.ui.KegiatanActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            elevation = 0f
            title = "BARNEWS ADMIN"
        }

        initUI()
    }

    private fun initUI(){
        binding.btnAddActivity.setOnClickListener {
            val intent = Intent(this, KegiatanActivity::class.java)
            startActivity(intent)
        }
        binding.btnAddArticle.setOnClickListener{
            val intent = Intent(this, ArtikelActivity::class.java)
            startActivity(intent)
        }

    }
}