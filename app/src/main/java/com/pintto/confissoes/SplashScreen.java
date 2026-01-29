package com.pintto.confissoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.pintto.confissoes.Gerenciadores.GerenciadorDeSenhas;
import com.pintto.confissoes.Gerenciadores.Preferencias;
import com.pintto.confissoes.R;

public class SplashScreen extends AppCompatActivity {

    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        preferencias = new Preferencias(getApplicationContext());
        verificarModoNoturno();

        new Handler(Looper.getMainLooper()).postDelayed(() -> { //Delay da splash screen

            GerenciadorDeSenhas gerenciadorDeSenhas = new GerenciadorDeSenhas(getApplicationContext());

            //Verifica se tem senha ou não e inicia a respectiva activity

            if (gerenciadorDeSenhas.senhaAtivada()) {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();

            }else{
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }, 1000);


    }

    public void verificarModoNoturno(){ //Verifica o modo escuro das preferencias e aplica.
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
