package maprenderer;

public class PMS_Vertex {
	
    private float x;
    private float y;
    private float z;
    private float rhw;
    private PMS_Color c;
    private float tu;
    private float tv;

    public PMS_Vertex(float x, float y, float z, float rhw, PMS_Color c, float tu, float tv) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rhw = rhw;
        this.c = c;
        this.tu = tu;
        this.tv = tv;
        
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getRHW() { return rhw; }
    public PMS_Color getColor() { return c; }
    public float getTU() { return tu; }
    public float getTV() { return tv; }

}