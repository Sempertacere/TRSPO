package trspo;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TRSPO_3_Client {
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        String serverName = "127.0.0.1";
        int port = 8778;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF(sc.nextLine());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println(in.readUTF());
            client.close();
        } catch (IOException e) {
            System.out.println("Connection attempt has failed");
            e.printStackTrace();
        }
    }
}
