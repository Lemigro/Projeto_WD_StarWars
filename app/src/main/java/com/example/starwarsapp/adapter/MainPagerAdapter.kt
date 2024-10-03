package com.example.starwarsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.starwarsapp.fragment.*

class MainPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PeopleFragment()
            1 -> PlanetsFragment()
            2 -> VehiclesFragment()
            3 -> SpeciesFragment()
            4 -> StarshipsFragment()
            5 -> FilmsFragment()
            6 -> FavoritesFragment()
            else -> PeopleFragment()
        }
    }
}
