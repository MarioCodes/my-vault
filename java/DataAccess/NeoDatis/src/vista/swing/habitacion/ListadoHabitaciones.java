/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.swing.habitacion;

import controlador.datos.NeoDatis;
import controlador.dto.Habitacion;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 * @author Mario Codes Sánchez
 * @since 10/11/2016
 */
public class ListadoHabitaciones extends javax.swing.JFrame {
    private DefaultTableModel model;
    private String tipo_habitacion = null;
    private Objects<Habitacion> habitaciones = null; //Usado para ver las habitaciones dentro de la modif. de un Alojamiento.
    private boolean modo_alojamiento = false; //Modo para ver las habitaciones de cada alojamiento.
    
    {{
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }}
    
    public ListadoHabitaciones() {
        initComponents();
        
        this.setTitle("Lista de Habitaciones existentes.");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        Objects<Habitacion> habitaciones = NeoDatis.getHabitaciones();
        rellenoTablaDatos(habitaciones);
    }
    
    public ListadoHabitaciones(Set<Habitacion> habitaciones) {
        initComponents();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Habitaciones propias del Alojamiento.");
        rellenoTablaDatos(habitaciones);
    }
    
    public ListadoHabitaciones(Objects<Habitacion> habitaciones) {
        initComponents();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        this.habitaciones = habitaciones;
        rellenoTablaDatos(habitaciones);
    }
    
    /**
     * Metodo que se encarga de actualizar la tabla con el RestultSet que paso como parametro por el constructor.
     * Montado por mi cuenta googleando, explicado dentro.
     */
    private void rellenoTablaDatos() {
//        if(model != null) model.setRowCount(0);
//        
//        model = (DefaultTableModel) tabla.getModel(); //Hacemos un get del DefaultModel con el que creamos la tabla desde Swing.
//
//        Session s = Facade.abrirSessionHibernate();
//        List lista = s.createCriteria(Habitacion.class).list();
//        
//        ListIterator li = lista.listIterator();
//        
//        while(li.hasNext()) {
//            Habitacion habitacion = (Habitacion) li.next();
//            
//            try {
//                Object[] row = new Object[7];
//                row[0] = habitacion.getIdHabitacion();
//                row[1] = habitacion.getAlojamientoIdAlojamiento();
//                row[2] = habitacion.getReservaIdReserva();
//                row[3] = habitacion.getExtrasHabitacion();
//                row[4] = habitacion.getPrecio();
//                row[5] = habitacion.getTipoHabitacion();
//                row[6] = habitacion.getResenias();
//                model.addRow(row);
//            }catch(NullPointerException ex) {
//                System.out.println("Error especifico: " +ex.getLocalizedMessage());
//                JOptionPane.showMessageDialog(this, "ERROR. NullPointerException.");
//            }
//        }
    }
    
