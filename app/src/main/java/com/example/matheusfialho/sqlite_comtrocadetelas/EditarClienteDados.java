package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class EditarClienteDados extends AppCompatActivity {

    private ClienteDAO dao;
    private List<Cliente> clienteList;

    EditText txtNomeAtualize;
    EditText txtIdadeAtualize;
    Button butSalvarAtualize;
    Button butVoltarAtualize;

    Boolean bool = false;
    Cliente cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_dados);

        txtNomeAtualize = (EditText) findViewById(R.id.txtNomeAtualize);
        txtIdadeAtualize = (EditText) findViewById(R.id.txtIdadeAtualize);
        butSalvarAtualize = (Button) findViewById(R.id.butSalvarAtualize);
        butVoltarAtualize = (Button) findViewById(R.id.butVoltarAtualize);

        //RECEBENDO DADOS DA INTENT
        bool = (Boolean) getIntent().getExtras().getBoolean("valida");
        cliente = (Cliente) getIntent().getExtras().getSerializable("cliente");

        txtNomeAtualize.setText(cliente.getNome());
        txtIdadeAtualize.setText(String.valueOf(cliente.getIdade()));
        clienteList = new ArrayList<>();
        clienteList.add(cliente);

        butSalvarAtualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = clienteList.get(0).getId();
                String novoNome = txtNomeAtualize.getText().toString();
                int NovaIdade = Integer.parseInt(String.valueOf(txtIdadeAtualize.getText()));
                dao = new ClienteDAO(getBaseContext());
                boolean sucesso = dao.salvar(id, novoNome, NovaIdade);
                if (sucesso) {
                    limpaDados();

                    Snackbar.make(v, "Atualizou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    if(bool == true) {
                        Intent intent = new Intent(getApplicationContext(), ListarTodosClientes.class);
                        startActivity(intent);
                    } else {
                        onBackPressed();
                        limpaDados();
                    }
                } else {
                    Snackbar.make(v, "Erro ao atualizar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        butVoltarAtualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bool == true){
                    Intent intent = new Intent(getApplicationContext(), ListarTodosClientes.class);
                    startActivity(intent);
                }
                else {
                    onBackPressed();
                    limpaDados();
                }
            }
        });

    }

    private void limpaDados(){
        txtIdadeAtualize.setText("");
        txtNomeAtualize.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
