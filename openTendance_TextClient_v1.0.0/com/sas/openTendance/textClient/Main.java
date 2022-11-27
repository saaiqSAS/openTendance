package com.sas.openTendance.textClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    // Settings
    protected static boolean verboseLogs = true;

    // Colors
    private static Color hexDDDDDD = new Color(0xFFDDDDDD, true);
    private static Color hex202020 = new Color(0xFF202020, true);
    private static Color hex252525 = new Color(0xFF252525, true);
    private static Color hex181818 = new Color(0xFF181818, true);
    private static Color hex54EE00 = new Color(0xFF54EE00, true);
    private static Color hex33CC33 = new Color(0xFF33CC33, true);
    private static Color hexFF2E2E = new Color(0xFFFF2E2E, true);


    //Fonts
    private static Font ArialNormal14 = new Font("Arial", Font.PLAIN,14);
    private static Font ArialNormal16 = new Font("Arial", Font.PLAIN,16);
    private static Font ArialBold14 = new Font("Arial", Font.BOLD,14);
    private static Font ArialBold22 = new Font("Arial", Font.BOLD,22);

    // Vars
    private static String otServerIPInput = "";
    private static String otServerIP = "";
    private static String otServerPort = "";

    private static String otKeyServerIPInput = "";
    private static String otKeyServerIP = "";
    private static String otKeyServerPort = "";

    private static DataOutputStream dos = null;
    private static Socket s = null;

    public static void main(String[] args) {
        System.out.println("");
        System.out.println("================================================");
        System.out.println("||      openTendance: Text Client v1.0.0      ||");
        System.out.println("||                                            ||");
        System.out.println("||             - Debugging Only -             ||");
        System.out.println("||                  By #SFYD                  ||");
        System.out.println("||         +------------------------+         ||");
        System.out.println("||         | Developed By #saaiqSAS |         ||");
        System.out.println("||         +------------------------+         ||");
        System.out.println("================================================\n");
        System.out.println("[+] = Success | [*] = Processing\n[!] = Error   | [?] = Info/Help\n");


        log("Extracting SAS-STE Keys...",2);
        SasSte.extractSteKeys();
        log("Setting up main UI...",2);
        setUI();
        log("Main UI setup completed",1);
    }

    protected static void log(String logText,int logType) {
        String logTypeTemp = "";
        switch (logType) {
            case 1:
                logTypeTemp += "[+] ";
                break;
            case 2:
                logTypeTemp += "[*] ";
                break;
            case 3:
                logTypeTemp += "[!] ";
                break;
            case 4:
                logTypeTemp += "[?] ";
                break;
            default:
                //System.out.println("[!] Error!! Invalid logType");
        }

        if (!verboseLogs) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");
            String time = "(" + LocalTime.now().format(format).toString() + ") ";

            System.out.println(logTypeTemp + time + logText);

        } else if (verboseLogs) {
            DateTimeFormatter formatVerbose = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
            String timeVerbose = "(" + LocalTime.now().format(formatVerbose).toString() + ") ";

            System.out.println(logTypeTemp + timeVerbose + logText);
        }
    }

    protected static int stringToInt(String stringInt) {
        char[] stringIntArr = stringInt.toCharArray();
        int stringIntLength = stringInt.length()-1;
        String revStringInt = "";
        char[] revStringIntArr;
        int tempInt = 0;
        int placeValue = 1;
        int output = 0;

        while (stringIntLength >= 0) {
            revStringInt += stringIntArr[stringIntLength];
            stringIntLength--;
        }

        revStringIntArr = revStringInt.toCharArray();

        for (char echar : revStringIntArr) {
            switch (echar) {
                case ' ':
                    tempInt = 0;
                    break;
                case '0':
                    tempInt = 0;
                    break;
                case '1':
                    tempInt = 1;
                    break;
                case '2':
                    tempInt = 2;
                    break;
                case '3':
                    tempInt = 3;
                    break;
                case '4':
                    tempInt = 4;
                    break;
                case '5':
                    tempInt = 5;
                    break;
                case '6':
                    tempInt = 6;
                    break;
                case '7':
                    tempInt = 7;
                    break;
                case '8':
                    tempInt = 8;
                    break;
                case '9':
                    tempInt = 9;
                    break;
            }
            output += tempInt*placeValue;
            placeValue = placeValue*10;
        }
        return output;
    }

    private static void setUI() {

        // ----- Main Frame -----
        JFrame mainFrame = new JFrame("oT Text Client v1.0.0 ( Debugging Only )");
        mainFrame.setIconImage(new ImageIcon(Main.class.getResource("/res/clientsIcon.png")).getImage());
        Dimension minMainFrame = new Dimension(430,330);
        mainFrame.setMinimumSize(minMainFrame);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        // ----------

            // ----- Main Panel -----
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.setBackground(hex252525);
            // ----------

                // ----- oT Server -----
                JLabel oTServerIP = new JLabel("oT Server IP [ IP:Port ]:");
                oTServerIP.setFont(ArialBold14);
                oTServerIP.setForeground(hexDDDDDD);

                JTextField input_oTServerIP = new JTextField();
                input_oTServerIP.setForeground(hexDDDDDD);
                input_oTServerIP.setBackground(hex252525);
                input_oTServerIP.setFont(ArialNormal14);
                input_oTServerIP.setBorder(BorderFactory.createCompoundBorder(
                        input_oTServerIP.getBorder(),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                input_oTServerIP.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                input_oTServerIP.setBackground(hex181818);
                            }

                            @Override
                            public void focusLost(FocusEvent e) {
                                input_oTServerIP.setBackground(hex252525);
                            }
                        });

                JButton button_connect_oTServer = new JButton("Connect");
                button_connect_oTServer.setBackground(hex252525);
                button_connect_oTServer.setForeground(hex33CC33);
                button_connect_oTServer.setBorder(BorderFactory.createLineBorder(hex33CC33,1));
                button_connect_oTServer.setFont(ArialNormal16);
                button_connect_oTServer.setFocusable(false);
                button_connect_oTServer.setSize(200,100);
                button_connect_oTServer.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_connect_oTServer.setBackground(hex252525);
                        button_connect_oTServer.setForeground(hex33CC33);
                        button_connect_oTServer.setBorder(BorderFactory.createLineBorder(hex33CC33,1));
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_connect_oTServer.setBackground(hex33CC33);
                        button_connect_oTServer.setForeground(hex252525);
                        button_connect_oTServer.setBorder(BorderFactory.createLineBorder(hex33CC33,1));
                    }
                });
                button_connect_oTServer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        otServerIPInput = input_oTServerIP.getText();
                        connectToOTServer();
                    }
                });

                JButton button_disconnect_oTServer = new JButton("Disconnect");
                button_disconnect_oTServer.setBackground(hex252525);
                button_disconnect_oTServer.setForeground(hexFF2E2E);
                button_disconnect_oTServer.setBorder(BorderFactory.createLineBorder(hexFF2E2E,1));
                button_disconnect_oTServer.setFont(ArialNormal16);
                button_disconnect_oTServer.setFocusable(false);
                button_disconnect_oTServer.setSize(200,100);
                button_disconnect_oTServer.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_disconnect_oTServer.setBackground(hex252525);
                        button_disconnect_oTServer.setForeground(hexFF2E2E);
                        button_disconnect_oTServer.setBorder(BorderFactory.createLineBorder(hexFF2E2E,1));
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_disconnect_oTServer.setBackground(hexFF2E2E);
                        button_disconnect_oTServer.setForeground(hex252525);
                        button_disconnect_oTServer.setBorder(BorderFactory.createLineBorder(hexFF2E2E,1));
                    }
                });
                button_disconnect_oTServer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        disconnectFromOTServer();
                    }
                });
                // ----------

                // ----- oT Key Server -----
                JLabel oTKeyServerIP = new JLabel("oT Key Server IP [ IP:Port ]:");
                oTKeyServerIP.setFont(ArialBold14);
                oTKeyServerIP.setForeground(hexDDDDDD);

                JTextField input_oTKeyServerIP = new JTextField();
                input_oTKeyServerIP.setForeground(hexDDDDDD);
                input_oTKeyServerIP.setBackground(hex252525);
                input_oTKeyServerIP.setFont(ArialNormal14);
                input_oTKeyServerIP.setBorder(BorderFactory.createCompoundBorder(
                input_oTKeyServerIP.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                input_oTKeyServerIP.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        input_oTKeyServerIP.setBackground(hex181818);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        input_oTKeyServerIP.setBackground(hex252525);
                    }
                });

                JButton button_connect_oTKeyServer = new JButton("Connect");
                button_connect_oTKeyServer.setBackground(hex252525);
                button_connect_oTKeyServer.setForeground(hex33CC33);
                button_connect_oTKeyServer.setBorder(BorderFactory.createLineBorder(hex33CC33,1));
                button_connect_oTKeyServer.setFont(ArialNormal16);
                button_connect_oTKeyServer.setFocusable(false);
                button_connect_oTKeyServer.setSize(200,100);
                button_connect_oTKeyServer.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_connect_oTKeyServer.setBackground(hex252525);
                        button_connect_oTKeyServer.setForeground(hex33CC33);
                        button_connect_oTKeyServer.setBorder(BorderFactory.createLineBorder(hex33CC33,1));
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_connect_oTKeyServer.setBackground(hex33CC33);
                        button_connect_oTKeyServer.setForeground(hex252525);
                        button_connect_oTKeyServer.setBorder(BorderFactory.createLineBorder(hex33CC33,1));
                    }
                });
                button_connect_oTKeyServer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        log("oT Key Server Client Is Not Yet Ready",4);
                    }
                });
                // ----------

                // ----- Text -----
                JLabel text = new JLabel("Text To Send:");
                text.setFont(ArialBold14);
                text.setForeground(hexDDDDDD);

                JTextField input_text = new JTextField();
                input_text.setForeground(hexDDDDDD);
                input_text.setBackground(hex252525);
                input_text.setFont(ArialBold22);
                input_text.setBorder(BorderFactory.createCompoundBorder(
                input_text.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                input_text.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        input_text.setBackground(hex181818);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        input_text.setBackground(hex252525);
                    }
                });

                JButton button_send = new JButton("Send");
                button_send.setBackground(hex252525);
                button_send.setForeground(hex33CC33);
                button_send.setBorder(BorderFactory.createLineBorder(hex33CC33,1));
                button_send.setFont(ArialNormal16);
                button_send.setFocusable(false);
                button_send.setSize(200,100);
                button_send.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_send.setBackground(hex252525);
                        button_send.setForeground(hex33CC33);
                        button_send.setBorder(BorderFactory.createLineBorder(hex33CC33,1));
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_send.setBackground(hex33CC33);
                        button_send.setForeground(hex252525);
                        button_send.setBorder(BorderFactory.createLineBorder(hex33CC33,1));
                    }
                });
                button_send.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sendText(input_text.getText());
                    }
                });
                // ----------

            GridBagConstraints grid = new GridBagConstraints();
            Insets insetsNone= new Insets(0,0,0,0);
            Insets insetsL5R5 = new Insets(0,5,0,5);
            Insets insetsL5R100 = new Insets(0,5,0,100);
            Insets insetsL100R5 = new Insets(0,100,0,5);
            Insets insetsR5 = new Insets(0,0,0,5);


            // ot Server
            grid.gridx = 0; grid.gridy = 0;
            grid.gridwidth = 1; grid.gridheight = 1;
            grid.weightx = 0.1;  grid.weighty = 0.1;
            grid.anchor = GridBagConstraints.LINE_START;
            grid.insets = insetsL5R5;
            mainPanel.add(oTServerIP,grid);

            grid.gridx = 1; grid.gridy = 0;
            grid.gridwidth = 2; grid.gridheight = 1;
            grid.weightx = 1;  grid.weighty = 0.1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.anchor = GridBagConstraints.CENTER;
            grid.insets = insetsNone;
            mainPanel.add(input_oTServerIP,grid);

            grid.gridx = 0; grid.gridy = 1;
            grid.gridwidth = 1; grid.gridheight = 1;
            grid.weightx = 0.1;  grid.weighty = 0.1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.anchor = GridBagConstraints.LINE_START;
            grid.insets = insetsL5R100;
            mainPanel.add(button_connect_oTServer,grid);

            grid.gridx = 1; grid.gridy = 1;
            grid.gridwidth = 1; grid.gridheight = 1;
            grid.weightx = 0.1;  grid.weighty = 0.1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.anchor = GridBagConstraints.LINE_START;
            grid.insets = insetsL100R5;
            mainPanel.add(button_disconnect_oTServer,grid);

            // ot Key Server
            grid.gridx = 0; grid.gridy = 3;
            grid.gridwidth = 1; grid.gridheight = 1;
            grid.weightx = 0.1;  grid.weighty = 0.1;
            grid.anchor = GridBagConstraints.LINE_START;
            grid.insets = insetsL5R5;
            mainPanel.add(oTKeyServerIP,grid);

            grid.gridx = 1; grid.gridy = 3;
            grid.gridwidth = 2; grid.gridheight = 1;
            grid.weightx = 1;  grid.weighty = 0.1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.anchor = GridBagConstraints.CENTER;
            grid.insets = insetsNone;
            mainPanel.add(input_oTKeyServerIP,grid);

            grid.gridx = 0; grid.gridy = 4;
            grid.gridwidth = 1; grid.gridheight = 1;
            grid.weightx = 0.1;  grid.weighty = 0.1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.anchor = GridBagConstraints.LINE_START;
            grid.insets = insetsL5R100;
            mainPanel.add(button_connect_oTKeyServer,grid);

            // Text
            grid.gridx = 0; grid.gridy = 5;
            grid.gridwidth = 1; grid.gridheight = 1;
            grid.weightx = 0.1;  grid.weighty = 0.1;
            grid.fill = GridBagConstraints.NONE ;
            grid.anchor = GridBagConstraints.LINE_START;
            grid.insets = insetsL5R5;
            mainPanel.add(text,grid);

            grid.gridx = 0; grid.gridy = 6;
            grid.gridwidth = 2; grid.gridheight = 1;
            grid.weightx = 1;  grid.weighty = 0.1;
            grid.fill = GridBagConstraints.HORIZONTAL ;
            grid.anchor = GridBagConstraints.CENTER;
            grid.insets = insetsL5R5;
            mainPanel.add(input_text,grid);

            grid.gridx = 1; grid.gridy = 7;
            grid.gridwidth = 1; grid.gridheight = 1;
            grid.weightx = 0.1;  grid.weighty = 0.1;
            grid.fill = GridBagConstraints.HORIZONTAL ;
            grid.anchor = GridBagConstraints.CENTER;
            grid.insets = insetsL100R5;
            mainPanel.add(button_send,grid);

        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
    }

    private static void connectToOTServer() {

        char[] otServerIPInputArr = otServerIPInput.toCharArray();
        boolean isPort = false;
        otServerIP = "";
        otServerPort = "";

        for (char echar : otServerIPInputArr) {
            if (echar == ':') {
                isPort = true;
            } else {
                if (!isPort) {
                    otServerIP += echar;
                } else {
                    otServerPort += echar;
                }
            }
        }

        log("Extracted oT Server IP : "+otServerIP,1);
        log("Extracted oT Server Port : "+otServerPort,1);

        try {

            if (dos != null) {
                dos.close();
                s.close();
                log("Disconnected From Previous oT Server Connection",1);
            }

            log("Connecting To oT Server at "+otServerIP+":"+otServerPort+"...",2);

            s = new Socket(otServerIP, stringToInt(otServerPort));
            dos = new DataOutputStream(s.getOutputStream());

            log("Successfully Connected To oT Server at "+otServerIP+":"+otServerPort,1);

        } catch (Exception e) {
            log("Error!! Could Not Connect To oT Sever at "+otServerIP+":"+otServerPort,3);
            System.out.println(e.toString());
        }
    }

    private static void sendText(String textToSend) {
        try {
            if (dos == null) {
                log("Please Connect To An openTendance Server First",4);
            } else {
                log("Original Text: "+textToSend,4);
                log("Encrypted Text: "+SasSte.encrypt(textToSend),4);
                log("Decrypted Text: "+SasSte.decrypt(SasSte.encrypt(textToSend)),4);

                dos.writeUTF(SasSte.encrypt(textToSend));
                dos.flush();
            }

        } catch (Exception e) {
            log("Error!! Could Not Send The Text To oT Server",3);
            System.out.println(e.toString());
        }
    }

    private static void disconnectFromOTServer() {
        try {
            if (dos == null) {
                log("Please Connect To An openTendance Server First",4);
            } else {
                dos.close();
                s.close();
            }
        } catch (Exception e) {
            log("Error!! Could Not Disconnect From oT Server",3);
            System.out.println(e.toString());
        }
    }
}