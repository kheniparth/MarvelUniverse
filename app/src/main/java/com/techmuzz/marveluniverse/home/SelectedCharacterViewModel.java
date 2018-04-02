package com.techmuzz.marveluniverse.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.techmuzz.marveluniverse.models.Character;

public class SelectedCharacterViewModel extends ViewModel {

    private final MutableLiveData<Character> selectedCharacter = new MutableLiveData<>();

    public LiveData<Character> getSelectedCharacter() {
        return selectedCharacter;
    }

    void setSelectedCharacter(Character character) {
        selectedCharacter.setValue(character);
    }
}
