package com.example.starwarsapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.adapter.CharactersAdapter
import com.example.starwarsapp.adapter.Character
import com.example.starwarsapp.activity.DetailItemActivity
import com.example.starwarsapp.R
import com.example.starwarsapp.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)

        // Lista de personagens com nomes, imagens e descrições
        val characters = listOf(
            Character("Luke Skywalker", R.drawable.luke_skywalker, "Jedi Knight, leader of the Rebellion."),
            Character("Yoda", R.drawable.yoda, "Legendary Jedi Master."),
            Character("Darth Vader", R.drawable.darth_vader, "Sith Lord and former Jedi Knight.")
        )

        // Configura o RecyclerView e o Adapter
        val adapter = CharactersAdapter(characters) { character ->
            val intent = Intent(requireContext(), DetailItemActivity::class.java)
            intent.putExtra("ITEM_NAME", character.name)
            intent.putExtra("ITEM_DESCRIPTION", character.description) // Usando a descrição correta
            intent.putExtra("ITEM_IMAGE_URL", character.imageResId)    // Passando o ID da imagem
            startActivity(intent)
        }

        binding.recyclerViewCharacters.layoutManager = GridLayoutManager(context, 2) // 2 colunas
        binding.recyclerViewCharacters.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
