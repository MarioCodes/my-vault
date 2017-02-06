/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 * Hook que se ejecutara automaticamente al finalizar el programa para cerrar todas las conexiones abiertas durante este.
 * @author Mario Codes Sánchez
 * @since 06/02/2017
 */
public class ShutdownHook extends Thread{
    /**
     * Accion a ejecutar.
     * Para implementarlo donde quiera, copiar -> Runtime.getRuntime().addShutdownHook(new ShutdownHook());
     */
    @Override
    public void run() {
        
    }
}
