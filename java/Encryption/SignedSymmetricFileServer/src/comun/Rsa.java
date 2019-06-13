/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author Mario Codes Sánchez
 */
public class Rsa {
    private final BigInteger[] CLAVE_PROPIA_PUBLICA; //Enviarla para que el otro encripte.
    private final BigInteger[] CLAVE_PROPIA_PRIVADA; //Guardarla para desencriptar.
    
    private BigInteger[] CLAVE_AJENA_PUBLICA; //Usarla para encriptar.
    
    private BigInteger n, d, k;
    private int K=0,D=0, N=1;
    
    /**
     * Constructor por defecto, calcula directamente la clave publica y la privada propias a utilizar.
     * @param length BitLength.
     */
    public Rsa(int length) {
        preparacion(length);
        CLAVE_PROPIA_PUBLICA = new BigInteger[] {k, n};
        CLAVE_PROPIA_PRIVADA = new BigInteger[] {d, n};
    }
    
    /**
     * Calculos necesarios para estar listo.
     * @param length Bit length.
     */
    private void preparacion(int length) {
        SecureRandom r = new SecureRandom(); //Calculamos los dos primos p y q
        BigInteger p = new BigInteger(length / 2, 100, r);
        BigInteger q = new BigInteger(length / 2, 100, r);
        //n=p*q, v=(p-1)*(q-1)
        n = p.multiply(q);
        BigInteger v = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        //Calculamos k como el numero impar más pequeño relativamente primo de v
        k = new BigInteger("3");
        while (v.gcd(k).intValue() > 1) {
            k = k.add(new BigInteger("2"));
        }
        d = k.modInverse(v);
    }
    
    public BigInteger firmar(String firma) {
        byte[] bytes = firma.getBytes();
        BigInteger biNormal = new BigInteger(bytes);
        BigInteger biCifrado = biNormal.modPow(CLAVE_PROPIA_PRIVADA[D], CLAVE_PROPIA_PRIVADA[N]);
        return biCifrado;
    }
    
    public String desfirmar(BigInteger cifrado) {
        BigInteger descifrado = cifrado.modPow(CLAVE_AJENA_PUBLICA[K], CLAVE_AJENA_PUBLICA[N]);
        byte[] array = descifrado.toByteArray();
        StringBuilder msg = new StringBuilder();
        for (byte b : array) {
            msg.append((char) b);
        }

        return msg.toString();
    }
    
    /**
     * Pasa el BigInteger normal a cifrado usando la clave publica.
     * @param message
     * @return
     */
    public BigInteger encriptarPropia(BigInteger message){
        return message.modPow(k, n);
    }

    public String encryptMensajes(Rsa rsa, String mensaje, BigInteger[] claveServidor) {
        BigInteger numero = new BigInteger(mensaje.getBytes());
        BigInteger encriptado = rsa.encrypt(numero, claveServidor);

        return encriptado.toString();
    }
        
    /**
     * Pasa el BigInteger de cifrado a normal usando la clave privada.
     * 
     * @param message
     * @return
     */
    public BigInteger desencriptarPropia(BigInteger message) {
        return message.modPow(d, n);
    }

    public BigInteger decrypt(BigInteger message){
        return message.modPow(d, n);
    }
    
        public BigInteger encrypt(BigInteger message, BigInteger[] key)
    {
        return message.modPow(key[K], key[N]);
    }
        
    public byte[] encriptarSimetrico(String claveSimetrica, String mensaje) {
        String clave_aux = new String();
        int controlador = 0;
        while (clave_aux.length() < mensaje.length()) {
            clave_aux += claveSimetrica.charAt(controlador);
            controlador++;
            if (controlador == claveSimetrica.length()) {
                controlador = 0;
            }
        }
        byte[] bytesmensaje = mensaje.getBytes();
        byte[] bytesclave = clave_aux.getBytes();
        for (int i = 0; i < bytesclave.length; i++) {
            bytesmensaje[i] = (byte) (bytesmensaje[i] + (byte) bytesclave[i]);
        }
        return bytesmensaje;
    }
    
