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

import java.util.Objects;

/**
 *
 * @author joe
 */

public class FavoriteServer {
    public String Ip = null;
    public int Port = 0;
    public String Password = null;
    public ServerType Type = null;

    @Override
    public String toString() {
        return Ip+":"+Port+"/"+Password;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof FavoriteServer))
            return false;

        FavoriteServer server = (FavoriteServer) other;

        return server.Ip.equals(this.Ip)
                && server.Port == this.Port
                && server.Password.equals(this.Password)
                && server.Type == this.Type;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.Ip);
        hash = 29 * hash + this.Port;
        hash = 29 * hash + Objects.hashCode(this.Password);
        hash = 29 * hash + Objects.hashCode(this.Type);
        return hash;
    }
}
