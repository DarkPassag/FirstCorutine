package com.ch.ni.an.invest.model.retrofit


import android.util.Log
import androidx.lifecycle.*
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.AnimePerson
import com.ch.ni.an.invest.model.NameCharacter
import com.ch.ni.an.invest.model.PhotoCharacter
import com.ch.ni.an.invest.model.retrofit.STATE.*
import com.ch.ni.an.invest.model.room.AnimeDatabase
import com.ch.ni.an.invest.roomAnimeChar.DatabaseCharacterAnime
import com.ch.ni.an.invest.utills.SEARCH_BY_CHARACTER
import com.ch.ni.an.invest.utills.SEARCH_BY_TITLE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

class AnimeViewModel: ViewModel() {


    private val database = AnimeDatabase.get()
    private val secondDatabase = DatabaseCharacterAnime.get()

    private val _allNames: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val allNames:LiveData<List<String>> = _allNames
    private var listName: List<String> = emptyList()

    private val _listAvailableAnime = MutableLiveData<List<String>>()
    val listAvailableAnime: LiveData<List<String>> = _listAvailableAnime

    private val _randomQuotes = MutableLiveData<List<AnimeChan>>()
    val randomQuotes: LiveData<List<AnimeChan>> = _randomQuotes


    private val _quotesByTitle = MutableLiveData<List<AnimeChan>>()
    val quotesByTitle: LiveData<List<AnimeChan>> = _quotesByTitle

    private val _quotesByCharacter = MutableLiveData<List<AnimeChan>>()
    val quotesByCharacter: LiveData<List<AnimeChan>> = _quotesByCharacter


    private var listAnime = emptyList<String>()


    var tempList: MutableList<String> = mutableListOf()


    private val _state = MutableLiveData<STATE>()
    val state: LiveData<STATE> = _state

    init {
        getAvailableAnimeList()
        getRandomQuotes()
        getCharacters()
    }


    fun getRandomQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(PENDING)
            getQuotes()
        }
    }

    fun search(query: String, key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(key){
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

    fun getQuotesByAnime(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(PENDING)
            getQuotesByAnimeTitle(url)
        }
    }

    fun getQuotesByAnimeCharacter(name: String){
        viewModelScope.launch(Dispatchers.IO){
            _state.postValue(PENDING)
            quotesByAnimeCharacter(name)
        }
    }


    fun getAvailableAnimeList() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Common.retrofit.getAvailableAnime()
                if (response.isSuccessful) {
                    val availableAnime = response.body()
                    _listAvailableAnime.postValue(availableAnime)
                    listAnime = response.body()!!
                    _state.postValue(SUCCESS)
                } else {
                    val error = response.errorBody()
                    Log.e("ERROR", "$error")
                }

            } catch (e: Exception) {
                Log.e("TAG", "$e")
                _state.postValue(FAIL)
            }
        }

    }

    fun addQuote(quote :AnimeChan){
        viewModelScope.launch(Dispatchers.IO){
            database.animeDao().insertQuote(quote)
        }
    }

    fun deleteQuote(quote :AnimeChan){
        viewModelScope.launch(Dispatchers.IO){
            database.animeDao().deleteQuote(quote)
        }
    }



    suspend fun getImage(characterName :String): String {
        try {
            val paramObject = JSONObject()
            paramObject.put(
                "query",
                "query { Character (search: \"$characterName\") { name { full native } image { large } } }"
            )
            val dataAnimeList = CommonGraphQL.dataAnimeList.getCharByName(paramObject.toString())

            val data = JSONObject(dataAnimeList).optString("data")
            val character = JSONObject(data).optString("Character")
            val image = JSONObject(character).optString("image")
            val name = JSONObject(character).optString("name")
            val large = JSONObject(image).optString("large")
            val full = JSONObject(name).optString("full")
            val native = JSONObject(name).optString("native")


            val animeName = NameCharacter(full, native)
            val animeImage = PhotoCharacter(large)
            val animeCharacter = AnimePerson(animeName, animeImage)
            Log.e("handleParse", animeCharacter.toString())
            return large
        } catch (e:Exception){
            Log.e("Invalid query", e.toString())
            return ""
        }

    }

    fun getCharacters(){
        viewModelScope.launch(Dispatchers.IO){
            val a = secondDatabase.CharactersDao().getAllCharacter()
            listName = a
            _allNames.postValue(a)
        }
    }


    suspend fun getUrlForLoad(characterName :String): String{
        return getImage(characterName)
    }

    private suspend fun getQuotes(){
        try {
            val response = Common.retrofit.getRandomQuotes()
            if(response.isSuccessful){
                val quotes = response.body()
                _randomQuotes.postValue(quotes)
                _state.postValue(SUCCESS)
            } else {
                val errorResponse = response.errorBody().toString()
                Log.e("ErrorResponse", errorResponse )
                _state.postValue(FAIL)
            }
        } catch (e:Exception){
            _state.postValue(FAIL)
            Log.e("Exception", e.toString())
        }
    }



    private suspend fun quotesByAnimeCharacter(name: String){
        try {
            val response = Common.retrofit.getQuotesByAnimeCharacter(name)
            if(response.isSuccessful){
                val listQuotes = response.body()
                _quotesByCharacter.postValue(listQuotes)
                _state.postValue(SUCCESS)
            } else {
                val errorResponse = response.errorBody().toString()
                Log.e("ErrorResponse", errorResponse )
                _state.postValue(FAIL)
            }
        } catch (e:Exception){
            _state.postValue(FAIL)
            Log.e("Exception", e.toString())
        }
    }


    private suspend fun getQuotesByAnimeTitle(url: String) {
        try {
            val response = Common.retrofit.getQuotesByAnimeName(url)
            if(response.isSuccessful){
                val quotes = response.body()
                _quotesByTitle.postValue(quotes)
                _state.postValue(SUCCESS)
            } else {
                val error = response.errorBody()
                Log.e("ERROR", "$error")
            }
        } catch (e: Exception) {
            _state.postValue(FAIL)
        }
    }



}

enum class STATE{
    PENDING, SUCCESS, FAIL
}
