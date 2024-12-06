package com.example.starwarsapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.R
import com.example.starwarsapp.controller.FavoritesController
import com.example.starwarsapp.databinding.FragmentFavoritesBinding
import com.example.starwarsapp.model.FavoritesModel
import com.example.starwarsapp.repository.FavoritesRepository
import com.example.starwarsapp.view.activity.DetailItemActivity
import com.example.starwarsapp.view.adapter.FavoritesAdapter
import com.google.firebase.auth.FirebaseAuth

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var favoritesController: FavoritesController
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        favoritesController = FavoritesController(FavoritesRepository())

        favoritesAdapter = FavoritesAdapter(mutableListOf()) { selectedFavorite ->
            val intent = Intent(requireContext(), DetailItemActivity::class.java).apply {
                putExtra("ITEM_ID", selectedFavorite.itemId)
                putExtra("ITEM_TYPE", selectedFavorite.itemType)
                putExtra("ITEM_NAME", selectedFavorite.title)
                putExtra("ITEM_DESCRIPTION", selectedFavorite.description)
                putExtra("ITEM_IMAGE_URL", selectedFavorite.imageUrl)
            }
            startActivity(intent)
        }

        binding.recyclerViewFavorites.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewFavorites.adapter = favoritesAdapter

        loadFavorites()

        return binding.root
    }

    private fun loadFavorites() {
        val userId = auth.currentUser?.uid ?: run {
            Toast.makeText(context, "Usuário não autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        favoritesController.loadFavorites(userId, { favorites ->
            favoritesAdapter.updateFavorites(favorites)
        }, { error ->
            Toast.makeText(context, "Erro ao carregar favoritos: $error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun removeFavorite(favorite: FavoritesModel) {
        val userId = auth.currentUser?.uid ?: run {
            Toast.makeText(context, "Usuário não autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        favoritesController.removeFavorites(userId, favorite, {
            favoritesAdapter.removeFavorite(favorite)
        }, { error ->
            Toast.makeText(context, "Erro ao remover favorito: $error", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
