package com.example.roadmanagement.kaltara.CameraLoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;

import java.io.File;

public class Ztes extends AppCompatActivity {

    Button button;
    TextView tLatlong, tImage;
    Intent intent;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ztes);

        button = findViewById(R.id.zButton);
        tLatlong = findViewById(R.id.zLatlong);
        tImage = findViewById(R.id.zNamaImage);

        imageView = findViewById(R.id.zImage);

        intent = new Intent(Ztes.this, Cameraku.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                 tLatlong.setText(data.getStringExtra("latlong"));
                 tImage.setText(data.getStringExtra("image"));

                 File imgFile = new File(data.getStringExtra("image"));

                if(imgFile.exists()){

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    imageView.setImageBitmap(myBitmap);

                }

            }
        }
    }
}