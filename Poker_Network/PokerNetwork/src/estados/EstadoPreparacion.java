/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estados;

import juego.Juego;

/**
 * Estado Inicial de un Juego mientras repartimos cartas y preparamos todo.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class EstadoPreparacion implements Estado {

    @Override
    public void cambioFase(Juego juego) {
        juego.setEstado(this);
    }
    
    @Override
    public String toString() {
        return "Preparacion";
    }
}
