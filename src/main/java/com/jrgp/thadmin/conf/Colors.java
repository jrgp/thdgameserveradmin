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

package com.jrgp.thadmin.conf;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joe
 */
public class Colors {
    private static HashMap<String, Color> colors = null;
    private final static Logger LOGGER = LoggerFactory.getLogger(Colors.class);


    public static void Load() {

        colors = new HashMap<>();

        URL colorsPath = ClassLoader.getSystemResource("colors.ini");

        try {
            Ini ini = new Ini(new FileReader(colorsPath.getPath()));
            Ini.Section section = ini.get("colors");
            for (String key: section.keySet()) {
                colors.put(key, Color.decode("#" + section.get(key)));
            }
        }
        catch (IOException e) {
            LOGGER.error("Failed parsing colors.conf", e);
        }
    }

    public static Color getColor(String key) {
        Color color = colors.get(key);
        if (color == null) {
            LOGGER.warn("Missing color key {}; defaulting to gray.", key);
            return Color.gray;
        }
        else {
            return color;
        }
    }
}
