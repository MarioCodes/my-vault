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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import vista.swing.habitacion.ListadoHabitaciones;

/**
 * Ventana con la estructura para mostrar resultados de Alojamientos. Segun el constructor ejecutara unos metodos u otros.
 * @author Mario Codes Sánchez
 * @since 01/03/2017
 */
public class VentanaListadoAlojamientos extends javax.swing.JFrame {    
    private DefaultTableModel model = null;
    private Objects<Alojamiento> alojamientos = null;
    private boolean filtro_todos = true, filtro_valoracion = false;
    
    {{ //Codigo que se ejecuta en todos los constructores.
        this.setVisible(true); //Mejor que la padre no se haga invisible, no me acordare de los ID de memoria. Si vuelvo a cambiar esto, cambiar tambien los vp.setvisible a true del windowListener on close.
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }}
    
    /**
     * Constructor de la version sin filtrar. Muestra todos los Alojamientos Existentes.
     */
    public VentanaListadoAlojamientos() {
        initComponents(); //Ini necesario de los componentes intrinsecos de la ventana.
        this.setTitle("Lista de Alojamientos existentes");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        alojamientos = NeoDatis.getAlojamientos();
        rellenoTabla(alojamientos);
    }
    
    /**
     * Una vez rellenada la Coleccion con los datos que queremos (filtrados o no), introducimos estos en la tabla.
     */
    private void rellenoTabla(Objects col) {
        model = (DefaultTableModel) tabla.getModel(); //Hacemos un get del DefaultModel con el que creamos la tabla desde Swing.

        try {
            col.reset();
            while(col.hasNext()) {
                Alojamiento alDTO = (Alojamiento) col.next();
                Object[] row = new Object[9]; //Creamos un objeto 'fila' con tantas columnas como tenga nuestra tabla (10 en este caso).
                row[0] = alDTO.getNombre();
                row[1] = alDTO.getDireccionSocial();
                row[2] = alDTO.getRazonSocial();
                row[3] = alDTO.getTelefonoContacto();
                row[4] = alDTO.getDescripcion();
                row[5] = alDTO.getValoracion();
                row[6] = alDTO.getFechaApertura();
                row[7] = alDTO.getNumHabitaciones();
                row[8] = alDTO.getProvincia();
                model.addRow(row); //Añade la fila ya rellena al modelo de la tabla.
            }
        }catch(NullPointerException ex) {
            System.out.println("Error especifico: " +ex.getLocalizedMessage());
            JOptionPane.showMessageDialog(this, "ERROR. NullPointerException.");
        }
    }
    
    /**
     * Actualiza la informacion fuente y los datos que muestra la tabla.
     */ 
    private void actualizar() {
        model.setRowCount(0); //Hace un vaciado de la tabla. Despúes de ejecutar esto, se debera volver a rellenar o quedara vacia.
        if(filtro_todos) {
            alojamientos = NeoDatis.getAlojamientos();
            rellenoTabla(alojamientos);
        }
        else {
            if(filtro_valoracion) {
                int valoracion = ((Alojamiento) alojamientos.next()).getValoracion();
                alojamientos = NeoDatis.getAlojamientos(valoracion);
                rellenoTabla(alojamientos);
            }
        }
    }
    
    private void delete() {
        int row = tabla.getSelectedRow();
        if(row != -1) {
            String nombre = (String) tabla.getModel().getValueAt(row, 0);
            String dirSoc = (String) tabla.getModel().getValueAt(row, 1);
            String razSoc = (String) tabla.getModel().getValueAt(row, 2);
            String descripcion = (String) tabla.getModel().getValueAt(row, 4);

            Alojamiento a = null;
            ODB odb = ODBFactory.open(NeoDatis.getDATABASE());
            IQuery query = new CriteriaQuery(Alojamiento.class, Where.and().add(Where.equal("nombre", nombre)).add(Where.equal("direccionSocial", dirSoc)).add(Where.equal("razonSocial", razSoc)).add(Where.equal("descripcion", descripcion)));
            try {
                a = (Alojamiento) odb.getObjects(query).getFirst();
                
                Set<Habitacion> habitaciones = a.getSetHabitaciones(); //Delete on cascade. Si borramos un Alojamiento debemos borrar todas sus Habitaciones.
                for(Habitacion h: habitaciones) odb.delete(h);
                
                odb.delete(a);
                JOptionPane.showMessageDialog(this, "Alojamiento eliminado.");
            }catch(IndexOutOfBoundsException ex) {
                System.out.println("No existe un Alojamiento con esos datos.");
            }finally {
                odb.close();
                actualizar();
            }
        }
    }
    
