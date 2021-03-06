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

import java.util.regex.Pattern;

/**
 *
 * @author joe
 */
public class SoldatRegexes {
    
    public final static Pattern lineServerVersion = Pattern.compile("^Server Version: ([\\d\\.]+)");
    public final static Pattern linePlayerKill = Pattern.compile("\\(\\d+\\) (.+) killed \\(\\d+\\) (.+) with "+
         "(Ak-74|Barrett M82A1|Bow|Chainsaw|Clusters|Combat Knife|Desert Eagles|FN Minimi|Flamer|Grenade|HK MP5|Hands|LAW|M79|Ruger 77|Spas-12|Steyr AUG|USSOCOM"+
            "|Cluster Grenades|XM214 Minigun|Stationary gun)$");;
    public final static Pattern lineNextMap = Pattern.compile("^Next map: (.+)");

}
