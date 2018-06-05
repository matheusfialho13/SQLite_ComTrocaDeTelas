package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterPersonalizado extends BaseAdapter {

    private Cliente classCliente;
    private List<Cliente> clientes = new ArrayList<>();
    private Activity act;

    Context contexto;
    int id;


    public AdapterPersonalizado(List<Cliente> clientes, Activity act) {
        this.clientes = clientes;
        this.act = act;
    }

    /*
    public AdapterPersonalizado(Context contexto, int id, List<Cliente> lista){
        super(contexto, id, lista);
        this.contexto = contexto;
        this.clientes = lista;
        this.id = id;
    }
    */

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        classCliente = new Cliente();
        return classCliente.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap raw;
        byte[] fotoArray;
        ImageView foto;
        TextView nome;
        TextView idade;


        @SuppressLint("ViewHolder")
        View view = act.getLayoutInflater()
                .inflate(R.layout.lista_clientes_personalizada, parent, false);

        Cliente cliente = clientes.get(position);

        //pegando as referências das Views
        nome  = (TextView) view.findViewById(R.id.lista_clientes_personalizada_nome);
        idade = (TextView) view.findViewById(R.id.lista_clientes_personalizada_idade);
        foto  = (ImageView) view.findViewById(R.id.lista_clientes_personalizada_imagem);

        //populando as Views
        nome.setText(cliente.getNome());
        idade.setText(String.valueOf(cliente.getIdade())+" Anos");
        fotoArray = cliente.getFoto();

        if(fotoArray!=null){
            raw  = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
            foto.setImageBitmap(raw);
        }

        return view;
    }
}
