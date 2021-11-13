package com.ch.ni.an.invest.model

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ch.ni.an.invest.model.retrofit.RetrofitService
import com.ch.ni.an.invest.model.room.AnimeDao
import com.ch.ni.an.invest.utills.PAGE_SIZE
import retrofit2.HttpException

class AnimeCharacterPageSource(
    private val service :RetrofitService,
    private val query :String,
) : PagingSource<Int, AnimeChan>() {

    override fun getRefreshKey(state :PagingState<Int, AnimeChan>) :Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.nextKey?.minus(1) ?: page.prevKey?.plus(1)
    }

    override suspend fun load(params :LoadParams<Int>) :LoadResult<Int, AnimeChan> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page = params.key ?: 0

        return try {
            val response = service.getQuotesByAnimeCharacter(query, page)
            if (response.isSuccessful) {
                val quotes = checkNotNull(response.body())
                val nextKey = if (quotes.size < PAGE_SIZE) null else page + 2
                val prevKey = if (page == 0) null else page.minus(1)
                LoadResult.Page(quotes, prevKey, nextKey)
            } else LoadResult.Error(HttpException(response))
        } catch (e :Exception) {
            Log.e("Tag", e.toString())
            LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

    }
}

class AnimeNamePagerSource(
    private val service :RetrofitService,
    private val query :String,
    private val dbService :AnimeDao,
) : PagingSource<Int, AnimeChan>() {

    override fun getRefreshKey(state :PagingState<Int, AnimeChan>) :Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.nextKey?.minus(1) ?: page.prevKey?.plus(1)
    }

    override suspend fun load(params :LoadParams<Int>) :LoadResult<Int, AnimeChan> {

        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page = params.key ?: 0

        val quotes :List<AnimeChan> = dbService.getQuotesFromDB(query)
        val nexKey = if (quotes.size < PAGE_SIZE) {
           return getQuotesByNetwork(query, page, service)
        } else page + 2
        val prevKey = if (page == 0) null else page - 1

        return LoadResult.Page(quotes, prevKey, nexKey)



    }

    private suspend fun getQuotesByNetwork(query: String, page:Int, service :RetrofitService):LoadResult<Int, AnimeChan>{
       return try {
            val response = service.getQuotesByAnimeName(query, page)
            if (response.isSuccessful) {
                val quotes = checkNotNull(response.body())
                val nextKey = if (quotes.size < PAGE_SIZE) null else page + 2
                val prevKey = if (page == 0) null else page.minus(1)
                LoadResult.Page(quotes, prevKey, nextKey)
            } else LoadResult.Error(HttpException(response))
        } catch (e :Exception) {
            Log.e("Tag", e.toString())
            LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

    }
}




