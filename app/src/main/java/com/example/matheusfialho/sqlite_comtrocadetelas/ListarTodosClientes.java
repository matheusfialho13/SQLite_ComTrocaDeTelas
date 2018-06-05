package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //PARTE QUE ESTAVA NO "MainActivity" REFERENTE AO MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.itemCadastrar) {
            trocaTela(getApplicationContext(), CadastrarCliente.class);
            return true;
        }
        else if (id == R.id.itemBuscar){
            trocaTela(getApplicationContext(), BuscarCliente.class);
            return true;
        }
        else if (id == R.id.itemEditar){
            trocaTela(getApplicationContext(), EditarClienteConsulta.class);
            return true;
        }
        else if (id == R.id.itemDeletar){
            trocaTela(getApplicationContext(), DeletarCliente.class);
            return true;
        }
        else if (id == R.id.itemListar){
            trocaTela(getApplicationContext(), ListarTodosClientes.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void trocaTela(Context context, Class classe){
        trocaTela(context, classe, null, null, null);
    }

    public void trocaTela(Context context, Class classe, Boolean bool, Cliente cliente, String nome){
        Intent intent = new Intent(context, classe);
        if (bool != null) {
            intent.putExtra("bool", bool);
        }
        if (cliente != null){
            intent.putExtra("cliente", cliente);
        }
        if (nome != null){
            intent.putExtra("string", nome);
        }
        startActivity(intent);
    }
}
