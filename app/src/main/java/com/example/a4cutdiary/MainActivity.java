package com.example.a4cutdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationBarView;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    HomeFragment HFragment;
    MapFragment MFragment;
    AlbumFragment AFragment;
    DiaryFragment DFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        HFragment = new HomeFragment();
        MFragment = new MapFragment();
        AFragment = new AlbumFragment();
        DFragment = new DiaryFragment();

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

    public void onFragmentChanged(int index){
        if(index==0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,DFragment).commit();
        }else if(index==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,AFragment).commit();
        }
    }

    public void AddNewPhoto(){
        openGallery();
    }

    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, 101);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101){
            if(resultCode==RESULT_OK){
                Uri fileUri = data.getData();

                ContentResolver resolver = getContentResolver();

                try{

                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}