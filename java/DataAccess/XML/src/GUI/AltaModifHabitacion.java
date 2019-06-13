/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DTO.Alojamiento;
import DTO.Habitacion;
import XML.XML;
import javax.swing.JOptionPane;

/**
 * @author Mario Codes Sánchez
 * @since 07/03/2017
 */
public class AltaModifHabitacion extends javax.swing.JFrame {
    private Habitacion habitacion; //AlojamientoDTO del cual cargaremos los datos en pantalla para MODIFICAR.
    private Alojamiento alojamiento;
    private final XML CONEXION;
    
    /**
     * Version que llamaremos para hacer el alta de Habitacion.
     */
    public AltaModifHabitacion(XML conexion, Alojamiento al) {
        initComponents();
        
        this.setTitle("Alta de una nueva Habitación");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        this.CONEXION = conexion;
        this.alojamiento = al;
    }
    
    /**
     * Version que llamaremos para hacer la modificacion, pasandole una HabitacionDTO para cargar sus datos en la ventana.
     * @param hab
     */
    public AltaModifHabitacion(XML conexion, Alojamiento al, Habitacion hab) {
        initComponents();
        this.botonAniadir.setText("Modificar");
        
        this.setTitle("Modificar una Habitación Existente");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        this.alojamiento = al;
        this.habitacion = hab;
        this.CONEXION = conexion;
        
        cargadoDatosHabitacion(habitacion);
    }
                
        /**
        * Recoleccion del ID en funcion del modo de ejecucion de la ventana (alta o modificacion). 
        * En la version de Alta desde BDD se debe omitir totalmente, se encargara MySQL con un AI.
        * @param alDTO HabitacionDTO donde necesito meter los datos para realizar las operaciones oportunas con el.
        */
        private String recoleccionDatosID() {
            return this.jTextFieldInputIDHabitacion.getText();
        }
        
        /**
         * Recoleccion y pasado de datos desde la ventana grafica a un HabitacionDTO para operar con el.
         * @param habDTO 
         */
        private Habitacion recoleccionDatosInputHabitacion() {
            String id = recoleccionDatosID();
            int precio = (int) this.inputPrecio.getValue();
            String extras = this.inputExtras.getText();
            String tipo = this.jComboBoxTipoHabitacion.getSelectedItem().toString();
            String resenias = this.jTextFieldInputResenias.getText();

            return new Habitacion(id, extras, tipo, resenias, precio);
        }
        
        /**
         * Da la Habitacion de Alta mediante los metodos propios de Hibernate.
         * @return Estado de la transaccion.
         */
        private boolean darHabitacionAltaHibernate(Habitacion habDTO) {
//            Session s = Facade.abrirSessionHibernate();
//            if(habDTO.getReservaIdReserva() == -1) habDTO.setReservaIdReserva(null);
//            s.save(habDTO);
//            return Facade.cerrarSessionHibernate(s);
            return false;
        }
        
        private boolean actualizarHabitacionHibernate(Habitacion habDTO) {
//            try {
//                Session s = Facade.abrirSessionHibernate();
//
//                Query q = s.createQuery("UPDATE Habitacion "
//                        + "SET EXTRAS_HABITACION = :extras, PRECIO = :precio, TIPO_HABITACION = :tipo, "
//                        + "RESENIAS = :resenias, RESERVA_ID_RESERVA = :idReserva "
//                        + "WHERE ID_HABITACION = :idHabitacion");
//
//                q.setParameter("extras", habDTO.getExtrasHabitacion());
//                q.setParameter("precio", habDTO.getPrecio());
//                q.setParameter("tipo", habDTO.getTipoHabitacion());
//                q.setParameter("resenias", habDTO.getResenias());
//                if(habDTO.getReservaIdReserva() != -1) q.setParameter("idReserva", habDTO.getReservaIdReserva()); //Chapuza, arreglo rapido para Angel.
//                else q.setParameter("idReserva", "");
//                q.setParameter("idHabitacion", habDTO.getIdHabitacion());

//                q.executeUpdate();
//                return Facade.cerrarSessionHibernate(s);
//            }catch(ConstraintViolationException ex) {
//                JOptionPane.showMessageDialog(null, "Problema de violacion de clave.");
//                return false;
//            }
            return false;
        }
        
        /**
         * Recoleccion de los campos de la ventana e instanciacion de un alojamiento con esos datos. Previamente se ha realizado la comprobacion de que los datos sean correctos.
         */
        private void recoleccionDatosVentana() {            
            //Recoleccion y almacenado de los valores actuales en la ventana.


            try {
                Habitacion habDTO = recoleccionDatosInputHabitacion();
                
//                if(confirmacionCambioReferenciaAlojamiento(habDTO.getAlojamientoIdAlojamiento())) {
                    if(this.habitacion == null) {
                        boolean res = CONEXION.insertarHabitacion(this.alojamiento, habDTO);
                        if(res) {
                            JOptionPane.showMessageDialog(this, "Habitacion dada de Alta.");
                            reseteoCamposVentana();
                        } else JOptionPane.showMessageDialog(this, "Problema al dar de Alta la Habitacion. Compruebe que el #Habitacion no exista ya en ese Alojamiento.");
                    } else {
                        CONEXION.modificarHabitacion(alojamiento, habDTO);
                        JOptionPane.showMessageDialog(null, "Habitacion modificada con Exito.");
                        reseteoCamposVentana();
                        this.dispose();
                    }
//                }
            } catch(NullPointerException ex) {
                ex.printStackTrace();
            } catch(IllegalArgumentException | ClassCastException ex) {
                ex.printStackTrace();
            }
        } 
    
