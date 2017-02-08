/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fases;

import juego.Juego;

/**
 * Implementacion de una maquina de estados para saber en todo momento en que fase se encuentra el Juego.
 * De esta forma tengo control total sobre lo que se puede y lo que no se puede hacer en cada fase del juego.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 * @see https://en.wikipedia.org/wiki/State_pattern
 * @see https://www.tutorialspoint.com/design_pattern/state_pattern.htm
 */
public interface Fase {
    public void cambioFase(Juego juego);
    
    public void repartoCartasPersonales();
    
    public void repartoCartasComunes();
    
    public void apostarCiegas();
    
    public void apostar();
    
    public void retirarse();
}