    public String desencriptarSimetrico(String claveSimetrica, byte[] mensaje) {
        String clave_aux = "";
        int controlador = 0;
        while (clave_aux.length() < mensaje.length) {
            clave_aux += claveSimetrica.charAt(controlador);
            controlador++;
            if (controlador == claveSimetrica.length()) {
                controlador = 0;
            }
        }
        byte[] bytesmensaje = mensaje;
        byte[] bytesclave = clave_aux.getBytes();
        for (int i = 0; i < bytesclave.length; i++) {
            bytesmensaje[i] = (byte) (bytesmensaje[i] - (byte) bytesclave[i]);
        }
        String mensajeDescifrado = new String(bytesmensaje);
        return mensajeDescifrado;
    }
    
    /**
     * Pasa el BigInteger normal a cifrado usando la clave publica recibida.
     * @param message
     * @return
     */
    public BigInteger encriptar(BigInteger message) {
        return message.modPow(CLAVE_AJENA_PUBLICA[K], CLAVE_AJENA_PUBLICA[N]);
    }

    public BigInteger encriptar(String mensaje) {
        byte[] bytes = mensaje.getBytes();
        BigInteger biNormal = new BigInteger(bytes);
        return biNormal.modPow(CLAVE_AJENA_PUBLICA[K], CLAVE_AJENA_PUBLICA[N]);
    }
    
    public byte[] encriptarBytes(byte[] mensaje) {
        BigInteger biNormal = new BigInteger(mensaje);
        return biNormal.modPow(CLAVE_AJENA_PUBLICA[K], CLAVE_AJENA_PUBLICA[N]).toByteArray();
    }
    
    public String decryptMensaje(String mensaje) {
        BigInteger resultado = decrypt(new BigInteger(mensaje));

        byte[] array = resultado.toByteArray();
        StringBuilder msg = new StringBuilder();
        for (byte b : array) {
            msg.append((char) b);
        }

        return msg.toString();
    }
        
    public byte[] desencriptarBytes(byte[] encriptado) {
        BigInteger biCifrado = new BigInteger(encriptado);
        BigInteger biDescifrado = biCifrado.modPow(CLAVE_PROPIA_PRIVADA[D], CLAVE_PROPIA_PRIVADA[N]);
        return biDescifrado.toByteArray();
    }
    
    public BigInteger desencriptarBI(BigInteger encriptado) {
        BigInteger descifrado = encriptado.modPow(CLAVE_PROPIA_PRIVADA[D], CLAVE_PROPIA_PRIVADA[N]);
        return descifrado;
    }
    
    public String desencriptar(BigInteger encriptado) {
        BigInteger descifrado = encriptado.modPow(CLAVE_PROPIA_PRIVADA[D], CLAVE_PROPIA_PRIVADA[N]);
        byte[] bDescifrados = descifrado.toByteArray();
        String bufferDescifrado = "";
        for(byte b: bDescifrados) {
                bufferDescifrado += (char) b;
        }
        return bufferDescifrado.toString();
    }
    
    /**
     * @return the CLAVE_PROPIA_PUBLICA
     */
    public BigInteger[] getCLAVE_PROPIA_PUBLICA() {
        return CLAVE_PROPIA_PUBLICA;
    }

    /**
     * @return the CLAVE_PROPIA_PRIVADA
     */
    public BigInteger[] getCLAVE_PROPIA_PRIVADA() {
        return CLAVE_PROPIA_PRIVADA;
    }

    /**
     * @return the CLAVE_AJENA_PUBLICA
     */
    public BigInteger[] getCLAVE_AJENA_PUBLICA() {
        return CLAVE_AJENA_PUBLICA;
    }

    /**
     * @param CLAVE_AJENA_PUBLICA the CLAVE_AJENA_PUBLICA to set
     */
    public void setCLAVE_AJENA_PUBLICA(BigInteger[] CLAVE_AJENA_PUBLICA) {
        this.CLAVE_AJENA_PUBLICA = CLAVE_AJENA_PUBLICA;
    }
}
