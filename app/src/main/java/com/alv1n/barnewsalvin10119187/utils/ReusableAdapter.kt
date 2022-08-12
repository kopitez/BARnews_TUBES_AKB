package com.alv1n.barnewsalvin10119187.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.properties.Delegates

class ReusableAdapter <T>(
    private var context: Context
) : RecyclerView.Adapter<ReusableAdapter<T>.ViewHolder>(),
    ReusableAdapterInterface<T>,
    Filterable {

    // utils
    var listData = mutableListOf<T>()
    var currentList = mutableListOf<T>()
    private var filterable: Boolean = false
    private var layout by Delegates.notNull<Int>()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapterCallback: AdapterCallback<T>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return if (filterable) currentList.size
        else listData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(this.layout, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (filterable) {
            adapterCallback.initComponent(holder.itemView, currentList[position], position)
            holder.itemView.setOnClickListener {
                adapterCallback.onItemClicked(it, currentList[position], position)
            }
        } else {
            adapterCallback.initComponent(holder.itemView, listData[position], position)
            holder.itemView.setOnClickListener {
                adapterCallback.onItemClicked(it, listData[position], position)
            }
        }
    }


    override fun setLayout(layout: Int): ReusableAdapter<T> {
        this.layout = layout
        return this
    }

    override fun filterable(): ReusableAdapter<T> {
        this.filterable = true
        return this
    }

    override fun addData(items: List<T>): ReusableAdapter<T> {
        listData = items as MutableList<T>
        currentList = listData
        notifyDataSetChanged()
        return this
    }

    override fun updateData(item: T): ReusableAdapter<T> {
        if (!listData.contains(item)) {
            listData.add(item)
        } else {
            val index = listData.indexOf(item)
            listData[index] = item
        }

        notifyDataSetChanged()
        return this
    }

    override fun adapterCallback(adapterCallback: AdapterCallback<T>): ReusableAdapter<T> {
        this.adapterCallback = adapterCallback
        return this
    }

    override fun isVerticalView(): ReusableAdapter<T> {
        layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        return this
    }

    override fun isHorizontalView(): ReusableAdapter<T> {
        layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        return this
    }

    override fun isGridThreeView(grid:Int): ReusableAdapter<T> {
        layoutManager = GridLayoutManager(
            context,
            grid
        )
        return this
    }


    override fun build(recyclerView: RecyclerView): ReusableAdapter<T> {
        recyclerView.apply {
            this.adapter = this@ReusableAdapter
            this.layoutManager = this@ReusableAdapter.layoutManager
        }
        return this
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val search = constraint?.toString()

                search?.let {
                    val resultList = mutableListOf<T>()
                    for (row in listData) {
                        if (row.toString().toLowerCase(Locale.ROOT)
                                .contains(search.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    currentList = resultList
                } ?: run {
                    currentList = listData
                }

                val filterResults = FilterResults()
                filterResults.values = currentList

                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currentList = results?.values as MutableList<T>
                notifyDataSetChanged()
            }

        }
    }


}


interface AdapterCallback<T> {

    // setup init component
    fun initComponent(itemView: View, data: T, itemIndex: Int)

    // onclick listener
    fun onItemClicked(itemView: View, data: T, itemIndex: Int)
}

interface ReusableAdapterInterface<T> {

    // set layout
    fun setLayout(layout: Int): ReusableAdapter<T>

    // filterable
    fun filterable(): ReusableAdapter<T>

    // append data
    fun addData(items: List<T>): ReusableAdapter<T>

    // realtime change
    fun updateData(item: T): ReusableAdapter<T>

    // adapter callback
    fun adapterCallback(adapterCallback: AdapterCallback<T>): ReusableAdapter<T>

    // layout orientation
    fun isVerticalView(): ReusableAdapter<T>
    fun isHorizontalView(): ReusableAdapter<T>
    fun isGridThreeView(grid:Int): ReusableAdapter<T>

    // build view
    fun build(recyclerView: RecyclerView): ReusableAdapter<T>

}