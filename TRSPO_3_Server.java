package trspo;

import java.net.*;
import java.io.*;

public class TRSPO_3_Server extends Thread {
    private ServerSocket serverSocket;
    public TRSPO_3_Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(30000);
    }
    public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client on port " + 
                    serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept(); 
                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream()); 
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF(in.readUTF());
                server.close();   
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String [] args) {
        int port = 8778;
        try {
            Thread t = new TRSPO_3_Server(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
