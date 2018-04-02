package com.techmuzz.marveluniverse.home;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techmuzz.marveluniverse.R;
import com.techmuzz.marveluniverse.models.Character;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder> {

    private final List<Character> data = new ArrayList<>();

    public CharacterListAdapter(ListViewModel viewModel, LifecycleOwner lifecycleOwner) {
        viewModel.getCharacters().observe(lifecycleOwner, characters -> {
            data.clear();
            if (characters != null) {
                data.addAll(characters);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_characters_list_item, parent, false);
        return new CharacterViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    static final class CharacterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.character_image)
        ImageView characterImageView;
        @BindView(R.id.character_name)
        TextView characterNameTextView;
        Context context;

        CharacterViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

        void bind(Character character) {
            String imageUrl = character.getThumbnail().getPath() + "/detail." + character.getThumbnail().getExtension();
            Glide.with(context).load(imageUrl).into(characterImageView);
            characterNameTextView.setText(character.getName());
        }
    }
}
