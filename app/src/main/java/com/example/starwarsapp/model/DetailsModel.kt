package com.example.starwarsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsModel(
    val name: String?,
    val description: String?,
    val imageUrl: String?,
    val additionalData: Map<String, String?>
) : Parcelable
