package juegos.android.buscaminas.Juego;

import android.widget.ToggleButton;

/**
 * Representacion de una Casilla del Juego. El tablero se compondra por x numero de estas.
 * @since 17/01/2017
 * @author Mario Codes Sánchez
 */
public class Casilla {
    private int[] COORDENADAS = new int[2]; //[EjeX][EjeY].
    private boolean mina = false;
    private boolean flag = false;
    private boolean pulsada = false;
    private int numeroPropio; //Este numero equivale al numero de Minas que hay proximas.
    private ToggleButton boton;

    /**
     * Constructor de Casilla por defecto.
     * @param ejeX Coordenada del Eje X.
     * @param ejeY Coordenada del Eje Y.
     */
    public Casilla(int ejeX, int ejeY) {
        this.getCOORDENADAS()[0] = ejeX;
        this.getCOORDENADAS()[1] = ejeY;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isPulsada() {
        return pulsada;
    }

    public void setPulsada(boolean pulsada) {
        this.pulsada = pulsada;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(this.isMina());
        sb.append(" ");

        return sb.toString();
    }

    public int[] getCOORDENADAS() {
        return COORDENADAS;
    }

    public int getNumeroPropio() {
        return numeroPropio;
    }

    public void setNumeroPropio(int numeroPropio) {
        this.numeroPropio = numeroPropio;
    }

    public ToggleButton getBoton() {
        return boton;
    }

    public void setBoton(ToggleButton boton) {
        this.boton = boton;
    }

    public void setCOORDENADAS(int[] COORDENADAS) {
        this.COORDENADAS = COORDENADAS;
    }
}
