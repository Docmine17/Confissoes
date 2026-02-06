package com.pintto.confissoes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.pintto.confissoes.Gerenciadores.GerenciadorDeSenhas;
import com.pintto.confissoes.R;

public class Login extends AppCompatActivity {

    private GerenciadorDeSenhas gerenciadorDeSenhas;

    private EditText caixaDeSenha;
    private TextView textComent;

    private String senhaDaCaixa;
    private String senhaUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Instanciar o gerenciador de senha.
        gerenciadorDeSenhas = new GerenciadorDeSenhas(getApplicationContext());

        //Se não existir senha
        if (!gerenciadorDeSenhas.senhaAtivada()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Atribuir IDs as Variaveis.
        caixaDeSenha = findViewById(R.id.caixaDeSenha);
        textComent   = findViewById(R.id.textComent);

        //Não sei o que faz, mas parece que precisa.
        caixaDeSenha.setOnEditorActionListener(editorListener);
    }

    //Esse metodo serve para autenticar
    public void autenticar(){

        //Obtem o texto da caixa de texto e converte para string
        senhaUsuario = gerenciadorDeSenhas.recuperarSenha();
        senhaDaCaixa = caixaDeSenha.getText().toString();

        if(senhaDaCaixa.isEmpty()) {//Se a caixa de senha esiver vazia
            textComent.setText(R.string.preenchaCampoAcima);
        }else {
            //Se a senha estiver certa
            if (senhaUsuario.equals(senhaDaCaixa)) {
                textComent.setText(R.string.entrando);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else { //Se estiver errada
                textComent.setText(R.string.senhaIncorreta);
                //Limpa a caixa de senha
                caixaDeSenha.getText().clear();
            }
        }
    }

    //Se apertar o Enter do Teclado
    private EditText.OnEditorActionListener editorListener= (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE){ autenticar(); }
        return false;
    };

    //Para o OnClick do Botão de Login
    public void confirmar(View View){
        autenticar();
    }


}
