package com.alv1n.barnewsalvin10119187.ui.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_content.*
import com.alv1n.barnewsalvin10119187.R
import com.alv1n.barnewsalvin10119187.data.Activity
import com.alv1n.barnewsalvin10119187.data.Article
import com.alv1n.barnewsalvin10119187.databinding.ActivityDetailContentBinding

class DetailContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemArticle = intent?.getParcelableExtra<Article>("detailArticle")
        val itemActivity = intent?.getParcelableExtra<Activity>("detailActivity")


        if (itemArticle != null) {
            initUIArticle(itemArticle)
        } else if (itemActivity != null) {
            initUIActivity(itemActivity)
        }

        setActionBar()
    }

    private fun setActionBar(){
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            elevation = 0f
            title = "Detail"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initUIArticle(item: Article) {
        tv_judul_detail.text = item.title
        tv_tanggal_artikel.text = item.date
        tv_isi_detail.text = item.content
        Glide.with(this).load(item.image)
            .into(header_detail)
    }

    private fun initUIActivity(item: Activity) {
        tv_judul_detail.text = item.title
        tv_tanggal_artikel.text = item.date
        tv_isi_detail.text = item.content
        Glide.with(this).load(item.image)
            .into(header_detail)
    }



    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}