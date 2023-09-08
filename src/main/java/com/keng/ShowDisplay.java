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
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;



public class ShowDisplay extends JFrame {
    private JPanel panel1;
    private JButton button_setColor;
    private JLabel jLabal_MousePoint;
    private JLabel jLabal_ColorSelect;
    private JLabel jLabelDisplay;

    private JButton jButtonReset;
    private JTextArea jTextArea_stauts;
    private String path = "";

    public int xPoint = 0;
    public int yPoint = 0;
    private int ChkXpoint = 0;
    private int ChkYpoint = 0;
    private double hsvColorMouse[] ;
    private double hsvColorDetect[] = new double[3];
    private double[] colorMouse ;
    private VideoCapture videoCapture;
    private ImageIcon icon;
    public void showDisplay(){
        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        path = (location.getPath().substring(0,location.getPath().lastIndexOf("/")));
        setTitle("Color detect");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        File file  = new File(path+ "/ImgNotFound.jpg") ;
        icon = new ImageIcon(file.getPath());
        setLocationRelativeTo(null);

        setContentPane(panel1);
        jLabelDisplay.setIcon(resizeIcon(icon,800,600));
        setSize(1000, 800);

        jLabelDisplay.setOpaque(true);
        jLabal_MousePoint.setOpaque(true);
        jLabal_ColorSelect.setOpaque(true);
        jLabelDisplay.setText("");
        jLabal_MousePoint.setText("");
        jLabal_ColorSelect.setText("");
        jLabal_MousePoint.setBackground(Color.black);
        jLabal_ColorSelect.setBackground(Color.black);

        addListerner();
        Main.showDisplay.setVisible(true);
        toCameraDisplay();
        //
    }
    private void toCameraDisplay(){
        hsvColorDetect[0]=255;
        hsvColorDetect[1]=255;
        hsvColorDetect[2]=255;
        videoCapture = new VideoCapture(Main.cameraIndex);
        //videoCapture.release();
        if (!videoCapture.isOpened()) {
            System.out.println("Error: Camera not found or cannot be opened.");
            jLabelDisplay.setIcon(icon);
            return;
        }else{
            Mat frameMat = new Mat();
            while (true) {
                if (videoCapture.read(frameMat)) {
                    detectColor(frameMat);
                    BufferedImage bufImage = matToBufferedImage(frameMat);
                    jLabelDisplay.setIcon(new ImageIcon(bufImage));
                } else {
                    break;
                }
            }
        }


    }
    private void addListerner(){
        jLabelDisplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                xPoint = x;
                yPoint = y;
//                System.out.println("Mouse clicked at: (" + x + ", " + y + ")");
            }
        });
        // Add an ActionListener to the JComboBox


        //-----------
        button_setColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button_setColor_click(e);
            }
        });
        //-----------
        jButtonReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jButtonReset_setColor_click(e);
            }
        });
        Main.showDisplay.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                videoCapture.release();
            }
        });
    }
    private void jButtonReset_setColor_click(MouseEvent e){
        jLabal_ColorSelect.setBackground(Color.white);
//        Color colorHsv = new Color((int) hsvColorMouse[2], (int) hsvColorMouse[1], (int) hsvColorMouse[0]);// R G B
        hsvColorDetect[0] = 255;
        hsvColorDetect[1] = 255;
        hsvColorDetect[2] = 255;
    }
    private void button_setColor_click(MouseEvent e){
        Color color = new Color((int) colorMouse[2], (int) colorMouse[1], (int) colorMouse[0]);// R G B
        jLabal_ColorSelect.setBackground(color);
//        Color colorHsv = new Color((int) hsvColorMouse[2], (int) hsvColorMouse[1], (int) hsvColorMouse[0]);// R G B
        hsvColorDetect[0] = hsvColorMouse[0];
        hsvColorDetect[1] = hsvColorMouse[1];
        hsvColorDetect[2] = hsvColorMouse[2];
    }


    private Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    private void detectColor(Mat frame) {
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(frame, hsvImage, Imgproc.COLOR_BGR2HSV);
        if(ChkXpoint != xPoint
                && ChkYpoint != yPoint){
            ChkXpoint = xPoint;
            ChkYpoint = yPoint;
            Scalar getColor = getPixelColor(frame, xPoint, yPoint);
            colorMouse = frame.get(yPoint, xPoint);
            hsvColorMouse = hsvImage.get(yPoint, xPoint);
            if (hsvColorMouse != null) {
                //System.out.println(hsvColor[0] + "," + hsvColor[1] + "," + hsvColor[2]);//B, G, R,
                Color color = new Color((int) colorMouse[2], (int) colorMouse[1], (int) colorMouse[0]);// R G B
                jLabal_MousePoint.setBackground(color);
            }
        }
//        System.out.println(getColor.toString());//B, G, R,
//        double[] hsvColor= hsvImage.get(yPoint, xPoint);
//        hsvColor[0] = 107;
//        hsvColor[1] = 136;
//        hsvColor[2] = 255;
        int radius = 3;
        Scalar red = new Scalar(0, 0, 255); // BGR color (red)
        Imgproc.circle(frame, new org.opencv.core.Point(ChkXpoint, ChkYpoint), radius, red, 1); // -1 means filled circle
        // Define the color range you want to detect (example: green)
        // rgb(37, 150, 190)
//        Scalar lowerBound = new Scalar(50, 50, 0); // Lower bound for green in HSV
//        Scalar upperBound = new Scalar(100, 60, 35); // Upper bound for green in HSV
        // กำหนดช่วงของสีที่ต้องการค้นหา rgba(65,157,136,255)R G B
        // Define the range for green color in HSV
        Scalar lowerColor = new Scalar(hsvColorDetect[0] - 5, hsvColorDetect[1] - 20, hsvColorDetect[2] - 20);
        Scalar upperColor = new Scalar(hsvColorDetect[0] + 5, hsvColorDetect[1] + 20, hsvColorDetect[2] + 20);
//        System.out.println(hsvColorDetect[0]+"-"+hsvColorDetect[1]+"-"+hsvColorDetect[2]);
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
