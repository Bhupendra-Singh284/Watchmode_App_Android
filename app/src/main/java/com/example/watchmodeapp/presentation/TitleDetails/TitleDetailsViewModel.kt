package com.example.watchmodeapp.presentation.TitleDetails

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchmodeapp.data.WatchmodeApi
import com.example.watchmodeapp.data.models.TitleDetails
import com.example.watchmodeapp.utils.ResultState
import com.google.gson.JsonParseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TitleDetailsViewModel @Inject constructor(
    private val api: WatchmodeApi
) : ViewModel() {

    private val _detailsState = MutableStateFlow<ResultState<TitleDetails>?>(null)
    val detailsState: StateFlow<ResultState<TitleDetails>?> = this._detailsState

    var imageFile: File? = null
        private set

    fun getTitleDetails(titleId: Int, context: Context) {
        this._detailsState.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = api.getTitleDetails(titleId)
                imageFile = downloadImage(result.title ?: "", context, result.posterLarge ?: "")
                withContext(Dispatchers.Main) {
                    _detailsState.value = ResultState.Success(result)
                }
            } catch (e: IOException) {
                _detailsState.value =
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
                _detailsState.value = ResultState.Failure(errorMessage)
            } catch (e: JsonParseException) {
                _detailsState.value = ResultState.Failure("Parsing error: JSON parsing failed")
            } catch (e: Exception) {
                _detailsState.value = ResultState.Failure("Unexpected error: Something went wrong")
            }
        }
    }

    private suspend fun downloadImage(fileName: String, context: Context, imageUrl: String): File? {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(context.cacheDir, "${fileName}.jpg")

                val client = OkHttpClient()
                val request = Request.Builder().url(imageUrl).build()
                val response = client.newCall(request).execute()

                if (!response.isSuccessful) return@withContext null

                val inputStream = response.body?.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)

                FileOutputStream(file, false).use { output ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                }
                file
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    //delete file when navigating back
    fun deleteDownloadedImage() {
        imageFile?.let {
            if (it.exists()) {
                it.delete()
            }
        }
    }
}