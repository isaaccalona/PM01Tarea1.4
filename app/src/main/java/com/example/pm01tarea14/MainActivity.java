package com.example.pm01tarea14;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm01tarea14.Transacciones.SQLConexion;
import com.example.pm01tarea14.Transacciones.Transacciones;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLConexion conexion = new SQLConexion(this, Transacciones.NameDatabase,null,1);
    SQLiteDatabase db;

    EditText nombre, descripcion;
    ImageView foto;
    Button btnTomarFoto,btnguardar,btnlista;

    static final int PETICION_ACCESO_CAM = 100;
    static final int TAKE_PIC_REQUEST = 101;
    Bitmap imagen;

    ArrayList<String> lista_paises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (EditText) findViewById(R.id.txtNombre);
        descripcion = (EditText) findViewById(R.id.txtdescripcion);
        foto = (ImageView) findViewById(R.id.foto);
        btnTomarFoto = (Button) findViewById(R.id.btnfoto);
        btnguardar = (Button) findViewById(R.id.btnsqlite);
        btnlista = (Button) findViewById(R.id.btnlistap);


        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListaPersonas.class);
                startActivity(intent);
            }
        });

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    validarDatos();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Debe de tomarse una foto ",Toast.LENGTH_LONG).show();
                }

            }
        });
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
         }
        });
    }
    private void permisos() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PETICION_ACCESO_CAM);
        }else{
            tomarFoto();
        }
    }
    private void tomarFoto() {
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takepic.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takepic,TAKE_PIC_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requescode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requescode, resultCode, data);

        if(requescode == TAKE_PIC_REQUEST && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            imagen = (Bitmap) extras.get("data");
            foto.setImageBitmap(imagen);
        }else if (resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            foto.setImageURI(imageUri);
        }

    }
    private void validarDatos() {
        if (nombre.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Debe de escribir un nombre" ,Toast.LENGTH_LONG).show();
        }else if (descripcion.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Debe de escribir una descripcion" ,Toast.LENGTH_LONG).show();
        }else{
            guardarpersona(imagen);
        }
    }
    private void guardarpersona(Bitmap bitmap) {

        db = conexion.getWritableDatabase();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] ArrayFoto  = stream.toByteArray();

        ContentValues valores = new ContentValues();

        valores.put(Transacciones.nombreCompleto, nombre.getText().toString());
        valores.put(Transacciones.descripcion, descripcion.getText().toString());
        valores.put(String.valueOf(Transacciones.foto),ArrayFoto);

        Long resultado = db.insert(Transacciones.personasTabla, Transacciones.codigo, valores);

        Toast.makeText(getApplicationContext(), "Registro ingreso con exito, Codigo " + resultado.toString()
                ,Toast.LENGTH_LONG).show();
        db.close();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}