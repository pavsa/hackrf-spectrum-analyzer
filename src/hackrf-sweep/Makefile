#Settings:
JAVA_EXECUTABLE			= "c:\Program Files\Java\jre\bin\java.exe" #required to generate jna wrapper
IS_RELEASE				= y
IS_64BIT				= n
#temporary build dir
BUILD_PATH 				= obj
#where the resulting release zip will be copied
RELEASE_DIR				= ../../release/

ARCH_DEFINED			= n
ifeq ($(ARCH), x86-64)
ARCH_DEFINED			= y
IS_64BIT				= y
endif
ifeq ($(ARCH), x86)
ARCH_DEFINED			= y
IS_64BIT				= n
endif



#SOURCES					= \
#						lib/hackrf-2017.02.1/host/libhackrf/src/hackrf.c \
#						lib/hackrf-2017.02.1/host/hackrf-tools/src/hackrf_sweep.c

SOURCES					= \
						lib/hackrf/host/libhackrf/src/hackrf.c \
						lib/hackrf/host/hackrf-tools/src/hackrf_sweep.c
	
INCLUDE_PATHS			= -Ilib/hackrf/host/libhackrf/src	 -Ilib/libusb-1.0.20/include/libusb-1.0   
						 
						 
#-Ilib/hackrf-2017.02.1/host/libhackrf/src	
						 

ifeq ($(IS_64BIT), y)
	OBJECT_SUFFIX			= o64
else
	OBJECT_SUFFIX			= o
endif
						 
OBJECTS					= $(addsuffix .$(OBJECT_SUFFIX), $(addprefix $(BUILD_PATH)/, $(SOURCES))) 						

CFLAGS					= -DHACKRF_SWEEP_AS_LIBRARY -c  
LDFLAGS					=  

ifeq ($(IS_RELEASE), y)
	CFLAGS 					+= -fdata-sections -ffunction-sections -O3 #-O2
	LDFLAGS					+= -Wl,--gc-sections #-s 
else
	CFLAGS 					+= -O0 -g #-O2
	LDFLAGS					+= -g 
endif


CC						= gcc
CXX						= g++

ifeq ($(IS_64BIT), y)
	LDPATHS					= -L"lib/fftw-3.3.5-dll64" -L"lib/libusb-1.0.20/MinGW64/dll"
	INCLUDE_PATHS			+= -Ilib/fftw-3.3.5-dll64 
else
	LDPATHS					= -L"lib/fftw-3.3.5-dll32" -L"lib/libusb-1.0.20/MinGW32/dll"
	INCLUDE_PATHS			+= -Ilib/fftw-3.3.5-dll32 
endif

LDLIBS					= -lusb-1.0 -lfftw3f-3  
LDPATHS_STATIC			= -L""

LIB_DIR_32BIT			= win32-x86
LIB_DIR_64BIT			= win32-x86-64
ifeq ($(IS_64BIT), y)
	LIB_DIR					= $(LIB_DIR_64BIT)
else
	LIB_DIR					= $(LIB_DIR_32BIT)
endif

#paths

#path to build dlls
OUTPUT_DIR				= build/lib/$(LIB_DIR)

JNA_LIB					= lib/hackrf-sweep-jna.jar
JNA_SWEEP_HEADER				= $(CURDIR)/lib/hackrf/host/hackrf-tools/src/hackrf_sweep.h


DLL_LIB_PTHREAD			= lib/$(LIB_DIR)/libwinpthread-1.dll
DLL_JNA					= lib/$(LIB_DIR)/jnidispatch.dll
DLL_LIB					= $(OUTPUT_DIR)/hackrf-sweep.dll

ifeq ($(IS_64BIT), y)
	DLL_LIB_FFTW			= lib/fftw-3.3.5-dll64/libfftw3f-3.dll
	DLL_LIB_USB				= lib/libusb-1.0.20/MinGW64/dll/libusb-1.0.dll
else
	DLL_LIB_FFTW			= lib/fftw-3.3.5-dll32/libfftw3f-3.dll
	DLL_LIB_USB				= lib/libusb-1.0.20/MinGW32/dll/libusb-1.0.dll
endif

JNAERATOR				= $(CURDIR)/lib/jnaerator/jnaerator-0.13-20150328.111636-4-shaded.jar
ZIP_FILE				= hackrf_spectrum_analyzer.zip
ZIP_FILE_PATH			= $(OUTPUT_DIR)/../../$(ZIP_FILE)


.NOTPARALLEL: all
.PHONY: all

ifeq ($(ARCH_DEFINED), y)

all: prepare $(DLL_LIB) $(ZIP_FILE_PATH)

else

all:
	echo "no ARCH specified - 'x86' or 'x86-64' required"

endif

.PHONY: dirs
dirs:
	@echo "Creating directories"
	@mkdir -p $(dir $(OBJECTS))
	
$(ZIP_FILE_PATH): $(DLL_LIB)
	mkdir -p $(RELEASE_DIR)
	cd $(OUTPUT_DIR)/../../ && rm -rf $(ZIP_FILE) &&  zip -r $(ZIP_FILE)  *
	yes | cp -rf $(ZIP_FILE_PATH) $(RELEASE_DIR)
	#cd $(CURDIR)
	

.PHONY: prepare
prepare: dirs $(OBJECTS) $(DLL_LIB_FFTW)
	mkdir -p $(OUTPUT_DIR)
	cp -f $(DLL_LIB_FFTW) $(DLL_LIB_PTHREAD) $(DLL_JNA) $(DLL_LIB_USB) $(OUTPUT_DIR)
	cp -f lib/zadig_2.2.exe lib/program.ico lib/program.png $(OUTPUT_DIR)/../

$(DLL_LIB):  $(OBJECTS) $(JNA_LIB)
	echo "building " $(DLL_LIB)
	$(CXX) $(LDFLAGS)  -shared -Wl,--kill-at  -static-libgcc -static-libstdc++    $(LDPATHS)  $(OBJECTS)  -o $(DLL_LIB) $(LDLIBS)
	strip --strip-unneeded $(DLL_LIB)

$(JNA_LIB): $(JNA_SWEEP_HEADER) $(OBJECTS)
	$(JAVA_EXECUTABLE) -Xmx1024M -jar $(JNAERATOR) -limitComments -emptyStructsAsForwardDecls -f  -skipLibraryInstance -runtime JNA -direct  -mode Jar -o lib -jar $(JNA_LIB) -v -library "hackrf-sweep" $(JNA_SWEEP_HEADER)
	$(JAVA_EXECUTABLE) -Xmx1024M -jar $(JNAERATOR) -limitComments -emptyStructsAsForwardDecls -f  -skipLibraryInstance -runtime JNA -direct  -mode Jar -o lib -jar libhackrf-jna.jar -v -library "hackrf-sweep"  $(CURDIR)/lib/hackrf/host/libhackrf/src/hackrf.h
	rm -rf $(CURDIR)/_jnaerator.*


$(BUILD_PATH)/%.c.$(OBJECT_SUFFIX): %.c
	$(CC) $(CFLAGS) $(INCLUDE_PATHS)  $< -o $@
	
#%.o64: %.c
#	$(CC) $(CFLAGS) $(INCLUDE_PATHS)  $< -o $@
	
	
.PHONY: clean
clean:
	rm -f  $(OBJECTS) $(DLL_LIB) #$(JNA_LIB)
	rm -rf $(OUTPUT_DIR) $(BUILD_PATH)
	
