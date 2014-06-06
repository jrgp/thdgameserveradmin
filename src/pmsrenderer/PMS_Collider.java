package maprenderer;

public class PMS_Collider {

	private boolean Active;
	private float x;
	private float y;
	private float radius;
	
	public PMS_Collider(int Active, int filler, float x, float y, float radius) {
		this.Active = Active == 0 ? false : true;
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public boolean getActive() { return Active; }
	public float getX() { return x; }
	public float getY() { return y; }
	public float getRadius() { return radius; }
}