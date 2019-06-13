/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.swing.alojamientos;

import controlador.datos.NeoDatis;
import controlador.dto.Alojamiento;
import controlador.dto.Habitacion;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Ventana de doble funcionalidad. Segun que constructor se utilice, sirve para dar de Alta un Alojamiento completamente nuevo; O para modificar los datos de uno ya existente.
 * @author Mario Codes Sánchez
 * @since 01/03/2017
 */
public class VentanaAltaYModifAlojamiento extends javax.swing.JFrame {
    private boolean modo_alta = true; //Si estamos en la version de alta o modificacion.
    private Alojamiento alojamiento;
    
    {{
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }}
    
    /**
     * Version que llamaremos para hacer el alta de Alojamiento.
     */
    public VentanaAltaYModifAlojamiento() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.setTitle("Alta de un alojamiento nuevo");
    }
    
    /**
     * Version que llamaremos para hacer la modificacion, pasandole un AlojamientoDTO para cargar sus datos en la ventana.
     * @param alojamiento AlojamientoDTO instanciado cuyos datos modificaremos.
     */
    public VentanaAltaYModifAlojamiento(Alojamiento alojamiento) {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Modificacion de Alojamiento Existente");
         
        this.modo_alta = false;
        this.inputNombre.setEnabled(false);
        this.inputRazonSocial.setEnabled(false);
        
        this.alojamiento = alojamiento;
        cargadoDatosAlojamiento(alojamiento);
    }
    /**
     * Recopilacion de checks. Mira que no haya nada obligatorio vacio, que el telefono sea numerico y que no se intente meter la fecha por default.
     * @return True si correcto.
     */
    private boolean checkInput() {
        if(checkObligatorios()) { //Chequeo de que todos los campos estan llenos
            if(checkInputTlfNumericoExprRegular(this.inputTelefono.getText())) { //Chequeo de que el telefono solo contenga numeros y longitud correcta.
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
    private boolean checkObligatorios() {
        return (!this.inputNombre.getText().isEmpty() && !this.inputRazonSocial.getText().isEmpty());
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
    private boolean checkInputTlfNumericoExprRegular(String telefono) {
        try {
            if(telefono == null || telefono.isEmpty()) return true;
            Pattern pat = Pattern.compile("[0-9]{9,13}");
            Matcher mat = pat.matcher(telefono);

            return mat.matches();
        } catch(NumberFormatException ex) {
            return false;
        }
    }
    
    /**
     * Recoleccion de todos los datos de la ventana. Devueltos en un Alojamiento instanciado.
     * Lo uso para hacer insert de un Alojamiento nuevo.
     * @return Alojamiento con todos los datos de la ventana.
     */
    private Alojamiento getAlojamiento() {
        String nombre = this.inputNombre.getText();
        String razonSocial = this.inputRazonSocial.getText();
        int numHabitaciones = (int) this.spinnerNumHabitaciones.getValue();
        String telefono = this.inputTelefono.getText();
        String direccionSocial = this.inputDireccionSocial.getText();
        String provincia = (String) this.inputBoxProvincia.getSelectedItem();
        String descripcion = this.inputDescripcion.getText();
        int valoracion = this.sliderValoracion.getValue();
        String fecha = this.inputFecha.getText();
        Set<Habitacion> set = new HashSet<>();
        
        Alojamiento aloja = new Alojamiento(nombre, razonSocial, fecha);
        
        aloja.setNumHabitaciones(numHabitaciones);
        aloja.setValoracion(valoracion);
        aloja.setSetHabitaciones(set);
        
        aloja.setTelefonoContacto(telefono);
        aloja.setDireccionSocial(direccionSocial);
        aloja.setProvincia(provincia);
        aloja.setDescripcion(descripcion);
        
        return aloja;
    }
    
    /**
     * Recoleccion de los datos de la ventana. Insertados en el Alojamiento pasado como parametro.
     * Lo uso para hacer update ya que por NeoDatis el objeto que obtengo debe ser el mismo que inserto para hacer update (punteros de memoria).
     * @param a Alojamiento donde inserto los datos nuevos.
     */
    private void getDatos(Alojamiento a) {
        String nombre = this.inputNombre.getText();
        String razonSocial = this.inputRazonSocial.getText();
        int numHabitaciones = (int) this.spinnerNumHabitaciones.getValue();
        String telefono = this.inputTelefono.getText();
        String direccionSocial = this.inputDireccionSocial.getText();
        String provincia = (String) this.inputBoxProvincia.getSelectedItem();
        String descripcion = this.inputDescripcion.getText();
        int valoracion = this.sliderValoracion.getValue();
        String fecha = this.inputFecha.getText();
        Set<Habitacion> set = new HashSet<>();
        
        a.setNombre(nombre);
        a.setRazonSocial(razonSocial);
        a.setFechaApertura(fecha);
        
        a.setNumHabitaciones(numHabitaciones);
        a.setValoracion(valoracion);
        a.setSetHabitaciones(set);
        
        a.setTelefonoContacto(telefono);
        a.setDireccionSocial(direccionSocial);
        a.setProvincia(provincia);
        a.setDescripcion(descripcion);
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
     * Insert de un Alojamiento con los datos de la ventana.
     */
    private void alta() {
        Alojamiento aloja = getAlojamiento();
        getDatos(aloja);
        NeoDatis.insert(aloja);
        JOptionPane.showMessageDialog(this, "Alojamiento dado de alta exitosamente.");
        reseteoCamposVentana();
    }
    
    private void update() {
        getDatos(alojamiento);
        NeoDatis.updateAlojamiento(alojamiento);
        reseteoCamposVentana();
        JOptionPane.showMessageDialog(this, "Alojamiento modificado exitosamente.");
        this.dispose();
    }
    
    /**
     * Recopilacion de la ejecucion completa del programa. Metodo a llamar on Click.
     */
    private void ejecucion() {
        if(checkInput()) {
            if(modo_alta) alta();
            else update();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        inputDescripcion = new javax.swing.JTextField();
        jLabelRazonSocial = new javax.swing.JLabel();
        inputRazonSocial = new javax.swing.JTextField();
        inputFecha = new javax.swing.JTextField();
        LabelFechaApertura = new javax.swing.JLabel();
        jLabelNumHabitaciones = new javax.swing.JLabel();
        sliderValoracion = new javax.swing.JSlider();
        spinnerNumHabitaciones = new javax.swing.JSpinner();
        jLabelProvincia = new javax.swing.JLabel();
        jLabelNombre = new javax.swing.JLabel();
        inputBoxProvincia = new javax.swing.JComboBox();
        inputNombre = new javax.swing.JTextField();
        jLabelValoracion = new javax.swing.JLabel();
        jLabelTelefono = new javax.swing.JLabel();
        botonAceptar = new javax.swing.JButton();
        inputTelefono = new javax.swing.JTextField();
        botonCancelar = new javax.swing.JButton();
        jLabelDirSocial = new javax.swing.JLabel();
        jLabelDescripcion = new javax.swing.JLabel();
        inputDireccionSocial = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(22, 0));
        setResizable(false);

        inputDescripcion.setToolTipText("Descripcion breve de las caracteristicas del alojamiento.");
        inputDescripcion.setMaximumSize(new java.awt.Dimension(311, 79));
        inputDescripcion.setMinimumSize(new java.awt.Dimension(311, 79));
        inputDescripcion.setName(""); // NOI18N
        inputDescripcion.setPreferredSize(new java.awt.Dimension(311, 79));

        jLabelRazonSocial.setText("Razón Social*");

        inputRazonSocial.setToolTipText("Razon social del alojamiento");
        inputRazonSocial.setMaximumSize(new java.awt.Dimension(85, 30));
        inputRazonSocial.setMinimumSize(new java.awt.Dimension(85, 30));
        inputRazonSocial.setPreferredSize(new java.awt.Dimension(85, 30));

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

        LabelFechaApertura.setText("Fecha Apertura*");

        jLabelNumHabitaciones.setText("Num. Habitaciones");

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

        spinnerNumHabitaciones.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(0), null, Integer.valueOf(1)));
        spinnerNumHabitaciones.setMaximumSize(new java.awt.Dimension(85, 30));
        spinnerNumHabitaciones.setMinimumSize(new java.awt.Dimension(85, 30));
        spinnerNumHabitaciones.setPreferredSize(new java.awt.Dimension(85, 30));

        jLabelProvincia.setText("Provincia");

        jLabelNombre.setText("Nombre*");

        inputBoxProvincia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Huesca", "Zaragoza", "Teruel" }));
        inputBoxProvincia.setToolTipText("Provincia donde esta ubicada el alojamiento");
        inputBoxProvincia.setMaximumSize(new java.awt.Dimension(85, 30));
        inputBoxProvincia.setMinimumSize(new java.awt.Dimension(85, 30));
        inputBoxProvincia.setPreferredSize(new java.awt.Dimension(85, 30));

        inputNombre.setToolTipText("Nombre del alojamiento");
        inputNombre.setMaximumSize(new java.awt.Dimension(85, 30));
        inputNombre.setMinimumSize(new java.awt.Dimension(85, 30));
        inputNombre.setPreferredSize(new java.awt.Dimension(85, 30));

        jLabelValoracion.setText("Valoración");

        jLabelTelefono.setText("Telefono");

        botonAceptar.setText("Guardar");
        botonAceptar.setToolTipText("Añadir la entrada a la BDD");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        inputTelefono.setToolTipText("Telefono de contacto");
        inputTelefono.setMaximumSize(new java.awt.Dimension(85, 30));
        inputTelefono.setMinimumSize(new java.awt.Dimension(85, 30));
        inputTelefono.setPreferredSize(new java.awt.Dimension(85, 30));

        botonCancelar.setText("Cancelar");
        botonCancelar.setToolTipText("Volver atrás");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        jLabelDirSocial.setText("Dir. Social");

        jLabelDescripcion.setText("Descripción");

        inputDireccionSocial.setToolTipText("Direccion Social del alojamiento");
        inputDireccionSocial.setMaximumSize(new java.awt.Dimension(85, 30));
        inputDireccionSocial.setMinimumSize(new java.awt.Dimension(85, 30));
        inputDireccionSocial.setPreferredSize(new java.awt.Dimension(85, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelTelefono)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(inputTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelNombre)
                                .addGap(18, 18, 18)
                                .addComponent(inputNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelDirSocial)
                                .addGap(18, 18, 18)
                                .addComponent(inputDireccionSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelRazonSocial)
                                .addGap(33, 33, 33)
                                .addComponent(inputRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelNumHabitaciones)
                                    .addComponent(LabelFechaApertura)
                                    .addComponent(jLabelProvincia))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(spinnerNumHabitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(inputBoxProvincia, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDescripcion)
                            .addComponent(jLabelValoracion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(71, 71, 71)
                                        .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(sliderValoracion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelNombre)
                        .addComponent(inputNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelRazonSocial)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerNumHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNumHabitaciones))
                        .addGap(43, 43, 43)
                        .addComponent(inputBoxProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputDireccionSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDirSocial))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTelefono)
                            .addComponent(inputTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelProvincia)
                        .addGap(27, 27, 27)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDescripcion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sliderValoracion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelValoracion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        ejecucion();
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        this.dispose();
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
    private javax.swing.JLabel jLabelValoracion;
    private javax.swing.JPanel jPanel1;
    protected javax.swing.JSlider sliderValoracion;
    protected javax.swing.JSpinner spinnerNumHabitaciones;
    // End of variables declaration//GEN-END:variables
}
