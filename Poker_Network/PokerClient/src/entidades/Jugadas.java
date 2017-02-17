/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.ArrayList;

/**
 * Clase estatica para recopilar las comprobaciones de la mano que tiene cada jugador.
 * @author Mario Codes Sánchez
 * @since 17/02/2017
 */
public class Jugadas {
    private static int valor;
    private static String jugada;
    
    /**
     * Obtener el valor numerico de una carta. A = 14, K = 13...
     * @param carta Carta de la cual obtenemos el valor.
     * @return Valor numerico de la Carta.
     */
    private static int getValor(Carta carta) {
        int valor = -1;
        String v = carta.toString().substring(0, 1);
        
        try {
            valor = Integer.parseInt(v);
        }catch(ClassCastException|NumberFormatException ex) {
            switch(v) {
                case "A":
                    valor = 14;
                    break;
                case "K":
                    valor = 13;
                    break;
                case "Q":
                    valor = 12;
                    break;
                case "J":
                    valor = 11;
                    break;
            }
        }
        
        return valor;
    }
    
    /**
     * Obtencion del palo de la carta para comprobar escaleras, fulls y demas.
     * @param carta Carta a obtener el palo.
     * @return Palo de la carta introducida.
     */
    private static String getPalo(Carta carta) {
        return carta.toString().substring(2);
    }
    
    private static boolean checkPareja(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
        int primerValor = getValor(propias.get(0));
        if(getValor(propias.get(1)) == primerValor) return true;
        else {
            for(Carta c: comunes) {
                if(getValor(c) == primerValor) return true;
            }
        }
        
        return false;
    }
    
    private static boolean cartaAlta(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
        int primerValor = getValor(propias.get(0));
        if(getValor(propias.get(1)) > primerValor) return true;
        else {
            for(Carta c: comunes) {
                if(getValor(c) >= primerValor) return true;
            }
        }
        
        return true;
    }
}
