/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldilocksbear;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Challenge #291 [Easy].
 * @author Mario Codes Sánchez
 * @since 03/12/0216
 * @see https://www.reddit.com/r/dailyprogrammer/comments/5bn0b7/20161107_challenge_291_easy_goldilocks_bear/
 * @version 1.0 Challenge done.
 */
public class GoldilocksBear {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] buffer;
        int goldilocksWeight, maxTemp;
        int countPosition = 1;
        ArrayList<Integer> positions = new ArrayList<>();
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input: ");
        buffer = scanner.nextLine().split(" ");
        goldilocksWeight = Integer.parseInt(buffer[0]);
        maxTemp = Integer.parseInt(buffer[1]);
        
        while(!buffer[0].isEmpty()) {
            buffer = scanner.nextLine().split(" ");
            if(!buffer[0].isEmpty() && (goldilocksWeight < Integer.parseInt(buffer[0]) && maxTemp > Integer.parseInt(buffer[1]))) positions.add(countPosition); //First '&&' check as I don't want to add a try-catch so it does not break.
            countPosition++;
        }
        
        System.out.print("Valid seats are: #");
        for(int entero: positions) {
            System.out.print(entero +" ");
        }
    }
    
}
