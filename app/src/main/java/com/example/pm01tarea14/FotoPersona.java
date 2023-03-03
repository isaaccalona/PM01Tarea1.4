package com.example.pm01tarea14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pm01tarea14.Transacciones.SQLConexion;
import com.example.pm01tarea14.Transacciones.Transacciones;

import java.io.ByteArrayInputStream;

public class FotoPersona extends AppCompatActivity {
    SQLConexion conexion = new SQLConexion(this, Transacciones.NameDatabase, null, 1);
    ImageView verfoto;
    Button btnvolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_persona);
        verfoto = (ImageView) findViewById(R.id.imagen);

        Bitmap recuperarFoto = buscarImagen(getIntent().getStringExtra("codigoParaFoto"));
        verfoto.setImageBitmap(recuperarFoto);
    }

    public Bitmap buscarImagen(String id) {
        SQLiteDatabase db = conexion.getWritableDatabase();

        String sql = "SELECT foto FROM persona WHERE id =" + id;
        Cursor cursor = db.rawQuery(sql, new String[] {});
        Bitmap bitmap = null;
        if(cursor.moveToFirst()){
            byte[] blob = cursor.getBlob(0);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return bitmap;
    }
}