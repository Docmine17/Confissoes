package com.pintto.confissoes.Gerenciadores;

import android.content.Context;

import android.content.SharedPreferences;

public class Preferencias {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private final String NOME_ARQUIVO = "preferencias";
    private final String CHAVE_TAMANHO_FONTE = "1";
    private final String CHAVE_MODO_NOTURNO = "0";

    public Preferencias(Context c) {
        this.context = c;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, 0);
        editor = preferences.edit();
    }

    public String tamanhoFonte(){
        return preferences.getString(CHAVE_TAMANHO_FONTE,CHAVE_TAMANHO_FONTE);
    }

    public void salvarTamanhoFonte(String FONTE){
        editor.putString(CHAVE_TAMANHO_FONTE, FONTE);
        editor.commit();
    }

    public String modoNoturno(){
        return preferences.getString(CHAVE_MODO_NOTURNO,CHAVE_MODO_NOTURNO);
    }

    public void salvarModoNoturno(String MODO){
        editor.putString(CHAVE_MODO_NOTURNO, MODO);
        editor.commit();
    }

}
