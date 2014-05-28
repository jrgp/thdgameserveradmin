/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kag;

import java.net.*;
import java.io.*;
import javax.swing.SwingWorker;
import java.util.List;

class KagNotif {

    public String Type = "";
    public String Line = "";
    public String Event = "";
    
    public KagNotif() {
    }
    
    public static KagNotif lineFactory(String Line) {
        KagNotif notif = new KagNotif();
        notif.Line = Line;
        notif.Type = "line";
        return notif;
    }
    public static KagNotif eventFactory(String Event) {
        KagNotif notif = new KagNotif();
        notif.Event = Event;
        notif.Type = "event";
        return notif;
    }
}

/**
 *
 * @author joe
 */
public class KagSocket extends SwingWorker<Void, KagNotif> {
    
    private Socket Sock = null;
    private BufferedReader In = null;
    private DataOutputStream Out = null;
    
    private KagAdminGUI Window;
    
    private String Host, Password;
    private Integer Port;
    
    public Boolean Connected = false;
    
    public KagSocket () {

    }
    
    public void setWindow(KagAdminGUI Window) {
        this.Window = Window;
    }
    
    public void setDetails(String Host, String Password, Integer Port) {
        this.Host = Host;
        this.Password = Password;
        this.Port = Port;
    }
    
    public void Connect () {
        
        System.out.println(String.format("Connecting to %s:%d with %s", Host, Port, Password));
        
        Connected = false;
        
        try {
            Sock = new Socket(Host, Port);
            In = new BufferedReader(new InputStreamReader(Sock.getInputStream(), "ISO-8859-1"));
            Out = new DataOutputStream(Sock.getOutputStream());
            Out.writeBytes(Password+"\n");
        }
        catch (IOException e) {
            Connected = false;
        }
    }
    
    public void Disconnect() {
        System.out.println("Disconnecting");
        try {
            Sock.close();
            In.close();
            Out.close();
        }
        catch (IOException e) {
        } 
        Connected = false;
        publish(KagNotif.eventFactory("Disconnected"));
    }

    public void sendCommand(String line) {
        if (!Connected) {
            System.out.println("Cannot write if we're not connected");
            return;
        }
        try {
            Out.writeBytes(line+"\n");
        }
        catch (IOException e) {
            System.out.println("Failed writing");
        }
    }
    
    @Override
    protected Void doInBackground() throws Exception {
        System.out.println("in background");
        
        if (Connected) {
            System.out.println("Already connected?");
            return null;
        }
        
        Connect();
        
        String line;
        
        // TCP RCON Connection
        
        try {
            for (;;) {
                
                if (Thread.interrupted()) {
                    System.out.println("got interrupt");
                    throw new InterruptedException();
                }

                if (!In.ready()) {
                    Thread.sleep(100);
                    continue;
                }
                
                if ((line = In.readLine()) == null)
                    break;

                if (!Connected) {
                    Connected = true;
                    publish(KagNotif.eventFactory("Connected"));
                }
                    
                System.out.println("Received line via while");
                line = line.trim();
                publish(KagNotif.lineFactory(line));
            }
        }
        catch (IOException e) {
            
        }
        catch (InterruptedException e) {
            System.out.println("Disconnecting via interrupt");
        }
        finally  {
            Disconnect();
        }
        return null;
    }
    
    @Override
    protected void process(List<KagNotif> messages) {
        
        for (KagNotif notif : messages) {
            if (notif.Type.equals("line")) {
                System.out.println("Recieved line via process: "+notif.Line);
                Window.addConsoleLine(notif.Line);
            }
            else if (notif.Type.equals("event") && notif.Event.equals("Disconnected")) {
                Window.onDisconnect();
            }
            else if (notif.Type.equals("event") && notif.Event.equals("Connected")) {
                Window.onConnect();
            }
       }
    }
}
