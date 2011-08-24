// graph drawer 
// by Madura A.

import java.awt.*;
import java.util.Random;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

class graph extends JPanel {
    result r[];
    int m[];
    Color colors[];
    int deltax = 60;
    int deltay = 30;
    int max_val;
    Random rn;
    boolean cummu;
    graph(result dataset[], int methods[], boolean cummulative){
        r = dataset;
        m = methods;
        cummu = cummulative;
        max_val = max();
        rn = new Random();
        colors = new Color[r[0].time.length];
        int x;
        Color h = new Color(0,0,0);
        for (int i=0;i<colors.length;i++){
            colors[i] = h.getHSBColor((i+1.0f)/(colors.length+1.0f),0.6f,8.0f);
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(Color.BLACK);
        int w = getWidth();
        int h = getHeight();
        // draw background
        g2.fill(new Rectangle2D.Double(0.0,0.0,(double)w, (double)h));
        g2.setPaint(Color.WHITE);
        // draw axis
        g2.draw(new Line2D.Double(deltax, 0, deltax, h-deltay));
        g2.draw(new Line2D.Double(deltax, h-deltay, w, h-deltay));
        double x,y;
        double inc = (double)(w - 2*deltax)/(r.length-1);
        double scale = (double)(h - 2*deltay)/max_val;
        int deltasuby = (max_val>20) ? max_val/20 : 1;
        x = deltax - 50;
        // draw axis text
        for (int i=0;i<max_val;i+=deltasuby){
            y = h - deltay - scale*i;
            g2.drawString(i+"",(float)x,(float)y);
        }
        g2.drawString(max_val+"",(float)x,(float)(deltay));
        int deltasubx = (r.length>30) ? r.length/30 : 1;
        y = h - deltay+15;
        for (int i=0;i<r.length-1;i+=deltasubx){
            x = deltax + i*inc;
            g2.drawString(i+"",(float)x,(float)y);
        }
        g2.drawString(r.length-2+"",(float)(deltax + (r.length-2)*inc),(float)y);
        g2.setFont(new Font("Monospace",Font.PLAIN,11));
        sort s = new sort();
        for (int i=0;i<m.length;i++){
            g2.setPaint(colors[m[i]]);
            g2.fill(new Rectangle2D.Double(w-270, 10+20.0*i, 10.0, 10.0));
            g2.drawString(s.sorts[m[i]],w-250,20*(i+1));
        }
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setStroke(new BasicStroke(1));
        int cummu_arr[] = new int[m.length];
        for (int i=0;i<m.length;i++){
            g2.setPaint(colors[m[i]]);
            double prevx = deltax;
            double prevy = h - deltay - scale*r[0].time[m[i]];
            for (int j=0;j<r.length-1;j++){
                x = deltax + j*inc;
                
                if ( r[j].time[m[i]]<0)
                    continue;
                if (cummu == true){
                    cummu_arr[i] += r[j].time[m[i]];
                    y = h - deltay - scale*(cummu_arr[i]);
                } else
                    y = h - deltay - scale*r[j].time[m[i]];
                g2.draw(new Line2D.Double(prevx, prevy, x, y));
                prevx = x;
                prevy = y;
            }
        }
    }

    private int max() { // find max of a dataset
        long a = 0;
        if (cummu == false){
            a = r[0].time[0];
            for (int j=0;j<r.length-1;j++){
                for (int i=0;i<r[j].time.length;i++){
                    if (a<r[j].time[i])
                        a = r[j].time[i];
                }
            }
        } else {
            a = r[r.length-1].time[m[0]];
            for (int j=0;j<m.length;j++){
                if ( a < r[r.length -1].time[m[j]] )
                    a = r[r.length -1].time[m[j]];
            }
        }
        return (int)a;
    }

    
}
