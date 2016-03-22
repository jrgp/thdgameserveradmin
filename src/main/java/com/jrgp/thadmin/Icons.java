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

/**
 *
 * @author joe
 */
public class Icons {
    private static final String kagIconPath = "kag_icon.png";
    private static final String SoldatIconPath = "soldat_icon.png";
    private static final String botIconPath = "bot_icon.png";
    
    private static ImageIcon kagIcon = null;
    private static ImageIcon soldatIcon = null;
    private static ImageIcon botIcon = null;

    
    public static Icon getIcon(ServerType type) {
        
        Icon icon = null;

        try {
            if (type == ServerType.KAG) {
                if (kagIcon == null)
                    kagIcon = new ImageIcon(ClassLoader.getSystemResource(kagIconPath));
                icon = kagIcon;
            }
            else if (type == ServerType.SOLDAT) {
                if (soldatIcon == null)
                    soldatIcon = new ImageIcon(ClassLoader.getSystemResource(SoldatIconPath));
                icon = soldatIcon;
            }
        } 
        catch (Exception e) {
            return icon;
        }
        
        return icon;
    }
    
    public static Icon getBotIcon() {
        Icon icon = null;

        try {
            if (botIcon == null) 
                 botIcon = new ImageIcon(ClassLoader.getSystemResource(botIconPath));

            icon = botIcon;
        } 
        catch (Exception e) {
            return icon;
        }
        
        return icon;
    }
    
    
    public static Icon ipCountryIcon(String ip) {
        String code = lookup(ip);
        String path = "res/flags/"+code.toLowerCase()+".png";
        Icon icon;
        try {
             icon = new ImageIcon(path);
        }
        catch (Exception e) {
            System.out.println("Failed getting icon: "+e);
            return null;
        }
        
        return icon;
    }
    
    public static Icon soldatGunIcon(String gun) {
        String path = "res/soldatguns/"+gun.replace(" ", "_")+".png";
        Icon icon;
        try {
             icon = new ImageIcon(path);
        }
        catch (Exception e) {
            System.out.println("Failed getting icon: "+e);
            return null;
        }
        
        return icon;
    }
}
