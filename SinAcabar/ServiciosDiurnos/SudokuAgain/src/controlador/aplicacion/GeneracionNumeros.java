/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase estatica donde recopilo todos los metodos necesarios para la generacion de los numeros segun las reglas.
 * @author Mario Codes Sánchez
 * @since 01/12/2016
 */
public class GeneracionNumeros {
    /**
     * Check que comprueba si el ultimo numero generado ya se habia generado previamente EN EL MISMO CUADRADO o no.
     * @param numeroAComprobar
     * @param numerosGeneradosPreviamente
     * @return True si el numero no se habia generado previamente en el mismo cuadrado.
     */
    public static boolean checkNumeroGeneradoIsOK(int numeroAComprobar, int[] numerosGeneradosPreviamente) {
        boolean numIsOk = true; //Por defecto lo considero valido hasta que se demuestre lo contrario.
        
        for(int enteroTmp: numerosGeneradosPreviamente) {
            if(numeroAComprobar == enteroTmp) numIsOk = false;
        }
        
        return numIsOk;
    }
    
    /**
     * Sera el unico que se genere aleatoriamente sin chequear nadie mas.
     * @return ArrayList con los 9 digitos aleatorios.
     */
    public static int[] generacionNumerosPrimerCuadrado() {
        int[] numerosGeneradosPreviamente = new int[9];
        int actualNumber;
        
        for (int i = 0; i < numerosGeneradosPreviamente.length; i++) {
            do {
                actualNumber = ThreadLocalRandom.current().nextInt(1, 9 + 1);
            }while(!checkNumeroGeneradoIsOK(actualNumber, numerosGeneradosPreviamente));
            
            numerosGeneradosPreviamente[i] = actualNumber;
        }
        
        return numerosGeneradosPreviamente;
    }
}
