package com.example.starwarsapp.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.starwarsapp.R
import com.example.starwarsapp.adapter.DetailsAdapter
import com.example.starwarsapp.controller.DetailsController
import com.example.starwarsapp.controller.FavoritesController
import com.example.starwarsapp.databinding.ActivityDetailItemBinding
import com.example.starwarsapp.model.DetailsModel
import com.example.starwarsapp.model.FavoritesModel
import com.example.starwarsapp.repository.DetailsRepository
import com.example.starwarsapp.repository.FavoritesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailItemBinding
    private lateinit var detailsController: DetailsController
    private lateinit var favoritesController: FavoritesController
    private var isFavorited = false
    private val userId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: throw IllegalStateException("Usuário não autenticado")

    private lateinit var currentFavorite: FavoritesModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailsController = DetailsController(DetailsRepository())
        favoritesController = FavoritesController(FavoritesRepository())
        setupToolbar()

        val itemId = intent.getStringExtra(ITEM_ID) ?: ""
        val itemType = intent.getStringExtra(ITEM_TYPE) ?: ""
        Log.d("DetailItemActivity", "itemId: $itemId, itemType: $itemType")

        if (itemId.isNotEmpty() && itemType.isNotEmpty()) {
            loadDetails(itemId, itemType)
        } else {
            Log.e("DetailItemActivity", "ITEM_ID ou ITEM_TYPE estão vazios!")
            showError("ID ou tipo do item não fornecido.")
        }
        setupFavoriteButton()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.details)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun loadDetails(itemId: String, itemType: String) {
        Log.d("DetailItemActivity", "Carregando detalhes para o $itemType com ID: $itemId")
        CoroutineScope(Dispatchers.Main).launch {
            detailsController.fetchDetails(itemId, itemType,
                onSuccess = { details ->
                    Log.d("DetailItemActivity", "Detalhes obtidos com sucesso: $details")
                    displayDetails(details, itemType)
                    checkIfFavorited(itemId) // Lembrando que essa linha crashou o programa, tirar se der erro novamente
                },
                onError = { error ->
                    Log.e("DetailItemActivity", "Erro ao tentar obter detalhes", error)
                    showError(error.message ?: "Erro desconhecido")
                }
            )
        }
    }

    private fun displayDetails(details: DetailsModel, itemType: String) {
        Log.d("DetailItemActivity", "Exibindo detalhes de $itemType: $details.name")

        binding.apply {
            itemName.text = details.name ?: getString(R.string.no_name_available)
            itemDescription.text = details.description ?: getString(R.string.no_description_available)

            if (!details.imageUrl.isNullOrEmpty()) {
                Glide.with(this@DetailItemActivity)
                    .load(details.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(itemImageBackground)
            } else {
                Log.w("DetailItemActivity", "Nenhuma URL de imagem disponível para exibição.")
                itemImageBackground.setImageResource(R.drawable.placeholder)
            }

            if (details.additionalData.isNotEmpty()) {
                Log.d("DetailItemActivity", "Exibindo dados adicionais")
                binding.additionalDetailsRecyclerView.layoutManager = LinearLayoutManager(this@DetailItemActivity)
                binding.additionalDetailsRecyclerView.adapter = DetailsAdapter(details.additionalData)
                binding.additionalDetailsRecyclerView.visibility = View.VISIBLE
            } else {
                Log.w("DetailItemActivity", "Nenhum dado adicional disponível para exibição.")
                binding.additionalDetailsRecyclerView.visibility = View.GONE
            }

            currentFavorite = FavoritesModel(
                itemId = details.name?.hashCode().toString(),
                itemType = itemType,
                title = details.name ?: "",
                description = details.description ?: "",
                imageUrl = details.imageUrl ?: ""
            )
            Log.d("DetailItemActivity", "Current favorite: $currentFavorite")
        }
    }


    private fun checkIfFavorited(itemId: String) {
        favoritesController.loadFavorites(userId,
            onSuccess = { favorites ->
                isFavorited = favorites.any { it.itemId == itemId }
                updateFavoriteIcon()
            },
            onFailure = { error ->
                Toast.makeText(this, "Erro ao verificar favoritos: $error", Toast.LENGTH_SHORT).show()
                isFavorited = false
                updateFavoriteIcon()
            }
        )
    }

    private fun setupFavoriteButton() {
        binding.fabFavorite.setOnClickListener {
            isFavorited = !isFavorited
            updateFavoriteIcon()

            if (isFavorited) {
                saveFavorite()
            } else {
                removeFavorite()
            }
        }
    }

    private fun saveFavorite() {
        favoritesController.saveFavorites(
            userId,
            currentFavorite,
            onSuccess = {
                Toast.makeText(this, "Favorito salvo com sucesso!", Toast.LENGTH_SHORT).show()
            },
            onFailure = { error ->
                Toast.makeText(this, "Erro ao salvar favorito: $error", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun removeFavorite() {
        Log.d("DetailItemActivity", "Favorito atual: $currentFavorite")
        favoritesController.removeFavorites(
            userId,
            currentFavorite,
            onSuccess = {
                Toast.makeText(this, "Favorito removido com sucesso!", Toast.LENGTH_SHORT).show()
            },
            onFailure = { error ->
                Toast.makeText(this, "Erro ao remover favorito: $error", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun updateFavoriteIcon() {
        Log.d("DetailItemActivity", "Favorito atual: $currentFavorite")
        val iconRes = if (isFavorited) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        binding.fabFavorite.setImageResource(iconRes)
    }

    private fun showError(message: String) {
        binding.apply {
            itemName.text = getString(R.string.error)
            itemDescription.text = message
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val ITEM_ID = "ITEM_ID"
        const val ITEM_TYPE = "ITEM_TYPE"
    }
}
