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

    public void view() {
        setVisible(true);
    }

    public void plot(double[] x, double[] y, Color color, String title) {
        pp.addPlot(x, y, color, title);
    }

    public void plot(double[] x, double[] y, Color color) {
        pp.addPlot(x, y, color);
    }

    public void plot(double[] x, double[] y) {
        pp.addPlot(x, y);
    }

    public void legenda(boolean mostrar) {
        pp.legend(mostrar);
    }

}
