package com.example.da1_tpo.data

import com.example.da1_tpo.model.Artist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL

interface ArtistsApi {
    @GET("/search")
    fun getArtists(@Query("q") name: String) : Call<ArrayList<Artist>>
}
