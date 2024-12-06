package com.example.starwarsapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starwarsapp.R
import com.example.starwarsapp.databinding.ItemVehiclesBinding
import com.example.starwarsapp.model.VehiclesModel

class VehiclesAdapter(
    private val vehiclesModels: List<VehiclesModel>,
    private val onClick: (VehiclesModel) -> Unit
) : RecyclerView.Adapter<VehiclesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemVehiclesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vehiclesModel: VehiclesModel, onClick: (VehiclesModel) -> Unit) {
            binding.vehiclesName.text = vehiclesModel.name

            Glide.with(binding.vehiclesImage.context)
                .load(vehiclesModel.url?.let { getImageUrl(it) })
                .placeholder(R.drawable.placeholder)
                .into(binding.vehiclesImage)

            binding.root.setOnClickListener {
                onClick(vehiclesModel)
            }
        }

        private fun getImageUrl(url: String): String {
            val id = url.trimEnd('/').split("/").last()
            Log.d("VehiclesAdapter", "ID do veículo: $id")
            return "https://starwars-visualguide.com/assets/img/vehicles/$id.jpg"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVehiclesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(vehiclesModels[position], onClick)
    }

    override fun getItemCount(): Int = vehiclesModels.size
}
