package com.example.starwarsapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starwarsapp.R
import com.example.starwarsapp.databinding.ItemPlanetsBinding
import com.example.starwarsapp.model.PlanetsModel

class PlanetsAdapter(
    private val planetsModels: List<PlanetsModel>,
    private val onClick: (PlanetsModel) -> Unit
) : RecyclerView.Adapter<PlanetsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemPlanetsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(planetsModel: PlanetsModel, onClick: (PlanetsModel) -> Unit) {
            binding.planetName.text = planetsModel.name

            Glide.with(binding.planetImage.context)
                .load(getImageUrl(planetsModel.url))
                .placeholder(R.drawable.placeholder)
                .into(binding.planetImage)

            binding.root.setOnClickListener {
                onClick(planetsModel)
            }
        }

        private fun getImageUrl(url: String): String {
            val id = url.trimEnd('/').split("/").last()
            return "https://starwars-visualguide.com/assets/img/planets/$id.jpg"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlanetsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(planetsModels[position], onClick)
    }

    override fun getItemCount(): Int = planetsModels.size
}
