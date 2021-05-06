package com.example.bulletin_board

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*

class ViewHolder(val itemList: ArrayList<boardData>?) :
    RecyclerView.Adapter<ViewHolder.ViewHolder>() {

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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: boardData) {
            itemView.titleItem.text = item._title
            itemView.writerItem.text = item._name
            itemView.dayItem.text = item._day
            itemView.contentItem.text = item._content
        }
    }


}