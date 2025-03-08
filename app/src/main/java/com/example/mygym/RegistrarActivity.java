package com.example.mygym;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrarActivity extends AppCompatActivity {

    public static final String KEY_TIPO = "KEY_TIPO";
    public static final String KEY_DIA = "KEY_DIA";
    public static final String KEY_EXERCICIO = "KEY_EXERCICIO";
    public static final String KEY_PESO = "KEY_PESO";
    public static final String KEY_REPETICOES = "KEY_REPETICOES";
    public static final String KEY_CONCLUIDO = "KEY_CONCLUIDO";
    public static final String KEY_MODO = "MODO";

    public static final int MODO_NOVO   = 0;
    public static final int MODO_EDITAR = 1;
    private EditText cx_dia, cx_peso, cx_serie;
    private CheckBox cb_concluido;
    private RadioGroup rg_tipo;
    private Spinner sp_exercicio;
    private RadioButton rb_peitoral,rb_abdomen, rb_costas, rb_inferior;
    DateFormat dateFormat;
    Date date;
    String dataformatada;

    private int modo;
    private History historyOriginal;


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
        rb_peitoral = findViewById(R.id.rb_peitoral);
        rb_abdomen = findViewById(R.id.rb_abdomen);
        rb_costas = findViewById(R.id.rb_costas);
        rb_inferior = findViewById(R.id.rb_inferior);

        try{

            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if (bundle != null){

                modo = bundle.getInt(KEY_MODO);

                if (modo == MODO_NOVO){
                    setTitle(getString(R.string.Novo));
                }else{
                    setTitle(getString(R.string.item_editar));

                    Date dia = new Date();
                    String tipoStr = bundle.getString(RegistrarActivity.KEY_TIPO);
                    int exercicio = bundle.getInt(RegistrarActivity.KEY_EXERCICIO);
                    double peso = bundle.getDouble(RegistrarActivity.KEY_PESO);
                    int repeticoes = bundle.getInt(RegistrarActivity.KEY_REPETICOES);
                    boolean concluido = bundle.getBoolean(RegistrarActivity.KEY_CONCLUIDO);

                    TipoExercicio tipoExercicio = TipoExercicio.valueOf(tipoStr);

                    historyOriginal = new History(dia,
                            tipoExercicio,
                            exercicio,
                            peso,
                            repeticoes,
                            concluido);


                    cx_dia.setText(getDate(dia));
                    if(tipoExercicio == TipoExercicio.Peitoral){
                        rb_peitoral.setChecked(true);
                    } else if (tipoExercicio == TipoExercicio.Abdomem) {
                        rb_abdomen.setChecked(true);
                    } else if (tipoExercicio == TipoExercicio.Costas) {
                        rb_costas.setChecked(true);
                    } else if (tipoExercicio == TipoExercicio.Inferiores) {
                        rb_inferior.setChecked(true);
                    }
                    sp_exercicio.setSelection(exercicio);
                    cx_peso.setText(String.valueOf(peso));
                    cx_serie.setText(String.valueOf(repeticoes));
                    cb_concluido.setChecked(concluido);

                }
            }


        } catch (Exception e) {
            System.out.println("LOGDEV: " +  e.getStackTrace());
        }




    }

    public static Date stringToDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getDate() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = new Date();
        dataformatada = dateFormat.format(date);
        return dataformatada;
    }

    private String getDate(Date Paramdata) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataformatada = dateFormat.format(Paramdata);
        return dataformatada;
    }

    public void limpar(){
        cx_peso.setText(null);
        cx_serie.setText(null);
        cb_concluido.setChecked(false);
        rg_tipo.clearCheck();
        sp_exercicio.setSelection(0);

    Toast.makeText(this, R.string.toast_limpar, Toast.LENGTH_LONG).show();

    }

    public void salvar(){

        try {
            //Valida rg_tipo
            int rbId = rg_tipo.getCheckedRadioButtonId();

            TipoExercicio tipoExercicio;

            if (rbId == R.id.rb_abdomen) {
                tipoExercicio =TipoExercicio.Abdomem;
            } else if (rbId == R.id.rb_costas) {
                tipoExercicio = TipoExercicio.Costas;
            } else if (rbId == R.id.rb_inferior) {
                tipoExercicio = TipoExercicio.Inferiores;
            } else if (rbId == R.id.rb_peitoral) {
                tipoExercicio = TipoExercicio.Peitoral;
            } else {
                Toast.makeText(this,
                        R.string.toast_faltou_tipo,
                        Toast.LENGTH_LONG).show();
                return;
            }

            //Valida spinner de exercícios
            int exercicio = sp_exercicio.getSelectedItemPosition();

            if (exercicio == AdapterView.INVALID_POSITION) {

                Toast.makeText(this,
                        R.string.toast_spinner_vazio,
                        Toast.LENGTH_LONG).show();
                return;
            }

            //valida o peso do exercício
            String pesoString = cx_peso.getText().toString();

            if (pesoString == null || pesoString.trim().isEmpty()) {

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

            if (serieString == null || serieString.trim().isEmpty()) {

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

            if(modo==MODO_EDITAR &&
            tipoExercicio == historyOriginal.getTipoExercicio() &&
            exercicio == historyOriginal.getExercicio()&&
            pesoDouble == historyOriginal.getPeso() &&
            serieInt == historyOriginal.getRepeticoes() &&
            concluido == historyOriginal.isConcluido()
            ){
                setResult(RegistrarActivity.RESULT_CANCELED);
                finish();
                return;
            }

            Intent intentResposta = new Intent();
            intentResposta.putExtra(KEY_DIA, dataformatada);
            intentResposta.putExtra(KEY_TIPO, tipoExercicio.toString());
            intentResposta.putExtra(KEY_EXERCICIO, exercicio);
            intentResposta.putExtra(KEY_PESO, pesoDouble);
            intentResposta.putExtra(KEY_REPETICOES, serieInt);
            intentResposta.putExtra(KEY_CONCLUIDO, concluido);

            setResult(GymHistoryActivity.RESULT_OK, intentResposta);
            finish();


        } catch (Exception e) {
            System.out.println("LOGDEV: " +  e.getStackTrace());
            setResult(GymHistoryActivity.RESULT_CANCELED);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.registrar_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItem = item.getItemId();

        if(menuItem == R.id.menuItem_salvar){
            salvar();
            return true;
        }else if( menuItem == R.id.menuItem_limpar){
            limpar();
            return true;
        } else{
            return super.onOptionsItemSelected(item);
        }

    }
}