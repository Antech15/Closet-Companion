package com.example.closetcompanion.fragments.RecyclerView

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.closetcompanion.R
import com.example.closetcompanion.data.Outfit

class OutfitAdapter(
    private val outfitList: List<Outfit>,
    private val mContext: Context
) : RecyclerView.Adapter<OutfitAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val outfitName: TextView = itemView.findViewById(R.id.outfitName)
        //val topImage: ImageView = itemView.findViewById(R.id.topImage)
        //val bottomImage: ImageView = itemView.findViewById(R.id.bottomImage)
        //val shoesImage: ImageView = itemView.findViewById(R.id.shoesImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.outfit_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentOutfit = outfitList[position]
        holder.outfitName.text = currentOutfit.name
        holder.topImage.setImageResource(currentOutfit.topImage)
        holder.bottomImage.setImageResource(currentOutfit.bottomImage)
        holder.shoesImage.setImageResource(currentOutfit.shoesImage)
    }

    override fun getItemCount(): Int {
        return outfitList.size
    }
}