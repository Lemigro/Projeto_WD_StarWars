package com.example.starwarsapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.FragmentPlanetsBinding
import com.example.starwarsapp.controller.PlanetsController
import com.example.starwarsapp.model.PlanetsModel
import com.example.starwarsapp.repository.PlanetsRepository
import com.example.starwarsapp.view.activity.DetailItemActivity
import com.example.starwarsapp.view.adapter.PlanetsAdapter

class PlanetsFragment : Fragment() {

    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    private val planetsController = PlanetsController(PlanetsRepository())
    private lateinit var planetsAdapter: PlanetsAdapter
    private val planetsList = mutableListOf<PlanetsModel>()
    private var currentPage = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadPlanets(currentPage)
        return binding.root
    }

    private fun setupRecyclerView() {
        planetsAdapter = PlanetsAdapter(planetsList) { selectedPlanet ->
            navigateToDetail(selectedPlanet)
        }

        binding.recyclerViewPlanets.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewPlanets.adapter = planetsAdapter

        binding.recyclerViewPlanets.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItemPosition >= totalItemCount - 1) {
                    currentPage++
                    loadPlanets(currentPage)
                }
            }
        })
    }

    private fun loadPlanets(page: Int) {
        isLoading = true
        planetsController.fetchPlanets(
            page,
            onSuccess = { planets ->
                planetsList.addAll(planets)
                planetsAdapter.notifyDataSetChanged()
                isLoading = false
            },
            onError = { error ->
                Toast.makeText(context, "Erro ao carregar planetas: ${error.message}", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        )
    }

    private fun navigateToDetail(planetsModel: PlanetsModel) {
        val intent = Intent(requireContext(), DetailItemActivity::class.java).apply {
            putExtra("ITEM_NAME", planetsModel.name)
            putExtra("ITEM_URL", planetsModel.url)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
