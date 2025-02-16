package org.chrontax.musicstreaming.network

import org.chrontax.musicstreaming.data.Artist
import org.chrontax.musicstreaming.data.NewArtist
import org.chrontax.musicstreaming.data.NewRelease
import org.chrontax.musicstreaming.data.NewTrack
import org.chrontax.musicstreaming.data.Release
import org.chrontax.musicstreaming.data.Track
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.uuid.Uuid

interface MusicApi {
    @GET("/music/track/{id}")
    suspend fun getTrack(@Path("id") id: Uuid): Track?

    @GET("/music/tracks/search")
    suspend fun searchTracks(
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Track>

    @POST("/music/track")
    suspend fun createTrack(track: NewTrack): Uuid

    @GET("/music/release/{id}")
    suspend fun getRelease(@Path("id") id: Uuid): Release?

    @GET("/music/releases/search")
    suspend fun searchReleases(
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Release>

    @POST("/music/release")
    suspend fun createRelease(release: NewRelease): Uuid

    @GET("/music/artist/{id}")
    suspend fun getArtist(@Path("id") id: Uuid): Artist?

    @GET("/music/artists/search")
    suspend fun searchArtists(
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Artist>

    @POST("/music/artist")
    suspend fun createArtist(artist: NewArtist): Uuid
}