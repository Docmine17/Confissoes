package com.pintto.confissoes;

import android.content.Intent;
import android.os.Bundle;

import com.pintto.confissoes.Gerenciadores.GerenciadorDeTexto;
import com.pintto.confissoes.Gerenciadores.Preferencias;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import com.pintto.confissoes.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Pref
    private Preferencias preferencias;
    private GerenciadorDeTexto gerenciadorDeTexto;
    private EditText editText;
    private FloatingActionButton fab;
    private String textoRecuperado;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configura a Toolbar
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        // Instancia o gerenciador de Texto
        gerenciadorDeTexto = new GerenciadorDeTexto(getApplicationContext());

        // Instancia as Preferencias
        preferencias = new Preferencias(getApplicationContext());

        // Atribui os IDs.
        editText = findViewById(R.id.editText);
        fab = binding.fab;

        textoRecuperado = editText.getText().toString();

        // Recuperar a anotacao
        String anotacao = gerenciadorDeTexto.recuperarAnoacao();

        if (!anotacao.isEmpty()) {
            editText.setText(anotacao);
        }

        if (textoRecuperado.isEmpty() && anotacao.isEmpty()) {
            editText.setText(R.string.texto_padrao);
        }

        fab.setOnClickListener(view -> {

            // Validar se foi digitado algo
            String textoRecuperado1 = editText.getText().toString();

            if (textoRecuperado1.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.preencha_anotacao, Toast.LENGTH_LONG).show();
            } else {
                gerenciadorDeTexto.salvarAnotacao(textoRecuperado1);
                Toast.makeText(getApplicationContext(), R.string.anotacao_salva_sucesso, Toast.LENGTH_LONG).show();
            }
        });
        verificarTamanhoFonte();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Se pega o texto e guarda numa variavel
        textoRecuperado = editText.getText().toString();

    }

    @Override
    protected void onResume() {
        super.onResume();
        verificarTamanhoFonte();

        if (!textoRecuperado.isEmpty()) {
            editText.setText(textoRecuperado);
        }

    }

    // Menu da TollBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Quando o item do menu for selecionado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_erase) {
            abrirDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    // Alert Dialog.
    public void abrirDialog() {
        // instanciar o Alertdialog
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);

        // Configurar titulo e mensagem
        materialAlertDialogBuilder.setTitle(R.string.pergunta_apagar_conteudo);
        materialAlertDialogBuilder.setMessage(R.string.perdera_conteudo_permantentemente);

        // Configurar cancelamento(clicar Fora)
        materialAlertDialogBuilder.setCancelable(true);

        // Config Ações sim e não
        materialAlertDialogBuilder.setPositiveButton(R.string.apagar, (dialog, which) -> {
            // Deleta o Conteúdo.
            gerenciadorDeTexto.zerarAnotacao();

            // Poẽ o texto padrão de volta
            editText.setText(R.string.texto_padrao);

            Toast.makeText(getApplicationContext(), R.string.conteudo_apagado_sucesso, Toast.LENGTH_LONG).show();
        });
        materialAlertDialogBuilder.setNegativeButton(R.string.cancelar, (dialog, which) -> {
        });

        // Criar e exibir o AlertDialog
        materialAlertDialogBuilder.create();
        materialAlertDialogBuilder.show();

    }

    public void verificarTamanhoFonte() {
        String tamanho = preferencias.tamanhoFonte();
        switch (tamanho) {
            case "0" -> editText.setTextSize(15);
            case "1" -> editText.setTextSize(18);
            case "2" -> editText.setTextSize(22);
        }
    }

}
