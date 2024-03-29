package com.techmuzz.marveluniverse.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techmuzz.marveluniverse.R;
import com.techmuzz.marveluniverse.details.DetailsFragment;
import com.techmuzz.marveluniverse.models.Character;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListFragment extends Fragment implements CharacterSelectedListener{

    @BindView(R.id.recycler_view)
    RecyclerView listView;
    @BindView(R.id.tv_error)
    TextView errorTextView;
    @BindView(R.id.loading_view)
    View loadingView;

    private Unbinder unbinder;
    private ListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        listView.setAdapter(new CharacterListAdapter(viewModel, this, this));
        listView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getCharacters().observe(this, characters -> {
            if (characters != null) {
                listView.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getError().observe(this, isError -> {
            if (isError != null) {
                errorTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                errorTextView.setText(R.string.api_error_characters);
            } else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });
        viewModel.getLoading().observe(this, isLoading -> {
            loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading) {
                errorTextView.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
            }
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

    @Override
    public void onCharacterSlected(Character character) {
        SelectedCharacterViewModel selectedCharacterViewModel = ViewModelProviders.of(getActivity())
                .get(SelectedCharacterViewModel.class);
        selectedCharacterViewModel.setSelectedCharacter(character);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.screen_container, new DetailsFragment())
                .addToBackStack(null)
                .commit();
    }
}
