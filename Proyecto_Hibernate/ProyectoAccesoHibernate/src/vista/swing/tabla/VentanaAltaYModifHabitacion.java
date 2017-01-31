/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.swing.tabla;

import vista.swing.comun.VentanaPrincipal;
import aplicacion.facade.Facade;
import controlador.datos.Singleton;
import dto.Habitacion;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 * @author Mario Codes Sánchez
 * @since 11/11/2016
 */
public class VentanaAltaYModifHabitacion extends javax.swing.JFrame {
    private final Facade FACHADA = new Facade();
    private final VentanaPrincipal VP = Singleton.getVentanaPrincipalObtencionSingleton(); //Obtencion de VentanaPrincipal por Singleton.
    private Habitacion habitacion; //AlojamientoDTO del cual cargaremos los datos en pantalla para MODIFICAR.
    
    /**
     * Version que llamaremos para hacer el alta de Habitacion.
     */
    public VentanaAltaYModifHabitacion() {
        initComponents();
        
        this.setTitle("Alta de una nueva Habitación");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        VP.setVisible(false);
    }
    
    /**
     * Version que llamaremos para hacer la modificacion, pasandole una HabitacionDTO para cargar sus datos en la ventana.
     * @param hab
     */
    public VentanaAltaYModifHabitacion(Habitacion hab) {
        initComponents();
        this.habitacion = hab;
        this.jLabelTituloVentana.setText("Modificar Habitación");
        this.botonAniadir.setText("Modificar");
        
        this.setTitle("Modificar una Habitación Existente");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
//        cargadoDatosHabitacion(habitacion);
    }
    
