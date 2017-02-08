/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estados;

import pokercliente.Jugador;

/**
 * Implementacion de una maquina de estados para saber en todo momento que Jugador tiene el 'Focus' para hablar en la partida y cual debe esperar.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 * @see https://en.wikipedia.org/wiki/State_pattern
 * @see https://www.tutorialspoint.com/design_pattern/state_pattern.htm
 */
public interface Estado {
    public void cambioEstado(Jugador jugador);
    
    public int apostar(int fichas);
}
