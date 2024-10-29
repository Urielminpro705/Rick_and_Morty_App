package com.example.rickmortyapp.services

import com.example.rickmortyapp.models.Character
import com.example.rickmortyapp.models.CharactersResponse
import com.example.rickmortyapp.models.Episode
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character")
    suspend fun getCharacters() : CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id : Int) : Character
}