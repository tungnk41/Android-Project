package com.example.recipes.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipes.Model.Entity.Recipe
import com.example.recipes.Model.Paging.RecipePagingDataSource
import com.example.recipes.Model.Service.RecipeApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(val recipeApi: RecipeApi) : ViewModel() {

    val recipes: Flow<PagingData<Recipe>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { RecipePagingDataSource(recipeApi) }).flow.cachedIn(viewModelScope)
}
