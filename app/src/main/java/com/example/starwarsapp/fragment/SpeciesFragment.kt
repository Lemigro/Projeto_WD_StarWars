package com.example.starwarsapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.adapter.Species
import com.example.starwarsapp.adapter.SpeciesAdapter
import com.example.starwarsapp.R
import com.example.starwarsapp.activity.DetailItemActivity
import com.example.starwarsapp.databinding.FragmentSpeciesBinding


class SpeciesFragment : Fragment() {

    private var _binding: FragmentSpeciesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpeciesBinding.inflate(inflater, container, false)

        // Lista de espécies
        val species = listOf(
            Species("Human", R.drawable.human, "Humanos são a espécie dominante em muitos planetas."),
            Species("Wookiee", R.drawable.wookiee, "Wookiees são conhecidos por sua força e lealdade."),
            Species("Twi'lek", R.drawable.twilek, "Twi'leks são uma espécie humanoide com tentáculos em suas cabeças.")
        )

        // Configura o RecyclerView e o Adapter
        val adapter = SpeciesAdapter(species) { selectedSpecies ->
            val intent = Intent(requireContext(), DetailItemActivity::class.java)
            intent.putExtra("ITEM_NAME", selectedSpecies.name)
            intent.putExtra("ITEM_DESCRIPTION", selectedSpecies.description)
            intent.putExtra("ITEM_IMAGE_URL", selectedSpecies.imageResId)
            startActivity(intent)
        }

        // Configuração do RecyclerView
        binding.recyclerViewSpecies.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewSpecies.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
