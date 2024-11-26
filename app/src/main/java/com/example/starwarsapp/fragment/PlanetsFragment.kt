package com.example.starwarsapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.adapter.Planets
import com.example.starwarsapp.adapter.PlanetsAdapter
import com.example.starwarsapp.R
import com.example.starwarsapp.activity.DetailItemActivity
import com.example.starwarsapp.databinding.FragmentPlanetsBinding

class PlanetsFragment : Fragment() {

    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)

        // Lista de planetas
        val planets = listOf(
            Planets("Tatooine", R.drawable.tatooine, "Tatooine é um planeta desértico fictício da franquia Star Wars. É um mundo pouco habitado e de cor bege que orbita estrelas binárias e habitado por colonos humanos e uma grande variedade de espécies de outros planetas."),
            Planets("Naboo", R.drawable.endor, "Endor é uma lua fictícia do universo de Star Wars, conhecida por suas infinitas florestas, savanas, pastagens, cadeias de montanhas e alguns oceanos. A lua foi o local de uma batalha fundamental retratada em O Retorno de Jedi."),
            Planets("Hoth", R.drawable.hoth, "Hoth é um planeta gelado do universo fictício de Star Wars. Ele apareceu pela primeira vez no filme O Império Contra-Ataca, de 1980, e também foi cenário de livros e videogames de Star Wars.")
        )

        // Configura o RecyclerView e o Adapter
        val adapter = PlanetsAdapter(planets) { selectedPlanets ->
            val intent = Intent(requireContext(), DetailItemActivity::class.java)
            intent.putExtra("ITEM_NAME", selectedPlanets.name)
            intent.putExtra("ITEM_DESCRIPTION", selectedPlanets.description)
            intent.putExtra("ITEM_IMAGE_URL", selectedPlanets.imageResId)
            startActivity(intent)
        }

        binding.recyclerViewPlanets.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewPlanets.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
