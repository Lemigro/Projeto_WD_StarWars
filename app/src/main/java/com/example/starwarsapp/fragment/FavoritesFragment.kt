package com.example.starwarsapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.adapter.Favorites
import com.example.starwarsapp.R
import com.example.starwarsapp.activity.DetailItemActivity
import com.example.starwarsapp.adapter.FavoritesAdapter
import com.example.starwarsapp.adapter.People
import com.example.starwarsapp.adapter.Planets
import com.example.starwarsapp.adapter.Starships
import com.example.starwarsapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        // Lista de favoritos
        val favorites = listOf(
            Favorites("A New Hope", R.drawable.starwars_newhope, "O primeiro filme da saga Star Wars."),
            Favorites("Naboo", R.drawable.endor, "Endor é uma lua fictícia do universo de Star Wars, conhecida por suas infinitas florestas, savanas, pastagens, cadeias de montanhas e alguns oceanos. A lua foi o local de uma batalha fundamental retratada em O Retorno de Jedi."),
            Favorites("Yoda", R.drawable.yoda, "Legendary Jedi Master."),
            Favorites("Darth Vader", R.drawable.darth_vader, "Sith Lord and former Jedi Knight."),
            Favorites("X-Wing", R.drawable.xwing, "Um caça estelar usado pela Aliança Rebelde.")
            )

        // Configura o RecyclerView e o Adapter
        val adapter = FavoritesAdapter(favorites) { selectedFavorites ->
            val intent = Intent(requireContext(), DetailItemActivity::class.java)
            intent.putExtra("ITEM_NAME", selectedFavorites.title)
            intent.putExtra("ITEM_DESCRIPTION", selectedFavorites.description)
            intent.putExtra("ITEM_IMAGE_URL", selectedFavorites.imageResId)
            startActivity(intent)
        }

        binding.recyclerViewFavorites.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewFavorites.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
