package com.alv1n.barnewsalvin10119187.ui.home

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_article.view.*
import com.alv1n.barnewsalvin10119187.R
import com.alv1n.barnewsalvin10119187.data.Activity
import com.alv1n.barnewsalvin10119187.data.Article
import com.alv1n.barnewsalvin10119187.databinding.FragmentHomeBinding
import com.alv1n.barnewsalvin10119187.ui.home.activity.ActivityAcivity
import com.alv1n.barnewsalvin10119187.ui.home.article.ArticleActivity
import com.alv1n.barnewsalvin10119187.ui.home.detail.DetailContentActivity
import com.alv1n.barnewsalvin10119187.utils.AdapterCallback
import com.alv1n.barnewsalvin10119187.utils.ReusableAdapter
import com.alv1n.barnewsalvin10119187.utils.notif.sendNotification

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    // adapter
    private lateinit var activityAdapter: ReusableAdapter<Activity>
    private lateinit var articleAdapter: ReusableAdapter<Article>

    // utils
    private var rowIndex = -1
    private lateinit var user: FirebaseAuth
    private lateinit var activitys: MutableList<Activity>
    private lateinit var articles: MutableList<Article>

    private var sampleImages = intArrayOf(
        R.drawable.hello,
        R.drawable.tebarcinta2,
        R.drawable.tebarcinta
    )

    private var caption = arrayOf(
        "Berita",
        "BARNews",
        "Barnoy Media"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container,  false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // utils init
        activitys = mutableListOf()
        articles = mutableListOf()
        user = FirebaseAuth.getInstance()

        activityAdapter = ReusableAdapter(requireContext())
        articleAdapter = ReusableAdapter(requireContext())

        setupActivityAdapter(binding.rvActivity)
        setupArticleAdapter(binding.rvArticle)

        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout2.visibility = View.VISIBLE

        imageSlider()

        initUI()

        buttonMore()

        return binding.root
    }


    private fun initUI(){
        // init activity
        homeViewModel.fetchActivity()
        homeViewModel.activitys.observe(viewLifecycleOwner, Observer {
            activitys = it.toMutableList()

            val dataLimit = it.reversed().take(2)

            activityAdapter.addData(dataLimit)

            // null data check
            if (it.isEmpty()){
                binding.shimmerFrameLayout.visibility = View.GONE
            } else {
                binding.shimmerFrameLayout.visibility = View.GONE
            }
        })

        homeViewModel.activityRealtimeUpdate()
        homeViewModel.activity.observe(viewLifecycleOwner, Observer {

            // update activity
            if (!activitys.contains(it)) {
                activitys.add(it)
            } else {
                val index = activitys.indexOf(it)
                activitys[index] = it
                tampilNotifikasi()
            }

            // realtime
//            activityAdapter.addData(activitys)

        })

        // init article
        homeViewModel.fetchArticle()
        homeViewModel.articles.observe(viewLifecycleOwner, Observer {
            articles = it.toMutableList()

            val dataLimit = it.reversed().take(2)

            articleAdapter.addData(dataLimit)

            // null data check
            if (it.isEmpty()){
                binding.shimmerFrameLayout2.visibility = View.GONE
            } else {
                binding.layoutTitleArticle.visibility = View.VISIBLE
                binding.shimmerFrameLayout2.visibility = View.GONE
            }
        })

        homeViewModel.articleRealtimeUpdate()
        homeViewModel.article.observe(viewLifecycleOwner, Observer {

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

    private fun setupActivityAdapter(recyclerView: RecyclerView){
        activityAdapter.adapterCallback(activityAdapterCallback)
            .setLayout(R.layout.item_article)
            .isVerticalView()
            .build(recyclerView)
    }

    private val activityAdapterCallback = object: AdapterCallback<Activity> {
        override fun initComponent(itemView: View, data: Activity, itemIndex: Int) {
            // set utils
            itemView.tv_judul_artikel.text = data.title
            itemView.tv_isi_artikel.text = data.content

            // set gambar activity
            Glide.with(requireContext())
                .load(data.image)
                .into(itemView.img_poster)
        }

        override fun onItemClicked(itemView: View, data: Activity, itemIndex: Int) {
            val intent = Intent(requireContext(), DetailContentActivity::class.java)
            intent.putExtra("detailActivity", data)
            startActivity(intent)
        }
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
            Glide.with(requireContext())
                .load(data.image)
                .into(itemView.img_poster)
        }

        override fun onItemClicked(itemView: View, data: Article, itemIndex: Int) {
            val intent = Intent(requireContext(), DetailContentActivity::class.java)
            intent.putExtra("detailArticle", data)
            startActivity(intent)
        }
    }


    private fun imageSlider(){
        binding.imageSliderHome.pageCount = caption.size
        with(binding.imageSliderHome){
            pageCount = caption.size

            setImageListener{ position, imageView ->
                imageView.setImageResource(sampleImages[position])
            }

            setImageClickListener{ position ->
                Toast.makeText(context, caption[position], Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun buttonMore(){
        binding.tvKegiatanLainnya.setOnClickListener {
           // tampilNotifikasi()
            val intent = Intent(requireContext(), ActivityAcivity::class.java)
            startActivity(intent)
        }

        binding.tvArtikelLainnya.setOnClickListener {
            val intent = Intent(requireContext(), ArticleActivity::class.java)
            startActivity(intent)
        }

        binding.layoutTitleArticle.setOnClickListener {
            val intent = Intent(requireContext(), ArticleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun tampilNotifikasi() {
        val notificationManager = ContextCompat.getSystemService(
            requireContext(), NotificationManager::class.java)
        notificationManager?.sendNotification(requireContext())
    }
}