    private void update() {
        int row = tabla.getSelectedRow();
        if(row != -1) {
            String nombre = (String) tabla.getModel().getValueAt(row, 0);
            String razSoc = (String) tabla.getModel().getValueAt(row, 2);

            Alojamiento a = null;
            ODB odb = ODBFactory.open(NeoDatis.getDATABASE());
            IQuery query = new CriteriaQuery(Alojamiento.class, Where.and().add(Where.equal("nombre", nombre)).add(Where.equal("razonSocial", razSoc)));
            try {
                a = (Alojamiento) odb.getObjects(query).getFirst();
                if(a != null) {
                    odb.close();
                    new VentanaAltaYModifAlojamiento(a);
                }
            }catch(IndexOutOfBoundsException ex) {
                System.out.println("No existe una Habitacion con id y alojamiento parecidos.");
            }finally {
                if(!odb.isClosed()) odb.close();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemActualizar = new javax.swing.JMenuItem();
        jMenuItemCerrar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemInsertar = new javax.swing.JMenuItem();
        jMenuItemUpdate = new javax.swing.JMenuItem();
        jMenuItemHabitacionesAloja = new javax.swing.JMenuItem();
        jMenuItemBorrar = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItemElimiarFiltros = new javax.swing.JMenuItem();
        jMenuItemFiltroValoracion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 0));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Dir_Social", "Razon_Social", "Telefono", "Descripcion", "Valoracion", "Fecha_Apertura", "Num_Habitaciones", "Provincia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setRowHeight(18);
        tabla.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(tabla);

        jMenu1.setText("Ventana");

        jMenuItemActualizar.setText("Actualizar");
        jMenuItemActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActualizarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemActualizar);

        jMenuItemCerrar.setText("Cerrar");
        jMenuItemCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCerrarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemCerrar);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Alojamiento");

        jMenuItemInsertar.setText("Insertar Nuevo");
        jMenuItemInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemInsertarActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemInsertar);

        jMenuItemUpdate.setText("Modificar Seleccionado");
        jMenuItemUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemUpdateActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemUpdate);

        jMenuItemHabitacionesAloja.setText("Ver Habitaciones Propias");
        jMenuItemHabitacionesAloja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHabitacionesAlojaActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemHabitacionesAloja);

        jMenuItemBorrar.setText("Borrar Seleccionado");
        jMenuItemBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBorrarActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemBorrar);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Filtros");

        jMenuItemElimiarFiltros.setText("Eliminar Filtros");
        jMenuItemElimiarFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemElimiarFiltrosActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemElimiarFiltros);

        jMenuItemFiltroValoracion.setText("Por Valoracion Minima");
        jMenuItemFiltroValoracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFiltroValoracionActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemFiltroValoracion);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jMenuItemCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItemCerrarActionPerformed

    private void jMenuItemActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActualizarActionPerformed
        actualizar();
        JOptionPane.showMessageDialog(this, "Tabla actualizada.");
    }//GEN-LAST:event_jMenuItemActualizarActionPerformed

    private void jMenuItemInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemInsertarActionPerformed
        new VentanaAltaYModifAlojamiento();
    }//GEN-LAST:event_jMenuItemInsertarActionPerformed

    private void jMenuItemBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBorrarActionPerformed
        delete();
    }//GEN-LAST:event_jMenuItemBorrarActionPerformed

    private void jMenuItemUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemUpdateActionPerformed
        update();
    }//GEN-LAST:event_jMenuItemUpdateActionPerformed

    private void jMenuItemFiltroValoracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFiltroValoracionActionPerformed
        String input = JOptionPane.showInputDialog("Introduce la valoracion minima del Alojamiento:");
        if(input != null) {
            model.setRowCount(0); //Hace un vaciado de la tabla. Despúes de ejecutar esto, se debera volver a rellenar o quedara vacia.
            int valoracion = Integer.parseInt(input);
            Objects<Alojamiento> alojamientos = NeoDatis.getAlojamientos(valoracion);
            this.alojamientos = alojamientos;
            rellenoTabla(alojamientos);
//            actualizar();
        }
    }//GEN-LAST:event_jMenuItemFiltroValoracionActionPerformed

    private void jMenuItemElimiarFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemElimiarFiltrosActionPerformed
        actualizar();
    }//GEN-LAST:event_jMenuItemElimiarFiltrosActionPerformed

    private void jMenuItemHabitacionesAlojaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHabitacionesAlojaActionPerformed
        int row = tabla.getSelectedRow();
        if(row != -1) {
            String nombre = (String) tabla.getValueAt(row, 0);
            String razSoc = (String) tabla.getValueAt(row, 2);
            Objects<Habitacion> objects = NeoDatis.getHabitacionesAlojamiento(nombre, razSoc);
            ListadoHabitaciones l = new ListadoHabitaciones(objects);
            l.setModo_alojamiento(true);
        }
    }//GEN-LAST:event_jMenuItemHabitacionesAlojaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemActualizar;
    private javax.swing.JMenuItem jMenuItemBorrar;
    private javax.swing.JMenuItem jMenuItemCerrar;
    private javax.swing.JMenuItem jMenuItemElimiarFiltros;
    private javax.swing.JMenuItem jMenuItemFiltroValoracion;
    private javax.swing.JMenuItem jMenuItemHabitacionesAloja;
    private javax.swing.JMenuItem jMenuItemInsertar;
    private javax.swing.JMenuItem jMenuItemUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
