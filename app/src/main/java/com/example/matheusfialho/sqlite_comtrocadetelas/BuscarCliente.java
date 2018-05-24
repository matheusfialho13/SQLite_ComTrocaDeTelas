package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BuscarCliente extends AppCompatActivity {

    private ClienteDAO dao;

    /*COMPONENTES DA TELA*/
    EditText txtConsultarNome;
    Button butConsultarTodos;
    Button butConsultarNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.tela_buscar_cliente);
        setContentView(R.layout.lista_clientes);

        ListView listaDeClientes = (ListView) findViewById(R.id.lista);
        List<Cliente> listaClientes = new ArrayList<>();
        dao = new ClienteDAO(getBaseContext());
        listaClientes = dao.retornarTodos();
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(this,
                android.R.layout.simple_list_item_1, listaClientes);
        listaDeClientes.setAdapter(adapter);


        /*
        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        txtConsultarNome = (EditText) findViewById(R.id.txtConsultarNome);
        butConsultarTodos = (Button) findViewById(R.id.butConsultarTodos);
        butConsultarNome = (Button) findViewById(R.id.butConsultarNome);

        //CRIA EVENTO DOS BOTÕES
        butConsultarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao = new ClienteDAO(getBaseContext());

                List<Cliente> listaClientes = new ArrayList<>();
                listaClientes = dao.retornarTodos();

                if (listaClientes != null && !listaClientes.isEmpty()) {
                    Log.d("Clientes", " ");
                    for (int i = 0; i < listaClientes.size(); i++) {
                        Log.d("Clientes", "Nome: "+listaClientes.get(i).getNome()+"  ID: "+listaClientes.get(i).getId());
                    }
                }
                txtConsultarNome.setText("");
            }
        });

        butConsultarNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txtConsultarNome.getText().toString();
                dao = new ClienteDAO(getBaseContext());
                dao.retornaConsulta(nome);
                txtConsultarNome.setText("");
            }
        });
        */
    }
}
