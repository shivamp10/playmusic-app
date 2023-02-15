package com.example.playmusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<String> songs = new ArrayList<>();
    ArrayList<File> songsFile = new ArrayList<>();
    RecyclerViewAdapter(Context context, ArrayList<String> songsList, ArrayList<File> songsFile){
        this.context = context;
        this.songs = songsList;
        this.songsFile = songsFile;
    }
    public void setFilteredList(ArrayList<String> filteredList){
        this.songs = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("songSize", "Size "+songs.size());
        View view = LayoutInflater.from(context).inflate(R.layout.songs_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.musicIcon.setImageResource(R.drawable.music);
        holder.songName.setText(songs.get(position).toString());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Song.class);
                String currentsong = songs.get(position).toString();
                intent.putExtra("songlist",songsFile);
                intent.putExtra("currentsong",currentsong);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView musicIcon;
        TextView songName;
        public ViewHolder(@NonNull View view) {
            super(view);
            cardView = view.findViewById(R.id.songsCardView);
            musicIcon = view.findViewById(R.id.musicIcon);
            songName = view.findViewById(R.id.songName);
        }
    }
}
