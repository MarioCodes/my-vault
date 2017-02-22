/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.swing.alojamientos;

import controlador.datos.NeoDatis;
import controlador.datos.Singleton;
import controlador.dto.Alojamiento;
import vista.swing.comun.VentanaPrincipal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * Ventana de doble funcionalidad. Segun que constructor se utilice, sirve para dar de Alta un Alojamiento completamente nuevo; O para modificar los datos de uno ya existente.
 *  En función de esto, se utilizaran unos metodos de la clase u otros.
 * @author Mario Codes Sánchez
 * @since 30/11/2016
 */
public class VentanaAltaYModifAlojamiento extends javax.swing.JFrame {
    private final VentanaPrincipal VP = Singleton.getVentanaPrincipalObtencionSingleton(); //Obtencion de VentanaPrincipal por Singleton.
    private Alojamiento alojamiento; //AlojamientoDTO del cual cargaremos los datos en pantalla para MODIFICAR. Lo utilizo como comprobante (== null) para ver que pantalla se ha iniciado.
    
    /**
     * Version que llamaremos para hacer el alta de Alojamiento.
     */
    public VentanaAltaYModifAlojamiento() {
        initComponents();
        
        this.setTitle("Alta de Alojamiento Nuevo");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        VP.setVisible(false);
        
//        desactivarCamposIDAlojamiento(); //En todas las ventanas de alta, no me interesa que se vea el campo de ID, ya que estara vacio. Lo hace la propia BDD con AI / Secuencias.
    }
    
    /**
     * Version que llamaremos para hacer la modificacion, pasandole un AlojamientoDTO para cargar sus datos en la ventana.
     * @param alojamiento AlojamientoDTO instanciado cuyos datos modificaremos.
     */
    public VentanaAltaYModifAlojamiento(Alojamiento alojamiento) {
        initComponents();
        this.alojamiento = alojamiento;
        this.jLabelTituloVentana.setText("Modificar Alojamiento");
        this.botonAceptar.setText("Guardar");
        
        this.setTitle("Modificacion de Alojamiento Existente");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        cargadoDatosAlojamiento(alojamiento);
    }
    
    /**
     * El ID en al version de Alta, con BDD se hace de manera automatica con AI de MySQL.
     */
//    private void desactivarCamposIDAlojamiento() {
//        this.jLabelID.setVisible(false);
//        this.inputIDAlojamiento.setVisible(false);
//    }
    
    /*
        Metodos Comunes a ambas versiones (Checks principalmente).
    */
    
