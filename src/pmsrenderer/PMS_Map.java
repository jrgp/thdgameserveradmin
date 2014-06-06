
package maprenderer;

/*
Soldat PMS Parser by iDante
Use and abuse it however you want, just leave this here.

I'll just go ahead and define the other classes I use here as well as this one:

//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\
PMS_Map
	String getTitle()
	String getTexture()
	PMS_Color getTopColor()
	PMS_Color getBottomColor()
	int getJets()
	int getGrenades()
	int getMedKits()
	int getWeather()
	int getStepType()
	
	int getPolyCount()
	PMS_Polygon getPoly(int)
	PMS_Polygon[] getAllPolys()
	
	int getPropCount()
	PMS_Prop getProp(int)
	PMS_Prop[] getAllProps()
	
	int getSceneryCount()
	String getSceneryName(int)
	String[] getAllSceneryNames()
	int getDOSTime(int i)
	int[] getAllDOSTime()
	int getDOSDate(int i)
	int[] getAllDOSDate()
	
	int getColliderCount()
	PMS_Collider getCollider(int)
	PMS_Collider[] getAllColliders()
	
	int getSpawnCount()
	PMS_Spawn getSpawn(int i)
	PMS_Spawn[] getAllSpawns()
	
	int getWayPointCount()
	PMS_Waypoint getWayPoint(int)
	PMS_Waypoint[] getAllWayPoints()
//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\
PMS_Color
	Color getColor()
//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\
PMS_Collider
	boolean getActive()
	float getX()
	float getY()
	float getRadius()
//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\
PMS_Prop
	boolean getActive()
	int getPropStyle()
	int getWidth()
	int getHeight()
	float getX()
	float getY()
	float getRotation()
	float getScaleX()
	float getScaleY()
	int getAlpha()
	PMS_Color getPMS_Color()
	int getDrawBehind()
	String getDrawBehindString()
//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\
PMS_Vertex
	float getX()
	float getY()
	float getZ()
	float getRHW()
	PMS_Color getPMS_Color();
	float getTU()
	float getTV()
//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\
PMS_Vector
	float getX()
	float getY()
	float getZ()
//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\
PMS_Polygon
	PMS_Vertex getVertex(int)
	PMS_Vector getPerp(int)
	int getPolyType()
	String getPolyTypeString()
//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\
PMS_Spawn
	boolean getActive()
	int getX()
	int getY()
	int getSpawnType()
	String getSpawnTypeString()
//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\
PMS_Waypoint
	boolean getActive()
	int getX()
	int getY()
	int getID()
	boolean getLeft()
	boolean getRight()
	boolean getUp()
	boolean getDown()
	boolean getJet()
	int getPath()
	int getSpecialAction()
	String getSpecialActionString()
	int getC2()
	int getC3()
	int getNumConnections()
//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\

	
*/

import java.io.*;
import java.nio.*;
public class PMS_Map {
	
	// Options
	private String Title = ""; // The short description of the map.
	private String Texture = "";
	private PMS_Color TopColor;
	private PMS_Color BottomColor;
	private int Jets;
	private int Grenades;
	private int MedKits;
	private int Weather; // 0=None, 1=Rain, 2=Sandstorm, 3=Snow
	private int StepType; // 0=Hard,1=Soft,2=None
	
	public String getTitle() { return Title; }
	public String getTexture() { return Texture; }
	public PMS_Color getTopColor() { return TopColor; }
	public PMS_Color getBottomColor() { return BottomColor; }
	public int getJets() { return Jets; }
	public int getGrenades() { return Grenades; }
	public int getMedKits() { return MedKits; }
	public int getWeather() { return Weather; }
	public int getStepType() { return StepType; }
	
	// Poly Data
	private int PolyCount;
	private PMS_Polygon[] Polys;
	
	public int getPolyCount() { return PolyCount; }
	public PMS_Polygon getPoly(int i) { return Polys[i]; }
	public PMS_Polygon[] getAllPolys() { return Polys; }
	
	// Prop Data
	private int PropCount;
	private PMS_Prop[] Props;

	public int getPropCount() { return PropCount; }
	public PMS_Prop getProp(int i) { return Props[i]; }
	public PMS_Prop[] getAllProps() { return Props; }
	
