/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controlador.tablero;

/**
 * Representacion de una casilla.
 * @author Mario Codes Sánchez
 * @since 12/12/2016
 */
public class Casilla {
    private int numeroPropio;
    private final int NUMERO_CUADRADO;
    private final int NUMERO_FILA;
    private final int NUMERO_COLUMNA;
    private boolean casillaFija;
    
    /**
     * Constructor final a utilizar. Empieza con los numeros a 0 y se asigna a cada Casilla despues.
     * @param numero_cuadrado Numero del cuadrado segun el plano, al que pertenece esta casilla.
     * @param numero_fila Numero de la fila.
     * @param numero_columna Numero de la columna.
     */
    public Casilla(int numero_cuadrado, int numero_fila, int numero_columna) {
        this.numeroPropio = 0;
        this.NUMERO_CUADRADO = numero_cuadrado;
        this.NUMERO_FILA = numero_fila;
        this.NUMERO_COLUMNA = numero_columna;
        this.casillaFija = true;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.numeroPropio);
        return sb.toString();
    }

    /**
     * @return the NUMERO_CUADRADO
     */
    public int getNUMERO_CUADRADO() {
        return NUMERO_CUADRADO;
    }

    /**
     * @return the NUMERO_FILA
     */
    public int getNUMERO_FILA() {
        return NUMERO_FILA;
    }

    /**
     * @return the NUMERO_COLUMNA
     */
    public int getNUMERO_COLUMNA() {
        return NUMERO_COLUMNA;
    }

    /**
     * @param casillaFija the casillaFija to set
     */
    public void setCasillaFija(boolean casillaFija) {
        this.casillaFija = casillaFija;
    }

    /**
     * @param numeroPropio the numeroPropio to set
     */
    public void setNumeroPropio(int numeroPropio) {
        this.numeroPropio = numeroPropio;
    }

    /**
     * @return the numeroPropio
     */
    public int getNumeroPropio() {
        return numeroPropio;
    }

    /**
     * @return the casillaFija
     */
    public boolean isCasillaFija() {
        return casillaFija;
    }
}