    private void rellenoTablaDatos(Set<Habitacion> objects) {
        if(model != null) model.setRowCount(0);
        model = (DefaultTableModel) tabla.getModel();

        Iterator it = objects.iterator();
        if(objects != null) {
            while(it.hasNext()) {
                Habitacion habitacion = (Habitacion) it.next();

                try {
                    Object[] row = new Object[6];
                    row[0] = habitacion.getNumero();
                    row[1] = habitacion.getAlojamiento();
                    row[2] = habitacion.getExtrasHabitacion();
                    row[3] = habitacion.getPrecio();
                    row[4] = habitacion.getTipoHabitacion();
                    row[5] = habitacion.getResenias();
                    model.addRow(row);
                }catch(NullPointerException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }
    
    private void rellenoTablaDatos(Objects<Habitacion> objects) {
        if(model != null) model.setRowCount(0);
        model = (DefaultTableModel) tabla.getModel();

        if(objects != null) {
            while(objects.hasNext()) {
                Habitacion habitacion = (Habitacion) objects.next();

                try {
                    Object[] row = new Object[6];
                    row[0] = habitacion.getNumero();
                    row[1] = habitacion.getAlojamiento();
                    row[2] = habitacion.getExtrasHabitacion();
                    row[3] = habitacion.getPrecio();
                    row[4] = habitacion.getTipoHabitacion();
                    row[5] = habitacion.getResenias();
                    model.addRow(row);
                }catch(NullPointerException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }
    
    private void rellenoTablaDatos(String tipo_habitacion) {
//        if(model != null) model.setRowCount(0);
//        
//        model = (DefaultTableModel) tabla.getModel(); //Hacemos un get del DefaultModel con el que creamos la tabla desde Swing.
//
//        Session s = Facade.abrirSessionHibernate();
//        List lista = s.createCriteria(Habitacion.class).list();
//        
//        ListIterator li = lista.listIterator();
//        
//        while(li.hasNext()) {
//            Habitacion habitacion = (Habitacion) li.next();
//            
//            if(habitacion.getTipoHabitacion().matches(tipo_habitacion)) {
//                try {
//                    Object[] row = new Object[7];
//                    row[3] = habitacion.getExtrasHabitacion();
//                    row[4] = habitacion.getPrecio();
//                    row[5] = habitacion.getTipoHabitacion();
//                    row[6] = habitacion.getResenias();
//                    model.addRow(row);
//                }catch(NullPointerException ex) {
//                    System.out.println("Error especifico: " +ex.getLocalizedMessage());
//                    JOptionPane.showMessageDialog(this, "ERROR. NullPointerException.");
//                }
//            }
//        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupTipo = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemActualizar = new javax.swing.JMenuItem();
        jMenuItemCerrar = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemInsertar = new javax.swing.JMenuItem();
        jMenuItemModificar = new javax.swing.JMenuItem();
        jMenuItemBorrar = new javax.swing.JMenuItem();
        jMenuFiltros = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem4 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem5 = new javax.swing.JRadioButtonMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 0));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Alojamiento", "Extras", "Precio", "Tipo Habitacion", "Reseñas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setRowHeight(18);
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(tabla);

        jMenu2.setText("Ventana");

        jMenuItemActualizar.setText("Actualizar Tabla");
        jMenuItemActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActualizarActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemActualizar);

        jMenuItemCerrar.setText("Cerrar");
        jMenuItemCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCerrarActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemCerrar);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Habitación");

        jMenuItemInsertar.setText("Insertar Nueva");
        jMenuItemInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemInsertarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemInsertar);

        jMenuItemModificar.setText("Modificar Seleccionada");
        jMenuItemModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemModificarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemModificar);

        jMenuItemBorrar.setText("Borrar Seleccionada");
        jMenuItemBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBorrarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemBorrar);

        jMenuBar1.add(jMenu1);

        jMenuFiltros.setText("Filtros");

        jMenuItem7.setText("Eliminar Filtros");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenuFiltros.add(jMenuItem7);

        jMenu4.setText("Por Tipo");

