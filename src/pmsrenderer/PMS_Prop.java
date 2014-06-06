package maprenderer;

public class PMS_Prop {
	
	private boolean Active;
	private int PropStyle;
	private int Width;
	private int Height;
	private float x;
	private float y;
	private float rotation;
	private float sx;
	private float sy;
	private int alpha;
	private PMS_Color c;
	private int DrawBehind;
	private String DrawBehindString;
	
	public PMS_Prop(int Active, int filler, int style, int w, int h, float x, float y, float r, float sx, float sy, int alpha, int filler2, PMS_Color c, int d) {
		this.Active = Active == 0 ? false : true;
		this.PropStyle = style;
		this.Width = w;
		this.Height = h;
		this.x = x;
		this.y = y;
		this.rotation = r;
		this.sx = sx;
		this.sy = sy;
		this.alpha = alpha;
		this.c = c;
		this.DrawBehind = d;
		
		switch(d) {
			case 0: this.DrawBehindString = "Behind All";break;
			case 1: this.DrawBehindString = "Behind Map";break;
			case 2: this.DrawBehindString = "Behind None";break;
			default: this.DrawBehindString = "Invalid";break;
		}
	}
	
	public boolean getActive() { return Active; }
	public int getPropStyle() { return PropStyle; }
	public int getWidth() { return Width; }
	public int getHeight() { return Height; }
	public float getX() { return x; }
	public float getY() { return y; }
	public float getRotation() { return rotation; }
	public float getScaleX() { return sx; }
	public float getScaleY() { return sy; }
	public int getAlpha() { return alpha; }
	public PMS_Color getPMS_Color() { return c; }
	public int getDrawBehind() { return DrawBehind; }
	public String getDrawBehindString() { return DrawBehindString; }
}