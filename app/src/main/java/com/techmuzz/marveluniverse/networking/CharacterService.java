package com.techmuzz.marveluniverse.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CharacterService {

    @GET("https://gateway.marvel.com:443/v1/public/characters?name=Iron%20man&apikey=3d78ab6c74ec2b4896f20964019c1c81")
    Call<List<Character>> getCharacters();
}
