package com.example.starwarsapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starwarsapp.R
import com.example.starwarsapp.databinding.ItemPeoplesBinding
import com.example.starwarsapp.model.PeoplesModel
import android.util.Log

class PeoplesAdapter(
    private val characters: List<PeoplesModel>,
    private val onClick: (PeoplesModel) -> Unit
) : RecyclerView.Adapter<PeoplesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemPeoplesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(peoplesModel: PeoplesModel, onClick: (PeoplesModel) -> Unit) {
            Log.d("PeoplesAdapter", "Trazendo o personagem: ${peoplesModel.name}")
            binding.peopleName.text = peoplesModel.name

            Glide.with(binding.peopleImage.context)
                .load(peoplesModel.url?.let { getImageUrl(it) })
                .placeholder(R.drawable.placeholder)
                .into(binding.peopleImage)

            binding.root.setOnClickListener {
                onClick(peoplesModel)
            }
        }

        private fun getImageUrl(url: String): String {
            val id = url.trimEnd('/').split("/").last()
            Log.d("PeoplesAdapter", "ID do personagem: $id")
            return "https://starwars-visualguide.com/assets/img/characters/$id.jpg"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("PeoplesAdapter", "onCreateViewHolder called")
        val binding = ItemPeoplesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("PeoplesAdapter", "onBindViewHolder called for position: $position")
        holder.bind(characters[position], onClick)
    }

    override fun getItemCount() = characters.size
}
