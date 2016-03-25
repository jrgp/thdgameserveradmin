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

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joe
 */
public class Conf {
    private static HashMap<String, Color> colors = null;
    private final static Logger LOGGER = LoggerFactory.getLogger(Conf.class);


    public static void Load() {

        colors = new HashMap<>();

        URL colorsPath = ClassLoader.getSystemResource("colors.conf");
        InputStream colorStream = null;
        BufferedReader colorReader = null;
        String line;

        try {
            colorStream = colorsPath.openStream();
            colorReader = new BufferedReader(new InputStreamReader(colorStream));

            while ((line = colorReader.readLine()) != null) {
                String parts[] = line.split(" ");
                if (parts[0].equals(";") || parts.length != 2)
                    continue;
                try {
                  colors.put(parts[0], Color.decode("#"+parts[1]));
                }
                catch (NumberFormatException e) {
                }
            }

            colorStream.close();
            colorReader.close();
        }
        catch (IOException e) {
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
