package com.example.rickmortyapp.services

import com.example.rickmortyapp.models.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface EpisodeService {
    @GET
    suspend fun getEpisodeByUrl(@Url url : String) : Episode
}