package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CarregarImagem {
    public static Intent pegaIntencao(Context context, File file) {

        // Cria um array de Intenções de Câmera.
        final List<Intent> cameraIntencoes = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam){
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            cameraIntencoes.add(intent);
        }

        /*
        // Cria Intenção de Galeria.
        final Intent galeriaIntencao = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galeriaIntencao.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        executarCorte(galeriaIntencao, 1, 1, 300, 300);
        */

        // Cria Intenção de Galeria e outros app do tipo.
        final Intent galeriaIntencao = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galeriaIntencao.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        // Seletor de opções do sistema de arquivos.
        final Intent chooserIntent = Intent.createChooser(galeriaIntencao, "Foto de Perfil");

        // Add a opção de câmera.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntencoes.toArray(new Parcelable[]{}));

        return chooserIntent;

    }

    public static Intent executarCorte(Uri uriEntrada, Uri uriSaida, int aspectX, int aspectY, int outputX, int outputY) {
        // Os 4 ultimos parâmetros dizem as dimensões que aceita ser cortado
        // Se comentar eles, o corte da imagem fica livre, por isso comentei.
        Intent intencaoCortar = new Intent("com.android.camera.action.CROP");
        intencaoCortar.putExtra(MediaStore.EXTRA_OUTPUT, uriSaida);
        intencaoCortar.setDataAndType(uriEntrada, "image/*");
        intencaoCortar.putExtra("crop", "true");
        //intencaoCortar.putExtra("aspectX", aspectX);
        //intencaoCortar.putExtra("aspectY", aspectY);
        intencaoCortar.putExtra("scale", true);
        //intencaoCortar.putExtra("outputX", outputX);
        //intencaoCortar.putExtra("outputY", outputY);
        intencaoCortar.putExtra("return-data", true);
        return intencaoCortar;
    }

    /*
    public static void executarCorte(Intent cortarIntencao, int aspectX, int aspectY, int outputX, int outputY) {
        cortarIntencao.putExtra("crop", "true");
        //cortarIntencao.putExtra("aspectX", aspectX);
        //cortarIntencao.putExtra("aspectY", aspectY);
        cortarIntencao.putExtra("scale", true);
        //cortarIntencao.putExtra("outputX", outputX);
        //cortarIntencao.putExtra("outputY", outputY);
        cortarIntencao.putExtra("return-data", true);
    }
    */
}
