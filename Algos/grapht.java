// A threaded JFrame 
// by Madura A.

import javax.swing.*;
import java.awt.event.*;

class grapht extends Thread {
    result res[];
    graph g;
    JFrame f;
    grapht() {
    }
    grapht(String name, graph gr){
        super(name);
        g = gr;
        f = new JFrame(name);
    }
    public void run(){
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.add(g);
        f.setSize(600,400);
        f.setLocation(200,200);
        f.setVisible(true); //show graph
    }
}
