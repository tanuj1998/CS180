import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Write a description of class Server here.
 * 
 * @author Tanuj Yadav, tyadav@purdue.edu, Lab Section: G06
 * @version 11/11/16
 */
public class Server
{
    ServerSocket serverSocket;
    Socket clientSocket;

    public static void main(String[] args) throws IOException, InfoNotFoundException, FileNotFoundException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1573);
            System.out.println ("Server waiting for connection");
        }
        catch (IOException e) {
            System.err.println("Could not listen on port: 27.0.0.1.");
            System.exit(1);
        }

        Socket clientSocket = null;

        try {
            clientSocket = serverSocket.accept();
        }
        catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        System.out.println ("Connection is successful and waiting for commands”");
        System.out.println ("Waiting for input.....");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String fileName = in.readLine();
        File f = new File("/homes/tyadav/Desktop/cs180/lab11/Server/" + fileName);
        if(!(f.exists())) {
            out.println("File does not exist");
            out.println("No username found since file does not exist");
        }
        String str = "";
        Scanner scan = new Scanner(f);
        while(scan.hasNextLine()) {
            str += scan.nextLine() + "--";
        }

        String[] arr = str.split("--");
        String[][] split = new String[arr.length][];

        for (int i = 0; i < arr.length; i++) {
            split[i] = arr[i].split(";");
        }


        String userName = in.readLine();

        int col = 1;
        int i;

        for (i = 1; i < split.length ; i++) {
            if((split[i][col].equals(userName))) {
                out.println("File found: " + fileName);
                out.println("Username found: " + userName);
                break;
            }
        }

        if(i >= split.length) {
            out.println("File found: " + fileName);
            out.println("Username does not exist");
            throw new InfoNotFoundException("Username not found");
        }

        File file = new File("info.txt");
        FileWriter fr = new FileWriter(file);
        BufferedWriter br = new BufferedWriter(fr);
        for(int a=0; a<split.length;a++){
            if(split[i].equals(userName)){
                for(int b =0; b<split[a].length;b++){
                    br.write(split[a][b]);
                }
            }
        }

            if (fileName.equals("Exit"))
                System.exit(1);


        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}




