/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.swing.vista;

import controlador.datos.Facade;
import hibernate.dto.VistaActividadesAlojamiento;
import hibernate.dto.VistaActividadesAlojamientoId;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;

/**
 *
 * @author Mario Codes Sánchez
 */
public class ListadoVista extends javax.swing.JFrame {
    private DefaultTableModel model;
    private int valoracion = 0;
    
    /**
     * Creates new form VentanaListadoVista
     */
    public ListadoVista() {
        initComponents();
        
        this.setVisible(true);
        
        this.jTabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        rellenoTablaDatos();
    }
    
    /**
     * Constructor para la version filtrada por valoracion media.
     * @param valoracion Valoracion media que queremos que tenga el alojamiento / actividad donde vamos.
     */
    public ListadoVista(int valoracion) {
        initComponents();
        
        this.setVisible(true);
        
        this.valoracion = valoracion;
        this.jTabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        rellenoTablaDatos(valoracion);
    }
    
    /**
     * Operacion Base. Hace un get de los datos actuales y los muestra en una JTable.
     */
    private void rellenoTablaDatos() {
        if(model != null) model.setRowCount(0);
        
        model = (DefaultTableModel) this.jTabla.getModel();
        
        Session s = Facade.abrirSessionHibernate();
        List lista = s.createCriteria(VistaActividadesAlojamiento.class).list();
        
        ListIterator li = lista.listIterator();
        
        while(li.hasNext()) {
            VistaActividadesAlojamiento vaa = (VistaActividadesAlojamiento) li.next();
            VistaActividadesAlojamientoId vaaID = vaa.getId();
            
            try {
                Object[] row = new Object[21];
                row[0] = vaaID.getIdAlojamiento();
                row[1] = vaaID.getIdActividad();
                row[2] = vaaID.getNombreAlojamiento();
                row[3] = vaaID.getDescripcionAlojamiento();
                row[4] = vaaID.getDireccionSocial();
                row[5] = vaaID.getRazonSocial();
                row[6] = vaaID.getTelefonoContacto();
                row[7] = vaaID.getValoracionAlojamiento();
                row[8] = vaaID.getFechaApertura();
                row[9] = vaaID.getNumeroHabitaciones();
                row[10] = vaaID.getProvincia();
                row[11] = vaaID.getNombreActividad();
                row[12] = vaaID.getDescripcionActividad();
                row[13] = vaaID.getDiaRealizacion();
                row[14] = vaaID.getDiaSemana();
                row[15] = vaaID.getHoraInicio();
                row[16] = vaaID.getHoraFin();
                row[17] = vaaID.getLocalizacion();
                row[18] = vaaID.getDificultad();
                row[19] = vaaID.getCapacidad();
                row[20] = vaaID.getNombreGuia();
                model.addRow(row);
            }catch(NullPointerException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Version para obtener filtrados los que tengan mas de 'x' valoracion.
     * @param valoracion Valoracion minima que queremos que tenga.
     */
    private void rellenoTablaDatos(int valoracion) {
        if(model != null) model.setRowCount(0);
        
        model = (DefaultTableModel) this.jTabla.getModel();
        
        Session s = Facade.abrirSessionHibernate();
        List lista = s.createCriteria(VistaActividadesAlojamiento.class).list();
        
        ListIterator li = lista.listIterator();
        
        while(li.hasNext()) {
            VistaActividadesAlojamiento vaa = (VistaActividadesAlojamiento) li.next();
            VistaActividadesAlojamientoId vaaID = vaa.getId();
            
            if(vaaID.getValoracionAlojamiento() >= valoracion) {
                try {
                    Object[] row = new Object[21];
                    row[0] = vaaID.getIdAlojamiento();
                    row[1] = vaaID.getIdActividad();
                    row[2] = vaaID.getNombreAlojamiento();
                    row[3] = vaaID.getDescripcionAlojamiento();
                    row[4] = vaaID.getDireccionSocial();
                    row[5] = vaaID.getRazonSocial();
                    row[6] = vaaID.getTelefonoContacto();
                    row[7] = vaaID.getValoracionAlojamiento();
                    row[8] = vaaID.getFechaApertura();
                    row[9] = vaaID.getNumeroHabitaciones();
                    row[10] = vaaID.getProvincia();
                    row[11] = vaaID.getNombreActividad();
                    row[12] = vaaID.getDescripcionActividad();
                    row[13] = vaaID.getDiaRealizacion();
                    row[14] = vaaID.getDiaSemana();
                    row[15] = vaaID.getHoraInicio();
                    row[16] = vaaID.getHoraFin();
                    row[17] = vaaID.getLocalizacion();
                    row[18] = vaaID.getDificultad();
                    row[19] = vaaID.getCapacidad();
                    row[20] = vaaID.getNombreGuia();
                    model.addRow(row);
                }catch(NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
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

        jLabelTitulo = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jButtonRefrescar = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabelTitulo.setText("Lista Completa");

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_ALOJAMIENTO", "ID_ACTIVIDAD", "NOMBRE", "DESCRIPCION", "DIRECCION", "RAZON_SOCIAL", "TELEFONO", "VALORACION", "FECHA_APERTURA", "HABITACIONES", "PROVINCIA", "ACTIVIDAD", "DESCRIPCION", "SIGUIENTE_DIA", "DIA_SEMANA", "HORA_INICIO", "HORA_FIN", "LOCALIZACION", "DIFICULTAD", "CAPACIDAD", "NOMBRE_GUIA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane.setViewportView(jTabla);

        jButtonRefrescar.setText("Refrescar");
        jButtonRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefrescarActionPerformed(evt);
            }
        });

        jButtonSalir.setText("Cerrar");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 409, Short.MAX_VALUE)
                        .addComponent(jButtonRefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jButtonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(474, 474, 474))))
            .addGroup(layout.createSequentialGroup()
                .addGap(523, 523, 523)
                .addComponent(jLabelTitulo)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRefrescar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jButtonRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefrescarActionPerformed
        if(valoracion == 0)rellenoTablaDatos();
        else rellenoTablaDatos(valoracion);
        JOptionPane.showMessageDialog(this, "Tabla Actualizada.");
    }//GEN-LAST:event_jButtonRefrescarActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRefrescar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable jTabla;
    // End of variables declaration//GEN-END:variables
}
