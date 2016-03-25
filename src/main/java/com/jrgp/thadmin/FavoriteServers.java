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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;


/**
 *
 * @author joe
 */
public class FavoriteServers {

    private static List<FavoriteServer> Servers = new ArrayList<>();
    private final static String FavoritesConfPath = System.getProperty("user.home") + "/thdadmin_favorites.json";

    public static void Load() {

        Servers.clear();

        try {
            BufferedReader confReader = new BufferedReader(new FileReader(FavoritesConfPath)); //new InputStreamReader(confStream));
            JSONObject contents = (JSONObject) new JSONTokener(confReader).nextValue();
            JSONArray servers = contents.getJSONArray("servers");

            for (int i = 0; i < servers.length(); i++) {
                JSONObject json_server = servers.getJSONObject(i);
                FavoriteServer Server = new FavoriteServer();
                Server.Ip = json_server.getString("ip");
                Server.Port = json_server.getInt("port");
                Server.Password = json_server.getString("password");
                Server.Type = ServerTypeString.StringToType(json_server.getString("type"));
                Servers.add(Server);
            }
        }
        catch (IOException e) {
            System.out.println("Failed reading servers: "+e);
        }
    }

    public static void Save() {

        JSONArray serversArray = new JSONArray();

        for (FavoriteServer Server : Servers) {
             JSONObject serverObject = new JSONObject();
             serverObject.put("type", ServerTypeString.TypeToString(Server.Type));
             serverObject.put("ip", Server.Ip);
             serverObject.put("port", Server.Port);
             serverObject.put("password", Server.Password);
             serversArray.put(serverObject);
         }

        try (BufferedWriter out = new BufferedWriter(new FileWriter(FavoritesConfPath))) {
            JSONObject wrapper = new JSONObject();
            wrapper.put("servers", serversArray);
            out.write(wrapper.toString(4));
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
