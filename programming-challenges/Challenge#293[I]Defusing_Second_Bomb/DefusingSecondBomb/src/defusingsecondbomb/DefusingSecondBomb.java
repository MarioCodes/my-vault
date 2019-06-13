/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package defusingsecondbomb;

import controlador.aplicacion.Bomb;
import java.util.Scanner;

/**
 * Intermediate Challenge #293. Defusing the second bomb.
 * @author Mario Codes Sánchez
 * @since 03/12/2016
 * @see https://www.reddit.com/r/dailyprogrammer/comments/5emuuy/20161124_challenge_293_intermediate_defusing_the/
 * @version 1.0 Challenge Solved without bonus. 
 */
public class DefusingSecondBomb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        game(new Bomb());
    }
    
    public static void game(Bomb b) {
        Scanner scanner = new Scanner(System.in);
        String actualCut, previousCut = "", antePreviousCut = "";
        
        System.out.println("Colors: white, black, red, orange, green.");
        do {
            System.out.print("Insert wire to cut: ");
            if(b.cutWire(actualCut = scanner.nextLine(), previousCut, antePreviousCut)) {
                previousCut = "";
                antePreviousCut = "";
            } else {
                antePreviousCut = previousCut;
                previousCut = actualCut;
            }
        }while(b.isLive());
        
        System.out.println("Bomb Defused.");
    }
}
