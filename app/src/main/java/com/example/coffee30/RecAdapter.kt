package com.example.coffee30

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecAdapter(var iList: List<Items>, var clickListener: ClickListener) : RecyclerView.Adapter<RecAdapter.ItemViewHolder>() {

        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titles: TextView = itemView.findViewById(R.id.titles)
        }

        fun setFilteredList(iList: List<Items>) {
            this.iList = iList
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val items = iList[position]

            holder.titles.text = iList[position].title
            holder.itemView.setOnClickListener {
                clickListener.ClickedItem(items)
            }
        }

        override fun getItemCount(): Int {
            return iList.size
        }
        interface ClickListener{
            fun ClickedItem(items : Items)
        }
}
