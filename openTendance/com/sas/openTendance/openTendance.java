package com.sas.openTendance;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class openTendance extends Thread {
    
    private Boolean interrupted = false;

    // Session Objects
    private Session session1;
    private Session session2;
    private Session session3;
    private Session session4;

    private Session[] sessions = new Session[4];

    // Clients
    private Client[] clients;

    // Times
    private String StartTime = "";
    private String LateTime = "";
    private String EndTime = "";

    // Server Threads
    private Server[] serverThreads;

    @Override
    public void run() {
        startOT();
    }

    private void startOT() {
        Main.log("----- openTendance Server Started -----",1);
        // ----- Setting Up All Variables -----
        session1 = Main.session1;
        session2 = Main.session2;
        session3 = Main.session3;
        session4 = Main.session4;

        sessions[0] = session1;
        sessions[1] = session2;
        sessions[2] = session3;
        sessions[3] = session4;

        clients = Main.clients;
        //----------

        // ----- openTendance Server -----
        while (!interrupted) {
            int sessAmount = 0;
            for (Session eSession : sessions) {
                if (eSession.isEnabled() && !interrupted) {
                    Main.log("Session"+(sessAmount+1)+" "+eSession.getSessionName()+" Started",1);

                    // Reset DB Assigned To Session
                    DBMS dbms = new DBMS(eSession);
                    dbms.resetFullAttendance();

                    // Extract All Times
                    StartTime = eSession.getStartTime();
                    LateTime = eSession.getLateTime();
                    EndTime = eSession.getEndTime();

                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");
                    String time = LocalTime.now().format(format).toString();


                    // ----- Start Time -----
                    if (!interrupted) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                        // Waiting Till Start Time
                        Main.log("Waiting Till Start Time: " + StartTime, 1);
                        while (!time.equals(StartTime) && !interrupted) {
                            time = LocalTime.now().format(format).toString();
                        }
                        if (interrupted) {
                            Main.log("Stopped Waiting For Start Time", 1);
                        }
                        // Run At Start Time
                        if (!interrupted) {
                            Main.DBUpdateValue = "P";
                            Main.log("Database Attendance Update Value Set To 'P' (Present)", 1);

                            serverThreads = new Server[clients.length];
                            int crrClientsValue = 0;

                            // Create Listener For Each Client
                            for (Client eClient : clients) {
                                serverThreads[crrClientsValue] = new Server(eClient, eSession);
                                crrClientsValue++;
                            }

                            // Start Each Client Listener
                            for (Server eServer : serverThreads) {
                                eServer.start();
                                if (!eServer.isAlive()) {
                                    Main.log("Error!! Could Not Start " + eServer.getServerName() + " Listener Thread", 3);
                                }
                            }
                        }
                    }
                    //----------


                    // ----- Late Time -----
                    if (!interrupted) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                        // Waiting Till Late Time
                        Main.log("Waiting Till Late Time: " + LateTime, 1);
                        while (!time.equals(LateTime) && !interrupted) {
                            time = LocalTime.now().format(format).toString();
                        }
                        if (interrupted) {
                            Main.log("Stopped Waiting For Late Time", 1);
                        }
                        // Run At Late Time
                        if (!interrupted) {
                            Main.DBUpdateValue = "L";
                            Main.log("Database Attendance Update Value Set To 'L' (Late)", 1);
                        }
                    }
                    //----------


                    // ----- End Time -----
                    if (!interrupted) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                        // Waiting Till End Time
                        Main.log("Waiting Till End Time: " + EndTime, 1);
                        while (!time.equals(EndTime) && !interrupted) {
                            time = LocalTime.now().format(format).toString();
                        }
                        if (interrupted) {
                            Main.log("Stopped Waiting For End Time", 1);
                        }
                        // Run At End Time
                        if (!interrupted) {
                            // Stop All Client Listeners
                            for (Server eServer : serverThreads) {
                                eServer.interruptThread();
                                if (!eServer.getInterrupted()) {
                                    Main.log("Error!! Could Not Interrupt Thread " + eServer.getName(), 3);
                                }
                            }
                        }
                    }
                    //----------
                }
                sessAmount++;
            }
        }
        //---------
        try {
            Thread.sleep(1500);
        } catch (Exception e) {}
        Main.log("----- openTendance Server Stopped -----",1);
    }
    
    protected void stopOT() {
        interrupted = true;

        Main.log("----- Stopping openTendance Server -----", 2);

        // Stop All Client Listeners
        if (serverThreads != null) {
            for (Server eServer : serverThreads) {
                eServer.interruptThread();
                if (!eServer.getInterrupted()) {
                    Main.log("Error!! Could Not Interrupt Thread " + eServer.getName(), 3);
                }
            }
        }
    }

}
