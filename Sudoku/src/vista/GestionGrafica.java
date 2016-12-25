/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase recopilacion de metodos para gestion de la parte grafica del programa.
 *  Necesito descargar un poco la ventana principal y dejarla mas solo grafica y menos logica.
 * @author Mario Codes Sánchez
 * @since 25/12/2016
 */
public class GestionGrafica {
    /**
     * Scanner para pedir Integer.
     * @param output Output que se mostrarara al user.
     * @return Integer metido por el usuario.
     */
    static int askInteger(String output) {
        Scanner scanner;
        int number = -1;
        do {
            try {
                scanner = new Scanner(System.in);
                System.out.print(output);
                number = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Dato no valido.");
            }
        } while (number == -1);
        return number;
    }
    
}
