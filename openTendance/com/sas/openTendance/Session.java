package com.sas.openTendance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Session {
    protected int SessionNum;
    protected String SessionRunAmount;
    protected Boolean SessionEnabled = false;

    protected String SessionName = "";
    protected String StartTime = "";
    protected String LateTime = "";
    protected String EndTime = "";

    protected String DBIp = "";
    protected String DBUserName = "";
    protected String DBPassword = "";
    protected String DBName = "";
    protected String DBTable = "";
    protected String DBUniqueIDField = "";
    protected String DBAttendanceField = "";

    protected String XLSavePath = "";
    protected String NewXLField = "";

    // Colors
    private static Color hexDDDDDD = new Color(0xFFDDDDDD, true);
    private static Color hex252525 = new Color(0xFF252525, true);
    private static Color hex202020 = new Color(0xFF202020, true);
    private static Color hex181818 = new Color(0xFF181818, true);
    private static Color hex54EE00 = new Color(0xFF54EE00, true);

    //Fonts
    private static Font ArialNormal14 = new Font("Arial", Font.PLAIN,14);
    private static Font ArialBold16 = new Font("Arial", Font.BOLD,16);

    Session(int sessionNum) {
        SessionNum = sessionNum;
    }

    protected void setSessionName(String sessionName) {
        SessionName = sessionName;
    }
    protected String getSessionName() {
        return SessionName;
    }



    protected void setStartTime(String startTime) {
        StartTime = startTime;
    }
    protected String getStartTime() {
        return StartTime;
    }



    protected void setLateTime(String lateTime) {
        LateTime = lateTime;
    }
    protected String getLateTime() {
        return LateTime;
    }



    protected void setEndTime(String endTime) {
        EndTime = endTime;
    }
    protected String getEndTime() {
        return EndTime;
    }



    protected void setDBIp(String dbIp) {
        DBIp = dbIp;
    }
    protected String getDBIp() {
        return DBIp;
    }



    protected void setDBUserName(String dbUserName) {
        DBUserName = dbUserName;
    }
    protected String getDBUserName() {
        return DBUserName;
    }



    protected void setDBPassword(String dbPassword) {
        DBPassword = dbPassword;
    }
    protected String getDBPassword() {
        return DBPassword;
    }



    protected void setDBName(String dbName) {
        DBName = dbName;
    }
    protected String getDBName() {
        return DBName;
    }



    protected void setDBTable(String dbTable) {
        DBTable = dbTable;
    }
    protected String getDBTable() {
        return DBTable;
    }



    protected void setDBUniqueIDField(String dbUniqueIDField) {
        DBUniqueIDField = dbUniqueIDField;
    }
    protected String getDBUniqueIDField() {
        return DBUniqueIDField;
    }



    protected void setDBAttendanceField(String dbAttendanceField) {
        DBAttendanceField = dbAttendanceField;
    }
    protected String getDBAttendanceField() {
        return DBAttendanceField;
    }



    protected void setXLSavePath(String xlSavePath) {
        XLSavePath = xlSavePath;
    }
    protected String getXLSavePath() {
        return XLSavePath;
    }



    protected void setNewXLField(String newXLField) {
        NewXLField = newXLField;
    }
    protected String getNewXLField() {
        return NewXLField;
    }



    protected void enable(Boolean enableSession) {
        SessionEnabled = enableSession;
    }
    protected Boolean isEnabled() {
        return SessionEnabled;
    }



    protected void extractConfiguration() {
        try {
            Boolean errorHasOccurred = false;
            int fileLine = 1;
            String fileLineValue = "";
            Boolean fileHasNextLine;


            // ----- session config extraction -----
            File sessionConfFile = new File(Main.class.getResource("/conf/sess_conf/session"+SessionNum+".conf").toURI());

            if (sessionConfFile.exists()) {
                try {
                    Scanner sessionConfFileRead = new Scanner(sessionConfFile);
                    fileHasNextLine = sessionConfFileRead.hasNextLine();

                    while (fileHasNextLine) {
                        fileLineValue = sessionConfFileRead.nextLine();
                        if (fileLineValue.equals("")) {
                            fileLineValue = "Not Set";
                        } else {
                            fileLineValue = SasSte.decrypt(fileLineValue);
                        }
                        switch (fileLine) {
                            case 1:
                                setSessionName(fileLineValue);
                                break;
                            case 2:
                                setStartTime(fileLineValue);
                                break;
                            case 3:
                                setLateTime(fileLineValue);
                                break;
                            case 4:
                                setEndTime(fileLineValue);
                                break;
                            case 5:
                                setDBIp(fileLineValue);
                                break;
                            case 6:
                                setDBUserName(fileLineValue);
                                break;
                            case 7:
                                setDBPassword(fileLineValue);
                                break;
                            case 8:
                                setDBName(fileLineValue);
                                break;
                            case 9:
                                setDBTable(fileLineValue);
                                break;
                            case 10:
                                setDBUniqueIDField(fileLineValue);
                                break;
                            case 11:
                                setDBAttendanceField(fileLineValue);
                                break;
                            case 12:
                                setXLSavePath(fileLineValue);
                                break;
                            case 13:
                                setNewXLField(fileLineValue);
                                break;
                            default:
                                Main.log("Error!! Maximum Cases Exceeded",3);
                        }
                        fileHasNextLine = sessionConfFileRead.hasNextLine();
                        fileLine++;
                    }
                } catch (Exception e) {
                    Main.log("Error!! Could Not Successfully Read Session"+SessionNum+" Configuration File",3);
                    errorHasOccurred = true;
                }
            } else if (!sessionConfFile.exists()) {
                errorHasOccurred = true;
            }
            //----------

            if (!errorHasOccurred) {
                Main.log("Session"+SessionNum+" Configurations Extracted",1);
            } else {
                Main.log("An Error Has Occurred While Extracting Session"+SessionNum+" Configurations",3);
            }
        } catch (Exception e) {
            Main.log("Error!! Session"+SessionNum+" Configuration File Missing",3);
        }
    }



    protected void configureSession() {
        String title = "Session "+SessionNum+" Configuration";

        // ----- Main Session Config Frame -----
        JFrame confSessFrame = new JFrame(title);
        confSessFrame.setIconImage(new ImageIcon(Main.class.getResource("/res/logos/settingsIcon.png")).getImage());
        Dimension minFrameSize = new Dimension(500,620);
        confSessFrame.setMinimumSize(minFrameSize);
        confSessFrame.setResizable(true);
        confSessFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        // ----------

            // ----- Main Session Config Panel -----
            JPanel confSessPanel = new JPanel();
            confSessPanel.setLayout(new GridBagLayout());
            confSessPanel.setBackground(hex181818);
            // ----------

                // ----- Labels -----
                JLabel name = new JLabel("Name:");
                name.setForeground(hexDDDDDD);
                name.setFont(ArialBold16);

                JLabel start_time = new JLabel("Start Time:");
                start_time.setForeground(hexDDDDDD);
                start_time.setFont(ArialBold16);

                JLabel late_time = new JLabel("Late Time:");
                late_time.setForeground(hexDDDDDD);
                late_time.setFont(ArialBold16);

                JLabel end_time = new JLabel("End Time:");
                end_time.setForeground(hexDDDDDD);
                end_time.setFont(ArialBold16);

                JLabel db_ip = new JLabel("DB IP [ IP:Port ]:");
                db_ip.setForeground(hexDDDDDD);
                db_ip.setFont(ArialBold16);

                JLabel db_user_name = new JLabel("DB User Name:");
                db_user_name.setForeground(hexDDDDDD);
                db_user_name.setFont(ArialBold16);

                JLabel db_password = new JLabel("DB Password:");
                db_password.setForeground(hexDDDDDD);
                db_password.setFont(ArialBold16);

                JLabel db_name = new JLabel("DB Name:");
                db_name.setForeground(hexDDDDDD);
                db_name.setFont(ArialBold16);

                JLabel db_table = new JLabel("DB Table:");
                db_table.setForeground(hexDDDDDD);
                db_table.setFont(ArialBold16);

                JLabel db_unique_id = new JLabel("DB Unique ID Field:");
                db_unique_id.setForeground(hexDDDDDD);
                db_unique_id.setFont(ArialBold16);

                JLabel db_attendance = new JLabel("DB Attendance Field:");
                db_attendance.setForeground(hexDDDDDD);
                db_attendance.setFont(ArialBold16);

                JLabel save_xl_path = new JLabel("Save XL Path:");
                save_xl_path.setForeground(hexDDDDDD);
                save_xl_path.setFont(ArialBold16);

                JLabel new_xl_field = new JLabel("New XL Field:");
                new_xl_field.setForeground(hexDDDDDD);
                new_xl_field.setFont(ArialBold16);
                // ----------

                // ----- Label Inputs -----
                JTextField name_input = new JTextField(SessionName);
                name_input.setForeground(hexDDDDDD);
                name_input.setBackground(hex252525);
                name_input.setFont(ArialNormal14);
                name_input.setBorder(BorderFactory.createCompoundBorder(
                        name_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                name_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        name_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        name_input.setBackground(hex252525);
                    }
                });

                JTextField start_time_input = new JTextField(StartTime);
                start_time_input.setForeground(hexDDDDDD);
                start_time_input.setBackground(hex252525);
                start_time_input.setFont(ArialNormal14);
                start_time_input.setBorder(BorderFactory.createCompoundBorder(
                        start_time_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                start_time_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        start_time_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        start_time_input.setBackground(hex252525);
                    }
                });

                JTextField late_time_input = new JTextField(LateTime);
                late_time_input.setForeground(hexDDDDDD);
                late_time_input.setBackground(hex252525);
                late_time_input.setFont(ArialNormal14);
                late_time_input.setBorder(BorderFactory.createCompoundBorder(
                        late_time_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                late_time_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        late_time_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        late_time_input.setBackground(hex252525);
                    }
                });

                JTextField end_time_input = new JTextField(EndTime);
                end_time_input.setForeground(hexDDDDDD);
                end_time_input.setBackground(hex252525);
                end_time_input.setFont(ArialNormal14);
                end_time_input.setBorder(BorderFactory.createCompoundBorder(
                        end_time_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                end_time_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        end_time_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        end_time_input.setBackground(hex252525);
                    }
                });

                JTextField db_ip_input = new JTextField(DBIp);
                db_ip_input.setForeground(hexDDDDDD);
                db_ip_input.setBackground(hex252525);
                db_ip_input.setFont(ArialNormal14);
                db_ip_input.setBorder(BorderFactory.createCompoundBorder(
                        db_ip_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                db_ip_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        db_ip_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        db_ip_input.setBackground(hex252525);
                    }
                });

                JTextField db_user_name_input = new JTextField(DBUserName);
                db_user_name_input.setForeground(hexDDDDDD);
                db_user_name_input.setBackground(hex252525);
                db_user_name_input.setFont(ArialNormal14);
                db_user_name_input.setBorder(BorderFactory.createCompoundBorder(
                        db_user_name_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                db_user_name_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        db_user_name_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        db_user_name_input.setBackground(hex252525);
                    }
                });

                JTextField db_password_input = new JTextField(DBPassword);
                db_password_input.setForeground(hexDDDDDD);
                db_password_input.setBackground(hex252525);
                db_password_input.setFont(ArialNormal14);
                db_password_input.setBorder(BorderFactory.createCompoundBorder(
                        db_password_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                db_password_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        db_password_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        db_password_input.setBackground(hex252525);
                    }
                });

                JTextField db_name_input = new JTextField(DBName);
                db_name_input.setForeground(hexDDDDDD);
                db_name_input.setBackground(hex252525);
                db_name_input.setFont(ArialNormal14);
                db_name_input.setBorder(BorderFactory.createCompoundBorder(
                        db_name_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                db_name_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        db_name_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        db_name_input.setBackground(hex252525);
                    }
                });

                JTextField db_table_input = new JTextField(DBTable);
                db_table_input.setForeground(hexDDDDDD);
                db_table_input.setBackground(hex252525);
                db_table_input.setFont(ArialNormal14);
                db_table_input.setBorder(BorderFactory.createCompoundBorder(
                        db_table_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                db_table_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        db_table_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        db_table_input.setBackground(hex252525);
                    }
                });

                JTextField db_unique_id_input = new JTextField(DBUniqueIDField);
                db_unique_id_input.setForeground(hexDDDDDD);
                db_unique_id_input.setBackground(hex252525);
                db_unique_id_input.setFont(ArialNormal14);
                db_unique_id_input.setBorder(BorderFactory.createCompoundBorder(
                        db_unique_id_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                db_unique_id_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        db_unique_id_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        db_unique_id_input.setBackground(hex252525);
                    }
                });

                JTextField db_attendance_input = new JTextField(DBAttendanceField);
                db_attendance_input.setForeground(hexDDDDDD);
                db_attendance_input.setBackground(hex252525);
                db_attendance_input.setFont(ArialNormal14);
                db_attendance_input.setBorder(BorderFactory.createCompoundBorder(
                        db_attendance_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                db_attendance_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        db_attendance_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        db_attendance_input.setBackground(hex252525);
                    }
                });

                JTextField save_xl_path_input = new JTextField(XLSavePath);
                save_xl_path_input.setForeground(hexDDDDDD);
                save_xl_path_input.setBackground(hex252525);
                save_xl_path_input.setFont(ArialNormal14);
                save_xl_path_input.setBorder(BorderFactory.createCompoundBorder(
                        save_xl_path_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                save_xl_path_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        save_xl_path_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        save_xl_path_input.setBackground(hex252525);
                    }
                });

                JTextField new_xl_field_input = new JTextField(NewXLField);
                new_xl_field_input.setForeground(hexDDDDDD);
                new_xl_field_input.setBackground(hex252525);
                new_xl_field_input.setFont(ArialNormal14);
                new_xl_field_input.setBorder(BorderFactory.createCompoundBorder(
                        new_xl_field_input.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                new_xl_field_input.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        new_xl_field_input.setBackground(hex202020);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        new_xl_field_input.setBackground(hex252525);
                    }
                });
                // ----------

                JButton button_save_session_configuration = new JButton();
                button_save_session_configuration.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save.png")));
                button_save_session_configuration.setBorderPainted(false);
                button_save_session_configuration.setBackground(hex252525);
                button_save_session_configuration.setBorderPainted(false);
                button_save_session_configuration.setContentAreaFilled(false);
                button_save_session_configuration.setFocusable(false);
                button_save_session_configuration.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button_save_session_configuration.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save_hover.png")));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button_save_session_configuration.setIcon(new ImageIcon(Main.class.getResource("/res/buttons/button_save.png")));
                    }
                });
                button_save_session_configuration.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SessionName = name_input.getText();
                        StartTime = start_time_input.getText();
                        LateTime = late_time_input.getText();
                        EndTime = end_time_input.getText();

                        DBIp = db_ip_input.getText();
                        DBUserName = db_user_name_input.getText();
                        DBPassword = db_password_input.getText();
                        DBName = db_name_input.getText();
                        DBTable = db_table_input.getText();
                        DBUniqueIDField = db_unique_id_input.getText();
                        DBAttendanceField = db_attendance_input.getText();

                        XLSavePath = save_xl_path_input.getText();
                        NewXLField = new_xl_field_input.getText();

                        saveConfigurations();

                        confSessFrame.dispose();
                    }
                });

            GridBagConstraints grid = new GridBagConstraints();
            Insets insetsNone= new Insets(0,0,0,0);
            Insets insetsL5 = new Insets(0,5,0,0);
            Insets insetsR5 = new Insets(0,0,0,5);

            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 0;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(name, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 1;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(start_time, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 2;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(late_time, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 3;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(end_time, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 4;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_ip, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 5;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_user_name, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 6;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_password, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 7;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_name, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 8;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_table, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 9;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_unique_id, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 10;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_attendance, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 11;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(save_xl_path, grid);
            grid.gridx = 0;    grid.insets = insetsL5;
            grid.gridy = 12;
            grid.weightx = 0.2;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(new_xl_field, grid);

            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 0;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(name_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 1;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(start_time_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 2;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(late_time_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 3;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(end_time_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 4;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_ip_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 5;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_user_name_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 6;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_password_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 7;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_name_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 8;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_table_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 9;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_unique_id_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 10;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(db_attendance_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 11;    grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(save_xl_path_input, grid);
            grid.gridx = 1;    grid.fill = GridBagConstraints.HORIZONTAL;
            grid.gridy = 12;   grid.insets = insetsR5;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_START;
            confSessPanel.add(new_xl_field_input, grid);

            grid.gridx = 1;    grid.fill = GridBagConstraints.NONE;
            grid.gridy = 13;   grid.insets = insetsNone;
            grid.weightx = 1;  grid.weighty = 1;
            grid.anchor = GridBagConstraints.LINE_END;
            confSessPanel.add(button_save_session_configuration, grid);

        confSessFrame.setContentPane(confSessPanel);
        confSessFrame.setVisible(true);
    }



    private void saveConfigurations() {
        Boolean errorHasOccurred = false;
        try {
            File sessionConfFile = new File(Main.class.getResource("/conf/sess_conf/session" + SessionNum + ".conf").toURI());
            if (!sessionConfFile.exists()) {
                sessionConfFile.createNewFile();
            }
            FileWriter sessionConfFileWrite = new FileWriter(sessionConfFile,false);

            String tempWriteString = "";
            tempWriteString += SasSte.encrypt(SessionName)+"\n";
            tempWriteString += SasSte.encrypt(StartTime)+"\n";
            tempWriteString += SasSte.encrypt(LateTime)+"\n";
            tempWriteString += SasSte.encrypt(EndTime)+"\n";
            tempWriteString += SasSte.encrypt(DBIp)+"\n";
            tempWriteString += SasSte.encrypt(DBUserName)+"\n";
            tempWriteString += SasSte.encrypt(DBPassword)+"\n";
            tempWriteString += SasSte.encrypt(DBName)+"\n";
            tempWriteString += SasSte.encrypt(DBTable)+"\n";
            tempWriteString += SasSte.encrypt(DBUniqueIDField)+"\n";
            tempWriteString += SasSte.encrypt(DBAttendanceField)+"\n";
            tempWriteString += SasSte.encrypt(XLSavePath)+"\n";
            tempWriteString += SasSte.encrypt(NewXLField)+"\n";

            sessionConfFileWrite.write(tempWriteString);
            sessionConfFileWrite.close();

            extractConfiguration();
            Main.setSessionConfDisplay();
            Main.mainFrame.dispose();
            Main.setMainUI();

            if (!errorHasOccurred) {
                Main.log("New Session"+SessionNum+" Configurations Saved",1);
            }
        } catch (Exception e) {
            Main.log("An Error Has Occurred While Saving Session"+SessionNum+" Configurations",3);
            errorHasOccurred = true;
        }
    }







}
