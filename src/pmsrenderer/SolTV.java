/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmsrenderer;

import com.jogamp.newt.event.WindowEvent;
import com.jogamp.opengl.util.Animator;
import com.sun.prism.impl.BufferUtil;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import thadmin.SoldatPlayer;
import maprenderer.PMS_Map;
import maprenderer.PMS_Polygon;
import maprenderer.PMS_Vertex;

/**
 *
 * @author joe
 */
public class SolTV {
    
    // complete fucking bullshit. thanks to storm/StormMedia/src/storm/media/textures/Texture.java
    private static boolean changeTexture(String filepath, GL2 gl2) {
        int[] texlist = new int[1];
        gl2.glGenTextures(1, texlist, 0);
        gl2.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        BufferedImage image;
        boolean doTextures = true;
        try {
            image = ImageIO.read(new File(filepath)); 

            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            ByteBuffer buffer = BufferUtil.newByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x]; 
                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    buffer.put((byte) (pixel & 0xFF));               // Blue component
                    buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
                }   
            }   

            buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

            gl2.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, image.getWidth(), image.getHeight(), 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, buffer);                    
            gl2.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
            gl2.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        }
        catch (Exception e) {
            e.printStackTrace();
            doTextures = false;
        }
        return doTextures;
    }
    
    private SoldatPlayer players[];

    public SolTV() {
        players = new SoldatPlayer[32];
    }
    
    public void setPlayers(SoldatPlayer []players) {
        this.players = players;
    }
    
    public void addSamplePlayers() {
      /* SoldatPlayer tmp = new SoldatPlayer(); 
       tmp.name = "jack1";
       tmp.x = 922.45458984375;
       tmp.y = 48.2277946472168;
       players.add(tmp);
       
       tmp = new SoldatPlayer(); 
       tmp.name = "jack2";
       tmp.x = 1025.580322265625;
       tmp.y = -85.0887222290039;
       players.add(tmp);
       
       tmp = new SoldatPlayer(); 
       tmp.name = "jack3";
       tmp.x = -324.0078125;
       tmp.y = 121.7191162109375;
       players.add(tmp);*/
    }
    
    private GLCanvas canvas = null;
    private Animator animator;
    
    private JFrame frame;
    
    public void render(String mapName) {

        final String soldatDir = "/Users/joe/Downloads/yarr";

        String mapPath = soldatDir+"/maps/"+mapName+".pms";
        
        
        final PMS_Map map = new PMS_Map(new File(mapPath));
 
        final String texturepath = soldatDir+"/textures/"+map.getTexture();

        // positional offsets
        final int topoffs = map.getSectorDivision() * map.getSectorCount();
        final int bottomoffs = map.getSectorDivision() * -map.getSectorCount();
        final int rightoffs = map.getSectorDivision() * map.getSectorCount();
        final int leftoffs = map.getSectorDivision() * -map.getSectorCount();
        final double sfactor = 1.0;

        // make canvas?
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        canvas = new GLCanvas(glcapabilities);

        canvas.addGLEventListener(new GLEventListener() {

            @Override
            public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {

            }

            @Override
            public void init(GLAutoDrawable glautodrawable) {
            }

            @Override
            public void dispose(GLAutoDrawable glautodrawable) {
            }

            @Override
            public void display(GLAutoDrawable glautodrawable) {

                // init stuff (dunno how it works)
                GLU glu = new GLU();
                GL2 gl2 = glautodrawable.getGL().getGL2();
                gl2.glClearColor((float) 0.0, (float) 0.0, (float) 0.0, (float) 0.0);
                gl2.glColor3f((float) 0.0, (float) 0.0, (float) 1.0);
                gl2.glMatrixMode(GL2.GL_MODELVIEW);
                gl2.glLoadIdentity();
               // glu.gluOrtho2D(-3000.0, 3000.0, 3000.0, -3000.0);
                glu.gluOrtho2D(leftoffs, rightoffs, -bottomoffs, -topoffs);
                gl2.glEnable(GL2.GL_BLEND);
                gl2.glEnable(GL2.GL_TEXTURE_2D);
                gl2.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
                // init stuff end

                // background poly (i know how it works)
                gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
                gl2.glDisable(GL2.GL_TEXTURE_2D);
                gl2.glBegin(GL2.GL_POLYGON);
                gl2.glColor4f((float) (map.getTopColor().getR() / 255.0),
                        (float) (map.getTopColor().getG() / 255.0),
                        (float) (map.getTopColor().getB() / 255.0),
                        (float) (map.getTopColor().getA() / 255.0));
                gl2.glVertex2f(leftoffs, -topoffs);
                gl2.glVertex2f(rightoffs, -topoffs);
                gl2.glColor4f((float) (map.getBottomColor().getR() / 255.0),
                        (float) (map.getBottomColor().getG() / 255.0),
                        (float) (map.getBottomColor().getB() / 255.0),
                        (float) (map.getBottomColor().getA() / 255.0));
                gl2.glVertex2f(rightoffs, -bottomoffs);
                gl2.glVertex2f(leftoffs, -bottomoffs);
                gl2.glEnd();
                gl2.glEnable(GL2.GL_TEXTURE_2D);
                // done background poly

                // textures. 
                boolean doTextures = changeTexture(texturepath, gl2);
                // end textures
                
                // polygons (kinda understand how it works)
                gl2.glBegin(GL.GL_TRIANGLES);

                for (PMS_Polygon poly : map.getAllPolys()) {

                    for (PMS_Vertex vert : poly.getVertexes()) {
                        gl2.glColor4f((float) (vert.getColor().getR() / 255.0),
                                (float) (vert.getColor().getG() / 255.0),
                                (float) (vert.getColor().getB() / 255.0),
                                (float) (vert.getColor().getA() / 255.0));
                        
                        if (doTextures) {
                            gl2.glTexCoord3f(vert.getTU(), -vert.getTV(), 1 / vert.getRHW());
                        }
                        
                        gl2.glVertex3f(vert.getX(), vert.getY(), vert.getZ());
                    }
                }

                gl2.glEnd();
                // end polygons

                // lets try scenery... I know this is fucked.
                /*for (PMS_Prop prop: map.getAllProps()) {
                    String sceneryFileName = map.getSceneryName(prop.getPropStyle()-1);
                    String sceneryPathName = soldatDir+"/scenery-gfx/"+sceneryFileName;
                    
                    if (!new File(sceneryPathName).exists()) {
                        System.out.println(sceneryFileName+" does not exist");
                        continue;
                    }
                    
                    gl2.glPushMatrix();
                    gl2.glTranslatef(prop.getX(), prop.getY(), 0);
                    gl2.glRotatef((float) (prop.getRotation() * (180 / 3.14)), 0, 0, 1);
                    gl2.glScalef(prop.getScaleX(), prop.getScaleY(), 0);
                    if (changeTexture(sceneryPathName, gl2)) {
                        gl2.glBegin(GL2.GL_QUADS);
                       
                        gl2.glTexCoord2f(0, 0);
                        gl2.glVertex2f(0, 0);
                       
                        gl2.glTexCoord2f(1, 0);
                        gl2.glVertex2f(prop.getWidth(), 0);
                        
                        gl2.glTexCoord2f(1, 1);
                        gl2.glVertex2f(prop.getWidth(), prop.getHeight());
                        
                        gl2.glTexCoord2f(0, 1);
                        gl2.glVertex2f(0, prop.getHeight());
                        
                        gl2.glEnd();
                    }
                    else {
                        System.out.println("Skipping "+sceneryFileName);
                    }
                    gl2.glPopMatrix();
                }*/
                
                // Some players?
        
                for (SoldatPlayer player : players) {
                    if (player == null)
                        continue;
                    if (player.name.equals(""))
                        continue;
                    //System.out.println(player.name);
                    String iconPath = soldatDir+"/icon_player.png";
                    gl2.glPushMatrix();
                    gl2.glTranslatef((float)player.x, (float)player.y, 0);
                    //gl2.glRotatef((float) (prop.getRotation() * (180 / 3.14)), 0, 0, 1);
                    //gl2.glScalef(prop.getScaleX(), prop.getScaleY(), 0);
                    int width = 14, height = 24;
                    if (changeTexture(iconPath, gl2)) {
                        gl2.glBegin(GL2.GL_QUADS);
                       
                        gl2.glTexCoord2f(0, 0);
                        gl2.glVertex2f(0, 0);
                       
                        gl2.glTexCoord2f(1, 0);
                        gl2.glVertex2f(width, 0);
                        
                        gl2.glTexCoord2f(1, 1);
                        gl2.glVertex2f(width, height);
                        
                        gl2.glTexCoord2f(0, 1);
                        gl2.glVertex2f(0, height);
                        
                        gl2.glEnd();
                    }
                    else {
                        System.out.println("Skipping "+iconPath);
                    }        
                    gl2.glPopMatrix();
                }
                
                // other stuff (not sure how it works)
                gl2.glLoadIdentity();
                glu.gluOrtho2D(leftoffs * sfactor, rightoffs * sfactor, topoffs * sfactor, bottomoffs * sfactor);
                gl2.glTranslatef(0, 0, 0);
                gl2.glFlush();
                // end other stuff

            }
        });
        animator = new Animator(canvas);
        animator.start();
        
        frame = new JFrame(map.getTitle());
    //    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                System.out.println("closing?");
                frame.dispose();
                System.exit(0);
            }
        });

        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
    
    public void kill() {
        animator.stop();
        canvas.destroy();
        frame.removeAll();
        frame.dispose();
    }
}
