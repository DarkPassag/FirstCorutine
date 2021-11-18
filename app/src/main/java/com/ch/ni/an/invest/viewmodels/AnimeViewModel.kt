package com.ch.ni.an.invest.viewmodels


import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ch.ni.an.invest.model.*
import com.ch.ni.an.invest.model.retrofit.Common
import com.ch.ni.an.invest.model.retrofit.RetrofitService
import com.ch.ni.an.invest.viewmodels.STATE.*
import com.ch.ni.an.invest.model.room.AnimeDatabase
import com.ch.ni.an.invest.utills.PAGE_SIZE
import com.ch.ni.an.invest.utills.SEARCH_BY_CHARACTER
import com.ch.ni.an.invest.utills.SEARCH_BY_TITLE
import com.ch.ni.an.invest.utills.StateListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception


class AnimeViewModel: ViewModel() {


    private val database = AnimeDatabase.getDatabase().animeDao()

    private var retrofit :RetrofitService


    private val _allNames :MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val allNames :LiveData<List<String>> = _allNames
    private var listName :List<String> = emptyList()

    private val _listAvailableAnime = MutableLiveData<List<String>>()
    val listAvailableAnime :LiveData<List<String>> = _listAvailableAnime

    private val _randomQuotes = MutableLiveData<List<AnimeChan>>()
    val randomQuotes :LiveData<List<AnimeChan>> = _randomQuotes


    val character :MutableLiveData<String> = MutableLiveData()

    private var listAnime = emptyList<String>()

    var tempList :MutableList<String> = mutableListOf()

    private val _state = MutableLiveData<STATE>()
    val state :LiveData<STATE> = _state

    init {
        getAvailableAnimeList()
        getRandomQuotes()
        getCharacters()
        retrofit = Common.retrofit
    }


    fun getRandomQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(PENDING)
            getQuotes()
        }
    }

    fun search(query :String, key :String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (key) {
                SEARCH_BY_TITLE -> {
                    listAnime.forEach {
                        if (it.contains(query, ignoreCase = true)) {
                            tempList.add(it)
                            _listAvailableAnime.postValue(tempList)
                        }
                    }
                }
                SEARCH_BY_CHARACTER -> {
                    listName.forEach {
                        if (it.contains(query, ignoreCase = true)) {
                            tempList.add(it)
                            _allNames.postValue(tempList)

                        }
                    }
                }
            }

        }
    }

    fun getQuotesByTitle(title :String) :Flow<PagingData<AnimeChan>> {
        return Pager(PagingConfig(10, 8, enablePlaceholders = true, PAGE_SIZE * 2 ),
            pagingSourceFactory = {
                AnimeNamePagerSource(retrofit, title, database)
            }).flow
    }

    fun getQuotesByAnimeCharacter(character :String) : Flow<PagingData<AnimeChan>> {
        return Pager(PagingConfig(10, 8, enablePlaceholders = true, PAGE_SIZE * 2 ),
            pagingSourceFactory = {
                AnimeCharacterPageSource(retrofit, character, object : StateListener{
                    override fun invoke(state :STATE) {
                        _state.postValue(state)
                    }

                })
            }).flow
    }





    fun getAvailableAnimeList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Common.retrofit.getAvailableAnime()
                if (response.isSuccessful) {
                    val availableAnime = response.body()
                    _listAvailableAnime.postValue(availableAnime)
                    listAnime = response.body()!!
                    _state.postValue(SUCCESS)
                } else {
                    val tempList = database.getAvailableTitleAnime()
                    Log.e("TempList", "${tempList}")
                    _listAvailableAnime.postValue(tempList)
                    _state.postValue(SUCCESS)
                }

            } catch (e :Exception) {
                val tempList = database.getAvailableTitleAnime()
                _listAvailableAnime.postValue(tempList)
                listAnime = tempList
                _state.postValue(SUCCESS)
            }
        }

    }

    fun addQuote(quote :FavouriteAnimeChan) {
        viewModelScope.launch(Dispatchers.IO) {
            val index =
                database.loadFavouriteQuotes().indexOfFirst { it.quote == quote.quote }
            if (index == -1) database.insertQuote(quote)
        }
    }


    fun deleteQuote1(quote :FavouriteAnimeChan) {
        viewModelScope.launch(Dispatchers.IO) {
            val index =
                database.loadFavouriteQuotes().indexOfFirst { it.quote == quote.quote }
            if (index != -1) database.deleteQuoteByQuote(quote.quote)
        }
    }


    fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val allCharacters = database.getNameCharacters()
            listName = allCharacters
            _allNames.postValue(allCharacters)
        }
    }


    suspend fun getUrlForLoad(characterName :String) :String {
        return try {
            val url = database.getURL(characterName)
            Log.e("URL", url.toString())
            database.getURL(characterName)
        } catch (e :Exception) {
            Log.e("BadQuery", e.toString())
            "null"
        }
    }

    private suspend fun getUrlWithNetwork(character :String):String{

        return try {
            val paramObjects = JSONObject()
            paramObjects.put("query",
                "query { Character (search: \\\"$character\\\") { name { full native } image { large } } }")

            retrofit.
        }
    }

    private suspend fun getQuotes() {
        try {
            val response = Common.retrofit.getRandomQuotes()
            if (response.isSuccessful) {
                val quotes = response.body()
                _randomQuotes.postValue(quotes)
                _state.postValue(SUCCESS)
            } else {
                val errorResponse = response.errorBody().toString()
                Log.e("ErrorResponse", errorResponse)
                _state.postValue(FAIL)
            }
        } catch (e :Exception) {
            _state.postValue(FAIL)
            Log.e("Exception", e.toString())
        }
    }




//    private suspend fun getImage(characterName :String): String {
//        try {
//            val paramObject = JSONObject()
//            paramObject.put(
//                "query",
//                "query { Character (search: \"$characterName\") { name { full native } image { large } } }"
//            )
//            val dataAnimeList = CommonGraphQL.dataAnimeList.getCharByName(paramObject.toString())
//
//            val data = JSONObject(dataAnimeList).optString("data")
//            val character = JSONObject(data).optString("Character")
//            val image = JSONObject(character).optString("image")
//            val name = JSONObject(character).optString("name")
//            val large = JSONObject(image).optString("large")
//            val full = JSONObject(name).optString("full")
//            val native = JSONObject(name).optString("native")
//
//
//            val animeName = NameCharacter(full, native)
//            val animeImage = PhotoCharacter(large)
//            val animeCharacter = AnimePerson(animeName, animeImage)
//            Log.e("handleParse", animeCharacter.toString())
//            return large
//        } catch (e:Exception){
//            Log.e("Invalid query", e.toString())
//            return ""
//        }
//
//    }







}

enum class STATE{
    PENDING, SUCCESS, FAIL
}
