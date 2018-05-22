package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastrarCliente extends AppCompatActivity {

    private ClienteDAO dao;

    /*COMPONENTES DA TELA*/
    EditText txtNome;
    EditText txtIdade;
    Button butCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastrar_cliente);

        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtIdade = (EditText) findViewById(R.id.txtIdade);
        butCadastrar = (Button) findViewById(R.id.butCadastrar);


        //CRIA EVENTO DO BOTÃO CADASTRAR
        butCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Salvando...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //pegando os valores
                String nome = txtNome.getText().toString();
                int idade = Integer.parseInt(txtIdade.getText().toString());

                //salvando os dados
                dao = new ClienteDAO(getBaseContext());
                boolean sucesso = dao.salvar(nome, idade);
                if(sucesso) {
                    limpaDados();
                    Snackbar.make(view, "Salvou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });



    }

    private void limpaDados(){
        txtNome.setText("");
        txtIdade.setText("");
    }
}
