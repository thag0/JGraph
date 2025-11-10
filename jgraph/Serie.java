package jgraph;

import java.awt.Color;

public  class Serie {
    double[] x, y;
    Color cor;
    String nome;

    Serie(double[] x, double[] y, Color cor, String nome) {
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.nome = nome;
    }
}
