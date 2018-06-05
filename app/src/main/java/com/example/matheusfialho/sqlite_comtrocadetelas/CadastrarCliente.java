package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import static com.example.matheusfialho.sqlite_comtrocadetelas.R.id.imageButton;

public class CadastrarCliente extends AppCompatActivity {

    private ClienteDAO dao;
    private TratamentoImagem ti;

    /*COMPONENTES DA TELA*/
    EditText txtNome;
    EditText txtIdade;
    Button butCadastrar;
    ImageView imageView;
    ImageButton imageButton;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastrar_cliente);

        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtIdade = (EditText) findViewById(R.id.txtIdade);
        butCadastrar = (Button) findViewById(R.id.butCadastrar);
        imageView = (ImageView) findViewById(R.id.imageViewCads);
        imageButton = (ImageButton) findViewById(R.id.imageButton);


        //CRIA EVENTO DO BOTÃO CADASTRAR
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PRECISA PEDIR PERMISSÃO DE ARMAZENAMENTO PARA A FOTO SER EXIBIDA
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 2);

                //ti = new TratamentoImagem();
                //ti.galleryButtonClick(R.id.imageButton);
                //onActivityResult(2, -1, intent);
                //intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);

            }
        });

        butCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Salvando...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //pegando os valores
                String nome = txtNome.getText().toString();
                int idade = Integer.parseInt(txtIdade.getText().toString());


                // Vinculando imagem
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                //Convertendo de Bitmap para byte[]
                ByteArrayOutputStream saida = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,saida);
                byte[] foto = saida.toByteArray();

                //salvando os dados
                dao = new ClienteDAO(getBaseContext());
                boolean sucesso = dao.salvar(nome, idade, foto);
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
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            }
        }
    }

    private void limpaDados(){
        txtNome.setText("");
        txtIdade.setText("");
        imageView.setImageResource(android.R.drawable.ic_menu_report_image);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(getApplicationContext(), ListarTodosClientes.class);
        startActivity(intent);
    }

    /*
    private void permissão(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(CadastrarCliente.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }*/
}
