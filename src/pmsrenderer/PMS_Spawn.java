package maprenderer;

public class PMS_Spawn {
	
	private boolean Active;
	private int x;
	private int y;
	private int Spawn;
	private String SpawnString;
	
	public PMS_Spawn(int Active, int filler, int x, int y, int Spawn) {
		this.Active = Active == 0 ? false : true;
		this.x = x;
		this.y = y;
		this.Spawn = Spawn;
		
		switch(Spawn) {
			case 0: SpawnString = "General";break;
			case 1: SpawnString = "Alpha";break;
			case 2: SpawnString = "Bravo";break;
			case 3: SpawnString = "Charlie";break;
			case 4: SpawnString = "Delta";break;
			case 5: SpawnString = "Alpha Flag";break;
			case 6: SpawnString = "Bravo Flag";break;
			case 7: SpawnString = "Grenades";break;
			case 8: SpawnString = "Medkits";break;
			case 9: SpawnString = "Clusters";break;
			case 10: SpawnString = "Vest";break;
			case 11: SpawnString = "Flamer";break;
			case 12: SpawnString = "Berserker";break;
			case 13: SpawnString = "Predator";break;
			case 14: SpawnString = "Yellow Flag";break;
			case 15: SpawnString = "Rambo Bow";break;
			case 16: SpawnString = "Stat Gun";break;
			default: SpawnString = "Invalid";break;
		}
	}
	
	public boolean getActive() { return Active; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getSpawnType() { return Spawn; }
	public String getSpawnTypeString() { return SpawnString; }
	
}