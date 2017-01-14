/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semaforoconmonitor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Replicacion del funcionamiento de un Semaforo mediante el uso de Monitores y metodos sincronizados.
 * @author Mario Codes Sánchez
 * @since 14/01/2017
 */
public class SemaforoConMonitor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Semaforo s = new Semaforo(2);
            
            Runnable runnable1 = () -> s.adquirir();
            Runnable runnable2 = () -> s.adquirir();
            
            Thread thread1 = new Thread(runnable1);
            Thread thread2 = new Thread(runnable2);
            
            thread1.start();
            thread2.start();
            
            thread1.join();
            thread2.join();
            
            Thread thread3 = new Thread(runnable1);
            thread3.start();
            thread3.join();
            
            System.out.println(s.getPermisos());
        } catch (InterruptedException ex) {
            Logger.getLogger(SemaforoConMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
