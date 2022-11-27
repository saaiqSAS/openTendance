package com.sas.openTendance.textClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class SasSte {

    // Colors
    private static Color hexDDDDDD = new Color(0xFFDDDDDD, true);
    private static Color hex252525 = new Color(0xFF252525, true);
    private static Color hex202020 = new Color(0xFF202020, true);
    private static Color hex181818 = new Color(0xFF181818, true);
    private static Color hex54EE00 = new Color(0xFF54EE00, true);

    //Fonts
    private static Font ArialNormal14 = new Font("Arial", Font.PLAIN,14);
    private static Font ArialBold16 = new Font("Arial", Font.BOLD,16);

    // UI Elements
    private static JTextArea keyEditor;
    private static JTextArea staticKeysEditor;

    // File Texts
    private static String session1ConfText = "";
    private static String session2ConfText = "";
    private static String session3ConfText = "";
    private static String session4ConfText = "";
    private static String clientsConfText = "";

    protected static final char[] charSet = {' ','!','\"','#','$','%','&','\'','(',')','*','+',',','-','.','/','0','1','2','3','4','5','6','7','8','9',':',';','<','=','>','?','@','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','[','\\',']','^','_','`','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','{','|','}','~'}; //95chars
    protected static String key = "";
    protected static String processString = "";
    protected static String result = "";


    protected static String encryptMulti(String textToEncrypt) {
        String[] processStringArr = textToEncrypt.split("\n");
        result = "";

        for (String estring : processStringArr) {
            processString = estring;
            process.encrypt();
            result += process.result + "\n";
        }

        return result;
    }

    protected static String decryptMulti(String textToDecrypt) {
        String[] processStringArr = textToDecrypt.split("\n");
        result = "";

        for (String estring : processStringArr) {
            processString = estring;
            process.decrypt();
            result += process.result + "\n";
        }

        return  result;
    }

    protected static String encrypt(String textToEncrypt) {
        result = "";
        processString = textToEncrypt;
        process.encrypt();
        result += process.result;

        return result;
    }

    protected static String decrypt(String textToDecrypt) {
        result = "";
        processString = textToDecrypt;
        process.decrypt();
        result += process.result;

        return  result;
    }


    protected static void configureSasSte() {
        // ----- Main Session Config Frame -----
        JFrame confSasSteFrame = new JFrame("SAS STE Configuration");
        confSasSteFrame.setIconImage(new ImageIcon(Main.class.getResource("/res/logos/steIcon.png")).getImage());
        confSasSteFrame.setSize(500,620);
        confSasSteFrame.setLayout(null);
        confSasSteFrame.setResizable(false);
        confSasSteFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        // ----------

            // ----- Main Session Config Panel -----
            JPanel confSasStePanel = new JPanel();
            confSasStePanel.setSize(500,620);
            confSasStePanel.setLayout(null);
            confSasStePanel.setBackground(hex181818);
            // ----------

                // ----- Key Gen Page -----
                JPanel keyGenPanel = new JPanel();
                keyGenPanel.setSize(475,575);
                keyGenPanel.setLayout(null);
                keyGenPanel.setBackground(hex181818);
                // ----------

                    // ----- Key Display ------
                    keyEditor = new JTextArea();
                    keyEditor.setBounds(0,0,450,170);
                    keyEditor.setLineWrap(true);
                    keyEditor.setFont(ArialNormal14);
                    keyEditor.setBackground(hex202020);
                    keyEditor.setForeground(hexDDDDDD);
                    keyEditor.setEditable(false);
                    keyEditor.setText("Key Display Area");
                    keyEditor.setBorder(BorderFactory.createCompoundBorder(
                            keyEditor.getBorder(),
                                        BorderFactory.createEmptyBorder(6, 6, 6, 6)));

                                JScrollPane keyEditorScrollable = new JScrollPane(keyEditor);
                                keyEditorScrollable.setBounds(10,10,450,100);
                                keyEditorScrollable.setBackground(hex202020);
                                keyEditorScrollable.setBorder(BorderFactory.createCompoundBorder(
                                                                keyEditor.getBorder(),
                                        BorderFactory.createBevelBorder(2,hex202020,hex202020)));
                    // ----------

                    // ----- Static Key Display ------
                    staticKeysEditor = new JTextArea();
                    staticKeysEditor.setLineWrap(true);
                    staticKeysEditor.setFont(ArialNormal14);
                    staticKeysEditor.setBackground(hex202020);
                    staticKeysEditor.setForeground(hexDDDDDD);
                    staticKeysEditor.setEditable(false);
                    staticKeysEditor.setText("Static Keys Display Area");
                    staticKeysEditor.setBorder(BorderFactory.createCompoundBorder(
                            staticKeysEditor.getBorder(),
                            BorderFactory.createEmptyBorder(6, 6, 6, 6)));

                    JScrollPane staticKeysEditorScrollable = new JScrollPane(staticKeysEditor);
                    staticKeysEditorScrollable.setBounds(10,120,450,380);
                    staticKeysEditorScrollable.setBackground(hex202020);
                    staticKeysEditorScrollable.setBorder(BorderFactory.createCompoundBorder(
                            staticKeysEditor.getBorder(),
                            BorderFactory.createBevelBorder(2,hex202020,hex202020)));
                    // ----------

                    JButton button_gen_key = new JButton();
                    button_gen_key.setBounds(10,510,143,26);
                    button_gen_key.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_generate_keys.png")));
                    button_gen_key.setBorderPainted(false);
                    button_gen_key.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            button_gen_key.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_generate_keys_hover.png")));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            button_gen_key.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_generate_keys.png")));
                        }
                    });
                    button_gen_key.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String key = generateKey();
                            String staticKey = generateStaticKeys();

                            keyEditor.setText(key);
                            staticKeysEditor.setText(staticKey);
                        }
                    });

                    JButton button_save_key = new JButton();
                    button_save_key.setBounds(380,510,80,26);
                    button_save_key.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save.png")));
                    button_save_key.setBorderPainted(false);
                    button_save_key.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            button_save_key.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save_hover.png")));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            button_save_key.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save.png")));
                        }
                    });
                    button_save_key.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                           saveKeys();
                           confSasSteFrame.dispose();
                        }
                    });

                keyGenPanel.add(button_gen_key);
                keyGenPanel.add(button_save_key);
                keyGenPanel.add(keyEditorScrollable);
                keyGenPanel.add(staticKeysEditorScrollable);

                // ----- Key Server Page -----
                JPanel keyServerPanel = new JPanel();
                keyServerPanel.setSize(475,575);
                keyServerPanel.setLayout(null);
                keyServerPanel.setBackground(hex181818);
                // ----------

                // ----- Tabbed Pane -----
                UIManager.put("TabbedPane.selected", hex252525);
                JTabbedPane tabbedPane =new JTabbedPane();
                tabbedPane.setBounds(5,0,475,575);
                tabbedPane.setFocusable(false);
                tabbedPane.setBackground(hex181818);
                tabbedPane.setForeground(hexDDDDDD);
                tabbedPane.setFont(ArialBold16);
                tabbedPane.add("     Key Generator    ",keyGenPanel);
                tabbedPane.add("      Key Server      ",keyServerPanel);
                // ----------

                confSasStePanel.add(tabbedPane);

        confSasSteFrame.add(confSasStePanel);
        confSasSteFrame.setVisible(true);
    }

    protected static void extractSteKeys() {
        // ----- StaticKeys extraction -----
        process.getStaticKeys();
        //----------
        try {
            Boolean errorHasOccurred = false;
            int us = 8;
            int crrNum = 0;
            String tempfileLineValue = "";
            char[] tempfileLineValueArr;
            String fileLineValue = "";
            Boolean fileHasNextLine;


            // ----- Keys extraction -----
            File steKeyFile = new File(Main.class.getResource("/conf/ste_conf/Key.skey").toURI());

            if (steKeyFile.exists()) {
                try {
                    Scanner steKeyFileRead = new Scanner(steKeyFile);
                    fileHasNextLine = steKeyFileRead.hasNextLine();

                    tempfileLineValue = steKeyFileRead.nextLine();
                    tempfileLineValueArr = tempfileLineValue.toCharArray();

                    for (char echar : tempfileLineValueArr) {
                        if (crrNum != us) {
                            fileLineValue += echar;
                        }
                        crrNum++;
                    }

                    if (fileLineValue.length() == 112) {
                        key = fileLineValue;
                    } else {
                        Main.log("Error!! SAS-STE Key File Is In An Incorrect Format",3);
                        errorHasOccurred = true;
                    }

                } catch (Exception e) {
                    Main.log("Error!! Could Not Successfully Read SAS-STE Key File",3);
                    errorHasOccurred = true;
                }
            } else if (!steKeyFile.exists()) {
                errorHasOccurred = true;
            }
            //----------

            if (!errorHasOccurred) {
                Main.log("SAS-STE Key Extracted",1);
            } else {
                Main.log("An Error Has Occurred While Setting Up SAS-STE",3);
            }
        } catch (Exception e) {
            Main.log("Error!! SAS-STE Key File Missing",3);
        }
    }

    private static String generateKey() {
        String tempKey = "$STE::";

        Random rand = new Random();
        Integer[] charSetKey = new Integer[95];

        for (int i = 0; i < charSetKey.length; i++) {
            charSetKey[i] = i;
        }
        Collections.shuffle(Arrays.asList(charSetKey));

        tempKey += rand.nextInt(3); // L0Type
        tempKey += rand.nextInt(6); //L2pAsign
        tempKey += charSet[rand.nextInt(90)];

        for (int charKey : charSetKey) {
            tempKey += charSet[charKey];
        }

        tempKey += rand.nextInt(10); //L1Rep
        tempKey += rand.nextInt(10); //L2Rep
        tempKey += rand.nextInt(5); // fullRep

        tempKey += "::KEY$";

        return tempKey;

    }

    private static String generateStaticKeys() {
                Integer[] arr = new Integer[95];
                String tempStaticKeys = "";
                String tempArrToString = "";
                char[] tempArrToStringArr = new char[95];

                for (int i = 0; i < arr.length; i++) {
                    arr[i] = i;
                }
                tempStaticKeys += "SAS-STE-StaticKeys\n";
                tempStaticKeys += "==================\n";
                tempStaticKeys += "StaticKeys: " + "openTendance" + "\n";
                tempStaticKeys += "==================\n";

                tempStaticKeys += "Layer1:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL1 : tempArrToStringArr) {
                    if (echarL1 != '[' && echarL1 != ']') {
                        tempArrToString += echarL1;
                    } else {

                    }
                }
                tempStaticKeys += " " + tempArrToString + "," + "\n";

                tempStaticKeys += "Layer2p1:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL2p1 : tempArrToStringArr) {
                    if (echarL2p1 != '[' && echarL2p1 != ']') {
                        tempArrToString += echarL2p1;
                    }
                }
                tempStaticKeys += " " + tempArrToString + "," + "\n";

                tempStaticKeys += "Layer2p2:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL2p2 : tempArrToStringArr) {
                    if (echarL2p2 != '[' && echarL2p2 != ']') {
                        tempArrToString += echarL2p2;
                    }
                }
                tempStaticKeys += " " + tempArrToString + "," + "\n";

                tempStaticKeys += "Layer2p3:\n";
                Collections.shuffle(Arrays.asList(arr));
                tempArrToStringArr = Arrays.toString(arr).toCharArray();
                tempArrToString = "";
                for (char echarL2p3 : tempArrToStringArr) {
                    if (echarL2p3 != '[' && echarL2p3 != ']') {
                        tempArrToString += echarL2p3;
                    }
                }
                tempStaticKeys += " " + tempArrToString + "," + "\n";

                tempStaticKeys += "==================\n";

                return tempStaticKeys;
    }

    private static void saveKeys() {
        try {
            //Get All Text From Files As Plain Text
            extractAllFileTexts();

            // Save New Keys
            File keyFile = new File(Main.class.getResource("/conf/ste_conf/Key.skey").toURI());
            File staticKeysFile = new File(Main.class.getResource("/conf/ste_conf/StaticKeys.stkey").toURI());

            FileWriter keyFileWrite = new FileWriter(keyFile,false);
            FileWriter staticKeysFileWrite = new FileWriter(staticKeysFile,false);

            String tempKey = keyEditor.getText();
            String tempStaticKeys = staticKeysEditor.getText();

            keyFileWrite.write(tempKey);
            staticKeysFileWrite.write(tempStaticKeys);

            keyFileWrite.close();
            staticKeysFileWrite.close();

            //Extract New Keys
            process.keyExtracted = false;
            extractSteKeys();

            //Save All Texts To Files After Encrypting With New Key
            saveAllFileTexts();



        } catch (Exception e) {

        }

    }

    private static void extractAllFileTexts() {
        try {
            // ----- Extract All Session Conf Text -----
            session1ConfText = "";
            session2ConfText = "";
            session3ConfText = "";
            session4ConfText = "";

            File session1ConfFile = new File(Main.class.getResource("/conf/sess_conf/session1.conf").toURI());
            File session2ConfFile = new File(Main.class.getResource("/conf/sess_conf/session2.conf").toURI());
            File session3ConfFile = new File(Main.class.getResource("/conf/sess_conf/session3.conf").toURI());
            File session4ConfFile = new File(Main.class.getResource("/conf/sess_conf/session4.conf").toURI());

            Scanner session1ConfFileRead = new Scanner(session1ConfFile);
            Scanner session2ConfFileRead = new Scanner(session2ConfFile);
            Scanner session3ConfFileRead = new Scanner(session3ConfFile);
            Scanner session4ConfFileRead = new Scanner(session4ConfFile);

            Boolean session1ConfFileHasNextLine = session1ConfFileRead.hasNextLine();
            Boolean session2ConfFileHasNextLine = session2ConfFileRead.hasNextLine();
            Boolean session3ConfFileHasNextLine = session3ConfFileRead.hasNextLine();
            Boolean session4ConfFileHasNextLine = session4ConfFileRead.hasNextLine();

            while (session1ConfFileHasNextLine) {
                session1ConfText += SasSte.decrypt(session1ConfFileRead.nextLine()) + "\n";
                session1ConfFileHasNextLine = session1ConfFileRead.hasNextLine();
            }
            while (session2ConfFileHasNextLine) {
                session2ConfText += SasSte.decrypt(session2ConfFileRead.nextLine()) + "\n";
                session2ConfFileHasNextLine = session2ConfFileRead.hasNextLine();
            }
            while (session3ConfFileHasNextLine) {
                session3ConfText += SasSte.decrypt(session3ConfFileRead.nextLine()) + "\n";
                session3ConfFileHasNextLine = session3ConfFileRead.hasNextLine();
            }
            while (session4ConfFileHasNextLine) {
                session4ConfText += SasSte.decrypt(session4ConfFileRead.nextLine()) + "\n";
                session4ConfFileHasNextLine = session4ConfFileRead.hasNextLine();
            }
            //-----------

            // ----- Extract Clients Conf Text -----
            clientsConfText = "";

            File clientConfFile = new File(Main.class.getResource("/conf/other_conf/clients.conf").toURI());

            Scanner clientConfFileRead = new Scanner(clientConfFile);

            Boolean clientConfFileHasNextLine = clientConfFileRead.hasNextLine();

            while (clientConfFileHasNextLine) {
                clientsConfText += SasSte.decrypt(clientConfFileRead.nextLine()) + "\n";
                clientConfFileHasNextLine = clientConfFileRead.hasNextLine();
            }
            //----------

            Main.log("All Files Read And Decrypted With Old Keys",1);
        } catch (Exception e) {
            Main.log("An Error Has Occurred While Reading All Files",3);
        }
    }

    private static void saveAllFileTexts() {
        try {
            // ----- Save All Session Confs -----
            File session1ConfFile = new File(Main.class.getResource("/conf/sess_conf/session1.conf").toURI());
            File session2ConfFile = new File(Main.class.getResource("/conf/sess_conf/session2.conf").toURI());
            File session3ConfFile = new File(Main.class.getResource("/conf/sess_conf/session3.conf").toURI());
            File session4ConfFile = new File(Main.class.getResource("/conf/sess_conf/session4.conf").toURI());

            FileWriter session1ConfFileWrite = new FileWriter(session1ConfFile,false);
            FileWriter session2ConfFileWrite = new FileWriter(session2ConfFile,false);
            FileWriter session3ConfFileWrite = new FileWriter(session3ConfFile,false);
            FileWriter session4ConfFileWrite = new FileWriter(session4ConfFile,false);

            session1ConfFileWrite.write(SasSte.encryptMulti(session1ConfText));
            session2ConfFileWrite.write(SasSte.encryptMulti(session2ConfText));
            session3ConfFileWrite.write(SasSte.encryptMulti(session3ConfText));
            session4ConfFileWrite.write(SasSte.encryptMulti(session4ConfText));

            session1ConfFileWrite.close();
            session2ConfFileWrite.close();
            session3ConfFileWrite.close();
            session4ConfFileWrite.close();
            //----------

            // ----- Save Client Conf -----
            File clientConfFile = new File(Main.class.getResource("/conf/other_conf/clients.conf").toURI());

            FileWriter clientConfFileWrite = new FileWriter(clientConfFile,false);

            clientConfFileWrite.write(SasSte.encryptMulti(clientsConfText));

            clientConfFileWrite.close();
            //----------
            Main.log("All Files Encrypted With New Keys And Saved",1);
        } catch (Exception e) {
            Main.log("An Error Has Occurred While Encrypting All Files With New Key",3);
        }
    }




}
