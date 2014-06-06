/*
 * Copyright (C) 2014 joe
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package thadmin;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.regex.Matcher;
import javax.swing.SwingWorker;

class SoldatNotif {

    public String Type = "";
    public String Line = "";
    public String Event = "";
    
    public SoldatNotif() {
    }
    
    public static SoldatNotif lineFactory(String Line) {
        SoldatNotif notif = new SoldatNotif();
        notif.Line = Line;
        notif.Type = "line";
        return notif;
    }
    public static SoldatNotif eventFactory(String Event) {
        SoldatNotif notif = new SoldatNotif();
        notif.Event = Event;
        notif.Type = "event";
        return notif;
    }
}

/**
 *
 * @author joe
 */
public class SoldatServer extends SwingWorker<Void, SoldatNotif> implements ServerInstance {
    
    private static final int refreshSize = 1188;
    
    private Socket Sock = null;
    private BufferedReader In = null;
    private DataOutputStream Out = null;
    
    private TabBody Window;
    
    private String Host, Password;
    private Integer Port;
    
    public Boolean Connected = false;
    
    private String ServerVersion = null;

    public final static String gameModeIdToString[] = {
        "Deathmatch",
        "Pointmatch",
        "Team Deathmatch",
        "Capture the Flag",
        "Rambomatch",
        "Infiltration",
        "Hold the Flag"
    };

    public final static String teamIdToString[] = {
        "None",
        "Alpha",
        "Bravo",
        "Charlie",
        "Delta",
        "Spectator"
    };
    
    public final static Color teamIdToColor[] = {
        Color.DARK_GRAY,
        Color.RED,
        Color.BLUE,
        Color.YELLOW,
        Color.GREEN,
        Color.GRAY
    };
    
    public SoldatServer () {
    }

    @Override
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
    
    @Override
    public void sendCommand(String line) {
        if (!Connected) {
            System.out.println("Cannot write if we're not connected");
            return;
        }
        try {
            Out.writeBytes(line+"\n");
            System.out.println("wrote "+line);
        }
        catch (IOException e) {
            System.out.println("Failed writing");
        }
    }
    
    @Override
    public void setDetails(String Host, String Password, Integer Port) {
        this.Host = Host;
        this.Password = Password;
        this.Port = Port;
    }

    @Override
    public void setWindow(TabBody Window) {
        this.Window = Window;
    }

    @Override
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
        publish(SoldatNotif.eventFactory("Disconnected"));
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
        
        Matcher match;
        
        char[] refresh = new char[refreshSize];
        char[] refreshx = new char[1992]; 
        
        int i;
        
