package com.example.recipes.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.Model.Service.RecipeApi
import com.example.recipes.R
import com.example.recipes.ViewModel.RecipeViewModel
import com.example.recipes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<RecipeViewModel>()
    private lateinit var binding : ActivityMainBinding
    private val recipeAdapter : RecipeAdapter by lazy { RecipeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvListRecipe.layoutManager = LinearLayoutManager(this@MainActivity)
            rvListRecipe.adapter = recipeAdapter
        }

        lifecycleScope.launchWhenCreated {
            viewModel.recipes.collectLatest { pagingData ->
                recipeAdapter.submitData(pagingData)
            }
        }
    }
}