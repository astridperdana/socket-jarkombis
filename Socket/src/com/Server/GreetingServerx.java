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
    String FILE_TO_SEND = "";
    public static String FILE_TO_RECEIVEDS = "";
    public final static int FILE_SIZES = 60223860;
    

    public void listFilesAndFilesSubDirectories(String directoryName) {
        File directory = new File(directoryName);
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
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

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
                        + "\n3. Membuat File (BETA)"
                        + "\n4. Melihat Tree Dalam Direktori"
                        + "\n5. Download File Pada Server"
                        + "\n6. Upload File Pada Server"
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
                    System.out.println("client" + conn + "meminta tree dari direktori" + dirvar);
                    listFilesAndFilesSubDirectories(dirvar);
                    out.writeUTF(kata);
                    kata = "";
                } else if (a.equals("5")) {
                    out.writeUTF("Masukkan lokasi file yang ingin di download. Contoh : C:/Users/5213100176");
                    mkdirvar = in.readUTF();
                    out.writeUTF("Masukkan nama file dan extensinya : ");
                    fname = in.readUTF();
                    gabung = mkdirvar + "/" + fname;
                    System.out.println("client " + conn + " ingin mendownload file" + gabung);
                    FILE_TO_SEND = gabung;
                    File myFile = new File(FILE_TO_SEND);
                    byte[] mybytearray = new byte[(int) myFile.length()];
                    fis = new FileInputStream(myFile);
                    bis = new BufferedInputStream(fis);
                    bis.read(mybytearray, 0, mybytearray.length);
                    os = server.getOutputStream();
                    System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                    os.write(mybytearray, 0, mybytearray.length);
                    os.flush();
                    System.out.println("Done.");
                } else if (a.equals("6")) {
                    out.writeUTF("Masukkan lokasi file yang ingin di upload. Contoh : C:/Users/5213100176");
                    mkdirvar = in.readUTF();
                    out.writeUTF("Masukkan nama file dan extensinya : ");
                    fname = in.readUTF();
                    String uploc = "C:/Users/5213100176/Downloads/Uploads"; // tempat upload disesuaikan
                    gabung = uploc + "/" + fname;
                    System.out.println("client " + conn + " ingin mengupload file ke " + gabung);
                    FILE_TO_RECEIVEDS = gabung;
                    byte[] mybytearray = new byte[FILE_SIZES];
                    InputStream is = server.getInputStream();
                    fos = new FileOutputStream(FILE_TO_RECEIVEDS);
                    bos = new BufferedOutputStream(fos);
                    bytesRead = is.read(mybytearray, 0, mybytearray.length);
                    current = bytesRead;

                    do {
                        bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
                        if (bytesRead >= 0) {
                            current += bytesRead;
                        }
                    } while (bytesRead > -1);

                    bos.write(mybytearray, 0, current);
                    bos.flush();
                    System.out.println("File " + FILE_TO_RECEIVEDS + " terupload (" + current + " bytes read)");
                } else {
                    System.out.println("Pilihan Tidak Ada.");
                }

                out.writeUTF("Selamat Tinggal.");
                if(bos != null){bos.close();}
                if(fos != null){fos.close();}
                if(fis != null){fis.close();}
                if(bis != null){bis.close();}
                server.close();
            } catch (SocketTimeoutException s) {
                System.out.print(".");   // menunggu
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } finally {

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
