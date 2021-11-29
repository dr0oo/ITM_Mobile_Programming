package com.example.itm_term_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreListAdapter(private val context: Context): RecyclerView.Adapter<ScoreListAdapter.ViewHolder>() {
    var datas = mutableListOf<ScoreList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.scorelist_frag,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ScoreListAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
        holder.itemView.setOnClickListener {
//            itemClickListener.onClick(it, position)
        }
    }

    fun removeAt(position: Int){
        if(position>0){
            datas.removeAt(position)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val name: TextView = itemView.findViewById(R.id.title)

        fun bind(item: ScoreList){
            name.text = item.name
        }
    }

//    interface OnItemClickListener {
//        fun onClick(v: View, position: Int)
//    }
//
//    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
//        this.itemClickListener = onItemClickListener
//    }
//
//    private lateinit var itemClickListener : OnItemClickListener

}