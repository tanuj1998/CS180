import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Write a description of class Client here.
 * 
 * @author Tanuj Yadav, tyadav@purdue.edu, Lab Section: G06
 * @version 11/11/16
 */
public class Client
{
    public static void main(String[] args) throws IOException {
        Scanner userInput = new Scanner(System.in);
        String serverHostname = "127.0.0.1";

        if (args.length > 0)
            serverHostname = args[0];
        System.out.println ("Attempting to connect to host " + serverHostname + " on port 3000.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, 1573);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fileName;
        String userName;

        System.out.println("Enter Filename: ");
        fileName = userInput.nextLine();
        out.println(fileName);

        System.out.println("Enter Username: ");
        userName = userInput.nextLine();
        out.println(userName);

        String fileStatus = in.readLine();
        System.out.println(fileStatus);

        String userStatus = in.readLine();
        System.out.println(userStatus);

        userInput.close();

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
