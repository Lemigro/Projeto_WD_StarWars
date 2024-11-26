package com.example.starwarsapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsapp.databinding.ActivityDetailItemBinding
import com.example.starwarsapp.R
import com.example.starwarsapp.repository.FavoritesRepository
import com.example.starwarsapp.adapter.Favorites

class DetailItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailItemBinding
    private var isFavorited = false
    private lateinit var favoritesRepository: FavoritesRepository
    private var userId = "defaultUserId" // Você pode obter o userId da autenticação

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializando o repositório de favoritos
        favoritesRepository = FavoritesRepository()

        // Configurando a Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Detalhes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Recebendo os dados passados via Intent
        val itemName = intent.getStringExtra("ITEM_NAME")
        val itemDescription = intent.getStringExtra("ITEM_DESCRIPTION")
        val itemImageResId = intent.getIntExtra("ITEM_IMAGE_URL", R.drawable.luke_skywalker)

        // Atualizando os TextViews com os dados recebidos
        binding.itemName.text = itemName
        binding.itemDescription.text = itemDescription

        // Definindo a imagem de fundo
        binding.itemImageBackground.setImageResource(itemImageResId)

        // Configurando o FAB (FloatingActionButton) para favoritar/desfavoritar
        binding.fabFavorite.setOnClickListener {
            isFavorited = !isFavorited

            // Atualiza o ícone de acordo com o estado
            if (isFavorited) {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                Toast.makeText(this, "Item favoritado", Toast.LENGTH_SHORT).show()

                // Salvar o item favorito no Firebase
                val favorite = Favorites(itemName ?: "", itemImageResId, itemDescription ?: "")
                favoritesRepository.saveFavorites(
                    userId,
                    favorite,
                    onSuccess = { Toast.makeText(this, "Favorito salvo com sucesso!", Toast.LENGTH_SHORT).show() },
                    onFailure = { error -> Toast.makeText(this, "Erro ao salvar favorito: $error", Toast.LENGTH_SHORT).show() }
                )
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                Toast.makeText(this, "Item desfavoritado", Toast.LENGTH_SHORT).show()

                // Aqui você pode implementar a lógica para remover o favorito se necessário
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
