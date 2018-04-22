#Settings:
IS_RELEASE				= y
IS_64BIT				= n
#temporary build dir
BUILD_PATH 				= obj
#where the resulting release zip will be copied
RELEASE_DIR				= ../../release/

SOURCES					= \
						lib/hackrf/host/libhackrf/src/hackrf.c \
						lib/hackrf/host/hackrf-tools/src/hackrf_sweep.c
	
INCLUDE_PATHS			= -Ilib/hackrf/host/libhackrf/src	 -I$(LIBUSB_DIR)/include/libusb-1.0   

JNA_LIB					= lib/hackrf-sweep-jna.jar
JNA_SWEEP_HEADER		= $(CURDIR)/lib/hackrf/host/hackrf-tools/src/hackrf_sweep.h
LIBUSB_DIR				= lib/libusb-1.0.21
MAIN_JAR				= build/lib/hackrf_sweep_spectrum_analyzer.jar


ARCH_DEFINED			= n

IS_WIN = 0
ifeq ($(OS),Windows_NT)
	IS_WIN = 1

ifeq ($(ARCH), x86-64)
	ARCH_DEFINED			= y
	IS_64BIT				= y
endif
ifeq ($(ARCH), x86)
	ARCH_DEFINED			= y
	IS_64BIT				= n
endif

else
ARCH_DEFINED	= y
IS_64BIT		= n
LBITS := $(shell getconf LONG_BIT)
ifeq ($(LBITS),64)
	IS_64BIT				= y
endif
endif


OBJECT_SUFFIX			= o
						 
OBJECTS					= $(addsuffix .$(OBJECT_SUFFIX), $(addprefix $(BUILD_PATH)/, $(SOURCES))) 						

CFLAGS					= -DHACKRF_SWEEP_AS_LIBRARY -c  -fPIC
LDFLAGS					=  -shared 



CC						= gcc
CXX						= g++

ifeq ($(IS_RELEASE), y)
	CFLAGS 					+= -fdata-sections -ffunction-sections -O3 #-O2
	LDFLAGS					+= -Wl,--gc-sections #-s 
else
	CFLAGS 					+= -O0 -g #-O2
	LDFLAGS					+= -g 
endif

LDPATHS_STATIC			= -L""

ifeq ($(IS_WIN),1) 
	#Windows build
	JDK_EXECUTABLE			= "c:\Program Files\Java\jdk\bin\java.exe" #JDK required for ant and to generate jna wrapper
	
	ifeq ($(IS_64BIT), y)
		LDPATHS					= -L"lib/fftw-3.3.5-dll64" -L"$(LIBUSB_DIR)/MinGW64/dll"
		INCLUDE_PATHS			+= -Ilib/fftw-3.3.5-dll64 
	else
		LDPATHS					= -L"lib/fftw-3.3.5-dll32" -L"$(LIBUSB_DIR)/MinGW32/dll"
		INCLUDE_PATHS			+= -Ilib/fftw-3.3.5-dll32 
	endif
	
	LDLIBS						= -lusb-1.0 -lfftw3f-3  
	ifeq ($(IS_64BIT), y)
		LIB_DIR					= win32-x86-64
	else
		LIB_DIR					= win32-x86
	endif	
	
	
	DLL_LIB_PTHREAD			= lib/$(LIB_DIR)/libwinpthread-1.dll
	DLL_LIB					= $(OUTPUT_DLL_DIR)/hackrf-sweep.dll
	
	ifeq ($(IS_64BIT), y)
		DLL_LIB_FFTW			= lib/fftw-3.3.5-dll64/libfftw3f-3.dll
		DLL_LIB_USB				= $(LIBUSB_DIR)/MinGW64/dll/libusb-1.0.dll
	else
		DLL_LIB_FFTW			= lib/fftw-3.3.5-dll32/libfftw3f-3.dll
		DLL_LIB_USB				= $(LIBUSB_DIR)/MinGW32/dll/libusb-1.0.dll
	endif
	LDFLAGS	+= -Wl,--kill-at  -static-libgcc -static-libstdc++
else 
	#linux build
	ANT_PATH	= ""
	#required installed libraries for linux: openjdk-8 or newer, libfftw3, libusb-1.0 
	JDK_EXECUTABLE			= "java" #required to generate jna wrapper
	DLL_LIB					= $(OUTPUT_DLL_DIR)/libhackrf-sweep.so
	LDLIBS					= -lusb-1.0 -lfftw3 -lfftw3f
	CFLAGS					+= -march=native
	ifeq ($(IS_64BIT), y)
		LIB_DIR					= linux-x86-64
	else
		LIB_DIR					= linux-x86
	endif	
	
