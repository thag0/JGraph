package jgraph;

import java.awt.Color;

public class ColorIterator {
    Color[] cores = {
        Color.blue,
        Color.red,
        Color.green,
        Color.orange,
        Color.yellow,
        Color.cyan,
        Color.magenta
    };

    int id = 0;

    public Color next() {
        id++;
        if (id >= cores.length) id = 0;
        return cores[id];
    }  
}
