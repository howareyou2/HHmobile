package com.example.a4cutdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    HomeFragment HFragment;
    MapViewFragment MFragment;
    AlbumFragment AFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        HFragment = new HomeFragment();
        MFragment = new MapViewFragment();
        AFragment = new AlbumFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, HFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.NavigationView_tabs);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, HFragment).commit();
                        return true;
                    case R.id.mapButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, MFragment).commit();
                        return true;
                    case R.id.albumButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, AFragment).commit();
                        return true;
                }

                return false;
            }
        });




    }
}