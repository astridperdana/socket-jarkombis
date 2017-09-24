/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Server;

// File Name GreetingServerx.java
// untuk compile     : javac GreetingServerx.java
// untuk menjalankan : java GreetingServerx 4321
import java.net.*;
import java.io.*;
import java.sql.*;

public class GreetingServerx extends Thread {

    private ServerSocket serverSocket;

    public GreetingServerx(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void simpan(String kueriku) {

        // simpan ke database
    }

    public void run() {
        String conn, mkdirvar;
        String a = "lala";

        System.out.println("Waiting for client on port "
                + serverSocket.getLocalPort() + "...");

        while (true) {
            try {

                Socket server = serverSocket.accept();

                DataInputStream in = new DataInputStream(server.getInputStream());

                conn = in.readUTF();
                System.out.println(" ");
                System.out.println(conn);

                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Terimakasih Telah Terkoneksi ke Server " + server.getLocalSocketAddress() + "\n"
                        + "\nBerikut Menu yang Telah Kami Siapkan :"
                        + "\n1. Membuat Direktori"
                        + "\nSilahkan Pilih Menu :");
                a = in.readUTF().toString();
                System.out.println(a);
//                mkdirvar = in.readUTF();
//                System.out.println(mkdirvar);
                if (a.equals("1")) {
                   out.writeUTF("Masukkan lokasi pembuatan Folder dan nama folder. Contoh : D:\\\\A");
                   mkdirvar = in.readUTF();
                   System.out.println(" ");
                   File folder = new File(mkdirvar);
                   if (folder.exists()) {
                        out.writeUTF("Nama Folder sudah ada.");
                   } else {
                        folder.mkdir();
                        System.out.println("Folder telah dibuat pada lokasi : " + mkdirvar);
                        out.writeUTF("Folder Telah Dibuat.");
                   }  
                } else {
                   System.out.println("Pilihan Tidak Ada.");
                }

                out.writeUTF("Selamat Tinggal.");
                server.close();
            } catch (SocketTimeoutException s) {
                System.out.print(".");   // menunggu
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
//      int port = Integer.parseInt(args[0]);
        int port = 4321;
        try {
            Thread t = new GreetingServerx(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
