package com.example.starwarsapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starwarsapp.R
import com.example.starwarsapp.databinding.ItemFilmsBinding
import com.example.starwarsapp.model.FilmsModel

class FilmsAdapter(
    private var films: List<FilmsModel>,
    private val onClick: (FilmsModel) -> Unit
) : RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemFilmsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: FilmsModel, onClick: (FilmsModel) -> Unit) {
            binding.filmsTitle.text = film.title

            Glide.with(binding.filmsImage.context)
                .load(getImageUrl(film.url))
                .placeholder(R.drawable.placeholder)
                .into(binding.filmsImage)

            binding.root.setOnClickListener {
                onClick(film)
            }
        }

        private fun getImageUrl(url: String): String {
            val id = url.trimEnd('/').split("/").last()
            return "https://starwars-visualguide.com/assets/img/films/$id.jpg"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilmsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(films[position], onClick)
    }

    override fun getItemCount(): Int = films.size
}
