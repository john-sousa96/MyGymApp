package com.example.mygym;

import static com.example.mygym.TipoExercicio.Peitoral;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GymHistoryActivity extends AppCompatActivity {

   private ListView lv_gymHistory;
    private List <History> historyList;
    private HistoryAdapter historyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_history);

        lv_gymHistory = findViewById(R.id.lv_gymHistory);

        lv_gymHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                History history = (History) lv_gymHistory.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        getString(R.string.dia_do_exercicio) + getDate(history.getData()) + getString(R.string.foi_clicado),
                        Toast.LENGTH_LONG).show();

            }
        });

       popularListaHistory();

    }
    private String getDate(Date Paramdata) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataformatada = dateFormat.format(Paramdata);
        return dataformatada;
    }

   private void popularListaHistory() {
       DateFormat dateFormat;
        Date dia;
        Double peso;
       Locale localeBr = new Locale("pt", "BR");

        String [] historico_dias = getResources().getStringArray(R.array.historico_dias);
        int [] historico_tipo_exercicios = getResources().getIntArray(R.array.historico_tipo_exercicios);
        int [] historico_exercicios = getResources().getIntArray(R.array.historico_exercicios);
        String [] historico_pesos = getResources().getStringArray(R.array.historico_pesos);
        int [] historico_repeticoes = getResources().getIntArray(R.array.historico_repeticoes);
        int [] historico_concluido =getResources().getIntArray(R.array.historico_concluido);

        historyList = new ArrayList<>();
        History history;
        boolean concluido;
        TipoExercicio tipoExercicio;
        TipoExercicio [] tipos = TipoExercicio.values();

        dateFormat = new SimpleDateFormat("dd/MM/yyyy", localeBr);

        try{
            for(int count = 0; count < historico_dias.length; count++){
                concluido = (historico_concluido[count] == 1 ? true : false);
                tipoExercicio = tipos[historico_tipo_exercicios[count]];

                 dia = dateFormat.parse(historico_dias[count]);
                 peso = Double.parseDouble(historico_pesos[count]);

            history = new History(dia,
                    tipoExercicio,
                    historico_exercicios[count],
                    peso,
                    historico_repeticoes[count],
                    concluido);
                historyList.add(history);
            }

        }catch (Exception e) {
            System.out.println("LOGDEV");
            System.out.println(e.getStackTrace());
        }


        historyAdapter = new HistoryAdapter(this, historyList);
        lv_gymHistory.setAdapter(historyAdapter);

    }

}