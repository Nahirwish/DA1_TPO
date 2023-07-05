package com.example.da1_tpo.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.da1_tpo.data.ArtistsRepository
import com.example.da1_tpo.model.Artist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.net.URL
import kotlin.coroutines.CoroutineContext


class MainViewModel: ViewModel() {
    private val TAG ="API_artistas"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("Artistas")
    private val scope = CoroutineScope(coroutineContext)

    private val repoArtist = ArtistsRepository()

    var artists = MutableLiveData<ArrayList<Artist>>()
    var name = "nombre"
    var picture="https://e-cdns-images.dzcdn.net/images/artist/94c0ac2bbae9b1e7f18b70e84c6c21db/1000x1000-000000-80-0-0.jpg"



    fun onStart(context: Context){
        scope.launch {
            kotlin.runCatching {
                repoArtist.getArtist(name, picture)
            }.onSuccess {
                Log.d(TAG, "Artists onSucces")
                artists.postValue(it)
            }.onFailure {
                Log.e(TAG, "Artist Error" + it)
            }

        }
    }
}