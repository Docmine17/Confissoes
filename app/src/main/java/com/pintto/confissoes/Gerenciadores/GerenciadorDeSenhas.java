package com.pintto.confissoes.Gerenciadores;

import android.content.Context;
import android.content.SharedPreferences;

public class GerenciadorDeSenhas {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private final String NOME_ARQUIVO = "senha.preferencias";
    private final String CHAVE_SENHA = "senha";

    public GerenciadorDeSenhas(Context c) {
        this.context = c;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, 0);
        editor = preferences.edit();
    }

    //Retorna a senha
    public String recuperarSenha() { return preferences.getString(CHAVE_SENHA,""); }

    //Salva a senha nas preferencias
    public void salvarSenha(String senhaSalva){
        editor.putString(CHAVE_SENHA, senhaSalva);
        editor.commit();
    }

    //Zera o valor da senha nas preferencias
    public void zerarSenha(){
        editor.putString(CHAVE_SENHA, "");
        editor.commit();
    }

    //Verifica se a senha está ativada e rotorna se true se está.
    public boolean senhaAtivada(){
        boolean ativa = false;
        if (!recuperarSenha().isEmpty()){
            ativa = true;
            }
        return ativa ;
    };


}
