package com.example.starwarsapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starwarsapp.R
import com.example.starwarsapp.databinding.ItemStarshipsBinding
import com.example.starwarsapp.model.StarshipsModel

class StarshipsAdapter(
    private val starships: List<StarshipsModel>,
    private val onClick: (StarshipsModel) -> Unit
) : RecyclerView.Adapter<StarshipsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemStarshipsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(starship: StarshipsModel, onClick: (StarshipsModel) -> Unit) {
            binding.starshipName.text = starship.name

            Glide.with(binding.starshipImage.context)
                .load(starship.url?.let { getImageUrl(it) })
                .placeholder(R.drawable.placeholder)
                .into(binding.starshipImage)

            binding.root.setOnClickListener {
                onClick(starship)
            }
        }

        private fun getImageUrl(url: String): String {
            val id = url.trimEnd('/').split("/").last()
            Log.d("StarshipsAdapter", "ID da nave: $id")
            return "https://starwars-visualguide.com/assets/img/starships/$id.jpg"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStarshipsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(starships[position], onClick)
    }

    override fun getItemCount(): Int = starships.size
}