endif

#paths
#path to build dlls
OUTPUT_DLL_DIR				= build/lib/$(LIB_DIR)



JNAERATOR				= $(CURDIR)/lib/jnaerator/jnaerator-0.13-20150328.111636-4-shaded.jar
ZIP_FILE				= hackrf_spectrum_analyzer.zip
ZIP_FILE_PATH			= $(OUTPUT_DLL_DIR)/../../$(ZIP_FILE)


.NOTPARALLEL: all
.PHONY: all

ifeq ($(ARCH_DEFINED), y)

all: prepare $(MAIN_JAR) $(DLL_LIB) $(ZIP_FILE_PATH)

else

all:
	echo "no ARCH specified - 'x86' or 'x86-64' required"

endif

.PHONY: dirs
dirs:
	@echo "Creating directories"
	@mkdir -p $(dir $(OBJECTS))

$(MAIN_JAR):
ifeq ($(IS_WIN),0) #on windows, build it from eclipse project
	echo "running ant..."
	ant -buildfile lib/hackrf_sweep_spectral_analyzer_buildjar_ant.xml compile
	ant -buildfile lib/hackrf_sweep_spectral_analyzer_buildjar_ant.xml create_run_jar
	echo "running ant... done"
endif


	
$(ZIP_FILE_PATH): $(DLL_LIB)
ifeq ($(IS_WIN),1)
	mkdir -p $(RELEASE_DIR)
	cd $(OUTPUT_DLL_DIR)/../../ && rm -rf $(ZIP_FILE) &&  zip -r $(ZIP_FILE)  *
	yes | cp -rf $(ZIP_FILE_PATH) $(RELEASE_DIR)
endif	

.PHONY: prepare
prepare: dirs $(OBJECTS) $(DLL_LIB_FFTW)
	mkdir -p $(OUTPUT_DLL_DIR)
	cp -f lib/program.ico lib/program.png $(OUTPUT_DLL_DIR)/../
ifeq ($(IS_WIN),1)
	cp -f $(DLL_LIB_FFTW) $(DLL_LIB_PTHREAD) $(DLL_LIB_USB) $(OUTPUT_DLL_DIR)
	cp -f lib/zadig_2.2.exe $(OUTPUT_DLL_DIR)/../
else
	echo '#!/bin/bash\nDIRECTORY=`dirname $$0`\njava -jar $$DIRECTORY/lib/hackrf_sweep_spectrum_analyzer.jar' > build/hackrf_sweep_spectrum_analyzer.sh
	chmod +x build/hackrf_sweep_spectrum_analyzer.sh
endif
	

$(DLL_LIB):  $(OBJECTS) $(JNA_LIB)
	echo "building " $(DLL_LIB)
	$(CXX) $(LDFLAGS)   $(LDPATHS)  $(OBJECTS)  -o $(DLL_LIB) $(LDLIBS)
	strip --strip-unneeded $(DLL_LIB)

$(JNA_LIB): $(JNA_SWEEP_HEADER) $(OBJECTS)
	$(JDK_EXECUTABLE) -Xmx1024M -jar $(JNAERATOR) -limitComments -emptyStructsAsForwardDecls -f  -skipLibraryInstance -runtime JNA -direct  -mode Jar -o lib -jar $(JNA_LIB) -v -library "hackrf-sweep" $(JNA_SWEEP_HEADER)
	$(JDK_EXECUTABLE) -Xmx1024M -jar $(JNAERATOR) -limitComments -emptyStructsAsForwardDecls -f  -skipLibraryInstance -runtime JNA -direct  -mode Jar -o lib -jar libhackrf-jna.jar -v -library "hackrf-sweep"  $(CURDIR)/lib/hackrf/host/libhackrf/src/hackrf.h
	rm -rf $(CURDIR)/_jnaerator.*

$(BUILD_PATH)/%.c.$(OBJECT_SUFFIX): %.c
	$(CC) $(CFLAGS) $(INCLUDE_PATHS)  $< -o $@

	
.PHONY: clean
clean:
	rm -f  $(MAIN_JAR) $(OBJECTS) $(DLL_LIB) #$(JNA_LIB)
	rm -rf $(OUTPUT_DLL_DIR) $(BUILD_PATH)
	
