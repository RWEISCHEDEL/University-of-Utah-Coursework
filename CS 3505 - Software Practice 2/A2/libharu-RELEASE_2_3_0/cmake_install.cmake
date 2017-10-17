# Install script for directory: /home/weisched/cs3505/libharu-RELEASE_2_3_0

# Set the install prefix
IF(NOT DEFINED CMAKE_INSTALL_PREFIX)
  SET(CMAKE_INSTALL_PREFIX "/usr/local")
ENDIF(NOT DEFINED CMAKE_INSTALL_PREFIX)
STRING(REGEX REPLACE "/$" "" CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}")

# Set the install configuration name.
IF(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)
  IF(BUILD_TYPE)
    STRING(REGEX REPLACE "^[^A-Za-z0-9_]+" ""
           CMAKE_INSTALL_CONFIG_NAME "${BUILD_TYPE}")
  ELSE(BUILD_TYPE)
    SET(CMAKE_INSTALL_CONFIG_NAME "")
  ENDIF(BUILD_TYPE)
  MESSAGE(STATUS "Install configuration: \"${CMAKE_INSTALL_CONFIG_NAME}\"")
ENDIF(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)

# Set the component getting installed.
IF(NOT CMAKE_INSTALL_COMPONENT)
  IF(COMPONENT)
    MESSAGE(STATUS "Install component: \"${COMPONENT}\"")
    SET(CMAKE_INSTALL_COMPONENT "${COMPONENT}")
  ELSE(COMPONENT)
    SET(CMAKE_INSTALL_COMPONENT)
  ENDIF(COMPONENT)
ENDIF(NOT CMAKE_INSTALL_COMPONENT)

# Install shared libraries without execute permission?
IF(NOT DEFINED CMAKE_INSTALL_SO_NO_EXE)
  SET(CMAKE_INSTALL_SO_NO_EXE "0")
ENDIF(NOT DEFINED CMAKE_INSTALL_SO_NO_EXE)

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include" TYPE FILE FILES
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_types.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_consts.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_version.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_annotation.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_catalog.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_conf.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_destination.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_doc.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_encoder.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_encrypt.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_encryptdict.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_error.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_ext_gstate.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_font.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_fontdef.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_gstate.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_image.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_info.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_list.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_mmgr.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_objects.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_outline.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_pages.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_page_label.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_streams.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_u3d.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_utils.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_pdfa.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_3dmeasure.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_exdata.h"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/include/hpdf_config.h"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/." TYPE FILE FILES
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/README"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/CHANGES"
    "/home/weisched/cs3505/libharu-RELEASE_2_3_0/INSTALL"
    )
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified")

IF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified")
  FILE(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/." TYPE DIRECTORY FILES "/home/weisched/cs3505/libharu-RELEASE_2_3_0/if")
ENDIF(NOT CMAKE_INSTALL_COMPONENT OR "${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified")

IF(NOT CMAKE_INSTALL_LOCAL_ONLY)
  # Include the install script for each subdirectory.
  INCLUDE("/home/weisched/cs3505/libharu-RELEASE_2_3_0/src/cmake_install.cmake")
  INCLUDE("/home/weisched/cs3505/libharu-RELEASE_2_3_0/demo/cmake_install.cmake")

ENDIF(NOT CMAKE_INSTALL_LOCAL_ONLY)

IF(CMAKE_INSTALL_COMPONENT)
  SET(CMAKE_INSTALL_MANIFEST "install_manifest_${CMAKE_INSTALL_COMPONENT}.txt")
ELSE(CMAKE_INSTALL_COMPONENT)
  SET(CMAKE_INSTALL_MANIFEST "install_manifest.txt")
ENDIF(CMAKE_INSTALL_COMPONENT)

FILE(WRITE "/home/weisched/cs3505/libharu-RELEASE_2_3_0/${CMAKE_INSTALL_MANIFEST}" "")
FOREACH(file ${CMAKE_INSTALL_MANIFEST_FILES})
  FILE(APPEND "/home/weisched/cs3505/libharu-RELEASE_2_3_0/${CMAKE_INSTALL_MANIFEST}" "${file}\n")
ENDFOREACH(file)
