package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeletarCliente extends AppCompatActivity {

    private ClienteDAO dao;
    private List<Cliente> clienteList;

    /*COMPONENTES DA TELA*/
    EditText txtConsultarNomeDel;
    EditText txtConsultarIdDel;
    Button butConsultarNomeDel;
    Button butConsultarIdDel;

    EditText txtNomeDel;
    EditText txtIdadeDel;
    Button butVoltarDel;
    Button butDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_deletar_main);

        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        txtConsultarNomeDel = (EditText) findViewById(R.id.txtConsultarNomeDel);
        txtConsultarIdDel = (EditText) findViewById(R.id.txtConsultarIdDel);
        butConsultarNomeDel = (Button) findViewById(R.id.butConsultarNomeDel);
        butConsultarIdDel = (Button) findViewById(R.id.butConsultarIdDel);

        txtNomeDel = (EditText) findViewById(R.id.txtNomeDel);
        txtIdadeDel = (EditText) findViewById(R.id.txtIdadeDel);
        butVoltarDel = (Button) findViewById(R.id.butVoltarDel);
        butDel = (Button) findViewById(R.id.butDel);

        //CRIA EVENTO DOS BOTÕES
        butConsultarNomeDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clienteList = new ArrayList<>();
                dao = new ClienteDAO(getBaseContext());
                String nome = txtConsultarNomeDel.getText().toString();
                clienteList = dao.retornaConsulta(nome);
                if (clienteList != null) {
                    if (clienteList.size() == 1) {
                        tela02Visivel();
                        txtNomeDel.setText(clienteList.get(0).getNome());
                        txtIdadeDel.setText(clienteList.get(0).getIdade() + "");
                    } else {
                        Toast.makeText(getApplicationContext(), "Seja mais específico!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        butConsultarIdDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clienteList = new ArrayList<>();
                dao = new ClienteDAO(getBaseContext());
                String id = txtConsultarIdDel.getText().toString();
                clienteList = dao.retornaConsultaPeloID(id);

                if (clienteList != null) {
                    if (clienteList.size() == 1) {
                        tela02Visivel();
                        txtNomeDel.setText(clienteList.get(0).getNome());
                        txtIdadeDel.setText(clienteList.get(0).getIdade() + "");
                    } else {
                        Toast.makeText(getApplicationContext(), "Seja mais específico!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        butVoltarDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tela01Visivel();
                limpaDados();
            }
        });

        butDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este cliente?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = clienteList.get(0).getId();
                                dao = new ClienteDAO(getBaseContext());
                                boolean sucesso = dao.deletar(id);
                                if(sucesso) {
                                    limpaDados();
                                    Snackbar.make(view, "Excluiu!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    tela01Visivel();
                                    limpaDados();
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

    private void tela02Visivel(){
        findViewById(R.id.includedeletarconsulta).setVisibility(View.INVISIBLE);
        findViewById(R.id.includedeletardados).setVisibility(View.VISIBLE);
    }

    private void tela01Visivel(){
        findViewById(R.id.includedeletarconsulta).setVisibility(View.VISIBLE);
        findViewById(R.id.includedeletardados).setVisibility(View.INVISIBLE);
    }

    private void limpaDados(){
        txtConsultarNomeDel.setText("");
        txtConsultarIdDel.setText("");
        txtNomeDel.setText("");
        txtIdadeDel.setText("");
    }
}
