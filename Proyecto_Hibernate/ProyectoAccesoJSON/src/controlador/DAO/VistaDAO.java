/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

package controlador.DAO;

import controlador.DTO.VistaActividadesAlojamientoId;
import java.util.Collection;
 */
/**
 * Representacion Abstracta que contiene el codigo generico para manipular el Acceso a la BDD.
 * @author Mario Codes Sánchez
 * @since 26/01/2017

public abstract class VistaDAO {
    public abstract int altaVista(VistaActividadesAlojamientoId vistaActividades);
    
    public int modificacionVista(VistaActividadesAlojamientoId vistaActividades) {
        throw new UnsupportedOperationException();
    }
    
    public int bajaVista(VistaActividadesAlojamientoId vistaActividades) {
        throw new UnsupportedOperationException();
    }
    
    public VistaActividadesAlojamientoId buscarVistaAlojamientoPorID(int id) {
        throw new UnsupportedOperationException();
    }
    
    public VistaActividadesAlojamientoId buscarVistaActividadPorID(int id) {
        throw new UnsupportedOperationException();
    }
    
    public Collection<VistaActividadesAlojamientoId> buscarVista() {
        throw new UnsupportedOperationException();
    }
    
    public Collection<VistaActividadesAlojamientoId> buscarVista(String campoAFiltrar) {
        throw new UnsupportedOperationException();
    }
}
 */