package com.example.practica3galeria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int PICK_IMAGE = 100;
    Uri imageUri;
    Button btnGaleria;
    GridView gvImagenes;
    List<Uri> listaImagenes = new ArrayList<>();
    GridViewAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGaleria = findViewById(R.id.btnGaleria);
        gvImagenes = findViewById(R.id.gvImagenes);

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }
    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "SELECCIONA LAS IMAGENES"), PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ClipData clipData = data.getClipData();

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            //para cuando se seleccione una imagen
            if(clipData == null) {
                imageUri = data.getData();
                listaImagenes.add(imageUri);
            } else {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    listaImagenes.add(clipData.getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(MainActivity.this, listaImagenes);
        gvImagenes.setAdapter(baseAdapter);

    }

}