	// Scenery
	private int SceneryCount;
	private String[] SceneryNames;
	private int[] DOSTime;
	private int[] DOSDate;
	
	public int getSceneryCount() { return SceneryCount; }
	public String getSceneryName(int i) { return SceneryNames[i]; }
	public String[] getAllSceneryNames() { return SceneryNames; }
	public int getDOSTime(int i) { return DOSTime[i]; }
	public int[] getAllDOSTime() { return DOSTime; }
	public int getDOSDate(int i) { return DOSDate[i]; }
	public int[] getAllDOSDate() { return DOSDate; }
	
	// Collider
	private int ColliderCount;
	private PMS_Collider[] Colliders;
	
	public int getColliderCount() { return ColliderCount; }
	public PMS_Collider getCollider(int i) { return Colliders[i]; }
	public PMS_Collider[] getAllColliders() { return Colliders; }
	
	// SpawnPoints
	private int SpawnpointCount;
	private PMS_Spawn[] Spawns;
	
	public int getSpawnCount() { return SpawnpointCount; }
	public PMS_Spawn getSpawn(int i) { return Spawns[i]; }
	public PMS_Spawn[] getAllSpawns() { return Spawns; }
	
	// Waypoints
	private int WayPointCount;
	private PMS_Waypoint[] wpts;

	public int getWayPointCount() { return WayPointCount; }
	public PMS_Waypoint getWayPoint(int i) { return wpts[i]; }
	public PMS_Waypoint[] getAllWayPoints() { return wpts; }
	
	// Non-important fields
	private int SceneryLength;
	private int TitleLength;
	private int TextureLength;
	private int SectorCount;
	private int SectorPolyCount;
        private int SectorDivision;
	
        public int getSectorCount() {
            return SectorCount;
        }
        
        public int getSectorDivision() {
            return SectorDivision;
        }        
        
	// Constructors
	public PMS_Map(String Path) {
		this(new File(Path));
	}
	public PMS_Map(File map) {
		try {
			ByteBuffer buf = ByteBuffer.wrap(getBytesFromFile(map));
			buf.order(ByteOrder.LITTLE_ENDIAN);
			this.read(buf);
		} catch(IOException e) {
			System.err.println(e);
		}
	}
	
