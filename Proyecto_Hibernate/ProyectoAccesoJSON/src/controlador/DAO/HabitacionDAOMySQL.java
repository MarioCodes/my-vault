/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.DAO;

import controlador.DTO.HabitacionDTO;
import controlador.datos.DBBConexion;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implementacion de la clase Abstracta DAO para realizar operaciones en MySQL. Utiliza SQL Propietario (AutoIncrementales).
 * El resto de metodos necesarios los hereda del padre.
 * @author Mario Codes Sánchez
 * @since 26/11/2016
 */
public class HabitacionDAOMySQL extends HabitacionDAO{
    /**
     * Insert con datos de HabitacionDTO que se le ha pasado. Cierra lo abierto.
     * @param habDTO HabitacionDTO del cual se extraen los datos para realizar el insert en la BDD.
     * @return Numero de filas modificadas.
     */
    @Override
    public int altaHabitacion(HabitacionDTO habDTO) {
        Connection con = null;
        PreparedStatement pstm = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern();
            
            String sql = "INSERT INTO HABITACION"
                    +"(EXTRAS_HABITACION, PRECIO, CUARTO_BAÑO, TIPO_HABITACION, RESEÑAS, ALOJAMIENTO_ID_ALOJAMIENTO)"
                    +"VALUE (?, ?, ?, ?, ?, ?)";
            
            pstm = con.prepareStatement(sql);
            
            //Añadido de datos al preparedStatement.
            pstm.setString(1, habDTO.getExtras_habitacion());
            pstm.setFloat(2, habDTO.getPrecio());
            pstm.setBoolean(3, habDTO.isCuarto_banio());
            pstm.setString(4, habDTO.getTipo_habitacion());
            pstm.setString(5, habDTO.getResenias());
            pstm.setInt(6, habDTO.getHabitacion_id_foranea_alojamiento());
            
            return pstm.executeUpdate();
        }catch(MySQLIntegrityConstraintViolationException ex) {
            return -1; //Problema con la clave foranea. Probablemente que no exista.
        }catch(SQLException ex) {
            return -1;
        }finally {
            try {
                if(pstm != null) pstm.close();
            }catch(SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }        
    }
}
