//package com.example.starwarsapp.view.activity
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.bumptech.glide.Glide
//import com.example.starwarsapp.R
//import com.example.starwarsapp.databinding.ActivityDetailItemBinding
//import com.example.starwarsapp.controller.*
//import com.example.starwarsapp.model.*
//import com.example.starwarsapp.repository.*
//
//class DetailItemActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityDetailItemBinding
//    private var isFavorite: Boolean = false
//    private lateinit var favoritesController: FavoritesController
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDetailItemBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val favoritesRepository = FavoritesRepository()
//        favoritesController = FavoritesController(favoritesRepository)
//
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.title = "Detalhes"
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        val itemType = intent.getStringExtra("ITEM_TYPE") ?: ""
//        val itemId = intent.getStringExtra("ITEM_ID") ?: ""
//
//        loadItemDetails(itemType, itemId)
//
//        binding.fabFavorite.setOnClickListener {
//            toggleFavorite(itemId, itemType)
//        }
//    }
//
//    private fun loadItemDetails(itemType: String, itemId: String) {
//        when (itemType) {
//            "vehicle" -> {
//                val vehiclesController = VehiclesController(VehiclesRepository())
//                vehiclesController.getVehicleDetails(itemId,
//                    onSuccess = { vehicle -> displayVehicleDetails(vehicle) },
//                    onError = { error -> showToast("Erro ao carregar os detalhes: ${error.message}") }
//                )
//            }
//            "people" -> {
//                val peoplesController = PeoplesController(PeoplesRepository())
//                peoplesController.getPeopleDetails(itemId,
//                    onSuccess = { people -> displayPeopleDetails(people) },
//                    onError = { error -> showToast("Erro ao carregar os detalhes: ${error.message}") }
//                )
//            }
//            "starship" -> {
//                val starshipsController = StarshipsController(StarshipsRepository())
//                starshipsController.getStarshipDetails(itemId,
//                    onSuccess = { starship -> displayStarshipDetails(starship) },
//                    onError = { error -> showToast("Erro ao carregar os detalhes: ${error.message}") }
//                )
//            }
//            "film" -> {
//                val filmsController = FilmsController(FilmsRepository())
//                filmsController.getFilmDetails(itemId,
//                    onSuccess = { film -> displayFilmDetails(film) },
//                    onError = { error -> showToast("Erro ao carregar os detalhes: ${error.message}") }
//                )
//            }
//            "species" -> {
//                val speciesController = SpeciesController(SpeciesRepository())
//                speciesController.getSpeciesDetails(itemId,
//                    onSuccess = { species -> displaySpeciesDetails(species) },
//                    onError = { error -> showToast("Erro ao carregar os detalhes: ${error.message}") }
//                )
//            }
//            "planet" -> {
//                val planetsController = PlanetsController(PlanetsRepository())
//                planetsController.getPlanetDetails(itemId,
//                    onSuccess = { planet -> displayPlanetDetails(planet) },
//                    onError = { error -> showToast("Erro ao carregar os detalhes: ${error.message}") }
//                )
//            }
//            else -> showToast("Tipo de item desconhecido")
//        }
//    }
//
//    private fun toggleFavorite(itemId: String, itemType: String) {
//        isFavorite = !isFavorite
//
//        if (isFavorite) {
//            binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
//            showToast("Adicionado aos favoritos")
//
//            val favorite = FavoritesModel(itemId, itemType)
//            favoritesController.saveFavorites("userId", favorite,
//                onSuccess = { showToast("Favorito salvo com sucesso") },
//                onFailure = { error -> showToast("Erro ao salvar favorito: $error") }
//            )
//        } else {
//            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
//            showToast("Removido dos favoritos")
//
//            val favorite = FavoritesModel(itemId, itemType)
//            favoritesController.removeFavorites("userId", favorite,
//                onSuccess = { showToast("Favorito removido com sucesso") },
//                onFailure = { error -> showToast("Erro ao remover favorito: $error") }
//            )
//        }
//    }
//
//    private fun displayVehicleDetails(vehiclesModel: VehiclesModel) {
//        binding.itemName.text = vehiclesModel.name
//        binding.itemDescription.text = vehiclesModel.model
//    }
//
//    private fun displayPeopleDetails(peoplesModel: PeoplesModel) {
//        binding.itemName.text = peoplesModel.name
//        binding.itemDescription.text = peoplesModel.mass
//    }
//
//    private fun displayStarshipDetails(starshipsModel: StarshipsModel) {
//        binding.itemName.text = starshipsModel.name
//        binding.itemDescription.text = starshipsModel.model
//    }
//
//    private fun displayFilmDetails(filmsModel: FilmsModel) {
//        binding.itemName.text = filmsModel.title
//        binding.itemDescription.text = filmsModel.openingCrawl
//    }
//
//    private fun displaySpeciesDetails(speciesModel: SpeciesModel) {
//        binding.itemName.text = speciesModel.name
//        binding.itemDescription.text = speciesModel.classification
//    }
//
//    private fun displayPlanetDetails(planetsModel: PlanetsModel) {
//        binding.itemName.text = planetsModel.name
//        binding.itemDescription.text = planetsModel.climate
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        this.onBackPressedDispatcher.onBackPressed()
//        return true
//    }
//}



