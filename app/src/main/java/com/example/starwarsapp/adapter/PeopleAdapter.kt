package com.example.starwarsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.ItemPeopleBinding

data class People(val name: String, val imageResId: Int, val description: String)

class PeopleAdapter(
    private val characters: List<People>,
    private val onClick: (People) -> Unit
) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemPeopleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(people: People, onClick: (People) -> Unit) {
            binding.peopleName.text = people.name
            binding.peopleImage.setImageResource(people.imageResId)
            binding.root.setOnClickListener {
                onClick(people)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position], onClick)
    }

    override fun getItemCount() = characters.size
}
