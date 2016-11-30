/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.DAO;

import controlador.DTO.AlojamientoDTO;
import controlador.datos.DBBConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Clase DAO (Data Access Object). Patron utilizado para encapsular los metodos relacionados con el acceso a la Base de Datos.
 * Clase Abstracta de donde sacaremos las hijas especializadas, en funcion de la BDD a la que conectemos. Asi se podran
 *      usar sentencias SQL propietarias.
 * Solo hago Abstractas las que tocan el ID, ya que cambiara en funcion de MySQL (Autoincremental) o Oracle XE (Secuencia).
 *  El resto usara ANSI SQL por lo que lo resuelvo en la propia case Abstracta.
 * Los metodos tienen que ser 'public' y no 'protected' porque en el momento de la instanciacion, invocare a las hijas como
 *  AlojamientoDAO (el padre), de esa forma me dara igual que tipo de subhija sea o que conexion a que BDD haya por detras,
 *  que yo no tendre que modificar el codigo.
 * @author Mario Codes Sánchez
 * @since 26/11/2016
 */
public abstract class AlojamientoDAO {
    public abstract int altaAlojamiento(AlojamientoDTO alDTO); //A implementar en la clase que hereda, son las que utilizan SQL propietario de la BDD.
    
    /**
     * Hace un Update de un Alojamiento previamente creado, le mete los datos del AlojamientoDTO que paso como parametro.
     * @param alDTO AlojamientoDTO cuyos datos se obtienen a traves de la pantalla de modificacion.
     * @return Cantidad de filas modificadas.
     */
    public int modificacionAlojamiento(AlojamientoDTO alDTO) {
        Connection con = null;
        PreparedStatement pstm = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern();
            String sql = "UPDATE ALOJAMIENTO SET "
                    +"NOMBRE = ?, DIRECCION_SOCIAL = ?, RAZON_SOCIAL = ?, TELEFONO_CONTACTO = ?, DESCRIPCION = ?, VALORACION = ?, FECHA_APERTURA = ?,"
                    + " NUM_HABITACIONES = ?, PROVINCIA = ? "
                    + "WHERE ID_ALOJAMIENTO = ?";
            
            pstm = con.prepareStatement(sql);
            
            pstm.setString(1, alDTO.getNombre());
            pstm.setString(2, alDTO.getDir_Social());
            pstm.setString(3, alDTO.getRazon_Social());
            pstm.setString(4, alDTO.getTelefono_Contacto());
            pstm.setString(5, alDTO.getDescripcion());
            pstm.setInt(6, alDTO.getValoracion());
            pstm.setString(7, alDTO.getFecha_Apertura());
            pstm.setInt(8, alDTO.getNumero_Habitaciones());
            pstm.setString(9, alDTO.getProvincia());
            pstm.setInt(10, alDTO.getId());

            return pstm.executeUpdate();
        }catch(SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }finally {
            try {
                if(pstm != null) pstm.close();
            }catch(Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
    
    /**
     * Damos de Baja un Alojamiento de la BDD.
     * @param alDTO AlojamientoDTO que se quiere dar de baja.
     * @return Resultado de la operacion. #Filas modificadas.
     */
    public int bajaAlojamiento(AlojamientoDTO alDTO) {
        int id_alojamiento = alDTO.getId();
        String nombre = alDTO.getNombre();
        
        Connection con = null;
        PreparedStatement pstm = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern();
            
            String sql = "DELETE FROM ALOJAMIENTO WHERE "
                    +"ID_ALOJAMIENTO = ? "
                    +"AND NOMBRE LIKE ?";
            
            pstm = con.prepareStatement(sql);
            
            pstm.setInt(1, id_alojamiento);
            pstm.setString(2, nombre);
            
            return pstm.executeUpdate();
        }catch(SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }finally {
            try {
                if(pstm != null) pstm.close();
            }catch(Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
    
    /**
     * Buscar Alojamiento con ID. Devuelve el AlojamientoDTO deseado.
     * @param id ID del Alojamiento que nos interesa obtener.
     * @return AlojamientoDTO deseado instanciado.
     */
    public AlojamientoDTO buscarAlojamientoPorID(int id) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern();
            String sql = "SELECT * FROM ALOJAMIENTO "
                    + "WHERE ID_ALOJAMIENTO = ?";
            
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id); //Hacemos un set del primer '?'.
            
            rs = pstm.executeQuery();
            
            AlojamientoDTO alDTOTmp = null;
            
            while(rs.next()) {
                alDTOTmp = new AlojamientoDTO();
                alDTOTmp.setId(rs.getInt("ID_ALOJAMIENTO"));
                alDTOTmp.setNombre(rs.getString("NOMBRE"));
                alDTOTmp.setDir_Social(rs.getString("DIRECCION_SOCIAL"));
                alDTOTmp.setRazon_Social(rs.getString("RAZON_SOCIAL"));
                alDTOTmp.setTelefono_Contacto(rs.getString("TELEFONO_CONTACTO"));
                alDTOTmp.setDescripcion(rs.getString("DESCRIPCION"));
                alDTOTmp.setValoracion(rs.getInt("VALORACION"));
                alDTOTmp.setFecha_Apertura(rs.getString("FECHA_APERTURA"));
                alDTOTmp.setNumero_Habitaciones(rs.getInt("NUM_HABITACIONES"));
                alDTOTmp.setProvincia(rs.getString("PROVINCIA"));
            }            
            
            return alDTOTmp;
        }catch(SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }finally {
            try {
                if(rs != null) rs.close();
                if(pstm != null) pstm.close();
            }catch(Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
    
    /**
     * Metodo Sobrecargado. Version sin filtrar. Devuelve TODOS los alojamientos existentes en la BDD.
     * Ejecuta Query y devuelve Collection con todos los Alojamientos existentes.
     * @return Collection de AlojamientoDTO que contiene todos los registros existentes en la BDD.
     */
    public Collection<AlojamientoDTO> buscarAlojamientos() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern(); 
            String sql = "SELECT * FROM ALOJAMIENTO"; 
            
            pstm = con.prepareStatement(sql); 
            
            rs = pstm.executeQuery(); 
            
            ArrayList<AlojamientoDTO> arrayListResultados = new ArrayList<>(); //Coleccion que vamos a devolver.
            AlojamientoDTO alDTOTmp = null; //AlojamientoDTO donde meteremos los datos de forma TMP.
            
            while(rs.next()) { //Sacamos los datos de cada Alojamiento y los metemos en el AlojamientoTmp.
                alDTOTmp = new AlojamientoDTO();
                alDTOTmp.setId(rs.getInt("ID_ALOJAMIENTO"));
                alDTOTmp.setNombre(rs.getString("NOMBRE"));
                alDTOTmp.setDir_Social(rs.getString("DIRECCION_SOCIAL"));
                alDTOTmp.setRazon_Social(rs.getString("RAZON_SOCIAL"));
                alDTOTmp.setTelefono_Contacto(rs.getString("TELEFONO_CONTACTO"));
                alDTOTmp.setDescripcion(rs.getString("DESCRIPCION"));
                alDTOTmp.setValoracion(rs.getInt("VALORACION"));
                alDTOTmp.setFecha_Apertura(rs.getString("FECHA_APERTURA"));
                alDTOTmp.setNumero_Habitaciones(rs.getInt("NUM_HABITACIONES"));
                alDTOTmp.setProvincia(rs.getString("PROVINCIA"));
                arrayListResultados.add(alDTOTmp); //Añadimos el AlojamientoDTO con todos sus datos en la Collection a devolver.
            }
            
            return arrayListResultados;
        }catch(SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }finally {
            try {
                if(rs != null) rs.close();
                if(pstm != null) pstm.close();
            }catch(Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
    
    /**
     * Metodo Sobrecargado. Version filtrar por Provincia.
     * Ejecuta Query y devuelve Collection con todos los 'Alojamientos' existentes que esten localizados en la provincia indicada.
     * @param provincia Provincia del Alojamiento por la cual se filtra.
     * @return Collection de AlojamientoDTO. Contiene todos los registros filtrados de la BDD.
     */
    public Collection<AlojamientoDTO> buscarAlojamientos(String provincia) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern(); //Obtenemos la conexion con Singleton Pattern.
            String sql = "SELECT * FROM ALOJAMIENTO "
                    + "WHERE Provincia LIKE ?"; //SQL a ejecutar.
            
            pstm = con.prepareStatement(sql); //Lo preparamos para evitar inyecciones de codigo.
            pstm.setString(1, provincia);
            
            rs = pstm.executeQuery(); //Ejecutamos la sentencia y metemos el resultado en un ResultSet.
            
            ArrayList<AlojamientoDTO> arrayListResultados = new ArrayList<>(); //Coleccion que vamos a devolver.
            AlojamientoDTO alDTOTmp = null; //AlojamientoDTO donde meteremos los datos de forma TMP.
            
            while(rs.next()) { //Sacamos los datos de cada Alojamiento y los metemos en el AlojamientoTmp.
                alDTOTmp = new AlojamientoDTO();
                alDTOTmp.setId(rs.getInt("ID_ALOJAMIENTO"));
                alDTOTmp.setNombre(rs.getString("NOMBRE"));
                alDTOTmp.setDir_Social(rs.getString("DIRECCION_SOCIAL"));
                alDTOTmp.setRazon_Social(rs.getString("RAZON_SOCIAL"));
                alDTOTmp.setTelefono_Contacto(rs.getString("TELEFONO_CONTACTO"));
                alDTOTmp.setDescripcion(rs.getString("DESCRIPCION"));
                alDTOTmp.setValoracion(rs.getInt("VALORACION"));
                alDTOTmp.setFecha_Apertura(rs.getString("FECHA_APERTURA"));
                alDTOTmp.setNumero_Habitaciones(rs.getInt("NUM_HABITACIONES"));
                alDTOTmp.setProvincia(rs.getString("PROVINCIA"));
                arrayListResultados.add(alDTOTmp); //Añadimos el AlojaientoDTO con todos sus datos en la Collection a devolver.
            }
            
            return arrayListResultados;
        }catch(SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }finally {
            try {
                if(rs != null) rs.close();
                if(pstm != null) pstm.close();
            }catch(Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
}