package com.example.starwarsapp.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.starwarsapp.R
import com.example.starwarsapp.databinding.ActivityDetailItemBinding
import com.example.starwarsapp.controller.PeoplesController
import com.example.starwarsapp.model.PeoplesModel
import com.example.starwarsapp.repository.PeoplesRepository
import com.example.starwarsapp.view.adapter.DetailsAdapter

class DetailItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val itemType = intent.getStringExtra("ITEM_TYPE") ?: ""
        val itemId = intent.getStringExtra("ITEM_ID") ?: ""

        if (itemType == "people") {
            loadPeopleDetails(itemId)
        } else {
            showToast("Tipo de item desconhecido!")
        }
    }

    private fun loadPeopleDetails(itemId: String) {
        val peoplesController = PeoplesController(PeoplesRepository())
        peoplesController.getPeopleDetails(itemId,
            onSuccess = { people -> displayPeopleDetails(people) },
            onError = { error -> showToast("Erro ao carregar detalhes: ${error.message}") }
        )
    }

    private fun displayPeopleDetails(peoplesModel: PeoplesModel) {
        // Configurar os detalhes principais
        binding.itemName.text = peoplesModel.name
        val description = """
            Altura: ${peoplesModel.height} cm
            Peso: ${peoplesModel.mass} kg
            Cor dos olhos: ${peoplesModel.eyeColor}
            Ano de nascimento: ${peoplesModel.birthYear}
            Gênero: ${peoplesModel.gender}
        """.trimIndent()
        binding.itemDescription.text = description

        // Carregar imagem
        Glide.with(this)
            .load(getImageUrl(peoplesModel.url))
            .placeholder(R.drawable.placeholder)
            .into(binding.itemImageBackground)

        // Configurar RecyclerView para filmes, veículos e naves
        setupRecyclerView(binding.relatedFilmsRecyclerView, peoplesModel.films, "Filmes")
        setupRecyclerView(binding.relatedVehiclesRecyclerView, peoplesModel.vehicles, "Veículos")
        setupRecyclerView(binding.relatedStarshipsRecyclerView, peoplesModel.starships, "Naves")
    }

    private fun setupRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView, items: List<String>, label: String) {
        if (items.isNotEmpty()) {
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = DetailsAdapter(items, label)
        }
    }

    private fun getImageUrl(url: String): String {
        val id = url.trimEnd('/').split("/").last()
        return "https://starwars-visualguide.com/assets/img/characters/$id.jpg"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
