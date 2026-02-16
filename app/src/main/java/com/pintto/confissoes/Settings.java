package com.pintto.confissoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;

import com.pintto.confissoes.Gerenciadores.GerenciadorDeSenhas;
import com.pintto.confissoes.Gerenciadores.Preferencias;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    private SwitchCompat switchSenha;
    private LinearLayout buttonMudarSenha;
    private GerenciadorDeSenhas gerenciadorDeSenhas;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Instancia o gerenciador de senhas;
        gerenciadorDeSenhas = new GerenciadorDeSenhas(getApplicationContext());
        // Intancia as Preferencias
        preferencias = new Preferencias(getApplicationContext());

        // Atribui os IDs
        switchSenha = findViewById(R.id.switchSenha);
        buttonMudarSenha = findViewById(R.id.buttonMudarSenha);

        // Verifica o Estado da Senha
        verificacoes();

        // Fiva ouvindo pra ver se o Switch é mechido
        switchSenha.setOnCheckedChangeListener((compoundButton, b) -> {
            if (switchSenha.isChecked()) {
                iniciaCriacaoDeSenha();
            } else {
                gerenciadorDeSenhas.zerarSenha();
                verificacoes();
            }
        });

    }

    // Para finalizar a activity quando apertar no botao de voltar da titlebar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Quando a Activity volta
    @Override
    protected void onResume() {
        super.onResume();
        verificacoes();
    }

    // Verifica os estados da senha
    public void verificacoes() {

        if (!gerenciadorDeSenhas.senhaAtivada()) {
            switchSenha.setChecked(false);
            buttonMudarSenha.setEnabled(false);
        } else {
            switchSenha.setChecked(true);
            buttonMudarSenha.setEnabled(true);
        }
    }

    // Metodo para iniciar a criação de senha
    public void iniciaCriacaoDeSenha() {
        Intent senhaIntent = new Intent(Settings.this, Criar_senha.class);
        startActivity(senhaIntent);
    }

    // Metodo para Mudar Senha que é iniciado pelo botão de Mudar Senha
    public void password(View view) {
        verificacoes();
        iniciaCriacaoDeSenha();

    }

    public void tamanhoFonte(View view) { // Metodo iniciado pelo botão de Tamanho da Fonte
        MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(this);

        String[] singleItems = { getString(R.string.item_fonte_pequena), getString(R.string.item_fonte_normal),
                getString(R.string.item_fonte_grande) };
        int numero = Integer.parseInt(preferencias.tamanhoFonte());

        // Cria o Alert Dialog
        alert.setTitle(R.string.escolha_tamanho_fonte)
                .setSingleChoiceItems(singleItems, numero, (dialog, which) -> {
                    if (which == 0) {
                        preferencias.salvarTamanhoFonte(String.valueOf(which));
                        dialog.dismiss();
                    } else if (which == 1) {
                        preferencias.salvarTamanhoFonte(String.valueOf(which));
                        dialog.dismiss();
                    } else {
                        preferencias.salvarTamanhoFonte(String.valueOf(which));
                        dialog.dismiss();
                    }

                }).show();

    }

    public void modoNoturno(View view) { // Inicia o AlertDialog do Modo Noturno pelo Botao
        MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(this);
        String[] singleItems = { getString(R.string.item_tema_sistema), getString(R.string.item_tema_claro),
                getString(R.string.item_tema_escuro) };

        int numero = Integer.parseInt(preferencias.modoNoturno());

        // Cria o Alert Dialog
        alert.setTitle(R.string.escolha_tema)
                .setSingleChoiceItems(singleItems, numero, (dialog, which) -> {
                    if (which == 0) {
                        preferencias.salvarModoNoturno(String.valueOf(which));
                        verificarModoNoturno();
                        dialog.dismiss();
                    } else if (which == 1) {
                        preferencias.salvarModoNoturno(String.valueOf(which));
                        verificarModoNoturno();
                        dialog.dismiss();
                    } else {
                        preferencias.salvarModoNoturno(String.valueOf(which));
                        verificarModoNoturno();
                        dialog.dismiss();
                    }
                }).show();

    }

    public void verificarModoNoturno() { // Verifica o estado do modo noturno e aplica a configuração
        String modo = preferencias.modoNoturno();
        switch (modo) {
            case "0":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case "1":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "2":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }
}
