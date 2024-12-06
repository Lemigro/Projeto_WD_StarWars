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
import com.example.starwarsapp.databinding.FragmentSpeciesBinding
import com.example.starwarsapp.controller.SpeciesController
import com.example.starwarsapp.model.SpeciesModel
import com.example.starwarsapp.repository.SpeciesRepository
import com.example.starwarsapp.view.activity.DetailItemActivity
import com.example.starwarsapp.view.adapter.SpeciesAdapter
import kotlinx.coroutines.launch

class SpeciesFragment : Fragment() {

    private var _binding: FragmentSpeciesBinding? = null
    private val binding get() = _binding!!

    private val speciesController = SpeciesController(SpeciesRepository())
    private val speciesList = mutableListOf<SpeciesModel>()
    private lateinit var speciesAdapter: SpeciesAdapter

    private var currentPage = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpeciesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadSpecies(currentPage)
        return binding.root
    }

    private fun setupRecyclerView() {
        speciesAdapter = SpeciesAdapter(speciesList) { selectedSpecies ->
            navigateToDetail(selectedSpecies)
        }

        binding.recyclerViewSpecies.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewSpecies.adapter = speciesAdapter

        binding.recyclerViewSpecies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItemPosition >= totalItemCount - 1) {
                    currentPage++
                    loadSpecies(currentPage)
                }
            }
        })
    }

    private fun loadSpecies(page: Int) {
        isLoading = true
        lifecycleScope.launch {
            speciesController.fetchSpecies(page,
                onSuccess = { species ->
                    speciesList.addAll(species)
                    speciesAdapter.notifyDataSetChanged()
                    isLoading = false
                },
                onError = { error ->
                    Toast.makeText(context, "Erro ao carregar espécies: ${error.message}", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
            )
        }
    }

    private fun navigateToDetail(speciesModel: SpeciesModel) {
        Log.d("SpeciesFragment", "Entrou no navigateToDetail")
        val intent = Intent(requireContext(), DetailItemActivity::class.java).apply {
            putExtra(DetailItemActivity.ITEM_ID, speciesModel.id)
            putExtra(DetailItemActivity.ITEM_TYPE, "species")
            Log.d("SpeciesFragment", "ITEM_ID: ${speciesModel.id}, ITEM_TYPE: species")
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
