package com.alv1n.barnewsalvin10119187.ui.home.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_article.view.*
import com.alv1n.barnewsalvin10119187.R
import com.alv1n.barnewsalvin10119187.data.Article
import com.alv1n.barnewsalvin10119187.databinding.ActivityArticleBinding
import com.alv1n.barnewsalvin10119187.ui.home.HomeViewModel
import com.alv1n.barnewsalvin10119187.ui.home.detail.DetailContentActivity
import com.alv1n.barnewsalvin10119187.utils.AdapterCallback
import com.alv1n.barnewsalvin10119187.utils.ReusableAdapter

class ArticleActivity : AppCompatActivity() {


    private lateinit var binding: ActivityArticleBinding
    private lateinit var homeViewModel: HomeViewModel

    // adapter
    private lateinit var articleAdapter: ReusableAdapter<Article>

    // utils
    private var rowIndex = -1
    private lateinit var user: FirebaseAuth
    private lateinit var articles: MutableList<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            elevation = 0f
            title = "Artikel"
            setDisplayHomeAsUpEnabled(true)
        }

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // utils init
        articles = mutableListOf()
        user = FirebaseAuth.getInstance()

        articleAdapter = ReusableAdapter(this)

        setupArticleAdapter(binding.rvArticle)

        initUI()
    }

    private fun initUI(){
        // init article
        homeViewModel.fetchArticle()
        homeViewModel.articles.observe(this, Observer {
            articles = it.toMutableList()

            articleAdapter.addData(it)

            // null data check
            if (it.isEmpty()){
            } else {
            }
        })

        homeViewModel.articleRealtimeUpdate()
        homeViewModel.article.observe(this, Observer {

            // update article
            if (!articles.contains(it)) {
                articles.add(it)
            } else {
                val index = articles.indexOf(it)
                articles[index] = it
            }

            // realtime
//            articleAdapter.addData(articles)

        })

    }

    private fun setupArticleAdapter(recyclerView: RecyclerView){
        articleAdapter.adapterCallback(articleAdapterCallback)
            .setLayout(R.layout.item_article)
            .isVerticalView()
            .build(recyclerView)
    }

    private val articleAdapterCallback = object: AdapterCallback<Article> {
        override fun initComponent(itemView: View, data: Article, itemIndex: Int) {
            // set utils
            itemView.tv_judul_artikel.text = data.title
            itemView.tv_isi_artikel.text = data.content

            // set gambar article
            Glide.with(this@ArticleActivity)
                .load(data.image)
                .into(itemView.img_poster)
        }

        override fun onItemClicked(itemView: View, data: Article, itemIndex: Int) {
            val intent = Intent(this@ArticleActivity, DetailContentActivity::class.java)
            intent.putExtra("detailArticle", data)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}