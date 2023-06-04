package com.example.arendelle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ConsultaDadosActivity extends AppCompatActivity {
    TextView txtnome, txttelefone, txtemail, txtstatus_registro;

    SQLiteDatabase db;

    ImageView imgprimeiro, imganterior, imgproximo, imgultimo;

    int indice;

    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_dados);
        txtnome = (TextView) findViewById(R.id.txtnome);
        txttelefone = (TextView) findViewById(R.id.txttelefone);
        txtemail = (TextView) findViewById(R.id.txtemail);
        txtstatus_registro = (TextView) findViewById(R.id.txtstatus_registro);

        txtnome.setText("");
        txttelefone.setText("");
        txtemail.setText("");

        imgprimeiro = (ImageView) findViewById(R.id.imgprimeiro);

        imganterior = (ImageView) findViewById(R.id.imganterior);

        imgproximo = (ImageView) findViewById(R.id.imgproximo);

        imgultimo = (ImageView) findViewById(R.id.imgultimo);

        try {

            //Abre o banco de dados
            db = openOrCreateDatabase("banco_dados", Context.MODE_PRIVATE, null);

            c = db.query("usuarios", new String[]{"nome", "telefone", "email"}, null, null, null, null, null);


            if (c.getCount() > 0) {

                //Move para o primeiro registro
                c.moveToFirst();
                indice = 1;

                txtnome.setText(c.getString(0));//Obtem o nome
                txttelefone.setText(c.getString(1)); //Obtém o telefone
                txtemail.setText(c.getString(2));//Obtém o e-mail

                txtstatus_registro.setText(indice + " / " + c.getCount());

            } else {

                txtstatus_registro.setText("Nenhum Registro");

            }


            imgprimeiro.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (c.getCount() > 0) {

                        //Move para o primeiro registro
                        c.moveToFirst();
                        indice = 1;

                        //Obtem o nome
                        txtnome.setText(c.getString(0));

                        //Obtem o telefone
                        txttelefone.setText(c.getString(1));

                        //Obtém o e-mail
                        txtemail.setText(c.getString(2));

                        txtstatus_registro.setText(indice + " / " + c.getCount());

                    }

                }

            });

            imgproximo.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View arg0) {

                    if (c.getCount() > 0) {

                        if (indice != c.getCount()) {

                            indice++;
                            //Move para o próximo registro.
                            c.moveToNext();

                            txtnome.setText(c.getString(0));//obtem o nome
                            txttelefone.setText(c.getString(1));//Obtém o telefone
                            txtemail.setText(c.getString(2));//Obtém o e-mail

                            txtstatus_registro.setText(indice + " / " + c.getCount());
                        }

                    }
                }
            });

            imgultimo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (c.getCount() > 0) {
                        //Move para o último registro
                        c.moveToLast();
                        indice = c.getCount();
                        //Obtem o nome
                        txtnome.setText(c.getString(0));
                        //Obtém o telefone
                        txttelefone.setText(c.getString(1));
                        //Obtem o e-mail
                        txtemail.setText(c.getString(2));

                        txtstatus_registro.setText(indice + " / " + c.getCount());
                    }
                }

            });

        } catch (Exception e) {
            MostraMensagem("Erro : " + e.toString());
        }
    }

    public void MostraMensagem(String str) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(ConsultaDadosActivity.this);

        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }
}