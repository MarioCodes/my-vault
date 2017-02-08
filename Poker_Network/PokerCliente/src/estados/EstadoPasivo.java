/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estados;

import pokercliente.Conexion;
import pokercliente.Jugador;

/**
 * Estado Pasivo. Hay otro jugador que tiene el activo.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 */
public class EstadoPasivo implements Estado{

    @Override
    public void cambioEstado(Jugador jugador) {
        jugador.setEstado(this);
    }
    
    @Override
    public int apostar(int fichas) {
        System.out.println("Espera por Favor. No tienes el turno de Hablar."); //todo: implementar algun cambio en la interfaz grafica cuando este hecha.
        return Conexion.getPoolFichasApostadas();
    }
    
    @Override
    public String toString() {
        return "Pasivo";
    }
}
