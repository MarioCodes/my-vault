/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.DAO;

import controlador.DTO.HabitacionDTO;
import controlador.datos.DBBConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Clase DAO (Data Access Object). Encapsulacion de los metodos relacionados con acceso a la base de datos.
 * Clase Abstracta, solucion para resolver el problema de poder conectar a diferentes BDD, pudiendo asi usar SQL propietario.
 * Las consultas que van a ser iguales para todos, las resuelvo aqui. En las hijas solo las que tengan SQL propietario.
 * @author Mario Codes Sánchez
 * @since 26/11/2016
 */
public abstract class HabitacionDAO {
    public abstract int altaHabitacion(HabitacionDTO habDTO); //Sera la unica que varia entre las BDD, el resto las resuelvo aqui.
    
    /**
     * Update de una Habitación con los nuevos de HabitacionDTO que paso como parametro.
     * @param habDTO HabitacionDTO de donde se obtienen los datos a Updatear.
     * @return Número de filas modificadas.
     */
    public int modificacionHabitacion(HabitacionDTO habDTO) {
        Connection con = null;
        PreparedStatement pstm = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern();
            String sql = "UPDATE HABITACION SET "
                    +"EXTRAS_HABITACION = ?, PRECIO = ?, CUARTO_BAÑO = ?, TIPO_HABITACION = ?, RESEÑAS = ?, ALOJAMIENTO_ID_ALOJAMIENTO = ? "
                    + "WHERE ID_HABITACION = ?";
            
            pstm = con.prepareStatement(sql);
            
            //Añadido de datos al preparedStatement.
            
            pstm.setString(1, habDTO.getExtras_habitacion());
            pstm.setFloat(2, habDTO.getPrecio());
            pstm.setBoolean(3, habDTO.isCuarto_banio());
            pstm.setString(4, habDTO.getTipo_habitacion());
            pstm.setString(5, habDTO.getResenias());
            pstm.setInt(6, habDTO.getHabitacion_id_foranea_alojamiento());
            pstm.setInt(7, habDTO.getId_habitacion());
            
            return pstm.executeUpdate();
        }catch(SQLException ex) {
            return -1;
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
     * Borra la Habitación que se le pasa como parametro. Antes se ha hecho una busqueda de Habitacion por ID, si esta no existe, aqui se pasara una Habitacion null.
     * @param habDTO HabitacionDTO del cual se saca la ID.
     * @return Numero de filas modificadas. 0 malo, 1 correcto, +1 problemas.
     */
    public int bajaHabitacion(HabitacionDTO habDTO) {
        Connection con = null;
        PreparedStatement pstm = null;
        
        int resultado = 0;
        
        if(habDTO != null) {
            int id_habitacion = habDTO.getId_habitacion();

            try {
                con = DBBConexion.getConexionDBBSingletonPattern();

                String sql = "DELETE FROM HABITACION WHERE "
                        +"ID_HABITACION = ?";

                pstm = con.prepareStatement(sql);

                pstm.setInt(1, id_habitacion);

                resultado = pstm.executeUpdate();
                return resultado;
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
        
        return resultado;
    }
    
    /**
     * Busca una Habitacion por numero de ID.
     * @param id ID de la habitacion a obtener.
     * @return HabitacionDTO de la Habitacion correspondiente.
     */
    public HabitacionDTO buscarHabitacionPorID(int id) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern();
            String sql = "SELECT * FROM HABITACION "
                    + "WHERE ID_HABITACION = ?";
            
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id); //Hacemos un set del primer '?'.
            
            rs = pstm.executeQuery();
            
            HabitacionDTO habDTOTmp = null;
            
            while(rs.next()) {
                habDTOTmp = new HabitacionDTO();
                habDTOTmp.setId_habitacion(rs.getInt("ID_HABITACION"));
                habDTOTmp.setExtras_habitacion(rs.getString("EXTRAS_HABITACION"));
                habDTOTmp.setPrecio(rs.getInt("PRECIO"));
                habDTOTmp.setCuarto_banio(rs.getBoolean("CUARTO_BAÑO"));
                habDTOTmp.setTipo_habitacion(rs.getString("TIPO_HABITACION"));
                habDTOTmp.setResenias(rs.getString("RESEÑAS"));
                habDTOTmp.setHabitacion_id_foranea_alojamiento(rs.getInt("ALOJAMIENTO_ID_ALOJAMIENTO"));
            }            
            
            return habDTOTmp;
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
     * Metodo Sobrecargado. Version sin filtrar.
     * Obtenemos una coleccion conteniendo todas las habitaciones existentes en la BDD.
     * @return Collection conteniendo todos los HabitacionDTO de las Habitaciones existentes en BDD.
     */
    public Collection<HabitacionDTO> buscarHabitaciones() {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern();
            String sql = "SELECT ID_HABITACION, ALOJAMIENTO_ID_ALOJAMIENTO, PRECIO, TIPO_HABITACION, CUARTO_BAÑO, EXTRAS_HABITACION, RESEÑAS "
                    + "FROM HABITACION "
                    + "ORDER BY ID_HABITACION";
            
            pstm = con.prepareStatement(sql);

            rs = pstm.executeQuery();
            
            ArrayList<HabitacionDTO> arrayListResHabitaciones = new ArrayList<>();
            HabitacionDTO habDTOTmp;
            
            while(rs.next()) {
                habDTOTmp = new HabitacionDTO();
                habDTOTmp.setId_habitacion(rs.getInt("ID_HABITACION"));
                habDTOTmp.setExtras_habitacion(rs.getString("EXTRAS_HABITACION"));
                habDTOTmp.setPrecio(rs.getInt("PRECIO"));
                habDTOTmp.setCuarto_banio(rs.getBoolean("CUARTO_BAÑO"));
                habDTOTmp.setTipo_habitacion(rs.getString("TIPO_HABITACION"));
                habDTOTmp.setResenias(rs.getString("RESEÑAS"));
                habDTOTmp.setHabitacion_id_foranea_alojamiento(rs.getInt("ALOJAMIENTO_ID_ALOJAMIENTO"));
                arrayListResHabitaciones.add(habDTOTmp);
            }
            
            return arrayListResHabitaciones;
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
     * Metodo Sobrecargado. Version filtrar habitaciones por precio_maximo.
     * Obtenemos una coleccion conteniendo todas las habitaciones con un precio menor o igual al señalado.
     * @param precio_max Precio max para filtrar.
     * @return Collection conteniendo todos los HabitacionDTO con precio menor o igual.
     */
    public Collection<HabitacionDTO> buscarHabitaciones(float precio_max) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern();
            String sql = "SELECT ID_HABITACION, ALOJAMIENTO_ID_ALOJAMIENTO, PRECIO, TIPO_HABITACION, CUARTO_BAÑO, EXTRAS_HABITACION, RESEÑAS "
                    + "FROM HABITACION "
                    + "WHERE PRECIO <= ? ORDER BY PRECIO";
            
            pstm = con.prepareStatement(sql);
            pstm.setFloat(1, precio_max);

            rs = pstm.executeQuery();
            
            ArrayList<HabitacionDTO> arrayListResHabitaciones = new ArrayList<>();
            HabitacionDTO habDTOTmp;
            
            while(rs.next()) {
                habDTOTmp = new HabitacionDTO();
                habDTOTmp.setId_habitacion(rs.getInt("ID_HABITACION"));
                habDTOTmp.setExtras_habitacion(rs.getString("EXTRAS_HABITACION"));
                habDTOTmp.setPrecio(rs.getInt("PRECIO"));
                habDTOTmp.setCuarto_banio(rs.getBoolean("CUARTO_BAÑO"));
                habDTOTmp.setTipo_habitacion(rs.getString("TIPO_HABITACION"));
                habDTOTmp.setResenias(rs.getString("RESEÑAS"));
                habDTOTmp.setHabitacion_id_foranea_alojamiento(rs.getInt("ALOJAMIENTO_ID_ALOJAMIENTO"));
                arrayListResHabitaciones.add(habDTOTmp);
            }
            
            return arrayListResHabitaciones;
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
}
