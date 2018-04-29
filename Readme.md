# Spectrum Analyzer GUI for hackrf_sweep for Windows/Linux

![screenshot](screenshot.gif "screenshot")

### Download:
Windows: [Download the latest version](https://github.com/pavsa/hackrf-spectrum-analyzer/releases/download/1.4/hackrf_spectrum_analyzer.zip)  
Linux: read Installation section below

### Features:
- Optimized for only one purpose - to use HackRF as a spectrum analyzer
- All changes in settings restart hackrf_sweep automatically 
- Easy retuning    
- Peak / Persistent display
- Frequency allocation bands for EU / USA(partial)
- High resolution waterfall plot
- Spur filter - removes spur artifacts from the spectrum 
- hackrf_sweep integrated as a shared library

### Why?
Other software is limited or hard to use
 
### Requirements:
* HackRF One with [Firmware 2017.02.1](https://github.com/mossmann/hackrf/releases/tag/v2017.02.1) or newer (use linux inside virtual machine to [update the firmware](https://github.com/mossmann/hackrf/wiki/Updating-Firmware))  

### Installation:
Make sure HackRF is using at least the minimum firmware version (see above) 

Windows:
1. Windows 7+ x64 required 
1. Install Java JRE 64bit v1.8+
1. [Download the latest version of Spectrum Analyzer](release/hackrf_spectrum_analyzer.zip) and unzip
1. Install HackRF as a libusb device
  1. [Download Zadig](src/hackrf-sweep/lib/zadig_2.2.exe) and run  
  2. Goto Options and check List All Devices  
  3. Find "HackRF One" and select Driver "WinUSB" and click install
1. Install (if you don't have one installed) [Java JRE for Windows x64](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)     
1. Run "hackrf_sweep_spectrum_analyzer.exe"

Linux (beta - only ubuntu tested):
1. Needs to be compiled first, so you'll need to install these packages:  
`sudo apt install build-essential ant git libusb-1.0 libfftw3 libfftw3-dev openjdk-8-jdk`
1. `git clone --depth=1 --recurse-submodules https://github.com/pavsa/hackrf-spectrum-analyzer.git`
1. `cd hackrf-spectrum-analyzer/src/hackrf-sweep/`
1. `make` 
1. `build/hackrf_sweep_spectrum_analyzer.sh`

### Known issues:
* Spectrum updates stop on parameter change
  * Solution: press reset button on the HackRF (firmware bug)  

### License:
GPL v3 