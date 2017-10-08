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

    public static String FILE_TO_RECEIVED = "";  
    public static String FILE_TO_SENDS = "";

    public final static int FILE_SIZE = 6022386;

    public static void main(String[] args) {
//      String serverName = args[0];
//      int port = Integer.parseInt(args[1]);
        String serverName = "localhost";
        int port = 4321;
        String inp1, inp2, inp3, gabung, lokasi;
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        while (true) {
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
                } else if (inp1.equals("4")) {
                    System.out.println(in.readUTF());
                    inp2 = bf.readLine();
                    out.writeUTF(inp2);
                    System.out.println(in.readUTF());
                } else if (inp1.equals("5")) {
                    System.out.println(in.readUTF());
                    inp2 = bf.readLine();
                    out.writeUTF(inp2);
                    System.out.println(in.readUTF());
                    inp3 = bf.readLine();
                    out.writeUTF(inp3);
                    System.out.println("Masukkan lokasi penyimpanan file :");
                    lokasi = bf.readLine();
                    gabung = lokasi + "/" + inp3;
                    FILE_TO_RECEIVED = gabung;
                    byte[] mybytearray = new byte[FILE_SIZE];
                    InputStream is = client.getInputStream();
                    fos = new FileOutputStream(FILE_TO_RECEIVED);
                    bos = new BufferedOutputStream(fos);
                    bytesRead = is.read(mybytearray, 0, mybytearray.length);
                    current = bytesRead;

                    do {
                        bytesRead
                                = is.read(mybytearray, current, (mybytearray.length - current));
                        if (bytesRead >= 0) {
                            current += bytesRead;
                        }
                    } while (bytesRead > -1);

                    bos.write(mybytearray, 0, current);
                    bos.flush();
                    System.out.println("File " + FILE_TO_RECEIVED + " telah di download (" + current + " bytes read)");
                } else if (inp1.equals("6")) {
                    System.out.println(in.readUTF());
                    inp2 = bf.readLine();
                    out.writeUTF(inp2);
                    System.out.println(in.readUTF());
                    inp3 = bf.readLine();
                    out.writeUTF(inp3);
                    gabung = inp2 + "/" + inp3;
                    FILE_TO_SENDS = gabung;
                    File myFile = new File(FILE_TO_SENDS);
                    byte[] mybytearray = new byte[(int) myFile.length()];
                    fis = new FileInputStream(myFile);
                    bis = new BufferedInputStream(fis);
                    bis.read(mybytearray, 0, mybytearray.length);
                    os = client.getOutputStream();
                    System.out.println("Sending " + FILE_TO_SENDS + "(" + mybytearray.length + " bytes)");
                    os.write(mybytearray, 0, mybytearray.length);
                    os.flush();
                    System.out.println("Done.");
                }
                if (os != null) {
                    os.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (bos != null) {
                    bos.close();
                }
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
