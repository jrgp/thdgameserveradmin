/*
 * Copyright (C) 2014, 2016 joe
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

import com.maxmind.geoip.LookupService;
import java.io.IOException;
import java.net.URL;


/**
 *
 * @author joe
 */
public class IpCountry {

    private static LookupService Lookup = null;

    public static void Load() {

        URL datfile = ClassLoader.getSystemResource("GeoIP.dat");

        if (datfile == null) {
            System.out.println("ip2counry datfile missing. lookups will all fail.");
            return;
        }

        try {
            Lookup = new LookupService(datfile.getPath(), LookupService.GEOIP_MEMORY_CACHE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String lookup(String ip) {
        if (Lookup == null) {
            return null;
        }
        
        String country = Lookup.getCountry(ip).getCode();
        
        if (country.equals("--")) {
            return null;
        }

        return country;
    }
}
