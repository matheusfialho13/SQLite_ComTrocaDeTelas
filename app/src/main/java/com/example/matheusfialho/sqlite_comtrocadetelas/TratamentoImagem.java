package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

public class TratamentoImagem extends AppCompatActivity {

    File caminhoFoto;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ACTIVITY INUTILIZADA POR ENQUANTO


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == 0) {
            ImageView img = (ImageView)
                    findViewById(R.id.imageViewCads);

            // Obtém o tamanho da ImageView
            int targetW = img.getWidth();
            int targetH = img.getHeight();

            // Obtém a largura e altura da foto
            BitmapFactory.Options bmOptions =
                    new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(
                    caminhoFoto.getAbsolutePath(), bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determina o fator de redimensionamento
            int scaleFactor = Math.min(
                    photoW/targetW, photoH/targetH);

            // Decodifica o arquivo de imagem em
            // um Bitmap que preencherá a ImageView
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(
                    caminhoFoto.getAbsolutePath(), bmOptions);
            img.setImageBitmap(bitmap);
        }

        //Carregar da galeria agr

        if (resultCode == RESULT_OK && requestCode == 2) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {
                    MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(
                    selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(
                    filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap yourSelectedImage =
                    BitmapFactory.decodeFile(filePath);

            ImageView img = (ImageView)
                    findViewById(R.id.imageViewCads);
            img.setImageBitmap(yourSelectedImage);
        }
    }
    public void tirarFotoClick (View v){
        String nomeFoto = DateFormat.format(
                "yyyy-MM-dd_hhmmss", new Date()).toString();

        caminhoFoto = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),
                nomeFoto);

        Intent it = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(caminhoFoto));
        startActivityForResult(it, 0);
    }
    public void salvarFotoClick(View v) {
        if (caminhoFoto != null && caminhoFoto.exists()) {
            try {
                MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        caminhoFoto.getAbsolutePath(),
                        caminhoFoto.getName(), "");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Toast.makeText(this,
                    "Imagem adicionada a galeria.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void galleryButtonClick(int v) {

        Intent intent = new Intent(
                Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }
}


/*
new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == 123) {
                    Uri imagemSelecionada = data.getData();

                }
            }
        }
 */