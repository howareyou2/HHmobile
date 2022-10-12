package com.example.a4cutdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationBarView;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    HomeFragment HFragment;
    MapFragment MFragment;
    AlbumFragment AFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        HFragment = new HomeFragment();
        MFragment = new MapFragment();
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