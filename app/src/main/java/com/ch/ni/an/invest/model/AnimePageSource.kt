package com.ch.ni.an.invest.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ch.ni.an.invest.model.retrofit.RetrofitService
import com.ch.ni.an.invest.utills.PAGE_SIZE
import retrofit2.HttpException

class AnimeCharacterPageSource(
    private val service :RetrofitService,
    private val query: String,
    ): PagingSource<Int, AnimeChan>()
{

    override fun getRefreshKey(state :PagingState<Int, AnimeChan>) :Int? {
        val anchorPosition =state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params :LoadParams<Int>) :LoadResult<Int, AnimeChan> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page = params.key ?: 0
        val response = service.getQuotesByAnimeCharacter(query, page)

        return if (response.isSuccessful) {
            val quotes = checkNotNull(response.body())
            val nextKey = if(quotes.size < PAGE_SIZE) null else page.plus(1)
            val prevKey = if(page == 0) null else page.minus(1)
            LoadResult.Page(quotes, prevKey, nextKey)
        } else LoadResult.Error(HttpException(response))
    }
}

class AnimeNamePagerSource(
    private val service: RetrofitService,
    private val query: String
) : PagingSource<Int, AnimeChan>() {
    override fun getRefreshKey(state :PagingState<Int, AnimeChan>) :Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.nextKey?.minus(1) ?: page.prevKey?.plus(1)
    }

    override suspend fun load(params :LoadParams<Int>) :LoadResult<Int, AnimeChan> {

        if(query.isEmpty()){
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page = params.key ?: 0
        val response = service.getQuotesByAnimeName(query, page)

       return if(response.isSuccessful){
            val quotes = checkNotNull(response.body())
            val nextKey = if(quotes.size < PAGE_SIZE) 0 else page.plus(1)
            val prevKey = if( page == 0) null else page.minus(1)
            LoadResult.Page(quotes, prevKey, nextKey)
        } else LoadResult.Error(HttpException(response))

    }
}



