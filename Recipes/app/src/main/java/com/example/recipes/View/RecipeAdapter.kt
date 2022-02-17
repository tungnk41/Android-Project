package com.example.recipes.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.Extension.setUrl
import com.example.recipes.Model.Entity.Recipe
import com.example.recipes.databinding.ItemListRecipeBinding

class RecipeAdapter : PagingDataAdapter<Recipe,RecipeAdapter.RecipeViewHolder>(RecipeComparator) {
    private var listRecipes : List<Recipe> = ArrayList()
    var recipeClickListener: RecipeClickListener? = null

    inner class RecipeViewHolder(val binding: ItemListRecipeBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                recipeClickListener?.onRecipeClicked(getItem(absoluteAdapterPosition) as Recipe)
            }
        }
        fun bind(recipe : Recipe){
            binding.apply {
                imgRecipe.setUrl(recipe.urlImage)
                tvRecipe.setText(recipe.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = ItemListRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    object RecipeComparator : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.name.equals(newItem.name)

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }

    interface RecipeClickListener {
        fun onRecipeClicked(recipe: Recipe)
    }
}
