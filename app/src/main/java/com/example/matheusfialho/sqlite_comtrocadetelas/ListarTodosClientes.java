package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListarTodosClientes extends AppCompatActivity {

    private ClienteDAO dao;
    private List<Cliente> listaClientes;
    private List<Cliente> listaPesquisa;
    ListView listViewClientes;
    AdapterPersonalizado adapter;
    AdapterPersonalizado adapter2;
    SearchView searchView;
    CharSequence query;
    Menu menuZao;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_clientes);

        //OLHAR A PESQUISA COM SEARCH VIEW

        listViewClientes = (ListView) findViewById(R.id.lista);
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

    public void pesquisa(Menu menu){
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //searchView.setIconifiedByDefault ( false );
        // executar definido no evento do ouvinte de texto de consulta
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newQuery) {
                // faça algo no envio de texto
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // fazer alguma coisa quando o texto muda
                List<Cliente> listaPesquisa = new ArrayList<>();
                if((newText != null)&&(newText != "")) {
                    listaPesquisa = concatenaArray(dao.retornaConsulta(newText), dao.retornaConsultaPeloNum(newText));
                } else {
                    listaPesquisa = dao.retornarTodos();
                }
                adapter2 = new AdapterPersonalizado(listaPesquisa, ListarTodosClientes.this);
                listViewClientes.setAdapter(adapter2);

                return false;
            }
        });


        /*
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                query = searchView.getQuery();
                List<Cliente> listaPesquisa = new ArrayList<>();
                listaPesquisa = dao.retornaConsulta(String.valueOf(query));
                adapter = new AdapterPersonalizado(listaPesquisa, ListarTodosClientes.this);
                listViewClientes.setAdapter(adapter);
            }
        });

        //searchView.setQueryHint((CharSequence) adapter);
        */
    }
    public static List<Cliente> concatenaArray (List<Cliente> array1, List<Cliente> array2){
        List<Cliente> result = new ArrayList<>();

        if(array2 == null){
            return array1;
        }
        else if(array1 == null){
            return array2;
        }
        else if(array1.equals(array2)){
            return array1;
        }
        else {
            for(int i=0; i<array1.size(); i++){
                result.add(array1.get(i));
            }
            for(int i=0; i<array2.size(); i++){
                result.add(array2.get(i));
            }
            return result;
        }
    }

    //PARTE QUE ESTAVA NO "MainActivity" REFERENTE AO MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        getMenuInflater().inflate(R.menu.menu_search_view, menu);
        menuZao = menu;

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        } else if(id == R.id.search){
            pesquisa(menuZao);
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
