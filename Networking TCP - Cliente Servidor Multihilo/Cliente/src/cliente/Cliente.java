/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Mario
 */
public class Cliente {
    private static final int BUFFER_LENGTH = 3;
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
//        ObjectOutputStream oos = null;
//        ObjectInputStream ois = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        Socket s = null;
        
        try {
            //Instancio el server con la IP y el PORT
            s = new Socket("127.0.0.1", 5432);
//            oos = new ObjectOutputStream(s.getOutputStream());
//            ois = new ObjectInputStream(s.getInputStream());
            bw = new BufferedWriter(new PrintWriter(s.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            //Envio un nombre
//            oos.writeObject("Pablo");
            char[] bEnvia = "Pablo".toCharArray();
            bw.write(bEnvia);
            bw.flush();
            
            //Recibo la respuesta (saludo personalizado).
//            String ret = (String) ois.readObject();
            char[] bRecibe = new char[BUFFER_LENGTH];
            StringBuffer sb = new StringBuffer();
            
            int n;
            while((n = br.read(bRecibe)) == BUFFER_LENGTH) {
                sb.append(bRecibe);
            }
            
            sb.append(bRecibe, 0, n);
            
            //Muestro la respuesta del server.
//            System.out.println(ret);
            System.out.println(sb);
        }catch(Exception ex) {
            ex.printStackTrace();
        }finally {
//            if(ois != null) ois.close();
//            if(oos != null) oos.close();
            if(br != null) br.close();
            if(bw != null) bw.close();
            if(s != null) s.close();
        }
    }
    
}
