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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 *
 * @author joe
 */
public class TabUtils {
    private static final String kagIconPath = "/Users/joe/NetBeansProjects/kagadmin/pics/kag_icon.png";
    private static final String SoldatIconPath = "/Users/joe/NetBeansProjects/kagadmin/pics/soldat_icon.png";

    private static ImageIcon kagIcon = null;
    private static ImageIcon soldatIcon = null;
    
    public static Icon getIcon(ServerType type) {
        
        Icon icon = null;
        
        if (type == type.KAG) {
            if (kagIcon == null)
                kagIcon = new ImageIcon(kagIconPath);
            icon = kagIcon;
        }
        else if (type == type.SOLDAT) {
            if (soldatIcon == null)
                soldatIcon = new ImageIcon(SoldatIconPath);
            icon = soldatIcon;
        }
        
        return icon;
    }
    
    public static Component getTabLabel(ServerType type, String title, ServerTabs tabs, TabBody tab) {
        JLabel label = new JLabel(title);
        label.setHorizontalTextPosition(JLabel.TRAILING);
        label.setIcon(getIcon(type));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        
        //JButton closeButton = new JButton(MetalIconFactory.getInternalFrameCloseIcon(16));
        JButton closeButton = new JButton("X");

        
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        //closeButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        closeButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        closeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tabs.killTab(tab);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

	});
        
        JPanel tabComponent = new JPanel(new BorderLayout());
        tabComponent.setOpaque(false);
        tabComponent.add(label, BorderLayout.WEST);
        tabComponent.add(closeButton, BorderLayout.EAST);

        
        return (Component) tabComponent;
    }
}
