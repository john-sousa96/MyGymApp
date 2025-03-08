package com.example.mygym;

import static com.example.mygym.TipoExercicio.Peitoral;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
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

    private int posicaoSelecionada = -1;
    private ActionMode actionMode;

    private View     viewSelecionada;
    private Drawable backgroundDrawable;

    private ActionMode.Callback actionCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.historico_item_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int idMenuItem = item.getItemId();

            if (idMenuItem == R.id.menuItem_editar){
                editar();
                return true;
            }else
            if (idMenuItem == R.id.menuItem_excluir){
                excluir();
                mode.finish();
                return true;
            }else{
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if (viewSelecionada != null){
                viewSelecionada.setBackground(backgroundDrawable);
            }

            actionMode = null;
            viewSelecionada = null;
            backgroundDrawable = null;

            lv_gymHistory.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_history);

        lv_gymHistory = findViewById(R.id.lv_gymHistory);
        setTitle(getString(R.string.title_historico));

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
        //registerForContextMenu(lv_gymHistory);

    }
    private String getDate(Date Paramdata) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataformatada = dateFormat.format(Paramdata);
        return dataformatada;
    }

   private void popularListaHistory() {
       historyList = new ArrayList<>();

      /*  DateFormat dateFormat;
        Date dia;
        Double peso;
       Locale localeBr = new Locale("pt", "BR");

       History history;
       boolean concluido;
       TipoExercicio tipoExercicio;
       TipoExercicio [] tipos = TipoExercicio.values();

       dateFormat = new SimpleDateFormat("dd/MM/yyyy", localeBr);*/


        /*String [] historico_dias = getResources().getStringArray(R.array.historico_dias);
        int [] historico_tipo_exercicios = getResources().getIntArray(R.array.historico_tipo_exercicios);
        int [] historico_exercicios = getResources().getIntArray(R.array.historico_exercicios);
        String [] historico_pesos = getResources().getStringArray(R.array.historico_pesos);
        int [] historico_repeticoes = getResources().getIntArray(R.array.historico_repeticoes);
        int [] historico_concluido =getResources().getIntArray(R.array.historico_concluido);*/




        /*try{
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
        }*/


        historyAdapter = new HistoryAdapter(this, historyList);

       lv_gymHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               posicaoSelecionada = position;
               editar();
           }
       });

       lv_gymHistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

               if (actionMode != null) {
                   return false;
               }

               posicaoSelecionada = position;

               viewSelecionada = view;
               backgroundDrawable = view.getBackground();

               view.setBackgroundColor(Color.rgb(200, 225, 255));

               lv_gymHistory.setEnabled(false);

               actionMode = startSupportActionMode(actionCallback);

               return true;
           }
       });

        lv_gymHistory.setAdapter(historyAdapter);

    }

    public void abrirSobre (){
        Intent intent = new Intent(this, SobreActivity.class);
        startActivity(intent);
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

    ActivityResultLauncher <Intent> launcherNovoCadastro =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {

                            if(result.getResultCode() == GymHistoryActivity.RESULT_OK){
                                Intent intent = result.getData();
                                Bundle bundle = intent.getExtras();

                                if(bundle != null){

                                    try {
                                        Date dia = stringToDate(bundle.getString(RegistrarActivity.KEY_DIA));
                                        String tipoStr = bundle.getString(RegistrarActivity.KEY_TIPO);
                                        int exercicio = bundle.getInt(RegistrarActivity.KEY_EXERCICIO);
                                        double peso = bundle.getDouble(RegistrarActivity.KEY_PESO);
                                        int repeticoes = bundle.getInt(RegistrarActivity.KEY_REPETICOES);
                                        boolean concluido = bundle.getBoolean(RegistrarActivity.KEY_CONCLUIDO);

                                        History history = new History(dia,
                                                TipoExercicio.valueOf(tipoStr),
                                                exercicio,
                                                peso,
                                                repeticoes,
                                                concluido);

                                        historyList.add(history);
                                        historyAdapter.notifyDataSetChanged();
                                    }catch (Exception e) {
                                        System.out.println("DEVLOG: " + e.getStackTrace().toString());
                                    }
                                }
                            }
                        }
                    }
            );

    public void abrirNovoCadastro(){

        Intent intent = new Intent(this, RegistrarActivity.class);
        intent.putExtra(RegistrarActivity.KEY_MODO, RegistrarActivity.MODO_NOVO);
        launcherNovoCadastro.launch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.historico_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idMenuItem = item.getItemId();
        if(idMenuItem == R.id.menuItem_adicionar){
            abrirNovoCadastro();
            return true;
        }else if(idMenuItem == R.id.menuItem_Sobre){
            abrirSobre();
            return true;
        } else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void excluir (){
        historyList.remove(posicaoSelecionada);
        historyAdapter.notifyDataSetChanged();
    }

    ActivityResultLauncher<Intent> launcherEditar = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {

                    try{
                        if (result.getResultCode() == GymHistoryActivity.RESULT_OK){

                            Intent intent = result.getData();

                            Bundle bundle = intent.getExtras();

                            if (bundle != null){


                                Date dia = stringToDate(bundle.getString(RegistrarActivity.KEY_DIA));
                                String tipoStr = bundle.getString(RegistrarActivity.KEY_TIPO);
                                int exercicio = bundle.getInt(RegistrarActivity.KEY_EXERCICIO);
                                double peso = bundle.getDouble(RegistrarActivity.KEY_PESO);
                                int repeticoes = bundle.getInt(RegistrarActivity.KEY_REPETICOES);
                                boolean concluido = bundle.getBoolean(RegistrarActivity.KEY_CONCLUIDO);

                                History history = historyList.get(posicaoSelecionada);

                                history.setData(dia);
                                TipoExercicio tipoExercicio = TipoExercicio.valueOf(tipoStr);
                                history.setTipoExercicio(tipoExercicio);
                                history.setExercicio(exercicio);
                                history.setPeso(peso);
                                history.setRepeticoes(repeticoes);
                                history.setConcluido(concluido);

                                historyAdapter.notifyDataSetChanged();
                            }
                        }

                        posicaoSelecionada = -1;

                        if (actionMode != null){
                            actionMode.finish();
                        }

                    } catch (Exception e) {
                        System.out.println("LOVDEV: " + e.getStackTrace());
                    }
                }
            });

    private void editar(){

        try{
            History history = historyList.get(posicaoSelecionada);

            Intent intent = new Intent(this, RegistrarActivity.class);

            intent.putExtra(RegistrarActivity.KEY_MODO, RegistrarActivity.MODO_EDITAR);

            intent.putExtra(RegistrarActivity.KEY_DIA,      history.getData());
            intent.putExtra(RegistrarActivity.KEY_TIPO,     history.getTipoExercicio().toString());
            intent.putExtra(RegistrarActivity.KEY_EXERCICIO,  history.getExercicio());
            intent.putExtra(RegistrarActivity.KEY_PESO,      history.getPeso());
            intent.putExtra(RegistrarActivity.KEY_REPETICOES, history.getRepeticoes());
            intent.putExtra(RegistrarActivity.KEY_CONCLUIDO, history.isConcluido());

            launcherEditar.launch(intent);

        } catch (Exception e) {
            System.out.println("LOVDEV: " + e.getStackTrace());
        }

    }
}