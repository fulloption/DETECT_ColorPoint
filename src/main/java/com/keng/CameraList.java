package com.keng;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CameraList extends JFrame {
    private JComboBox jComboCameraList;
    private JButton selectButton;
    private JPanel panel1;

    public void showDisplay(){
        setTitle("Select Camera");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 100);
        setContentPane(panel1);


        jComboCameraList.setOpaque(true);
        cameraList();
        Main.cameraList.setVisible(true);
//        jComboCameraList.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Handle the selection change here
//                jComboCameraList_event(e);
//            }
//        });

        selectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println(Main.cameraIndex);
//                VideoCapture videoCapture = new VideoCapture(Main.cameraIndex);
//                if (!videoCapture.isOpened()) {
//                    System.out.println("Error: Camera not found or cannot be opened.");
//                }else{
//                    Main.cameraList.setVisible(false);
//                    ShowDisplay showDisplay = new ShowDisplay();
//                    showDisplay.showDisplay();
//
//                    //this.setVisible(false);
//                }
                Main.cameraList.setVisible(false);
                Main.showDisplay = new ShowDisplay();
                Main.showDisplay.setVisible(true);
                Main.showDisplay.showDisplay();
            }
        });
    }
    private void jComboCameraList_event(ActionEvent e){// handle webcam to change display
        String selectedItem = (String) jComboCameraList.getSelectedItem();
        Main.cameraIndex = Integer.parseInt(selectedItem)-1;
        System.out.println(Main.cameraIndex);

    }
    private void cameraList(){
        /*VideoCapture videoCapture1 = new VideoCapture(0);
        VideoCapture videoCapture2 = new VideoCapture(1);
        VideoCapture videoCapture3 = new VideoCapture(2);
        VideoCapture videoCapture4 = new VideoCapture(3);
        VideoCapture videoCapture5 = new VideoCapture(4);
        jComboCameraList.addItem("9");
        if(videoCapture1.isOpened()){
            jComboCameraList.addItem("1");
        }
        if(videoCapture2.isOpened()){
            jComboCameraList.addItem("2");
        }
        if(videoCapture3.isOpened()){
            jComboCameraList.addItem("3");
        }
        if(videoCapture4.isOpened()){
            jComboCameraList.addItem("4");
        }
        if(videoCapture5.isOpened()){
            jComboCameraList.addItem("5");
        }*/
    }
}
