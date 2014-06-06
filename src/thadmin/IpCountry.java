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

import java.io.IOException;
import net.firefang.ip2c.Country;
import net.firefang.ip2c.IP2Country;

/**
 *
 * @author joe
 */
public class IpCountry {
    private static IP2Country ip2c = null;
    
    public static void Load() {
        if (ip2c != null)
            return;
        
        try {
            ip2c = new IP2Country("res/ip.db", IP2Country.MEMORY_MAPPED);
        }
        catch (IOException e) {
            System.out.println("Failed loading ip db: "+e);
        }
    }
    
    public static String lookup(String ip) {
        Country c;
        try {
            c = ip2c.getCountry(ip);
        }
        catch (IOException e) {
            System.out.println("Failed looking up ip: "+e);
            return "A1";
        }
        
        if (c == null) {
            return "A1";
        }
        else {
            return c.get2cStr();
        }
    }

}
