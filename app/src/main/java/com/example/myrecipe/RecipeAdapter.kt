package com.example.myrecipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Recipe
import com.squareup.picasso.Picasso

class RecipeAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val ivRecipeImage: ImageView = itemView.findViewById(R.id.ivRecipeImage)
        private val tvRecipeName: TextView = itemView.findViewById(R.id.tvRecipeName)
        private val buttonDeleteRecipe: Button = itemView.findViewById(R.id.buttonDeleteRecipe)

        init {
            itemView.setOnClickListener(this)
            buttonDeleteRecipe.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val recipe = getItem(position)
                    listener.onDeleteClick(recipe)
                }
            }
        }

        fun bind(recipe: Recipe) {
            tvRecipeName.text = recipe.namamasakan
            // Load image using Picasso
            Picasso.get().load(recipe.gambar).placeholder(R.drawable.placeholder_image).into(ivRecipeImage)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val recipe = getItem(position)
                listener.onItemClick(recipe)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(recipe: Recipe)
        fun onDeleteClick(recipe: Recipe)
    }

    class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}




