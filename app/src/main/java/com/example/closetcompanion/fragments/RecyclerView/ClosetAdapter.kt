package com.example.closetcompanion.fragments.RecyclerView

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.closetcompanion.R
import com.example.closetcompanion.data.Closet

class ClosetAdapter(
    private val closetList: MutableList<Closet>,
    private val mContext: Context
) : RecyclerView.Adapter<ClosetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val closet = closetList[position]
        holder.closetName.text = closet.name
    }

    override fun getItemCount(): Int {
        return closetList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val closetName: TextView = itemView.findViewById(R.id.Title)

        init {
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    "You have clicked ${closetList[adapterPosition].name}",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(itemView.context, Details::class.java)
                intent.putExtra("thang", closetList[adapterPosition] as java.io.Serializable)
                mContext.startActivity(intent)
            }
        }
    }
}
