/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Gestiona lo relacionado con la conexion del programa a las diferentes Bases de Datos. Implementa Patron de disenio Singleton.
 * Patron 'Singleton' - Se tiene un elemento miembro estatico asignado a 'null' desde el comienzo y un metodo igualmente estatico, el
 *  metodo comprueba si el elemento es null, si lo es, lo instancia una unica vez, lo almacena y lo devuelve. A partir de la segunda
 *  vez que se llame, comprueba si es null, como no lo es, simplemente devuelve el elemento miembro que ya se ha instanciado la primera vez
 *  De esta forma solo se tiene una unica instancia de un elemento, que se puede ir pasando a placer a lo largo y ancho del programa.
 * @author Mario Codes Sánchez
 * @since 22/11/2016
 * @version 0.2 - Replanteada la clase entera. Inner Class con un 'Shutdown Hook' para cerrar la conexion a la BDD de manera automatica 
 *  cuando se cierre el programa.
 */
public class DBBConexion {
    private static Connection con = null;
    
    //Clase estatica, no quiero que se puedan realizar instancias.
    private DBBConexion() {}
    
    /**
     * Check rapido para comprobar si esta abierta la conexion a alguna BDD.
     * Lo utilizare a lo largo del programa para comprobar si estoy en la version de JSON o con conexion a BDD.
     * @return True si hay conexion.
     */
    public static boolean checkConexionDBBExiste() {
        return (con != null);
    }
    
    /**
     * Version que se llamara una vez se haya instanciado antes la conexion para obtener siempre la misma instancia.
     * @return Instancia de la conexion previamente creada, lista para usar.
     */
    public static Connection getConexionDBBSingletonPattern() {
        try {
            if(con == null) {
                throw new UnsupportedOperationException("Esta version se debe usar solo cuando la conexion YA HAYA SIDO INSTANCIADA PREVIAMENTE. Utilizar la otra version"
                        + " sobrecargada del metodo.");
            }
            
            return con;
        }catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error al crear la conexion", ex);
        }
    }
    
    /**
     * Se debera llamar esta version del metodo sobrecargado por primera vez, en la ventana de logeo para asignar las variables estaticas. Despues ya se llamara a la otra version para obtener la conexion dentro del programa.
     * Establece la conexion si no existia antes. Devuelve sino la instancia existente.
     * Necesita el fichero 'package'.jdbc.properties para obtener los datos de conexion a utilizar.
     * @param driver Driver a utilizar para la conexion.
     * @param url URL donde esta localizada la BDD y la tabla a acceder.
     * @param usr Usuario a logear.
     * @param pwd Contraseña a utilizar.
     * @return Connection. Instancia actual de la conexion para realizar operaciones.
     */
    public static Connection getConexionDBBSingletonPattern(String driver, String url, String usr, String pwd) {
        try {
            if(con == null) { //Aqui solo se entrara la primera vez; cuando no este instanciada.
                Runtime.getRuntime().addShutdownHook(new MiShutdownHook()); //Determina cuando finalice el programa para cerrar conexion.
                
                Class.forName(driver); //Cargamos el driver.
                con = DriverManager.getConnection(url, usr, pwd); //Creo la conexion.
                
                System.out.println("¡Conexion Establecida!");
            }
            
            return con;
        }catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage()); //Informacion al usuario del fallo.
            return con;
        }
    }

    /**
     * Aunque se cierra automaticamente al acabar el programa con un 'Shutdown Hook', si cambio la ventana de modo de ejecucion, la quiero cerrar a mano.
     */
    public static void cerrarConexionBDDSingletonAMano() {
        try {
            if(DBBConexion.getCon() != null) { //Que solo la intente cerrar cuando ha sido abierta.
                Connection con = DBBConexion.getConexionDBBSingletonPattern();
                con.close();
                DBBConexion.setCon(null);
                System.out.println("¡Conexion Cerrada!");
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * @return the con
     */
    public static Connection getCon() {
        return con;
    }

    /**
     * @param aCon the con to set
     */
    public static void setCon(Connection aCon) {
        con = aCon;
    }

    /**
     * Inner class separada que extiende de Thread. La utilizare para meter un shutdown hook que cierre la conexion a la BDD cuando se cierre el programa (siempre que esta se encuentre abierta, claro).
     * La pongo como inner class ya que depende totalmente de esta, no tiene sentido que exista por si misma.
     */
    private static class MiShutdownHook extends Thread {
        /**
         * Si la conexion ha sido instanciada, se cierra.
         * @throws SQLException
         */
        private void cerrarConexionBDDFinalizacionPrograma() throws SQLException {
            if(DBBConexion.getCon() != null) { //Solo la intente cerrar cuando ha sido abierta.
                Connection con = DBBConexion.getConexionDBBSingletonPattern();
                con.close();
                System.out.println("¡Conexion Cerrada!");
            }
        }

        @Override
        public void run() { //Justo antes de finalizar el programa, la JVM invocara a este metodo donde obtiene la conexion abierta y la cierra.
            try {
                cerrarConexionBDDFinalizacionPrograma();
            }catch(Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
}