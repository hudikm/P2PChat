import client.ConnectionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Martin on 01.03.2017.
 */
public class Client {

    private static int portNumber = 100;
    private static InetAddress hostName;

    public Client() throws UnknownHostException {

    }

    public static void main(String[] args) {
        Socket echoSocket = null;
        try {
            hostName = InetAddress.getLocalHost();
            echoSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            new Thread(new ConnectionHandler(in)).start();

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                //System.out.println("echo: " + in.readLine());
            }

            //Thread.sleep(5000);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
