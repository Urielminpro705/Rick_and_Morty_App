package com.example.rickmortyapp.models

data class CharactersResponse(
    val info: Info,
    val results: List<Character>
)