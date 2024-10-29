package com.example.rickmortyapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickmortyapp.R
import com.example.rickmortyapp.models.Character
import com.example.rickmortyapp.models.Episode
import com.example.rickmortyapp.services.CharacterService
import com.example.rickmortyapp.services.EpisodeService
import com.example.rickmortyapp.ui.theme.RickMortyAppTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun CharacterScreen(innerPadding : PaddingValues, characterId : Int){
    var character by remember {
        mutableStateOf<Character?>(null)
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var isLoadingEpisodes by remember {
        mutableStateOf(true)
    }

    var episodes by remember {
        mutableStateOf(listOf<Episode>())
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch {
            val base_url = "https://rickandmortyapi.com/api/"
            val characterService = Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CharacterService::class.java)
            isLoading = true
            val response = characterService.getCharacterById(id = characterId)
            isLoading = false
            character = response
        }
    }

    LaunchedEffect(key1 = character) {
        scope.launch {
            val baseUrl = "https://rickandmortyapi.com/"
            val episodeService = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EpisodeService::class.java)

            isLoadingEpisodes = true
            episodes = character?.episode?.map { url -> episodeService.getEpisodeByUrl(url) } ?: emptyList()
            isLoadingEpisodes = false
        }
    }

    if(isLoading){
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    AsyncImage(
                        model = character?.image,
                        contentDescription = character?.name,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                Box (
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary)
                ){
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp, top = 15.dp, start = 15.dp, end = 15.dp)
                    ){
                        character?.name?.let {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = it,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        character?.status?.let {
                            val textoEstilizado = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                                    append("Status: ")
                                }
                                append(it)
                            }
                            Text(
                                text = textoEstilizado,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        character?.species?.let {
                            val textoEstilizado = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                                    append("Species: ")
                                }
                                append(it)
                            }
                            Text(
                                text = textoEstilizado,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        character?.gender?.let {
                            val textoEstilizado = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                                    append("Gender: ")
                                }
                                append(it)
                            }
                            Text(
                                text = textoEstilizado,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        character?.origin?.let {
                            val textoEstilizado = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                                    append("Origin: ")
                                }
                                append(it.name)
                            }
                            Text(
                                text = textoEstilizado,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                color = MaterialTheme.colorScheme.background
                            )
                        }

                        character?.location?.let {
                            val textoEstilizado = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                                    append("Location: ")
                                }
                                append(it.name)
                            }
                            Text(
                                text = textoEstilizado,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }
            }

            item {

                Box (
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary)
                ){
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp, top = 15.dp, start = 15.dp, end = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            text = "Episodios",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.background
                        )

                        if (isLoadingEpisodes) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(top = 20.dp, bottom = 20.dp),
                                color = MaterialTheme.colorScheme.background
                            )
                        } else {
                            episodes.forEach { episode ->
                                Text(
                                    text = "- ${episode.name}",
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.background
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CharacterScreenPreview(){
    RickMortyAppTheme {
        CharacterScreen(innerPadding = PaddingValues(10.dp), characterId = 1)
    }
}