    /**
     * Recopilacion de checks. Mira que no haya nada obligatorio vacio, que el telefono sea numerico y que no se intente meter la fecha por default.
     * @return True si correcto.
     */
    private boolean checkCompletoInput() {
        if(checkInputsLlenos()) { //Chequeo de que todos los campos estan llenos
            if(checkInputTlfNumericoExprRegular()) { //Chequeo de que el telefono solo contenga numeros y longitud correcta.
                if(checkFechaDefault()) { //Chequeo de que no me intenten introducir el valor default ('dd/mm/yyyy') y que la fecha este entre valores correctos.
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "ERROR. La fecha NO es valida. Comprueba el Formato.");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this, "ERROR. El numero de telefono Introducido, NO es valido.");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "ERROR. Hay algun campo obligatorio vacío.");
            return false;
        }
    }

    /**
     * Hace check de que los campos de input no estan vacios.
     * @return True si todos estan rellenados.
     */
    private boolean checkInputsLlenos() {
        return (!this.inputNombre.getText().isEmpty() && !this.inputTelefono.getText().isEmpty() && !this.inputDireccionSocial.getText().isEmpty() && !this.inputRazonSocial.getText().isEmpty() && !this.inputFecha.getText().isEmpty());
    }

    /**
     * Chequeo de que la fecha no es el valor por defecto 'dd-mm-yyyy', si no que se ha introducido algo.
     * @return True si es valida (no coincide con el valor default)
     */
    private boolean checkFechaDefault() {
        return !this.inputFecha.getText().matches("dd/mm/yyyy");
    }

    /**
     * Chequea que el input realizado sobre el telefono sean solo numeros y de longitud 9 a 13.
     * @return True si el campo contiene solo numeros (int).
     */
    private boolean checkInputTlfNumericoExprRegular() {
        try {
            String cadena = this.inputTelefono.getText();

            Pattern pat = Pattern.compile("[0-9]{9,13}");
            Matcher mat = pat.matcher(cadena);

            return mat.matches();
        } catch(NumberFormatException ex) {
            return false;
        }
    }
    
    /**
     * Recoleccion de los datos actuales en la ventana. ID solo si estamos en la version de JSON.
     * @param alDTO AlojamientoDTO donde se meteran todos los datos.
     */
    private void recoleccionDatos(Alojamiento alDTO) {
        alDTO.setNumHabitaciones((int) this.spinnerNumHabitaciones.getValue());
        alDTO.setNombre(this.inputNombre.getText());
        alDTO.setTelefonoContacto(this.inputTelefono.getText());
        alDTO.setDireccionSocial(this.inputDireccionSocial.getText());
        alDTO.setRazonSocial(this.inputRazonSocial.getText());
        alDTO.setProvincia((String) this.inputBoxProvincia.getSelectedItem());
        alDTO.setDescripcion(this.inputDescripcion.getText());
        alDTO.setValoracion(this.sliderValoracion.getValue());
        alDTO.setFechaApertura(this.inputFecha.getText());
    }
    
    /**
     * Recoleccion de los campos de la ventana e instanciacion de un alojamiento con esos datos. Previamente se ha realizado la comprobacion de que los datos sean correctos.
     */
    private void insert() {
        Alojamiento alDTOLocal = null;

        try {
            //Instanciacion del DTO de Alojamiento y pasado a fachada con este DTO.
            alDTOLocal = new Alojamiento();
            recoleccionDatos(alDTOLocal);
            
            NeoDatis.insert(alDTOLocal);

            //Output para el usuario, dependiendo de si estamos dando de alta o modificando.
            if(this.alojamiento == null) {
                JOptionPane.showMessageDialog(null, "¡Alojamiento dado de alta exitosamente!");
                reseteoCamposVentana(); //Vaciamos todos los campos.
            } else {
                JOptionPane.showMessageDialog(this, "¡Alojamiento modificado exitosamente!");
                this.dispose();
            }
        } catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. NullPointerException. Mirar Output. \n");
            System.out.println(ex.getLocalizedMessage());
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. Problema con la conversion de la fecha: \n" +ex.getLocalizedMessage());
        } catch(ClassCastException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. Problema generico: \n" +ex.getLocalizedMessage());
        }
    }
    
    private void update() {
        Alojamiento alojamiento = null;
        
        try {
            alojamiento = new Alojamiento();
            recoleccionDatos(alojamiento);
            
            
        } catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. NullPointerException. Mirar Output. \n");
            System.out.println(ex.getLocalizedMessage());
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. Problema con la conversion de la fecha: \n" +ex.getLocalizedMessage());
        } catch(ClassCastException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. Problema generico: \n" +ex.getLocalizedMessage());
        }
    }
    
    /**
     * Se encarga de vaciar los campos de input, una vez se haya instanciado con exito el alojamiento.
     */
    private void reseteoCamposVentana() {
        this.spinnerNumHabitaciones.setValue(0);
        this.inputNombre.setText(null);
        this.inputTelefono.setText(null);
        this.inputDireccionSocial.setText(null);
        this.inputRazonSocial.setText(null);
        this.inputFecha.setText("dd/mm/yyyy");
        this.inputDescripcion.setText(null);
        this.sliderValoracion.setValue(3);
        this.inputBoxProvincia.setSelectedIndex(0); //Primer index disponible.
    }

    /*
        Metodos de Version 'Modificar Alojamiento'
    */

    /**
     * Hace un match del valor actual contenido en el AlojamientoDTO e iguala al valor de la box que coincida.
     * @param alDTO AlojamientoDTO del cual se obtiene la provincia.
     */
    private void rellenoAutoProvincia(Alojamiento alDTO) {
        String provincia = alDTO.getProvincia();
        switch(provincia) {
            case "Huesca":
                this.inputBoxProvincia.setSelectedIndex(0);
                break;
            case "Zaragoza":
                this.inputBoxProvincia.setSelectedIndex(1);
                break;
            case "Teruel":
                this.inputBoxProvincia.setSelectedIndex(2);
                break;
            default:
                this.inputBoxProvincia.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "ERROR. Problema con valor provincia.");
                break;
        }
    }

    /**
     * Rellena la ventana con los datos de AlojamientoDTO pasado por contructor.
     * @param alDTO Alojamiento cuyos datos se mostraran en la ventana.
     */
    private void cargadoDatosAlojamiento(Alojamiento alDTO) {
        this.inputNombre.setText(alDTO.getNombre());
        this.inputDireccionSocial.setText(alDTO.getDireccionSocial());
        this.inputRazonSocial.setText(alDTO.getRazonSocial());
        this.inputTelefono.setText(alDTO.getTelefonoContacto());
        this.inputDescripcion.setText(alDTO.getDescripcion());
        this.sliderValoracion.setValue(alDTO.getValoracion());
        this.inputFecha.setText(alDTO.getFechaApertura());
        this.spinnerNumHabitaciones.setValue(alDTO.getNumHabitaciones());
        rellenoAutoProvincia(alDTO);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNombre = new javax.swing.JLabel();
        inputNombre = new javax.swing.JTextField();
        jLabelTelefono = new javax.swing.JLabel();
        inputTelefono = new javax.swing.JTextField();
        jLabelDirSocial = new javax.swing.JLabel();
        inputDireccionSocial = new javax.swing.JTextField();
        jLabelRazonSocial = new javax.swing.JLabel();
        inputRazonSocial = new javax.swing.JTextField();
        LabelFechaApertura = new javax.swing.JLabel();
        sliderValoracion = new javax.swing.JSlider();
        jLabelProvincia = new javax.swing.JLabel();
        inputBoxProvincia = new javax.swing.JComboBox();
        jLabelValoracion = new javax.swing.JLabel();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        jLabelDescripcion = new javax.swing.JLabel();
        inputDescripcion = new javax.swing.JTextField();
        jLabelTituloVentana = new javax.swing.JLabel();
        inputFecha = new javax.swing.JTextField();
        jLabelNumHabitaciones = new javax.swing.JLabel();
        spinnerNumHabitaciones = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(22, 0));
        setResizable(false);

        jLabelNombre.setText("Nombre*");

        inputNombre.setToolTipText("Nombre del alojamiento");
        inputNombre.setMaximumSize(new java.awt.Dimension(85, 30));
        inputNombre.setMinimumSize(new java.awt.Dimension(85, 30));
        inputNombre.setPreferredSize(new java.awt.Dimension(85, 30));

        jLabelTelefono.setText("Telefono*");

        inputTelefono.setToolTipText("Telefono de contacto");
        inputTelefono.setMaximumSize(new java.awt.Dimension(85, 30));
        inputTelefono.setMinimumSize(new java.awt.Dimension(85, 30));
        inputTelefono.setPreferredSize(new java.awt.Dimension(85, 30));

        jLabelDirSocial.setText("Dir. Social*");

        inputDireccionSocial.setToolTipText("Direccion Social del alojamiento");
        inputDireccionSocial.setMaximumSize(new java.awt.Dimension(85, 30));
        inputDireccionSocial.setMinimumSize(new java.awt.Dimension(85, 30));
        inputDireccionSocial.setPreferredSize(new java.awt.Dimension(85, 30));

        jLabelRazonSocial.setText("Razón Social*");

        inputRazonSocial.setToolTipText("Razon social del alojamiento");
        inputRazonSocial.setMaximumSize(new java.awt.Dimension(85, 30));
        inputRazonSocial.setMinimumSize(new java.awt.Dimension(85, 30));
        inputRazonSocial.setPreferredSize(new java.awt.Dimension(85, 30));

        LabelFechaApertura.setText("Fecha Apertura*");

        sliderValoracion.setMajorTickSpacing(1);
        sliderValoracion.setMaximum(5);
        sliderValoracion.setMinimum(1);
        sliderValoracion.setPaintLabels(true);
        sliderValoracion.setSnapToTicks(true);
        sliderValoracion.setToolTipText("Valoracion media de los clientes");
        sliderValoracion.setValue(3);
        sliderValoracion.setMaximumSize(new java.awt.Dimension(311, 31));
        sliderValoracion.setMinimumSize(new java.awt.Dimension(311, 31));
        sliderValoracion.setPreferredSize(new java.awt.Dimension(311, 31));

        jLabelProvincia.setText("Provincia*");

        inputBoxProvincia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Huesca", "Zaragoza", "Teruel" }));
        inputBoxProvincia.setToolTipText("Provincia donde esta ubicada el alojamiento");

        jLabelValoracion.setText("Valoración*");

        botonAceptar.setText("Añadir");
        botonAceptar.setToolTipText("Añadir la entrada a la BDD");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");
        botonCancelar.setToolTipText("Volver atrás");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        jLabelDescripcion.setText("Descripción");

        inputDescripcion.setToolTipText("Descripcion breve de las caracteristicas del alojamiento.");
        inputDescripcion.setMaximumSize(new java.awt.Dimension(311, 79));
        inputDescripcion.setMinimumSize(new java.awt.Dimension(311, 79));
        inputDescripcion.setName(""); // NOI18N
        inputDescripcion.setPreferredSize(new java.awt.Dimension(311, 79));

        jLabelTituloVentana.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabelTituloVentana.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelTituloVentana.setText("Alta Alojamiento");

        inputFecha.setText("dd/mm/yyyy");
        inputFecha.setToolTipText("Formato: dd/mm/yyyy");
        inputFecha.setMaximumSize(new java.awt.Dimension(85, 30));
        inputFecha.setMinimumSize(new java.awt.Dimension(85, 30));
        inputFecha.setPreferredSize(new java.awt.Dimension(85, 30));
        inputFecha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputFechaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputFechaFocusLost(evt);
            }
        });

        jLabelNumHabitaciones.setText("Num. Habitaciones*");

        spinnerNumHabitaciones.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(0), null, Integer.valueOf(1)));
        spinnerNumHabitaciones.setMaximumSize(new java.awt.Dimension(85, 30));
        spinnerNumHabitaciones.setMinimumSize(new java.awt.Dimension(85, 30));
        spinnerNumHabitaciones.setPreferredSize(new java.awt.Dimension(85, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabelValoracion)
                                .addComponent(jLabelDescripcion))
                            .addComponent(jLabelDirSocial, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(jLabelProvincia, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabelTelefono))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(inputDescripcion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addComponent(sliderValoracion, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabelTituloVentana)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(inputBoxProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(inputDireccionSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(inputTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(LabelFechaApertura)
                                        .addComponent(jLabelNombre)
                                        .addComponent(jLabelRazonSocial)
                                        .addComponent(jLabelNumHabitaciones))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(inputFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spinnerNumHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(inputRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(inputNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTituloVentana)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputDireccionSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDirSocial))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTelefono))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputBoxProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelProvincia)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNombre)
                            .addComponent(inputNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelRazonSocial)
                            .addComponent(inputRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNumHabitaciones)
                            .addComponent(spinnerNumHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDescripcion))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sliderValoracion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelValoracion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        if(checkCompletoInput()) {
            if(alojamiento == null) insert(); //Depende de la version de ventana en la que estemos.
            else update();
        }
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        this.dispose();
        VP.setVisible(true);
    }//GEN-LAST:event_botonCancelarActionPerformed
   
    /*
        Problemas para mostrar como introducir el formato de la fecha, al hacer focus quita el formato requerido.
    */
    private void inputFechaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputFechaFocusGained
        String input;
        
        try {
            input = this.inputFecha.getText();
            
            if(input.matches("dd/mm/yyyy")) this.inputFecha.setText(null);
        } catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. NullPointerException: \n" +ex.getLocalizedMessage());
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. Problema con la conversion de la fecha: \n" +ex.getLocalizedMessage());
        } catch(ClassCastException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. Problema generico: \n" +ex.getLocalizedMessage());
        }
    }//GEN-LAST:event_inputFechaFocusGained

    /*
        Y al perder el focus, si no se ha introducido nada, o un espacio en blanco, lo vuelve a poner.
    */
    private void inputFechaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputFechaFocusLost
        String input;
        
        try {
            input = this.inputFecha.getText();
            
            if(input.matches("") || input.matches(" ")) this.inputFecha.setText("dd/mm/yyyy");
        } catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. NullPointerException: \n" +ex.getLocalizedMessage());
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. Problema con la conversion de la fecha: \n" +ex.getLocalizedMessage());
        } catch(ClassCastException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. Problema generico: \n" +ex.getLocalizedMessage());
        }
    }//GEN-LAST:event_inputFechaFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelFechaApertura;
    protected javax.swing.JButton botonAceptar;
    protected javax.swing.JButton botonCancelar;
    protected javax.swing.JComboBox inputBoxProvincia;
    protected javax.swing.JTextField inputDescripcion;
    protected javax.swing.JTextField inputDireccionSocial;
    protected javax.swing.JTextField inputFecha;
    protected javax.swing.JTextField inputNombre;
    protected javax.swing.JTextField inputRazonSocial;
    protected javax.swing.JTextField inputTelefono;
    private javax.swing.JLabel jLabelDescripcion;
    private javax.swing.JLabel jLabelDirSocial;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelNumHabitaciones;
    private javax.swing.JLabel jLabelProvincia;
    private javax.swing.JLabel jLabelRazonSocial;
    private javax.swing.JLabel jLabelTelefono;
    protected javax.swing.JLabel jLabelTituloVentana;
    private javax.swing.JLabel jLabelValoracion;
    protected javax.swing.JSlider sliderValoracion;
    protected javax.swing.JSpinner spinnerNumHabitaciones;
    // End of variables declaration//GEN-END:variables
}
