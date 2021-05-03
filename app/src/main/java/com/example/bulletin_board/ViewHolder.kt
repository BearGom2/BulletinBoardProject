package com.example.bulletin_board

import android.app.ListActivity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*

class ViewHolder(val itemList: ArrayList<boardData>) :
    RecyclerView.Adapter<ViewHolder.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, MainActivity::class.java)
            intent.putExtra("title", holder.itemView.titleItem.text)
            intent.putExtra("writer", holder.itemView.writerItem.text)
            intent.putExtra("day", holder.itemView.dayItem.text)
            intent.putExtra("content", holder.itemView.contentItem.text)
            ContextCompat.startActivity(holder.itemView?.context,intent,null)
        }
    }
    

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: boardData) {
            itemView.titleItem.text = item.title
            itemView.writerItem.text = item.writer
            itemView.dayItem.text = item.day
            itemView.contentItem.text = item.content
        }
    }


}