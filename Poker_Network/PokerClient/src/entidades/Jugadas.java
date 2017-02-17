/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Clase estatica para recopilar las comprobaciones de la mano que tiene cada jugador.
 * @author Mario Codes Sánchez
 * @since 17/02/2017
 */
public class Jugadas {
    public static int valor; //fixme: cambiar a private.
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
    
//    private static boolean checkDoblePareja(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
//        int parejas = 0;
//        int primerValor = getValor(propias.get(0));
//        if(getValor(propias.get(1)) == primerValor)
//    }
    
    public static boolean checkPareja(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
        ArrayList<Integer> valores = getValores(propias, comunes);
        
        for(Integer i: valores) {
            int valor = i;
            for(Integer c: valores) {
               if(c == i) return true; 
            }
        }
        
//        int primerValor = getValor(propias.get(0));
//        if(getValor(propias.get(1)) == primerValor) return true;
//        else {
//            for(Carta c: comunes) {
//                if(getValor(c) == primerValor) return true;
//            }
//        }
//        
        return false;
    }
    
    private static boolean checkCartaAlta(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
        int maxValor = getValor(propias.get(0));
        if(getValor(propias.get(1)) > maxValor) maxValor = getValor(propias.get(1));
        else {
            for(Carta c: comunes) {
                if(getValor(c) > maxValor) maxValor = getValor(c);
            }
        }
        
        Jugadas.valor = maxValor;
        return true;
    }
    
    private static ArrayList<Integer> getValores(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
        ArrayList<Integer> valores = new ArrayList<>();
        for(Carta c: propias) valores.add(getValor(c));
        for(Carta c: comunes) valores.add(getValor(c));
        valores.sort(Comparator.naturalOrder());
        return valores;
    }
}
