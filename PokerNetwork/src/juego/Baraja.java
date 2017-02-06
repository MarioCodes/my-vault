/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.util.ArrayList;

/**
 * Conjunto de Cartas que forman la baraja de Juego.
 * @author Mario Codes Sánchez
 * @since 06/02/2017
 */
public class Baraja {
    private final ArrayList<Carta> BARAJA = new ArrayList<>(52);
    
    /**
     * Constructor por defecto. Se encarga de inicializar la baraja.
     */
    public Baraja() {
        iniBaraja();
    }
    
    /**
     * Inicializacion de las 13 cartas de cada palo. Se repetira 4 veces.
     * @param palo Nombre del palo a asignar.
     */
    private void iniPalos(String palo) {
        for (int i = 0; i < 9; i++) {
            BARAJA.add(new Carta(Integer.toString(i+1), palo));
        }
        
        BARAJA.add(new Carta("J", palo));
        BARAJA.add(new Carta("Q", palo));
        BARAJA.add(new Carta("K", palo));
        BARAJA.add(new Carta("A", palo));
    }
    
    /**
     * Inicializacion de la Baraja con todas sus cartas.
     */
    private void iniBaraja() {
        iniPalos("Corazones");
        iniPalos("Picas");
        iniPalos("Treboles");
        iniPalos("Diamantes");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for(Carta carta: BARAJA) {
            sb.append(carta);
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * @return the BARAJA
     */
    public ArrayList<Carta> getBARAJA() {
        return BARAJA;
    }
}
