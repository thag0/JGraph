package src;

import jgraph.PlotFrame;
import jnn.core.Dicionario;
import jnn.core.tensor.Tensor;

public class Main {
	
	static {
		limparConsole();
	}

    public static void main(String[] args) {
        int n = 100;
        double[] x = new double[n];
        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
			x[i] = i + 1 - (n/2);
			double val = x[i] / 10;
			y[i] = func(val);
		}

        PlotFrame pf = new PlotFrame(800, 600);
        pf.plot(x, y);
		pf.legenda(true);
		pf.view();
	}

	static double func(double x) {
		Tensor t = new Tensor(new double[] { x });
		
		return new Dicionario()
		 .getAtivacao("sigmoid")
		 .forward(t)
		 .item();
	}

	static double[] linspace(double inicio, double fim, int elementos) {
		if (elementos < 2) {
			return new double[]{inicio};
		}

		double[] result = new double[elementos];
		double step = (fim - inicio) / (elementos - 1);

		for (int i = 0; i < elementos; i++) {
			result[i] = inicio + i * step;
		}

		return result;
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
