package maprenderer;

public class PMS_Polygon {
    PMS_Vertex[] vert;
    PMS_Vector[] perpendicular;
    int PolyType;
    String PolyTypeString;

    public PMS_Polygon(PMS_Vertex[] vert, PMS_Vector[] perpendicular, int PolyType) {
        this.vert = vert;
        this.perpendicular = perpendicular;
        this.PolyType = PolyType;

        switch(PolyType) {
            case 0: this.PolyTypeString = "Normal";break;
            case 1: this.PolyTypeString = "Only Bullets Collide";break;
            case 2: this.PolyTypeString = "Only Players Collide";break;
            case 3: this.PolyTypeString = "No Collide";break;
            case 4: this.PolyTypeString = "Ice";break;
            case 5: this.PolyTypeString = "Deadly";break;
            case 6: this.PolyTypeString = "Bloody Deadly";break;
            case 7: this.PolyTypeString = "Hurts";break;
            case 8: this.PolyTypeString = "Regenerates";break;

            // There new poly types which post date this 
            default: this.PolyTypeString = "Normal";break;
        }
    }

    public PMS_Vertex[] getVertexes() {
        return vert;
    }
    
    public PMS_Vertex getVertex(int i) { return vert[i]; }
    public PMS_Vector getPerp(int i) { return perpendicular[i]; }
    public int getPolyType() { return PolyType; }
    public String getPolyTypeString() { return PolyTypeString; }
	
}