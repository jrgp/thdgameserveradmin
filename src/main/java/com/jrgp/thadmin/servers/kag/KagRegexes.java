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

package com.jrgp.thadmin.servers.kag;

import java.util.regex.*;

/**
 *
 * @author joe
 */
public class KagRegexes {
    
    // Used for network/etc parsing 
    public final static Pattern linePlayer = Pattern.compile("^\\[[\\d:]+\\]\\s+\\[(.+)\\] \\(id (\\d+)\\) \\(ip ([\\d\\.]+)\\) \\(hwid (\\d+)\\)$");;
    public final static Pattern linePlayersListStart = Pattern.compile("^\\[[\\d:]+\\] List of Players \\S+ use RCON to get IP and hwid info$");;
    
    // Used to prettyprint lines displayed in console
    public final static Pattern linePlayerSpeak = Pattern.compile("^(\\[[\\d:]+\\]) (\\<.+\\>) (.+)");;
    public final static Pattern lineRconConnect = Pattern.compile("^(\\[[\\d:]+\\]) TCP RCON Connection from (\\S+) is now authenticated$");;
    public final static Pattern lineRconCommand = Pattern.compile("^(\\[[\\d:]+\\]) RCON command from (\\S+): (.*)$");;
    public final static Pattern lineConsole = Pattern.compile("^(\\[[\\d:]+\\])(.*)");
    
}
