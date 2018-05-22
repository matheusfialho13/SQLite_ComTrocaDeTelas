package com.example.matheusfialho.sqlite_comtrocadetelas;

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

public class EditarCliente extends AppCompatActivity {

    private ClienteDAO dao;
    private List<Cliente> clienteList;

    /*COMPONENTES DA TELA*/
    EditText txtConsultarNome2;
    EditText txtId;
    Button butConsultarNome2;
    Button butConsultarId;

    EditText txtNomeAtualize;
    EditText txtIdadeAtualize;
    Button butSalvarAtualize;
    Button butVoltarAtualize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_main);

        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        txtConsultarNome2 = (EditText) findViewById(R.id.txtConsultarNome2);
        txtId = (EditText) findViewById(R.id.txtId);
        butConsultarNome2 = (Button) findViewById(R.id.butConsultarNome2);
        butConsultarId = (Button) findViewById(R.id.butConsultarId);

        txtNomeAtualize = (EditText) findViewById(R.id.txtNomeAtualize);
        txtIdadeAtualize = (EditText) findViewById(R.id.txtIdadeAtualize);
        butSalvarAtualize = (Button) findViewById(R.id.butSalvarAtualize);
        butVoltarAtualize = (Button) findViewById(R.id.butVoltarAtualize);

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
                        tela02Visivel();
                        txtNomeAtualize.setText(clienteList.get(0).getNome());
                        txtIdadeAtualize.setText(clienteList.get(0).getIdade() + "");
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
                        tela02Visivel();
                        txtNomeAtualize.setText(clienteList.get(0).getNome());
                        txtIdadeAtualize.setText(clienteList.get(0).getIdade() + "");
                    } else {
                        Toast.makeText(getApplicationContext(), "Seja mais específico!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        butSalvarAtualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = clienteList.get(0).getId();
                String novoNome = txtNomeAtualize.getText().toString();
                int NovaIdade = Integer.parseInt(txtIdadeAtualize.getText() + "");
                dao = new ClienteDAO(getBaseContext());
                boolean sucesso = dao.salvar(id, novoNome, NovaIdade);
                if (sucesso) {
                    limpaDados();

                    Snackbar.make(v, "Atualizou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    tela01Visivel();

                } else {
                    Snackbar.make(v, "Erro ao atualizar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        butVoltarAtualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tela01Visivel();
                limpaDados();
            }
        });
    }

    private void tela02Visivel(){
        findViewById(R.id.includeeditar01).setVisibility(View.INVISIBLE);
        findViewById(R.id.includeeditar02).setVisibility(View.VISIBLE);
    }

    private void tela01Visivel(){
        findViewById(R.id.includeeditar01).setVisibility(View.VISIBLE);
        findViewById(R.id.includeeditar02).setVisibility(View.INVISIBLE);
    }

    private void limpaDados(){
        txtIdadeAtualize.setText("");
        txtNomeAtualize.setText("");
        txtConsultarNome2.setText("");
        txtId.setText("");
    }
}
