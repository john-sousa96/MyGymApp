package com.example.mygym;

import java.util.Date;

public class History {

    private Date data;
    private TipoExercicio tipoExercicio;
    private int exercicio;
    private double peso;
    private int repeticoes;
    private boolean concluido;

    public History(Date data, TipoExercicio tipoExercicio, int exercicio, double peso, int repeticoes, boolean concluido) {
        this.data = data;
        this.tipoExercicio = tipoExercicio;
        this.exercicio = exercicio;
        this.peso = peso;
        this.repeticoes = repeticoes;
        this.concluido = concluido;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public TipoExercicio getTipoExercicio() {
        return tipoExercicio;
    }

    public void setTipoExercicio(TipoExercicio tipoExercicio) {
        this.tipoExercicio = tipoExercicio;
    }

    public int getExercicio() {
        return exercicio;
    }

    public void setExercicio(int exercicio) {
        this.exercicio = exercicio;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    @Override
    public String toString() {
        return "History{" +
                "data=" + data +
                ", tipoExercicio=" + tipoExercicio +
                ", exercicio='" + exercicio + '\'' +
                ", peso=" + peso +
                ", repeticoes=" + repeticoes +
                ", concluido=" + concluido +
                '}';
    }
}
