package com.example.myrecipe

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.Recipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditRecipeActivity : AppCompatActivity() {



    private lateinit var editTextName: EditText
    private lateinit var editTextIngredients: EditText
    private lateinit var editTextImage: EditText
    private lateinit var editTextInstructions: EditText
    private lateinit var buttonUpdate: Button
    private lateinit var recipe: Recipe
    private val apiService = ApiService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        editTextName = findViewById(R.id.editTextName)
        editTextIngredients = findViewById(R.id.editTextIngredients)
        editTextImage = findViewById(R.id.editTextImage)
        editTextInstructions = findViewById(R.id.editTextInstructions)
        buttonUpdate = findViewById(R.id.buttonUpdate)

        recipe = intent.getParcelableExtra("recipe")!!

        editTextName.setText(recipe.namamasakan)
        editTextIngredients.setText(recipe.bahan)
        editTextImage.setText(recipe.gambar)
        editTextInstructions.setText(recipe.carapembuatan)

        buttonUpdate.setOnClickListener {
            recipe.namamasakan = editTextName.text.toString()
            recipe.bahan = editTextIngredients.text.toString()
            recipe.gambar = editTextImage.text.toString()
            recipe.carapembuatan = editTextInstructions.text.toString()
            updateRecipe(recipe)
        }
    }

    private fun updateRecipe(recipe: Recipe) {
        apiService.updateRecipe(recipe.id, recipe).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    finish()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
