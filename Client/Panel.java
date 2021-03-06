
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kam
 */
public class Panel extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    private ArrayList<Route> routes;
    private static Log log = new Log();
    private JLabel labelContent[];
    int videoCounter = 0;
    Adv adv;

    public Panel() {
        //this.setLayout(new GridLayout(1,15));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    //socket.close();
                    //outputobj.close();
                    //outputobj.flush();
                    //inputobj.close();
                    //inputobj = null;
                    e.getWindow().dispose();
                } catch (Exception ex) {
                    log.jTextArea1.append("Error occur while closing" + LocalDateTime.now() + "\n");
                }

            }
        });
        setVisible(true);
        setTitle("Bus Panel Display");
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        routes = new ArrayList<Route>(0);
        //pane = getContentPane();
        //pane.setLayout(null);
        labelContent = new JLabel[10];
        for (int i = 0; i < 10; i++) {
            labelContent[i] = new JLabel();
            labelContent[i].setFont(new Font("Arial", 0, 20));
            labelContent[i].setLocation(0, 0);
            labelContent[i].setBounds(0, 0 + (i * 53), 1280, 53);
            labelContent[i].setLayout(null);
            labelContent[i].setVisible(true);
            labelContent[i].setOpaque(true);
            if (i % 2 == 0) {
                labelContent[i].setBackground(Color.YELLOW);
            } else {
                labelContent[i].setBackground(Color.white);
            }
            add(labelContent[i]);
        }
        adv = new Adv();
        add(adv);
        //add(adv);
        //validate();
        //repaint();
        initComponents();
    }

    public class Adv extends javax.swing.JPanel {

        public Adv() {
            setSize(640, 360);
            setLocation(0, 530);
            //setOpaque(true);
            setVisible(true);
            new Thread(new Play()).start();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Socket socket = null;
                while (true) {
                    String ip = JOptionPane.showInputDialog("Welcome to Bus_Client\nIP: ", "localhost");
                    String port = JOptionPane.showInputDialog("Port: ", "8009");
                    log.jTextArea1.append("Connecting to " + ip);
                    try {
                        socket = new Socket(ip.trim(), Integer.parseInt(port.trim()));
                        break;
                    } catch (IOException e) {
                        log.jTextArea1.append(" fail\t" + LocalDateTime.now() + "\n");
                    } catch (Exception e) {
                        System.exit(0);
                    }
                }
                Panel Panel = new Panel();
                Panel.setSize(645, 920);
                log.jTextArea1.append(" successful\t" + LocalDateTime.now() + "\n");
                Panel.Client thread1 = Panel.new Client(socket);
                new Thread(thread1).start();
                Panel.Update thread2 = Panel.new Update(socket);
                new Thread(thread2).start();
            }
        });
    }

    private class Client implements Runnable {

        private ObjectOutputStream outputobj;
        private ObjectInputStream inputobj;
        private Socket socket;

        public Client(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            Route route = null;
            while (true) {
                try {
                    outputobj = new ObjectOutputStream(socket.getOutputStream());
                    outputobj.flush();
                    inputobj = new ObjectInputStream(socket.getInputStream());
                } catch (Exception e) {
                    log.jTextArea1.append("IOError occur when getting the stream.\t" + LocalDateTime.now() + "\n");
                }
                if (inputobj == null) {
                    break;
                }
                try {
                    route = (Route) inputobj.readObject();
                    inputobj = null;
                } catch (IOException | ClassNotFoundException e) {
                    continue;
                }
                if (route != null) {
                    routes.add(route);
                    log.jTextArea1.append("One data is receive from server\t" + LocalDateTime.now() + "\n");
                    /*for (int i = 0; i < 10; i++) {
                        if (labelContent[i].getText() == "") {
                            log.jTextArea1.append("One data is receive from server\t" + LocalDateTime.now() + "\n");
                            labelContent[i].setText("Bus Number: " + route.getRouteNum() + " Fares: $" + route.getFares() + " Time: " + route.getTime());
                            break;
                        }
                    }*/
                }
            }
        }
    }

    private class Play extends Thread {

        String advname;

        public void run() {
            while (true) {
                for (int i = 0; i <= 748; i++) {
                    if (i < 10) {
                        advname = "《跟住矛盾去旅行》 曾鈺成 X 梁國雄 30s trailer000" + i + ".jpg";
                    } else if (i >= 10 && i <= 99) {
                        advname = "《跟住矛盾去旅行》 曾鈺成 X 梁國雄 30s trailer00" + i + ".jpg";
                    } else {
                        advname = "《跟住矛盾去旅行》 曾鈺成 X 梁國雄 30s trailer0" + i + ".jpg";
                    }
                    try {
                        BufferedImage myPicture = ImageIO.read(new File("untitled folder/" + advname));
                        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                        adv.add(picLabel);
                        adv.repaint();
                        adv.validate();
                    } catch (IOException e) {
                    }
                    try {
                        sleep(30);
                    } catch (Exception e) {
                    }
                    adv.removeAll();
                }
            }
        }
    }

    private class Update implements Runnable {

        private DataOutputStream outputdata;
        private Socket socket;

        public Update(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            while (true) {
                try {
                    outputdata = new DataOutputStream(socket.getOutputStream());
                    outputdata.flush();
                } catch (Exception e) {
                }
                System.out.flush();
                if (!routes.isEmpty()) {
                    while (routes.size() > 10) {
                        routes.remove(0);
                    }
                    for (int i = 0; i < routes.size(); i++) {
                        labelContent[i].setText("Bus Number: " + routes.get(i).getRouteNum() + "           Fares: $" + routes.get(i).getFares() + "           Time: " + routes.get(i).getTime());
                        Scanner input = new Scanner(routes.get(i).getTime()).useDelimiter(":");
                        try {
                            int hour = input.nextInt(), min = input.nextInt(), sec = input.nextInt();
                            int totalSecond = sec + min * 60 + hour * 3600;
                            if (hour == 0 && min == 0 && sec == 0) {
                                routes.get(i).setTime("Arrived");
                                outputdata.writeInt(i);
                                //outputdata.writeUTF(routes.get(i).getRouteNum()+"     "+routes.get(i).getFares()+"     "+"0:0:0");
                            } else {
                                routes.get(i).setTime(String.valueOf((totalSecond - 1) / 3600) + ":" + String.valueOf(((totalSecond - 1) % 3600) / 60) + ":" + String.valueOf(((totalSecond - 1) % 3600) % 60));
                            }
                        } catch (Exception e) {
                        }
                        //hour=(sec+min*60+hour*3600-1)/3600;
                        //min=((sec+min*60+hour*3600-1)-hour*3600)/60;
                        //sec=(sec+min*60+hour*3600-1)-hour*3600-min*60;      
                        //routes.get(i).setTime(String.valueOf((sec+min*60+hour*3600-1)/3600)+":"+String.valueOf(((sec+min*60+hour*3600-1)-(sec+min*60+hour*3600-1)/3600*3600)/60)+":"+String.valueOf((sec+min*60+hour*3600-1)-((sec+min*60+hour*3600-1)/3600)*3600-(((sec+min*60+hour*3600-1)-hour*3600)/60)*60));

                        //System.out.printf("%d  %d  %d\n",(sec+min*60+hour*3600-1)/3600,((sec+min*60+hour*3600-1)-(sec+min*60+hour*3600-1)/3600*3600)/60,(sec+min*60+hour*3600-1)-((sec+min*60+hour*3600-1)/3600)*3600-(((sec+min*60+hour*3600-1)-hour*3600)/60)*60);
                        //System.out.println((totalSecond-1)/3600+"  "+((totalSecond-1)/60)%60+"  "+((totalSecond-1)/3600)%60);
                        //if(Integer.parseInt("5")                        
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

 
