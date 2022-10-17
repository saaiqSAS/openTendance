package com.sas.openTendance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static boolean isServerOn = false;

    // session objects
    protected static Session session1 = new Session(1);
    protected static Session session2 = new Session(2);
    protected static Session session3 = new Session(3);
    protected static Session session4 = new Session(4);

    // client object
    protected static Client[] clients;

    // oT main thread
    private static openTendance mainThread;

    //DB
    protected static String DBUpdateValue = "P";

    // Colors
    private static Color hexDDDDDD = new Color(0xFFDDDDDD, true);
    private static Color hex252525 = new Color(0xFF252525, true);
    private static Color hex181818 = new Color(0xFF181818, true);
    private static Color hex54EE00 = new Color(0xFF54EE00, true);

    //Fonts
    private static Font ArialNormal12 = new Font("Arial", Font.PLAIN,12);
    private static Font ArialBold14 = new Font("Arial", Font.BOLD,14);

    protected static JFrame mainFrame;

    //Settings
    protected static Boolean verboseLogs = true;
    protected static Boolean timestampDBUpdates = false;

    //session1 label values
    private static JLabel session1_name_value;
    private static JLabel session1_start_time_value;
    private static JLabel session1_late_time_value;
    private static JLabel session1_end_time_value;
    private static JLabel session1_db_table_value;
    private static JLabel session1_db_unique_id_value;
    private static JLabel session1_save_xl_path_value;
    private static JLabel session1_new_xl_field_value;

    //session2 label values
    private static JLabel session2_name_value;
    private static JLabel session2_start_time_value;
    private static JLabel session2_late_time_value;
    private static JLabel session2_end_time_value;
    private static JLabel session2_db_table_value;
    private static JLabel session2_db_unique_id_value;
    private static JLabel session2_save_xl_path_value;
    private static JLabel session2_new_xl_field_value;

    //session3 label values
    private static JLabel session3_name_value;
    private static JLabel session3_start_time_value;
    private static JLabel session3_late_time_value;
    private static JLabel session3_end_time_value;
    private static JLabel session3_db_table_value;
    private static JLabel session3_db_unique_id_value;
    private static JLabel session3_save_xl_path_value;
    private static JLabel session3_new_xl_field_value;

    //session4 label values
    private static JLabel session4_name_value;
    private static JLabel session4_start_time_value;
    private static JLabel session4_late_time_value;
    private static JLabel session4_end_time_value;
    private static JLabel session4_db_table_value;
    private static JLabel session4_db_unique_id_value;
    private static JLabel session4_save_xl_path_value;
    private static JLabel session4_new_xl_field_value;



    public static void main(String[] args) {
        System.out.println("");
        System.out.println("================================================");
        System.out.println("||           openTendance v0.0.2_BETA         ||");
        System.out.println("||                   By #SFYD                 ||");
        System.out.println("||          +------------------------+        ||");
        System.out.println("||          | Developed By #saaiqSAS |        ||");
        System.out.println("||          | Special Thanks To Yooh |        ||");
        System.out.println("||          +------------------------+        ||");
        System.out.println("================================================\n");
        System.out.println("[+] = Success | [*] = Processing\n[!] = Error   | [?] = Info/Help\n");

        Settings.extractSettings();

        log("openTendance Started",1);

        log("Extracting Configurations",2);
        SasSte.extractSteKeys();
        session1.extractConfiguration();
        session2.extractConfiguration();
        session3.extractConfiguration();
        session4.extractConfiguration();
        ClientConf.extractConfiguration();

        log("Setting Up The UI",2);
        setSessionConfDisplay();
        setMainUI();
        log("UI Setup Completed",1);

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

    protected static void setMainUI() {

        // ----- Main Frame -----
        mainFrame = new JFrame("openTendance v0.0.2_BETA");
        mainFrame.setIconImage(new ImageIcon(Main.class.getResource("/res/logos/openTendanceIcon.png")).getImage());
        mainFrame.setLayout(null);
        mainFrame.setResizable(false);
        mainFrame.setSize(1100,660);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            // ----- Main Panel -----
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(null);
            mainPanel.setSize(1100,660);
            mainPanel.setBackground(hex252525);
            // ----------


                // ----- openTendance Logo -----
                JLabel openTendanceLogo = new JLabel(new ImageIcon(Main.class.getResource("/res/logos/openTendanceLogo.png")));
                openTendanceLogo.setBounds(0,10,250,40);
                // ----------


                // ----- openTendance On/Off toggle button -----
                JButton button_oTOnOff = new JButton();
                if (!isServerOn) {
                    button_oTOnOff.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_Start_oT.png")));
                } else {
                    button_oTOnOff.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_Stop_oT.png")));
                }
                button_oTOnOff.setBounds(62,75,150,150);
                button_oTOnOff.setBorderPainted(false);
                button_oTOnOff.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        if (!isServerOn) {
                            button_oTOnOff.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_Start_oT_hover.png")));
                        } else {
                            button_oTOnOff.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_Stop_oT_hover.png")));
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        if (!isServerOn) {
                            button_oTOnOff.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_Start_oT.png")));
                        } else {
                            button_oTOnOff.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_Stop_oT.png")));
                        }
                    }
                });
                button_oTOnOff.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!isServerOn){
                            startOpenTendanceServer();
                            if (isServerOn) {
                                button_oTOnOff.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_Stop_oT_hover.png")));
                            }
                        } else if (isServerOn) {
                            stopOpenTendanceServer();
                            if (!isServerOn) {
                                button_oTOnOff.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_Start_oT_hover.png")));
                            }
                        }
                    }
                });
                // ----------


                // ----- clients configuration button -----
                JButton button_clients = new JButton();
                button_clients.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_clients.png")));
                button_clients.setBounds(262,75,150,150);
                button_clients.setBorderPainted(false);
                button_clients.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_clients.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_clients_hover.png")));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_clients.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_clients.png")));
                    }
                });
                button_clients.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!isServerOn) {
                            ClientConf.configureClients();
                        } else if (isServerOn) {
                            log("Please Stop The openTendance Server Before Configuring Clients",4);
                        }
                    }
                });
                // ----------


                // ----- SAS-STE encryption configuration button -----
                JButton button_sas_ste = new JButton();
                button_sas_ste.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_sas_ste.png")));
                button_sas_ste.setBounds(462,75,150,150);
                button_sas_ste.setBorderPainted(false);
                button_sas_ste.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_sas_ste.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_sas_ste_hover.png")));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_sas_ste.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_sas_ste.png")));
                    }
                });
                button_sas_ste.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!isServerOn) {
                            SasSte.configureSasSte();
                        } else if (isServerOn) {
                            log("Please Stop The openTendance Server Before Configuring SAS-STE",4);
                        }
                    }
                });
                // ----------


                // ----- settings button -----
                JButton button_settings = new JButton();
                button_settings.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_settings.png")));
                button_settings.setBounds(662,75,150,150);
                button_settings.setBorderPainted(false);
                button_settings.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_settings.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_settings_hover.png")));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_settings.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_settings.png")));
                    }
                });
                button_settings.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!isServerOn) {
                            Settings.configureSettings();
                        } else if (isServerOn) {
                            log("Please Stop The openTendance Server Before Changing Settings",4);
                        }
                    }
                });
                // ----------


                // ----- help button -----
                JButton button_help = new JButton();
                button_help.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_help.png")));
                button_help.setBounds(862,75,150,150);
                button_help.setBorderPainted(false);
                button_help.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_help.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_help_hover.png")));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_help.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_help.png")));
                    }
                });
                button_help.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // stuff to do
                    }
                });
                // ----------


                // ----- Session 1 Panel -----
                JPanel session1Panel = new JPanel();
                session1Panel.setBounds(25,280,250,320);
                session1Panel.setBackground(hex181818);
                session1Panel.setLayout(null);

                    JLabel session1_label = new JLabel(new ImageIcon(Main.class.getResource("/res/text/text_session1.png")));
                    session1_label.setBounds(0,0,115,40);

                    JButton button_session1_config = new JButton();
                    button_session1_config.setBounds(220,8,24,24);
                    button_session1_config.setBorderPainted(false);
                    button_session1_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config.png")));
                    button_session1_config.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            button_session1_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config_hover.png")));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            button_session1_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config.png")));
                        }
                    });
                    button_session1_config.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!isServerOn) {
                                session1.configureSession();
                            } else if (isServerOn) {
                                log("Please Stop The openTendance Server Before Configuring Sessions",4);
                            }
                        }
                    });

                    // ----- Labels -----
                    JLabel session1_name = new JLabel("Name:");
                    session1_name.setBounds(15,40,200,30);
                    session1_name.setForeground(hexDDDDDD);
                    session1_name.setFont(ArialBold14);

                    JLabel session1_start_time = new JLabel("Start Time:");
                    session1_start_time.setBounds(14,65,200,30);
                    session1_start_time.setForeground(hexDDDDDD);
                    session1_start_time.setFont(ArialBold14);

                    JLabel session1_late_time = new JLabel("Late Time:");
                    session1_late_time.setBounds(15,90,200,30);
                    session1_late_time.setForeground(hexDDDDDD);
                    session1_late_time.setFont(ArialBold14);

                    JLabel session1_end_time = new JLabel("End Time:");
                    session1_end_time.setBounds(15,115,200,30);
                    session1_end_time.setForeground(hexDDDDDD);
                    session1_end_time.setFont(ArialBold14);

                    JLabel session1_db_table = new JLabel("DB Table:");
                    session1_db_table.setBounds(15,140,200,30);
                    session1_db_table.setForeground(hexDDDDDD);
                    session1_db_table.setFont(ArialBold14);

                    JLabel session1_db_unique_id = new JLabel("DB Unique ID:");
                    session1_db_unique_id.setBounds(15,165,200,30);
                    session1_db_unique_id.setForeground(hexDDDDDD);
                    session1_db_unique_id.setFont(ArialBold14);

                    JLabel session1_save_xl_path = new JLabel("Save XL Path:");
                    session1_save_xl_path.setBounds(15,190,200,30);
                    session1_save_xl_path.setForeground(hexDDDDDD);
                    session1_save_xl_path.setFont(ArialBold14);

                    JLabel session1_new_xl_field = new JLabel("New XL Field:");
                    session1_new_xl_field.setBounds(15,215,200,30);
                    session1_new_xl_field.setForeground(hexDDDDDD);
                    session1_new_xl_field.setFont(ArialBold14);
                    // ----------


                    JButton button_session1_enableDissable = new JButton();
                    button_session1_enableDissable.setBounds(160,285,80,26);
                    if (!session1.isEnabled()) {
                        button_session1_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable.png")));
                    } else if (session1.isEnabled()) {
                        button_session1_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable.png")));
                    }
                    button_session1_enableDissable.setBorderPainted(false);
                    button_session1_enableDissable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            if (!session1.isEnabled()) {
                                button_session1_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable_hover.png")));
                            } else if (session1.isEnabled()) {
                                button_session1_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable_hover.png")));
                            }
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            if (!session1.isEnabled()) {
                                button_session1_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable.png")));
                            } else if (session1.isEnabled()) {
                                button_session1_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable.png")));
                            }
                        }
                    });
                    button_session1_enableDissable.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!isServerOn) {
                                if (!session1.isEnabled()) {
                                    session1.enable(true);
                                    button_session1_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable_hover.png")));
                                    log("Session1 Enabled",1);
                                } else if (session1.isEnabled()) {
                                    session1.enable(false);
                                    button_session1_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable_hover.png")));
                                    log("Session1 Disabled",1);
                                }
                            } else if (isServerOn) {
                                log("Please Stop The openTendance Server Before Enabling/Disabling Sessions",4);
                            }
                        }
                    });

                session1Panel.add(session1_label);
                session1Panel.add(button_session1_config);

                session1Panel.add(session1_name);
                session1Panel.add(session1_start_time);
                session1Panel.add(session1_late_time);
                session1Panel.add(session1_end_time);
                session1Panel.add(session1_db_table);
                session1Panel.add(session1_db_unique_id);
                session1Panel.add(session1_save_xl_path);
                session1Panel.add(session1_new_xl_field);

                session1Panel.add(session1_name_value);
                session1Panel.add(session1_start_time_value);
                session1Panel.add(session1_late_time_value);
                session1Panel.add(session1_end_time_value);
                session1Panel.add(session1_db_table_value);
                session1Panel.add(session1_db_unique_id_value);
                session1Panel.add(session1_save_xl_path_value);
                session1Panel.add(session1_new_xl_field_value);

                session1Panel.add(button_session1_enableDissable);
                // ----------


                // ----- Session 2 Panel -----
                JPanel session2Panel = new JPanel();
                session2Panel.setBounds(285,280,250,320);
                session2Panel.setBackground(hex181818);
                session2Panel.setLayout(null);

                    JLabel session2_label = new JLabel(new ImageIcon(Main.class.getResource("/res/text/text_session2.png")));
                    session2_label.setBounds(0,0,115,40);

                    JButton button_session2_config = new JButton();
                    button_session2_config.setBounds(220,8,24,24);
                    button_session2_config.setBorderPainted(false);
                    button_session2_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config.png")));
                    button_session2_config.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            button_session2_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config_hover.png")));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            button_session2_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config.png")));
                        }
                    });
                    button_session2_config.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!isServerOn) {
                                session2.configureSession();
                            } else if (isServerOn) {
                                log("Please Stop The openTendance Server Before Configuring Sessions",4);
                            }
                        }
                    });


                    // ----- Labels -----
                    JLabel session2_name = new JLabel("Name:");
                    session2_name.setBounds(15,40,200,30);
                    session2_name.setForeground(hexDDDDDD);
                    session2_name.setFont(ArialBold14);

                    JLabel session2_start_time = new JLabel("Start Time:");
                    session2_start_time.setBounds(14,65,200,30);
                    session2_start_time.setForeground(hexDDDDDD);
                    session2_start_time.setFont(ArialBold14);

                    JLabel session2_late_time = new JLabel("Late Time:");
                    session2_late_time.setBounds(15,90,200,30);
                    session2_late_time.setForeground(hexDDDDDD);
                    session2_late_time.setFont(ArialBold14);

                    JLabel session2_end_time = new JLabel("End Time:");
                    session2_end_time.setBounds(15,115,200,30);
                    session2_end_time.setForeground(hexDDDDDD);
                    session2_end_time.setFont(ArialBold14);

                    JLabel session2_db_table = new JLabel("DB Table:");
                    session2_db_table.setBounds(15,140,200,30);
                    session2_db_table.setForeground(hexDDDDDD);
                    session2_db_table.setFont(ArialBold14);

                    JLabel session2_db_unique_id = new JLabel("DB Unique ID:");
                    session2_db_unique_id.setBounds(15,165,200,30);
                    session2_db_unique_id.setForeground(hexDDDDDD);
                    session2_db_unique_id.setFont(ArialBold14);

                    JLabel session2_save_xl_path = new JLabel("Save XL Path:");
                    session2_save_xl_path.setBounds(15,190,200,30);
                    session2_save_xl_path.setForeground(hexDDDDDD);
                    session2_save_xl_path.setFont(ArialBold14);

                    JLabel session2_new_xl_field = new JLabel("New XL Field:");
                    session2_new_xl_field.setBounds(15,215,200,30);
                    session2_new_xl_field.setForeground(hexDDDDDD);
                    session2_new_xl_field.setFont(ArialBold14);
                    // ----------


                    JButton button_session2_enableDissable = new JButton();
                    button_session2_enableDissable.setBounds(160,285,80,26);
                    if (!session2.isEnabled()) {
                        button_session2_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable.png")));
                    } else if (session2.isEnabled()) {
                        button_session2_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable.png")));
                    }
                    button_session2_enableDissable.setBorderPainted(false);
                    button_session2_enableDissable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            if (!session2.isEnabled()) {
                                button_session2_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable_hover.png")));
                            } else if (session2.isEnabled()) {
                                button_session2_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable_hover.png")));
                            }
                        }
            
                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            if (!session2.isEnabled()) {
                                button_session2_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable.png")));
                            } else if (session2.isEnabled()) {
                                button_session2_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable.png")));
                            }
                        }
                    });
                    button_session2_enableDissable.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!isServerOn) {
                                if (!session2.isEnabled()) {
                                    session2.enable(true);
                                    button_session2_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable_hover.png")));
                                    log("Session2 Enabled",1);
                                } else if (session2.isEnabled()) {
                                    session2.enable(false);
                                    button_session2_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable_hover.png")));
                                    log("Session2 Disabled",1);
                                }
                            } else if (isServerOn) {
                                log("Please Stop The openTendance Server Before Enabling/Disabling Sessions",4);
                            }
                        }
                    });

                session2Panel.add(session2_label);
                session2Panel.add(button_session2_config);

                session2Panel.add(session2_name);
                session2Panel.add(session2_start_time);
                session2Panel.add(session2_late_time);
                session2Panel.add(session2_end_time);
                session2Panel.add(session2_db_table);
                session2Panel.add(session2_db_unique_id);
                session2Panel.add(session2_save_xl_path);
                session2Panel.add(session2_new_xl_field);

                session2Panel.add(session2_name_value);
                session2Panel.add(session2_start_time_value);
                session2Panel.add(session2_late_time_value);
                session2Panel.add(session2_end_time_value);
                session2Panel.add(session2_db_table_value);
                session2Panel.add(session2_db_unique_id_value);
                session2Panel.add(session2_save_xl_path_value);
                session2Panel.add(session2_new_xl_field_value);

                session2Panel.add(button_session2_enableDissable);
                // ----------


                // ----- Session 3 Panel -----
                JPanel session3Panel = new JPanel();
                session3Panel.setBounds(545,280,250,320);
                session3Panel.setBackground(hex181818);
                session3Panel.setLayout(null);

                    JLabel session3_label = new JLabel(new ImageIcon(Main.class.getResource("/res/text/text_session3.png")));
                    session3_label.setBounds(0,0,115,40);

                    JButton button_session3_config = new JButton();
                    button_session3_config.setBounds(220,8,24,24);
                    button_session3_config.setBorderPainted(false);
                    button_session3_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config.png")));
                    button_session3_config.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            button_session3_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config_hover.png")));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            button_session3_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config.png")));
                        }
                    });
                    button_session3_config.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!isServerOn) {
                                session3.configureSession();
                            } else if (isServerOn) {
                                log("Please Stop The openTendance Server Before Configuring Sessions",4);
                            }
                        }
                    });


                    // ----- Labels -----
                    JLabel session3_name = new JLabel("Name:");
                    session3_name.setBounds(15,40,200,30);
                    session3_name.setForeground(hexDDDDDD);
                    session3_name.setFont(ArialBold14);

                    JLabel session3_start_time = new JLabel("Start Time:");
                    session3_start_time.setBounds(14,65,200,30);
                    session3_start_time.setForeground(hexDDDDDD);
                    session3_start_time.setFont(ArialBold14);

                    JLabel session3_late_time = new JLabel("Late Time:");
                    session3_late_time.setBounds(15,90,200,30);
                    session3_late_time.setForeground(hexDDDDDD);
                    session3_late_time.setFont(ArialBold14);

                    JLabel session3_end_time = new JLabel("End Time:");
                    session3_end_time.setBounds(15,115,200,30);
                    session3_end_time.setForeground(hexDDDDDD);
                    session3_end_time.setFont(ArialBold14);

                    JLabel session3_db_table = new JLabel("DB Table:");
                    session3_db_table.setBounds(15,140,200,30);
                    session3_db_table.setForeground(hexDDDDDD);
                    session3_db_table.setFont(ArialBold14);

                    JLabel session3_db_unique_id = new JLabel("DB Unique ID:");
                    session3_db_unique_id.setBounds(15,165,200,30);
                    session3_db_unique_id.setForeground(hexDDDDDD);
                    session3_db_unique_id.setFont(ArialBold14);

                    JLabel session3_save_xl_path = new JLabel("Save XL Path:");
                    session3_save_xl_path.setBounds(15,190,200,30);
                    session3_save_xl_path.setForeground(hexDDDDDD);
                    session3_save_xl_path.setFont(ArialBold14);

                    JLabel session3_new_xl_field = new JLabel("New XL Field:");
                    session3_new_xl_field.setBounds(15,215,200,30);
                    session3_new_xl_field.setForeground(hexDDDDDD);
                    session3_new_xl_field.setFont(ArialBold14);
                    // ----------


                    JButton button_session3_enableDissable = new JButton();
                    button_session3_enableDissable.setBounds(160,285,80,26);
                    if (!session3.isEnabled()) {
                        button_session3_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable.png")));
                    } else if (session3.isEnabled()) {
                        button_session3_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable.png")));
                    }
                    button_session3_enableDissable.setBorderPainted(false);
                    button_session3_enableDissable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            if (!session3.isEnabled()) {
                                button_session3_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable_hover.png")));
                            } else if (session3.isEnabled()) {
                                button_session3_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable_hover.png")));
                            }
                        }
            
                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            if (!session3.isEnabled()) {
                                button_session3_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable.png")));
                            } else if (session3.isEnabled()) {
                                button_session3_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable.png")));
                            }
                        }
                    });
                    button_session3_enableDissable.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!isServerOn) {
                                if (!session3.isEnabled()) {
                                    session3.enable(true);
                                    button_session3_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable_hover.png")));
                                    log("Session3 Enabled",1);
                                } else if (session3.isEnabled()) {
                                    session3.enable(false);
                                    button_session3_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable_hover.png")));
                                    log("Session3 Disabled",1);
                                }
                            } else if (isServerOn) {
                                log("Please Stop The openTendance Server Before Enabling/Disabling Sessions",4);
                            }
                        }
                    });

                session3Panel.add(session3_label);
                session3Panel.add(button_session3_config);

                session3Panel.add(session3_name);
                session3Panel.add(session3_start_time);
                session3Panel.add(session3_late_time);
                session3Panel.add(session3_end_time);
                session3Panel.add(session3_db_table);
                session3Panel.add(session3_db_unique_id);
                session3Panel.add(session3_save_xl_path);
                session3Panel.add(session3_new_xl_field);

                session3Panel.add(session3_name_value);
                session3Panel.add(session3_start_time_value);
                session3Panel.add(session3_late_time_value);
                session3Panel.add(session3_end_time_value);
                session3Panel.add(session3_db_table_value);
                session3Panel.add(session3_db_unique_id_value);
                session3Panel.add(session3_save_xl_path_value);
                session3Panel.add(session3_new_xl_field_value);

                session3Panel.add(button_session3_enableDissable);
                // ----------


                // ----- Session 4 Panel -----
                JPanel session4Panel = new JPanel();
                session4Panel.setBounds(805,280,250,320);
                session4Panel.setBackground(hex181818);
                session4Panel.setLayout(null);

                    JLabel session4_label = new JLabel(new ImageIcon(Main.class.getResource("/res/text/text_session4.png")));
                    session4_label.setBounds(0,0,115,40);

                    JButton button_session4_config = new JButton();
                    button_session4_config.setBounds(220,8,24,24);
                    button_session4_config.setBorderPainted(false);
                    button_session4_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config.png")));
                    button_session4_config.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            button_session4_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config_hover.png")));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            button_session4_config.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_config.png")));
                        }
                    });
                    button_session4_config.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!isServerOn) {
                                session4.configureSession();
                            } else if (isServerOn) {
                                log("Please Stop The openTendance Server Before Configuring Sessions",4);
                            }
                        }
                    });


                    // ----- Labels -----
                    JLabel session4_name = new JLabel("Name:");
                    session4_name.setBounds(15,40,200,30);
                    session4_name.setForeground(hexDDDDDD);
                    session4_name.setFont(ArialBold14);

                    JLabel session4_start_time = new JLabel("Start Time:");
                    session4_start_time.setBounds(14,65,200,30);
                    session4_start_time.setForeground(hexDDDDDD);
                    session4_start_time.setFont(ArialBold14);

                    JLabel session4_late_time = new JLabel("Late Time:");
                    session4_late_time.setBounds(15,90,200,30);
                    session4_late_time.setForeground(hexDDDDDD);
                    session4_late_time.setFont(ArialBold14);

                    JLabel session4_end_time = new JLabel("End Time:");
                    session4_end_time.setBounds(15,115,200,30);
                    session4_end_time.setForeground(hexDDDDDD);
                    session4_end_time.setFont(ArialBold14);

                    JLabel session4_db_table = new JLabel("DB Table:");
                    session4_db_table.setBounds(15,140,200,30);
                    session4_db_table.setForeground(hexDDDDDD);
                    session4_db_table.setFont(ArialBold14);

                    JLabel session4_db_unique_id = new JLabel("DB Unique ID:");
                    session4_db_unique_id.setBounds(15,165,200,30);
                    session4_db_unique_id.setForeground(hexDDDDDD);
                    session4_db_unique_id.setFont(ArialBold14);

                    JLabel session4_save_xl_path = new JLabel("Save XL Path:");
                    session4_save_xl_path.setBounds(15,190,200,30);
                    session4_save_xl_path.setForeground(hexDDDDDD);
                    session4_save_xl_path.setFont(ArialBold14);

                    JLabel session4_new_xl_field = new JLabel("New XL Field:");
                    session4_new_xl_field.setBounds(15,215,200,30);
                    session4_new_xl_field.setForeground(hexDDDDDD);
                    session4_new_xl_field.setFont(ArialBold14);
                    // ----------


                    JButton button_session4_enableDissable = new JButton();
                    button_session4_enableDissable.setBounds(160,285,80,26);
                    if (!session4.isEnabled()) {
                        button_session4_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable.png")));
                    } else if (session4.isEnabled()) {
                        button_session4_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable.png")));
                    }
                    button_session4_enableDissable.setBorderPainted(false);
                    button_session4_enableDissable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            if (!session4.isEnabled()) {
                                button_session4_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable_hover.png")));
                            } else if (session4.isEnabled()) {
                                button_session4_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable_hover.png")));
                            }
                        }
            
                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            if (!session4.isEnabled()) {
                                button_session4_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable.png")));
                            } else if (session4.isEnabled()) {
                                button_session4_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable.png")));
                            }
                        }
                    });
                    button_session4_enableDissable.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!isServerOn) {
                                if (!session4.isEnabled()) {
                                    session4.enable(true);
                                    button_session4_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_dissable_hover.png")));
                                    log("Session4 Enabled",1);
                                } else if (session4.isEnabled()) {
                                    session4.enable(false);
                                    button_session4_enableDissable.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_session_enable_hover.png")));
                                    log("Session4 Disabled",1);
                                }
                            } else if (isServerOn) {
                                log("Please Stop The openTendance Server Before Enabling/Disabling Sessions",4);
                            }
                        }
                    });

                session4Panel.add(session4_label);
                session4Panel.add(button_session4_config);

                session4Panel.add(session4_name);
                session4Panel.add(session4_start_time);
                session4Panel.add(session4_late_time);
                session4Panel.add(session4_end_time);
                session4Panel.add(session4_db_table);
                session4Panel.add(session4_db_unique_id);
                session4Panel.add(session4_save_xl_path);
                session4Panel.add(session4_new_xl_field);

                session4Panel.add(session4_name_value);
                session4Panel.add(session4_start_time_value);
                session4Panel.add(session4_late_time_value);
                session4Panel.add(session4_end_time_value);
                session4Panel.add(session4_db_table_value);
                session4Panel.add(session4_db_unique_id_value);
                session4Panel.add(session4_save_xl_path_value);
                session4Panel.add(session4_new_xl_field_value);

                session4Panel.add(button_session4_enableDissable);
                // ----------


            // ----- Main Panel Addings -----
            mainPanel.add(openTendanceLogo);

            mainPanel.add(button_oTOnOff);
            mainPanel.add(button_clients);
            mainPanel.add(button_sas_ste);
            mainPanel.add(button_settings);
            mainPanel.add(button_help);

            mainPanel.add(session1Panel);
            mainPanel.add(session2Panel);
            mainPanel.add(session3Panel);
            mainPanel.add(session4Panel);
            // ----------

        // ----- Main Frame Addings -----
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        // ----------
    }


    protected static void setSessionConfDisplay() {

        // ----- session1 Label Values -----
        session1_name_value = new JLabel(session1.getSessionName());
        session1_name_value.setBounds(65,40,165,30);
        session1_name_value.setForeground(hexDDDDDD);
        session1_name_value.setFont(ArialNormal12);

        session1_start_time_value = new JLabel(session1.getStartTime());
        session1_start_time_value.setBounds(95,65,140,30);
        session1_start_time_value.setForeground(hexDDDDDD);
        session1_start_time_value.setFont(ArialNormal12);

        session1_late_time_value = new JLabel(session1.getLateTime());
        session1_late_time_value.setBounds(95,90,140,30);
        session1_late_time_value.setForeground(hexDDDDDD);
        session1_late_time_value.setFont(ArialNormal12);

        session1_end_time_value = new JLabel(session1.getEndTime());
        session1_end_time_value.setBounds(95,115,140,30);
        session1_end_time_value.setForeground(hexDDDDDD);
        session1_end_time_value.setFont(ArialNormal12);

        session1_db_table_value = new JLabel(session1.getDBTable());
        session1_db_table_value.setBounds(95,140,140,30);
        session1_db_table_value.setForeground(hexDDDDDD);
        session1_db_table_value.setFont(ArialNormal12);

        session1_db_unique_id_value = new JLabel(session1.getDBUniqueIDField());
        session1_db_unique_id_value.setBounds(115,165,120,30);
        session1_db_unique_id_value.setForeground(hexDDDDDD);
        session1_db_unique_id_value.setFont(ArialNormal12);

        session1_save_xl_path_value = new JLabel(session1.getXLSavePath());
        session1_save_xl_path_value.setBounds(115,190,120,30);
        session1_save_xl_path_value.setForeground(hexDDDDDD);
        session1_save_xl_path_value.setFont(ArialNormal12);

        session1_new_xl_field_value = new JLabel(session1.getNewXLField());
        session1_new_xl_field_value.setBounds(115,215,120,30);
        session1_new_xl_field_value.setForeground(hexDDDDDD);
        session1_new_xl_field_value.setFont(ArialNormal12);
        // ----------

        // ----- session2 Label Values -----
        session2_name_value = new JLabel(session2.getSessionName());
        session2_name_value.setBounds(65,40,165,30);
        session2_name_value.setForeground(hexDDDDDD);
        session2_name_value.setFont(ArialNormal12);

        session2_start_time_value = new JLabel(session2.getStartTime());
        session2_start_time_value.setBounds(95,65,140,30);
        session2_start_time_value.setForeground(hexDDDDDD);
        session2_start_time_value.setFont(ArialNormal12);

        session2_late_time_value = new JLabel(session2.getLateTime());
        session2_late_time_value.setBounds(95,90,140,30);
        session2_late_time_value.setForeground(hexDDDDDD);
        session2_late_time_value.setFont(ArialNormal12);

        session2_end_time_value = new JLabel(session2.getEndTime());
        session2_end_time_value.setBounds(95,115,140,30);
        session2_end_time_value.setForeground(hexDDDDDD);
        session2_end_time_value.setFont(ArialNormal12);

        session2_db_table_value = new JLabel(session2.getDBTable());
        session2_db_table_value.setBounds(95,140,140,30);
        session2_db_table_value.setForeground(hexDDDDDD);
        session2_db_table_value.setFont(ArialNormal12);

        session2_db_unique_id_value = new JLabel(session2.getDBUniqueIDField());
        session2_db_unique_id_value.setBounds(115,165,120,30);
        session2_db_unique_id_value.setForeground(hexDDDDDD);
        session2_db_unique_id_value.setFont(ArialNormal12);

        session2_save_xl_path_value = new JLabel(session2.getXLSavePath());
        session2_save_xl_path_value.setBounds(115,190,120,30);
        session2_save_xl_path_value.setForeground(hexDDDDDD);
        session2_save_xl_path_value.setFont(ArialNormal12);

        session2_new_xl_field_value = new JLabel(session2.getNewXLField());
        session2_new_xl_field_value.setBounds(115,215,120,30);
        session2_new_xl_field_value.setForeground(hexDDDDDD);
        session2_new_xl_field_value.setFont(ArialNormal12);
        // ----------

        // ----- session3 Label Values -----
        session3_name_value = new JLabel(session3.getSessionName());
        session3_name_value.setBounds(65,40,165,30);
        session3_name_value.setForeground(hexDDDDDD);
        session3_name_value.setFont(ArialNormal12);

        session3_start_time_value = new JLabel(session3.getStartTime());
        session3_start_time_value.setBounds(95,65,140,30);
        session3_start_time_value.setForeground(hexDDDDDD);
        session3_start_time_value.setFont(ArialNormal12);

        session3_late_time_value = new JLabel(session3.getLateTime());
        session3_late_time_value.setBounds(95,90,140,30);
        session3_late_time_value.setForeground(hexDDDDDD);
        session3_late_time_value.setFont(ArialNormal12);

        session3_end_time_value = new JLabel(session3.getEndTime());
        session3_end_time_value.setBounds(95,115,140,30);
        session3_end_time_value.setForeground(hexDDDDDD);
        session3_end_time_value.setFont(ArialNormal12);

        session3_db_table_value = new JLabel(session3.getDBTable());
        session3_db_table_value.setBounds(95,140,140,30);
        session3_db_table_value.setForeground(hexDDDDDD);
        session3_db_table_value.setFont(ArialNormal12);

        session3_db_unique_id_value = new JLabel(session3.getDBUniqueIDField());
        session3_db_unique_id_value.setBounds(115,165,120,30);
        session3_db_unique_id_value.setForeground(hexDDDDDD);
        session3_db_unique_id_value.setFont(ArialNormal12);

        session3_save_xl_path_value = new JLabel(session3.getXLSavePath());
        session3_save_xl_path_value.setBounds(115,190,120,30);
        session3_save_xl_path_value.setForeground(hexDDDDDD);
        session3_save_xl_path_value.setFont(ArialNormal12);

        session3_new_xl_field_value = new JLabel(session3.getNewXLField());
        session3_new_xl_field_value.setBounds(115,215,120,30);
        session3_new_xl_field_value.setForeground(hexDDDDDD);
        session3_new_xl_field_value.setFont(ArialNormal12);
        // ----------

        // ----- session4 Label Values -----
        session4_name_value = new JLabel(session4.getSessionName());
        session4_name_value.setBounds(65,40,165,30);
        session4_name_value.setForeground(hexDDDDDD);
        session4_name_value.setFont(ArialNormal12);

        session4_start_time_value = new JLabel(session4.getStartTime());
        session4_start_time_value.setBounds(95,65,140,30);
        session4_start_time_value.setForeground(hexDDDDDD);
        session4_start_time_value.setFont(ArialNormal12);

        session4_late_time_value = new JLabel(session4.getLateTime());
        session4_late_time_value.setBounds(95,90,140,30);
        session4_late_time_value.setForeground(hexDDDDDD);
        session4_late_time_value.setFont(ArialNormal12);

        session4_end_time_value = new JLabel(session4.getEndTime());
        session4_end_time_value.setBounds(95,115,140,30);
        session4_end_time_value.setForeground(hexDDDDDD);
        session4_end_time_value.setFont(ArialNormal12);

        session4_db_table_value = new JLabel(session4.getDBTable());
        session4_db_table_value.setBounds(95,140,140,30);
        session4_db_table_value.setForeground(hexDDDDDD);
        session4_db_table_value.setFont(ArialNormal12);

        session4_db_unique_id_value = new JLabel(session4.getDBUniqueIDField());
        session4_db_unique_id_value.setBounds(115,165,120,30);
        session4_db_unique_id_value.setForeground(hexDDDDDD);
        session4_db_unique_id_value.setFont(ArialNormal12);

        session4_save_xl_path_value = new JLabel(session4.getXLSavePath());
        session4_save_xl_path_value.setBounds(115,190,120,30);
        session4_save_xl_path_value.setForeground(hexDDDDDD);
        session4_save_xl_path_value.setFont(ArialNormal12);

        session4_new_xl_field_value = new JLabel(session4.getNewXLField());
        session4_new_xl_field_value.setBounds(115,215,120,30);
        session4_new_xl_field_value.setForeground(hexDDDDDD);
        session4_new_xl_field_value.setFont(ArialNormal12);
        // ----------

        log("Configurations Displayed",1);

    }

    private static void startOpenTendanceServer() {
        Boolean noSessionEnabled = true;

        Session[] sessions = new Session[4];

        sessions[0] = session1;
        sessions[1] = session2;
        sessions[2] = session3;
        sessions[3] = session4;

        for (Session eSession : sessions) {
            if (eSession.isEnabled()) {
                noSessionEnabled = false;
            }
        }

        if (!noSessionEnabled) {
            mainThread = new openTendance();
            mainThread.setName("openTendanceDaemon");
            mainThread.start();
            isServerOn = true;
        } else {
            Main.log("Please Enable At least One Session Before Starting The openTendance Server",4);
        }
    }

    private static void stopOpenTendanceServer() {
        mainThread.stopOT();
        isServerOn = false;
    }


}
