package com.sas.openTendance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class ClientConf {

    private static int numOfClients;
    private static int tempNumOfClients;
    protected static Client[] clients ;
    private static String clientConfFileText = "";
    private static String tempClientConfText = "";

    // Colors
    private static Color hexDDDDDD = new Color(0xFFDDDDDD, true);
    private static Color hex252525 = new Color(0xFF252525, true);
    private static Color hex202020 = new Color(0xFF202020, true);
    private static Color hex181818 = new Color(0xFF181818, true);
    private static Color hex54EE00 = new Color(0xFF54EE00, true);

    //Fonts
    private static Font ArialNormal18 = new Font("Arial", Font.PLAIN,18);
    private static Font ArialBold16 = new Font("Arial", Font.BOLD,16);

    //ClientConfEditor
    private static JTextArea clientConfEditor;

    protected static void extractConfiguration() {
        try {
            clientConfFileText = "";
            Boolean errorHasOccurred = false;
            int totalFileLine = 0;
            int lineNum = 1;
            int clientNum = 0;
            String fileLineValue = "";
            String tempClientName = "";
            char[] fileLineValueArr;
            Boolean fileHasNextLineOne;
            Boolean fileHasNextLine;


            // ----- client config extraction -----
            File clientConfFile = new File(Main.class.getResource("/conf/other_conf/clients.conf").toURI());

            if (clientConfFile.exists()) {
                Scanner clientConfigFileReadOne = new Scanner(clientConfFile);
                fileHasNextLineOne = clientConfigFileReadOne.hasNextLine();

                // ----- Find The Total Number Of Clients In The Conf File -----
                while (fileHasNextLineOne) {
                    totalFileLine++;
                    clientConfFileText += SasSte.decrypt(clientConfigFileReadOne.nextLine()) + "\n";
                    fileHasNextLineOne = clientConfigFileReadOne.hasNextLine();
                }
                numOfClients = totalFileLine/3;
                //----------

                clients = new Client[numOfClients];

                // ----- Extract Configurations From Client Conf File -----
                Scanner clientConfigFileRead = new Scanner(clientConfFile);
                fileHasNextLine = clientConfigFileRead.hasNextLine();

                while (fileHasNextLine) {
                    if (lineNum > 3) {
                        lineNum = 1;
                        clientNum++;
                    }

                    // ----- Remove The "Name:" And "Port:" From Line -----
                    fileLineValueArr = SasSte.decrypt(clientConfigFileRead.nextLine()).toCharArray();
                    int placeValue = 0;
                    fileLineValue = "";
                    for (char echar : fileLineValueArr) {
                        if (placeValue > 4) {
                            fileLineValue += echar;
                        }
                        placeValue++;
                    }
                    //----------

                    switch (lineNum) {
                        case 1:
                            tempClientName = fileLineValue;
                            break;
                        case 2:
                            clients[clientNum] = new Client(tempClientName,Main.stringToInt(fileLineValue));
                            break;
                        case 3:
                            break;
                    }
                    lineNum++;
                    fileHasNextLine = clientConfigFileRead.hasNextLine();
                }
                //----------
                Main.clients = clients;

                for (Client eclient : clients) {
                    String name = eclient.getName();
                    int port = eclient.getPort();
                    Main.log("Client "+name+" Listener At Port "+port,4);
                }
            } else if (!clientConfFile.exists()) {
                errorHasOccurred = true;
            }
            //----------

            if (!errorHasOccurred) {
                Main.log("Client Configurations Extracted",1);
            } else {
                Main.log("An Error Has Occurred While Extracting Client Configurations",3);
            }
        }catch (Exception e) {
            Main.log("Error!! Client Configuration File Missing",3);
        }
    }

    protected static void configureClients() {
        tempClientConfText = clientConfFileText;
        tempNumOfClients = numOfClients;

        // ----- Main Clients Conf Frame -----
        JFrame confClientsFrame = new JFrame("Clients Configuration");
        confClientsFrame.setIconImage(new ImageIcon(Main.class.getResource("/res/logos/clientsIcon.png")).getImage());
        Dimension minSessionConfFrame = new Dimension(500,620);
        confClientsFrame.setMinimumSize(minSessionConfFrame);
        confClientsFrame.setResizable(false);
        confClientsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        // ----------

            // ----- Main Session Config Panel -----
            JPanel confClientsPanel = new JPanel();
            confClientsPanel.setLayout(null);
            confClientsPanel.setBackground(hex181818);
            // ----------

                JButton button_add_client = new JButton();
                button_add_client.setBounds(438,20,24,24);
                button_add_client.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_add.png")));
                button_add_client.setBorderPainted(false);
                button_add_client.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_add_client.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_add_hover.png")));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_add_client.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_add.png")));
                    }
                });
                button_add_client.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tempClientConfText = clientConfEditor.getText();
                        tempClientConfText += "Name:Client"+tempNumOfClients+"\nPort:788"+tempNumOfClients+"\n====================\n";
                        clientConfEditor.setText(tempClientConfText);
                        tempNumOfClients++;
                    }
                });

                clientConfEditor = new JTextArea();
                clientConfEditor.setFont(ArialNormal18);
                clientConfEditor.setBackground(hex202020);
                clientConfEditor.setForeground(hexDDDDDD);
                clientConfEditor.setText(tempClientConfText);
                clientConfEditor.setBorder(BorderFactory.createCompoundBorder(
                        clientConfEditor.getBorder(),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                JScrollPane clientConfEditorScrollable = new JScrollPane(clientConfEditor);
                clientConfEditorScrollable.setBounds(20,55,445,460);
                clientConfEditorScrollable.setBackground(hex202020);
                clientConfEditorScrollable.setBorder(BorderFactory.createCompoundBorder(
                clientConfEditor.getBorder(),
                BorderFactory.createBevelBorder(2,hex202020,hex202020)));

                JButton button_save_clients_configuration = new JButton();
                button_save_clients_configuration.setBounds(385,536,80,26);
                button_save_clients_configuration.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save.png")));
                button_save_clients_configuration.setBorderPainted(false);
                button_save_clients_configuration.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_save_clients_configuration.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save_hover.png")));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_save_clients_configuration.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save.png")));
                    }
                });
                button_save_clients_configuration.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tempClientConfText = clientConfEditor.getText();
                        saveClientConf();
                        clientConfEditor.setText(clientConfFileText);
                        confClientsFrame.dispose();
                    }
                });

            confClientsPanel.add(button_add_client);
            confClientsPanel.add(clientConfEditorScrollable);
            confClientsPanel.add(button_save_clients_configuration);

        confClientsFrame.setContentPane(confClientsPanel);
        confClientsFrame.setVisible(true);
    }

    private static void saveClientConf() {
        try {
            // ----- Check The New Client Conf -----
            // Make Temporary File
            String tempFilePathExtract = new File("temp.temp").getCanonicalPath();

            File tempFile = new File(tempFilePathExtract);
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }

            // Save New Conf To Temporary File
            FileWriter tempFileWrite = new FileWriter(tempFile,false);
            tempFileWrite.write(SasSte.encryptMulti(tempClientConfText));
            tempFileWrite.close();

            // Read Temporary File While Checking
            Scanner tempFileRead = new Scanner(tempFile);
            boolean fileHasNextLine = tempFileRead.hasNextLine();
            boolean errorHasOccurred = false;
            int lineNum = 1;
            int totalLineNum = 1;
            int currLineValueLength;
            String currLineValue = "";
            char[] currLineValueArr;

            while (fileHasNextLine) {
                currLineValue = "";

                if (lineNum > 3) {
                    lineNum = 1;
                }

                currLineValue = SasSte.decrypt(tempFileRead.nextLine());
                currLineValueArr = currLineValue.toCharArray();
                currLineValueLength = currLineValue.length() - 1;

                switch (lineNum) {
                    case 1:
                            if (currLineValueArr[0] != 'N' | currLineValueArr[1] != 'a' | currLineValueArr[2] != 'm' | currLineValueArr[3] != 'e' | currLineValueArr[4] != ':') {
                                Main.log("Error!! New Client Configuration Is In An Incorrect Format, Could Not Save The New Configuration",3);
                                errorHasOccurred = true;
                            }
                        break;
                    case 2:
                        if (currLineValueArr[0] != 'P' | currLineValueArr[1] != 'o' | currLineValueArr[2] != 'r' | currLineValueArr[3] != 't' | currLineValueArr[4] != ':') {
                            Main.log("Error!! New Client Configuration Is In An Incorrect Format, Could Not Save The New Configuration",3);
                            errorHasOccurred = true;
                        }

                        int placeValue = 5;
                        String port = "";
                        while (placeValue <= currLineValueLength) {
                            port += currLineValueArr[placeValue];
                            placeValue++;
                        }
                        if (Main.stringToInt(port) > 65000 | Main.stringToInt(port) < 1 ) {
                            Main.log("Error!! New Client Configuration Contains An Invalid Port Number, Could Not Save The New Configuration",3);
                            errorHasOccurred = true;
                        }
                        break;
                    case 3:
                        if (currLineValueArr[0] != '=' | currLineValueArr[1] != '=' | currLineValueArr[2] != '=' | currLineValueArr[3] != '=' | currLineValueArr[4] != '=' | currLineValueLength != 19) {
                            Main.log("Error!! New Client Configuration Is In An Incorrect Format, Could Not Save The New Configuration",3);
                            errorHasOccurred = true;
                        }
                        break;
                }

                fileHasNextLine = tempFileRead.hasNextLine();
                totalLineNum++;
                lineNum++;
            }

            if (tempFile.exists()) {
                tempFile.delete();
            }

            if (errorHasOccurred) {
                Main.log("Error!! Could Not Save The New Client Configuration",3);
            } else if (!errorHasOccurred) {
                // Save New Configuration To Actual Client Conf File
                File clientConfFile = new File(Main.class.getResource("/conf/other_conf/clients.conf").toURI());
                if (!clientConfFile.exists()) {
                    clientConfFile.createNewFile();
                }
                FileWriter clientConfFileWrite = new FileWriter(clientConfFile,false);
                clientConfFileWrite.write(SasSte.encryptMulti(tempClientConfText));
                clientConfFileWrite.close();

                Main.log("New Client Configuration Saved",1);
                extractConfiguration();
            }
            //----------
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
