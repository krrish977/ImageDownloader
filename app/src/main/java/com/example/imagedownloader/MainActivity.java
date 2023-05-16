package com.example.imagedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.imagedownloder.imageD;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);

        Drawable myDrawable = getResources().getDrawable(R.drawable.bird);
        Bitmap bitmap = ((BitmapDrawable) myDrawable).getBitmap();
//        Bitmap bitmap = R.drawable.image;
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageD.Downloader(MainActivity.this, bitmap, "Downloaded");
            }
        });



    }
}