	private void read(ByteBuffer reader) throws IOException {
		reader.getInt();
		
		// TitleLength and Title
		this.TitleLength = reader.get();
		for(int i=0;i<this.TitleLength;i++) {
			this.Title = this.Title + (char)reader.get();
		}
		for(int i=0;i<38-this.TitleLength;i++) {
			reader.get(); // Padding
		}
		
		// TextureLength and Texture
		TextureLength = reader.get();
		for(int i=0;i<this.TextureLength;i++) {
			this.Texture = this.Texture + (char)reader.get();
		}
		for(int i=0;i<24-this.TextureLength;i++) {
			reader.get(); // Padding
		}
		
		// Top and bottom colors
		this.TopColor = new PMS_Color(reader.get(), reader.get(), reader.get(), reader.get());
		this.BottomColor = new PMS_Color(reader.get(), reader.get(), reader.get(), reader.get());		

		this.Jets = reader.getInt();
		this.Grenades = reader.get();
		this.MedKits = reader.get();
		this.Weather = reader.get();
		this.StepType = reader.get();
		
		// Random ID, evidently used to be used to check for wrong map version, now useless
		reader.getInt();
		
		this.PolyCount = reader.getInt();
		this.Polys = new PMS_Polygon[this.PolyCount];
		// Read the polygons
		for(int i=0;i<this.PolyCount;i++) {
			PMS_Vertex[] TVert = new PMS_Vertex[3];
			PMS_Vector[] TVect = new PMS_Vector[3];
			TVert[0] = new PMS_Vertex(reader.getFloat(), reader.getFloat(), reader.getFloat(), reader.getFloat(), new PMS_Color(reader.get(), reader.get(), reader.get(), reader.get()), reader.getFloat(), reader.getFloat());
			TVert[1] = new PMS_Vertex(reader.getFloat(), reader.getFloat(), reader.getFloat(), reader.getFloat(), new PMS_Color(reader.get(), reader.get(), reader.get(), reader.get()), reader.getFloat(), reader.getFloat());
			TVert[2] = new PMS_Vertex(reader.getFloat(), reader.getFloat(), reader.getFloat(), reader.getFloat(), new PMS_Color(reader.get(), reader.get(), reader.get(), reader.get()), reader.getFloat(), reader.getFloat());
			TVect[0] = new PMS_Vector(reader.getFloat(), reader.getFloat(), reader.getFloat());
			TVect[1] = new PMS_Vector(reader.getFloat(), reader.getFloat(), reader.getFloat());
			TVect[2] = new PMS_Vector(reader.getFloat(), reader.getFloat(), reader.getFloat());
			this.Polys[i] = new PMS_Polygon(TVert, TVect, reader.get());
		}
		
		this.SectorDivision = reader.getInt(); // Padding
		
		// SectorCount
		this.SectorCount = reader.getInt();
		// Each Individual Sector
		for(int i=0;i<((SectorCount*2)+1)*((SectorCount*2)+1);i++) {
			this.SectorPolyCount = reader.getShort();
			if(this.SectorPolyCount > 0) {
				for(int p=0;p<this.SectorPolyCount;p++) {
					// Save this to an array if you need it.
					reader.getShort();
				}
			}
		}
		
		// Prop Count
		this.PropCount = reader.getInt();
		this.Props = new PMS_Prop[this.PropCount];
		
		for(int i=0;i<this.PropCount;i++) {
			
			this.Props[i] = new PMS_Prop(reader.get(), reader.get(), reader.getShort(), reader.getInt(), reader.getInt(), reader.getFloat(), reader.getFloat(), reader.getFloat(), reader.getFloat(), reader.getFloat(), reader.get(), reader.getShort()+reader.get(), new PMS_Color(reader.get(), reader.get(), reader.get(), reader.get()), reader.get());
			reader.getShort();
			reader.get();
			
		}
		
		// Scenerys
		this.SceneryCount = reader.getInt();
		
		SceneryNames = new String[this.SceneryCount];
		DOSTime = new int[this.SceneryCount];
		DOSDate = new int[this.SceneryCount];
		
		for(int i=0;i<this.SceneryCount;i++) {
			this.SceneryNames[i] = "";
			this.SceneryLength = (int)reader.get();
			for(int p=0;p<this.SceneryLength;p++) {
				this.SceneryNames[i] = this.SceneryNames[i] + (char)reader.get();
			}
			for(int p=0;p<50-this.SceneryLength;p++) {
				reader.get();
			}
			// I still don't know what this is for. Best just leave it.
			DOSTime[i] = reader.getShort();
			DOSDate[i] = reader.getShort();
		}
		
		// Colliders... One knows the drill...
		this.ColliderCount = reader.getInt();
		Colliders = new PMS_Collider[this.ColliderCount];
		
		for(int i=0;i<this.ColliderCount;i++) {
		
			Colliders[i] = new PMS_Collider(reader.get(), reader.getShort()+reader.get(), reader.getFloat(), reader.getFloat(), reader.getFloat());
		
		}

		// Spawnpoints...
		this.SpawnpointCount = reader.getInt();
		Spawns = new PMS_Spawn[this.SpawnpointCount];
		
		for(int i=0;i<this.SpawnpointCount;i++) {
		
			Spawns[i] = new PMS_Spawn(reader.get(), reader.getShort()+reader.get(),reader.getInt(),reader.getInt(),reader.get());
			reader.getShort();
			reader.get();
		}
		
		// Waypoints
		this.WayPointCount = reader.getInt();
		this.wpts = new PMS_Waypoint[this.WayPointCount];
		for(int i=0;i<this.WayPointCount;i++) {
		
			wpts[i] = new PMS_Waypoint(reader.get(), reader.getShort()+reader.get(),reader.getInt(),reader.getInt(),reader.getInt(),reader.get(),reader.get(),reader.get(),reader.get(),reader.get(),reader.get(),reader.get(),reader.get(),reader.get(),reader.getShort()+reader.get(),reader.getInt());
			for(int p=0;p<20;p++) {
				// Connections. Save to an array if you need
				reader.getInt();
			}
		}
	}

	// I didn't actually write this function. The author didn't give any
	// 	info as to liscensing, so... to be safe heres the URL
	//		http://www.exampledepot.com/egs/java.io/File2ByteArray.html
 	// Returns the contents of the file in a byte array.
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            System.out.println("File too large");
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        is.close();
        return bytes;
    }
}
