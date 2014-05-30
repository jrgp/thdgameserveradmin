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

package kag;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.SwingWorker;

/**
 *
 * @author joe
 */
public class SoldatSocket extends SwingWorker<Void, KagNotif> {
    
    private Socket Sock = null;
    private BufferedReader In = null;
    private DataOutputStream Out = null;
    
    private TabBody Window;
    
    private String Host, Password;
    private Integer Port;
    
    public Boolean Connected = false;
    
    public SoldatSocket (TabBody Window) {
        this.Window = Window;
        KagRegexes.init();
    }

    @Override
    protected Void doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
