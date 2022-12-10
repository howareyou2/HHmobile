package com.example.a4cutdiary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        String imagePath = getIntent().getStringExtra("imagePath");

        ImageView imageView = findViewById(R.id.imageView);
        Button submitButton = findViewById(R.id.button);

        Glide.with(getApplicationContext()).load(imagePath).into(imageView);

        // set listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}