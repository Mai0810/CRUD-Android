package com.example.arendelle;

import android.app.Activity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExcluirDadosActivity extends Activity{

    TextView txtnome, txttelefone, txtemail, txtstatus_registro;
    SQLiteDatabase db;
    ImageView imgprimeiro, imganterior, imgproximo, imgultimo;
    int indice;
    int numreg;
    Cursor c;
    Button btexcluirdados;
    DialogInterface.OnClickListener diExcluirRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_dados);

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

        btexcluirdados = (Button) findViewById(R.id.btexcluirdados);
        try {
            //Abre o banco de dados
            db = openOrCreateDatabase("banco_dados", Context.MODE_PRIVATE, null);
            CarregarDados();
            imgprimeiro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getCount() > 0)
                    {
                        //Move para o primeiro registro
                        c.moveToFirst();
                        indice = 1;
                        numreg = c.getInt(0); //Obtem o numero do registro
                        txtnome.setText(c.getString(1));//obtem o nome
                        txttelefone.setText(c.getString(2));//obtem o telefone
                        txtstatus_registro.setText(indice + "/" + c.getCount());
                    }
                }
            });
            imganterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (c.getCount() > 0)
                    {
                        if (indice > 1) {

                            indice--;
                            //move para o registro anterior
                            c.moveToPrevious();
                            numreg = c.getInt(0); //obtem o numero do registro
                            txtnome.setText(c.getString(1)); //obtem o nome
                            txttelefone.setText(c.getString(2));//obtem o telefone
                            txtemail.setText(c.getString(3));//obtem o email
                            txtstatus_registro.setText(indice + "/" + c.getCount());
                        }
                    }

                }
            });
            imgproximo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (c.getCount() > 0)
                    {
                        if (indice != c.getCount()) {


                            indice++;
                            //move para o proximo registro
                            c.moveToNext();

                            numreg = c.getInt(0); //obtem o numero do registro
                            txtnome.setText(c.getString(1)); //obtem o nome
                            txttelefone.setText(c.getString(2));//obtem o telefone
                            txtemail.setText(c.getString(3));//obtem o email
                            txtstatus_registro.setText(indice + "/" + c.getCount());
                        }
                    }

                }
            });
            imgultimo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getCount() > 0)
                    {
                          //move para o ultimo registro
                            c.moveToLast();
                            indice = c.getCount();
                            numreg = c.getInt(0); //obtem o numero do registro
                            txtnome.setText(c.getString(1)); //obtem o nome
                            txttelefone.setText(c.getString(2));//obtem o telefone
                            txtemail.setText(c.getString(3));//obtem o email
                            txtstatus_registro.setText(indice + "/" + c.getCount());
                        }
                    }
            });

            diExcluirRegistro = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    db.execSQL("delete from usuarios where numreg = " + numreg);
                    CarregarDados();
                    MostraMensagem("Dados excluidos com sucesso");

                }
            };
            btexcluirdados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (c.getCount() > 0) {

                        AlertDialog.Builder dialog = new
                        AlertDialog.Builder(ExcluirDadosActivity.this);
                        dialog.setTitle("Confirma");
                        dialog.setMessage("Deseja excluir esse registro ?");
                        dialog.setNegativeButton("Nao", null);
                        dialog.setPositiveButton("Sim", diExcluirRegistro);
                        dialog.show();
                    }
                    else {
                        MostraMensagem("Nao existem registros para excluir");
                    }
                }
            });
        } catch (Exception e)
        {
            MostraMensagem("Erro: " + e.toString());
        }
    }
public void CarregarDados()
        {
            c = db.query("usuarios", new String[]
                            {"numreg", "nome", "telefone", "email"},
                    null, null, null, null, null);
            txtnome.setText(" ");
            txttelefone.setText(" ");
            txtemail.setText(" ");

            if (c.getCount() > 0) {
                //move para o primeiro registro
                c.moveToFirst();
                indice = 1;
                numreg = c.getInt(0); //Obtem o numero do registro
                txtnome.setText(c.getString(1));//obtem o nome
                txttelefone.setText(c.getString(2));//obtem o telefone
                txtemail.setText((c.getString(3)));//obter email
                txtstatus_registro.setText(indice + "/" + c.getCount());

            } else {
                txtstatus_registro.setText("Nenhum Registro");
            }
        }
public void MostraMensagem(String str)
            {
                AlertDialog.Builder dialogo = new
                AlertDialog.Builder(ExcluirDadosActivity.this);

                dialogo.setTitle("Aviso");
                dialogo.setMessage(str);
                dialogo.setNeutralButton("OK", null);

                dialogo.show();
            }
        }


