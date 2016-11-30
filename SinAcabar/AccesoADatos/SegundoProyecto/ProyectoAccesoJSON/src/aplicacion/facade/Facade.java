/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.facade;

import controlador.DAO.AlojamientoDAO;
import controlador.DAO.HabitacionDAO;
import controlador.DTO.AlojamientoDTO;
import controlador.DAO.DAOFactory;
import controlador.DTO.HabitacionDTO;
import controlador.datos.EjecucionJSON;
import java.util.Collection;
import org.json.JSONException;

/**
 * Patron de Diseño Façade. Modelo Vista - Controlador. Clase Intermediaria entre ambas para mantener separado el la ejecucion de codigo.
 * @author Mario Codes Sánchez
 * @since 26/11/2016
 */
public class Facade {
    private static String claseSubhijaAlojamientoDAOSegunBDD; //Cambia en funcion de la BDD a la que nos conectamos, la pongo como variable miembro ya podra cambiar durante el programa.
    private static String claseSubhijaHabitacionDAOSegunBDD;
    
    /**
     * Parte relacionada a Alojamientos desde BDD.
     */
        /**
         * Alta o Modificacion de un Alojamiento. Obtenemos AlojamientoDTO de la ventana grafica, con los datos que haya en ese momento, se hace una busqueda por el ID.
         *  Si ese Alojamiento ya existe, lo modifica, si es null, lo crea nuevo.
         * @param alDTO AlojamientoDTO con todos sus datos, se obtiene por input del user.
         */
        public void altaOModificacionAlojamientoBDD(AlojamientoDTO alDTO) {
            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);

