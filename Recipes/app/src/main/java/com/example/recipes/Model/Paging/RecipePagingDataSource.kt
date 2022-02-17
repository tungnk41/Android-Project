package com.example.recipes.Model.Paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipes.Model.Entity.Recipe
import com.example.recipes.Model.Service.RecipeApi
import java.lang.Exception

class RecipePagingDataSource(private val service : RecipeApi) : PagingSource<Int,Recipe>() {
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return 1; //When refresh, load page 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val pageNumber = params.key ?: 1
        val response = service.getAllRecipe(pageNumber)
        val pagedResponse = response.body()
        val data = pagedResponse?.results


        //Get next page number
        var nextPageNumber : Int? = null
        if(pagedResponse?.pageInfo?.next != null){
            val uri = Uri.parse(pagedResponse.pageInfo.next)
            val nextPageQuery = uri.getQueryParameter("page")
            nextPageNumber = nextPageQuery?.toInt()
        }

        return try {
            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}