# This will build windows and linux versions of required native libraries 
# The following utilities have to be installed (tested on ubuntu 18.04):
# build-essential ant git libusb-1.0 libfftw3-dev libfftw3-bin mingw-w64 openjdk-8 libfftw3-bin libusb-1.0  

#Settings:
IS_RELEASE					= y

# object files path
BUILD_PATH 					= obj
# build output 
OUTPUT_PATH					= build
#where the resulting release zip will be copied
RELEASE_DIR					= ../../release/

SOURCES						= \
								lib/hackrf/host/libhackrf/src/hackrf.c \
								lib/hackrf/host/hackrf-tools/src/hackrf_sweep.c
	
INCLUDE_PATHS				= -Ilib/hackrf/host/libhackrf/src	 -I$(LIBUSB_DIR)/include/libusb-1.0   
JNA_LIB						= lib/hackrf-sweep-jna.jar
LIBUSB_DIR					= lib/libusb-1.0.21
JNA_SWEEP_HEADER			= $(CURDIR)/lib/hackrf/host/hackrf-tools/src/hackrf_sweep.h
MAIN_JAR					= $(OUTPUT_PATH)/lib/hackrf_sweep_spectrum_analyzer.jar

OBJECT_SUFFIX				= o
OBJECT_SUFFIX_WIN			= ow
				 
OBJECTS						= $(addsuffix .$(OBJECT_SUFFIX), $(addprefix $(BUILD_PATH)/, $(SOURCES))) 						
OBJECTS_WIN					= $(addsuffix .$(OBJECT_SUFFIX_WIN), $(addprefix $(BUILD_PATH)/, $(SOURCES))) 						

CFLAGS						= -DHACKRF_SWEEP_AS_LIBRARY -c -fPIC
LDFLAGS						=  -shared 
CFLAGS_WIN					= $(CFLAGS)

# Compiler setup for linux
CC							= gcc
CXX							= g++
# ...and for windows
CC_WIN						= x86_64-w64-mingw32-gcc 
CXX_WIN						= x86_64-w64-mingw32-g++

ifeq ($(IS_RELEASE), y)
	CFLAGS 					+= -fdata-sections -ffunction-sections -O2
	LDFLAGS					+= -Wl,--gc-sections #-s 
else
	CFLAGS 					+= -O0 -g #-O2
	LDFLAGS					+= -g 
endif

LDPATHS_STATIC				= -L""

#paths
#path to build dlls
OUTPUT_DLL_DIR	    		= $(OUTPUT_PATH)/lib/linux-x86-64
OUTPUT_DLL_DIR_WIN	     	= $(OUTPUT_PATH)/lib/win32-x86-64

# Windows build setup
LDPATHS_WIN					= -L"lib/fftw-3.3.5-dll64" -L"$(LIBUSB_DIR)/MinGW64/dll"
INCLUDE_PATHS_WIN			= $(INCLUDE_PATHS) -Ilib/fftw-3.3.5-dll64 
LDLIBS_WIN					= -lusb-1.0 -lfftw3f-3 -lpthread 
DLL_LIB_PTHREAD				= lib/win32-x86-64/libwinpthread-1.dll
DLL_LIB_WIN					= $(OUTPUT_DLL_DIR_WIN)/hackrf-sweep.dll
DLL_LIB_FFTW				= lib/fftw-3.3.5-dll64/libfftw3f-3.dll
DLL_LIB_USB					= $(LIBUSB_DIR)/MinGW64/dll/libusb-1.0.dll
LDFLAGS_WIN					+= -Wl,--kill-at  -static-libgcc -static-libstdc++ -shared

# Linux build setup
ANT_PATH					= ""
#required installed libraries for linux: openjdk-8 or newer, libfftw3, libusb-1.0 
JDK_EXECUTABLE				= java #required to generate jna wrapper
DLL_LIB						= $(OUTPUT_DLL_DIR)/libhackrf-sweep.so
LDLIBS						= -lusb-1.0 -lfftw3 -lfftw3f
CFLAGS						+= -march=x86-64
LIB_DIR						= linux-x86-64

JNAERATOR					= $(CURDIR)/lib/jnaerator/jnaerator-0.13-20150328.111636-4-shaded.jar
ZIP_FILE					= hackrf_spectrum_analyzer.zip
ZIP_FILE_PATH				= $(OUTPUT_DLL_DIR)/../../$(ZIP_FILE)


.NOTPARALLEL: all
.PHONY: all

all: prepare $(MAIN_JAR) $(DLL_LIB) $(ZIP_FILE_PATH)

.PHONY: dirs
dirs:
	@echo "Creating directories"
	@mkdir -p $(dir $(OBJECTS))

