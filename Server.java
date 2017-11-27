package trspo;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server extends Thread {
    private ServerSocket serverSocket;
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(300000);
    }
    public static int[][] strToField(String line) {
        int[][] field = new int[10][10];
        String[] cells = line.split(" ");
        //System.out.println(cells.length);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = Integer.parseInt(cells[i*10 + j]);
            }
        }
        return field;
    }
    public static boolean fieldValidator(int[][] field) {
        int coords[][] = new int[20][2], ships[] = new int[4], i, j, length = 0, count = 0, nowVertical, y, x;
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                if(field[i][j] == 1 && (i == 0 || field[i - 1][j] == 0) && (i == 9 || field[i + 1][j] == 0)) {
                    if(length == 4 || count == 20) return false;
                    coords[count][0] = i;
                    coords[count][1] = j;
                    length++;
                    count++;
                } else if(length > 0) {
                    ships[4 - length]++;
                    length = 0;
                }
            }
        }
        if(length > 0) {
            ships[4 - length]++;
            length = 0;
        }   
        nowVertical = count;
        for (j = 0; j < 10; j++) {
            for (i = 0; i < 10; i++) {
                if(field[i][j] == 1 && (i < 9 && field[i + 1][j] == 1 || length > 0) && (j == 0 || field[i][j - 1] == 0) && (j == 9 || field[i][j + 1] == 0)) {
                    if(length == 4 || count == 20) return false;
                    coords[count][0] = i;
                    coords[count][1] = j;
                    length++;
                    count++;
                } else if(length > 0) {
                    ships[4 - length]++;
                    length = 0;
                }
            }
        }
        for (i = 0; i < 4; i++) {
            if(ships[i] != i + 1) return false;
        }
        for (i = 0; i < nowVertical; i++) {
            y = coords[i][0];
            x = coords[i][1];
            if(y > 0) { 
                if(field[y - 1][x] == 1) return false;
                if(x > 0 && field[y - 1][x - 1] == 1) return false;
                if(x < 9 && field[y - 1][x + 1] == 1) return false;
            }
            if(y < 9) {
                if(field[y + 1][x] == 1) return false;
                if(x > 0 && field[y + 1][x - 1] == 1) return false;
                if(x < 9 && field[y + 1][x + 1] == 1) return false;
            }
        }
        for (i = nowVertical; i < 20; i++) {
            y = coords[i][0];
            x = coords[i][1];
            if(x > 0) { 
                if(field[y][x - 1] == 1) return false;
                if(y > 0 && field[y - 1][x - 1] == 1) return false;
                if(y < 9 && field[y + 1][x - 1] == 1) return false;
            }
            if(x < 9) {
                if(field[y][x + 1] == 1) return false;
                if(y > 0 && field[y - 1][x + 1] == 1) return false;
                if(y < 9 && field[y + 1][x + 1] == 1) return false;
            }
        }
        return true;
    }
    
    public static boolean lookAround(int x, int y, int[][] field) {
        if(y > 0 && field[y - 1][x] == 1) return true;
        if(x < 9 && field[y][x + 1] == 1) return true;
        if(y < 9 && field[y + 1][x] == 1) return true;
        if(x > 0 && field[y][x - 1] == 1) return true;
        return false;
    }
    
    public static int[][] markAsPointless(int x, int y, int[][] field) {
        if(y > 0) {
            field[y - 1][x] = 2;
            if(x > 0) {
                field[y - 1][x - 1] = 2;
                field[y][x - 1] = 2;
            }
            if(x < 9) {
                field[y - 1][x + 1] = 2;
                field[y][x + 1] = 2;
            }
        }
        if(y < 9) {
            field[y + 1][x] = 2;
            if(x > 0) field[y + 1][x - 1] = 2;
            if(x < 9) field[y + 1][x + 1] = 2;
        }
        return field;
    }
    public void run() {
        int[][] firstField = new int[10][10], secondField = new int[10][10];
        int firstShips = 10, secondShips = 10, i, j, firstDamaged = 0, secondDamaged = 0, x, y;
        boolean firstTurn = true, isAround;
        ArrayList<Integer[]> damagedShip = new ArrayList<Integer[]>();
        String[] coords;
        //int clients = 0;
        //while(true) {
            try {
                System.out.println("Waiting for client on port " + 
                    serverSocket.getLocalPort() + "...");
                Socket server1 = serverSocket.accept(); 
                System.out.println("Just connected to " + server1.getRemoteSocketAddress());
                DataInputStream in1 = new DataInputStream(server1.getInputStream()); 
                DataOutputStream out1 = new DataOutputStream(server1.getOutputStream());
                
                out1.writeUTF("Enter your field: ");
                firstField = strToField(in1.readUTF());
                
                System.out.println("Waiting for client on port " + 
                    serverSocket.getLocalPort() + "...");
                Socket server2 = serverSocket.accept(); 
                System.out.println("Just connected to " + server2.getRemoteSocketAddress());
                DataInputStream in2 = new DataInputStream(server2.getInputStream()); 
                DataOutputStream out2 = new DataOutputStream(server2.getOutputStream());
                
                out2.writeUTF("Enter your field: ");
                secondField = strToField(in2.readUTF());
                
                while (firstShips > 0 && secondShips > 0) {
                    if(firstTurn) {
                        out1.writeUTF("Your turn. Enter x & y of your shot: ");
                        out2.writeUTF("Your opponent's turn. Wait for his shot...");   
                        coords = in1.readUTF().split(" ");
                        x = Integer.parseInt(coords[0]) - 1; y = Integer.parseInt(coords[1]) - 1;
                        if(secondField[y][x] == 2) {
                            out1.writeUTF("Shooting here has no sense, make another shot");
                            out2.writeUTF("He made pointless shot at "+(x + 1)+"-"+(y + 1)+", so he is up to another try...");
                        } else if(secondField[y][x] == 0) {
                            out1.writeUTF("You missed");
                            out2.writeUTF("He shoot at "+(x + 1)+"-"+(y + 1)+" and missed");
                            secondField[y][x] = 2;
                            firstTurn = false;
                        } else {
                            secondField[y][x] = 2;
                            damagedShip.add(new Integer[]{x, y});
                            isAround = false;
                            for (i = 0; i < damagedShip.size(); i++) {
                                if(lookAround(damagedShip.get(i)[0], damagedShip.get(i)[1], secondField)) {
                                    isAround = true;
                                    break;
                                }
                            }
                            if(isAround) {
                                out1.writeUTF("You damaged his ship");
                                out2.writeUTF("He shoot at "+(x + 1)+"-"+(y + 1)+" and damaged your ship");
                            }
                            else {
                                out1.writeUTF("You sinked his ship");
                                out2.writeUTF("He shoot at "+(x + 1)+"-"+(y + 1)+" and sinked your ship");
                                secondShips--;
                                for (i = 0; i < damagedShip.size(); i++) {
                                    secondField = markAsPointless(damagedShip.get(i)[0], damagedShip.get(i)[1], secondField);
                                }
                                while (damagedShip.size() > 0) damagedShip.remove(damagedShip.size() - 1);
                            }
                        }
                    } else {
                        out2.writeUTF("Your turn. Enter x & y of your shot: ");
                        out1.writeUTF("Your opponent's turn. Wait for his shot...");
                        coords = in2.readUTF().split(" ");
                        x = Integer.parseInt(coords[0]) - 1; y = Integer.parseInt(coords[1]) - 1;
                        if(firstField[y][x] == 2) {
                            out2.writeUTF("Shooting here has no sense, make another shot");
                            out1.writeUTF("He made pointless shot at "+(x + 1)+"-"+(y + 1)+", so he is up to another try...");
                        } else if(firstField[y][x] == 0) {
                            out2.writeUTF("You missed");
                            out1.writeUTF("He shoot at "+(x + 1)+"-"+(y + 1)+" and missed");
                            firstField[y][x] = 2;
                            firstTurn = true;
                        } else {
                            firstField[y][x] = 2;
                            damagedShip.add(new Integer[]{x, y});
                            isAround = false;
                            for (i = 0; i < damagedShip.size(); i++) {
                                if(lookAround(damagedShip.get(i)[0], damagedShip.get(i)[1], firstField)) {
                                    isAround = true;
                                    break;
                                }
                            }
                            if(isAround) {
                                out2.writeUTF("You damaged his ship");
                                out1.writeUTF("He shoot at "+(x + 1)+"-"+(y + 1)+" and damaged your ship");
                            }
                            else {
                                out2.writeUTF("You sinked his ship");
                                out1.writeUTF("He shoot at "+(x + 1)+"-"+(y + 1)+" and sinked your ship");
                                firstShips--;
                                for (i = 0; i < damagedShip.size(); i++) {
                                    firstField = markAsPointless(damagedShip.get(i)[0], damagedShip.get(i)[1], firstField);
                                }
                                while (damagedShip.size() > 0) damagedShip.remove(damagedShip.size() - 1);
                            }
                        }
                    }
                }
                if(firstShips == 0) {
                    out1.writeUTF("You lose! Game over");
                    out2.writeUTF("You win! Game over");
                }
                else {
                    out2.writeUTF("You lose! Game over");
                    out1.writeUTF("You win! Game over");
                }
                
                //out2.writeUTF(in1.readUTF());
                //out1.writeUTF(in2.readUTF());
                server1.close();
                server2.close();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                //break;
            } catch (IOException e) {
                e.printStackTrace();
                //break;
            }
        //}
    }
    public static void main(String [] args) {
        int port = 8778;
        try {
            Thread t = new Server(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
