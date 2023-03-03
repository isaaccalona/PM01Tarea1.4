package com.example.pm01tarea14;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pm01tarea14.Class.Usuarios;
import com.example.pm01tarea14.Transacciones.SQLConexion;
import com.example.pm01tarea14.Transacciones.Transacciones;

import java.util.ArrayList;

public class ListaPersonas extends AppCompatActivity {

    SQLConexion conexion;
    ListView personaslista;
    ArrayList<Usuarios> listapersonas;
    ArrayList<String> Arreglopersonas;
    Button btnimagen,Imagenb;
    Intent intent;
    Usuarios persona;
    int previousPosition=10;
    int count=0;
    long pM=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personas);
        personaslista = (ListView) findViewById(R.id.lista);
        Imagenb = (Button) findViewById(R.id.btnverfoto);

        conexion = new SQLConexion(this, Transacciones.NameDatabase,null,1);
        ObtenerListaPersonas();
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_checked,Arreglopersonas);
        personaslista.setAdapter(adp);

        personaslista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    previousPosition=i;
                    count=1;
                    pM=System.currentTimeMillis();
                    persona = listapersonas.get(i);
                    setContactoSeleccionado();
                }
        });
        Imagenb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(),FotoPersona.class);
                    intent.putExtra("codigoParaFoto", persona.getCodigo()+"");
                    startActivity(intent);
                }catch (NullPointerException e){
                    Intent intent = new Intent(getApplicationContext(),FotoPersona.class);
                    intent.putExtra("codigoParaFoto", "1");
                    startActivity(intent);
                }
            }
        });

    }
    private void setContactoSeleccionado() {
        intent.putExtra("codigo", persona.getCodigo()+"");
        intent.putExtra("nombre", persona.getNombre()+"");
        intent.putExtra("descripcion", persona.getDescripcion()+"");
    }
    private void ObtenerListaPersonas()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Usuarios person = null;
        listapersonas = new ArrayList<Usuarios>();

        // Cursor
        Cursor cursor = db.rawQuery("SELECT * FROM "+Transacciones.personasTabla, null );

        while(cursor.moveToNext())
        {
            person = new Usuarios();
            person.setCodigo(cursor.getInt(0));
            person.setNombre(cursor.getString(1));
            person.setDescripcion(cursor.getString(2));
            listapersonas.add(person);
        }
        cursor.close();
        FillList();
    }

    private void FillList()
    {
        Arreglopersonas = new ArrayList<String>();
        for(int i = 0; i < listapersonas.size(); i++)
        {
            Arreglopersonas.add(listapersonas.get(i).getCodigo() + " | "+
                    listapersonas.get(i).getNombre() + " | ");
        }
    }
}