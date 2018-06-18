package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.Context;

import java.io.Serializable;

public class Cliente implements Serializable {
    private int id;
    private int idade;
    private String nome;
    private String cel;
    private byte[] foto;

    public Cliente (){ }

    public Cliente(int id, String nome, int idade, String cel, byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.cel = cel;
        this.foto = foto;

    }

    public int getId(){ return id; }
    public String getNome(){ return nome; }
    public int getIdade(){ return idade; }
    public String getCel(){ return cel;}
    public byte[] getFoto() { return foto; }

    public void setFoto(byte[] foto) { this.foto = foto; }


    @Override
    public boolean equals(Object o){
        return this.id == ((Cliente)o).id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }

    public String toString() {
        return " ID: " + id + "  Cliente: " + nome + "  Idade: " + idade + " Celular: " + cel;
    }

}
