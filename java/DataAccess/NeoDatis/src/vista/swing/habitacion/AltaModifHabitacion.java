/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.swing.habitacion;

import controlador.datos.NeoDatis;
import controlador.dto.Alojamiento;
import controlador.dto.Habitacion;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 * @author Mario Codes Sánchez
 * @since 28/02/2017
 */
public class AltaModifHabitacion extends javax.swing.JFrame {
    private ODB odb = null;
    private boolean modo_alta = true;
    private Habitacion habitacion = null;
    private Objects<Alojamiento> objects = null;
    
    {{
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }}
    
    /**
     * Version que llamaremos para hacer el alta de Habitacion.
     */
    public AltaModifHabitacion() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Alta de una nueva Habitación");
        cargarAlojamientos();
    }
    
    /**
     * Version que llamaremos para hacer la modificacion, pasandole una HabitacionDTO para cargar sus datos en la ventana.
     * @param hab
     */
    public AltaModifHabitacion(Habitacion hab) {
        initComponents();
        
        this.setTitle("Modificar una Habitación Existente");
        this.modo_alta = false;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.habitacion = hab;
        this.jComboBoxAlojamientos.setEnabled(false);
        cargarAlojamientos();
        cargadoDatosHabitacion(hab);
    }
    
    /**
     * Cargado de Alojamientos al comboBox para poder elegir.
     * @param alojamientos Listado de todos los Alojamientos disponibles.
     */
    private void cargarAlojamientos() {
        objects = NeoDatis.getAlojamientos();
        
        this.jComboBoxAlojamientos.addItem(null); //Para poder añadir sin ninguno.
        while(objects.hasNext()) {
            this.jComboBoxAlojamientos.addItem((Alojamiento) objects.next()); //todo: check, no se si esto se puede hacer.
        }
        
        objects.reset();
    }

    /**
     * Recoleccion y pasado de datos desde la ventana grafica a un HabitacionDTO para operar con el.
     * @param habDTO 
     */
    private Habitacion getDatos() {
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(this.jTextFieldInputNumero.getText());
        habitacion.setPrecio((long) this.inputPrecio.getValue());
        habitacion.setExtrasHabitacion(this.inputExtras.getText());
        habitacion.setTipoHabitacion((String) this.jComboBoxTipoHabitacion.getSelectedItem());
        habitacion.setResenias(this.jTextFieldInputResenias.getText());
        return habitacion;
    }

    private void getDatos(Habitacion hab) {
        String numero = this.jTextFieldInputNumero.getText();
        long precio = (long) this.inputPrecio.getValue();
        String extras = this.inputExtras.getText();
        String tipo = (String) this.jComboBoxTipoHabitacion.getSelectedItem();
        String resenias = this.jTextFieldInputResenias.getText();
        
        hab.setNumero(numero);
        hab.setPrecio(precio);
        hab.setExtrasHabitacion(extras);
        hab.setTipoHabitacion(tipo);
        hab.setResenias(resenias);
    }
    
    private void alta() {
        ODB odb = ODBFactory.open(NeoDatis.getDATABASE());
        Habitacion habitacion = getDatos();
        Alojamiento aloja = (Alojamiento) this.jComboBoxAlojamientos.getSelectedItem();
        Alojamiento aloja2 = null;
        if(aloja != null) {
            String nombre = aloja.getNombre();
            String dirSoc = aloja.getDireccionSocial();
            
            IQuery queryAloja = new CriteriaQuery(Alojamiento.class, Where.and().add(Where.equal("nombre", nombre)).add(Where.equal("direccionSocial", dirSoc)));
            aloja2 = (Alojamiento) odb.getObjects(queryAloja).getFirst();
            
            aloja2.getSetHabitaciones().add(habitacion);
            habitacion.setAlojamiento(aloja2);
        }
        
        odb.store(habitacion);
        if(aloja2 != null) odb.store(aloja2);
        odb.close();
        JOptionPane.showMessageDialog(this, "Habitacion introducida correctamente.");
    }
    
    private void update() {
        ODB odb = ODBFactory.open(NeoDatis.getDATABASE());
        IQuery query = new CriteriaQuery(Habitacion.class, Where.and().add(Where.equal("numero", habitacion.getNumero())).add(Where.equal("tipoHabitacion", habitacion.getTipoHabitacion())).add(Where.equal("precio", habitacion.getPrecio())));
        
        this.habitacion = (Habitacion) odb.getObjects(query).getFirst();
        
        getDatos(habitacion);
        Alojamiento aloja = (Alojamiento) this.jComboBoxAlojamientos.getSelectedItem();
        if(aloja != null) {
            String nombre = aloja.getNombre();
            String dirSoc = aloja.getDireccionSocial();
            
            IQuery queryAloja = new CriteriaQuery(Alojamiento.class, Where.and().add(Where.equal("nombre", nombre)).add(Where.equal("direccionSocial", dirSoc)));
            Alojamiento aloja2 = (Alojamiento) odb.getObjects(queryAloja).getFirst();
            
            aloja2.getSetHabitaciones().add(habitacion);
            odb.store(aloja2);
            habitacion.setAlojamiento(aloja2);
        }
        odb.store(habitacion);
        odb.close();
        JOptionPane.showMessageDialog(this, "Habitacion modificada con exito.");
    }
    
    private void ejecucion() {
        if(modo_alta) alta();
        else update();
    }
    
    /**
     * Recoleccion de los campos de la ventana e instanciacion de un alojamiento con esos datos. Previamente se ha realizado la comprobacion de que los datos sean correctos.
     */
    private void recoleccionDatosVentana() {            
        //Recoleccion y almacenado de los valores actuales en la ventana.
        try {
            if(modo_alta) {
                
            } else {
//                try {
//                    IQuery query = new CriteriaQuery(Habitacion.class, Where.and().add(Where.equal("numero", habitacion.getNumero())).add(Where.equal("precio", habitacion.getPrecio())).add(Where.equal("resenias", habitacion.getResenias())).add(Where.like("tipoHabitacion", habitacion.getTipoHabitacion())));
//                    Habitacion h = (Habitacion) odb.getObjects(query).getFirst();
//                    recoleccionDatos(h);
//                    Alojamiento a = (Alojamiento) this.jComboBoxAlojamientos.getSelectedItem();
//                    if(a != null) a.getSetHabitaciones().add(h);
//                    odb.store(h);
//                    JOptionPane.showMessageDialog(this, "Habitacion modificada correctamente.");
//                }catch(IndexOutOfBoundsException|NullPointerException ex) {
//                    System.out.println("Ninguna habitacion con esos datos." +ex.getLocalizedMessage());
//                }
            }
            this.dispose();
        } catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "ERROR. NullPointerException. Mirar Output. \n");
            ex.printStackTrace();
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
        String tipo = habDTO.getTipoHabitacion();

        switch(tipo) {
            case "simple":
                this.jComboBoxTipoHabitacion.setSelectedIndex(0);
                break;
            case "doble":
                this.jComboBoxTipoHabitacion.setSelectedIndex(1);
                break;
            case "triple":
                this.jComboBoxTipoHabitacion.setSelectedIndex(2);
                break;
            case "cuadruple":
                this.jComboBoxTipoHabitacion.setSelectedIndex(3);
                break;
            default:
                this.jComboBoxTipoHabitacion.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "ERROR. Problema con valor Tipo_Habitacion.");
                break;
        }
    }

    private void rellenoAlojamiento(Habitacion hab) {
        for(Alojamiento a: objects) {
            if(a.getRazonSocial().matches(hab.getAlojamiento().getRazonSocial())) jComboBoxAlojamientos.setSelectedItem(a);
        }
    }
    
    /**
     * Rellena la ventana con los datos de HabitacionDTO pasado por contructor.
     * @param habDTO Habitacion cuyos datos se mostraran en la ventana.
     */
    private void cargadoDatosHabitacion(Habitacion habDTO) {
        try {
            this.inputPrecio.setValue(habDTO.getPrecio());

            if(habDTO.getExtrasHabitacion() != null) this.inputExtras.setText(habDTO.getExtrasHabitacion());
            if(habDTO.getResenias() != null) this.jTextFieldInputResenias.setText(habDTO.getResenias());
            this.jTextFieldInputNumero.setText(habDTO.getNumero());
            rellenoAlojamiento(habDTO);
            rellenoAutoTipoHabitacion(habDTO);
        }catch (NullPointerException ex) {} //Hay algunos campos que no son obligatorios.
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
        jTextFieldInputResenias = new javax.swing.JTextField();
        inputPrecio = new javax.swing.JSpinner();
        jLabelTipoHabitacion = new javax.swing.JLabel();
        jLabelPrecio = new javax.swing.JLabel();
        jLabelAloja = new javax.swing.JLabel();
        jComboBoxAlojamientos = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabelExtras = new javax.swing.JLabel();
        jTextFieldInputNumero = new javax.swing.JTextField();
        inputExtras = new javax.swing.JTextField();
        jComboBoxTipoHabitacion = new javax.swing.JComboBox();
        jLabelResenias = new javax.swing.JLabel();
        botonCancelar = new javax.swing.JButton();
        botonAniadir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(22, 0));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Alta de Habitacion"));

        inputPrecio.setModel(new javax.swing.SpinnerNumberModel(Long.valueOf(1L), Long.valueOf(1L), null, Long.valueOf(100L)));

        jLabelTipoHabitacion.setText("Tipo");

        jLabelPrecio.setText("Precio / Noche");

        jLabelAloja.setText("Alojamiento");

        jLabel2.setText("Numero");

        jLabelExtras.setText("Extras");

        inputExtras.setToolTipText("Descripcion breve de las caracteristicas del alojamiento.");

        jComboBoxTipoHabitacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "simple", "doble", "triple", "cuadruple" }));

        jLabelResenias.setText("Reseñas");

        botonCancelar.setText("Cancelar");
        botonCancelar.setToolTipText("Volver atrás");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        botonAniadir.setText("Guardar");
        botonAniadir.setToolTipText("Añadir la entrada a la BDD");
        botonAniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAniadirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAloja, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelResenias, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelExtras, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelTipoHabitacion, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBoxTipoHabitacion, 0, 88, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelPrecio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldInputNumero, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inputExtras, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldInputResenias, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBoxAlojamientos, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonAniadir, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonCancelar)
                .addGap(60, 60, 60))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxTipoHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPrecio)
                    .addComponent(inputPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTipoHabitacion))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldInputNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelExtras))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputResenias, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelResenias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxAlojamientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAloja))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAniadir)
                    .addComponent(botonCancelar))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
    
    private void botonAniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAniadirActionPerformed
        ejecucion();
    }//GEN-LAST:event_botonAniadirActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        this.dispose();
        if(odb != null && !odb.isClosed()) odb.close();
    }//GEN-LAST:event_botonCancelarActionPerformed
     
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton botonAniadir;
    protected javax.swing.JButton botonCancelar;
    protected javax.swing.JTextField inputExtras;
    private javax.swing.JSpinner inputPrecio;
    private javax.swing.JComboBox jComboBoxAlojamientos;
    private javax.swing.JComboBox jComboBoxTipoHabitacion;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelAloja;
    private javax.swing.JLabel jLabelExtras;
    private javax.swing.JLabel jLabelPrecio;
    private javax.swing.JLabel jLabelResenias;
    private javax.swing.JLabel jLabelTipoHabitacion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldInputNumero;
    private javax.swing.JTextField jTextFieldInputResenias;
    // End of variables declaration//GEN-END:variables
}
