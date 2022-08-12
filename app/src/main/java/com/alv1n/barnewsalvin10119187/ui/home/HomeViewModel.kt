package com.alv1n.barnewsalvin10119187.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.alv1n.barnewsalvin10119187.data.Activity
import com.alv1n.barnewsalvin10119187.data.Article
import com.alv1n.barnewsalvin10119187.utils.Constant.DB_ACTIVITY
import com.alv1n.barnewsalvin10119187.utils.Constant.DB_ARTICLE

@Suppress("SpellCheckingInspection")
class HomeViewModel: ViewModel() {

    private val TAG = "MyActivity"

    // database
    private val dbActivity = FirebaseDatabase.getInstance().getReference(DB_ACTIVITY)
    private val dbArticle = FirebaseDatabase.getInstance().getReference(DB_ARTICLE)

    // buat activity
    private val _activitys = MutableLiveData<List<Activity>>()
    val activitys: LiveData<List<Activity>>
        get() = _activitys

    // buat activity untuk realtime update
    private val _activity = MutableLiveData<Activity>()
    val activity: LiveData<Activity>
        get() = _activity

    // buat article
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    // buat article untuk realtime update
    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article>
        get() = _article

    fun fetchActivity() {
        dbActivity.addListenerForSingleValueEvent(activityValueEventListener)
    }

    private val activityValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val activitys = mutableListOf<Activity>()
                for (activitySnapshot in snapshot.children) {
                    val activity = Activity(
                        activitySnapshot.child("id").getValue(String::class.java),
                        activitySnapshot.child("title").getValue(String::class.java),
                        activitySnapshot.child("content").getValue(String::class.java),
                        activitySnapshot.child("image").getValue(String::class.java),
                        activitySnapshot.child("date").getValue(String::class.java),
                        activitySnapshot.child("source").getValue(String::class.java)
                    )
                    activity.id = activitySnapshot.key
                    activity.let { activitys.add(it) }
                }
                _activitys.value = activitys
                Log.d(TAG, "Value is: ")
            }
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    // realtime update
    fun activityRealtimeUpdate(){
        dbActivity.addChildEventListener(getActivityRealtimeUpate)
    }

    private val getActivityRealtimeUpate = object : ChildEventListener {
        override fun onChildAdded(activitySnapshot: DataSnapshot, previousChildName: String?) {
            val activity = Activity(
                activitySnapshot.child("id").getValue(String::class.java),
                activitySnapshot.child("title").getValue(String::class.java),
                activitySnapshot.child("content").getValue(String::class.java),
                activitySnapshot.child("image").getValue(String::class.java),
                activitySnapshot.child("date").getValue(String::class.java),
                activitySnapshot.child("source").getValue(String::class.java)
            )
            activity.id = activitySnapshot.key
            _activity.value = activity
        }

        override fun onChildChanged(activitySnapshot: DataSnapshot, previousChildName: String?) {
            val activity = Activity(
                activitySnapshot.child("id").getValue(String::class.java),
                activitySnapshot.child("title").getValue(String::class.java),
                activitySnapshot.child("content").getValue(String::class.java),
                activitySnapshot.child("image").getValue(String::class.java),
                activitySnapshot.child("date").getValue(String::class.java),
                activitySnapshot.child("source").getValue(String::class.java)
            )
            activity.id = activitySnapshot.key
            _activity.value = activity
//            Toast.makeText(
//                conte,
//                "Data di update",
//                Toast.LENGTH_LONG
//            ).show()
        }

        override fun onChildRemoved(activitySnapshot: DataSnapshot) {
        }

        override fun onChildMoved(activitySnapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    fun fetchArticle() {
        dbArticle.addListenerForSingleValueEvent(articleValueEventListener)
    }

    private val articleValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val articles = mutableListOf<Article>()
                for (articleSnapshot in snapshot.children) {
                    val article = Article(
                        articleSnapshot.child("id").getValue(String::class.java),
                        articleSnapshot.child("title").getValue(String::class.java),
                        articleSnapshot.child("content").getValue(String::class.java),
                        articleSnapshot.child("image").getValue(String::class.java),
                        articleSnapshot.child("date").getValue(String::class.java),
                        articleSnapshot.child("source").getValue(String::class.java)
                    )
                    article.id = articleSnapshot.key
                    article.let { articles.add(it) }
                }
                _articles.value = articles
            }
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    // realtime update
    fun articleRealtimeUpdate(){
        dbArticle.addChildEventListener(getArticleRealtimeUpate)
    }

    private val getArticleRealtimeUpate = object : ChildEventListener {
        override fun onChildAdded(articleSnapshot: DataSnapshot, previousChildName: String?) {
            val article = Article(
                articleSnapshot.child("id").getValue(String::class.java),
                articleSnapshot.child("title").getValue(String::class.java),
                articleSnapshot.child("content").getValue(String::class.java),
                articleSnapshot.child("image").getValue(String::class.java),
                articleSnapshot.child("date").getValue(String::class.java),
                articleSnapshot.child("source").getValue(String::class.java)
            )
            article.id = articleSnapshot.key
            _article.value = article
        }

        override fun onChildChanged(articleSnapshot: DataSnapshot, previousChildName: String?) {
            val article = Article(
                articleSnapshot.child("id").getValue(String::class.java),
                articleSnapshot.child("title").getValue(String::class.java),
                articleSnapshot.child("content").getValue(String::class.java),
                articleSnapshot.child("image").getValue(String::class.java),
                articleSnapshot.child("date").getValue(String::class.java),
                articleSnapshot.child("source").getValue(String::class.java)
            )
            article.id = articleSnapshot.key
            _article.value = article
        }

        override fun onChildRemoved(articleSnapshot: DataSnapshot) {
        }

        override fun onChildMoved(articleSnapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    override fun onCleared() {
        super.onCleared()
        dbActivity.removeEventListener(activityValueEventListener)
    }
}