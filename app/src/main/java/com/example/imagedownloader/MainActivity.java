package com.example.imagedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.imagedownloder.imageD;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Drawable myDrawable = getResources().getDrawable(R.drawable.bird);
        Bitmap bitmap = ((BitmapDrawable) myDrawable).getBitmap();
//        Bitmap bitmap = R.drawable.image;

        imageD.Downloader(this, bitmap, "Hii");
    }
}