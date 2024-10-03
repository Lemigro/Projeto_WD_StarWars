package com.example.starwarsapp.repository

import com.google.firebase.database.*
import com.example.starwarsapp.adapter.Favorites
import com.example.starwarsapp.adapter.FavoritesAdapter



class FavoritesRepository {

    // Inicializando a instância do Firebase Realtime Database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val favoritesRef: DatabaseReference = database.getReference("favorites")

    // Função para salvar um favorito no Realtime Database
    fun saveFavorites(userId: String, favorite: Favorites, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        // Gera uma chave única para cada favorito
        val favoritesId = favoritesRef.push().key ?: return

        val favoritesData = mapOf(
            "title" to favorite.title,
            "description" to favorite.description,
            "imageResId" to favorite.imageResId
        )

        // Salva o favorito no caminho: /favoritos/{userId}/{favoriteId}
        favoritesRef.child(userId).child(favoritesId).setValue(favoritesData)
            .addOnSuccessListener {
                // Se a operação foi bem-sucedida, chama o callback de sucesso
                onSuccess()
            }
            .addOnFailureListener { error ->
                // Se houve falha, chama o callback de erro com a mensagem de erro
                onFailure(error.message ?: "Erro desconhecido ao salvar favorito")
            }
    }

    // Função para carregar os favoritos do Realtime Database
    fun loadFavorites(userId: String, onSuccess: (List<Favorites>) -> Unit, onFailure: (String) -> Unit) {
        // Lê os favoritos do caminho: /favoritos/{userId}
        favoritesRef.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoritesList = mutableListOf<Favorites>()
                for (favoritesSnapshot in snapshot.children) {
                    // Extrai os dados do snapshot
                    val title = favoritesSnapshot.child("title").getValue(String::class.java) ?: ""
                    val description = favoritesSnapshot.child("description").getValue(String::class.java) ?: ""
                    val imageResId = favoritesSnapshot.child("imageResId").getValue(Int::class.java) ?: 0

                    // Cria uma instância de Favorites e adiciona à lista
                    favoritesList.add(Favorites(title, imageResId, description))
                }
                // Chama o callback de sucesso e retorna a lista de favoritos
                onSuccess(favoritesList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Em caso de falha, chama o callback de erro com a mensagem de erro
                onFailure(error.message)
            }
        })
    }
}
