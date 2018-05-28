package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterPersonalizado extends BaseAdapter {

    private Cliente classCliente;
    private List<Cliente> clientes = new ArrayList<>();
    private final Activity act;

    public AdapterPersonalizado(List<Cliente> clientes, Activity act) {
        this.clientes = clientes;
        this.act = act;
    }

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
        @SuppressLint("ViewHolder")
        View view = act.getLayoutInflater()
                .inflate(R.layout.lista_clientes_personalizada, parent, false);

        Cliente cliente = clientes.get(position);

        TextView nome = (TextView)
                view.findViewById(R.id.lista_clientes_personalizada_nome);

        nome.setText(cliente.getNome());

        //String i = String.valueOf(cliente.getIdade());

        //TextView id = (TextView) view.findViewById(R.id.lista_clientes_personalizada_id);
        //id.setText("ID: "+String.valueOf(cliente.getId()));

        return view;
    }
}
