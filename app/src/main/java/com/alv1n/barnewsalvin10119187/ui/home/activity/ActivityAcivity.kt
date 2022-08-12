package com.alv1n.barnewsalvin10119187.ui.home.activity

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
import com.alv1n.barnewsalvin10119187.data.Activity
import com.alv1n.barnewsalvin10119187.databinding.ActivityAcivityBinding
import com.alv1n.barnewsalvin10119187.ui.home.HomeViewModel
import com.alv1n.barnewsalvin10119187.ui.home.detail.DetailContentActivity
import com.alv1n.barnewsalvin10119187.utils.AdapterCallback
import com.alv1n.barnewsalvin10119187.utils.ReusableAdapter

class ActivityAcivity : AppCompatActivity() {
    private lateinit var binding: ActivityAcivityBinding
    private lateinit var homeViewModel: HomeViewModel

    // adapter
    private lateinit var activityAdapter: ReusableAdapter<Activity>

    // utils
    private var rowIndex = -1
    private lateinit var user: FirebaseAuth
    private lateinit var activitys: MutableList<Activity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAcivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            elevation = 0f
            title = "Berita Terkini"
            setDisplayHomeAsUpEnabled(true)
        }

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // utils init
        activitys = mutableListOf()
        user = FirebaseAuth.getInstance()

        activityAdapter = ReusableAdapter(this)

        setupActivityAdapter(binding.rvActivity)

        initUI()

    }

    private fun initUI(){
        // init activity
        homeViewModel.fetchActivity()
        homeViewModel.activitys.observe(this, Observer {
            activitys = it.toMutableList()

            activityAdapter.addData(it)

            // null data check
            if (it.isEmpty()){
            } else {
            }
        })

        homeViewModel.activityRealtimeUpdate()
        homeViewModel.activity.observe(this, Observer {

            // update activity
//           if (!activitys.contains(it)) {
//             activitys.add(it)
//           } else {
//                val index = activitys.indexOf(it)
//                activitys[index] = it
//           }

            // realtime
//            activityAdapter.addData(activitys)

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
            Glide.with(this@ActivityAcivity)
                .load(data.image)
                .into(itemView.img_poster)
        }

        override fun onItemClicked(itemView: View, data: Activity, itemIndex: Int) {
            val intent = Intent(this@ActivityAcivity, DetailContentActivity::class.java)
            intent.putExtra("detailActivity", data)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}