# Spectrum Analyzer GUI for hackrf_sweep for Windows

![screenshot](screenshot.gif "screenshot")

### Download:
[Download the latest version](release/hackrf_spectrum_analyzer.zip)

### Features:
- Optimized for only one purpose - to use HackRF as a spectrum analyzer
- All changes in settings restart hackrf_sweep automatically 
- Easy retuning    
- hackrf_sweep integrated as a shared library
- Peak display
- High resolution waterfall plot
- Spur filter - removes spur artifacts from the spectrum 

### Why?
Other software is limited or hard to use
 
### Requirements:
* HackRF One with [Firmware 2017.02.1](https://github.com/mossmann/hackrf/releases/tag/v2017.02.1) or newer (use linux inside virtual machine to [update the firmware](https://github.com/mossmann/hackrf/wiki/Updating-Firmware))
* Libusb driver for HackRF One (see below)
* 64bit v1.8+ Java JRE installed
* Windows 7+ x64

### Installation:
1. Make sure HackRF is using at least the minimum firmware version (see above) 
1. [Download the latest version of Spectrum Analyzer](release/hackrf_spectrum_analyzer.zip) and unzip
1. Install HackRF as a libusb device
  1. [Download Zadig](src/hackrf-sweep/lib/zadig_2.2.exe) and run  
  2. Goto Options and check List All Devices  
  3. Find "HackRF One" and select Driver "WinUSB" and click install
1. Install (if you don't have one installed) [Java JRE for Windows x64](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)     
1. Run "hackrf_sweep_spectrum_analyzer.exe"

### Known issues:
* Spectrum updates stop on parameter change
  * Solution: press reset button on the HackRF (firmware bug)  

### License:
GPL v3 