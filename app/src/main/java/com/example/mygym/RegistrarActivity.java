package com.example.mygym;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrarActivity extends AppCompatActivity {

    private EditText cx_dia, cx_peso, cx_serie;
    private CheckBox cb_concluido;
    private RadioGroup rg_tipo;
    private Spinner sp_exercicio;
    DateFormat dateFormat;
    Date date;
    String dataformatada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        cx_dia = findViewById(R.id.cx_dia);
        cx_dia.setText(getDate());
        cx_peso = findViewById(R.id.cx_peso);
        cx_serie = findViewById(R.id.cx_serie);
        cb_concluido = findViewById(R.id.cb_concludo);
        rg_tipo = findViewById(R.id.rg_tipo);
        sp_exercicio = findViewById(R.id.sp_exercicio);

    }

    private String getDate() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = new Date();
        dataformatada = dateFormat.format(date);
        return dataformatada;
    }

    public void limpar(View view){
        cx_peso.setText(null);
        cx_serie.setText(null);
        cb_concluido.setChecked(false);
        rg_tipo.clearCheck();
        sp_exercicio.setSelection(0);

    Toast.makeText(this, R.string.toast_limpar, Toast.LENGTH_LONG).show();

    }

    public void salvar(View view){

       //Valida rg_tipo
        int rbId = rg_tipo.getCheckedRadioButtonId();

        String tipoExercicio;

        if (rbId == R.id.rb_abdomen){
            tipoExercicio = getString(R.string.rb_abdomem);
        }else
        if (rbId == R.id.rb_costas){
            tipoExercicio = getString(R.string.rb_costas);
        }else
        if (rbId == R.id.rb_inferior){
            tipoExercicio = getString(R.string.rb_inferior);
        }else
            if (rbId == R.id.rb_peitoral){
            tipoExercicio = getString(R.string.rb_peitoral);
        }
        else{
            Toast.makeText(this,
                    R.string.toast_faltou_tipo,
                    Toast.LENGTH_LONG).show();
            return;
        }

        //Valida spinner de exercícios
        String exercicio = (String) sp_exercicio.getSelectedItem();

        if (exercicio == null){

            Toast.makeText(this,
                    R.string.toast_spinner_vazio,
                    Toast.LENGTH_LONG).show();
            return;
        }

        //valida o peso do exercício
        String pesoString = cx_peso.getText().toString();

        if (pesoString == null || pesoString.trim().isEmpty()){

            Toast.makeText(this,
                    R.string.toast_peso_vazio,
                    Toast.LENGTH_LONG).show();

            cx_peso.requestFocus();
            return;
        }

        double pesoDouble = 0.0;

        try {
            pesoDouble = Double.parseDouble(pesoString);

        } catch (NumberFormatException e) {

            Toast.makeText(this,
                    R.string.toast_peso_incorreto,
                    Toast.LENGTH_LONG).show();

            cx_peso.requestFocus();
            cx_peso.setSelection(0, cx_peso.getText().toString().length());
            return;
        }

        //valida a quantidade de séries
        String serieString = cx_serie.getText().toString();

        if (serieString == null || serieString.trim().isEmpty()){

            Toast.makeText(this,
                    R.string.toast_serie_vazio,
                    Toast.LENGTH_LONG).show();

            cx_serie.requestFocus();
            return;
        }

        int serieInt = 0;

        try {
            serieInt = Integer.parseInt(serieString);

        } catch (NumberFormatException e) {

            Toast.makeText(this,
                    R.string.toast_serie_incorreto,
                    Toast.LENGTH_LONG).show();

            cx_serie.requestFocus();
            cx_serie.setSelection(0, cx_serie.getText().toString().length());
            return;
        }

       boolean concluido = cb_concluido.isChecked();

       Toast.makeText(this,
                getString(R.string.lb_dia)  + dataformatada,Toast.LENGTH_LONG).show();
        Toast.makeText(this,
                getString(R.string.lb_tipo)  + tipoExercicio,Toast.LENGTH_LONG).show();
        Toast.makeText(this,
                getString(R.string.lb_exercicio) + exercicio,Toast.LENGTH_LONG).show();
        Toast.makeText(this,
                getString(R.string.lb_peso) + pesoString,Toast.LENGTH_LONG).show();
        Toast.makeText(this,
                getString(R.string.lb_serie) + serieString,Toast.LENGTH_LONG).show();
        Toast.makeText(this,
                (concluido ? getString(R.string.cb_concluido) : getString(R.string.cb_nao_concluido)),Toast.LENGTH_LONG).show();

    }
}