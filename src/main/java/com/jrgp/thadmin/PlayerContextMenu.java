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

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author joe
 */
public class PlayerContextMenu extends JPopupMenu {
    
    private final JMenuItem kick, ban;
    private final String playerName;
    private final int playerId;
    private ServerInstance Server = null;
    
    public PlayerContextMenu(ServerInstance Server, String playerName, int PlayerId) {
        this.Server = Server;
        this.playerName = playerName;
        this.playerId = PlayerId;
        
        kick = new JMenuItem("Kick "+playerName);
        ban = new JMenuItem("Ban "+playerName);
        
        
        kick.addActionListener(new java.awt.event.ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   performAction("kick");
               }
        });
        
        ban.addActionListener(new java.awt.event.ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   performAction("ban");
               }
        });
        
        add(kick);
        add(ban);
    }
    
    private void performAction(String type) {
        switch (type) {
            case "kick":
                Server.kickPlayer(playerId);
                break;
            case "ban":
                Server.banPlayer(playerId);
                break;
        }
    }
}
