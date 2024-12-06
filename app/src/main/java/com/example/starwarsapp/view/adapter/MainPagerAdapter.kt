package com.example.starwarsapp.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.starwarsapp.view.fragment.*


class MainPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        const val PEOPLE_FRAGMENT_POSITION = 0
        const val PLANETS_FRAGMENT_POSITION = 1
        const val VEHICLES_FRAGMENT_POSITION = 2
        const val SPECIES_FRAGMENT_POSITION = 3
        const val STARSHIPS_FRAGMENT_POSITION = 4
        const val FILMS_FRAGMENT_POSITION = 5
        const val FAVORITES_FRAGMENT_POSITION = 6
        const val TOTAL_FRAGMENTS = 7
    }


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PEOPLE_FRAGMENT_POSITION -> PeoplesFragment()
            PLANETS_FRAGMENT_POSITION -> PlanetsFragment()
            VEHICLES_FRAGMENT_POSITION -> VehiclesFragment()
            SPECIES_FRAGMENT_POSITION -> SpeciesFragment()
            STARSHIPS_FRAGMENT_POSITION -> StarshipsFragment()
            FILMS_FRAGMENT_POSITION -> FilmsFragment()
            FAVORITES_FRAGMENT_POSITION -> FavoritesFragment()
            else -> PeoplesFragment()
        }
    }

    override fun getItemCount(): Int = TOTAL_FRAGMENTS
}
