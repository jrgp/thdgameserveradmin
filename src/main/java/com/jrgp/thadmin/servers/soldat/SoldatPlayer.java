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

package com.jrgp.thadmin.servers.soldat;

/**
 *
 * @author joe
 */
public class SoldatPlayer {
    public String name = "";
    public int id = -1;
    public String hwid = "";
    public String ip = "";
    public int team;
    public long  kills;
    public long deaths;
    public int ping;
    public double x;
    public double y;
    public int caps;
    public boolean isBot() {
        return ip.equals("0.0.0.0");
    }
}
