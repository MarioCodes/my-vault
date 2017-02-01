/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.swing.vista;

import aplicacion.facade.Facade;
import controlador.datos.Singleton;
import dto.VistaActividadesAlojamiento;
import dto.VistaActividadesAlojamientoId;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import vista.swing.comun.VentanaPrincipal;

/**
 * Ventana encargada de hacer las altas y modificaciones para la gestion de la vista.
 * @author Mario Codes Sánchez
 * @since 01/02/2017
 */
public class VentanaAltaYModifVista extends javax.swing.JFrame {
    private final VentanaPrincipal VP = Singleton.getVentanaPrincipalObtencionSingleton();
    private VistaActividadesAlojamiento vista = null;
    
    /**
     * Creates new form VentanaAltaYModifVista
     */
    public VentanaAltaYModifVista() {
        initComponents();
        
        this.setTitle("Alta de Conjunto de Datos Nuevo.");
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        VP.setVisible(false);
    }
    
    public VentanaAltaYModifVista(VistaActividadesAlojamiento vista) {
        initComponents();
        
        this.jLabelMainTitulo.setText("Modificacion Vista");
        this.setTitle("Modificacion de Conjunto de Datos.");
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.vista = vista;
        
        setDatosVistaModif(vista);
        VP.setVisible(false);
    }
    
    /**
     * Se encarga de dar de Alta una instancia de la Vista completamente Nueva.
     * @param vaaID VistaID con todos los datos para dar de Alta introducidos.
     */
    private void altaVista(VistaActividadesAlojamientoId vaaID) {
        Session s = Facade.abrirSessionHibernate();
        
        VistaActividadesAlojamiento vaa = new VistaActividadesAlojamiento(vaaID);
        s.save(vaa);

        Facade.cerrarSessionHibernate(s);
    }
    
    private void updateVista(VistaActividadesAlojamientoId vaaID) {
        Session s = Facade.abrirSessionHibernate();
        
        VistaActividadesAlojamiento vaa = new VistaActividadesAlojamiento(vaaID);
        s.update(vaa);
        
        Facade.cerrarSessionHibernate(s);
        System.out.println("SUUU");
    }
    
    private int setSelectedIndexComboBox(String provincia) {
        switch(provincia.toLowerCase()) {
            case "huesca":
                return 0;
            case "zaragoza":
                return 1;
            case "teruel":
                return 2;
            default:
                System.out.println("Problema con VentanaAltaVista.setSelectedIndexComboBox");
                return 0;
        }
    }
    
    /**
     * Set de los datos de una instancia de la Vista en Pantalla.
     * @param vista Vista que pasa a VistaID para extraer los datos.
     */
    private void setDatosVistaModif(VistaActividadesAlojamiento vista) {
        VistaActividadesAlojamientoId vistaID = vista.getId();
        this.jTextFieldInputNombreAloj.setText(vistaID.getNombreAlojamiento());
        this.jTextFieldInputTelefono.setText(vistaID.getTelefonoContacto());
        this.jTextFieldInputDirSocial.setText(vistaID.getDireccionSocial());
        this.jTextFieldInputRazonSocial.setText(vistaID.getRazonSocial());
        this.jSpinnerValoracion.setValue(vistaID.getValoracionAlojamiento());
        this.jTextFieldInputFechaApertura.setText(vistaID.getFechaApertura());
        this.jSpinnerHabitaciones.setValue(vistaID.getNumeroHabitaciones());
        this.jComboBoxProvincia.setSelectedIndex(setSelectedIndexComboBox(vistaID.getProvincia()));
        this.jTextPaneInputDescripcionAloj.setText(vistaID.getDescripcionAlojamiento());
        
        this.jTextFieldInputIDActividad.setText(Integer.toString(vistaID.getIdActividad()));
        this.jTextFieldInputNombreActiv.setText(vistaID.getNombreActividad());
        this.jTextFieldInputFechaActiv.setText(vistaID.getDiaRealizacion());
        this.jTextFieldInputDiaSemana.setText(vistaID.getDiaSemana());
        this.jTextFieldInputHoraInicioAc.setText(vistaID.getHoraInicio());
        this.jTextFieldInputHoraFinActiv.setText(vistaID.getHoraFin());
        this.jSpinnerDificultad.setValue(vistaID.getDificultad());
        this.jSpinnerCapacidadActiv.setValue(Integer.parseInt(vistaID.getCapacidad()));
        this.jTextFieldInputLocalizacion.setText(vistaID.getLocalizacion());
        this.jTextFieldInputNombreGuia.setText(vistaID.getNombreGuia());
        this.jTextPaneInputDescrip.setText(vistaID.getDescripcionActividad());
    }
    
