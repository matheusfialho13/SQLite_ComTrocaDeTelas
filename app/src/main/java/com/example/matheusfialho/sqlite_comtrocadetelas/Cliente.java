package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.Context;

import java.io.Serializable;

public class Cliente implements Serializable {
    private int id;
    private String nome;
    private int idade;

    public Cliente (Context context){ }

    public Cliente(int id, String nome, int idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }

    public int getId(){ return this.id; }
    public String getNome(){ return this.nome; }
    public int getIdade(){ return this.idade; }

    @Override
    public boolean equals(Object o){
        return this.id == ((Cliente)o).id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }

    public String toString() {
        return " ID: " + id + "  Cliente: " + nome + "  Idade: " +
                idade;
    }
}
