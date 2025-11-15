package src;

import jgraph.PlotFrame;
import jnn.camadas.Densa;
import jnn.camadas.Entrada;
import jnn.core.tensor.Tensor;
import jnn.dataloader.DataLoader;
import jnn.modelos.Sequencial;

public class Playground {
    public static void main(String[] args) {
        DataLoader train = xor();

        Sequencial model = new Sequencial(
            new Entrada(2),
            new Densa(3, "sigmoid"),
            new Densa(1, "sigmoid")
        );
        model.compilar("sgd", "mse");

        int epochs = 1 * 1000;
        model.setHistorico(true);
        model.treinar(train, epochs, true);
        System.out.println("loss: " + model.avaliar(train.getX(), train.getY()));

        double[] axisX = new double[epochs];
        for (int i = 0; i < epochs; i++) axisX[i] = i+1;

        PlotFrame pf = new PlotFrame(800, 600);
        pf.plot(axisX, model.hist());
        pf.view();
    }

    static DataLoader xor() {
        DataLoader loader = new DataLoader();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                var x = new Tensor(new double[]{ i, j });
                var y = new Tensor(new double[]{ i^j });
                loader.add(x, y);
            }
        }

        return loader;
    }
}
