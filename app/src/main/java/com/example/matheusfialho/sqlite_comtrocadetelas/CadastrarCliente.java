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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static com.example.matheusfialho.sqlite_comtrocadetelas.R.id.imageButton;

public class CadastrarCliente extends AppCompatActivity {

    private ClienteDAO dao;

    /*COMPONENTES DA TELA*/
    EditText txtNome;
    EditText txtIdade;
    EditText txtCel;
    Button butCadastrar;
    ImageView imageView;
    ImageButton imageButton;
    Intent intent;

    private static int RESULTADO_IMAGEM_CARREGADA = 1;
    private static int RESULTADO_IMAGEM_CORTADA = 2;
    private static final String filename = "profile.jpg";
    private static final File file = new File(Environment.getExternalStorageDirectory(), filename);
    private Uri outputFileUri = Uri.fromFile(file);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastrar_cliente);

        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtIdade = (EditText) findViewById(R.id.txtIdade);
        txtCel = (EditText) findViewById(R.id.txtCel);
        butCadastrar = (Button) findViewById(R.id.butCadastrar);
        imageView = (ImageView) findViewById(R.id.imageViewCads);
        imageButton = (ImageButton) findViewById(R.id.imageButton);


        //CRIA EVENTO PARA BUSCAR IMAGEM NA GALERIA
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PRECISA PEDIR PERMISSÃO DE ARMAZENAMENTO PARA A FOTO SER EXIBIDA
                /*
                Selecionar foto:
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 2);
                */


                //File file = new File(String.valueOf(Environment.getExternalStorageDirectory()));
                //File file = new File(Environment.getExternalStorageDirectory(), "Profile.jpg");

                //Uri imagemSelecionada = intent.getData();
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, imagemSelecionada);
                //executarCorte(intent,1, 1, 300, 300);



                //ti = new TratamentoImagem();
                //ti.galleryButtonClick(R.id.imageButton);
                //onActivityResult(2, -1, intent);
                //intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);

                if(v.getId() == R.id.imageButton) {
                    boolean mExternalStorageAvailable = false;
                    boolean mExternalStorageWriteable = false;
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        // Podemos ler e escrever os meios de comunicação
                        mExternalStorageAvailable = mExternalStorageWriteable = true;
                        Log.i("PERMISSÂO", "Podemos ler e escrever os meios de comunicação");
                    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                        // Só podemos ler a mídia
                        mExternalStorageAvailable = true;
                        mExternalStorageWriteable = false;
                        Log.i("PERMISSÂO", "Só podemos ler a mídia");
                    } else {
                        // Não podemos ler nem escrever
                        mExternalStorageAvailable = mExternalStorageWriteable = false;
                        Log.i("PERMISSÂO", "Não podemos ler nem escrever");
                    }

                    Intent carregaImagem = CarregarImagem.pegaIntencao(CadastrarCliente.this, file);

                    startActivityForResult(carregaImagem, RESULTADO_IMAGEM_CARREGADA);
                }

            }
        });

        //CRIA EVENTO DO BOTÃO CADASTRAR
        butCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Salvando...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //pegando os valores
                String nome = txtNome.getText().toString();
                int idade = Integer.parseInt(txtIdade.getText().toString());
                String cel = txtCel.getText().toString();


                // Vinculando imagem
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                //Convertendo de Bitmap para byte[]
                ByteArrayOutputStream saida = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,saida);
                byte[] foto = saida.toByteArray();

                //salvando os dados
                dao = new ClienteDAO(getBaseContext());
                boolean sucesso = dao.salvar(nome, idade, cel, foto);
                if(sucesso) {
                    limpaDados();
                    Snackbar.make(view, "Salvou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    onBackPressed();
                }else{
                    Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });




    }
    /*
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
    */

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final Uri uriImagem;

        // Detecta códigos de pedido
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == RESULTADO_IMAGEM_CARREGADA) {
                Log.i("AQUI!", "Entrou no IF");
                Bitmap bitmap = null;
                if (data == null) {
                    uriImagem = outputFileUri;
                } else {
                    uriImagem = data.getData();
                }

                // Após obter o URI da imagem selecionada iniciar a intenção de corte!
                try {
                    final Intent intencaoCortar = CarregarImagem.executarCorte(uriImagem, outputFileUri, 1, 1, 300, 300);
                    startActivityForResult(intencaoCortar, RESULTADO_IMAGEM_CORTADA);
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(CadastrarCliente.this, "Ops - Seu dispositivo não suporta a ação de cortar imagens!", Toast.LENGTH_LONG).show();
                }
            }
            if(requestCode == RESULTADO_IMAGEM_CORTADA) {
                // Imagem foi cortada!

                // Por ser uma imagem de perfil de usuário
                // A melhor prática é salvar em uma pasta interna
                // Mas se quiser salvar em uma pasta externa
                // Salve com um novo nome diferente do nome criado em "filename"
                // Ou remova esta linha do código: "file.delete();"
                Bundle extras = data.getExtras();
                Bitmap bitmap = extras.getParcelable("data");
                final File finalfile = new File(this.getFilesDir(), filename);
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(finalfile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    //if(configurada) {
                        imageView.setImageBitmap(bitmap);
                    //}
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // Para cortar a imagem foi necessário criar um arquivo
                // Após ter cortado e salvado a imagem cortada
                // Esse arquivo não é mais necessário e podemos excluir(opcional)
                file.delete();
            /*
                if (data == null) {
                    Log.i("AQUI!", "Câmera");
                    String local_foto = Environment.getExternalStorageDirectory() + "/diretorio/profile.png";
                    File fileFoto = new File(local_foto);
                    FileOutputStream fos;
                    bitmap = BitmapFactory.decodeFile(local_foto);
                    try {
                        fos = new FileOutputStream(fileFoto);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);

                } else {
                    Log.i("AQUI!", "Imagem cortada");
                    Bundle extras = data.getExtras();
                    bitmap = extras.getParcelable("data");
                    Log.i("AQUI!", "Bitmap recebeu a imagem");

                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    int largura = imageView.getWidth();
                    int altura = largura;

                    ConstraintLayout.LayoutParams margens = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
                    // Esse "params" faz com que a imagem apareça quadrada (lados e altura iguais) na lista.
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(largura, altura);
                    params.setMargins(0, margens.topMargin, 0, 0);
                    //imageView.setLayoutParams(params);
                    imageView.setImageBitmap(bitmap);
                }
                */
            }
        }
    }

    private void limpaDados(){
        txtNome.setText("");
        txtIdade.setText("");
        txtCel.setText("");
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
