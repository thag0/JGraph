package jgraph;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.RenderingHints;

public class PlotPanel extends JPanel {
    double[] x;
    double[] y;

    Color corBase = Color.BLUE;
    Color corTracejado = Color.GRAY;

    public PlotPanel(int w, int h) {
        setPreferredSize(new Dimension(w, h));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // converte para Graphics2D (melhor qualidade)
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // margem
        int margin = 40;

        // encontra valores min e max
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;

        for (double v : x) { minX = Math.min(minX, v); maxX = Math.max(maxX, v); }
        for (double v : y) { minY = Math.min(minY, v); maxY = Math.max(maxY, v); }

        // desenha eixos
        g2.drawLine(margin, h - margin, w - margin, h - margin); // eixo X
        g2.drawLine(margin, margin, margin, h - margin);         // eixo Y
        
        if (minY <= 0 && maxY >= 0) {
            int y0 = (int) (h - margin - (0 - minY) / (maxY - minY) * (h - 2 * margin));
            g2.setColor(corTracejado);
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0)); // linha tracejada
            g2.drawLine(margin, y0, w - margin, y0);
        }

        // desenha rótulos simples
        g2.drawString(String.valueOf(minX), margin, h - margin + 15);
        g2.drawString(String.valueOf(maxX), w - margin - 20, h - margin + 15);
        g2.drawString(String.valueOf(minY), margin - 30, h - margin);
        g2.drawString(String.valueOf(maxY), margin - 35, margin + 5);

        // plota pontos e linhas
        g2.setColor(corBase);
        int prevX = -1, prevY = -1;

        for (int i = 0; i < x.length; i++) {
            int px = (int) (margin + (x[i] - minX) / (maxX - minX) * (w - 2 * margin));
            int py = (int) (h - margin - (y[i] - minY) / (maxY - minY) * (h - 2 * margin));

            g2.fillOval(px - 3, py - 3, 6, 6);

            if (i > 0) {
                g2.drawLine(prevX, prevY, px, py);
            }

            prevX = px;
            prevY = py;
        }
    }
}
