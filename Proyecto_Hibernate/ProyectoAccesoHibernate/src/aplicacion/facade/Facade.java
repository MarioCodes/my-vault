/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.facade;

import dto.HibernateUtil;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Patron de Diseño Façade. Modelo Vista - Controlador. Clase Intermediaria entre ambas para mantener separado el la ejecucion de codigo.
 * @author Mario Codes Sánchez
 * @since 26/11/2016
 */
public class Facade {
    /**
     * Abre la Session de Hibernate y la devuelve para manipular los datos 
     * @return Session abierta.
     */
    public static Session abrirSessionHibernate() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        return s;
    }
    
    /**
     * Hacemos Commit y cerramos la Session.
     * @param s session abierta y con datos manipulados.
     * @return Estado de la transaccion. True si se completa sin problemas.
     */
    public static boolean cerrarSessionHibernate(Session s) {
        try {
            s.getTransaction().commit();
            s.close();
            return true; 
        }catch(ConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Problema de Violacion de Clave.");
            return false;
        }
    }
}
