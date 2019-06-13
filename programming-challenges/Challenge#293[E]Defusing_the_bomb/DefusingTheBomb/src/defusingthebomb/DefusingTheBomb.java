/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package defusingthebomb;

import controlador.aplicacion.Bomb;
import java.util.Scanner;

/**
 * Solution to 'Defusing the Bomb problem'.
 * @author Mario Codes Sánchez.
 * @since 03/12/2016
 * @see https://www.reddit.com/r/dailyprogrammer/comments/5e4mde/20161121_challenge_293_easy_defusing_the_bomb/
 * @version 2.0 Game redone. Better done and less complicated now. Now it truly 'explodes'.
 */
public class DefusingTheBomb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        game(new Bomb());
    }
    
    public static void game(Bomb b) {
        System.out.println("Colors: white, red, black, orange, green, purple.\n");
        String input, previousInput = "";
        
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter color to cut (enter to end): ");
            b.cutWire(previousInput, input = scanner.nextLine());
            previousInput = input;
        }while(!input.matches(""));
        
        System.out.println("Bomb defused.");
    }
}
