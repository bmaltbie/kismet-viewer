# kismet-viewer
Java / JavaFX app that takes in Kismet output files and displays them in a GUI for easy handling - [independent project]

TO COMPILE: javac *.java

TO RUN: java KismetViewer

Kismet is a network detector, packet sniffer, and intrusion detection system for 802.11 wireless LANs. Kismet will work with any wireless card which supports raw monitoring mode, and can sniff 802.11a, 802.11b, 802.11g, and 802.11n traffic. The program runs under Linux, FreeBSD, NetBSD, OpenBSD, and Mac OS X. Kismet differs from other wireless network detectors in working passively. Namely, without sending any loggable packets, it is able to detect the presence of both wireless access points and wireless clients, and to associate them with each other. It is also the most widely used and up to date open source wireless monitoring tool.
This app was designed to take XML output from Kismet (scans of all nearby networks and information regarding them) and parse it into a GUI so that the user could much more intuitively view and sort them. 



## Home
![image1](https://github.com/bmaltbie/kismet-viewer/blob/master/images/home.png)

The home screen shows important information about each of the networks parsed and gives the user the option to search for specific strings for a selected category or to select a specific network and find further details about them. It also gives users the option to delete the network from the table so they can view only networks they're interested in.

## Statistics
![image2](https://github.com/bmaltbie/kismet-viewer/blob/master/images/statistics.png)

The statistics screen, a pop up opened by clicking the 'Statistics' button on the home page, opens an interactive pie chart that showcases the different encryption types from the parsed in networks.

## Details
![image3](https://github.com/bmaltbie/kismet-viewer/blob/master/images/details.png)

The details screen shows complex details about a selected network and is a pop up opened by clicking on the 'Details' button on the home page when a network is selected in the table. Further information about the network can be found by clicking the various interactive features on the pop up.

## Clients
![image4](https://github.com/bmaltbie/kismet-viewer/blob/master/images/clients.png)

The clients screen shows further details about the selected network, namely what clients the network was observed to have. Arrow keys provide easy ability to maneuver between clients to see more information about them.


