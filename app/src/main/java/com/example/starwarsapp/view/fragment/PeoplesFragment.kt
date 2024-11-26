package com.example.starwarsapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.FragmentPeoplesBinding
import com.example.starwarsapp.controller.PeoplesController
import com.example.starwarsapp.model.PeoplesModel
import com.example.starwarsapp.repository.PeoplesRepository
import com.example.starwarsapp.view.activity.DetailItemActivity
import com.example.starwarsapp.view.adapter.PeoplesAdapter

class PeoplesFragment : Fragment() {

    private var _binding: FragmentPeoplesBinding? = null
    private val binding get() = _binding!!
    private val peoplesController = PeoplesController(PeoplesRepository())
    private val peopleList = mutableListOf<PeoplesModel>()
    private lateinit var adapter: PeoplesAdapter

    private var currentPage = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeoplesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadPeople(currentPage)
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = PeoplesAdapter(peopleList) { selectedPerson ->
            navigateToDetail(selectedPerson)
        }

        val layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewPeople.layoutManager = layoutManager
        binding.recyclerViewPeople.adapter = adapter

        binding.recyclerViewPeople.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == peopleList.size - 1) {
                    currentPage++
                    loadPeople(currentPage)
                }
            }
        })
    }

    private fun loadPeople(page: Int) {
        isLoading = true
        peoplesController.fetchPeople(page,
            onSuccess = { newPeople ->
                peopleList.addAll(newPeople)
                adapter.notifyDataSetChanged()
                isLoading = false
            },
            onError = {
                isLoading = false
            }
        )
    }

    private fun navigateToDetail(peoplesModel: PeoplesModel) {
        val intent = Intent(requireContext(), DetailItemActivity::class.java).apply {
            putExtra("ITEM_NAME", peoplesModel.name)
            putExtra("ITEM_URL", peoplesModel.url)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