    /*
        Metodos Version Ventana Alta Habitación.
    */

        /**
         * Se encarga de vaciar los campos de input, una vez se haya instanciado con exito el alojamiento.
         */
        private void reseteoCamposVentana() {
            this.jComboBoxTipoHabitacion.setSelectedIndex(0);
            this.inputPrecio.setValue(Long.parseLong("1")); //Si simplemente pongo '0', es un Int, debe ser Float.
            this.inputExtras.setText(null);
            this.jTextFieldInputResenias.setText(null);
        }
    
    /*
        Metodos de Version 'Modificar Alojamiento'
    */
        
        /**
         * Hace un match del valor actual contenido en el rs e iguala al valor de la box que coincida.
         * @param alDTO AlojamientoDTO del cual se obtiene la provincia.
         */
        private void rellenoAutoTipoHabitacion(Habitacion habDTO) {
            String tipo = habDTO.getTipo_habitacion();

            switch(tipo) {
                case "simple":
                    this.jComboBoxTipoHabitacion.setSelectedIndex(0);
                    break;
                case "doble":
                    this.jComboBoxTipoHabitacion.setSelectedIndex(1);
                    break;
                case "matrimonio":
                    this.jComboBoxTipoHabitacion.setSelectedIndex(2);
                    break;
                case "triple":
                    this.jComboBoxTipoHabitacion.setSelectedIndex(3);
                    break;
                case "cuadruple":
                    this.jComboBoxTipoHabitacion.setSelectedIndex(4);
                    break;
                default:
                    this.jComboBoxTipoHabitacion.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(this, "ERROR. Problema con valor Tipo_Habitacion.");
                    break;
            }
        }

        /**
         * Rellena la ventana con los datos de HabitacionDTO pasado por contructor.
         * @param habDTO Habitacion cuyos datos se mostraran en la ventana.
         */
        private void cargadoDatosHabitacion(Habitacion habDTO) {
            try {
                this.jTextFieldInputIDHabitacion.setText(habDTO.getId_habitacion());
                this.jTextFieldInputIDHabitacion.setEnabled(false);

                this.inputPrecio.setValue(habDTO.getPrecio());
                
                if(habDTO.getExtras_habitacion() != null) this.inputExtras.setText(habDTO.getExtras_habitacion());
                if(habDTO.getReseñas() != null) this.jTextFieldInputResenias.setText(habDTO.getReseñas());
                rellenoAutoTipoHabitacion(habDTO);
            }catch (NullPointerException ex) {
                ex.printStackTrace();
            } //Hay algunos campos que no son obligatorios.
        }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTipoHabitacion = new javax.swing.JLabel();
        jLabelPrecio = new javax.swing.JLabel();
        botonAniadir = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        jLabelExtras = new javax.swing.JLabel();
        inputExtras = new javax.swing.JTextField();
        jComboBoxTipoHabitacion = new javax.swing.JComboBox();
        jLabelResenias = new javax.swing.JLabel();
        jTextFieldInputResenias = new javax.swing.JTextField();
        inputPrecio = new javax.swing.JSpinner();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabel1 = new javax.swing.JLabel();
        jTextFieldInputIDHabitacion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(22, 0));
        setResizable(false);

        jLabelTipoHabitacion.setText("Tipo");

        jLabelPrecio.setText("Precio / Noche");

        botonAniadir.setText("Añadir");
        botonAniadir.setToolTipText("Añadir la entrada a la BDD");
        botonAniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAniadirActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");
        botonCancelar.setToolTipText("Volver atrás");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        jLabelExtras.setText("Extras");

        inputExtras.setToolTipText("Descripcion breve de las caracteristicas del alojamiento.");

        jComboBoxTipoHabitacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "simple", "doble", "matrimonio", "triple", "cuadruple" }));

        jLabelResenias.setText("Reseñas");

        inputPrecio.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(100)));

        jLabel1.setText("#Habitacion");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabelResenias)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInputResenias)
                            .addComponent(inputExtras)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabelExtras)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelTipoHabitacion)
                                        .addGap(16, 16, 16)
                                        .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addComponent(jComboBoxTipoHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelPrecio))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldInputIDHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(13, 13, 13)))
                        .addComponent(inputPrecio)))
                .addGap(33, 33, 33))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonAniadir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputIDHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxTipoHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelPrecio)
                        .addComponent(inputPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTipoHabitacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelExtras))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputResenias, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelResenias))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAniadir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void botonAniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAniadirActionPerformed
        recoleccionDatosVentana(); //Llamada al metodo que comienza a trabajar los datos.
    }//GEN-LAST:event_botonAniadirActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botonCancelarActionPerformed
     
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton botonAniadir;
    protected javax.swing.JButton botonCancelar;
    private javax.swing.Box.Filler filler2;
    protected javax.swing.JTextField inputExtras;
    private javax.swing.JSpinner inputPrecio;
    private javax.swing.JComboBox jComboBoxTipoHabitacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelExtras;
    private javax.swing.JLabel jLabelPrecio;
    private javax.swing.JLabel jLabelResenias;
    private javax.swing.JLabel jLabelTipoHabitacion;
    private javax.swing.JTextField jTextFieldInputIDHabitacion;
    private javax.swing.JTextField jTextFieldInputResenias;
    // End of variables declaration//GEN-END:variables
}
