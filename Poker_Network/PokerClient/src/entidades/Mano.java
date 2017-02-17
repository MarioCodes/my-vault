/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Mano del Jugador, se compone de sus 2 cartas personales y las 3 mas altas de las 5 de la mesa.
 * @author Mario Codes Sánchez
 * @since 15/02/2017
 */
public class Mano {
    private ArrayList<Carta> cartas_propias = new ArrayList<>();
    private ArrayList<Carta> cartas_mesa = new ArrayList<>();
    private String jugada;
    private int valor;
    
    /**
     * Aniadido de una unica carta a la mano personal.
     * @param carta Carta a anaidir.
     */
    public void aniadirCartaPropias(Carta carta) {
        cartas_propias.add(carta);
    }
    
    /**
     * Obtener el valor numerico de una carta. A = 14, K = 13...
     * @param carta Carta de la cual obtenemos el valor.
     * @return Valor numerico de la Carta.
     */
    private int getValor(Carta carta) {
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
    private String getPalo(Carta carta) {
        return carta.toString().substring(2);
    }
    
    /**
     * Para los casos en los que las 5 cartas de la mesa hagan escalera.
     * @param valoresComunes 5 cartas de la mesa transformadas en su valor int.
     * @return True si las 5 hacen escalera.
     */
    private boolean checkEscaleraComunes(ArrayList<Integer> valoresComunes) {
        if(valoresComunes.size() == 5) {
            int firstNum = valoresComunes.get(0);
            for (int i = 1; i < valoresComunes.size(); i++) {
                if(valoresComunes.get(i) != firstNum+1) return false;
                else firstNum++;
            }
            return true;
        } else return false;
    }
    
    private boolean checkEscalera(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
        ArrayList<Carta> tmp = new ArrayList<>();
        tmp.addAll(propias);
        
        ArrayList<Integer> valoresPropias = new ArrayList<>();
        for(Carta c: propias) valoresPropias.add(getValor(c));
        valoresPropias.sort(Comparator.naturalOrder());
//        for(int i : valoresPropias) System.out.println(i);
        
        ArrayList<Integer> valoresComunes = new ArrayList<>();
        for(Carta c: comunes) valoresComunes.add(getValor(c));
        valoresComunes.sort(Comparator.naturalOrder());
//        for(int i : valoresComunes) System.out.println(i);
        
        if(checkEscaleraComunes(valoresComunes)) return true;
        
        int primerValor = getValor(comunes.get(0));
        for (int i = 0; i < comunes.size()+2; i++) {
            
        }
        
        return false;
    }
    
    private boolean checkPareja(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
        int primerValor = getValor(propias.get(0));
        if(getValor(propias.get(1)) == primerValor) return true;
        else {
            for(Carta c: comunes) {
                if(getValor(c) == primerValor) return true;
            }
        }
        
        return false;
    }
    
    private boolean cartaAlta(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
        int primerValor = getValor(propias.get(0));
        if(getValor(propias.get(1)) > primerValor) return true;
        else {
            for(Carta c: comunes) {
                if(getValor(c) >= primerValor) return true;
            }
        }
        
        return true;
    }
    
    private ArrayList<Carta> compararCartas(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
        checkEscalera(propias, comunes);
        
        return null;
    }
    
    public ArrayList<Carta> mejorCombinacion() {
        ArrayList<Carta> propias = cartas_propias, comunes = cartas_mesa;
        ArrayList<Carta> mejorCombinacion = null;
        
        compararCartas(propias, comunes);
        
        return mejorCombinacion;
    }
    
    /**
     * Aniadido de varias cartas a la mano personal.
     * @param carta Cartas a aniadir.
     */
    public void aniadirCartaPropias(ArrayList<Carta> carta) {
        cartas_propias.addAll(carta);
    }
    
    /**
     * Aniadido de una unica carta nueva a las comunes de la mesa.
     * @param carta Carta a aniadir.
     */
    public void aniadirCartaMesa(Carta carta) {
        cartas_mesa.add(carta);
    }
    
    /**
     * Aniadido de varias cartas nuevas a las comunes de la mesa.
     * @param carta Cartas a aniadir.
     */
    public void aniadirCartaMesa(ArrayList<Carta> carta) {
        cartas_mesa.addAll(carta);
    }
    
    /**
     * Limpiamos las cartas de la mano y la mesa para comenzar una nueva ronda.
     */
    public void limpiarCartas() {
        cartas_propias = new ArrayList<>();
        cartas_mesa = new ArrayList<>();
    }

    /**
     * @return the mano
     */
    public ArrayList<Carta> getCartasPropias() {
        return cartas_propias;
    }

    /**
     * @return the cartas_mesa
     */
    public ArrayList<Carta> getCartas_mesa() {
        return cartas_mesa;
    }
}