$(MAIN_JAR):
	echo "running ant..."
	ant -buildfile lib/hackrf_sweep_spectral_analyzer_buildjar_ant.xml compile
	ant -buildfile lib/hackrf_sweep_spectral_analyzer_buildjar_ant.xml create_run_jar
	echo "running ant... done"
	
$(ZIP_FILE_PATH): $(DLL_LIB) $(DLL_LIB_WIN)
	mkdir -p $(RELEASE_DIR)
	cd $(OUTPUT_DLL_DIR)/../../ && rm -rf $(ZIP_FILE) &&  zip -r $(ZIP_FILE)  *
	yes | cp -rf $(ZIP_FILE_PATH) $(RELEASE_DIR)

.PHONY: prepare
prepare: dirs $(OBJECTS) $(OBJECTS_WIN) $(DLL_LIB_FFTW)
	mkdir -p $(OUTPUT_DLL_DIR) $(OUTPUT_DLL_DIR_WIN)	
	cp -f lib/program.ico lib/program.png $(OUTPUT_DLL_DIR)/../
	cp lib/launchers/hackrf_sweep_spectrum_analyzer_linux.sh $(OUTPUT_PATH)
	cp lib/launchers/hackrf_sweep_spectrum_analyzer_windows.cmd $(OUTPUT_PATH)
	chmod +x $(OUTPUT_PATH)/hackrf_sweep_spectrum_analyzer_linux.sh #linux launcher
	cp -f $(DLL_LIB_FFTW) $(DLL_LIB_PTHREAD) $(DLL_LIB_USB) $(OUTPUT_DLL_DIR_WIN)
	cp -f lib/zadig-2.5.exe $(OUTPUT_DLL_DIR_WIN)/../

# if native header in hackrf lib changes, rebuild with "make jnabridge" - but openjdk-8 is required - jnaerator does not work with jdk9+
.PHONY: jnabridge
jnabridge: $(JNA_SWEEP_HEADER)
	$(JDK_EXECUTABLE) -Xmx1024M -jar $(JNAERATOR) -limitComments -emptyStructsAsForwardDecls -f  -skipLibraryInstance -runtime JNA -direct  -mode Jar -o lib -jar $(JNA_LIB) -v -library "hackrf-sweep" $(JNA_SWEEP_HEADER)
	unzip -p lib/hackrf-sweep-jna.jar hackrfsweep/HackrfSweepLibrary.java  > src-java/hackrfsweep/HackrfSweepLibrary.java
	rm lib/hackrf-sweep-jna.jar
	#$(JDK_EXECUTABLE) -Xmx1024M -jar $(JNAERATOR) -limitComments -emptyStructsAsForwardDecls -f  -skipLibraryInstance -runtime JNA -direct  -mode Jar -o lib -jar libhackrf-jna.jar -v -library "hackrf-sweep"  $(CURDIR)/lib/hackrf/host/libhackrf/src/hackrf.h
	rm -rf $(CURDIR)/_jnaerator.*

$(DLL_LIB_WIN): $(OBJECTS_WIN) 
	echo "---- Building windows version [start] ----"	
	$(CXX_WIN) $(LDFLAGS_WIN)   $(LDPATHS_WIN)  $(OBJECTS_WIN)  -o $(DLL_LIB_WIN) $(LDLIBS_WIN)
	x86_64-w64-mingw32-strip --strip-unneeded $(DLL_LIB_WIN)
	echo "---- Building windows version [done ] ----"	
	
$(DLL_LIB):  $(OBJECTS)
	echo "---- Building linux version [start] ----"	
	$(CXX) $(LDFLAGS) $(LDPATHS) $(OBJECTS) -o $(DLL_LIB) $(LDLIBS)
	strip --strip-unneeded $(DLL_LIB)
	echo "---- Building linux version [done ] ----"

$(BUILD_PATH)/%.c.$(OBJECT_SUFFIX): %.c
	$(CC) $(CFLAGS) $(INCLUDE_PATHS)  $< -o $@

$(BUILD_PATH)/%.c.$(OBJECT_SUFFIX_WIN): %.c
	$(CC_WIN) $(CFLAGS_WIN) $(INCLUDE_PATHS_WIN)  $< -o $@
	
.PHONY: clean
clean:
	rm -f  $(MAIN_JAR) $(OBJECTS) $(OBJECTS_WIN) $(DLL_LIB) $(DLL_LIB_WIN) # $(JNA_LIB)
	rm -rf $(OUTPUT_DLL_DIR) $(BUILD_PATH) $(OUTPUT_PATH)/*	
