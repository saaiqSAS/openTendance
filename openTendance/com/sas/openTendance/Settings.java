package com.sas.openTendance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Settings {

    // Colors
    private static Color hexDDDDDD = new Color(0xFFDDDDDD, true);
    private static Color hex909090 = new Color(0xFF909090, true);
    private static Color hex252525 = new Color(0xFF252525, true);
    private static Color hex181818 = new Color(0xFF181818, true);
    private static Color hex54EE00 = new Color(0xFF54EE00, true);

    //Fonts
    private static Font ArialNormal12 = new Font("Arial", Font.PLAIN,12);
    private static Font ArialBold14 = new Font("Arial", Font.BOLD,14);

    //Settings
    protected static Boolean verboseLogsTemp;
    protected static Boolean timestampDBUpdatesTemp;

    protected static void extractSettings() {
        try {
            Boolean errorHasOccurred = false;

            // ----- Settings Configuration Extraction -----
            File settingsConfFile = new File(Main.class.getResource("/conf/other_conf/settings.conf").toURI());

            if (settingsConfFile.exists()) {
                Scanner settingsConfFileRead = new Scanner(settingsConfFile);
                Boolean fileHasNextLine = settingsConfFileRead.hasNextLine();
                String fileLineValue = "";
                Boolean fileLineValueBoolean = false;
                int fileLineNum = 1;

                while (fileHasNextLine) {
                    fileLineValue = settingsConfFileRead.nextLine();

                    if (fileLineValue.equals("0")) {
                        fileLineValueBoolean = false;
                    } else if (fileLineValue.equals("1")) {
                        fileLineValueBoolean = true;
                    } else {
                        errorHasOccurred = true;
                        Main.log("Error!! Invalid Configuration In Settings Configuration File",3);
                    }

                    if (!errorHasOccurred) {
                        switch (fileLineNum) {
                            case 1:
                                Main.verboseLogs = fileLineValueBoolean;
                                break;
                            case 2:
                                Main.timestampDBUpdates = fileLineValueBoolean;
                                break;
                        }
                    }

                    fileHasNextLine = settingsConfFileRead.hasNextLine();
                    fileLineNum++;
                }

            } else if (!settingsConfFile.exists()) {
                errorHasOccurred = true;
            }
            // ----------
            if (!errorHasOccurred) {
                //Main.log("Settings Configurations Extracted",1);
            } else {
                Main.log("An Error Has Occurred While Extracting Settings Configurations",3);
            }
        } catch (Exception e) {
            Main.log("Error!! Setting Configuration File Missing",3);
        }
    }

    protected static void configureSettings() {
        verboseLogsTemp = Main.verboseLogs;
        timestampDBUpdatesTemp = Main.timestampDBUpdates;

        // ----- Main Settings Conf Frame -----
        JFrame confSettingsFrame = new JFrame("Settings");
        confSettingsFrame.setIconImage(new ImageIcon(Main.class.getResource("/res/logos/settingsIcon.png")).getImage());
        confSettingsFrame.setSize(500,620);
        confSettingsFrame.setLayout(null);
        confSettingsFrame.setResizable(false);
        confSettingsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        // ----------

            // ----- Main Settings Config Panel -----
            JPanel confSettingsPanel = new JPanel();
            confSettingsPanel.setSize(500,620);
            confSettingsPanel.setLayout(null);
            confSettingsPanel.setBackground(hex181818);
            // ----------

                // ----- Verbose Logs -----
                JTextArea settings1 = new JTextArea();
                settings1.setBounds(10,15,300,27);
                settings1.setLineWrap(true);
                settings1.setFont(ArialBold14);
                settings1.setBackground(hex181818);
                settings1.setForeground(hexDDDDDD);
                settings1.setEditable(false);
                settings1.setText("Verbose Logs");
                settings1.setBorder(BorderFactory.createCompoundBorder(
                settings1.getBorder(),
                    BorderFactory.createEmptyBorder(6, 6, 6, 6)));

                JTextArea settings1Description = new JTextArea();
                settings1Description.setBounds(20,35,300,30);
                settings1Description.setLineWrap(true);
                settings1Description.setFont(ArialNormal12);
                settings1Description.setBackground(hex181818);
                settings1Description.setForeground(hex909090);
                settings1Description.setEditable(false);
                settings1Description.setText("Prints extra detailed logs for everything");
                settings1Description.setBorder(BorderFactory.createCompoundBorder(
                        settings1Description.getBorder(),
                        BorderFactory.createEmptyBorder(6, 6, 6, 6)));

                JButton button_settings1 = new JButton();
                button_settings1.setBounds(430,30,26,26);
                if (verboseLogsTemp) {
                    button_settings1.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_check.png")));
                } else {
                    button_settings1.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_cross.png")));
                }
                button_settings1.setBorderPainted(false);
                button_settings1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        if (verboseLogsTemp) {
                            button_settings1.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_check_hover.png")));
                        } else {
                            button_settings1.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_cross_hover.png")));
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        if (verboseLogsTemp) {
                            button_settings1.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_check.png")));
                        } else {
                            button_settings1.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_cross.png")));
                        }
                    }
                });
                button_settings1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (verboseLogsTemp) {
                            verboseLogsTemp = false;
                            button_settings1.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_cross_hover.png")));
                        } else {
                            verboseLogsTemp = true;
                            button_settings1.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_check_hover.png")));
                        }
                    }
                });
                // ----------

                // ----- timestamp DB Updates -----
                JTextArea settings2 = new JTextArea();
                settings2.setBounds(10,65,300,27);
                settings2.setLineWrap(true);
                settings2.setFont(ArialBold14);
                settings2.setBackground(hex181818);
                settings2.setForeground(hexDDDDDD);
                settings2.setEditable(false);
                settings2.setText("Timestamp DB Attendance field");
                settings2.setBorder(BorderFactory.createCompoundBorder(
                        settings2.getBorder(),
                        BorderFactory.createEmptyBorder(6, 6, 6, 6)));

                JTextArea settings2Description = new JTextArea();
                settings2Description.setBounds(20,85,300,40);
                settings2Description.setLineWrap(true);
                settings2Description.setFont(ArialNormal12);
                settings2Description.setBackground(hex181818);
                settings2Description.setForeground(hex909090);
                settings2Description.setEditable(false);
                settings2Description.setText("Adds a 24hr format timestamp to the DB Attendance field while it is updated");
                settings2Description.setBorder(BorderFactory.createCompoundBorder(
                        settings2Description.getBorder(),
                        BorderFactory.createEmptyBorder(6, 6, 6, 6)));

                JButton button_settings2 = new JButton();
                button_settings2.setBounds(430,80,26,26);
                if (timestampDBUpdatesTemp) {
                    button_settings2.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_check.png")));
                } else {
                    button_settings2.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_cross.png")));
                }
                button_settings2.setBorderPainted(false);
                button_settings2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        if (timestampDBUpdatesTemp) {
                            button_settings2.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_check_hover.png")));
                        } else {
                            button_settings2.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_cross_hover.png")));
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        if (timestampDBUpdatesTemp) {
                            button_settings2.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_check.png")));
                        } else {
                            button_settings2.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_cross.png")));
                        }
                    }
                });
                button_settings2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (timestampDBUpdatesTemp) {
                            timestampDBUpdatesTemp = false;
                            button_settings2.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_cross_hover.png")));
                        } else {
                            timestampDBUpdatesTemp = true;
                            button_settings2.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_check_hover.png")));
                        }
                    }
                });
                // ----------


                JButton button_save_settings = new JButton();
                button_save_settings.setBounds(380,540,80,26);
                button_save_settings.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save.png")));
                button_save_settings.setBorderPainted(false);
                button_save_settings.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_save_settings.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save_hover.png")));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_save_settings.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save.png")));
                    }
                });
                button_save_settings.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveSettingsConf();
                        extractSettings();
                        confSettingsFrame.dispose();
                    }
                });

            confSettingsPanel.add(settings1);
            confSettingsPanel.add(settings1Description);
            confSettingsPanel.add(button_settings1);
            confSettingsPanel.add(settings2);
            confSettingsPanel.add(settings2Description);
            confSettingsPanel.add(button_settings2);
            confSettingsPanel.add(button_save_settings);

        confSettingsFrame.add(confSettingsPanel);
        confSettingsFrame.setVisible(true);

    }

    private static void saveSettingsConf() {
        try {
            Boolean errorHasOccurred = false;

            // ----- Settings Configuration Extraction -----
            File settingsConfFile = new File(Main.class.getResource("/conf/other_conf/settings.conf").toURI());

            if (settingsConfFile.exists()) {
                FileWriter settingsConfFileWrite = new FileWriter(settingsConfFile,false);
                String settingsText = "";

                if (verboseLogsTemp) {
                    settingsText += "1\n";
                } else if (!verboseLogsTemp){
                    settingsText += "0\n";
                } else {
                    errorHasOccurred = true;
                }

                if (timestampDBUpdatesTemp) {
                    settingsText += "1\n";
                } else if (!timestampDBUpdatesTemp){
                    settingsText += "0\n";
                } else {
                    errorHasOccurred = true;
                }

                settingsConfFileWrite.write(settingsText);
                settingsConfFileWrite.close();

            } else if (!settingsConfFile.exists()) {
                errorHasOccurred = true;
            }
            // ----------
            if (!errorHasOccurred) {
                Main.log("New Settings Saved",1);
            } else {
                Main.log("An Error Has Occurred While Saving Settings",3);
            }
        } catch (Exception e) {
            Main.log("Error!! Setting Configuration File Missing",3);
        }
    }
}
