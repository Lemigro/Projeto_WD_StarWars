package com.example.starwarsapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.adapter.Films
import com.example.starwarsapp.adapter.FilmsAdapter
import com.example.starwarsapp.R
import com.example.starwarsapp.activity.DetailItemActivity
import com.example.starwarsapp.databinding.FragmentFilmsBinding

class FilmsFragment : Fragment() {

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)

        // Lista de filmes
        val films = listOf(
            Films("A New Hope", R.drawable.starwars_newhope, "O primeiro filme da saga Star Wars."),
            Films("The Empire Strikes Back", R.drawable.starwars_empirestrikesback, "O segundo filme da trilogia original."),
            Films("Return of the Jedi", R.drawable.starwars_returnofthejedi, "O terceiro filme da trilogia original.")
        )

        // Configura o RecyclerView e o Adapter
        val adapter = FilmsAdapter(films) { selectedFilms ->
            val intent = Intent(requireContext(), DetailItemActivity::class.java)
            intent.putExtra("ITEM_NAME", selectedFilms.title)
            intent.putExtra("ITEM_DESCRIPTION", selectedFilms.description)
            intent.putExtra("ITEM_IMAGE_URL", selectedFilms.imageResId)
            startActivity(intent)
        }

        binding.recyclerViewFilms.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewFilms.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}