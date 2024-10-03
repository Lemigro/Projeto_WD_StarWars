package com.example.starwarsapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.adapter.Vehicles
import com.example.starwarsapp.adapter.VehiclesAdapter
import com.example.starwarsapp.databinding.FragmentVehiclesBinding
import com.example.starwarsapp.R
import com.example.starwarsapp.activity.DetailItemActivity

class VehiclesFragment : Fragment() {

    private var _binding: FragmentVehiclesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVehiclesBinding.inflate(inflater, container, false)

        // Lista de veículos
        val vehicles = listOf(
            Vehicles("Millennium Falcon", R.drawable.millennium_falcon, "A Millennium Falcon foi uma cargueiro leve corelliana YT-1300 usada pelos contrabandistas Han Solo e Chewbacca durante a Guerra Civil Galáctica."),
            Vehicles("X-Wing", R.drawable.xwing, "O caça estelar X-wing é um nome aplicado a uma família de naves espaciais fictícias fabricadas pela Corporação Incom da franquia Star Wars."),
            Vehicles("TIE Fighter", R.drawable.tie_fighter, "Uma TIE fighter é uma nave espacial militar fictícia no universo da franquia Star Wars. Impulsionado por propulsores de íons gêmeos, TIE fighters são naves espaciais de guerra rápidas e ágeis porém frágeis utilizadas pelo Império Galáctico. ")
        )

        // Configura o RecyclerView e o Adapter
        val adapter = VehiclesAdapter(vehicles) { selectedVehicles ->
            val intent = Intent(requireContext(), DetailItemActivity::class.java)
            intent.putExtra("ITEM_NAME", selectedVehicles.name)
            intent.putExtra("ITEM_DESCRIPTION", selectedVehicles.description)
            intent.putExtra("ITEM_IMAGE_URL", selectedVehicles.imageResId)
            startActivity(intent)
        }

        // Configura o RecyclerView
        binding.recyclerViewVehicles.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewVehicles.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
