/*
 * Copyright (C) 2014 Joe Gillotti
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

package com.jrgp.thadmin;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import javax.swing.SwingWorker;
import static com.jrgp.thadmin.ServerType.KAG;
import org.slf4j.LoggerFactory;


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
public class KagServer extends SwingWorker<Void, KagNotif> implements ServerInstance {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(KagServer.class);

    private Socket Sock = null;
    private BufferedReader In = null;
    private DataOutputStream Out = null;

    private TabBody Window;

    private String Host, Password;
    private Integer Port;

    public Boolean Connected = false;

    public KagServer () {
    }


    @Override
    public void setWindow(TabBody Window) {
        this.Window = Window;
    };

    @Override
    public void setDetails(String Host, String Password, Integer Port) {
        this.Host = Host;
        this.Password = Password;
        this.Port = Port;
    }

    @Override
    public void Connect () {

        LOGGER.info("Connecting to {}:{} with {}", Host, Port, Password);

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
    public void Disconnect() {
        LOGGER.info("Disconnecting");
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

    @Override
    public void sendCommand(String line) {
        if (!Connected) {
            LOGGER.debug("Cannot write to socket if we're not connected");
            return;
        }
        try {
            Out.writeBytes(line+"\n");
        }
        catch (IOException e) {
            LOGGER.error("Failed writing to socket", e);
        }
    }

    @Override
    public void kickPlayer(int id) {
        if (!Connected)
            return;
        LOGGER.info("Will kick player ID ", id);
        try {
            Out.writeBytes("/kickid "+id+"\n");
        } catch (IOException e) {
            LOGGER.error("Failed writing to socket to kick player", e);
        }
    }

    @Override
    public void banPlayer(int id) {
        if (!Connected)
            return;
        LOGGER.info("Will ban player ID ", id);
        try {
            Out.writeBytes("/banid "+id+"\n");
        } catch (IOException e) {
            LOGGER.error("Failed writing to socket to ban player", e);
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        if (Connected) {
            LOGGER.debug("Not starting background thread if we're already connected");
            return null;
        }

        Connect();

        String line;

        // [06:16:53]        [jrgp] (id 140) (ip 67.188.114.9) (hwid 1169726824)

        Matcher playermatcher;
        List<KagPlayer> foundPlayers = new ArrayList<>();

        int i;
        boolean findingPlayers = false;

        try {
            for (i = 0; ;i++) {

                // Only way we can watch for this thread being killed (user clicking disconnect)
                // is if we çheck the result of this method call often
                if (Thread.interrupted()) {
                    LOGGER.debug("Thread got interrupt; disconnecting.");
                    throw new InterruptedException();
                }

                // With java there is no way of detecting when a socket closes on the remote end except
                // to see if writing fails. Fortunately kag doesn't care if we send it shit. We can conveiently
                // use this to check for players...
                if (i % 100 == 0) {
                    LOGGER.debug("Sending keepalive to see if writing fails which means we're disconnected");
                    Out.writeBytes("/players\n");
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

                if (!Connected) {
                    Connected = true;
                    publish(KagNotif.eventFactory("Connected"));
                }

                line = line.trim();
                LOGGER.info("Received '{}' in while", line);

                if ((playermatcher = KagRegexes.linePlayer.matcher(line)) != null
                        && playermatcher.find()) {

                    int playerID = 0;

                    try {
                        playerID = Integer.parseInt(playermatcher.group(2));
                    }
                    catch (NumberFormatException e) {
                    }

                    foundPlayers.add(new KagPlayer(playermatcher.group(1),
                        playerID, playermatcher.group(3), playermatcher.group(4)));

                    continue;
                }
                else if (KagRegexes.linePlayersListStart.matcher(line).matches()) {
                    findingPlayers = true;
                    continue;
                }
                else {
                    if (foundPlayers.size() > 0) {
                        Window.drawKagPlayers(foundPlayers);
                        foundPlayers.clear();
                        findingPlayers = false;
                    }

                    // Empty list? no players brah
                    else if (findingPlayers) {
                        findingPlayers = false;
                        Window.drawKagPlayers(foundPlayers);
                        foundPlayers.clear();
                    }
                }

                publish(KagNotif.lineFactory(line));
            }
        }
        catch (IOException e) {
            LOGGER.error("Failed socket work", e);
        }
        catch (InterruptedException e) {
            LOGGER.info("Disconnecting via interrupt");
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

    @Override
    public FavoriteServer getFavServer() {
        FavoriteServer fav = new FavoriteServer();
        fav.Ip = Host;
        fav.Port = Port;
        fav.Password = Password;
        fav.Type = KAG;
        return fav;
    }
}
