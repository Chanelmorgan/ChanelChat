package com.example.loginsystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Server {

    /*
    UDP:
    - operates on top of IP to transmit datagrams over a networks
    - connectionless meaning no end-to-end connection is formed.
    - Avoids the overhead associated with connections, error checking, and retransmission of data
    - Fast, making it useful for query response protocols such as DNS

    Threats and Vulnerabilities:
    - Lack of verification and end-to-end connection makes it vulnerable to denial-of-service attacks.
    -UPD Flood - large volumes of spoofed UDP packets sent to multiple ports on a single server
    - DNS amplification - send UDP packets with spoofed IP address, which corresponds to the IP fo the victim, toi itS
      DNS resolvers
    - DNS Port  Scan - send UDP packets to ports on a server to determine which ports are open

     UDP Packet Structure:
     - an entre packet header is only 64 bits ( 8 bits_
     - Length - length of UDP header + UDP data
     - Check sum - allows receiving devices to verify the integrity of the packet header and payload

     Source port ( 16 bits_
     Length( 16 bites)
     Destination port ( 16 bits)
     Check sum ( 16 bits)
     Data ( if any )
     */

    // class variables
    private DatagramSocket datagramSocket;
    // holds the data
    private byte[] buffer = new byte[256]; // why 256?

    // constructor
    public Server(DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    // Method to send and receive packets
    public void receiveThenSend(){

        while(true){
            try{
                // no connection between the client and server
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                // this is a blocking method - until a datagram packet has been received
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress = datagramPacket.getAddress(); // so we can send information back, so we get the ip address from the packets' header information
                int port = datagramPacket.getPort();
                String messageFromClient = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Message from client: "  + messageFromClient);
                // referencing a new object, sending the other packet
                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                // sends with no connection because it has the destination information
                datagramSocket.send(datagramPacket);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                break; // so we can break out of the while loop
            }
        }
    }

    public static void main(String[] args) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        Server server = new Server(datagramSocket);
        server.receiveThenSend();

    }



}
