package com.keng;

import org.opencv.core.Core;

import java.io.File;

class opencv_version {



  public static void main(String[] args) {
      String opencvLibPath = "D:\\Program\\opencv\\build\\java\\x64\\opencv_java470.dll"; // Change this to the correct path for your system

      // Add the OpenCV library path to the java.library.path system property
      //System.setProperty("java.library.path", opencvLibPath);
      File file = new File(opencvLibPath);
      if(file.isFile()){
          System.load(opencvLibPath);
          // Load the OpenCV native library
          System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
      }


    if ((1==args.length) && (0==args[0].compareTo("--build"))) {

        System.out.println(Core.getBuildInformation());
    } else {
        if ((1 == args.length) && (0 == args[0].compareTo("--help"))) {

            System.out.println("\t--build\n\t\tprint complete build info");
            System.out.println("\t--help\n\t\tprint this help");
        } else {

            System.out.println("Welcome to OpenCV " + Core.VERSION);
        }
    }
  }

}
