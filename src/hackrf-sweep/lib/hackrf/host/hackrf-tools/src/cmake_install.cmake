# Install script for directory: /Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/hackrf-tools/src

# Set the install prefix
if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX "/usr/local")
endif()
string(REGEX REPLACE "/$" "" CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}")

# Set the install configuration name.
if(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)
  if(BUILD_TYPE)
    string(REGEX REPLACE "^[^A-Za-z0-9_]+" ""
           CMAKE_INSTALL_CONFIG_NAME "${BUILD_TYPE}")
  else()
    set(CMAKE_INSTALL_CONFIG_NAME "")
  endif()
  message(STATUS "Install configuration: \"${CMAKE_INSTALL_CONFIG_NAME}\"")
endif()

# Set the component getting installed.
if(NOT CMAKE_INSTALL_COMPONENT)
  if(COMPONENT)
    message(STATUS "Install component: \"${COMPONENT}\"")
    set(CMAKE_INSTALL_COMPONENT "${COMPONENT}")
  else()
    set(CMAKE_INSTALL_COMPONENT)
  endif()
endif()

# Is this installation the result of a crosscompile?
if(NOT DEFINED CMAKE_CROSSCOMPILING)
  set(CMAKE_CROSSCOMPILING "FALSE")
endif()

# Set default install directory permissions.
if(NOT DEFINED CMAKE_OBJDUMP)
  set(CMAKE_OBJDUMP "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/objdump")
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/bin" TYPE EXECUTABLE FILES "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/hackrf-tools/src/hackrf_transfer")
  if(EXISTS "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_transfer" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_transfer")
    execute_process(COMMAND "/usr/bin/install_name_tool"
      -change "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/libhackrf/src/libhackrf.0.dylib" "libhackrf.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_transfer")
    execute_process(COMMAND /usr/bin/install_name_tool
      -delete_rpath "/usr/local/lib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_transfer")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/strip" -u -r "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_transfer")
    endif()
  endif()
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/bin" TYPE EXECUTABLE FILES "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/hackrf-tools/src/hackrf_spiflash")
  if(EXISTS "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_spiflash" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_spiflash")
    execute_process(COMMAND "/usr/bin/install_name_tool"
      -change "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/libhackrf/src/libhackrf.0.dylib" "libhackrf.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_spiflash")
    execute_process(COMMAND /usr/bin/install_name_tool
      -delete_rpath "/usr/local/lib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_spiflash")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/strip" -u -r "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_spiflash")
    endif()
  endif()
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/bin" TYPE EXECUTABLE FILES "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/hackrf-tools/src/hackrf_cpldjtag")
  if(EXISTS "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_cpldjtag" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_cpldjtag")
    execute_process(COMMAND "/usr/bin/install_name_tool"
      -change "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/libhackrf/src/libhackrf.0.dylib" "libhackrf.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_cpldjtag")
    execute_process(COMMAND /usr/bin/install_name_tool
      -delete_rpath "/usr/local/lib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_cpldjtag")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/strip" -u -r "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_cpldjtag")
    endif()
  endif()
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/bin" TYPE EXECUTABLE FILES "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/hackrf-tools/src/hackrf_info")
  if(EXISTS "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_info" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_info")
    execute_process(COMMAND "/usr/bin/install_name_tool"
      -change "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/libhackrf/src/libhackrf.0.dylib" "libhackrf.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_info")
    execute_process(COMMAND /usr/bin/install_name_tool
      -delete_rpath "/usr/local/lib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_info")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/strip" -u -r "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_info")
    endif()
  endif()
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/bin" TYPE EXECUTABLE FILES "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/hackrf-tools/src/hackrf_debug")
  if(EXISTS "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_debug" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_debug")
    execute_process(COMMAND "/usr/bin/install_name_tool"
      -change "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/libhackrf/src/libhackrf.0.dylib" "libhackrf.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_debug")
    execute_process(COMMAND /usr/bin/install_name_tool
      -delete_rpath "/usr/local/lib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_debug")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/strip" -u -r "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_debug")
    endif()
  endif()
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/bin" TYPE EXECUTABLE FILES "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/hackrf-tools/src/hackrf_clock")
  if(EXISTS "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_clock" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_clock")
    execute_process(COMMAND "/usr/bin/install_name_tool"
      -change "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/libhackrf/src/libhackrf.0.dylib" "libhackrf.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_clock")
    execute_process(COMMAND /usr/bin/install_name_tool
      -delete_rpath "/usr/local/lib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_clock")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/strip" -u -r "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_clock")
    endif()
  endif()
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/bin" TYPE EXECUTABLE FILES "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/hackrf-tools/src/hackrf_sweep")
  if(EXISTS "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_sweep" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_sweep")
    execute_process(COMMAND "/usr/bin/install_name_tool"
      -change "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/libhackrf/src/libhackrf.0.dylib" "libhackrf.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_sweep")
    execute_process(COMMAND /usr/bin/install_name_tool
      -delete_rpath "/usr/local/lib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_sweep")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/strip" -u -r "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_sweep")
    endif()
  endif()
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/bin" TYPE EXECUTABLE FILES "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/hackrf-tools/src/hackrf_operacake")
  if(EXISTS "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_operacake" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_operacake")
    execute_process(COMMAND "/usr/bin/install_name_tool"
      -change "/Users/josemoreira/Pentesting/hackrf-spectrum-analyzer/src/hackrf-sweep/lib/hackrf/host/libhackrf/src/libhackrf.0.dylib" "libhackrf.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_operacake")
    execute_process(COMMAND /usr/bin/install_name_tool
      -delete_rpath "/usr/local/lib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_operacake")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/strip" -u -r "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/bin/hackrf_operacake")
    endif()
  endif()
endif()

