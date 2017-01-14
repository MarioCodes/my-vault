/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productoresconsumidoresmonitor;

import vista.ventanas.MainWindow;

/**
 * Version del programa mejorada, ya no se utilizan semaforos con tamaños variables, sino un Monitor. El .run() de Productores / Consumidores se vuelve mucho mas sencillo.
 * @author Mario Codes Sánchez
 * @since 14/01/2017
 * @version 1.1 - Aniadidas opciones para recomenzar los hilos una vez parados.
 */
public class ProductoresConsumidores {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new MainWindow();
    }
}
