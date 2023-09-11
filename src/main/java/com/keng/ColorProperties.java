package com.keng;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ColorProperties {
    String R_Setup1 = "0";
    String G_Setup1 = "0";
    String B_Setup1 = "0";

    String R_Setup2 = "0";
    String G_Setup2 = "0";
    String B_Setup2 = "0";

    public void readColor(){
        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        String path = (location.getPath().substring(0,location.getPath().lastIndexOf("/")));
        String filePath = path+"/config.properties";

        // Create a Properties object to store the properties
        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            // Load the properties from the file
            properties.load(fileInputStream);

            // Retrieve and print specific properties
            String R_Setup1 = properties.getProperty("R_Setup1");
            String G_Setup1 = properties.getProperty("G_Setup1");
            String B_Setup1 = properties.getProperty("B_Setup1");
            String R_Setup2 = properties.getProperty("R_Setup2");
            String G_Setup2 = properties.getProperty("G_Setup2");
            String B_Setup2 = properties.getProperty("B_Setup2");
            String R_HSV_Setup1 = properties.getProperty("R_HSV_Setup1");
            String G_HSV_Setup1 = properties.getProperty("G_HSV_Setup1");
            String B_HSV_Setup1 = properties.getProperty("B_HSV_Setup1");
            String R_HSV_Setup2 = properties.getProperty("R_HSV_Setup2");
            String G_HSV_Setup2 = properties.getProperty("G_HSV_Setup2");
            String B_HSV_Setup2 = properties.getProperty("B_HSV_Setup2");
            Main.R_Setup1 = R_Setup1;
            Main.G_Setup1 = G_Setup1;
            Main.B_Setup1 = B_Setup1;
            Main.R_Setup2 = R_Setup2;
            Main.G_Setup2 = G_Setup2;
            Main.B_Setup2 = B_Setup2;
            Main.R_HSV_Setup1 = R_HSV_Setup1;
            Main.G_HSV_Setup1 = G_HSV_Setup1;
            Main.B_HSV_Setup1 = B_HSV_Setup1;
            Main.R_HSV_Setup2 = R_HSV_Setup2;
            Main.G_HSV_Setup2 = G_HSV_Setup2;
            Main.B_HSV_Setup2 = B_HSV_Setup2;
//            System.out.println("Database URL: " + databaseUrl);
//            System.out.println("Username: " + username);
//            System.out.println("Password: " + password);
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    public void setColorMaster1(String R_Setup1,String G_Setup1,String B_Setup1
                                ,String R_HSV_Setup1,String G_HSV_Setup1,String B_HSV_Setup1
                                ) {

        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        String path = (location.getPath().substring(0,location.getPath().lastIndexOf("/")));
        Properties properties = new Properties();
        // Set your configuration properties
        properties.setProperty("R_Setup1", R_Setup1);
        properties.setProperty("G_Setup1", G_Setup1);
        properties.setProperty("B_Setup1", B_Setup1);
        properties.setProperty("R_Setup2", Main.R_Setup2);
        properties.setProperty("G_Setup2", Main.G_Setup2);
        properties.setProperty("B_Setup2", Main.B_Setup2);

        properties.setProperty("R_HSV_Setup1", R_HSV_Setup1);
        properties.setProperty("G_HSV_Setup1", G_HSV_Setup1);
        properties.setProperty("B_HSV_Setup1", B_HSV_Setup1);
        properties.setProperty("R_HSV_Setup2", Main.R_HSV_Setup2);
        properties.setProperty("G_HSV_Setup2", Main.G_HSV_Setup2);
        properties.setProperty("B_HSV_Setup2", Main.B_HSV_Setup2);
        // Specify the file path for your config.properties file
        String filePath = path+"/config.properties";

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            // Store the properties to the file
            properties.store(fileOutputStream, "Updated Configuration Properties");
            System.out.println("Configuration properties have been written to " + filePath);
            fileOutputStream.close();
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    public void setColorMaster2(String R_Setup2,String G_Setup2,String B_Setup2
                                ,String R_HSV_Setup2,String G_HSV_Setup2,String B_HSV_Setup2
                                ) {

        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        String path = (location.getPath().substring(0,location.getPath().lastIndexOf("/")));
        Properties properties = new Properties();
        // Set your configuration properties
        properties.setProperty("R_Setup1", Main.R_Setup1);
        properties.setProperty("G_Setup1", Main.G_Setup1);
        properties.setProperty("B_Setup1", Main.B_Setup1);
        properties.setProperty("R_Setup2", R_Setup2);
        properties.setProperty("G_Setup2", G_Setup2);
        properties.setProperty("B_Setup2", B_Setup2);


        properties.setProperty("R_HSV_Setup1", Main.R_HSV_Setup1);
        properties.setProperty("G_HSV_Setup1", Main.G_HSV_Setup1);
        properties.setProperty("B_HSV_Setup1", Main.B_HSV_Setup1);
        properties.setProperty("R_HSV_Setup2", R_HSV_Setup2);
        properties.setProperty("G_HSV_Setup2", G_HSV_Setup2);
        properties.setProperty("B_HSV_Setup2", B_HSV_Setup2);

        // Specify the file path for your config.properties file
        String filePath = path+"/config.properties";

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            // Store the properties to the file
            properties.store(fileOutputStream, "Updated Configuration Properties");
            System.out.println("Configuration properties have been written to " + filePath);
            fileOutputStream.close();
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
