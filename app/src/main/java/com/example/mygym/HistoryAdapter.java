package com.example.mygym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private List<History> historyList;

    private String [] exercicios;

    private static class HistoryHolder{
        public TextView lb_line_dia_valor;
        public TextView lb_line_tipo_valor;
        public TextView lb_line_exercicio_valor;
        public TextView lb_line_peso_valor;
        public TextView lb_line_repeticoes_valor;
        public TextView lb_line_concluido;
    }

    public HistoryAdapter(Context context, List<History> historyList) {
        this.context = context;
        this.historyList = historyList;

        exercicios = context.getResources().getStringArray(R.array.exercicios);
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            HistoryHolder holder;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.line_history_list, parent, false);

                holder = new HistoryHolder();

                holder.lb_line_dia_valor = convertView.findViewById(R.id.lb_line_dia_valor);
                holder.lb_line_tipo_valor = convertView.findViewById(R.id.lb_line_tipo_valor);
                holder.lb_line_exercicio_valor = convertView.findViewById(R.id.lb_line_exercicio_valor);
                holder.lb_line_peso_valor = convertView.findViewById(R.id.lb_line_peso_valor);
                holder.lb_line_repeticoes_valor = convertView.findViewById(R.id.lb_line_repeticoes_valor);
                holder.lb_line_concluido = convertView.findViewById(R.id.lb_line_concluido);
                convertView.setTag(holder);
            } else {
                holder = (HistoryHolder) convertView.getTag();
            }

            History history = historyList.get(position);
            holder.lb_line_dia_valor.setText(sdf.format(history.getData()));

            holder.lb_line_exercicio_valor.setText(exercicios[history.getExercicio()]);
            holder.lb_line_peso_valor.setText(String.valueOf(history.getPeso()));
            holder.lb_line_repeticoes_valor.setText(String.valueOf(history.getRepeticoes()));
            if (history.isConcluido()) {
                holder.lb_line_concluido.setText(R.string.cb_concluido);
            } else {
                holder.lb_line_concluido.setText(R.string.cb_nao_concluido);
            }

            switch (history.getTipoExercicio()) {
                case Costas:
                    holder.lb_line_tipo_valor.setText(R.string.rb_costas);
                    break;
                case Inferiores:
                    holder.lb_line_tipo_valor.setText(R.string.rb_inferior);
                    break;
                case Abdomem:
                    holder.lb_line_tipo_valor.setText(R.string.rb_abdomem);
                    break;
                case Peitoral:
                    holder.lb_line_tipo_valor.setText(R.string.rb_costas);
                    break;
            }
        } catch (Exception e) {
            System.out.println("DEVLOG");
            System.out.println(e.getStackTrace());
        }

        return convertView;
    }
}
