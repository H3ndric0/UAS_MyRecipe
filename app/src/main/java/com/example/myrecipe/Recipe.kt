package com.example.recipeapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    var namamasakan: String,
    var bahan: String,
    var gambar: String,
    var carapembuatan: String
) : Parcelable
