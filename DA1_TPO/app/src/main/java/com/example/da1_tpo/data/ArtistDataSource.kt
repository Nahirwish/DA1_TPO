package com.example.da1_tpo.data

import android.util.Log
import com.example.da1_tpo.model.Artist

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class ArtistDataSource {
    private val BASE_URL = "https://api.deezer.com/genre/464/artists"
    private val TAG = "API-DEMO"
    suspend fun getAllArtists(name: String, picture:URL): ArrayList<Artist> {
        Log.d(TAG, "Artistas DataSource Get")

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ArtistsApi::class.java)

        var result = api.getArtists(name, picture).execute()

        return if (result.isSuccessful) {
            Log.d(TAG, "Resultado Exitoso")
            result.body() ?: ArrayList<Artist>()
        } else {
            Log.e(TAG, "Error en llamado API: " + result.message())
            ArrayList<Artist>()
        }


    }
}