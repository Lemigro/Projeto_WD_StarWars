package com.example.starwarsapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.adapter.PeopleAdapter
import com.example.starwarsapp.activity.DetailItemActivity
import com.example.starwarsapp.R
import com.example.starwarsapp.adapter.People
import com.example.starwarsapp.databinding.FragmentPeopleBinding

class PeopleFragment : Fragment() {

    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)

        // Lista de personagens com nomes, imagens e descrições
        val people = listOf(
            People("Luke Skywalker", R.drawable.luke_skywalker, "Jedi Knight, leader of the Rebellion."),
            People("Yoda", R.drawable.yoda, "Legendary Jedi Master."),
            People("Darth Vader", R.drawable.darth_vader, "Sith Lord and former Jedi Knight.")
        )

        // Configura o RecyclerView e o Adapter
        val adapter = PeopleAdapter(people) { people ->
            val intent = Intent(requireContext(), DetailItemActivity::class.java)
            intent.putExtra("ITEM_NAME", people.name)
            intent.putExtra("ITEM_DESCRIPTION", people.description) // Usando a descrição correta
            intent.putExtra("ITEM_IMAGE_URL", people.imageResId)    // Passando o ID da imagem
            startActivity(intent)
        }

        binding.recyclerViewPeople.layoutManager = GridLayoutManager(context, 2) // 2 colunas
        binding.recyclerViewPeople.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
