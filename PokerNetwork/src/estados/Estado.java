/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estados;

import juego.Juego;

/**
 * Implementacion de una maquina de estados para saber en todo momento en que fase se encuentra el Juego.
 * @author Mario Codes Sánchez
 * @since 07/02/2017
 * @see https://en.wikipedia.org/wiki/State_pattern
 * @see https://www.tutorialspoint.com/design_pattern/state_pattern.htm
 */
public interface Estado {
    public void cambioFase(Juego juego);
}
