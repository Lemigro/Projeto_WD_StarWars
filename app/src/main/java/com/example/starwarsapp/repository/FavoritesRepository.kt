package com.example.starwarsapp.repository

import android.util.Log
import com.example.starwarsapp.model.FavoritesModel
import com.google.firebase.database.*

class FavoritesRepository {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val favoritesRef: DatabaseReference = database.getReference("favorites")

    fun saveFavorites(userId: String, favorite: FavoritesModel, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        Log.d("FavoritesRepository", "Salvando favorito: $favorite")
        favoritesRef.child(userId).child(favorite.itemId).setValue(favorite)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { error -> onFailure(error.message ?: "Erro desconhecido ao salvar favorito") }
    }

    fun loadFavorites(userId: String, onSuccess: (List<FavoritesModel>) -> Unit, onFailure: (String) -> Unit) {
        Log.d("FavoritesRepository", "Carregando favoritos para o usuário: $userId")
        favoritesRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoritesList = snapshot.children.mapNotNull {
                    Log.d("FavoritesRepository", "Item ID: ${it.key}")
                    it.getValue(FavoritesModel::class.java)
                }
                onSuccess(favoritesList)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.message)
            }
        })
    }

    fun removeFavorite(userId: String, favorite: FavoritesModel, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        Log.d("FavoritesRepository", "Removendo favorito: $favorite")
        favoritesRef.child(userId).child(favorite.itemId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { error -> onFailure(error.message ?: "Erro desconhecido ao remover favorito") }
    }
}
