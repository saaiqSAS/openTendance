package com.sas.openTendance;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    
    private Boolean interrupted = false;

    private Client crrClient;
    private Session crrSession;

    private ServerSocket ss;
    private Socket s;
    private DataInputStream dis;

    private int port;
    private String name;
    private String primaryKeyValue = "";

    private DBMS dbms;

    Server(Client client,Session session) {
        crrClient = client;
        crrSession = session;
        port = crrClient.getPort();
        name = crrClient.getName();
    }

    @Override
    public void run() {
        if (Main.verboseLogs) {
            Main.log(name + " Listener Thread Started", 1);
        }

        dbms = new DBMS(crrSession);

        startServer();

        if (ss.isClosed()) {
            Main.log(name + " Listener At Port:" + port + " Ended", 1);
        }

        if (Main.verboseLogs) {
            Main.log(name + " Listener Thread Closed", 1);
        }
    }

    protected void interruptThread() {
        try {
            interrupted = true;
            if (Main.verboseLogs) {
                Main.log(name + " Listener Thread Interrupted", 1);
            }

            if (dis != null) {
                dis.close();
            }
            if (ss != null && !ss.isClosed()) {
                ss.close();
            }
            if (s != null && !s.isClosed()) {
                s.close();
            }
        }catch (Exception e) {
            Main.log("An Error Has Occurred While Interrupting "+name+" Listener Thread = " +e.toString(),3);
        }
    }

    public Boolean getInterrupted() {
        return interrupted;
    }

    protected String getServerName() {
        return name;
    }

    private void startServer() {
        if (!interrupted) {
            try {
                ss = new ServerSocket(port);
                Main.log(name + " Listener Started At Port: " + ss.getLocalPort(), 1);
                if (!interrupted) {
                    s = ss.accept();
                }
                if (s.isConnected()) {
                    Main.log(name + " Listener At Port:" + port + " Connected To Client " + s.getInetAddress() + ":" + s.getPort(), 4);
                }

                dis = new DataInputStream(s.getInputStream());
                String str = "";

                while (!interrupted) {
                    str = dis.readUTF();

                    if (!interrupted) {
                        if (Main.verboseLogs) {
                            Main.log("Received Data From Client At Port " + port + ": " + str, 4);
                            Main.log("Decrypted Data From Client At Port " + port + ": " + SasSte.decrypt(str), 4);
                        }

                        primaryKeyValue = SasSte.decrypt(str);
                        dbms.updateAttendance(primaryKeyValue);
                    }
                }

                ss.close();
                s.close();

            } catch (Exception e) {
                try {
                    ss.close();
                    s.close();
                    Thread.sleep(1000);
                } catch (Exception t) {
                }

                if (e.toString().equals("java.net.SocketException: Connection reset")) {
                    Main.log("Error!! Connection At Port: " + port + " Terminated", 3);
                    startServer();
                } else if (e.toString().equals("java.net.BindException: Address already in use: bind")) {
                    Main.log("Error!! Port: " + port + " Already In Use", 3);
                } else if (e.toString().equals("java.net.SocketException: Socket closed")){
                        // Do nothing
                } else {
                    Main.log("An Error Has Occurred In Client Listener " + name + " At Port: " + port + " = "+ e.toString(), 3);
                }
            }
        }
    }
}
