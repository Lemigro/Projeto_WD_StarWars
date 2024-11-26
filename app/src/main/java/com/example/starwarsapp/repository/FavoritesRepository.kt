package com.example.starwarsapp.repository

import com.example.starwarsapp.model.FavoritesModel
import com.google.firebase.database.*

class FavoritesRepository {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val favoritesRef: DatabaseReference = database.getReference("favorites")

    fun saveFavorites(userId: String, favorite: FavoritesModel, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val favoritesId = favoritesRef.push().key ?: return

        val favoritesData = mapOf(
            "itemId" to favorite.itemId,
            "itemType" to favorite.itemType,
            "title" to favorite.title,
            "description" to favorite.description,
            "imageResId" to favorite.imageResId
        )

        favoritesRef.child(userId).child(favoritesId).setValue(favoritesData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { error ->
                onFailure(error.message ?: "Erro desconhecido ao salvar favorito")
            }
    }

    fun loadFavorites(userId: String, onSuccess: (List<FavoritesModel>) -> Unit, onFailure: (String) -> Unit) {
        favoritesRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoritesList = mutableListOf<FavoritesModel>()
                for (favoritesSnapshot in snapshot.children) {
                    val itemId = favoritesSnapshot.child("itemId").getValue(String::class.java) ?: ""
                    val itemType = favoritesSnapshot.child("itemType").getValue(String::class.java) ?: ""
                    val title = favoritesSnapshot.child("title").getValue(String::class.java) ?: ""
                    val description = favoritesSnapshot.child("description").getValue(String::class.java) ?: ""
                    val imageResId = favoritesSnapshot.child("imageResId").getValue(Int::class.java) ?: 0

                    favoritesList.add(FavoritesModel(itemId, itemType, title, imageResId, description))
                }
                onSuccess(favoritesList)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.message)
            }
        })
    }

    fun removeFavorite(userId: String, favorite: FavoritesModel, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        favoritesRef.child(userId).orderByChild("itemId").equalTo(favorite.itemId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (favoriteSnapshot in snapshot.children) {
                            favoriteSnapshot.ref.removeValue()
                                .addOnSuccessListener {
                                    onSuccess()
                                }
                                .addOnFailureListener { error ->
                                    onFailure(error.message ?: "Erro desconhecido ao remover favorito")
                                }
                        }
                    } else {
                        onFailure("Favorito não encontrado.")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }
            })
    }
}
