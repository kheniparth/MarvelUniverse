package com.techmuzz.marveluniverse.networking;

import com.techmuzz.marveluniverse.models.Character;
import com.techmuzz.marveluniverse.models.Data;
import com.techmuzz.marveluniverse.models.MarvelResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static java.lang.System.currentTimeMillis;

public interface CharacterService {

    //pubkey = apikey
    String apikey = "3d78ab6c74ec2b4896f20964019c1c81";
    String privatekey = "3d7b4635ce578ece040e630a794b5d1891f2e71a";
    String ts = String.valueOf(currentTimeMillis());
    String hash = new HashGenerator().generateHash(ts, apikey, privatekey);


    @GET("/v1/public/characters")
    Call<MarvelResponse> getCharacters(@Query("apikey") String apikey,
                                       @Query("hash") String hash,
                                       @Query("ts") String ts
    );

    @GET("/v1/public/characters/{characterId}")
    Call<MarvelResponse> getCharacter(@Path("characterId") String characterId,
                                      @Query("apikey") String apikey,
                                      @Query("hash") String hash,
                                      @Query("ts") String ts
    );
}
