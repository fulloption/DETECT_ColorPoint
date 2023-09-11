package com.keng;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URL;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static String path = "";
    public static int xPoint = 0;
    public static int yPoint = 0;
    public static int cameraIndex = 1;
    public static CameraList cameraList;
    public static ShowDisplay showDisplay;
    public static void main(String[] args) {
        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        path = (location.getPath().substring(0,location.getPath().lastIndexOf("/")));
        System.load(path+"/opencv_java480.dll");
        System.load(path+"/opencv_videoio_ffmpeg480_64.dll");


        cameraList = new CameraList();
        cameraList.showDisplay();

//        showDisplay = new ShowDisplay();
//        showDisplay.showDisplay();

//        VideoCapture videoCapture = new VideoCapture(0); // Use 0 for the default camera, or change it to the appropriate camera index
//        if (!videoCapture.isOpened()) {
//            System.out.println("Error: Camera not found or cannot be opened.");
//            return;
//        }
//
//        JFrame frame = new JFrame("Camera Feed");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(640, 480);
//
//        JLabel label = new JLabel();
//        frame.add(label);
//        label.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int x = e.getX();
//                int y = e.getY();
//                xPoint = x;
//                yPoint = y;
//                //System.out.println("Mouse clicked at: (" + x + ", " + y + ")");
//            }
//        });
//        frame.setVisible(true);
//
//        while (true) {
//            Mat frameMat = new Mat();
//            if (videoCapture.read(frameMat)) {
//                detectColor(frameMat);
//                BufferedImage bufImage = matToBufferedImage(frameMat);
//                label.setIcon(new ImageIcon(bufImage));
//                frame.pack();
//            } else {
//                break;
//            }
//        }
    }

    private static void detectColor(Mat frame) {
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(frame, hsvImage, Imgproc.COLOR_BGR2HSV);

        Scalar getColor = getPixelColor(frame,xPoint,yPoint);
        double[] hsvColor = hsvImage.get(yPoint,xPoint);
        if(hsvColor != null)
            System.out.println(hsvColor[0]+","+hsvColor[1]+","+hsvColor[2]);//B, G, R,
//        System.out.println(getColor.toString());//B, G, R,
        hsvColor[0] = 107;
        hsvColor[1] = 136;
        hsvColor[2] = 255;
        int radius = 5;
        Scalar red = new Scalar(0, 0, 255); // BGR color (red)
        Imgproc.circle(frame, new org.opencv.core.Point(xPoint, yPoint), radius, red, 1); // -1 means filled circle
        // Define the color range you want to detect (example: green)
        // rgb(37, 150, 190)
//        Scalar lowerBound = new Scalar(50, 50, 0); // Lower bound for green in HSV
//        Scalar upperBound = new Scalar(100, 60, 35); // Upper bound for green in HSV
        // กำหนดช่วงของสีที่ต้องการค้นหา rgba(65,157,136,255)R G B
        // Define the range for green color in HSV
        Scalar lowerColor = new Scalar(hsvColor[0] - 5, hsvColor[1] - 20, hsvColor[2] - 20);
        Scalar upperColor = new Scalar(hsvColor[0] + 5, hsvColor[1] + 20, hsvColor[2] + 20);

        Mat mask = new Mat();
        Core.inRange(hsvImage, lowerColor, upperColor, mask);

        // Apply the mask to the original frame
        frame.setTo(new Scalar(0, 5, 255), mask); // Highlight detected green regions in the frame (green color)
    }
    private static BufferedImage matToBufferedImage(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(bis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bufImage;
    }
    private static Scalar getPixelColor(Mat mat, int x, int y) {
        if (y < 0 || x < 0 || y >= mat.rows() || x >= mat.cols()) {
            System.out.println("Error: Invalid pixel position.");
            return new Scalar(0, 0, 0);
        }

        double[] pixelValue = mat.get(y, x); // Get the pixel value as an array of doubles
        if (pixelValue.length < mat.channels()) {
            System.out.println("Error: Unable to get pixel value.");
            return new Scalar(0, 0, 0);
        }

        // Create a Scalar object from the pixel value (RGB or grayscale)
        if (mat.channels() == 1) {
            return new Scalar(pixelValue[0]);
        } else if (mat.channels() == 3) {
            return new Scalar(pixelValue[2], pixelValue[1], pixelValue[0]);
        } else {
            System.out.println("Error: Unsupported number of channels.");
            return new Scalar(0, 0, 0);
        }
    }
}


