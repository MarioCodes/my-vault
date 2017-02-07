/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokercliente;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import pokercliente.Carta;

/**
 *
 * @author Mario
 */
public class Conexion {
    private static final int BUFFER_LENGTH = 8192;
    private static final int PUERTO = 8143;
    private static final String SERVER_IP = "127.0.0.1";
    
    private static Socket socket = null;
    private static InputStream in = null;
    private static OutputStream out = null;
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;
    
    private static void cabeceraComienzoConexion() throws IOException, IllegalArgumentException {
        socket = new Socket(SERVER_IP, PUERTO); //IP y PORT del Server.

        in = socket.getInputStream();
        out = socket.getOutputStream();
        oos = new ObjectOutputStream(out);
        ois = new ObjectInputStream(in);
    }
    
    private static void finConexion() {
        try {
            if(ois != null) ois.close();
            if(oos != null) oos.close();
            if(out != null) out.close();
            if(in != null) in.close();
            if(socket != null) socket.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static ArrayList<Carta> obtenerCartasComunes(int identificadorJugador) {
        ArrayList<Carta> comunes = new ArrayList<>();
        try {
            cabeceraComienzoConexion();
            
            oos.writeInt(3);
            oos.flush();
            
            String valor = ois.readObject().toString(); //Carta1
            String palo = ois.readObject().toString();
            comunes.add(new Carta(valor, palo));

            valor = ois.readObject().toString(); //Carta2
            palo = ois.readObject().toString();
            comunes.add(new Carta(valor, palo));

            valor = ois.readObject().toString(); //Carta3
            palo = ois.readObject().toString();
            comunes.add(new Carta(valor, palo));

            return comunes;
            
        }catch(ClassNotFoundException|ClassCastException ex) {
            ex.printStackTrace();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static int apostarJugador(int fichas) {
        int totalPool = -1;
        
        try {
            oos.writeInt(fichas);
            oos.flush();

            totalPool = ois.readInt();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return totalPool;
    }
    
    public static ArrayList<Carta> obtenerManoJugador(int identificadorJugador) {
        ArrayList<Carta> mano = new ArrayList<>();
        try {
            cabeceraComienzoConexion();
            
            oos.writeInt(2);
            oos.flush();
            
            int idJugadorServer = ois.readInt();
            if(idJugadorServer == identificadorJugador) {
                oos.writeBoolean(true);
                oos.flush();
                
                String valor = ois.readObject().toString(); //Carta1
                String palo = ois.readObject().toString();
                mano.add(new Carta(valor, palo));
                
                valor = ois.readObject().toString(); //Carta2
                palo = ois.readObject().toString();
                mano.add(new Carta(valor, palo));
                
                return mano;
            }
        }catch(ClassNotFoundException|ClassCastException ex) {
            ex.printStackTrace();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Realizacion de la conexion al server.
     * @param tipoConexion tipo de conexion. 0 = un jugador mas; 1 = ultimo jugador a añadir.
     * @return Int. Numero propio del jugador.
     */
    public static int realizarConexion(int tipoConexion) {
        try {
            cabeceraComienzoConexion();
            
            in = new BufferedInputStream(socket.getInputStream());
            
            oos.writeInt(tipoConexion);
            oos.flush();
            
            return ois.readInt();
//            boolean estado = ois.readBoolean(); //Leemos la respuesta del server.
            
//            finConexion();
//            return estado;
        }catch(ConnectException ex) {
            System.out.println("Problema en la conexion. " +ex.getLocalizedMessage());
        }catch(IllegalArgumentException ex) {
            System.out.println("Numero de argumentos erroneo. Comprobar que el puerto este dentro de rango.\t" +ex.getLocalizedMessage());
        }catch(IOException ex) {
            System.out.println("Problema de IO.");
            ex.printStackTrace();
        }
        
        return -1;
    }
    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws IOException {
//        Runnable r = () -> realizarConexion();
//        new Thread(r).start();
//        System.out.println("Jugador");
//    }
}
