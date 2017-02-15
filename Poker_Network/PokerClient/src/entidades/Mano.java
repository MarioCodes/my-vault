/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.ArrayList;

/**
 * Mano del Jugador, se compone de sus 2 cartas personales y las 3 mas altas de las 5 de la mesa.
 * @author Mario Codes Sánchez
 * @since 15/02/2017
 */
public class Mano {
    private ArrayList<Carta> cartas_propias = new ArrayList<>();
    private ArrayList<Carta> cartas_mesa = new ArrayList<>();
    
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
                case "K":
                    valor = 13;
                case "Q":
                    valor = 12;
                case "J":
                    valor = 11;
            }
        }
        
        return valor;
    }
    
    private ArrayList<Carta> compararCartas(ArrayList<Carta> propias, ArrayList<Carta> comunes) {
        ArrayList<Carta> mejorCombinacion = new ArrayList<>();
        
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
