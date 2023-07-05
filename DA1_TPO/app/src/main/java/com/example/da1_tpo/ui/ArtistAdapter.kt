package com.example.da1_tpo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.da1_tpo.R
import com.example.da1_tpo.model.Artist

class ArtistAdapter: RecyclerView.Adapter<ArtistViewHolder>()  {
    var items: MutableList<Artist> = ArrayList<Artist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_item, parent, false)
        return ArtistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.name.text = items[position].name
        Glide.with(holder.itemView)
            .load(items[position].picture)
            .into(holder.picture)

    }

    fun Update(lista: MutableList<Artist>){
        items = lista
        this.notifyDataSetChanged()
    }

}