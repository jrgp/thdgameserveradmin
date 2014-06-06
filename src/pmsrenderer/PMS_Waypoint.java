
package maprenderer;

public class PMS_Waypoint {
	
	private boolean Active;
	private int ID;
	private int x;
	private int y;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private boolean jet;
	private int path;
	private int SpecialAction;
	private String SpecialActionString;
	private int c2;
	private int c3;
	private int numConnections;
	private int[] Connections;
	
	public PMS_Waypoint(int a, int filler, int id, int x, int y, int l, int r, int u, int d, int j, int p, int s, int c2, int c3, int filler2, int nc) {
		this.Active = a == 0 ? false : true;
		this.x = x;
		this.y = y;
		this.ID = id;
		this.left = l == 0 ? false : true;
		this.right = r == 0 ? false : true;
		this.up = u == 0 ? false : true;
		this.down = d == 0 ? false : true;
		this.jet = j == 0 ? false : true;
		this.path = unSign(p);
		this.SpecialAction = s;
		this.c2 = unSign(c2);
		this.c3 = unSign(c3);
		this.numConnections = nc;
		
		switch(this.SpecialAction) {
			case 0: SpecialActionString = "None";break;
			case 1: SpecialActionString = "Stop and Camp";break;
			case 2: SpecialActionString = "Wait 1 Second";break;
			case 3: SpecialActionString = "Wait 5 Seconds";break;
			case 4: SpecialActionString = "Wait 10 Seconds";break;
			case 5: SpecialActionString = "Wait 15 Seconds";break;
			case 6: SpecialActionString = "Wait 20 Seconds";break;
			default: SpecialActionString = "Invalid";break;
		}
	}
	
	public void setConnections(int[] c) {
		this.Connections = c;
	}
	
	public boolean getActive() { return Active; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getID() { return ID; }
	public boolean getLeft() { return left; }
	public boolean getRight() { return right; }
	public boolean getUp() { return up; }
	public boolean getDown() { return down; }
	public boolean getJet() { return jet; }
	public int getPath() { return path; }
	public int getSpecialAction() { return SpecialAction; }
	public String getSpecialActionString() { return SpecialActionString; }
	public int getC2() { return c2; }
	public int getC3() { return c3; }
	public int getNumConnections() { return numConnections; }
	public int[] getConnections() { return Connections; }
	
	private int unSign(int i) {
		if(i<0) {
			i += 256;
		}
		return i;
	}
}