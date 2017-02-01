/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.swing.vista;

import aplicacion.facade.Facade;
import dto.Alojamiento;
import dto.VistaActividadesAlojamiento;
import dto.VistaActividadesAlojamientoId;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mario
 */
public class VentanaBuscarAlojID extends javax.swing.JFrame {

    /**
     * Creates new form VentanaBuscarAlojID
     */
    public VentanaBuscarAlojID() {
        initComponents();
        
        this.setTitle("Introduce un ID de Alojamiento");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    /**
     * Comprobacion de que el ID sea un numero valido.
     * @return True si el numero es valido.
     */
    private boolean checkInputIDNumericoExprRegular(String id) {
        try {
            Pattern pat = Pattern.compile("[0-9]+");
            Matcher mat = pat.matcher(id);
        
            return mat.matches();
        } catch(NumberFormatException ex) {
            return false;
        }
    }
    
    /**
     * Obtencion de una instancia de la Vista si existe con el ID introducido.
     * @param id_pk ID de la vista a obtener.
     * @return Instancia de la Vista si existe, null si no.
     */
    private VistaActividadesAlojamiento getVistaPorID(int id_pk) {
        Session s = Facade.abrirSessionHibernate();
        
        Query q = s.createQuery("from VistaActividadesAlojamiento "
                                    + "where ID_ALOJAMIENTO = :idAloj");
        q.setParameter("idAloj", id_pk);
        return (VistaActividadesAlojamiento) q.uniqueResult();
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
        jLabelTituloID = new javax.swing.JLabel();
        jTextFieldInputIDAloj = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabelTitulo.setText("Modificar Alojamiento");

        jLabelTituloID.setText("ID del Alojamiento");

        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabelTituloID))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButtonBuscar)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, Short.MAX_VALUE))
                            .addComponent(jTextFieldInputIDAloj)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelTitulo)))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInputIDAloj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTituloID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonBuscar))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        String id = this.jTextFieldInputIDAloj.getText();
        
        if(checkInputIDNumericoExprRegular(id)) {
            VistaActividadesAlojamiento vistaTmp = getVistaPorID(Integer.parseInt(id));
            
            if(vistaTmp != null) new VentanaAltaYModifVista(vistaTmp);
            else JOptionPane.showMessageDialog(this, "No existe ninguna entrada de Vista con ese ID.");
        }
        else JOptionPane.showMessageDialog(this, "Introduce un numero valido.");
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelTituloID;
    private javax.swing.JTextField jTextFieldInputIDAloj;
    // End of variables declaration//GEN-END:variables
}