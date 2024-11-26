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
import com.example.starwarsapp.databinding.FragmentStarshipsBinding
import com.example.starwarsapp.controller.StarshipsController
import com.example.starwarsapp.model.StarshipsModel
import com.example.starwarsapp.repository.StarshipsRepository
import com.example.starwarsapp.view.activity.DetailItemActivity
import com.example.starwarsapp.view.adapter.StarshipsAdapter

class StarshipsFragment : Fragment() {

    private var _binding: FragmentStarshipsBinding? = null
    private val binding get() = _binding!!

    private val starshipsController = StarshipsController(StarshipsRepository())
    private lateinit var starshipsAdapter: StarshipsAdapter
    private val starshipList = mutableListOf<StarshipsModel>()
    private var currentPage = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStarshipsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadStarships(currentPage)
        return binding.root
    }

    private fun setupRecyclerView() {
        starshipsAdapter = StarshipsAdapter(starshipList) { selectedStarship ->
            navigateToDetail(selectedStarship)
        }

        binding.recyclerViewStarships.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewStarships.adapter = starshipsAdapter

        binding.recyclerViewStarships.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItemPosition >= totalItemCount - 1) {
                    currentPage++
                    loadStarships(currentPage)
                }
            }
        })
    }

    private fun loadStarships(page: Int) {
        isLoading = true
        starshipsController.fetchStarships(page,
            onSuccess = { starships ->
                starshipList.addAll(starships)
                starshipsAdapter.notifyDataSetChanged()
                isLoading = false
            },
            onError = { error ->
                Toast.makeText(context, "Erro ao carregar naves: ${error.message}", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        )
    }

    private fun navigateToDetail(starshipsModel: StarshipsModel) {
        val intent = Intent(requireContext(), DetailItemActivity::class.java).apply {
            putExtra("ITEM_NAME", starshipsModel.name)
            putExtra("ITEM_URL", starshipsModel.url)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
