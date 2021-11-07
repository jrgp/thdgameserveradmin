# THD Game Server Admin - Java/Swing admin client for THD Games

This is a kinda simple swing app which connects to the socket admin game server protocol for the following games made by TransHuman Design (www.thd.vg):
- Soldat (http://www.soldat.pl)
- King Athur's Gold (http://www.kag2d.com)

![Screenshot on Windows](http://jrgp.us/screenshots/kagadmin12.png)

Screenshots:
- http://jrgp.us/screenshots/kagadmin11.png
- http://jrgp.us/screenshots/kagadmin12.png
- http://jrgp.us/screenshots/kagadmin13.png

# Compile:

    ./update_ipdb.sh
    mvn clean package
    chmod +x target/thdadmin-pkg/bin/thdadmin.sh

# Run:

    target/thdadmin-pkg/bin/thdadmin.sh

or

    target/thdadmin-pkg/bin/thdadmin.bat


# Features:

- Multiple servers for either game in multiple tabs
- List of players currently in server, as well as ip2country picture and HW ID where applicable
- Console log with color codes as well as inline pictures
- Tested on Windows, Mac, and Linux

# Notes:

- It's developed in Netbeans as it allegedly has better support for designing Swing forums
- Licensed under GPL

# Contact:

- Contact me via github or at joe@u13.net via email (I promise I'll reply)
- PR's welcome
