package com.example.starwarsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.ItemCharacterBinding

data class Character(val name: String, val imageResId: Int, val description: String)

class CharactersAdapter(
    private val characters: List<Character>,
    private val onClick: (Character) -> Unit
) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character, onClick: (Character) -> Unit) {
            binding.characterName.text = character.name
            binding.characterImage.setImageResource(character.imageResId)
            binding.root.setOnClickListener {
                onClick(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position], onClick)
    }

    override fun getItemCount() = characters.size
}
