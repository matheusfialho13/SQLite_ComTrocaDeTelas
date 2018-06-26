package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InformacoesClientes extends AppCompatActivity {

    private TextView infoNome;
    private TextView infoIdade;
    private TextView infoId;
    private TextView infoCel;
    private Button infoEditar;
    private Button infoDeletar;

    private ImageView infoFoto;
    Bitmap raw;
    byte[] fotoArray;

    private ClienteDAO dao;
    private Cliente cliente;
    ListarTodosClientes LTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_clientes);

        cliente = (Cliente) getIntent().getExtras().getSerializable("cliente");

        infoFoto = (ImageView) findViewById(R.id.infoFoto);
        infoNome = (TextView) findViewById(R.id.infoNome);
        infoIdade = (TextView) findViewById(R.id.infoIdade);
        infoId = (TextView) findViewById(R.id.infoId);
        infoCel = (TextView) findViewById(R.id.infoCel);

        infoEditar = (Button) findViewById(R.id.infoEditar);
        infoDeletar = (Button) findViewById(R.id.infoDeletar);

        //Colocando no formato (xx) xxxxx-xxxx
        String celular = cliente.getCel();
        if(celular.length() == 11)
            celular = "(" + celular.substring(0, 2) + ") " + celular.substring(2, 3)+ " " +celular.substring(3, 7) + "-" + celular.substring(7);
        else if(celular.length() == 10){
            celular = "(" + celular.substring(0, 2) + ") " + celular.substring(2, 6) + "-" + celular.substring(6);
        }
        //Populando XML
        infoNome.setText(cliente.getNome());
        infoIdade.setText(String.valueOf(cliente.getIdade()));
        infoId.setText("Id: "+String.valueOf(cliente.getId()));
        infoCel.setText(celular);

        fotoArray = cliente.getFoto();
        if(fotoArray!=null){
            raw  = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
            infoFoto.setImageBitmap(raw);
        }

        infoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trocaTela(getApplicationContext(), EditarClienteDados.class, true, cliente);
            }
        });

        infoDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este cliente?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = cliente.getId();
                                dao = new ClienteDAO(getBaseContext());
                                boolean sucesso = dao.deletar(id);
                                if(sucesso) {
                                    Snackbar.make(view, "Excluiu!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    Intent intent = new Intent(getApplicationContext(), ListarTodosClientes.class);
                                    startActivity(intent);
                                }else{
                                    Snackbar.make(view, "Erro ao excluir o cliente!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void trocaTela(Context context, Class classe){
        trocaTela(context, classe, null, null);
    }

    public void trocaTela(Context context, Class classe, Boolean bool, Cliente cliente){
        Intent intent = new Intent(context, classe);
        if (bool != null) {
            intent.putExtra("valida", bool);
        }
        if (cliente != null){
            intent.putExtra("cliente", cliente);
        }
        startActivity(intent);
    }
}
