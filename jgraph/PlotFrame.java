package jgraph;

import java.awt.Color;

import javax.swing.JFrame;

public class PlotFrame extends JFrame {
    PlotPanel pp;

    public PlotFrame(int w, int h) {
        setSize(w, h);
        setTitle("PlotFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        pp = new PlotPanel(w, h);
        add(pp);
    }

    public void plot(double[] x, double[] y, Color cor) {
        pp.x = x;
        pp.y = y;
        pp.corBase = cor;

        setVisible(true);
    }

    public void plot(double[] x, double[] y) {
        plot(x, y, pp.corBase);
    }

}