        try {
            for (i = 0; ;i++) {
                
                // Only way we can watch for this thread being killed (user clicking disconnect)
                // is if we çheck the result of this method call often
                if (Thread.interrupted()) {
                    System.out.println("got interrupt");
                    throw new InterruptedException();
                }

                // With java there is no way of detecting when a socket closes on the remote end except
                // to see if writing fails.
                if (i % 25 == 0) {
                    System.out.println("Sending REFRESHX to see if writing fails which means we're disconnected");
                    Out.writeBytes("REFRESHX\n");
                    Out.flush();
                }

                // We don't want to block on waiting for input in case the interrupt is triggered
                // and we're waiting for a line from the server before we can notice
                if (!In.ready()) {
                    Thread.sleep(100);
                    continue;
                }
                
                if ((line = In.readLine()) == null)
                    break;

                if (line.equals("REFRESH")) {
                    System.out.println("I am expecting refresh packet...");
                    try {
                       System.out.println("Got bytes for refresh: "+In.read(refresh, 0, refreshSize));
                       parseRefresh(refresh);
                    }
                    catch (IOException e) {
                        System.out.println("failed refresh: "+e);
                    }
                    continue; 
                }
                else if (line.equals("REFRESHX")) {
                    System.out.println("I am expecting refreshx packet...");
                    
                    try {
                        int recv, j = 0;
                        while (j <= 1992) {
                            recv = In.read(refreshx, j, 1992 - j);
                            if (recv < 1)
                                break;
                            j += recv;
                            System.out.println("Got bytes"+recv);
                        }
                        System.out.println("Got bytes for refreshx: "+j);
                        parseRefreshX(refreshx);                        
                    }
                    catch (Exception e) {
                        System.out.println("issue with refreshx:");
                        e.printStackTrace();
                    }

                    continue; 
                }
                else {
                    System.out.println("not expecting refresh");
                }
                
                if (!Connected) {
                    Connected = true;
                    publish(SoldatNotif.eventFactory("Connected"));
                }
                    
                if (ServerVersion == null && (match = SoldatRegexes.lineServerVersion.matcher(line)) != null && match.find()) {
                    ServerVersion = match.group(1);
                }
                
                System.out.println("Received line via while: '"+line+"'");
                line = line.trim();

                publish(SoldatNotif.lineFactory(line));
            }
        }
        catch (IOException e) {
            System.out.println("Io socket exception: "+e);
        }
        catch (InterruptedException e) {
            System.out.println("Disconnecting via interrupt");
        }
        catch (Exception e) {
            System.out.println("caught something else: " + e);
            e.printStackTrace();
        }
        finally  {
            Disconnect();
        }
        return null;
    }
    
    @Override
    protected void process(List<SoldatNotif> messages) {
        
        for (SoldatNotif notif : messages) {
            if (notif.Type.equals("line")) {
                System.out.println("Recieved line via process: "+notif.Line);
                Window.addConsoleLine(notif.Line, "console");
            }
            else if (notif.Type.equals("event") && notif.Event.equals("Disconnected")) {
                Window.onDisconnect();
            }
            else if (notif.Type.equals("event") && notif.Event.equals("Connected")) {
                Window.onConnect();
            }
       }
    }

    private void parseRefresh(char[] refresh) {

        int i, j;
        int pos = 0;
       
        SoldatPlayer[] players = new SoldatPlayer[32];
        
        long [] teamscore = new long[4];
        
        String mapname = "";
        long timelimit = 0;
        long currenttime = 0;
        long killlimit = 0;
        int gametype;
        
        // player names
        for (i = 0; i < 32; i++) {
            players[i] = new SoldatPlayer();
            int length = refresh[pos];
            pos++;
            for (j = 0; j < length; j++) {
                players[i].name += refresh[pos];
                pos++;
            }
            pos += 24 - length;
        }
    
        // player teams
        for (i = 0; i < 32; i++) {
            players[i].team = refresh[pos];
            pos++;
        }
        
        // player kills
        for (i = 0; i < 32; i++) {
            players[i].kills = refresh[pos] + (refresh[pos + 1] * 256);
            pos += 2;
        }
        
        // player deaths
        for (i = 0; i < 32; i++) {
            players[i].deaths = refresh[pos] + (refresh[pos + 1] * 256);
            pos += 2;
        }
        
        // Get player pings
	for (i = 0; i < 32; i++) {
            players[i].ping = refresh[pos];
            pos++;
	}
 
	// Get player IDs
	for (i = 0; i < 32; i++) {
            players[i].id = refresh[pos];
            pos++;
	}
 
	// Get player IPs
	for (i = 0; i < 32; i++) {
            int[] ips = new int[4];
            for (j = 0; j < 4; j++) {
                ips[j] = refresh[pos];
                pos++;
            }
            players[i].ip = ips[0]+"."+ips[1]+"."+ips[2]+"."+ips[3];
	}        
        
        // team scores
        for (i = 0; i < 4; i++) {
            teamscore[i] = refresh[pos] + (refresh[pos + 1] * 256);
            pos += 2;
        }
        
        // map
        int maplength = refresh[pos];
        pos++;
        for (i = 0; i < maplength; i++) {
            mapname += refresh[pos];
            pos++;
        }
        pos += 16 - maplength;
        
        // time limit
        for (i = 0; i < 4; i++) {
            timelimit += refresh[pos] * (256 ^ i);
            pos++;
        }
        
        // current time
        for (i = 0; i < 4; i++) {
            currenttime += refresh[pos] * (256 ^ i);
            pos++;
        }
        
        killlimit = refresh[pos] + refresh[pos + 1];
        pos += 2;
        
        gametype = refresh[pos];
        
        Window.drawSoldatPlayers(players);
        Window.updateSoldatGameInfo(mapname, "", gameModeIdToString[gametype], timelimit - currenttime, ServerVersion);
    }
    
    private void parseRefreshX(char[] refreshx) {
        
        int i, j;
        
        ByteBuffer buff = ByteBuffer.allocate(refreshx.length + 1);
        buff.order(ByteOrder.LITTLE_ENDIAN);
        
        for (i = 0; i < refreshx.length; i++)
            buff.put((byte) refreshx[i]);
        
        SoldatPlayer[] players = new SoldatPlayer[32];
        String map = "", nextMap = "";
        boolean passworded;
        int[] teamscore = new int[4];
        double redflagx, redflagy, blueflagx, blueflagy;
        long currentTime, timeLimit;
        int killLimit, maxPlayers, maxSpectators, gameType;
        int pos = 0;
        
        
        for (i = 0; i < 32; i++) {
            players[i] = new SoldatPlayer();
            int length = refreshx[pos];
            pos++;
            for (j = 0; j < length; j++) {
                players[i].name += refreshx[pos];
                pos++;
            }
            pos += 24 - length;
        }

        for (i = 0; i < 32; i++) {
            for (j = 0; j < 12; j++) {
                players[i].hwid += refreshx[pos];
                pos++;
            }
        }
    
        for (i = 0; i < 32; i++) {
            players[i].team = refreshx[pos];
            pos++;
        }

        for (i = 0; i < 32; i++) {
            players[i].kills = buff.getShort(pos);
            pos += 2;
        }        
    
        for (i = 0; i < 32; i++) {
            players[i].caps = refreshx[pos];
            pos++;
        }
 
        for (i = 0; i < 32; i++) {
            players[i].deaths = buff.getShort(pos);
            pos += 2;
        }      
        
        // ping
        for (i = 0; i < 32; i++) {
            players[i].ping = buff.getInt(pos);
            pos += 4;
        }              
    
        for (i = 0; i < 32; i++) {
            players[i].id = refreshx[pos];
            pos++;
        }

	for (i = 0; i < 32; i++) {
            int[] ips = new int[4];
            for (j = 0; j < 4; j++) {
                ips[j] = refreshx[pos];
                pos++;
            }
            players[i].ip = ips[0]+"."+ips[1]+"."+ips[2]+"."+ips[3];
	}          

        // player x
        for (i = 0; i < 32; i++) {
            players[i].x = buff.getFloat(pos);
            pos += 4;
        }           

        // player y
        for (i = 0; i < 32; i++) {
            players[i].y = buff.getFloat(pos);
            pos += 4;
        }          
        
        // red flag x
        redflagx = buff.getFloat(pos);
        pos += 4;

        // red flag y
        redflagy = buff.getFloat(pos);
        pos += 4;

        // blue flag x
        blueflagx = buff.getFloat(pos);
        pos += 4;

        // blue flag y
        blueflagy = buff.getFloat(pos);
        pos += 4;
        
        // team scores
        for (i = 0; i < 4; i++) {
            teamscore[i] = refreshx[pos] + (refreshx[pos + 1] * 256);
            pos += 2;
        }
        
        // map
        j = refreshx[pos];
        System.out.println("map length: "+j);
        pos++;
        for (i = 0; i < j; i++) {
            map += refreshx[pos];
            pos++;
        }
        pos += 16 - j;        
        
        // time limit
        timeLimit = buff.getInt(pos);
        pos += 4;
        
        // current time
        currentTime = buff.getInt(pos);
        pos += 4;
        
        System.out.println("Time: "+currentTime+"/"+timeLimit);
        
        // kill limit
        killLimit = buff.getShort(pos);
        pos += 2;
        
        gameType = refreshx[pos];
        pos++;
        
        maxPlayers = refreshx[pos];
        pos++;
        
        maxSpectators = refreshx[pos];
        pos++;
        
        passworded = refreshx[pos] == 1;
        pos++;
        
        System.out.println("Gametype: "+gameType);
        System.out.println("max players: "+maxPlayers);
        System.out.println("passworded: "+(passworded ? "Yes" : "no"));

        // next map
        j = refreshx[pos];
        pos++;
        for (i = 0; i < j; i++) {
            nextMap += refreshx[pos];
            pos++;
        }
        
        // debug
        for (SoldatPlayer player : players) {
            if (player.name.equals(""))
                continue;
            System.out.println("Player: "+player.name+" hwid: "+player.hwid+" team: "+player.team+" kills: "+player.kills+" deaths: "+player.deaths+" ping: "+
                    player.ping+" x:"+player.x+" y: "+player.y);
        }
        
        System.out.println("Current map: '"+map+"' next map: '"+nextMap+"'");
        
       
        Window.drawSoldatPlayers(players);
        Window.updateSoldatGameInfo(map, nextMap, gameModeIdToString[gameType], timeLimit - currentTime, ServerVersion);        
    }
}
