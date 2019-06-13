/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

/**
 * Contains the bomb and its defusal methods.
 */
public class Bomb {
    /**
     * Default constructor.
     */
    public Bomb() {}
    
    /**
     * Wrong Cut.
     */
    private void explodes() {
        throw new UnsupportedOperationException("You're Dead.");
    }
    
    /**
     * We compare the actual cut with last one and check whether the bomb explodes or not.
     * @param previousWire
     * @param actualWire
     */
    public void cutWire(String previousWire, String actualWire) {
        switch(previousWire) {
            case "white":
                if(actualWire.matches("white") || actualWire.matches("black")) this.explodes();
                break;
            case "red":
                if(!actualWire.matches("green")) this.explodes();
                break;
            case "black":
                if(actualWire.matches("white") || actualWire.matches("green") || actualWire.matches("orange")) this.explodes();
                break;
            case "orange":
                if(!(actualWire.matches("red") || actualWire.matches("black"))) this.explodes();
                break;
            case "green":
                if(!(actualWire.matches("orange") || actualWire.matches("white"))) this.explodes();
                break;
            case "purple":
                if(!(actualWire.matches("black") || actualWire.matches("red"))) this.explodes();
                break;
            default:
                break; //It'll be done the first time.
        }
    }
}
