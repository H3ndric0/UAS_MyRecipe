package com.example.myrecipe

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Recipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), RecipeAdapter.OnItemClickListener {

    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonAddRecipe: Button
    private val apiService = ApiService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        buttonAddRecipe = findViewById(R.id.buttonAddRecipe)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter(this)
        recyclerView.adapter = recipeAdapter

        buttonAddRecipe.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchRecipes()
    }

    private fun fetchRecipes() {
        apiService.getRecipes().enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                if (response.isSuccessful) {
                    recipeAdapter.submitList(response.body())
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    override fun onItemClick(recipe: Recipe) {
        val intent = Intent(this, EditRecipeActivity::class.java)
        intent.putExtra("recipe", recipe)
        startActivity(intent)
    }

    override fun onDeleteClick(recipe: Recipe) {
        showDeleteConfirmationDialog(recipe)
    }

    private fun showDeleteConfirmationDialog(recipe: Recipe) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah anda yakin menghapus resep ini?")
            .setPositiveButton("Ya") { dialog, id ->
                deleteRecipe(recipe)
            }
            .setNegativeButton("Tidak") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun deleteRecipe(recipe: Recipe) {
        apiService.deleteRecipe(recipe.id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    fetchRecipes()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
