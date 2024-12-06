package com.example.starwarsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.R
import android.util.Log

class DetailsAdapter(
    private val details: Map<String, String?>
) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.detailTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("DetailsAdapter", "Criando suporte de visualização")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val key = details.keys.elementAt(position)
        val value = details[key]
        holder.title.text = "$key: ${value ?: "Não disponível"}"
        Log.d("DetailsAdapter", "Suporte de visualização de ligação na posição $position")
    }

    override fun getItemCount(): Int = details.size
}
