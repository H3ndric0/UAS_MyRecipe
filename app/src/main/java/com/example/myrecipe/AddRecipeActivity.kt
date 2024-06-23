package com.example.myrecipe


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.Recipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextIngredients: EditText
    private lateinit var editTextImage: EditText
    private lateinit var editTextInstructions: EditText
    private lateinit var buttonAdd: Button
    private val apiService = ApiService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        editTextName = findViewById(R.id.editTextName)
        editTextIngredients = findViewById(R.id.editTextIngredients)
        editTextImage = findViewById(R.id.editTextImage)
        editTextInstructions = findViewById(R.id.editTextInstructions)
        buttonAdd = findViewById(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            val recipe = Recipe(
                id = 0,
                namamasakan = editTextName.text.toString(),
                bahan = editTextIngredients.text.toString(),
                gambar = editTextImage.text.toString(),
                carapembuatan = editTextInstructions.text.toString()
            )
            addRecipe(recipe)
        }
    }

    private fun addRecipe(recipe: Recipe) {
        apiService.addRecipe(recipe).enqueue(object : Callback<Void> {
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
