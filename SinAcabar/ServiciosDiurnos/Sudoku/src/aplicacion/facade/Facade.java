/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.facade;

/**
 * Patron de disenio Facade. Sirve de intermediario entre vista y controlador del programa.
 * @author Mario Codes Sánchez
 * @since 12/12/2016
 */
public class Facade {
    /**
     * Generacion del tablero mediante singleton pattern.
     */
    public void generacionTablero() {
        System.out.println(ContenedorSingletton.getTableroSingleton());
    }
}
