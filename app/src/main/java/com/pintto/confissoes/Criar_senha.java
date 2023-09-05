package com.pintto.confissoes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.pintto.confissoes.Gerenciadores.GerenciadorDeSenhas;
import com.example.confissoes.R;

public class Criar_senha extends AppCompatActivity {

    private GerenciadorDeSenhas gerenciadorDeSenhas;

    private EditText caixaDeSenha;
    private EditText caixaDeConfirmarSenha;
    private TextView textComent;

    private String senhaDaCaixa;
    private String senhaDaCaixaDeConfirmar;

    private boolean caixa1 = false;
    private boolean caixa2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_senha);

        //Atribuir IDs.
        caixaDeSenha = findViewById(R.id.caixaDeSenha);
        caixaDeConfirmarSenha = findViewById(R.id.caixaDeConfirmarSenha);
        textComent   = findViewById(R.id.textComent);

        //Não sei o que faz
        caixaDeConfirmarSenha.setOnEditorActionListener(editorListener);

        //Manejar senha
        gerenciadorDeSenhas = new GerenciadorDeSenhas(getApplicationContext());
    }

    public void verificacoes() {//Valida se as caixas de texto estão preenchidas corretamente
        senhaDaCaixa = caixaDeSenha.getText().toString();

        if (senhaDaCaixa.isEmpty()) {
            textComent.setText(R.string.preenchaCampoSenha);
            caixa1 = false;
        } else if (senhaDaCaixa.length() < 4) {
            textComent.setText(R.string.senhaPrecisaMin4);
            caixa1 =false;
        } else if (senhaDaCaixa.length() > 9) {
            textComent.setText(R.string.senhaMaior9Dig);
            caixa1 =false;
        } else {caixa1 =true;}


        senhaDaCaixaDeConfirmar = caixaDeConfirmarSenha.getText().toString();

        if (caixa1 && senhaDaCaixaDeConfirmar.isEmpty()) {
            textComent.setText(R.string.preenchaCampoConfirmarSenha);
            caixa2 =false;
        }else if (caixa1 && !senhaDaCaixa.equals(senhaDaCaixaDeConfirmar)) {
            textComent.setText(R.string.senhaNaoCondizem);
            caixa2 =false;
        }else if(caixa1 && senhaDaCaixa.equals(senhaDaCaixaDeConfirmar)){
            caixa2 = true; textComent.setText(null);}

        if (caixa1 & caixa2){
            criarSenha();
        }

    }


    public void criarSenha(){//Cria a Senha
        gerenciadorDeSenhas.salvarSenha(senhaDaCaixa);
        textComent.setTextColor(getResources().getColor(R.color.white));
        textComent.setText(R.string.senhaDefinida);
        finish();
    }



    //Se apertar o Enter do Teclado
    private EditText.OnEditorActionListener editorListener= (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) { verificacoes(); }
        return false;
    };

    public void confirmar(View View){ verificacoes(); } //Para o OnClick do Botão de Criar Senha

    public void voltar(View View){ finish(); } //Botão de voltar

}




