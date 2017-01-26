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
import java.sql.SQLException;

/**
 * DAO especializado para la conexion con Oracle XE. Utiliza las secuencias implementadas en el disenio de la BDD para
 *  introducir los Alojamientos.
 * El resto de metodos necesarios se han resuelto en el padre ya que utilizan ANSI SQL.
 * @author Mario Codes Sánchez
 * @since 26/11/2016
 */
public class AlojamientoDAOOracle extends AlojamientoDAO {

    /**
     * Forma de dar de alta un Alojamiento en la BDD 'Oracle XE'. Utiliza Secuencias para la resolucion del ID.
     * @param alDTO Alojamiento de donde extraemos los datos intrinsecos del mismo para meter en la BDD.
     * @return Entero con el resultado de la operacion.
     */
    @Override
    public int altaAlojamiento(AlojamientoDTO alDTO) {
        Connection con = null; 
        PreparedStatement pstm = null;
        
        try {
            con = DBBConexion.getConexionDBBSingletonPattern(); //Obtenemos la conexion con Singleton Pattern.

            String sql = "INSERT INTO ALOJAMIENTO"
                    +"(ID_ALOJAMIENTO, NOMBRE, DIRECCION_SOCIAL, RAZON_SOCIAL, TELEFONO_CONTACTO, DESCRIPCION, VALORACION, FECHA_APERTURA, NUM_HABITACIONES, PROVINCIA)"
                    +"VALUES (seq_al_pk.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            pstm = con.prepareStatement(sql); //Lo preparamos para evitar inyecciones de codigo.
            
            //Añadido de datos al preparedStatement.            
            pstm.setString(1, alDTO.getNombre());
            pstm.setString(2, alDTO.getDir_Social());
            pstm.setString(3, alDTO.getRazon_Social());
            pstm.setString(4, alDTO.getTelefono_Contacto());
            pstm.setString(5, alDTO.getDescripcion());
            pstm.setInt(6, alDTO.getValoracion());
            pstm.setString(7, alDTO.getFecha_Apertura());
            pstm.setInt(8, alDTO.getNumero_Habitaciones());
            pstm.setString(9, alDTO.getProvincia());
            
            return pstm.executeUpdate(); //Ejecutamos la sentencia y devolvemos el resultado.
        }catch(SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
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
