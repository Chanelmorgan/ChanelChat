package com.example.loginsystem;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {

    // class variable
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;
    private int port = 1234;

    // constructor
    public Client(DatagramSocket datagramSocket, InetAddress inetAddress){
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    // Method to send and receive packets
    public void sendThenReceive(){
        // read information from the console
        Scanner scanner = new Scanner(System.in);

        while(true){
            try{
                String messageToSend = scanner.nextLine();
                buffer = messageToSend.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket); // blocking operation, until the server sends something back
                String messageFromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("The message you sent to the server: " + messageFromServer);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                break; // break out of the infinite loop
            }
        }


    }



    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost"); // losthost because in own computer
        Client client = new Client(datagramSocket, inetAddress);
        System.out.println("The program started... ");
        client.sendThenReceive();

    }

}