            if((alDAO.buscarAlojamientoPorID(alDTO.getId())) == null) {
                alDAO.altaAlojamiento(alDTO);
            } else {
                alDAO.modificacionAlojamiento(alDTO);
            }
        }

        /**
         * Recorre la BDD mirando todos los ids existentes y devuelve el mas alto +1. Se usara cuando cree un Alojamiento nuevo, para hacer un set automatico del ID.
         * @return ID actual existente mas alto +1.
         */
        public int obtenerIdAUsarNuevoAlojamientoBDD() {
            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
            Collection <AlojamientoDTO> col = alDAO.buscarAlojamientos();

            int id = 1; //No quiero que haya IDs <= 0. El minimo será 1.
            for(AlojamientoDTO alDTO: col) {
                id = alDTO.getId() > id ? alDTO.getId() : id;
            }

            return id+1;
        }

        /**
         * Baja de alojamiento, ambos datos deben coincidir en el registro del alojamiento.
         * @param alDTOTMP Alojamiento desde el cual se obtiene la ID y el Nombre.
         * @return Resultado. Numero de filas modificada(s).
         */
        public int bajaAlojamientoBDD(AlojamientoDTO alDTOTMP) {
            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
            return alDAO.bajaAlojamiento(alDTOTMP);
        }

        /**
         * Busca un Alojamiento con la ID especificada que se le pasa como parametro.
         * @param id ID del alojamiento a buscar, se obtiene de modo grafico.
         * @return AlojamientoDTO instanciado.
         */
        public AlojamientoDTO buscarAlojamientoIdEspecificoBDD(int id) {
            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
            return alDAO.buscarAlojamientoPorID(id);
        }

        /**
         * Hace una busqueda de todos los alojamiento y los devuelve en una Collection.
         * @return Collection con todos los alojamientos disponibles en la BDD.
         */
        public Collection<AlojamientoDTO> listadoAlojamientosBDD() {
            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
            return alDAO.buscarAlojamientos();
        }

        /**
         * Hace una busqueda de todos los alojamientos que esten situados en la provincia que indicamos.
         * @param provincia Provincia de donde queremos consultar los Alojamientos.
         * @return Collection con todos los AlojamientosDTO resultado.
         */
        public Collection<AlojamientoDTO> listadoAlojamientosBDD(String provincia) {
            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
            return alDAO.buscarAlojamientos(provincia);
        }
    
    /*
        Parte relacionada a Alojamientos desde JSON.
    */
        /**
         * Le pasamos un AlojamientoDTO como parametro, comprueba si existe. Si no lo hace, lo da de Alta y lo almacena en el HashMap. Si si que existe, lo modifica.
         * @param alDTO AlojamientoDTO instanciado y listo.
         */
        public void altaOModificacionAlojamientoJSON(AlojamientoDTO alDTO) {
            EjecucionJSON ejecJSON = (EjecucionJSON) DAOFactory.getInstancia("EjecucionJSON");
            
            if((ejecJSON.buscarAlojamientoPorIdJSON(alDTO.getId())) == null) {
                ejecJSON.altaAlojamiento(alDTO);
            } else {
                ejecJSON.modificacionAlojamiento(alDTO);
            }
        }
        
        /**
         * Da de baja en el HashMap interno, el AlojamientoDTO que le pasamos como parametro.
         * @param alDTO AlojamientoDTO instanciado que queremos dar de baja.
         */
        public void bajaAlojamientoJSON(AlojamientoDTO alDTO) {
            EjecucionJSON ejecJSON = (EjecucionJSON) DAOFactory.getInstancia("EjecucionJSON");
            ejecJSON.bajaAlojamiento(alDTO);
        }
        
        /**
         * Busca un AlojamientoDTO en el HashMap por su #ID.
         * @param id ID del Alojamiento que queremos obtener.
         * @return AlojamientoDTO con esa ID.
         */
        public AlojamientoDTO buscarAlojamientoIDespecificoJSON(int id) {
            EjecucionJSON ejecJSON = (EjecucionJSON) DAOFactory.getInstancia("EjecucionJSON");
            return ejecJSON.buscarAlojamientoPorIdJSON(id);
        }
        
        /**
         * Recorremos el HashMap entero, almacenando cada AlojamientoDTO en una Collection que al final devolvemos.
         * @return Collection de AlojamientosDTO con todos los existentes actualmente.
         */
        public Collection <AlojamientoDTO> listadoAlojamientosJSON() {
            EjecucionJSON ejecJSON = (EjecucionJSON) DAOFactory.getInstancia("EjecucionJSON");
            return ejecJSON.listadoAlojamientosJSON();
        }
        
        /**
         * Listamos todos los AlojamientoDTO existentes filtrando por Provincia.
         * @param provincia Provincia de la que queremos obtener todos los AlojamientoDTO.
         * @return Collection de AlojamientosDTO situados en la provincia indicada.
         */
        public Collection <AlojamientoDTO> listadoAlojamientosJSON(String provincia) {
            EjecucionJSON ejecJSON = (EjecucionJSON) DAOFactory.getInstancia("EjecucionJSON");
            return ejecJSON.listadoAlojamientosJSON(provincia);
        }
        
    /*
        Parte relacionada a Habitaciones (BDD hasta ahora, aqui no me meto con JSON).
    */
        /**
         * Damos de alta o modificamos una habitacion en la BDD, segun si este ya existe o no. Esto se comprueba realizando una busqueda de todas las habitaciones existentes y comparando ID.
         * @param habDTO HabitacionDTO de donde se sacan los datos que se pasaran a la BDD.
         * @return Resultado de la operacion. -1 error por clave foranea. Luego por fila(s) modificadas: 0 problema, 1 salida normal. 
         */
        public int altaOModificacionHabitacion(HabitacionDTO habDTO) {
            HabitacionDAO habDAO = (HabitacionDAO) DAOFactory.getInstancia(claseSubhijaHabitacionDAOSegunBDD);

            if((habDAO.buscarHabitacionPorID(habDTO.getId_habitacion())) == null) {
                return habDAO.altaHabitacion(habDTO);
            } else {
                return habDAO.modificacionHabitacion(habDTO);
            }
        }

        /**
         * Recorre la BDD mirando todos los ids de Habitacion existentes y devuelve el mas alto +1.
         * @return ID actual existente mas alto +1.
         */
        public int obtenerIdAUsarNuevaHabitacion() {
            HabitacionDAO habDAO = (HabitacionDAO) DAOFactory.getInstancia(claseSubhijaHabitacionDAOSegunBDD);
            Collection <HabitacionDTO> col = habDAO.buscarHabitaciones();

            int id = 1;
            for(HabitacionDTO habDTO: col) {
                id = habDTO.getId_habitacion() > id ? habDTO.getId_habitacion(): id;
            }

            return id+1;
        }

        /**
         * Baja de habitacion mediante el ID introducido.
         * @param habDTOTmp HabDTO del cual se saca la ID.
         * @return Resultado. Numero de filas modificada(s).
         */
        public int bajaHabitacion(HabitacionDTO habDTOTmp) {
            HabitacionDAO habDAO = (HabitacionDAO) DAOFactory.getInstancia(claseSubhijaHabitacionDAOSegunBDD);
            return habDAO.bajaHabitacion(habDTOTmp);
        }

        /**
         * Buscamos una habitacion especifica seleccionando por ID.
         * @param id ID de la habitacion a devolver.
         * @return HabitacionDTO con los datos de la ID seleccionada.
         */
        public HabitacionDTO buscarHabitacionIdEspecifica(int id) {
            HabitacionDAO habDAO = (HabitacionDAO) DAOFactory.getInstancia(claseSubhijaHabitacionDAOSegunBDD);
            return habDAO.buscarHabitacionPorID(id);
        }

        /**
         * Listado de Habitaciones completo de la BDD. Metodo Sobrecargado.
         * @return Colleccion conteniendo absolutamente todas las habitaciones existentes.
         */
        public Collection<HabitacionDTO> listadoHabitaciones() {
            HabitacionDAO habDAO = (HabitacionDAO) DAOFactory.getInstancia(claseSubhijaHabitacionDAOSegunBDD);
            return habDAO.buscarHabitaciones();
        }

        /**
         * Listado de Habitaciones que tienen un precio igual o menor al precio indicado.
         * @param precioMax Precio para filtrar.
         * @return Collection conteniendo los HabitacionesDTO que cumplen con el precio.
         */
        public Collection<HabitacionDTO> listadoHabitaciones(float precioMax) {
            HabitacionDAO habDAO = (HabitacionDAO) DAOFactory.getInstancia(claseSubhijaHabitacionDAOSegunBDD);
            return habDAO.buscarHabitaciones(precioMax);
        }
        
    /*
        Cambio de las SubhijasDAO a instanciar. Esto se debera ejecutar cuando el User seleccione BDD.
    */
        
        /**
         * Cambiamos la clase a instanciar a la version usada por MySQL.
         */
        public void cargadoPropertiesMySQL() {
            claseSubhijaAlojamientoDAOSegunBDD = "AlojamientoDAOMySQL";
            claseSubhijaHabitacionDAOSegunBDD = "HabitacionDAOMySQL";
        }
        
        /**
         * Cambiamos la clase a instanciar a la version de Oracle.
         */
        public void cargadoPropertiesOracle() {
            claseSubhijaAlojamientoDAOSegunBDD = "AlojamientoDAOOracle";
            claseSubhijaHabitacionDAOSegunBDD = "HabitacionDAOOracle";
        }
}