    /**
     * Updatea los datos de la vista mediante HQL.
     */
    private void updateDatosVista() {
        Session s = Facade.abrirSessionHibernate();
        
        Query q = s.createQuery("UPDATE VistaActividadesAlojamiento "
                                    + "SET ID_ACTIVIDAD = :idActiv, NOMBRE_ALOJAMIENTO = :nombreAloj, DESCRIPCION_ALOJAMIENTO = :descripAloj, "
                                    + "DIRECCION_SOCIAL = :dirSoc, RAZON_SOCIAL = :razSoc, TELEFONO_CONTACTO = :telefono, VALORACION_ALOJAMIENTO = :valoracion, FECHA_APERTURA = :fechaApertura, "
                                    + "NUMERO_HABITACIONES = :numHabitaciones, PROVINCIA = :provincia, NOMBRE_ACTIVIDAD = :nombreActiv, DESCRIPCION_ACTIVIDAD = :descripActiv, "
                                    + "DIA_REALIZACION = :diaRealizacion, DIA_SEMANA = :diaSemana, HORA_INICIO = :horaInicio, HORA_FIN = :horaFin, LOCALIZACION = :localizacion, "
                                    + "DIFICULTAD = :dificultad, CAPACIDAD = :capacidad, NOMBRE_GUIA = :nombreGuia "
                                    + "WHERE ID_ALOJAMIENTO = :idAloj");
        
        q.setParameter("idActiv", Integer.parseInt(this.jTextFieldInputIDActividad.getText()));
        q.setParameter("nombreAloj", this.jTextFieldInputNombreAloj.getText());
        q.setParameter("descripAloj", this.jTextPaneInputDescripcionAloj.getText());
        q.setParameter("dirSoc", this.jTextFieldInputDirSocial.getText());
        q.setParameter("razSoc", this.jTextFieldInputRazonSocial.getText());
        q.setParameter("telefono", this.jTextFieldInputTelefono.getText());
        q.setParameter("valoracion", (int) this.jSpinnerValoracion.getValue());
        q.setParameter("fechaApertura", this.jTextFieldInputFechaApertura.getText());
        q.setParameter("numHabitaciones", (int) this.jSpinnerHabitaciones.getValue());
        q.setParameter("provincia", (String) this.jComboBoxProvincia.getSelectedItem());
        q.setParameter("nombreActiv", this.jTextFieldInputNombreActiv.getText());
        q.setParameter("descripActiv", this.jTextPaneInputDescrip.getText());
        q.setParameter("diaRealizacion", this.jTextFieldInputFechaActiv.getText());
        q.setParameter("diaSemana", this.jTextFieldInputDiaSemana.getText());
        q.setParameter("horaInicio", this.jTextFieldInputHoraInicioAc.getText());
        q.setParameter("horaFin", this.jTextFieldInputHoraFinActiv.getText());
        q.setParameter("localizacion", this.jTextFieldInputLocalizacion.getText());
        q.setParameter("dificultad", (int) this.jSpinnerDificultad.getValue());
        q.setParameter("capacidad", this.jSpinnerCapacidadActiv.getValue().toString());
        q.setParameter("nombreGuia", this.jTextFieldInputNombreGuia.getText());
        q.setParameter("idAloj", vista.getId().getIdAlojamiento());
        
        q.executeUpdate();
        Facade.cerrarSessionHibernate(s);
    }
    
