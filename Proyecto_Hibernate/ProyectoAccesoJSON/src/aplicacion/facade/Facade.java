/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.facade;

/**
 * Patron de Diseño Façade. Modelo Vista - Controlador. Clase Intermediaria entre ambas para mantener separado el la ejecucion de codigo.
 * @author Mario Codes Sánchez
 * @since 26/11/2016
 */
public class Facade {
    /**
     * Parte relacionada a Alojamientos desde BDD.
     */
//        /**
//         * Alta o Modificacion de un Alojamiento. Obtenemos AlojamientoDTO de la ventana grafica, con los datos que haya en ese momento, se hace una busqueda por el ID.
//         *  Si ese Alojamiento ya existe, lo modifica, si es null, lo crea nuevo.
//         * @param alDTO AlojamientoDTO con todos sus datos, se obtiene por input del user.
//         */
//        public void altaOModificacionAlojamientoBDD(AlojamientoDTO alDTO) {
////            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
////
////            if((alDAO.buscarAlojamientoPorID(alDTO.getId())) == null) {
////                alDAO.altaAlojamiento(alDTO);
////            } else {
////                alDAO.modificacionAlojamiento(alDTO);
////            }
//        }
//
//        /**
//         * Recorre la BDD mirando todos los ids existentes y devuelve el mas alto +1. Se usara cuando cree un Alojamiento nuevo, para hacer un set automatico del ID.
//         * @return ID actual existente mas alto +1.
//         */
//        public int obtenerIdAUsarNuevoAlojamientoBDD() {
////            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
////            Collection <AlojamientoDTO> col = alDAO.buscarAlojamientos();
////
////            int id = 1; //No quiero que haya IDs <= 0. El minimo será 1.
////            for(AlojamientoDTO alDTO: col) {
////                id = alDTO.getId() > id ? alDTO.getId() : id;
////            }
////
////            return id+1;
//        }
//
//        /**
//         * Baja de alojamiento, ambos datos deben coincidir en el registro del alojamiento.
//         * @param alDTOTMP Alojamiento desde el cual se obtiene la ID y el Nombre.
//         * @return Resultado. Numero de filas modificada(s).
//         */
//        public int bajaAlojamientoBDD(AlojamientoDTO alDTOTMP) {
////            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
////            return alDAO.bajaAlojamiento(alDTOTMP);
//        }
//
//        /**
//         * Busca un Alojamiento con la ID especificada que se le pasa como parametro.
//         * @param id ID del alojamiento a buscar, se obtiene de modo grafico.
//         * @return AlojamientoDTO instanciado.
//         */
//        public AlojamientoDTO buscarAlojamientoIdEspecificoBDD(int id) {
////            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
////            return alDAO.buscarAlojamientoPorID(id);
//        }
//
//        /**
//         * Hace una busqueda de todos los alojamiento y los devuelve en una Collection.
//         * @return Collection con todos los alojamientos disponibles en la BDD.
//         */
//        public Collection<AlojamientoDTO> listadoAlojamientosBDD() {
////            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
////            return alDAO.buscarAlojamientos();
//        }

//        /**
//         * Hace una busqueda de todos los alojamientos que esten situados en la provincia que indicamos.
//         * @param provincia Provincia de donde queremos consultar los Alojamientos.
//         * @return Collection con todos los AlojamientosDTO resultado.
//         */
//        public Collection<AlojamientoDTO> listadoAlojamientosBDD(String provincia) {
//            AlojamientoDAO alDAO = (AlojamientoDAO) DAOFactory.getInstancia(claseSubhijaAlojamientoDAOSegunBDD);
//            return alDAO.buscarAlojamientos(provincia);
//        }
}
