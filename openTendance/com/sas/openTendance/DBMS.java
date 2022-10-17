package com.sas.openTendance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DBMS {

    private String DBIp = "";
    private String DBUserName = "";
    private String DBPassword = "";
    private String DBName = "";
    private String DBTable = "";
    private String DBUniqueIDField = "";
    private String DBAttendanceField = "";

    private Connection DBConnection = null;

    protected String updateValue;

    DBMS(Session session) {
        DBIp = session.getDBIp();
        DBUserName = session.getDBUserName();
        DBPassword = session.getDBPassword();
        DBName = session.getDBName();
        DBTable = session.getDBTable();
        DBUniqueIDField = session.getDBUniqueIDField();
        DBAttendanceField = session.getDBAttendanceField();

        refreshUpdateValue();

        connectToDB();
    }

    private void connectToDB() {
        try {
            String url = "jdbc:mysql://" + DBIp + "/" + DBName;

            // create a connection to the database
            DBConnection = DriverManager.getConnection(url, DBUserName, DBPassword);

        } catch (Exception e) {
            Main.log("Error!! Could Not Connect To MySQL Database At "+DBIp+" With Username '"+DBUserName+"' "+"And Password '"+DBPassword+"' ",3);
            Main.log("Check Whether The Database Is Up And Running",4);
        }
    }

    protected void refreshUpdateValue() {
        updateValue = Main.DBUpdateValue;
    }

    protected void updateAttendance(String primaryKeyValue) {
        try {
            refreshUpdateValue();

            String statement = "UPDATE `" + DBName + "`.`" + DBTable + "` SET `" + DBAttendanceField + "` = ? WHERE (`" + DBUniqueIDField + "` = ?)";

            PreparedStatement preparedSqlStatement = DBConnection.prepareStatement(statement);

            if (Main.timestampDBUpdates) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");
                String time = "  (" + LocalTime.now().format(format).toString() + ") ";

                preparedSqlStatement.setString(1, updateValue + time);
            } else {
                preparedSqlStatement.setString(1, updateValue);
            }
            preparedSqlStatement.setString(2, primaryKeyValue);

            preparedSqlStatement.executeUpdate();

            if (Main.verboseLogs) {
                Main.log("Attendance Field '" + DBAttendanceField + "' Of '" + primaryKeyValue + "' Set To '" + updateValue + "' In Database " + DBName + "'s " + DBTable + " Table", 1);
            }

        } catch (Exception e) {
            Main.log("An Error Has Occurred While Updating Database Attendance Field ('"+DBAttendanceField+"')",3);
        }
    }

    protected void resetFullAttendance() {
        try {
            Main.DBUpdateValue = "A";
            refreshUpdateValue();

            String statement = "UPDATE `" + DBName + "`.`" + DBTable + "` SET `" + DBAttendanceField + "` = ? WHERE (`" + DBUniqueIDField + "` != ?)";

            PreparedStatement preparedSqlStatement = DBConnection.prepareStatement(statement);

            preparedSqlStatement.setString(1, updateValue);
            preparedSqlStatement.setString(2, "!ResetDatabase!");

            preparedSqlStatement.executeUpdate();

            Main.log("Restored 'A' (Absent) In The Attendance Field '"+DBAttendanceField+"' Of Database "+DBName+"'s "+DBTable+" Table",1);

        } catch (Exception e) {
            Main.log("An Error Has Occurred While Updating Database Attendance Field ('"+DBAttendanceField+"')",3);
        }
    }



}
