package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditarClienteConsulta extends AppCompatActivity {

    private ClienteDAO dao;
    private List<Cliente> clienteList;

    /*COMPONENTES DA TELA*/
    EditText txtConsultarNome2;
    EditText txtId;
    Button butConsultarNome2;
    Button butConsultarId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_consulta);

        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        txtConsultarNome2 = (EditText) findViewById(R.id.txtConsultarNome2);
        txtId = (EditText) findViewById(R.id.txtId);
        butConsultarNome2 = (Button) findViewById(R.id.butConsultarNome2);
        butConsultarId = (Button) findViewById(R.id.butConsultarId);


        //CRIA EVENTO DOS BOTÕES
        butConsultarNome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clienteList = new ArrayList<>();
                dao = new ClienteDAO(getBaseContext());
                String nome = txtConsultarNome2.getText().toString();
                clienteList = dao.retornaConsulta(nome);
                if (clienteList != null) {
                    if (clienteList.size() == 1) {
                        trocaTela(getApplicationContext(), EditarClienteDados.class, false, clienteList.get(0));
                        limpaDados();
                    } else {
                        Toast.makeText(getApplicationContext(), "Seja mais específico!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        butConsultarId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clienteList = new ArrayList<>();
                dao = new ClienteDAO(getBaseContext());
                String id = txtId.getText().toString();
                clienteList = dao.retornaConsultaPeloID(id);

                if (clienteList != null) {
                    if (clienteList.size() == 1) {
                        trocaTela(getApplicationContext(), EditarClienteDados.class, false, clienteList.get(0));
                        limpaDados();
                    } else {
                        Toast.makeText(getApplicationContext(), "Seja mais específico!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });





    }

    private void limpaDados(){
        txtConsultarNome2.setText("");
        txtId.setText("");
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
