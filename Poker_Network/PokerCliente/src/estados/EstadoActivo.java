/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estados;

import pokercliente.Conexion;
import pokercliente.Jugador;

/**
 * Estado Activo. El jugador tiene el focus para realizar una accion en la partida.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class EstadoActivo implements Estado {

    @Override
    public void cambioEstado(Jugador jugador) {
        jugador.setEstado(this);
    }
    
    @Override
    public int apostar(int fichas) {
        int totalPool = Conexion.apostarJugador(fichas);
        System.out.println("Apuesta de " +fichas +" fichas realizada.");
        return totalPool;
    }
    
    @Override
    public String toString() {
        return "Activo";
    }
}
