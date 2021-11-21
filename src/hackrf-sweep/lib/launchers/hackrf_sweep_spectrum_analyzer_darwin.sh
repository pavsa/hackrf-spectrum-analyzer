#!/bin/bash
DIRECTORY=`dirname $0`
java -Djna.platform.library.path=lib/darwin -jar $DIRECTORY/lib/hackrf_sweep_spectrum_analyzer.jar
