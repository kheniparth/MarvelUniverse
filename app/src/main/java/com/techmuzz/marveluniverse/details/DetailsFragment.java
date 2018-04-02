package com.techmuzz.marveluniverse.details;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.techmuzz.marveluniverse.R;
import com.techmuzz.marveluniverse.home.SelectedCharacterViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsFragment extends Fragment {

    @BindView(R.id.character_image)
    ImageView characterImageView;
    @BindView(R.id.character_name)
    TextView characterNameTextView;
    @BindView(R.id.character_id)
    TextView characterIdTextView;
    @BindView(R.id.character_description)
    TextView characterDescTextView;
    private Unbinder unbinder;
    private SelectedCharacterViewModel selectedCharacterViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        selectedCharacterViewModel = ViewModelProviders.of(getActivity())
                .get(SelectedCharacterViewModel.class);
        selectedCharacterViewModel.restoreFromBundle(savedInstanceState);
        displayCharacter();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        selectedCharacterViewModel.saveToBundle(outState);
    }

    private void displayCharacter() {
        selectedCharacterViewModel.getSelectedCharacter().observe(this, character -> {
            String imageUrl = character.getThumbnail().getPath() + "/detail." + character.getThumbnail().getExtension();

            RequestOptions options = new RequestOptions();
            options.centerCrop();

            Glide.with(this)
                    .load(imageUrl)
                    .apply(options)
                    .into(characterImageView);
            characterNameTextView.setText(character.getName());
            characterIdTextView.setText(String.valueOf(character.getId()));
            characterDescTextView.setText(character.getDescription());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
