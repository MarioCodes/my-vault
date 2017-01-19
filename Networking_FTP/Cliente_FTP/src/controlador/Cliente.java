/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Recopilacion de la implementacion logica del Cliente.
 * @author Mario Codes Sánchez
 * @since 19/01/2017
 */
public class Cliente {
    private static final int BUFFER_LENGTH = 16384;
    private static final int PUERTO = 8142;
    private static final String SERVER_IP = "127.0.0.1";
    
    /**
     * Envia el nombre al Server y devuelve el saludo personalizado de este.
     * @param bw BufferedWriter usado para enviar el nombre.
     * @param br BufferedReader usado para recibir el saludo personalizado.
     * @throws IOException Capturada en el metodo donde se invoca.
     * @deprecated 
     */
    private void ejecucionString(BufferedWriter bw, BufferedReader br) throws IOException {
        //Envio de datos.
            char[] bEnvia = "Mario".toCharArray();
            bw.write(bEnvia);
            bw.flush();

            //Y recibimos la respuesta.
            char[] bRecibe = new char[BUFFER_LENGTH];
            StringBuffer sb = new StringBuffer();

            int n;
            while((n = br.read(bRecibe)) == BUFFER_LENGTH) {
                sb.append(bRecibe);
            }

            sb.append(bRecibe, 0, n);

            System.out.println(sb);
    }
    
    /**
     * Metodo para el envio de un fichero al server.
     */
    private void envioFichero(Socket socket) throws IOException {
        File file = new File("ficheros/fichero.txt");
        long length = file.length();
        byte[] bytes = new byte[16384];
        
        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();
        
        int count;
        while((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }
        
        out.close();
        in.close();
        socket.close();
    }
    
    /**
     * Ejecucion de la conexion al server en si misma.
     */
    public void ejecucion() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        Socket socket = null;
        
        try {
            socket = new Socket(SERVER_IP, PUERTO); //IP y PORT del Server.

            bw = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //ejecucionString(bw, br);
            long inicio = System.currentTimeMillis();
            envioFichero(socket);
            System.out.println("Tiempo de Ejecucion: " +(System.currentTimeMillis()-inicio));
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if(br != null) br.close();
                if(bw != null) bw.close();
                if(socket != null) socket.close();
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
