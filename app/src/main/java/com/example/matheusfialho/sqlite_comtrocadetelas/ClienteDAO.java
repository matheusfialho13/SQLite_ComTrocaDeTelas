package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public static final String TABLE_CLIENTES = DbHelper.TABELA;
    public static DbGateway gw;
    public static ClienteDAO dao;
    public static DbHelper dbHelper;
    public static SQLiteDatabase db;
    private Cliente cliente;


    public ClienteDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }


    /*
    ClienteDAO(Context ctx) { cliente = new Cliente(ctx);}

    public static ClienteDAO getInstance(Context ctx){
        if (dao == null) {
            dao = new ClienteDAO(ctx);
        }
        gw = DbGateway.getInstance(ctx);
        return dao;
    }
    */

    public boolean salvar(String nome, int idade, String cel, byte[] foto){
        return salvar(0, nome, idade, cel, foto);
    }

    public boolean salvar(int id, String nome, int idade, String cel, byte[] foto){
        boolean resultado;

        nome = nome.substring(0,1).toUpperCase()+nome.substring(1);

        ContentValues cv = new ContentValues();
        cv.put("Nome", nome);
        cv.put("Idade", idade);
        cv.put("Cell", cel);
        cv.put("Foto", foto);
        //resultado = gw.getDatabase().insert(TABLE_CLIENTES, null, cv) > -1;

        if(id > 0)
            resultado = gw.getDatabase().update(TABLE_CLIENTES, cv, "_ID = ?", new String[]{ String.valueOf(id) }) > 0;
        else
            resultado = gw.getDatabase().insert(TABLE_CLIENTES, null, cv) > 0;
        return resultado;
    }

    public List<Cliente> retornarTodos(){
        List<Cliente> listClientes = null;

        Cursor cursor = gw.getDatabase().query(
                TABLE_CLIENTES,
                new String[]{DbHelper._ID, DbHelper.NOME, DbHelper.IDADE, DbHelper.CELL, DbHelper.FOTO},
                null,
                null,
                null,
                null,
                DbHelper.NOME+" ASC");
        if(cursor != null && cursor.moveToFirst()){
            listClientes = new ArrayList<>();
            cursor.moveToFirst();
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(DbHelper._ID));
                String nome = cursor.getString(cursor.getColumnIndex(DbHelper.NOME));
                int idade = cursor.getInt(cursor.getColumnIndex(DbHelper.IDADE));
                byte[] foto = cursor.getBlob(cursor.getColumnIndex(DbHelper.FOTO));
                String cel = cursor.getString(cursor.getColumnIndex(DbHelper.CELL));
                listClientes.add(new Cliente(_id, nome, idade, cel, foto));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listClientes;
    }

    public List<Cliente> retornaConsulta(String nomeCliente){

        List<Cliente> listClientes = null;
        String selection = DbHelper.NOME + " LIKE ?";
        String[] selectionArgs = new String[]{"%"+nomeCliente+"%"};

        Cursor cursor = gw.getDatabase().query(
                "Clientes",
                new String[]{DbHelper._ID, DbHelper.NOME, DbHelper.IDADE, DbHelper.CELL, DbHelper.FOTO},
                selection,
                selectionArgs,
                null,
                null,
                DbHelper.NOME + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            listClientes = new ArrayList<>();
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(DbHelper._ID));
                String nome = cursor.getString(cursor.getColumnIndex(DbHelper.NOME));
                int idade = cursor.getInt(cursor.getColumnIndex(DbHelper.IDADE));
                byte[] foto = cursor.getBlob(cursor.getColumnIndex(DbHelper.FOTO));
                String cel = cursor.getString(cursor.getColumnIndex(DbHelper.CELL));
                listClientes.add(new Cliente(_id, nome, idade, cel, foto));
            } while (cursor.moveToNext());
            cursor.close();
        } else if(cursor == null){
            Log.d("Clientes", "Não existe nenhum registro de "+nomeCliente);
        }
        if (listClientes != null && !listClientes.isEmpty()) {
            Log.d("Clientes", " ");
            for (int i = 0; i < listClientes.size(); i++) {
                Log.d("Clientes", "Nome: "+listClientes.get(i).getNome()+"  ID: "+listClientes.get(i).getId());
            }
        }
        return listClientes;
    }

    public List<Cliente> retornaConsultaPeloID(String idCliente){
        List<Cliente> listClientes = null;
        String selection = DbHelper._ID + " = ?";
        String[] selectionArgs = new String[]{idCliente};

        Cursor cursor = gw.getDatabase().query(
                "Clientes",
                new String[]{DbHelper._ID, DbHelper.NOME, DbHelper.IDADE, DbHelper.CELL, DbHelper.FOTO},
                selection,
                selectionArgs,
                null,
                null,
                DbHelper.NOME+" ASC");

        if (cursor != null && cursor.moveToFirst()) {
            listClientes = new ArrayList<>();
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(DbHelper._ID));
                String nome = cursor.getString(cursor.getColumnIndex(DbHelper.NOME));
                int idade = cursor.getInt(cursor.getColumnIndex(DbHelper.IDADE));
                byte[] foto = cursor.getBlob(cursor.getColumnIndex(DbHelper.FOTO));
                String cel = cursor.getString(cursor.getColumnIndex(DbHelper.CELL));
                listClientes.add(new Cliente(_id, nome, idade, cel, foto));
            } while (cursor.moveToNext());
            cursor.close();
        } else if(cursor == null){
            Log.d("Clientes", "Não existe nenhum registro de "+idCliente);
        }
        if (listClientes != null && !listClientes.isEmpty()) {
            Log.d("Clientes", " ");
            for (int i = 0; i < listClientes.size(); i++) {
                Log.d("Clientes", "Nome: "+listClientes.get(i).getNome()+"  ID: "+listClientes.get(i).getId());
            }
        }
        return listClientes;
    }

    public List<Cliente> retornaConsultaPeloNum(String celCliente){
        List<Cliente> listClientes = null;
        String selection = DbHelper.CELL + " LIKE ?";
        String[] selectionArgs = new String[]{"%"+celCliente+"%"};

        Cursor cursor = gw.getDatabase().query(
                "Clientes",
                new String[]{DbHelper._ID, DbHelper.NOME, DbHelper.IDADE, DbHelper.CELL, DbHelper.FOTO},
                selection,
                selectionArgs,
                null,
                null,
                DbHelper.NOME+" ASC");

        if (cursor != null && cursor.moveToFirst()) {
            listClientes = new ArrayList<>();
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(DbHelper._ID));
                String nome = cursor.getString(cursor.getColumnIndex(DbHelper.NOME));
                int idade = cursor.getInt(cursor.getColumnIndex(DbHelper.IDADE));
                byte[] foto = cursor.getBlob(cursor.getColumnIndex(DbHelper.FOTO));
                String cel = cursor.getString(cursor.getColumnIndex(DbHelper.CELL));
                listClientes.add(new Cliente(_id, nome, idade, cel, foto));
            } while (cursor.moveToNext());
            cursor.close();
        } else if(cursor == null){
            Log.d("Clientes", "Não existe nenhum registro de "+celCliente);
        }
        if (listClientes != null && !listClientes.isEmpty()) {
            Log.d("Clientes", " ");
            for (int i = 0; i < listClientes.size(); i++) {
                Log.d("Clientes", "Nome: "+listClientes.get(i).getNome()+"  ID: "+listClientes.get(i).getId());
            }
        }
        return listClientes;
    }

    public boolean deletar(int id){
        return gw.getDatabase().delete(TABLE_CLIENTES, "_ID = ?", new String[]{ id + "" }) > 0;
    }
}
