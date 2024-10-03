package com.example.starwarsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.ItemVehiclesBinding

data class Vehicles(
    val name: String,
    val imageResId: Int,
    val description: String
)

class VehiclesAdapter(
    private val vehicles: List<Vehicles>,
    private val onClick: (Vehicles) -> Unit
) : RecyclerView.Adapter<VehiclesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemVehiclesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicles: Vehicles, onClick: (Vehicles) -> Unit) {
            binding.vehiclesName.text = vehicles.name
            binding.vehiclesImage.setImageResource(vehicles.imageResId)
            binding.root.setOnClickListener {
                onClick(vehicles)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVehiclesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(vehicles[position], onClick)
    }

    override fun getItemCount() = vehicles.size
}