    /**
     * Ejecucion de recogida de datos de la Ventana.
     */
    private VistaActividadesAlojamientoId recogidaDatosInstanciaVistaID() {
        try {
            String nombreAloj = this.jTextFieldInputNombreAloj.getText();
            String telefono = this.jTextFieldInputTelefono.getText();
            String dirSocial = this.jTextFieldInputDirSocial.getText();
            String razSocial = this.jTextFieldInputRazonSocial.getText();
            int valoracion = (int) this.jSpinnerValoracion.getValue();
            String fechaApertura = this.jTextFieldInputFechaApertura.getText();
            int habitaciones = (int) this.jSpinnerHabitaciones.getValue();
            String provincia = (String) this.jComboBoxProvincia.getSelectedItem();
            String descripAloj = this.jTextPaneInputDescripcionAloj.getText();

            int idActividad = Integer.parseInt(this.jTextFieldInputIDActividad.getText()); //todo: cambiar esto a ID automatico.
            String nombreActiv = this.jTextFieldInputNombreActiv.getText();
            String fechaActiv = this.jTextFieldInputFechaActiv.getText();
            String diaSemana = this.jTextFieldInputDiaSemana.getText();
            String horaInicio = this.jTextFieldInputHoraInicioAc.getText();
            String horaFin = this.jTextFieldInputHoraFinActiv.getText();
            int dificultad = (int) this.jSpinnerDificultad.getValue();
            String capacidad = this.jSpinnerCapacidadActiv.getValue().toString();
            String localizacionActividad = this.jTextFieldInputLocalizacion.getText();
            String nombreGuia = this.jTextFieldInputNombreGuia.getText();
            String descripActiv = this.jTextPaneInputDescrip.getText();
            
            return new VistaActividadesAlojamientoId(0, idActividad, nombreAloj, descripAloj, dirSocial, razSocial, telefono, valoracion, fechaApertura, habitaciones, provincia, nombreActiv, descripActiv, fechaActiv, diaSemana, horaInicio, horaFin, localizacionActividad, dificultad, capacidad, nombreGuia);
        }catch(NumberFormatException | ClassCastException ex) {
            JOptionPane.showMessageDialog(this, "Campo numerico no valido.");
            return null;
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

        jLabel10 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanelDatosAlojamiento = new javax.swing.JPanel();
        jLabelTelefono = new javax.swing.JLabel();
        jTextFieldInputTelefono = new javax.swing.JTextField();
        jLabelNombre = new javax.swing.JLabel();
        jTextFieldInputNombreAloj = new javax.swing.JTextField();
        jTextFieldInputDirSocial = new javax.swing.JTextField();
        jLabelDirSocial = new javax.swing.JLabel();
        jTextFieldInputRazonSocial = new javax.swing.JTextField();
        jLabelRazonSocial = new javax.swing.JLabel();
        jSpinnerValoracion = new javax.swing.JSpinner();
        jLabelValoracion = new javax.swing.JLabel();
        jLabelFechaApertura = new javax.swing.JLabel();
        jTextFieldInputFechaApertura = new javax.swing.JTextField();
        jLabelHabitaciones = new javax.swing.JLabel();
        jSpinnerHabitaciones = new javax.swing.JSpinner();
        jLabelProvincia = new javax.swing.JLabel();
        jComboBoxProvincia = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPaneInputDescripcionAloj = new javax.swing.JTextPane();
        jLabelDescripcion = new javax.swing.JLabel();
        jPanelDatosActividad = new javax.swing.JPanel();
        jLabelIDActiv = new javax.swing.JLabel();
        jTextFieldInputIDActividad = new javax.swing.JTextField();
        jLabelNombreAc = new javax.swing.JLabel();
        jTextFieldInputNombreActiv = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneInputDescrip = new javax.swing.JTextPane();
        jLabelDescripAc = new javax.swing.JLabel();
        jTextFieldInputDiaSemana = new javax.swing.JTextField();
        jLabelDiaSemana = new javax.swing.JLabel();
        jTextFieldInputFechaActiv = new javax.swing.JTextField();
        jLabelFechaAc = new javax.swing.JLabel();
        jTextFieldInputHoraInicioAc = new javax.swing.JTextField();
        jLabelHoraIni = new javax.swing.JLabel();
        jTextFieldInputHoraFinActiv = new javax.swing.JTextField();
        jLabelHoraFin = new javax.swing.JLabel();
        jSpinnerCapacidadActiv = new javax.swing.JSpinner();
        jLabelCapacidad = new javax.swing.JLabel();
        jSpinnerDificultad = new javax.swing.JSpinner();
        jLabelDificultad = new javax.swing.JLabel();
        jTextFieldInputLocalizacion = new javax.swing.JTextField();
        jLabelLocalizacion = new javax.swing.JLabel();
        jLabelNombreGuia = new javax.swing.JLabel();
        jTextFieldInputNombreGuia = new javax.swing.JTextField();
        jButtonIntroducir = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jLabelMainTitulo = new javax.swing.JLabel();

        jLabel10.setText("jLabel10");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelDatosAlojamiento.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Alojamiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 2, 12))); // NOI18N

        jLabelTelefono.setText("Telefono");

        jLabelNombre.setText("Nombre");

        jLabelDirSocial.setText("Dir. Social");

        jLabelRazonSocial.setText("Razon Social");

        jSpinnerValoracion.setModel(new javax.swing.SpinnerNumberModel(1, 1, 5, 1));

        jLabelValoracion.setText("Valoracion");

        jLabelFechaApertura.setText("Fecha Apertura");

        jLabelHabitaciones.setText("Habitaciones");

        jSpinnerHabitaciones.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabelProvincia.setText("Provincia");

        jComboBoxProvincia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Huesca", "Zaragoza", "Teruel" }));

        jScrollPane2.setViewportView(jTextPaneInputDescripcionAloj);

        jLabelDescripcion.setText("Descripcion");

        javax.swing.GroupLayout jPanelDatosAlojamientoLayout = new javax.swing.GroupLayout(jPanelDatosAlojamiento);
        jPanelDatosAlojamiento.setLayout(jPanelDatosAlojamientoLayout);
        jPanelDatosAlojamientoLayout.setHorizontalGroup(
            jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosAlojamientoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanelDatosAlojamientoLayout.createSequentialGroup()
                            .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelValoracion)
                                .addComponent(jLabelHabitaciones))
                            .addGap(12, 12, 12)
                            .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelDatosAlojamientoLayout.createSequentialGroup()
                                    .addComponent(jSpinnerHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabelProvincia)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                    .addComponent(jComboBoxProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelDatosAlojamientoLayout.createSequentialGroup()
                                    .addComponent(jSpinnerValoracion, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabelFechaApertura))))
                        .addComponent(jLabelDescripcion)
                        .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldInputFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelDatosAlojamientoLayout.createSequentialGroup()
                                .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelRazonSocial)
                                    .addComponent(jLabelDirSocial)
                                    .addComponent(jLabelTelefono)
                                    .addComponent(jLabelNombre))
                                .addGap(12, 12, 12)
                                .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextFieldInputDirSocial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                    .addComponent(jTextFieldInputTelefono, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInputNombreAloj, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInputRazonSocial)))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelDatosAlojamientoLayout.setVerticalGroup(
            jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosAlojamientoLayout.createSequentialGroup()
                .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNombre)
                    .addComponent(jTextFieldInputNombreAloj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTelefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputDirSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDirSocial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelRazonSocial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerValoracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelValoracion)
                    .addComponent(jLabelFechaApertura)
                    .addComponent(jTextFieldInputFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelDatosAlojamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHabitaciones)
                    .addComponent(jSpinnerHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelProvincia)
                    .addComponent(jComboBoxProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jLabelDescripcion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelDatosActividad.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Actividad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 2, 12))); // NOI18N

        jLabelIDActiv.setText("ID");

        jLabelNombreAc.setText("Nombre");

        jScrollPane1.setViewportView(jTextPaneInputDescrip);

        jLabelDescripAc.setText("Descripcion");

        jLabelDiaSemana.setText("Dia Semana");

        jLabelFechaAc.setText("Fecha");

        jLabelHoraIni.setText("Hora Inicio");

        jLabelHoraFin.setText("Hora Fin");

        jSpinnerCapacidadActiv.setModel(new javax.swing.SpinnerNumberModel(1, 1, 20, 1));

        jLabelCapacidad.setText("Capacidad");

        jSpinnerDificultad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));

        jLabelDificultad.setText("Dificultad");

        jLabelLocalizacion.setText("Localizacion");

        jLabelNombreGuia.setText("Nombre Guia");

        javax.swing.GroupLayout jPanelDatosActividadLayout = new javax.swing.GroupLayout(jPanelDatosActividad);
        jPanelDatosActividad.setLayout(jPanelDatosActividadLayout);
        jPanelDatosActividadLayout.setHorizontalGroup(
            jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosActividadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDatosActividadLayout.createSequentialGroup()
                        .addComponent(jLabelLocalizacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldInputLocalizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDatosActividadLayout.createSequentialGroup()
                        .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDatosActividadLayout.createSequentialGroup()
                                    .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelFechaAc)
                                        .addComponent(jLabelIDActiv))
                                    .addGap(37, 37, 37))
                                .addGroup(jPanelDatosActividadLayout.createSequentialGroup()
                                    .addComponent(jLabelHoraIni)
                                    .addGap(12, 12, 12)))
                            .addGroup(jPanelDatosActividadLayout.createSequentialGroup()
                                .addComponent(jLabelDificultad)
                                .addGap(20, 20, 20)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDatosActividadLayout.createSequentialGroup()
                                .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldInputIDActividad, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInputFechaActiv, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInputHoraInicioAc))
                                .addGap(27, 27, 27))
                            .addGroup(jPanelDatosActividadLayout.createSequentialGroup()
                                .addComponent(jSpinnerDificultad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCapacidad)
                            .addComponent(jLabelDiaSemana)
                            .addComponent(jLabelNombreAc, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelHoraFin))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldInputDiaSemana, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInputHoraFinActiv, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerCapacidadActiv, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInputNombreActiv, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanelDatosActividadLayout.createSequentialGroup()
                        .addComponent(jLabelDescripAc)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelDatosActividadLayout.createSequentialGroup()
                        .addComponent(jLabelNombreGuia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldInputNombreGuia, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelDatosActividadLayout.setVerticalGroup(
            jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosActividadLayout.createSequentialGroup()
                .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelIDActiv)
                    .addComponent(jTextFieldInputIDActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNombreAc)
                    .addComponent(jTextFieldInputNombreActiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputDiaSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDiaSemana)
                    .addComponent(jTextFieldInputFechaActiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFechaAc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputHoraInicioAc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHoraIni)
                    .addComponent(jTextFieldInputHoraFinActiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHoraFin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerCapacidadActiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCapacidad)
                    .addComponent(jSpinnerDificultad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDificultad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputLocalizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLocalizacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelDatosActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputNombreGuia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNombreGuia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelDescripAc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonIntroducir.setText("Introducir");
        jButtonIntroducir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIntroducirActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jLabelMainTitulo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabelMainTitulo.setText("Alta Juego de Datos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanelDatosActividad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(123, 123, 123)
                            .addComponent(jButtonIntroducir)
                            .addGap(51, 51, 51)
                            .addComponent(jButtonCancelar))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanelDatosAlojamiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabelMainTitulo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelMainTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelDatosAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDatosActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonIntroducir)
                    .addComponent(jButtonCancelar))
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        this.dispose();
        VP.setVisible(true);
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonIntroducirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIntroducirActionPerformed
        if(checkCamposLlenos()) {
            VistaActividadesAlojamientoId vaaID = recogidaDatosInstanciaVistaID();
            if(vaaID != null) {  //Mensaje de Output ya colocado, aqui simplemente tengo que tener cuidado de no  comerme el null.
                if(this.vista == null) altaVista(vaaID);
                else updateDatosVista();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Revisar los campos. Es necesario que TODOS esten rellenos.");
        }
    }//GEN-LAST:event_jButtonIntroducirActionPerformed

    /**
     * Comprobacion de que absolutamente todos los campos rellenables estan rellenos.
     * @return True si no hay ninguno vacio.
     */
    private boolean checkCamposLlenos() {
        return !(jTextFieldInputNombreAloj.getText().isEmpty() || jTextFieldInputTelefono.getText().isEmpty() || jTextFieldInputDirSocial.getText().isEmpty() || jTextFieldInputRazonSocial.getText().isEmpty() || jTextFieldInputFechaApertura.getText().isEmpty() || jTextPaneInputDescripcionAloj.getText().isEmpty() || jTextFieldInputIDActividad.getText().isEmpty() || jTextFieldInputNombreActiv.getText().isEmpty() || jTextFieldInputFechaActiv.getText().isEmpty() || jTextFieldInputDiaSemana.getText().isEmpty() || jTextFieldInputHoraInicioAc.getText().isEmpty() || jTextFieldInputHoraFinActiv.getText().isEmpty() || jTextFieldInputLocalizacion.getText().isEmpty() || jTextFieldInputNombreGuia.getText().isEmpty() || jTextPaneInputDescrip.getText().isEmpty());
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonIntroducir;
    private javax.swing.JComboBox jComboBoxProvincia;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabelCapacidad;
    private javax.swing.JLabel jLabelDescripAc;
    private javax.swing.JLabel jLabelDescripcion;
    private javax.swing.JLabel jLabelDiaSemana;
    private javax.swing.JLabel jLabelDificultad;
    private javax.swing.JLabel jLabelDirSocial;
    private javax.swing.JLabel jLabelFechaAc;
    private javax.swing.JLabel jLabelFechaApertura;
    private javax.swing.JLabel jLabelHabitaciones;
    private javax.swing.JLabel jLabelHoraFin;
    private javax.swing.JLabel jLabelHoraIni;
    private javax.swing.JLabel jLabelIDActiv;
    private javax.swing.JLabel jLabelLocalizacion;
    private javax.swing.JLabel jLabelMainTitulo;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelNombreAc;
    private javax.swing.JLabel jLabelNombreGuia;
    private javax.swing.JLabel jLabelProvincia;
    private javax.swing.JLabel jLabelRazonSocial;
    private javax.swing.JLabel jLabelTelefono;
    private javax.swing.JLabel jLabelValoracion;
    private javax.swing.JPanel jPanelDatosActividad;
    private javax.swing.JPanel jPanelDatosAlojamiento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerCapacidadActiv;
    private javax.swing.JSpinner jSpinnerDificultad;
    private javax.swing.JSpinner jSpinnerHabitaciones;
    private javax.swing.JSpinner jSpinnerValoracion;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTextFieldInputDiaSemana;
    private javax.swing.JTextField jTextFieldInputDirSocial;
    private javax.swing.JTextField jTextFieldInputFechaActiv;
    private javax.swing.JTextField jTextFieldInputFechaApertura;
    private javax.swing.JTextField jTextFieldInputHoraFinActiv;
    private javax.swing.JTextField jTextFieldInputHoraInicioAc;
    private javax.swing.JTextField jTextFieldInputIDActividad;
    private javax.swing.JTextField jTextFieldInputLocalizacion;
    private javax.swing.JTextField jTextFieldInputNombreActiv;
    private javax.swing.JTextField jTextFieldInputNombreAloj;
    private javax.swing.JTextField jTextFieldInputNombreGuia;
    private javax.swing.JTextField jTextFieldInputRazonSocial;
    private javax.swing.JTextField jTextFieldInputTelefono;
    private javax.swing.JTextPane jTextPaneInputDescrip;
    private javax.swing.JTextPane jTextPaneInputDescripcionAloj;
    // End of variables declaration//GEN-END:variables
}
