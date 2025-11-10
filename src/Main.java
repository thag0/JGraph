package src;

import java.awt.Color;

import jgraph.PlotFrame;

public class Main {
	
	static {
		limparConsole();
	}

    public static void main(String[] args) {
        int n = 2000;
		double[] x = new double[n];
        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            x[i] = (i + 1) / 10;
            y[i] = Math.tanh(x[i]);
        }

        PlotFrame pf = new PlotFrame(800, 600);
        pf.plot(x, y, Color.red, "Tanh");
		pf.view();
	}

    /**
     * Limpeza do console
     */
	static void limparConsole() {
		try {
			String nomeSistema = System.getProperty("os.name");

			if (nomeSistema.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				return;
			
			} else {
				for (int i = 0; i < 100; i++) {
					System.out.println();
				}
			}

		} catch(Exception e) {
			return;
		}
	}

}
