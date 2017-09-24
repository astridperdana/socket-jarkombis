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
import java.util.ArrayList;
import java.util.Iterator;

public class GreetingServerx extends Thread {

    private ServerSocket serverSocket;

    public GreetingServerx(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void simpan(String kueriku) {

        // simpan ke database
    }

    String kata = "";

    public void listFilesAndFilesSubDirectories(String directoryName) {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                kata += file.getAbsolutePath() + "\n";
            } else if (file.isDirectory()) {
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
            }
        }
    }

    public void run() {
        String conn, mkdirvar, fname, gabung, dirvar;
        String a = "", b = "";

        System.out.println("Waiting for client on port "
                + serverSocket.getLocalPort() + "...");

        while (true) {
            try {

                Socket server = serverSocket.accept();
                DataInputStream in = new DataInputStream(server.getInputStream());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());

                conn = in.readUTF();
                System.out.println(" ");
                System.out.println(conn + "Connected");

                out.writeUTF("Terimakasih Telah Terkoneksi ke Server " + server.getLocalSocketAddress() + "\n"
                        + "\nBerikut Menu yang Telah Kami Siapkan :"
                        + "\n1. Membuat Direktori"
                        + "\n2. Melihat Isi Direktori"
                        + "\n3. Membuat File"
                        + "\n4. Melihat Tree Dalam Direktori"
                        + "\nSilahkan Pilih Menu :");
                a = in.readUTF().toString();
                System.out.println(a);
//                mkdirvar = in.readUTF();
//                System.out.println(mkdirvar);
                if (a.equals("1")) {
                    out.writeUTF("Masukkan lokasi pembuatan Folder. Contoh : C:/Users/5213100176");
                    mkdirvar = in.readUTF();
                    System.out.println("client " + conn + " ingin membuat folder pada" + mkdirvar);
                    out.writeUTF("Masukkan nama folder : ");
                    fname = in.readUTF();
                    gabung = mkdirvar + "/" + fname;
                    File folder = new File(gabung);
                    if (folder.exists()) {
                        out.writeUTF("Nama Folder sudah ada.");
                    } else {
                        folder.mkdir();
                        System.out.println("Folder telah dibuat pada lokasi : " + mkdirvar);
                        out.writeUTF("Folder Telah Dibuat.");
                    }
                } else if (a.equals("2")) {
                    out.writeUTF("Input lokasi folder yang ingin diketahui isinya. Contoh : C:/Users/5213100176");
                    dirvar = in.readUTF();
                    System.out.println("client " + conn + " Meminta list file atau direktori, dari direktori : " + dirvar);
                    String f = "", d = "";
                    ArrayList file1 = new ArrayList();
                    ArrayList dir1 = new ArrayList();
                    File directory = new File(dirvar);
                    //get all the files from a directory
                    File[] fList = directory.listFiles();
                    for (File file : fList) {
                        if (file.isFile()) {
                            file1.add(file.getName());
                        } else if (file.isDirectory()) {
                            dir1.add(file.getName());
                        }
                    }
                    Iterator fileit = file1.iterator();
                    Iterator dirit = dir1.iterator();
                    out.writeUTF("Pilihan :"
                            + "\n1. Lihat file saja."
                            + "\n2. Lihat direktori saja."
                            + "\n3. Liat direktori dan file."
                            + "\nInput : ");
                    b = in.readUTF();
                    if (b.equals("1")) {
                        while (fileit.hasNext()) {
                            f += fileit.next() + "\n";
                        }
                        out.writeUTF("File : \n" + f);
                        System.out.println("List File Telah Dikirimkan.");
                    } else if (b.equals("2")) {
                        while (dirit.hasNext()) {
                            d += dirit.next() + "\n";
                        }
                        out.writeUTF("Folder : \n" + d);
                        System.out.println("List Direktori Telah Dikirimkan.");
                    } else {
                        while (fileit.hasNext()) {
                            f += fileit.next() + "\n";
                        }
                        while (dirit.hasNext()) {
                            d += dirit.next() + "\n";
                        }
                        out.writeUTF("Folder : \n" + d + "File : \n" + f);
                        System.out.println("List File dan Direktori Telah Dikirimkan.");
                    }
                } else if (a.equals("3")) {
                    out.writeUTF("Masukkan lokasi pembuatan File. Contoh : C:/Users/5213100176");
                    mkdirvar = in.readUTF();
                    System.out.println("client " + conn + " ingin membuat file pada" + mkdirvar);
                    out.writeUTF("Masukkan nama file dan extensinya : ");
                    fname = in.readUTF();
                    gabung = mkdirvar + "/" + fname;
                    File file = new File(gabung);
                    if (file.exists()) {
                        out.writeUTF("Nama File sudah ada.");
                    } else {
                        file.createNewFile();
                        System.out.println("File telah dibuat pada lokasi : " + mkdirvar);
                        out.writeUTF("File Telah Dibuat.");
                    }
                } else if (a.equals("4")) {
                    out.writeUTF("Input lokasi folder yang ingin diketahui isinya. Contoh : C:/Users/5213100176");
                    dirvar = in.readUTF();
                    listFilesAndFilesSubDirectories(dirvar);
                    out.writeUTF(kata);
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
