package com.keng;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.utils.Converters;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ShowDisplay extends JFrame {
    private JPanel panel1;
    private JButton button_setColor;
    private JLabel jLabal_MousePoint;
    private JLabel jLabal_ColorShow1;
    private JLabel jLabelDisplay;

    private JButton jButtonReset;
    private JLabel JlabelLogo2;
    private JLabel jLabelLogo1;
    private JLabel jLabelSetup;
    private JLabel jLabelResult;
    private JButton jButtonSetColor1;
    private JButton jButtonSetColor2;
    private JLabel jLabelSetup1;
    private JLabel jLabelSetup2;
    private JLabel jLabal_ColorShow2;
    private JLabel jLabelStatus;
    //private JLabel jLabelShow01;
    private JLabel a;
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
    private ImageIcon iconImageNotFound;
    public void showDisplay(){
        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        path = (location.getPath().substring(0,location.getPath().lastIndexOf("/")));
        setTitle("Color detect");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        File file  = new File(path+ "/ImgNotFound.jpg") ;
        iconImageNotFound = new ImageIcon(file.getPath());
        Image icon = Toolkit.getDefaultToolkit().getImage(path+ "/yokohama.jpg");
        Main.showDisplay.setIconImage(icon);

        setSize(1100, 800);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        initSwing();
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
            jLabelDisplay.setIcon(iconImageNotFound);
            videoCapture.release();
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
        videoCapture.release();

    }
    private void initSwing(){
        File file  = new File(path+ "/eac.jpg") ;
        ImageIcon eac = new ImageIcon(file.getPath());
        JlabelLogo2.setIcon(resizeIcon(eac,200,100));
        file  = new File(path+ "/yokohama.jpg") ;
        ImageIcon yoko = new ImageIcon(file.getPath());

        //jLabelShow01.setText("");
//        jLabelSetup.setPreferredSize(new Dimension(jLabelResult.getPreferredSize().width, 50)); // Set the height to 50 pixels
//        jLabelResult.setPreferredSize(new Dimension(jLabelResult.getPreferredSize().width, 50)); // Set the height to 50 pixels
        jLabelLogo1.setText("");
        JlabelLogo2.setText("");
        jLabelLogo1.setIcon(resizeIcon(yoko,200,100));
        jLabelDisplay.setIcon(resizeIcon(iconImageNotFound,800,600));
        jLabelDisplay.setOpaque(true);
        jLabal_MousePoint.setOpaque(true);
        jLabal_ColorShow1.setOpaque(true);
        jLabal_ColorShow2.setOpaque(true);
        jLabelSetup1.setOpaque(true);
        jLabelSetup2.setOpaque(true);
        jLabelStatus.setOpaque(true);
        jLabelSetup1.setText("");
        jLabelSetup2.setText("");
        Color color = new Color(Integer.parseInt( Main.R_Setup1)
                , Integer.parseInt( Main.G_Setup1)
                , Integer.parseInt( Main.B_Setup1));// R G B
        jLabelSetup1.setBackground(color);
        color = new Color(Integer.parseInt( Main.R_Setup2)
                , Integer.parseInt( Main.G_Setup2)
                , Integer.parseInt( Main.B_Setup2));// R G B
        jLabelSetup2.setBackground(color);
        jLabelDisplay.setText("");
        jLabal_MousePoint.setText("Click Color");
        jLabal_ColorShow1.setText("");
        jLabal_ColorShow2.setText("");
        jLabal_MousePoint.setBackground(new Color(255, 255, 255));
//        jLabal_ColorShow1.setBackground(Color.black);
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
        jButtonSetColor1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color color = new Color((int) colorMouse[2], (int) colorMouse[1], (int) colorMouse[0]);// R G B
                jLabelSetup1.setBackground(color);
                ColorProperties colorProperties = new ColorProperties();
                String B = (String) String.valueOf(colorMouse[0]).split("\\.")[0];
                String G = (String) String.valueOf(colorMouse[1]).split("\\.")[0];
                String R = (String) String.valueOf(colorMouse[2]).split("\\.")[0];
                String B_HSV = (String) String.valueOf(hsvColorMouse[0]).split("\\.")[0];
                String G_HSV = (String) String.valueOf(hsvColorMouse[1]).split("\\.")[0];
                String R_HSV = (String) String.valueOf(hsvColorMouse[2]).split("\\.")[0];
                colorProperties.setColorMaster1(R,G,B,R_HSV,G_HSV,B_HSV);
                Main.R_Setup1 = R;
                Main.G_Setup1 = G;
                Main.B_Setup1 = B;
                Main.R_HSV_Setup1 = R_HSV;
                Main.G_HSV_Setup1 = G_HSV;
                Main.B_HSV_Setup1 = B_HSV;
            }
        });
        jButtonSetColor2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color color = new Color((int) colorMouse[2], (int) colorMouse[1], (int) colorMouse[0]);// R G B
                jLabelSetup2.setBackground(color);
                ColorProperties colorProperties = new ColorProperties();
                String B = (String) String.valueOf(colorMouse[0]).split("\\.")[0];
                String G = (String) String.valueOf(colorMouse[1]).split("\\.")[0];
                String R = (String) String.valueOf(colorMouse[2]).split("\\.")[0];
                String B_HSV = (String) String.valueOf(hsvColorMouse[0]).split("\\.")[0];
                String G_HSV = (String) String.valueOf(hsvColorMouse[1]).split("\\.")[0];
                String R_HSV = (String) String.valueOf(hsvColorMouse[2]).split("\\.")[0];
                colorProperties.setColorMaster2(R,G,B,R_HSV,G_HSV,B_HSV);
                Main.R_Setup2 = R;
                Main.G_Setup2 = G;
                Main.B_Setup2 = B;
                Main.R_HSV_Setup2 = R_HSV;
                Main.G_HSV_Setup2 = G_HSV;
                Main.B_HSV_Setup2 = B_HSV;
            }
        });
        //-----------

        //-----------

        Main.showDisplay.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                videoCapture.release();

            }
        });
    }
    private void jButtonReset_setColor_click(MouseEvent e){
        jLabal_ColorShow1.setBackground(Color.white);
//        Color colorHsv = new Color((int) hsvColorMouse[2], (int) hsvColorMouse[1], (int) hsvColorMouse[0]);// R G B
        hsvColorDetect[0] = 255;
        hsvColorDetect[1] = 255;
        hsvColorDetect[2] = 255;
    }
    private void button_setColor_click(MouseEvent e){
        Color color = new Color((int) colorMouse[2], (int) colorMouse[1], (int) colorMouse[0]);// R G B
        jLabal_ColorShow1.setBackground(color);
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

        Scalar lowerColor1 = new Scalar(Integer.parseInt(Main.B_HSV_Setup1) - 5, Integer.parseInt(Main.G_HSV_Setup1) - 20, Integer.parseInt(Main.R_HSV_Setup1) - 20);//B, G, R
        Scalar upperColor1 = new Scalar(Integer.parseInt(Main.B_HSV_Setup1) + 5, Integer.parseInt(Main.G_HSV_Setup1) + 20, Integer.parseInt(Main.R_HSV_Setup1) + 20);//B, G, R
        // Threshold the image to isolate the desired color
        // Threshold the image to isolate the desired color
        Mat mask = new Mat();
        Core.inRange(hsvImage, lowerColor1, upperColor1, mask);

        // Find circles with a radius of 10 pixels
        Mat circles = new Mat();
        Imgproc.HoughCircles(mask, circles, Imgproc.HOUGH_GRADIENT, 1, 20, 100, 30, 2, 15);
//        mask: รูปภาพที่คุณต้องการที่จะค้นหาวงกลมในนั้น
//        circles: มาตรฐานของรายการที่เก็บข้อมูลวงกลมที่ค้นพบ
//        method: วิธีการทำงานของการแปลงวงกลม (สามารถใช้ Imgproc.HOUGH_GRADIENT เป็นค่ามาตรฐาน)
//        dp: ค่าสัดส่วนของออกรูปภาพ (1 คือขนาดเดิม, 2 จะลดครึ่งรูปภาพ)
//        minDist: ระยะห่างขั้นต่ำระหว่างจุดศูนย์กลางของวงกลม
//        param1: ค่าพารามิเตอร์แรกสำหรับการค้นหาวงกลม (ลองปรับให้เหมาะสม)
//        param2: ค่าพารามิเตอร์ที่สองสำหรับการค้นหาวงกลม (ลองปรับให้เหมาะสม)
//        minRadius: ความยาวขั้นต่ำของรัศมีวงกลม
//        maxRadius: ความยาวสูงสุดของรัศมีวงกลม
        // Count the number of circles (color points)
        int numberOfColorPoints = (circles.cols() == 0) ? 0 : circles.cols();
        System.out.println("Number of color points with a radius of 10 pixels: " + numberOfColorPoints);
    }
    private void detectColor2(Mat frame) {
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
        Scalar lowerColor1 = new Scalar(Integer.parseInt(Main.B_HSV_Setup1) - 5, Integer.parseInt(Main.G_HSV_Setup1) - 20, Integer.parseInt(Main.R_HSV_Setup1) - 20);//B, G, R
        Scalar upperColor1 = new Scalar(Integer.parseInt(Main.B_HSV_Setup1) + 5, Integer.parseInt(Main.G_HSV_Setup1) + 20, Integer.parseInt(Main.R_HSV_Setup1) + 20);//B, G, R

        Scalar lowerColor2 = new Scalar(Integer.parseInt(Main.B_HSV_Setup2) - 5, Integer.parseInt(Main.G_HSV_Setup2) - 20, Integer.parseInt(Main.R_HSV_Setup2) - 20);//B, G, R
        Scalar upperColor2 = new Scalar(Integer.parseInt(Main.B_HSV_Setup2) + 5, Integer.parseInt(Main.G_HSV_Setup2) + 20, Integer.parseInt(Main.R_HSV_Setup2) + 20);//B, G, R
//        System.out.println(hsvColorDetect[0]+"-"+hsvColorDetect[1]+"-"+hsvColorDetect[2]);
        Mat mask1 = new Mat();
        Mat mask2 = new Mat();
        Core.inRange(hsvImage, lowerColor1, upperColor1, mask1);
        Core.inRange(hsvImage, lowerColor2, upperColor2, mask2);
        int count = Core.countNonZero(mask1);
        int count2 = Core.countNonZero(mask2);
        int chk2Value = 0;




        if(count > Main.imgPixel){//change to detect
            Color color = new Color(Integer.parseInt( Main.R_Setup1)
                    , Integer.parseInt( Main.G_Setup1)
                    , Integer.parseInt( Main.B_Setup1));// R G B
            jLabal_ColorShow1.setBackground(color);
            chk2Value++;
        }else{
            jLabal_ColorShow1.setBackground(new Color(252, 252, 252));
        }

        if(count2 > Main.imgPixel){//change to detect
            Color color = new Color(Integer.parseInt( Main.R_Setup2)
                    , Integer.parseInt( Main.G_Setup2)
                    , Integer.parseInt( Main.B_Setup2));// R G B
            jLabal_ColorShow2.setBackground(color);
            chk2Value++;
        }else{
            jLabal_ColorShow2.setBackground(new Color(252, 252, 252));
        }

        if(chk2Value == 2){
            jLabelStatus.setText("OK");
            jLabelStatus.setBackground(Color.green);
        }else{
            jLabelStatus.setText("NG");
            jLabelStatus.setBackground(Color.RED);
        }
        //System.out.println(count+" - "+count2);
        // Apply the mask to the original frame
        //frame.setTo(mask);
//        frame.setTo(new Scalar(0, 5, 255), mask1).setTo(new Scalar(255, 5, 0), mask2);
//        frame.setTo(new Scalar(0, 5, 255), mask1); // Highlight detected green regions in the frame (green color)
//        frame.setTo(new Scalar(255, 5, 0), mask2); // Highlight detected green regions in the frame (green color)
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
