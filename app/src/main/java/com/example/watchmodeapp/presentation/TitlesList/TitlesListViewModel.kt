package com.example.watchmodeapp.presentation.TitlesList

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchmodeapp.data.WatchmodeApi
import com.example.watchmodeapp.data.models.Title
import com.example.watchmodeapp.utils.ResultState
import com.example.watchmodeapp.utils.Tabs
import com.google.gson.JsonParseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TitlesListViewModel @Inject constructor(private val api: WatchmodeApi) : ViewModel() {
    val listState = MutableStateFlow<ResultState<List<List<Title>>>?>(null)

    private var _moviesList = MutableStateFlow<List<Title>>(emptyList())

    private var _showsList = MutableStateFlow<List<Title>>(emptyList())

    val currentTab = mutableStateOf(Tabs.MOVIES)

    init {
        //update only when both lists are loaded
        _moviesList.zip(_showsList) { movies, shows ->
            if (movies.isNotEmpty() && shows.isNotEmpty()) {
                Log.d("RESULT", movies.toString())
                listState.value = ResultState.Success(listOf(movies, shows))
            }
        }.launchIn(viewModelScope)
    }

    //get movies and shows lists
    fun getLists() {
        listState.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                coroutineScope {
                    // Fetch movies and shows concurrently
                    val moviesDeferred =
                        async(Dispatchers.IO) { api.getTitles(type = "movie").titles }
                    val showsDeferred =
                        async(Dispatchers.IO) { api.getTitles(type = "tv_series").titles }

                    // Await results
                    val movies = moviesDeferred.await()
                    val shows = showsDeferred.await()

                    // Update MutableStateFlows
                    _moviesList.value = movies ?: emptyList()
                    _showsList.value = shows ?: emptyList()
                }
            } catch (e: IOException) {
                listState.value =
                    ResultState.Failure("Network error: Check your internet connection and try again")
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    400 -> "Bad Request: Error code - 400"
                    401 -> "Unauthorized: Error code - 401"
                    402 -> "Over Quota: Error code - 402"
                    404 -> "Not Found: Error code - 404"
                    429 -> "Too Many Requests: Error code - 429"
                    500 -> "Internal Server Error: Error code - 500"
                    else -> "HTTP error: ${e.message}"
                }
                listState.value = ResultState.Failure(errorMessage)
            } catch (e: JsonParseException) {
                listState.value = ResultState.Failure("Parsing error: JSON parsing failed")
            } catch (e: Exception) {
                listState.value = ResultState.Failure("Unexpected error: Something went wrong")
            }
        }
    }
}