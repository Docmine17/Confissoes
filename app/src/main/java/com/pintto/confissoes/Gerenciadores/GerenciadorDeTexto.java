package com.pintto.confissoes.Gerenciadores;

import android.content.Context;
import android.content.SharedPreferences;

public class GerenciadorDeTexto {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private final String NOME_ARQUIVO = "anotacao.preferencias";
    private final String CHAVE_NOME = "nome";

    public String recuperarAnoacao(){
        return preferences.getString(CHAVE_NOME,"");
    }

    public GerenciadorDeTexto(Context c) {
        this.context = c;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, 0);
        editor = preferences.edit();
    }

    public void salvarAnotacao(String anotacao){
        editor.putString(CHAVE_NOME, anotacao);
        editor.commit();
    }

    public void zerarAnotacao(){
        editor.putString(CHAVE_NOME, null);
        editor.commit();
    }


}