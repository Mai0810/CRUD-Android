package com.example.arendelle;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AlterarDadosActivity extends Activity {
    EditText txtnome, txttelefone, txtemail;

    TextView txtstatus_registro;

    SQLiteDatabase db;

    ImageView imgprimeiro, imganterior, imgproximo, imgultimo;

    Button btalterardados;

    int indice;

    int numreg;

    Cursor c;

    DialogInterface.OnClickListener diAlteraInformacoes;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_dados);

        txtnome = (EditText) findViewById(R.id.txtnome);
        txttelefone = (EditText) findViewById(R.id.txttelefone);
        txtemail = (EditText) findViewById(R.id.txtemail);

        txtstatus_registro = (TextView)

                findViewById(R.id.txtstatus_registro);

        imgprimeiro = (ImageView) findViewById(R.id.imgprimeiro);

        imganterior = (ImageView) findViewById(R.id.imganterior);
        imgproximo = (ImageView) findViewById(R.id.imgproximo);
        imgultimo = (ImageView) findViewById(R.id.imgultimo);

        btalterardados = (Button)
                findViewById(R.id.btalterardados);


        try {

//Abre o banco de dados

            db = openOrCreateDatabase("banco_dados", Context.MODE_PRIVATE, null);

            c = db.query("usuarios", new String[]
                            {"numreg", "nome", "telefone", "email"},
                    null, null, null, null, null);

            if (c.getCount() > 0) {

//Move para o primeiro registro

                c.moveToFirst();
                indice = 1;
                numreg = c.getInt(0); //Obtem o número de registro
                txtnome.setText(c.getString(1)); //Obtem o nome
                txttelefone.setText(c.getString(2)); //Obtém o telefone
                txtemail.setText(c.getString(3)); //Obtém o email


                txtstatus_registro.setText(indice + " / " + c.getCount());

            } else {

                txtstatus_registro.setText("Nenhum Registro");

            }

        } catch (Exception e) {

        }


        imgprimeiro.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (c.getCount() > 0) {
                    c.moveToFirst();//Move para o primeiro registro

                    indice = 1;
                    numreg = c.getInt(0);//Obtem o número de registro
                    txtnome.setText(c.getString(1));//Obtem o nome
                    txttelefone.setText(c.getString(2));//Obtém o telefone
                    txtemail.setText(c.getString(3));//Obtém o e-mail
                    txtstatus_registro.setText(indice + " / " + c.getCount());
                }
            }
        });

        imganterior.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO Auto-generated method stub
                if (c.getCount() > 0) {
                    if (indice > 1) {

                        indice--;
                        c.moveToPrevious();//Move para o registro anterior
                        numreg = c.getInt(0); //obtem o numero de registro
                        txtnome.setText(c.getString(1)); //Obtem o nome
                        txttelefone.setText(c.getString(2));//obtem o telefone
                        txtemail.setText(c.getString(3));//obtem o e-mail
                        txtstatus_registro.setText(indice + " / " + c.getCount());
                    }
                }
            }
        });
        imgproximo.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                if (c.getCount() > 0)
// TODO Auto-generated method stub
                {
                    if (indice != c.getCount()) {
                        indice++;
                        c.moveToNext();//Move para o proximo registro
                        numreg = c.getInt(0); //Obtem o numero de registro
                        txtnome.setText(c.getString(1));//Obtem o nome
                        txttelefone.setText(c.getString(2));//obtem o telefone
                        txtemail.setText(c.getString(3));//Obtem o e-mail
                        txtstatus_registro.setText(indice + " / " + c.getCount());
                    }
                }
            }

        });


        imgultimo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (c.getCount() > 0) {
                    c.moveToLast();//Move para o último registro
                    indice = c.getCount();
                    numreg = c.getInt(0); //Obtem o número de registro
                    txtnome.setText(c.getString(1)); //Obtem o nome
                    txttelefone.setText(c.getString(2)); //Obtem o telefone
                    txtemail.setText(c.getString(3));//Obtem o e-mail
                    txtstatus_registro.setText(indice + "/" + c.getCount());

                }

            }
        });


        diAlteraInformacoes = new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

//Altera as informações do registro na tabela

                String nome = txtnome.getText().toString();
                String telefone = txttelefone.getText().toString();
                String email = txtemail.getText().toString();

                try {

                    db.execSQL("update usuarios set nome = '" + nome + "' , " + "telefone ='" + telefone + "', email = '" + email + "'where numreg =" + numreg);

                    MostraMensagem("Dados alterados com sucesso.");

                } catch (Exception e) {

                    MostraMensagem("Erro: " + e.toString());
                }

            }

        };

        btalterardados.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                AlertDialog.Builder dialogo = new
                        AlertDialog.Builder(AlterarDadosActivity.this);
                dialogo.setTitle("Confirma");
                dialogo.setMessage("Deseja alterar as informações");
                dialogo.setNegativeButton("Não", null);
                dialogo.setPositiveButton("Sim", diAlteraInformacoes);
                dialogo.show();
            }
        });
    }

    public void MostraMensagem(String str) {

        AlertDialog.Builder dialogo = new
                AlertDialog.Builder(AlterarDadosActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }
}

