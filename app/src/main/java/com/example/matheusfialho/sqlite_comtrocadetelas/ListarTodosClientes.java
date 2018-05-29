package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListarTodosClientes extends AppCompatActivity {

    private ClienteDAO dao;
    private List<Cliente> listaClientes;
    AdapterPersonalizado adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_clientes);

        final ListView listViewClientes = (ListView) findViewById(R.id.lista);
        dao = new ClienteDAO(getBaseContext());
        listaClientes = new ArrayList<>();
        listaClientes = dao.retornarTodos();

        adapter = new AdapterPersonalizado(listaClientes, this);
        listViewClientes.setAdapter(adapter);

        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = (Cliente) listViewClientes.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), InformacoesClientes.class);
                    intent.putExtra("cliente", cliente);
                startActivity(intent);
            }
        });

        /*
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, listaClientes);

        adapter = new AdapterPersonalizado(listaClientes, this);
         */
    }

    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    */
}
