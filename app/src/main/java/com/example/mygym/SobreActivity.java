package com.example.mygym;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        setTitle(getString(R.string.title_sobre));

    }

    public void abrirSiteAutoria(View view){
        abrirSite("https://github.com/john-sousa96");
    }

    public void abrirSite(String url){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }else{
            Toast.makeText(this,
                    R.string.toast_n_possvel_abrir_o_app,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void enviaEmail(View view){
        enviarEmail(new String[]{"johnsousa@alunos.utfpr.edu.br"}, getString(R.string.contato_app));
    }

    private void enviarEmail(String[] enderecos, String assunto){

        Intent intent = new Intent(Intent.ACTION_SENDTO);

        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, enderecos);
        intent.putExtra(Intent.EXTRA_SUBJECT, assunto);

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(this,
                    R.string.toast_n_possvel_abrir_o_app,
                    Toast.LENGTH_LONG).show();
        }
    }
}