package com.example.playmusic;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<String> songs;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(this, "Developed by @shivam_p_10", Toast.LENGTH_LONG).show();


        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        ArrayList<File> songsFile = fetchSongs(Environment.getExternalStorageDirectory());
                        songs = new ArrayList<>();
                        for(int i = 0; i<songsFile.size();i++){
                            songs.add(i, songsFile.get(i).getName().replace(".mp3", ""));
                        }
                         recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,songs,songsFile);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.toolbar_layout,menu);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        };
        menu.findItem(R.id.searchIcon).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchIcon).getActionView();
        searchView.setQueryHint("Search Songs");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public ArrayList<File> fetchSongs (File file){
        ArrayList arrayList = new ArrayList();
        File[] files = file.listFiles();
        if (files != null) {
                for (File myfiles : files) {
                    if (!myfiles.isHidden() && myfiles.isDirectory()) {
                        arrayList.addAll(fetchSongs(myfiles));
                    } else {
                        if (myfiles.getName().endsWith(".mp3") && !myfiles.getName().startsWith(".")) {
                            arrayList.add(myfiles);
                        }
                    }
                }
            }
        return arrayList;
    }
    public void filterList(String text){
        ArrayList<String> filterdList = new ArrayList<>();
        for(String item: songs ){
            if(item.toString().toLowerCase().contains(text.toLowerCase())) {
                filterdList.add(item);
            }
        }
        if(filterdList.isEmpty()){
            Toast.makeText(this, "No song found", Toast.LENGTH_SHORT).show();
            recyclerViewAdapter.setFilteredList(filterdList);
        }
        else {
            recyclerViewAdapter.setFilteredList(filterdList);
        }
    }
}