    /*
        Metodos Comunes a ambas versiones.
    */
        private boolean checkCompletoInput() {
            if(checkInputsObligatoriosLlenos()) { //Chequeo de que todos los campos estan llenos
                if(checkExprRegularIdForanea()) { //Chequeo de que el telefono solo contenga numeros y longitud correcta.
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "ERROR. La ID del Alojamiento relacionado no es un entero.");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this, "ERROR. Hay algun campo obligatorio vacío.");
                return false;
            }
        }
        
        /**
         * Hace check de que los campos obligatorios de input no estan vacios.
         * @return True si todos estan rellenados.
         */
        private boolean checkInputsObligatoriosLlenos() {
            return !(inputIDForanea.getText().isEmpty());
        }

        /**
         * Chequea que el input realizado sobre el telefono sean solo numeros y de longitud 9 a 13.
         * @return True si el campo contiene solo numeros (int).
         */
        private boolean checkExprRegularIdForanea() {
            try {
                Pattern pat = Pattern.compile("[0-9]+");
                Matcher mat = pat.matcher(this.inputIDForanea.getText());

                return mat.matches();
            } catch(NumberFormatException ex) {
                return false;
            }
        }
        
        /**
         * Comprueba si la ID foranea del alojamiento metido al principio != de la que se intenta meter ahora. Si lo es, pide confirmacion y devuelve resultado.
         * Antes de esto comprueba si habDTO es nulo. Si lo es, estamos en la version de Alta y esta ventana no hace falta.
         * @param id_foranea_alojamiento ID actual de la ventana.
         * @return True si se desea cambiar.
         */
        private boolean confirmacionCambioReferenciaAlojamiento(int id_foranea_alojamiento) {
            boolean confirmacion = true;

            if(habitacion != null) {
                if(habitacion.getAlojamientoIdAlojamiento() != id_foranea_alojamiento) {
                    int res = JOptionPane.showConfirmDialog(this, "Estas a punto de cambiar el Alojamiento al que pertenece esta habitacion. ¿Seguro?");
                    if(res != 0) confirmacion = false;
                }
            }
            
            return confirmacion;
        }
        
        /**
        * Recoleccion del ID en funcion del modo de ejecucion de la ventana (alta o modificacion). 
        * En la version de Alta desde BDD se debe omitir totalmente, se encargara MySQL con un AI.
        * @param alDTO HabitacionDTO donde necesito meter los datos para realizar las operaciones oportunas con el.
        */
        private void recoleccionDatosID(Habitacion habDTO) {
            if(this.habitacion != null) habDTO.setIdHabitacion(this.habitacion.getIdHabitacion()); 
        }
        
        /**
         * Set del 'booleano' en forma de Char para indicar si existe cuarto de baño.
         * @param habDTO Habitacion a la cual se lo ponemos.
         * @return 1 si hay cuarto de baño. 0 sino.
         */
        private char seleccionCuartoBanio(Habitacion habDTO) {
            if(this.jCheckBoxCuartoBanio.isSelected()) return 1;
            else return 0;
        }
        
        /**
         * Recoleccion y pasado de datos desde la ventana grafica a un HabitacionDTO para operar con el.
         * @param habDTO 
         */
        private void recoleccionDatosInputHabitacion(Habitacion habDTO) {
            recoleccionDatosID(habDTO);
            
            habDTO.setIdHabitacion(Integer.parseInt(this.jTextFieldInputIDHabitacion.getText()));
            habDTO.setAlojamientoIdAlojamiento(Integer.parseInt(this.inputIDForanea.getText()));
            habDTO.setReservaIdReserva(Integer.parseInt(this.jTextFieldIDReserva.getText()));
            habDTO.setPrecio(BigDecimal.valueOf((long) this.inputPrecio.getValue()));
            habDTO.setExtrasHabitacion(this.inputExtras.getText());
            habDTO.setTipoHabitacion((String) this.jComboBoxTipoHabitacion.getSelectedItem());
            habDTO.setResenias(this.jTextFieldInputResenias.getText());
            habDTO.setCuartoBanio(seleccionCuartoBanio(habDTO));
        }
        
        /**
         * Da la Habitacion de Alta mediante los metodos propios de Hibernate.
         */
        private void darHabitacionAltaHibernate(Habitacion habDTO) {
            Session s = Facade.abrirSessionHibernate();
            s.save(habDTO);
            Facade.cerrarSessionHibernate(s);
        }
        
        /**
         * Recoleccion de los campos de la ventana e instanciacion de un alojamiento con esos datos. Previamente se ha realizado la comprobacion de que los datos sean correctos.
         */
        private void recoleccionDatosVentana() {            
            //Recoleccion y almacenado de los valores actuales en la ventana.
            Habitacion habDTO = new Habitacion();

            try {
                recoleccionDatosInputHabitacion(habDTO);
                
                if(confirmacionCambioReferenciaAlojamiento(habDTO.getAlojamientoIdAlojamiento())) {
                    darHabitacionAltaHibernate(habDTO);
                    JOptionPane.showMessageDialog(this, "Habitacion dada de Alta.");
                    
                    //Instanciacion del DTO de Alojamiento y pasado a fachada con este DTO.
//                    int res = FACHADA.altaOModificacionHabitacion(habDTO); //Numero de filas modificadas. -1 salida de error por clave foranea.

//                    if(this.habDTO == null) { //Si nunca se ha instanciado, estamos en la version de alta.
//                        if(res == -1) {
//                            JOptionPane.showMessageDialog(null, "Error de clave foránea. Comprueba que el Alojamiento Referenciado existe.");
//                        } else {
//                            if(res == 0) {
//                                JOptionPane.showMessageDialog(null, "Problema. 0 filas modificadas."); //No deberia darse nunca. Por si acaso.
//                            } else {
//                                JOptionPane.showMessageDialog(null, "¡Habitación dada de alta exitosamente!");  //Salida normal.
//                                reseteoCamposVentana(); //Vaciamos todos los campos.
//                            }
//                        }
//                    } else { //Si lo ha sido, estamos en la de modificacion.
//                        if(res == -1) {
//                            JOptionPane.showMessageDialog(this, "Error de clave foranea. Comprueba que el Alojamiento Referenciado existe.");
//                        } else {
//                            if(res == 0) {
//                                JOptionPane.showMessageDialog(this, "Problema. 0 filas modificadas.");
//                            } else {
//                                JOptionPane.showMessageDialog(null, "¡Habitación modificada exitosamente!\n " +res +" fila(s) modificada(s)");
//                                this.dispose();
//                            }
//                        }
//                    }
                }
            } catch(NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "ERROR. NullPointerException. Mirar Output. \n");
                System.out.println(ex.getLocalizedMessage());
            } catch(IllegalArgumentException | ClassCastException ex) {
                JOptionPane.showMessageDialog(this, "ERROR. Problema generico: \n" +ex.getLocalizedMessage());
            }
        } 
    
    /*
        Metodos Version Ventana Alta Habitación.
    */

        /**
         * Se encarga de vaciar los campos de input, una vez se haya instanciado con exito el alojamiento.
         */
        private void reseteoCamposVentana() {
            this.inputIDForanea.setText(null);
            this.jComboBoxTipoHabitacion.setSelectedIndex(0);
            this.inputPrecio.setValue(Float.parseFloat("0")); //Si simplemente pongo '0', es un Int, debe ser Float.
            this.inputExtras.setText(null);
            this.jTextFieldInputResenias.setText(null);
            this.jCheckBoxCuartoBanio.setSelected(false);
        }
    
    /*
        Metodos de Version 'Modificar Alojamiento'
    */
        
        /**
         * Hace un match del valor actual contenido en el rs e iguala al valor de la box que coincida.
         * @param alDTO AlojamientoDTO del cual se obtiene la provincia.
         */
//        private void rellenoAutoTipoHabitacion(HabitacionDTO habDTO) {
//            String tipo = habDTO.getTipo_habitacion();
//
//            switch(tipo) {
//                case "simple":
//                    this.jComboBoxTipoHabitacion.setSelectedIndex(0);
//                    break;
//                case "doble":
//                    this.jComboBoxTipoHabitacion.setSelectedIndex(1);
//                    break;
//                case "matrimonio":
//                    this.jComboBoxTipoHabitacion.setSelectedIndex(2);
//                    break;
//                case "triple":
//                    this.jComboBoxTipoHabitacion.setSelectedIndex(3);
//                    break;
//                case "cuadruple":
//                    this.jComboBoxTipoHabitacion.setSelectedIndex(4);
//                    break;
//                default:
//                    this.jComboBoxTipoHabitacion.setSelectedIndex(0);
//                    JOptionPane.showMessageDialog(this, "ERROR. Problema con valor Tipo_Habitacion.");
//                    break;
//            }
//        }

        /**
         * Rellena la ventana con los datos de HabitacionDTO pasado por contructor.
         * @param habDTO Habitacion cuyos datos se mostraran en la ventana.
         */
