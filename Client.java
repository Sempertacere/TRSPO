package trspo;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        String serverName = "127.0.0.1", serverMsg;
        int port = 8778;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println(in.readUTF());
            out.writeUTF(sc.nextLine());
            while (true) {
                serverMsg = in.readUTF();
                System.out.println(serverMsg);
                if(serverMsg.contains("over")) break;
                if(serverMsg.charAt(serverMsg.length() - 1) == ' ') {
                    out.writeUTF(sc.nextLine());
                }
            }
            // System.out.println(in.readUTF());
            client.close();
        } catch (IOException e) {
            System.out.println("Connection attempt has failed");
            e.printStackTrace();
        }
    }
}
