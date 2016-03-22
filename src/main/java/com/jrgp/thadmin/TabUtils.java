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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author joe
 */
public class TabUtils {

    public static Component getTabLabel(ServerType type, String title, final MainWindow tabs, final TabBody tab) {
        JLabel label = new JLabel(title);
        label.setHorizontalTextPosition(JLabel.TRAILING);
        label.setIcon(Icons.getIcon(type));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        JButton closeButton = new JButton("X");


        closeButton.setMargin(new Insets(0, 0, 0, 0));

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
