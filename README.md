# Elite Dangerous BGS adapter for binjr
[![Build Status](https://dev.azure.com/binjr/binjr/_apis/build/status/fthevenet.binjr-adapter-elitebgs?branchName=master&jobName=Build)](https://dev.azure.com/binjr/binjr/_build/latest?definitionId=5&branchName=master) [![Github Release](https://img.shields.io/github/release/fthevenet/binjr-adapter-elitebgs.svg?label=Github%20Release)](https://github.com/fthevenet/binjr-adapter-elitebgs/releases/latest)  

This is a plugin for [binjr](https://binjr.eu), which allows it to access and visualize some of the data produced by the
 Background Simulation (BGS) of the video game [Elite Dangerous](https://elitedangerous.com) by [Frontier Development](https://frontier.co.uk).

[binjr](https://binjr.eu) is a time series data browser, and together with this plugin you can visualize and navigate 
through Minor Factions' influence over star systems.

In order to achieve this, it connects to and uses the [Elite BGS](https://elitebgs.app/about) website as a backend for
the historical data uploaded by the community through the [Elite Dangerous Data Network](https://github.com/EDSM-NET/EDDN/wiki).

### Getting started

 * First you need to download and install the binjr client application for your platform from https://binjr.eu
 * Download the the latest version of the plugin, available as a .jar file from the [release page](https://github.com/fthevenet/binjr-adapter-elitebgs/releases/latest)
 * Copy the file binjr-adapter-elitebgs-x.x.jar into the `plugins` folder of your binjr installation.
 * Start binjr, and select "Elite BGS" to add a new data source.


### License

The Elite BGS adapter for binjr is copyright 2019 Frederic Thevenet  
Licensed under the terms of the [Apache License version 2.0](https://apache.org/licenses/LICENSE-2.0).

This is an open source, community driven project and is not affiliated with or endorsed by [Frontier Development](https://frontier.co.uk).
 