package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Substitua por sua própria ação", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

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
        Intent intent = new Intent(context, classe);
        startActivity(intent);
    }

}
