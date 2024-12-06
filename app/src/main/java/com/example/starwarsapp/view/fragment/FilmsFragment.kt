package com.example.starwarsapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.FragmentFilmsBinding
import com.example.starwarsapp.controller.FilmsController
import com.example.starwarsapp.model.FilmsModel
import com.example.starwarsapp.repository.FilmsRepository
import com.example.starwarsapp.view.activity.DetailItemActivity
import com.example.starwarsapp.view.adapter.FilmsAdapter
import kotlinx.coroutines.launch

class FilmsFragment : Fragment() {

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private val filmsController = FilmsController(FilmsRepository())
    private lateinit var filmAdapter: FilmsAdapter
    private val filmList = mutableListOf<FilmsModel>()
    private var currentPage = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadFilms(currentPage)
        return binding.root
    }

    private fun setupRecyclerView() {
        filmAdapter = FilmsAdapter(filmList) { selectedFilm ->
            navigateToDetail(selectedFilm)
        }

        binding.recyclerViewFilms.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewFilms.adapter = filmAdapter

        binding.recyclerViewFilms.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItemPosition >= totalItemCount - 1) {
                    currentPage++
                    loadFilms(currentPage)
                }
            }
        })
    }

    private fun loadFilms(page: Int) {
        isLoading = true
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                filmsController.fetchFilms(page,
                    onSuccess = { films ->
                        filmList.addAll(films)
                        filmAdapter.notifyDataSetChanged()
                        isLoading = false
                    },
                    onError = { error ->
                        Toast.makeText(context, "Erro ao carregar filmes: ${error.message}", Toast.LENGTH_SHORT).show()
                        isLoading = false
                    }
                )
            } catch (e: Exception) {
                Toast.makeText(context, "Erro ao carregar filmes: ${e.message}", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        }
    }

    private fun navigateToDetail(filmsModel: FilmsModel) {
        Log.d("FilmsFragment", "Entrou no navigateToDetail")
        val intent = Intent(requireContext(), DetailItemActivity::class.java).apply {
            putExtra(DetailItemActivity.ITEM_ID, filmsModel.id)
            putExtra(DetailItemActivity.ITEM_TYPE, "films")
            Log.d("FilmsFragment", "ITEM_ID: ${filmsModel.id}, ITEM_TYPE: films")
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
