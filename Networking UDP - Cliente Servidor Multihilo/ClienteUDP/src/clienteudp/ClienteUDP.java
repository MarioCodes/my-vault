/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteudp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Mario
 */
public class ClienteUDP {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        //Instanciamos un DatagramSocket.
        DatagramSocket socket = new DatagramSocket();
        
        //Buffer con info a enviar.
        byte[] bEnviar = "Pablo".getBytes();
        
        //ip del server
        byte[] ip = {127, 0, 0, 1};
        InetAddress address = InetAddress.getByAddress(ip);
        
        //Paquete de informacion a enviar, ip + port (5432).
        DatagramPacket packet = new DatagramPacket(bEnviar, bEnviar.length, address, 5432);
        
        //Envio el paquete.
        socket.send(packet);
        
        //Muestro el resultado.
        String saludo = new String(packet.getData(), 0, packet.getLength());
        System.out.println(saludo);
        
        socket.close();
    }
    
}
