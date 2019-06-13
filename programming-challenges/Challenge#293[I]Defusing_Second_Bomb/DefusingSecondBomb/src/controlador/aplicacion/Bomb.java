/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.aplicacion;

/**
 * Bomb representation
 * @author Mario Codes Sánchez
 * @since 03/12/2016
 */
public class Bomb {
    private boolean live = true;
    
    public Bomb() {}
    
    /**
     * Cut of a Wire.
     * POSSIBLE COLORS -> ("white", "black", "red", "orange", "green")
     * @param actual wire we're trying to cut.
     * @param previous last wire we cutted and didn't die.
     * @param ante pre-previous(?) wire we cutted. Needed just for green wire.
     * @return Checks whether we have reestarted the game.
     */
    public boolean cutWire(String actual, String previous, String ante) {
        switch(actual) {
            case "white":
                if(!(previous.matches("") || previous.matches("white"))) this.explodes();
                break;
            case "black":
                if(previous.matches("") || previous.matches("green") ||(previous.matches("white") && ante.matches(""))) this.explodes();
                break;
            case "red":
                if(!(previous.matches("red") || previous.matches("") || previous.matches("white"))) this.explodes();
                if(previous.matches("red") || previous.matches("white")) return true;
                break;
            case "orange":
                if(!(previous.matches("white") || previous.matches("orange") || previous.matches("black") || previous.matches("green"))) this.explodes();
                if(previous.matches("green")) this.defused();
                break;
            case "green":
                if(!(previous.matches("black") || previous.matches("orange"))) this.explodes();
                if(previous.matches("orange") && (ante.matches("black") || ante.matches("orange"))) this.defused(); //Here there's a problem, as when we cut a green one, it may come from an orange one and the bomb should not be defused. So we need to check the previosu one to the orange.
                break;
            default:
                this.explodes();
                break;
        }
        
        return false;
    }
    
    /**
     * Wrong Cut.
     */
    public void explodes() {
        throw new UnsupportedOperationException("You're Dead.");
    }
    
    /**
     * Problem solved correctly.
     */
    public void defused() {
        this.live = false;
    }
    
    /**
     * @return the live
     */
    public boolean isLive() {
        return live;
    }
}
