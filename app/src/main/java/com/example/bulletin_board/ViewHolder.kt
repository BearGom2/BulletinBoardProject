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

    var itemFilterList: ArrayList<boardData>? = itemList //Filter 기능을 거치고 나온 결과를 저장하는 ArrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (itemFilterList != null) {
            holder.bindItem(itemFilterList!![position])
            holder.itemView.setOnClickListener { //한개의 itemView를 눌렀을 때 호출
                val intent = Intent(holder.itemView.context, MainActivity::class.java)
                intent.putExtra("title", holder.itemView.titleItem.text)
                intent.putExtra("day", holder.itemView.dayItem.text)
                intent.putExtra("content", holder.itemView.contentItem.text)
                intent.putExtra("name", holder.itemView.nameItem.text)
                intent.putExtra("id", holder.itemView.idItem.text)
                ContextCompat.startActivity(
                    holder.itemView.context,
                    intent,
                    null
                ) //MainActivity로 데이터 이동 및 Activity 이동
            }
        }
    }

    override fun getItemCount(): Int {
        if (itemFilterList != null) {
            return itemFilterList!!.size
        }
        return 0
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty() || charSearch.isBlank()) { //검색어가 비었거나 없으면 실행
                    itemFilterList = itemList //itemFilterList에 기존에 response.body에 있는 모든 값 대입
                } else { //검색어가 있으면 실행
                    val resultList = ArrayList<boardData>()
                    if (itemList != null) {
                        for (row in itemList) { //검색의 범위 설정
                            if (row._title.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT)) ||
                                row._day.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT)) ||
                                row._content.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT)) ||
                                row._name.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT)) ||
                                row._id.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT))
                            ) {
                                resultList.add(row) //검색 결과 resultList에 추가
                            }
                        }
                    }
                    itemFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemFilterList
                return filterResults //itemFilterList와 값은 같음
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemFilterList = results?.values as ArrayList<boardData>
                notifyDataSetChanged() //recyclerView 새로고침
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: boardData) {
            itemView.titleItem.text = item._title
            itemView.nameItem.text = item._name
            itemView.idItem.text = item._id
            itemView.dayItem.text = item._day
            itemView.contentItem.text = item._content
        }
    }
}