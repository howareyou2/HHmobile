package com.example.a4cutdiary;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    HomeFragment HFragment;
    MapViewFragment MFragment;
    AlbumFragment AFragment;
    DiaryFragment DFragment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);
            HFragment = new HomeFragment();
            AFragment = new AlbumFragment();
            DFragment = new DiaryFragment();
            MFragment = new MapViewFragment();
            AFragment = new AlbumFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.container, HFragment).commit();

            NavigationBarView navigationBarView = findViewById(R.id.NavigationView_tabs);
            navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                    switch (item.getItemId()) {
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

        public void onFragmentChanged(int index) {
            if (index == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, DFragment).commit();
            } else if (index == 1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, AFragment).commit();
            }
        }


}
