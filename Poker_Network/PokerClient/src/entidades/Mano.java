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
    
//    /**
//     * Para los casos en los que las 5 cartas de la mesa hagan escalera.
//     * @param valoresComunes 5 cartas de la mesa transformadas en su valor int.
//     * @return True si las 5 hacen escalera.
//     */
//    private boolean checkEscaleraComunes(ArrayList<Integer> valoresComunes) {
//        if(valoresComunes.size() == 5) {
//            int firstNum = valoresComunes.get(0);
//            for (int i = 1; i < valoresComunes.size(); i++) {
//                if(valoresComunes.get(i) != firstNum+1) return false;
//                else firstNum++;
//            }
//            return true;
//        } else return false;
//    }
    
//    private boolean checkEscalera(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
//        ArrayList<Carta> tmp = new ArrayList<>();
//        tmp.addAll(propias);
//        
//        ArrayList<Integer> valoresPropias = new ArrayList<>();
//        for(Carta c: propias) valoresPropias.add(getValor(c));
//        valoresPropias.sort(Comparator.naturalOrder());
////        for(int i : valoresPropias) System.out.println(i);
//        
//        ArrayList<Integer> valoresComunes = new ArrayList<>();
//        for(Carta c: comunes) valoresComunes.add(getValor(c));
//        valoresComunes.sort(Comparator.naturalOrder());
////        for(int i : valoresComunes) System.out.println(i);
//        
//        if(checkEscaleraComunes(valoresComunes)) return true;
//        
//        int primerValor = getValor(comunes.get(0));
//        for (int i = 0; i < comunes.size()+2; i++) {
//            
//        }
//        
//        return false;
//    }
    
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

    /**
     * @param cartas_mesa the cartas_mesa to set
     */
    public void setCartas_mesa(ArrayList<Carta> cartas_mesa) {
        this.cartas_mesa = cartas_mesa;
    }
}
