/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Clase para la ejecucion de Comandos en el sistema Windows. Copiada y adaptada de mi primer proyecto de Servicios.
 * @author Mario Codes Sánchez
 * @since 23/01/2017
 */
public class Comando {
    private Runtime r;
    private Process p;
    
    
    /**
     * Ejecucion del comando que se introduce y vuelta de la String con el resultado. Version sobrecargada sin ordenes extra.
     * @param comando Comando que se quiera ejecutar
     */
    public void ejecucionComando(String comando) {
        r = Runtime.getRuntime();
        p = null;
        
        try {
            p = r.exec(comando);
            
            OutputStream os = p.getOutputStream();
            os.flush();
            
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            ArrayList<String> output = new ArrayList<>();
            String linea;
            
            if(p.waitFor() == 0) {
                while((linea = br.readLine()) != null) {
                    output.add(linea);
                }
//                MainWindow.setOutputCorrectoPantalla(output); //Hacemos un set del arrayList en la pantalla donde sera mostrado.
            } else { //Caso en el que salga error.
                InputStream er = p.getErrorStream();
                BufferedReader brErr = new BufferedReader(new InputStreamReader(er));
                
//                new WindowError(brErr);
            }
        }catch(IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Ejecucion del comando que se introduce y vuelta de la String con el resultado. Version con parametros extra.
     * @param comando Comando que se quiere ejecutar.
     * @param inputExtra Parametros extra del comando.
     */
    public void ejecucionComando(String comando, String inputExtra) {       
        r = Runtime.getRuntime(); //Devuelve el objeto Runtime asociado con la aplicacion Java en curso.
        p = null; //Representa a un proceso, posee metodos que facilitan la interaccion con dicho proceso.
        
        try {
            p = r.exec(comando); /* Fixme - Nota. Esto ejecutado asi, puede dar conflicto con permisos de usuario, ya que no se ejecuta con permisos de administrador.
                                                para eso utilizar: RUNAS /ENV /USER:MARIO "COMANDO". Habra que mirar como meter la contraseña en esta linea, 
                                                por cmd la pide pero deberia introducirla en esta misma linea. */
            
            OutputStream os = p.getOutputStream();
            os.write(inputExtra.getBytes());
            os.flush();
                
            InputStream is = p.getInputStream(); //Se debe hacer despues del OutputStream y antes del BufferReader.
            BufferedReader br  = new BufferedReader(new InputStreamReader(is));
            
            ArrayList output = new <String> ArrayList(); //Creamos nueva arrayList de Strings donde se almacenara el output. Se podria hacer igual pasando el buffer(?) directamente.
            String linea;
            
            if(p.waitFor() == 0) { //Caso en el que sale bien.
                while((linea = br.readLine()) != null) {
                    output.add(linea); //Añadimos las lineas una a una.
                }
            
//                MainWindow.setOutputCorrectoPantalla(output); //Hacemos un set del arrayList en la pantalla donde sera mostrado.
            } else { //Caso en el que salga error.
                InputStream er = p.getErrorStream();
                BufferedReader brErr = new BufferedReader(new InputStreamReader(er));
                
//                new WindowError(brErr);
            }
        } catch(IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
