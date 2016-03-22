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

package com.jrgp.thadmin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joe
 */
public class FavoriteServers {

    private static List<FavoriteServer> Servers = new ArrayList<>();
    private final static URL serversConfPath = ClassLoader.getSystemResource("favorites.conf");

    public static void Load() {

        if (serversConfPath == null) {
            System.out.println("Failed getting path to favorites.conf..");
            return;
        }

        Servers.clear();

        InputStream confStream;
        BufferedReader confReader;
        String line;

        FavoriteServer Server = new FavoriteServer();
        String[] parts;

        try {
            confStream = serversConfPath.openStream();
            confReader = new BufferedReader(new FileReader("res/favorites.conf")); //new InputStreamReader(confStream));
            while ((line = confReader.readLine()) != null) {

                line = line.trim();

                if (line.startsWith(";")) {
                    continue;
                }

                parts = line.split(":", 2);

                switch (parts[0]) {
                    case "ip":
                        Server.Ip = parts[1];
                    break;
                    case "port":
                        Server.Port = Integer.parseInt(parts[1]);
                    break;
                    case "password":
                        Server.Password = parts[1];
                    break;
                    case "type":
                        Server.Type = ServerTypeString.StringToType(parts[1]);
                    break;
                }

                if (Server.Ip != null && Server.Port > 0 && Server.Password != null && Server.Type != null) {
                    Servers.add(Server);
                    System.out.println("Found server: "+Server);
                    Server = new FavoriteServer();
                }
            }
        }
        catch (IOException e) {
            System.out.println("Failed reading servers: "+e);
        }
    }

    public static void Save() {

        if (serversConfPath == null)
            return;

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("res/favorites.conf"));

            for (FavoriteServer Server : Servers) {
                out.write("type:"+ServerTypeString.TypeToString(Server.Type)+"\n");
                out.write("ip:"+Server.Ip+"\n");
                out.write("port:"+Server.Port+"\n");
                out.write("password:"+Server.Password+"\n");
                out.write("\n");
            }

            out.close();
        }
        catch (IOException ex) {
            Logger.getLogger(FavoriteServers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<FavoriteServer> getServers() {
        return Servers;
    }

    public static void AddToFavorites(FavoriteServer Server) {
        if (CheckInFavorites(Server))
            return;
        Servers.add(Server);
    }

    public static void DelFromFavorites(FavoriteServer Server) {
        if (!CheckInFavorites(Server))
            return;
        Servers.remove(Server);
    }

    public static boolean CheckInFavorites(FavoriteServer Server) {
        return Servers.contains(Server);
    }

    public static void setServers(List<FavoriteServer> _Servers) {
        Servers = _Servers;
    }

    public static void connectServers(MainWindow window) {
        for (FavoriteServer Server : Servers) {
            System.out.println("Auto connecting to: "+Server);
            TabBody tab = new TabBody(Server.Type, window);
            window.addTab(tab, "Server");
            tab.SetServerAndJoin(Server.Ip, Server.Port, Server.Password);
        }
    }
}