        buttonGroupTipo.add(jRadioButtonMenuItem1);
        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("Ninguno");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jRadioButtonMenuItem1);

        buttonGroupTipo.add(jRadioButtonMenuItem2);
        jRadioButtonMenuItem2.setText("Simple");
        jRadioButtonMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jRadioButtonMenuItem2);

        buttonGroupTipo.add(jRadioButtonMenuItem3);
        jRadioButtonMenuItem3.setText("Doble");
        jRadioButtonMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jRadioButtonMenuItem3);

        buttonGroupTipo.add(jRadioButtonMenuItem4);
        jRadioButtonMenuItem4.setText("Triple");
        jRadioButtonMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jRadioButtonMenuItem4);

        buttonGroupTipo.add(jRadioButtonMenuItem5);
        jRadioButtonMenuItem5.setText("Cuadruple");
        jRadioButtonMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jRadioButtonMenuItem5);

        jMenuFiltros.add(jMenu4);

        jMenuItem6.setText("Por Numero");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenuFiltros.add(jMenuItem6);

        jMenuBar1.add(jMenuFiltros);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actualizar() {
        if(this.tipo_habitacion == null) rellenoTablaDatos(NeoDatis.getHabitaciones());
        else rellenoTablaDatos(tipo_habitacion);
    }
    
    private void jMenuItemCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItemCerrarActionPerformed

    private void jMenuItemActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActualizarActionPerformed
        actualizar();
    }//GEN-LAST:event_jMenuItemActualizarActionPerformed

    private void jMenuItemInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemInsertarActionPerformed
        new AltaModifHabitacion();
    }//GEN-LAST:event_jMenuItemInsertarActionPerformed

    private void jMenuItemModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemModificarActionPerformed
        int row = tabla.getSelectedRow();
        if(row != -1) {
            String id = (String) tabla.getModel().getValueAt(row, 0);
            String extras = (String) tabla.getModel().getValueAt(row, 2);
            String tipo = (String) tabla.getModel().getValueAt(row, 4);
            String resenia = (String) tabla.getModel().getValueAt(row, 5);

            Habitacion h = null;
            ODB odb = ODBFactory.open(NeoDatis.getDATABASE());
            IQuery query = new CriteriaQuery(Habitacion.class, Where.and().add(Where.equal("numero", id)).add(Where.equal("extrasHabitacion", extras)).add(Where.equal("tipoHabitacion", tipo)).add(Where.equal("resenias", resenia)));
            try {
                h = (Habitacion) odb.getObjects(query).getFirst();
                if(h != null) {
                    odb.close();
                    new AltaModifHabitacion(h);
                }
            }catch(IndexOutOfBoundsException ex) {
                System.out.println("No existe una Habitacion con id y alojamiento parecidos.");
            }finally {
                if(!odb.isClosed()) odb.close();
            }
        }
    }//GEN-LAST:event_jMenuItemModificarActionPerformed

    private void jMenuItemBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBorrarActionPerformed
                int row = tabla.getSelectedRow();
        if(row != -1) {
            String id = (String) tabla.getModel().getValueAt(row, 0);
            String extras = (String) tabla.getModel().getValueAt(row, 2);
            String tipo = (String) tabla.getModel().getValueAt(row, 4);
            String resenia = (String) tabla.getModel().getValueAt(row, 5);

            Habitacion h = null;
            ODB odb = ODBFactory.open(NeoDatis.getDATABASE());
            IQuery query = new CriteriaQuery(Habitacion.class, Where.and().add(Where.equal("numero", id)).add(Where.equal("extrasHabitacion", extras)).add(Where.equal("tipoHabitacion", tipo)).add(Where.equal("resenias", resenia)));
            try {
                h = (Habitacion) odb.getObjects(query).getFirst();
                odb.delete(h);
                JOptionPane.showMessageDialog(this, "Habitacion eliminada.");
            }catch(IndexOutOfBoundsException ex) {
                System.out.println("No existe una Habitacion con id y alojamiento parecidos.");
            }finally {
                odb.close();
                actualizar();
            }
        }
    }//GEN-LAST:event_jMenuItemBorrarActionPerformed

    private void jRadioButtonMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem2ActionPerformed
        Objects<Habitacion> habitaciones = NeoDatis.getHabitaciones("simple");
        this.habitaciones = habitaciones;
        rellenoTablaDatos(habitaciones);
    }//GEN-LAST:event_jRadioButtonMenuItem2ActionPerformed

    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem1ActionPerformed
        Objects<Habitacion> habitaciones = NeoDatis.getHabitaciones();
        this.habitaciones = habitaciones;
        rellenoTablaDatos(habitaciones);
    }//GEN-LAST:event_jRadioButtonMenuItem1ActionPerformed

    private void jRadioButtonMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem3ActionPerformed
        Objects<Habitacion> habitaciones = NeoDatis.getHabitaciones("doble");
        this.habitaciones = habitaciones;
        rellenoTablaDatos(habitaciones);
    }//GEN-LAST:event_jRadioButtonMenuItem3ActionPerformed

    private void jRadioButtonMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem4ActionPerformed
        Objects<Habitacion> habitaciones = NeoDatis.getHabitaciones("triple");
        this.habitaciones = habitaciones;
        rellenoTablaDatos(habitaciones);
    }//GEN-LAST:event_jRadioButtonMenuItem4ActionPerformed

    private void jRadioButtonMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem5ActionPerformed
        Objects<Habitacion> habitaciones = NeoDatis.getHabitaciones("cuadruple");
        this.habitaciones = habitaciones;
        rellenoTablaDatos(habitaciones);
    }//GEN-LAST:event_jRadioButtonMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        String numero = JOptionPane.showInputDialog("Introduce el numero del Alojamiento por el cual buscar:");
        if(numero != null) {
            Objects<Habitacion> habitaciones = NeoDatis.getHabitacionesNumero(numero);
            this.habitaciones = habitaciones;
            rellenoTablaDatos(habitaciones);
            jRadioButtonMenuItem1.setSelected(true);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        Objects<Habitacion> habitaciones = NeoDatis.getHabitaciones();
        this.habitaciones = habitaciones;
        rellenoTablaDatos(habitaciones);
        jRadioButtonMenuItem1.setSelected(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupTipo;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFiltros;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItemActualizar;
    private javax.swing.JMenuItem jMenuItemBorrar;
    private javax.swing.JMenuItem jMenuItemCerrar;
    private javax.swing.JMenuItem jMenuItemInsertar;
    private javax.swing.JMenuItem jMenuItemModificar;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem4;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the modo_alojamiento
     */
    public boolean isModo_alojamiento() {
        return modo_alojamiento;
    }

    /**
     * @param modo_alojamiento the modo_alojamiento to set
     */
    public void setModo_alojamiento(boolean modo_alojamiento) {
        this.jMenuItemActualizar.setEnabled(!modo_alojamiento);
        this.jMenuItemInsertar.setEnabled(!modo_alojamiento);
        this.jMenuFiltros.setEnabled(!modo_alojamiento);
        this.modo_alojamiento = modo_alojamiento;
    }

}
