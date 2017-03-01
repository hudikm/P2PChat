package client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Martin on 01.03.2017.
 */
public class ConnectionHandler implements Runnable {
    private BufferedReader in;

    public ConnectionHandler(BufferedReader in) {
        this.in = in;
    }

    public void run() {


        try {
            String userInput;
            while ((userInput = in.readLine()) != null) {
                System.out.println(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
