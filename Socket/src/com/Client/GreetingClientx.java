/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Client;

// File Name GreetingClientx.java
// untuk compile     : javac GreetingClientx.java
// untuk menjalankan : java GreetingClientx localhost 4321
import java.net.*;
import java.io.*;

public class GreetingClientx {

//    private ClientSocket clientSocket;
//    public GreetingClientx (int port, String ip) throws IOException{
//        Socket client = new Socket(ip, port);
//        OutputStream outToServer = client.getOutputStream();
//    }
//    String serverName = "localhost";
//    int port = 4321;
//    
//    OutputStream outToServer = client.getOutputStream();
//    DataOutputStream out = new DataOutputStream(outToServer);
//    
//    public void choice(String inputUser) {
//        if (inputUser.equals("1")) {
//                System.out.println(in.readUTF());
//                inp2 = bf.readLine();
//                out.writeUTF(inp2);
//                System.out.println(in.readUTF());
//            }
//        
//    }
    public static void main(String[] args) {
//      String serverName = args[0];
//      int port = Integer.parseInt(args[1]);
        String serverName = "localhost";
        int port = 4321;
        String inp1, inp2, inp3;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            //output send to server
            out.writeUTF("Octgi " + client.getLocalSocketAddress());

            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println("Server says :" + in.readUTF());
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            inp1 = bf.readLine();
            out.writeUTF(inp1);
            if (inp1.equals("1")) {
                System.out.println(in.readUTF());
                inp2 = bf.readLine();
                out.writeUTF(inp2);
                System.out.println(in.readUTF());
                inp3 = bf.readLine();
                out.writeUTF(inp3);
                System.out.println(in.readUTF());
            } else if (inp1.equals("2")) {
                System.out.println(in.readUTF());
                inp2 = bf.readLine();
                out.writeUTF(inp2);
                System.out.println(in.readUTF());
                inp3 = bf.readLine();
                out.writeUTF(inp3);
                System.out.println(in.readUTF());
            } else if (inp1.equals("3")) {
                System.out.println(in.readUTF());
                inp2 = bf.readLine();
                out.writeUTF(inp2);
                System.out.println(in.readUTF());
                inp3 = bf.readLine();
                out.writeUTF(inp3);
                System.out.println(in.readUTF());
            } else if (inp1.equals("4")){
                System.out.println(in.readUTF());
                inp2 = bf.readLine();
                out.writeUTF(inp2);
                System.out.println(in.readUTF());
            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
