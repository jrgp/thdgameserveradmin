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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import static com.jrgp.thadmin.IpCountry.lookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joe
 */
public class Icons {

    private final static Logger LOGGER = LoggerFactory.getLogger(Icons.class);

    public static Icon getIcon(ServerType type) {
        
        if (type == ServerType.KAG) {
            return new ImageIcon(ClassLoader.getSystemResource("kag_icon.png"));
        }
        else if (type == ServerType.SOLDAT) {
            return new ImageIcon(ClassLoader.getSystemResource("soldat_icon.png"));
        }
        
        LOGGER.warn("Unkown game type: {}", type);

        return null;
    }
    
    public static Icon getBotIcon() {
        return new ImageIcon(ClassLoader.getSystemResource("bot_icon.png"));
    }
    
    public static Icon ipCountryIcon(String ip) {
        String code = lookup(ip);
        if (code == null) {
            LOGGER.warn("Not getting country icon for IP {}", ip);
            return null;
        }
        return new ImageIcon(ClassLoader.getSystemResource("flags/"+code.toLowerCase()+".png"));
    }
    
    public static Icon soldatGunIcon(String gun) {
        return new ImageIcon(ClassLoader.getSystemResource("soldatguns/"+gun.replace(" ", "_")+".png"));
    }
}