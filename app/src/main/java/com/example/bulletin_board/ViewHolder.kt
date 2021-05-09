package com.example.bulletin_board

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*
import java.util.*
import kotlin.collections.ArrayList

class ViewHolder(val itemList: ArrayList<boardData>?) :
    RecyclerView.Adapter<ViewHolder.ViewHolder>(), Filterable {

    var itemFilterList = itemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (itemList != null) {
            holder.bindItem(itemList[position])
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, MainActivity::class.java)
                intent.putExtra("title", holder.itemView.titleItem.text)
                intent.putExtra("name", holder.itemView.writerItem.text)
                intent.putExtra("day", holder.itemView.dayItem.text)
                intent.putExtra("content", holder.itemView.contentItem.text)
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int {
        if (itemList != null) {
            return itemList.size
        }
        return 0
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemFilterList = itemList
                } else {
                    val resultList = ArrayList<boardData>()
                    if (itemList != null) {
                        for (row in itemList) {
                            if (row._title.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT)) ||
                                row._day.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT)) ||
                                row._title.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT)) ||
                                row._name.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT))
                            ) {
                                resultList.add(row)
                            }
                        }
                    }
                    itemFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemFilterList = results?.values as ArrayList<boardData>
                notifyDataSetChanged()
            }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: boardData) {
            itemView.titleItem.text = item._title
            itemView.writerItem.text = item._name
            itemView.dayItem.text = item._day
            itemView.contentItem.text = item._content
        }
    }
}