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
import java.sql.SQLException;

/**
 * Implementacion de la clase abstracta para realizar operaciones relacionadas con la entidad 'Habitacion' en 'Oracle XE'
 * El resto de metodos necesarios estan resueltos en el padre al utilizar SQL ANSI.
 * @author Mario Codes Sánchez
 * @since 26/11/2016
 */
public class HabitacionDAOOracle extends HabitacionDAO{

    /**
     * Aniadido de una Habitacion a la BDD. Utiliza Secuencias para solucionar desde el gestor el ID (PK).
     * @param habDTO Objeto Habitacion de donde se extraen los datos que queremos introducir en la BDD.
     * @return Entero con el resultado de la operacion para informacion del User.
     */
    @Override
    public int altaHabitacion(HabitacionDTO habDTO) {
        Connection con = null;
        PreparedStatement pstm = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern();
            
            String sql = "INSERT INTO HABITACION"
                    +"(ID_HABITACION, EXTRAS_HABITACION, PRECIO, CUARTO_BAÑO, TIPO_HABITACION, RESEÑAS, ALOJAMIENTO_ID_ALOJAMIENTO)"
                    +"VALUES(seq_hab_pk.nextval, ?, ?, ?, ?, ?, ?)";
            
            pstm = con.prepareStatement(sql);
            
            //Añadido de datos al preparedStatement.
            pstm.setString(1, habDTO.getExtras_habitacion());
            pstm.setFloat(2, habDTO.getPrecio());
            pstm.setBoolean(3, habDTO.isCuarto_banio());
            pstm.setString(4, habDTO.getTipo_habitacion());
            pstm.setString(5, habDTO.getResenias());
            pstm.setInt(6, habDTO.getHabitacion_id_foranea_alojamiento());
            
            return pstm.executeUpdate();
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
