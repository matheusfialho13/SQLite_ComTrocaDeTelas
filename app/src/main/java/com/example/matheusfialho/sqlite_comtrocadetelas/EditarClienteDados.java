package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EditarClienteDados extends AppCompatActivity {

    private ClienteDAO dao;
    private List<Cliente> clienteList;

    EditText txtNomeAtualize;
    EditText txtIdadeAtualize;
    EditText txtCelAtualize;
    Button butSalvarAtualize;
    Button butVoltarAtualize;
    ImageView imageViewEdit;
    ImageButton imageButtonEdit;

    Intent intent;
    Boolean bool = false;
    Cliente cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_dados);

        txtNomeAtualize = (EditText) findViewById(R.id.txtNomeAtualize);
        txtIdadeAtualize = (EditText) findViewById(R.id.txtIdadeAtualize);
        txtCelAtualize = (EditText) findViewById(R.id.txtCelAtualize);
        butSalvarAtualize = (Button) findViewById(R.id.butSalvarAtualize);
        butVoltarAtualize = (Button) findViewById(R.id.butVoltarAtualize);
        imageViewEdit = (ImageView) findViewById(R.id.imageViewEdit);
        imageButtonEdit = (ImageButton) findViewById(R.id.imageButtonEdit);


        //RECEBENDO DADOS DA INTENT
        bool = (Boolean) getIntent().getExtras().getBoolean("valida");
        cliente = (Cliente) getIntent().getExtras().getSerializable("cliente");

        txtNomeAtualize.setText(cliente.getNome());
        txtIdadeAtualize.setText(String.valueOf(cliente.getIdade()));
        txtCelAtualize.setText(cliente.getCel());
        setarImagem(cliente.getFoto(), imageViewEdit);

        clienteList = new ArrayList<>();
        clienteList.add(cliente);

        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 2);
            }
        });

        butSalvarAtualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = clienteList.get(0).getId();
                String novoNome = txtNomeAtualize.getText().toString();
                int novaIdade = Integer.parseInt(String.valueOf(txtIdadeAtualize.getText()));
                String novoCel = txtCelAtualize.getText().toString();

                // Vinculando imagem
                imageViewEdit.buildDrawingCache();
                Bitmap bitmap = imageViewEdit.getDrawingCache();
                //Convertendo de Bitmap para byte[]
                ByteArrayOutputStream saida = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,saida);
                byte[] novaFoto = saida.toByteArray();

                dao = new ClienteDAO(getBaseContext());
                boolean sucesso = dao.salvar(id, novoNome, novaIdade, novoCel, novaFoto);
                if (sucesso) {
                    limpaDados();

                    Snackbar.make(v, "Atualizou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    if(bool == true) {
                        Intent intent = new Intent(getApplicationContext(), ListarTodosClientes.class);
                        startActivity(intent);
                        finish();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // PRECISA PEDIR PERMISSÃO DE ARMAZENAMENTO PARA A FOTO SER EXIBIDA
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 2){
                Uri imagemSelecionada = data.getData();

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(imagemSelecionada,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                imageViewEdit.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            }
        }
    }

    private void setarImagem(byte[] fotoArray, ImageView imageView){
        Bitmap raw;
        if(fotoArray!=null){
            raw  = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
            imageView.setImageBitmap(raw);
        }
    }

    private void limpaDados(){
        txtIdadeAtualize.setText("");
        txtNomeAtualize.setText("");
        txtCelAtualize.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
