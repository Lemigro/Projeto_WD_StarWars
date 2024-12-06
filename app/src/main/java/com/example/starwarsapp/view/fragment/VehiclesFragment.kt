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
import com.example.starwarsapp.databinding.FragmentVehiclesBinding
import com.example.starwarsapp.controller.VehiclesController
import com.example.starwarsapp.model.VehiclesModel
import com.example.starwarsapp.repository.VehiclesRepository
import com.example.starwarsapp.view.activity.DetailItemActivity
import com.example.starwarsapp.view.adapter.VehiclesAdapter
import kotlinx.coroutines.launch

class VehiclesFragment : Fragment() {

    private var _binding: FragmentVehiclesBinding? = null
    private val binding get() = _binding!!

    private val vehiclesController = VehiclesController(VehiclesRepository())
    private lateinit var vehiclesAdapter: VehiclesAdapter
    private val vehiclesList = mutableListOf<VehiclesModel>()
    private var currentPage = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehiclesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadVehicles(currentPage)
        return binding.root
    }

    private fun setupRecyclerView() {
        vehiclesAdapter = VehiclesAdapter(vehiclesList) { selectedVehicle ->
            navigateToDetail(selectedVehicle)
        }

        binding.recyclerViewVehicles.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewVehicles.adapter = vehiclesAdapter

        binding.recyclerViewVehicles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItemPosition >= totalItemCount - 1) {
                    currentPage++
                    loadVehicles(currentPage)
                }
            }
        })
    }

    private fun loadVehicles(page: Int) {
        isLoading = true
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                vehiclesController.fetchVehicles(page,
                    onSuccess = { vehicles ->
                        vehiclesList.addAll(vehicles)
                        vehiclesAdapter.notifyDataSetChanged()
                        isLoading = false
                    },
                    onError = { error ->
                        showToast("Erro ao carregar veículos: ${error.message}")
                        isLoading = false
                    }
                )
            } catch (e: Exception) {
                showToast("Erro inesperado ao carregar veículos: ${e.message}")
                isLoading = false
            }
        }
    }

    private fun navigateToDetail(vehiclesModel: VehiclesModel) {
        Log.d("VehiclesFragment", "Entrou no navigateToDetail")
        val intent = Intent(requireContext(), DetailItemActivity::class.java).apply {
            putExtra(DetailItemActivity.ITEM_ID, vehiclesModel.id)
            putExtra(DetailItemActivity.ITEM_TYPE, "vehicles")
            Log.d("VehiclesFragment", "ITEM_ID: ${vehiclesModel.id}, ITEM_TYPE: vehicles")
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
