/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Representacion de un Cuadrado de 3x3 casillas. Dentro de cada uno no se podran repetir numeros.
 * @author Mario Codes Sánchez
 * @since 30/11/2016
 */
public class Cuadrado {
    private Casilla[] casilla = new Casilla[9]; //Cambio de Array a ArrayList con capacidad maxima de 9. Me es mas sencillo para trabajar.
    
    /**
     * Constructor por defecto. Genera el cuadrado con sus 9 casillas.
     */
    public Cuadrado() {
        generacionCasillas();
    }
    
    /**
     * Check que comprueba si el ultimo numero generado ya se habia generado previamente EN EL MISMO CUADRADO o no.
     * @return True si el numero no se habia generado previamente en el mismo cuadrado.
     */
    private boolean checkNumeroGeneradoIsOK(int numeroAComprobar, ArrayList<Integer> numerosPreviamenteGenerados) {
        boolean numIsOk = true; //Por defecto lo considero valido hasta que se demuestre lo contrario.
        
        for(int enteroTmp: numerosPreviamenteGenerados) {
            if(numeroAComprobar == enteroTmp) numIsOk = false;
        }
        
        return numIsOk;
    }
    
    /**
     * Generacion de numeros 1-9 Aleatorios haciendo que no se puedan repetir dentro del propio cuadrado.
     * @return Numero 1-9 no repetido.
     * fixme: mas adelante esto habra que moverlo al Façade, debera chequear no solo el cuadrado, sino la fila y columna.
     */
    private int generacionNumerosAleatorios(ArrayList<Integer> numerosPreviamenteGenerados) {
        int actualNumber;
        
        do {
            actualNumber = ThreadLocalRandom.current().nextInt(1, 9 + 1);
        }while(!checkNumeroGeneradoIsOK(actualNumber, numerosPreviamenteGenerados));
        
        numerosPreviamenteGenerados.add(actualNumber);
        return actualNumber;
    }
    
    /**
     * Rellenamos el cuadrado con sus 9 Casillas correspondientes.
     */
    private void generacionCasillas() {
        ArrayList<Integer> numerosPreviamenteGenerados = new ArrayList<>(9);
        
        for (int i = 0; i < 9; i++) {
            casilla[i] = new Casilla(generacionNumerosAleatorios(numerosPreviamenteGenerados));
        }
    }
    
    /**
     * Al hacer Sout de un cuadrado, sale este correctamente formado de forma directa.
     * @return String con el contenido de un Cuadrado con todas sus Casillas.
     */
    @Override
    public String toString() {
        String buffer = "";
        
        for (int i = 1; i < 10; i++) {
            buffer += casilla[i-1] +"\t"; //i-1 porque necesito sacar modulos de 3. i debe empezar en 1 en vez de 0
            if(i%3 == 0) buffer += "\n"; //Para que salga con forma de cuadrado. Salto de linea cada 3 numeros.
        }
        
        return buffer;
    }
    
    /**
     * @return the casilla
     */
    public Casilla[] getCasilla() {
        return casilla;
    }

    /**
     * @param casilla the casilla to set
     */
    public void setCasilla(Casilla[] casilla) {
        this.casilla = casilla;
    }
    
    
}
