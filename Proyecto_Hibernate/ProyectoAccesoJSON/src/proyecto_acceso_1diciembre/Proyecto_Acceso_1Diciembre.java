/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_acceso_1diciembre;

import entidades.VistaActividadesAlojamiento;
import entidades.VistaActividadesAlojamientoId;
import hibernate.HibernateUtil;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import vista.swing.comun.VentanaLoginBDD;

/**
 * Tercer Proyecto para Angel (Acceso a Datos). Hibernate.
 * @author Mario Codes Sánchez
 * @since 26/01/2017
 * @version 0.1 Haciendo Limpieza de Codigo.
 */
public class Proyecto_Acceso_1Diciembre {
    
    /*
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        new VentanaLoginBDD();
//        SingletonVentanas.getVentanaPrincipalObtencionSingleton();
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        VistaActividadesAlojamientoId vaid = new VistaActividadesAlojamientoId();
        vaid.setIdAlojamiento(62);
        vaid.setIdActividad(62);
        vaid.setNombreAlojamiento("SUUU");
        vaid.setDescripcionAlojamiento("SUU");
        vaid.setDireccionSocial("SUU");
        vaid.setRazonSocial("SUUU");
        vaid.setTelefonoContacto("123");
        vaid.setValoracionAlojamiento(8);
        vaid.setFechaApertura("12");
        vaid.setNumeroHabitaciones(1);
        vaid.setProvincia("Huesca");
        vaid.setNombreActividad("Kayak");
        vaid.setDescripcionActividad("TEST");
        vaid.setDiaRealizacion("12");
        vaid.setDiaSemana("Lunes");
        vaid.setHoraInicio("8:10");
        vaid.setHoraFin("10:10");
        vaid.setLocalizacion("Barbastro");
        vaid.setDificultad(3);
        vaid.setCapacidad("12");
        vaid.setNombreGuia("Manolo");
        
        VistaActividadesAlojamiento va = new VistaActividadesAlojamiento();
        va.setId(vaid);
        
        s.save(va);
        s.getTransaction().commit();
    }
}