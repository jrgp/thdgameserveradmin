package kag;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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

class ColorString {
    String string;
    Color color;
}

/**
 *
 * @author joe
 */
public class TabBody extends javax.swing.JPanel {
    private ServerInstance Server = null;
    private boolean Connected = false;
    
    private DefaultTableModel PlayerModel = null;
    
    private ServerType type;
    
    private String hostString = "";
    
    private MainWindow tabController;
    
    /**
     * Creates new form TabBody
     * @param type
     * @param tabController
     */
    public TabBody(ServerType type, MainWindow tabController) {
        this.type = type;
        this.tabController = tabController;
        
        initComponents();
        
        // This gets visible for Soldat. Not for kag as i dont have enough 
        // info to show
        BottomInfoPanel.setVisible(false);
         
        if (type == ServerType.KAG) {
            KagRegexes.init();
        }
        else if (type == ServerType.SOLDAT) {
            SoldatRegexes.init();
        }
        
        Conf.load();
        
       

      //  DefaultCaret caret = (DefaultCaret)ConsoleLog.getCaret();
     //   caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

       
        // Add color capabilities to the cell renderer. Spent a while googling how to set the 
        // foreground color of a cell but nothing came up. Fell back to reading the swing source
        // and finding a suitable hack.
        DefaultTableCellRenderer coloredrenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                            boolean isSelected, boolean hasFocus, 
                                                            int row, int column) {

               Component elem = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

               if (value instanceof ColorString) {
                   ColorString info = (ColorString) value;
                   if (!isSelected)
                    elem.setForeground(info.color);
                   this.setText(info.string);
               }
               
               return elem;
             }
        };        
        
        if (type == ServerType.KAG) {
            PlayerModel = new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Player", "ID", "IP", "HWID"
                }
            ) {
                @Override
                public Class getColumnClass(int columnIndex) {
                    return getValueAt(0, columnIndex).getClass();
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
        }
        else if (type == ServerType.SOLDAT) {
             PlayerModel = new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "ID", "Player", "Team", "Kills", "Deaths", "IP"
                }
            ) {
                @Override
                public Class getColumnClass(int columnIndex) {
                    return getValueAt(0, columnIndex).getClass();
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };   
        }
        
        PlayerTable.setModel(PlayerModel);
        PlayerTable.setBackground(Color.white);
 
        // This must be called after the model set
        if (type == ServerType.SOLDAT)
            PlayerTable.getColumnModel().getColumn(2).setCellRenderer(coloredrenderer);
        
    }

    public ServerType getType() {
        return type;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        HostBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        PasswordBox = new javax.swing.JPasswordField();
        ConnectButton = new javax.swing.JButton();
        CommandBox = new javax.swing.JTextField();
        CommandButton = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        ConsoleLog = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        PlayerTable = new javax.swing.JTable();
        BottomInfoPanel = new javax.swing.JPanel();
        GameTypeLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        MapNameLabel = new javax.swing.JLabel();
        VersionLabel = new javax.swing.JLabel();

        jLabel1.setText("IP:port");

        HostBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HostBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Password:");

        ConnectButton.setText("Connect");
        ConnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectButtonActionPerformed(evt);
            }
        });

        CommandBox.setEditable(false);
        CommandBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CommandBoxActionPerformed(evt);
            }
        });

        CommandButton.setText("Run");
        CommandButton.setEnabled(false);
        CommandButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CommandButtonActionPerformed(evt);
            }
        });

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        ConsoleLog.setEditable(false);
        ConsoleLog.setMinimumSize(new java.awt.Dimension(0, 100));
        jScrollPane2.setViewportView(ConsoleLog);

        jSplitPane1.setBottomComponent(jScrollPane2);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(23, 100));

        PlayerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        PlayerTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(PlayerTable);

        jSplitPane1.setLeftComponent(jScrollPane1);

        GameTypeLabel.setText("GameType");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        MapNameLabel.setText("Map");

        VersionLabel.setText("Version");

        javax.swing.GroupLayout BottomInfoPanelLayout = new javax.swing.GroupLayout(BottomInfoPanel);
        BottomInfoPanel.setLayout(BottomInfoPanelLayout);
        BottomInfoPanelLayout.setHorizontalGroup(
            BottomInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BottomInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GameTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MapNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(VersionLabel)
                .addContainerGap())
        );
        BottomInfoPanelLayout.setVerticalGroup(
            BottomInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BottomInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BottomInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addComponent(GameTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BottomInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(MapNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(VersionLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BottomInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HostBox, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PasswordBox, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ConnectButton)
                        .addGap(0, 252, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CommandBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CommandButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HostBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(PasswordBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConnectButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CommandBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CommandButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BottomInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void HostBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HostBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HostBoxActionPerformed

    private void CommandBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CommandBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CommandBoxActionPerformed

    private void ConnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectButtonActionPerformed
        if (ConnectButton.getText().equals("Disconnect")) {
            HandleDisonnect();
        }
        else if (ConnectButton.getText().equals("Connect")) {
            System.out.println("clicked connect");
            HandleConnect();
        } 
    }//GEN-LAST:event_ConnectButtonActionPerformed
    private void SendCommand() {
        String command = CommandBox.getText().trim();
        CommandBox.setText("");
        
        if (command.length() == 0 || !Connected || Server == null) {
            return;
        }
        
        Server.sendCommand(command);
    }
    
    public boolean isConnected() {
        return Connected;
    }
    
    private void CommandButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CommandButtonActionPerformed
               SendCommand();
    }//GEN-LAST:event_CommandButtonActionPerformed

    private void PasswordBoxKeyTyped(java.awt.event.KeyEvent evt) {                                     
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            HandleConnect();
    }                                    

    private void CommandBoxKeyTyped(java.awt.event.KeyEvent evt) {                                    
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            HandleConnect();
    }   
    
    private void HandleConnect() {
        String IpPort = HostBox.getText().trim();
        String Password = new String(PasswordBox.getPassword());
        
        String Host;
        Integer Port;
        
        String parts[] = IpPort.split(":");
        
        if (parts.length < 2 || IpPort.length() == 0 || Password.length() == 0) {
            JOptionPane.showMessageDialog(this, "You must appropriately fill out the password fields",
                    "Incomplete input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Host = parts[0];
        
        try {
            Port = Integer.parseInt(parts[1]);
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Port does not look like a number",
                    "Incomplete input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (Port < 1 || Port > 65000) {
            JOptionPane.showMessageDialog(this, "Port is not within a realistic range",
                    "Incomplete input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        System.out.println("passed checks");
        
        ConnectButton.setEnabled(false);
        
        hostString = IpPort;
        
        if (this.type == ServerType.KAG)
            Server = new KagServer();
        else if (this.type == ServerType.SOLDAT)
            Server = new SoldatServer();
        else 
            return;
        
        Server.setWindow(this);
        
        Server.setDetails(Host, Password, Port);
        Server.execute();
        
        tabController.fixTabs();
    }        
    
    public String getHostString() {
        return hostString;
    }
    
    public void HandleDisonnect() {
        
        if (Server == null)
            return;
        
        boolean success = Server.cancel(true);
        
        if (success)
            System.out.println("Cancelled worker");
        else
            System.out.println("Failed to cancel worker");
    }
    
    public void addConsoleLine(String line, String type) {
        
        tabController.fixTabs();
        
        class StyledText {
            SimpleAttributeSet style;
            String text;
            public  StyledText(SimpleAttributeSet style, String text) {
                this.text = text;
                this.style = style;
            }
        }
        
        StyledDocument doc = ConsoleLog.getStyledDocument();
        
        List<StyledText> words = new ArrayList<>();
        
        Matcher match;
        
        SimpleAttributeSet style;
        
        if (type.equals("connect")) {
            style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, Conf.getColor("consolelog.connected")); 
            StyleConstants.setBold(style, true);
            words.add(new StyledText(style, line));
        }
        else if (type.equals("disconnect")) {
            style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, Conf.getColor("consolelog.disconnected")); 
            StyleConstants.setBold(style, true);
            words.add(new StyledText(style, line));
        }
        else if (type.equals("console") && this.type == ServerType.KAG) {
            
            if ((match = KagRegexes.lineRconConnect.matcher(line)) != null && match.find()) {
                style = new SimpleAttributeSet();
                StyleConstants.setBold(style, true);
                StyleConstants.setForeground(style, Conf.getColor("consolelog.timestamp")); 
                words.add(new StyledText(style, match.group(1)));
                
                words.add(new StyledText(null, " TCP RCON Connection from "));
                
                style = new SimpleAttributeSet();
                StyleConstants.setForeground(style, Conf.getColor("kag.rconauthip")); 
                StyleConstants.setBold(style, true);
                words.add(new StyledText(style, match.group(2)));
                
                words.add(new StyledText(null, " is now authenticated"));
            }
            else if ((match = KagRegexes.linePlayerSpeak.matcher(line)) != null && match.find()) {
                style = new SimpleAttributeSet();
                StyleConstants.setBold(style, true);
                StyleConstants.setForeground(style, Conf.getColor("consolelog.timestamp")); 
                words.add(new StyledText(style, match.group(1)));
                
                words.add(new StyledText(null, " "));
                
                style = new SimpleAttributeSet();
                StyleConstants.setForeground(style, Conf.getColor("kag.playerspeaknick")); 
                StyleConstants.setBold(style, true);
                words.add(new StyledText(style, match.group(2)));
                
                words.add(new StyledText(null, " "));
                
                style = new SimpleAttributeSet();
                StyleConstants.setForeground(style, Conf.getColor("kag.playerspeakmessage")); 
                words.add(new StyledText(style, match.group(3)));                
            }
            else if ((match = KagRegexes.lineRconCommand.matcher(line)) != null && match.find()) {
                
                // I do not care when people run /players
                if (match.group(3).equals("/players")) {
                    return;
                }
                
                style = new SimpleAttributeSet();
                StyleConstants.setBold(style, true);
                StyleConstants.setForeground(style, Conf.getColor("consolelog.timestamp")); 
                words.add(new StyledText(style, match.group(1)));
                
                words.add(new StyledText(null, " RCON command from "));
                
                style = new SimpleAttributeSet();
                StyleConstants.setForeground(style, Conf.getColor("kag.rconcmdsrc")); 
                StyleConstants.setBold(style, true);
                words.add(new StyledText(style, match.group(2)));
                
                words.add(new StyledText(null, ": "));
                
                style = new SimpleAttributeSet();
                StyleConstants.setForeground(style, Conf.getColor("kag.rconcmd")); 
                StyleConstants.setBold(style, true);
                words.add(new StyledText(style, match.group(3)));                
            }
            else if ((match = KagRegexes.lineConsole.matcher(line)) != null && match.find()) {
                
                // don't care about empty shiz or other stuff we know happens
                if (match.group(2).trim().length() == 0 || match.group(2).equals(" /players")) {
                    return;
                }
                
                style = new SimpleAttributeSet();
                StyleConstants.setBold(style, true);
                StyleConstants.setForeground(style, Conf.getColor("consolelog.timestamp")); 
                words.add(new StyledText(style, match.group(1)));
                
                words.add(new StyledText(null, " "));
                
                style = new SimpleAttributeSet();
                StyleConstants.setForeground(style, Conf.getColor("consolelog.default")); 
                words.add(new StyledText(style, match.group(2)));
            }
        }      
        // something our regexen didn't pickup... gray that fucker out
        else {
          style = new SimpleAttributeSet();
          StyleConstants.setForeground(style, Conf.getColor("consolelog.default"));
          words.add(new StyledText(style, line));
        }

        words.add(new StyledText(null, "\n"));
        
        try {
            for (StyledText text : words) {
           //     if (text.style == null) {
        //            text.style = new SimpleAttributeSet();
        //            StyleConstants.setForeground(text.style, Conf.getColor("consolelog.default"));
         //       }
                doc.insertString(doc.getLength(), text.text, text.style);
            }
        }
        catch (BadLocationException e) {
            
        }
    }
    
    public void onConnect() {
        ConnectButton.setText("Disconnect");
        HostBox.setEditable(false);
        PasswordBox.setEditable(false);
        CommandBox.setEditable(true);
        CommandButton.setEnabled(true);
        Connected = true;
        ConnectButton.setEnabled(true);
        addConsoleLine("Connected", "connect");
        tabController.fixTabs();
        if (type == ServerType.SOLDAT)
            BottomInfoPanel.setVisible(true);
    }

    public void onDisconnect() {
        ConnectButton.setText("Connect");
        HostBox.setEditable(true);
        PasswordBox.setEditable(true);
        CommandBox.setEditable(false);
        CommandBox.setText("");
        CommandButton.setEnabled(false);
        Connected = false;
        ConnectButton.setEnabled(true);
        addConsoleLine("Disconnected..", "disconnect");
        PlayerModel.setRowCount(0);
        tabController.fixTabs();
        BottomInfoPanel.setVisible(false);
    }
    
    public void drawKagPlayers(List<KagPlayer> players) {
        PlayerModel.setRowCount(0);
        for (KagPlayer player : players) {
            PlayerModel.addRow(new Object[]{
                player.name,
                player.id,
                player.ip,
                player.hwid
            });
        }
    }
 
    public void drawSoldatPlayers(SoldatPlayer[] players) {
        PlayerModel.setRowCount(0);
        for (SoldatPlayer player : players) {
            if (player.name.length() == 0)
                continue;
           
            ColorString team = new ColorString();
            team.color = SoldatServer.teamIdToColor[player.team];
            team.string = SoldatServer.teamIdToString[player.team];
            
            PlayerModel.addRow(new Object[] {
                player.id,
                player.name,
                team, 
                player.kills,
                player.deaths,
                player.ip.equals("0.0.0.0") ? Icons.getBotIcon() : player.ip
            });
        }
    } 
    
    public void updateSoldatGameInfo(String map, String gametype, long timeleft, String version) {
        GameTypeLabel.setText(gametype);
        MapNameLabel.setText(map);
        VersionLabel.setText(version);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BottomInfoPanel;
    private javax.swing.JTextField CommandBox;
    private javax.swing.JButton CommandButton;
    private javax.swing.JButton ConnectButton;
    private javax.swing.JTextPane ConsoleLog;
    private javax.swing.JLabel GameTypeLabel;
    private javax.swing.JTextField HostBox;
    private javax.swing.JLabel MapNameLabel;
    private javax.swing.JPasswordField PasswordBox;
    private javax.swing.JTable PlayerTable;
    private javax.swing.JLabel VersionLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
