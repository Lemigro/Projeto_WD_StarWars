package com.example.starwarsapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.starwarsapp.R
import com.example.starwarsapp.adapter.DetailsAdapter
import com.example.starwarsapp.databinding.FragmentDetailsBinding
import com.example.starwarsapp.model.DetailsModel
import android.util.Log

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("DetailsFragment", "onViewCreated chamada")
        super.onViewCreated(view, savedInstanceState)

        val details = arguments?.getParcelable<DetailsModel>("DETAILS")
        if (details != null) {
            Log.d("DetailsFragment", "Detalhes encontrados: $details")
            displayDetails(details)
        } else {
            Log.e("DetailsFragment", "Detalhes não encontrados.")
        }
    }

    private fun displayDetails(details: DetailsModel) {
        Log.d("DetailsFragment", "Exibindo detalhes: $details")
        binding.apply {
            detailsTitle.text = details.name ?: getString(R.string.no_name_available)
            detailsDescription.text = details.description ?: getString(R.string.no_description_available)

            Glide.with(this@DetailsFragment)
                .load(details.imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(detailsImage)

            if (details.additionalData.isNotEmpty()) {
                Log.d("DetailsFragment", "Dados Adicionais: ${details.additionalData.size}")
                detailsRecyclerView.adapter = DetailsAdapter(details.additionalData)
            } else {
                Log.w("DetailsFragment", "Não há dados adicionais.")
            }

            detailsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            detailsRecyclerView.adapter = DetailsAdapter(details.additionalData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
