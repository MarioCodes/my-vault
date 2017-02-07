/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estados;

import juego.Juego;

/**
 * Fase de juego de poner Ciegas, hacer draw de cartas comunes y repartir a los Jugadores.
 * @author Mario Codes Sánchez
 * @since 07/02/2017
 */
public class EstadoCiegas implements Estado{

    @Override
    public void cambioFase(Juego juego) {
        juego.setEstado(this);
    }
    
    @Override
    public String toString() {
        return "Ciegas";
    }
}
