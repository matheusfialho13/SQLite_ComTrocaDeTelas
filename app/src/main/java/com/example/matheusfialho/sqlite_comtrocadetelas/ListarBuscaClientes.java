package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListarBuscaClientes extends AppCompatActivity {

    private ClienteDAO dao;
    private List<Cliente> listaClientes;
    AdapterPersonalizado adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_clientes);

        String nome = (String) getIntent().getExtras().getString("string");

        final ListView listViewClientes = (ListView) findViewById(R.id.lista);
        dao = new ClienteDAO(getBaseContext());
        listaClientes = new ArrayList<>();
        listaClientes = dao.retornaConsulta(nome);

        if(listaClientes == null){
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "Não existe nenhum cliente com esse nome no Banco de Dados",
                    Toast.LENGTH_LONG);
            toast.show();
        } else {
            adapter = new AdapterPersonalizado(listaClientes, this);
            listViewClientes.setAdapter(adapter);
        }
        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = (Cliente) listViewClientes.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), InformacoesClientes.class);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
            }
        });
    }
}
