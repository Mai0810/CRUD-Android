package com.example.arendelle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.*;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    Button btcriarbanco;

    Button btcadastrardados;

    SQLiteDatabase db;

    Button btconsultardados;

    Button btalterardados;

    Button btexcluirdados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btcriarbanco = findViewById(R.id.btcriarbanco);
        btcriarbanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {


                    db = openOrCreateDatabase("banco_dados", Context.MODE_PRIVATE, null);

                    db.execSQL("create table if not exists " +
                            " usuarios (numreg integer primary key" +
                            " autoincrement, nome text not null, telefone text " +
                            " not null, " + "  email text not null ) ");

                    AlertDialog.Builder dialogo = new
                            AlertDialog.Builder(MainActivity.this);
                    dialogo.setTitle("Aviso")
                            .setMessage("Banco de dados criado com sucesso!")
                            .setNeutralButton("OK", null)
                            .show();

                } catch (Exception e) {
                }
            }
        });

        btcadastrardados = findViewById(R.id.btcadastrardados);

        btcadastrardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent gravaRegistroActivity = new Intent(MainActivity.this, GravaRegistrosActivity.class);
                MainActivity.this.startActivity(gravaRegistroActivity);
            }
        });

        btconsultardados = findViewById(R.id.btconsultardados);

        btconsultardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent consultaRegistroActivity = new Intent(MainActivity.this, ConsultaDadosActivity.class);
                MainActivity.this.startActivity(consultaRegistroActivity);
            }
        });

         btalterardados = findViewById(R.id.btalterardados);

        btalterardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent alterarRegistroActivity = new Intent(MainActivity.this, AlterarDadosActivity.class);
                MainActivity.this.startActivity(alterarRegistroActivity);
            }
        });


        btexcluirdados = findViewById(R.id.btexcluirdados);

        btexcluirdados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent excluirRegistrosActivity = new Intent(MainActivity.this, ExcluirDadosActivity.class);
                MainActivity.this.startActivity(excluirRegistrosActivity);
            }
        });

    }
}