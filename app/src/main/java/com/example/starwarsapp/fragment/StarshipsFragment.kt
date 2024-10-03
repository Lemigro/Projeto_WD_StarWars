package com.example.starwarsapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.adapter.Starships
import com.example.starwarsapp.adapter.StarshipsAdapter
import com.example.starwarsapp.R
import com.example.starwarsapp.activity.DetailItemActivity
import com.example.starwarsapp.databinding.FragmentStarshipsBinding

class StarshipsFragment : Fragment() {

    private var _binding: FragmentStarshipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStarshipsBinding.inflate(inflater, container, false)

        // Lista de starships
        val starships = listOf(
            Starships("Millennium Falcon", R.drawable.millennium_falcon, "A nave usada por Han Solo."),
            Starships("X-Wing", R.drawable.xwing, "Um caça estelar usado pela Aliança Rebelde."),
            Starships("TIE Fighter", R.drawable.tie_fighter, "A nave de combate rápida usada pelo Império.")
        )

        // Configura o RecyclerView e o Adapter
        val adapter = StarshipsAdapter(starships) { selectedStarships ->
            val intent = Intent(requireContext(), DetailItemActivity::class.java)
            intent.putExtra("ITEM_NAME", selectedStarships.name)
            intent.putExtra("ITEM_DESCRIPTION", selectedStarships.description)
            intent.putExtra("ITEM_IMAGE_URL", selectedStarships.imageResId)
            startActivity(intent)
        }

        binding.recyclerViewStarships.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewStarships.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