//        private void cargadoDatosHabitacion(HabitacionDTO habDTO) {
//            this.inputIDHabitacion.setText(Integer.toString(habDTO.getId_habitacion()));
//            this.inputIDForanea.setText(Integer.toString(habDTO.getHabitacion_id_foranea_alojamiento()));
//            this.inputPrecio.setValue(habDTO.getPrecio());
//            this.inputExtras.setText(habDTO.getExtras_habitacion());
//            this.jTextFieldInputResenias.setText(habDTO.getResenias());
//            if(habDTO.isCuarto_banio()) this.jCheckBoxCuartoBanio.setSelected(true);
//            rellenoAutoTipoHabitacion(habDTO);
//        }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelIDAlojamientoForanea = new javax.swing.JLabel();
        inputIDForanea = new javax.swing.JTextField();
        jLabelTipoHabitacion = new javax.swing.JLabel();
        jLabelPrecio = new javax.swing.JLabel();
        botonAniadir = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        jLabelExtras = new javax.swing.JLabel();
        inputExtras = new javax.swing.JTextField();
        jLabelTituloVentana = new javax.swing.JLabel();
        jComboBoxTipoHabitacion = new javax.swing.JComboBox();
        jCheckBoxCuartoBanio = new javax.swing.JCheckBox();
        jLabelResenias = new javax.swing.JLabel();
        jTextFieldInputResenias = new javax.swing.JTextField();
        inputPrecio = new javax.swing.JSpinner();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabel1 = new javax.swing.JLabel();
        jTextFieldInputIDHabitacion = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldIDReserva = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(22, 0));
        setResizable(false);

        jLabelIDAlojamientoForanea.setText("ID Alojamiento*");

        inputIDForanea.setToolTipText("Nombre del alojamiento");

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

        jLabelTituloVentana.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabelTituloVentana.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelTituloVentana.setText("Alta Habitación");

        jComboBoxTipoHabitacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "simple", "doble", "matrimonio", "triple", "cuadruple" }));

        jCheckBoxCuartoBanio.setText("Posee Cuarto de Baño*");

        jLabelResenias.setText("Reseñas");

        inputPrecio.setModel(new javax.swing.SpinnerNumberModel(Long.valueOf(1L), Long.valueOf(0L), null, Long.valueOf(100L)));

        jLabel1.setText("ID Hab*");

        jLabel2.setText("ID Reserva*");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jCheckBoxCuartoBanio)
                        .addGap(107, 107, 107))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabelResenias)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInputResenias)
                                    .addComponent(inputExtras)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelExtras)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelTipoHabitacion)
                                        .addGap(16, 16, 16)
                                        .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBoxTipoHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelPrecio)
                                        .addGap(13, 13, 13))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldInputIDHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabelIDAlojamientoForanea))
                                        .addGap(7, 7, 7)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputPrecio)
                                    .addComponent(jTextFieldIDReserva)
                                    .addComponent(inputIDForanea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(33, 33, 33))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botonAniadir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabelTituloVentana)
                        .addGap(118, 118, 118))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabelTituloVentana)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputIDForanea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelIDAlojamientoForanea)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldInputIDHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldIDReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxCuartoBanio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAniadir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void botonAniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAniadirActionPerformed
        if(checkCompletoInput()) {
            recoleccionDatosVentana(); //Llamada al metodo que comienza a trabajar los datos.
        }
    }//GEN-LAST:event_botonAniadirActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        this.dispose();
        VP.setVisible(true);
    }//GEN-LAST:event_botonCancelarActionPerformed
     
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton botonAniadir;
    protected javax.swing.JButton botonCancelar;
    private javax.swing.Box.Filler filler2;
    protected javax.swing.JTextField inputExtras;
    protected javax.swing.JTextField inputIDForanea;
    private javax.swing.JSpinner inputPrecio;
    private javax.swing.JCheckBox jCheckBoxCuartoBanio;
    private javax.swing.JComboBox jComboBoxTipoHabitacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelExtras;
    private javax.swing.JLabel jLabelIDAlojamientoForanea;
    private javax.swing.JLabel jLabelPrecio;
    private javax.swing.JLabel jLabelResenias;
    private javax.swing.JLabel jLabelTipoHabitacion;
    protected javax.swing.JLabel jLabelTituloVentana;
    private javax.swing.JTextField jTextFieldIDReserva;
    private javax.swing.JTextField jTextFieldInputIDHabitacion;
    private javax.swing.JTextField jTextFieldInputResenias;
    // End of variables declaration//GEN-END:variables
}
