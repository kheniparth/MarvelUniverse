package com.techmuzz.marveluniverse.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.techmuzz.marveluniverse.models.Character;
import com.techmuzz.marveluniverse.networking.CharacterApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewModel extends ViewModel {

    private final MutableLiveData<List<Character>> characters = new MutableLiveData<>();
    private final MutableLiveData<Boolean> characterLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private Call<List<Character>> characterCall;

    public ListViewModel() {
        fetchCharacters();
    }

    LiveData<List<Character>> getCharacters() {
        return characters;
    }

    LiveData<Boolean> getError() {
        return characterLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    private void fetchCharacters() {
        loading.setValue(true);
        characterCall = CharacterApi.getInstance().getCharacters();
        characterCall.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                characterLoadError.setValue(false);
                characters.setValue(response.body());
                loading.setValue(false);
                characterCall = null;
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                characterLoadError.setValue(true);
                loading.setValue(false);
                characterCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (characterCall != null) {
            characterCall.cancel();
            characterCall = null;
        }
    }
}
