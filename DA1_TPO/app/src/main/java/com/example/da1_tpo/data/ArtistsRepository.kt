package com.example.da1_tpo.data
import com.example.da1_tpo.model.Artist
import java.net.URL


class ArtistsRepository {
    private val artistDS = ArtistDataSource()

    suspend fun getArtist(name: String, picture: URL) : ArrayList<Artist> {
        return artistDS.getAllArtists(name,picture)
    }
}