package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Martin on 01.03.2017.
 */
public class ConnectionHandler implements Runnable {
    Logger serverLogger = Logger.getLogger("Handler");

    private class ConnectionData {
        Socket socket;
        BufferedReader inStream;
        PrintWriter outStream;

        public ConnectionData(Socket socket, BufferedReader bufferedReader, PrintWriter outStream) {
            this.socket = socket;
            this.inStream = bufferedReader;
            this.outStream = outStream;
        }
    }

    private List<ConnectionData> connectionDataList = new ArrayList();


    public ConnectionHandler(Socket socket) {

        addNewSocket(socket);

    }

    public void addNewSocket(Socket socket) {
        synchronized (connectionDataList) {
            try {
                connectionDataList.add(
                        new ConnectionData(
                                socket,
                                new BufferedReader(new InputStreamReader(socket.getInputStream())),
                                new PrintWriter(socket.getOutputStream(), true)));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        while (true) {
            synchronized (connectionDataList) {
                for (ConnectionData in : connectionDataList) {
                    String nextLine;
                    try {
                        if (in.inStream.ready()) {
                            nextLine = in.inStream.readLine();
                            System.out.println(nextLine);
                            //Broadcast message to others
                            for (ConnectionData connectionData : connectionDataList) {
                                if (!connectionData.equals(in)) {
                                    serverLogger.log(Level.INFO, String.valueOf(connectionData.socket.getPort()));
                                    connectionData.outStream.println(String.valueOf(connectionData.socket.getPort()) + ": " + nextLine);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
