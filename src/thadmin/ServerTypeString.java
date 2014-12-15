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

import static thadmin.ServerType.KAG;
import static thadmin.ServerType.SOLDAT;
import static thadmin.ServerType.STORM;

/**
 *
 * @author joe
 */
public class ServerTypeString {
    public static String TypeToString(ServerType type) {
        switch (type) {
            case SOLDAT:
                return "Soldat";
            case KAG:
                return "KAG";
            case STORM:
                return "Storm";
        }
        return null;
    }
    public static ServerType StringToType(String type) {
        switch (type.toLowerCase()) {
            case "soldat":
                return SOLDAT;
            case "kag":
                return KAG;
            case "storm":
                return STORM;
        }
        return null;
    }
}
