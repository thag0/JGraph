package jgraph;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.util.ArrayList;

public class PlotPanel extends JPanel {
    private ArrayList<Serie> series = new ArrayList<>();
    private int margin = 50;
    private Color corTracejado = Color.darkGray;
    private Color corBackground = new Color(180, 180, 180);
    private boolean mostrarLegenda = false;
    
    private int xTicks = 8;// valor padrão
    private int yTicks = 8;// valor padrão

    private ColorIterator ci = new ColorIterator();

    public PlotPanel(int w, int h) {
        setBackground(corBackground);
        setPreferredSize(new Dimension(w, h));
    }

    public void addPlot(double[] x, double[] y, Color cor, String nome) {
        series.add(new Serie(x, y, cor, nome));
        repaint();
    }

    public void addPlot(double[] x, double[] y, Color cor) {
        series.add(new Serie(x, y, cor, "Serie-" + series.size()));
    }

    public void addPlot(double[] x, double[] y) {
        addPlot(x, y, ci.next());
    }

    public void setXTicks(int ticks) {
        if (ticks > 1) {
            xTicks = ticks;
        }
    }

    public void legend(boolean mostrar) {
        mostrarLegenda = mostrar;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (series.size() < 1) {
            throw new IllegalStateException(
                "\nNenhuma série de dados para plotar."
            );
        }

        super.paintComponent(g);
        if (series.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // encontra min/max globais entre todas as séries
        double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE, maxY = -Double.MAX_VALUE;

        for (Serie s : series) {
            for (double v : s.x) { 
                minX = Math.min(minX, v);
                maxX = Math.max(maxX, v);
            }
            for (double v : s.y) { 
                minY = Math.min(minY, v);
                maxY = Math.max(maxY, v);
            }
        }

        drawAxes(g2, w, h);

        draw0AxisLines(g2, w, h, minX, maxX, minY, maxY);

        drawXTicks(g2, w, h, minX, maxX);
        drawYTicks(g2, w, h, minY, maxY);

        for (Serie s : series) {
            drawSerie(g2, s, w, h, minX, maxX, minY, maxY);
        }

        if (mostrarLegenda) drawLegend(g2);
    }

    private int toPixelX(double x, int w, double minX, double maxX) {
        return (int) Math.round(
            margin + (x - minX) / (maxX - minX) * (w - 2 * margin)
        );
    }

    private int toPixelY(double y, int h, double minY, double maxY) {
        return (int) Math.round(
            h - margin - (y - minY) / (maxY - minY) * (h - 2 * margin)
        );
    }

    /**
     * Desenha linhas tracejadas para séries com valores positivos e negativos.
     * @param g2
     * @param w
     * @param h
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     */
    private void draw0AxisLines(Graphics2D g2, int w, int h, double minX, double maxX, double minY, double maxY) {
        boolean linhaX = minX <= 0 && maxX >= 0;
        boolean linhaY = minY <= 0 && maxY >= 0;

        g2.setColor(corTracejado);
        BasicStroke stroke = new BasicStroke(
            1.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
            0, new float[]{5}, 0
        );
        g2.setStroke(stroke);

        if (linhaX) {
            int x0 = toPixelX(0, w, minX, maxX);
            g2.drawLine(x0, margin, x0, h - margin);
        }
        if (linhaY) {
            int y0 = toPixelY(0, h, minY, maxY);
            g2.drawLine(margin, y0, w - margin, y0);
        }

        g2.setColor(Color.black);
        if (linhaX && linhaY) {
            int x0 = toPixelX(0, w, minX, maxX);
            int y0 = toPixelY(0, h, minY, maxY);
            g2.drawString("0", x0 + 5, y0 - 5);
        
        } else if (linhaX) {
            int x0 = toPixelX(0, w, minX, maxX);
            g2.drawString("0", x0 + 5, h - margin + 15);
        
        } else if (linhaY) {
            int y0 = toPixelY(0, h, minY, maxY);
            g2.drawString("0", margin + 5, y0 - 5);
        }
    }

    /**
     * Desenha ticks no eixo horizontal.
     * @param g2
     * @param w
     * @param h
     * @param minX
     * @param maxX
     */
    private void drawXTicks(Graphics2D g2, int w, int h, double minX, double maxX) {
        double step = (maxX - minX) / xTicks;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1f));

        for (int i = 0; i <= xTicks; i++) {
            double valor = minX + i * step;
            int px = toPixelX(valor, w, minX, maxX);

            g2.drawLine(px, h - margin, px, h - margin + 6);//tick

            String label = String.format("%.2f", valor);
            g2.drawString(label, px - 10, h - margin + 20);
        }
    }

    /**
     * Desenha ticks no eixo vertical.
     * @param g2
     * @param w
     * @param h
     * @param minY
     * @param maxY
     */
    private void drawYTicks(Graphics2D g2, int w, int h, double minY, double maxY) {
        double step = (maxY - minY) / yTicks;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1f));

        for (int i = 0; i <= yTicks; i++) {
            double valor = minY + i * step;

            int py = toPixelY(valor, h, minY, maxY);

            g2.drawLine(margin - 6, py, margin, py);// tick

            String label = String.format("%.2f", valor);
            g2.drawString(label, margin - 40, py + 5);
        }
    }

    /**
     * Desenha o eixo X e Y do gráfico.
     * @param g2
     * @param w
     * @param h
     */
    private void drawAxes(Graphics2D g2, int w, int h) {
        g2.setColor(Color.BLACK);
        g2.drawLine(margin, h - margin, w - margin, h - margin);// X
        g2.drawLine(margin, margin, margin, h - margin);// Y
    }

    /**
     * Desenha uma série de dados.
     * @param g2
     * @param s
     * @param w
     * @param h
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     */
    private void drawSerie(Graphics2D g2, Serie s, int w, int h, double minX, double maxX, double minY, double maxY) {
        g2.setColor(s.cor);
        g2.setStroke(new BasicStroke(2f));

        int prevX = -1, prevY = -1;
        for (int i = 0; i < s.x.length; i++) {
            int px = (int) (margin + (s.x[i] - minX) / (maxX - minX) * (w - 2 * margin));
            int py = (int) (h - margin - (s.y[i] - minY) / (maxY - minY) * (h - 2 * margin));

            if (i > 0) g2.drawLine(prevX, prevY, px, py);
            g2.fillOval(px - 2, py - 2, 4, 4);

            prevX = px;
            prevY = py;
        }
    }

    /**
     * Desenha legenda.
     * @param g2
     */
    private void drawLegend(Graphics2D g2) {
        int w = getWidth();
        int legendX = w - margin - 100;
        int legendY = margin + 10;
        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
    
        for (Serie s : series) { 
            g2.setColor(s.cor);
            g2.fillRect(legendX, legendY - 10, 15, 8);
            g2.setColor(Color.BLACK);
            g2.drawString(s.nome, legendX + 20, legendY); 
            legendY += 15;
        }
    }
}
