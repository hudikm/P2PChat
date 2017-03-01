import server.ConnectionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Martin on 01.03.2017.
 */
public class Server {

    private static int portNumber = 100;

    public static void main(String[] argv) {
        ConnectionHandler connectionHandler = null;
        try {
            Logger serverLogger = Logger.getLogger("Server");
            ServerSocket serverSocket = new ServerSocket(portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serverLogger.log(Level.INFO, "Connection established");

                if (connectionHandler == null) {
                    connectionHandler = new ConnectionHandler(clientSocket);
                    new Thread(connectionHandler).start();
                    serverLogger.log(Level.INFO, "Connection handler started");
                } else {
                    connectionHandler.addNewSocket(clientSocket);

                }
            }


//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    clientSocket.getInputStream()));
//            String nextLine;
//
//            while ((nextLine = in.readLine()) != null) {
//                System.out.println(nextLine);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
