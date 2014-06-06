
package maprenderer;

import java.awt.Color;
public class PMS_Color {

    private int r;
    private int g;
    private int b;
    private int a;

    public PMS_Color(int b, int g, int r, int a) {
        this.r = unSign(r);
        this.g = unSign(g);
        this.b = unSign(b);
        this.a = unSign(a);
    }

    public Color getColor() {
        return new Color(r,g,b,a);
    }
    
    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }
    
    public int getA() {
        return a;
    }
    
    private int unSign(int i) {
        if(i<0) {
                i += 256;
        }
        return i;
    }
}