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

package kag;

import java.util.regex.*;

/**
 *
 * @author joe
 */
public class Regexes {
    
    public static Pattern linePlayer;
    public static Pattern linePlayersListStart;
    public static Pattern linePlayerSpeak;
    public static Pattern lineRconConnect;
    public static Pattern lineRconCommand;
    public static Pattern lineConsole;
    
    public static void init() {

        // Used for network/etc parsing 
        linePlayer = Pattern.compile("^\\[[\\d:]+\\]\\s+\\[(.+)\\] \\(id (\\d+)\\) \\(ip ([\\d\\.]+)\\) \\(hwid (\\d+)\\)$");
        //lineFilterPlayer = Pattern.compile("^\\[[\\d:]+\\] (List of Players.+|RCON command from \\S+ /players)$");
        linePlayersListStart = Pattern.compile("^\\[[\\d:]+\\] List of Players \\S+ use RCON to get IP and hwid info$");
        
        // Used to prettyprint lines displayed in console
        linePlayerSpeak = Pattern.compile("^(\\[[\\d:]+\\]) (\\<.+\\>) (.+)");
        lineRconCommand = Pattern.compile("^(\\[[\\d:]+\\]) RCON command from (\\S+): (.*)$");
        lineRconConnect = Pattern.compile("^(\\[[\\d:]+\\]) TCP RCON Connection from (\\S+) is now authenticated$");
        lineConsole = Pattern.compile("^(\\[[\\d:]+\\])(.*)");
    }
}
