/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoweb;

import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Mario
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    String[] columnNames;
    Object[][] rowData;
    double diferencia = 0.05;
    JTextArea textArea;
    JScrollPane scrollPane;
    boolean showChange;
    
    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
        
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        consultaPagina();
        setVisible(true);
    }

    public void consultaPagina() {

        Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        showChange = false;
                        textArea.setText("");
                        int rowCount = 0;
                        Document doc = Jsoup.connect("https://es.finance.yahoo.com/actives?e=mc").get();
                        Elements tableElements = doc.select("table");

                        Elements tableHeaderEles = tableElements.select("thead tr th");
                        Elements tableRowElements = tableElements.select("tr");
                        //CALCULAMOS EL NUMERO DE FILAS
                        for (int i = 0; i < tableRowElements.size(); i++) {
                            Element row = tableRowElements.get(i);
                            Elements rowItems = row.select("td");
                            if (rowItems.size() == 6) {
                                rowCount++;
                            }
                        }
                        columnNames = new String[tableHeaderEles.size() - 1];
                        rowData = new Object[(rowCount)][tableHeaderEles.size() - 1];
                        
                        // CARGAR EL NOMBRE DE LAS COLUMNAS
                        for (int i = 0; i < tableHeaderEles.size() - 1; i++) {
                            columnNames[i] = tableHeaderEles.get(i).text();
                        }
                        //CARGAMOS TODAS LAS FILAS
                        rowCount = 0;
                        for (int i = 0; i < tableRowElements.size(); i++) {
                            int colCount = 0;
                            Element row = tableRowElements.get(i);
                            Elements rowItems = row.select("td");
                            if (rowItems.size() == 6) {
                                for (int j = 0; j < rowItems.size() - 1; j++) {
                                    if (jTable1.getModel().getRowCount() > 0) {
                                        if (colCount == 3) {
                                            if (!jTable1.getValueAt(rowCount, j).equals(rowItems.get(j).text())) {
                                                double valorAntiguo = Double.parseDouble(jTable1.getValueAt(rowCount, j).toString().substring(0, 4).replace(",", "."));
                                                double valorActualizado = Double.parseDouble(rowItems.get(j).text().substring(0, 4).replace(",", "."));
                                                if ((valorAntiguo - diferencia) > valorActualizado) {
                                                    textArea.append("Simbolo: " + jTable1.getValueAt(rowCount, 0) + "\t\n");
                                                    textArea.append("Nombre: " + jTable1.getValueAt(rowCount, 1) + "\t\n");
                                                    textArea.append("Ha subido un: " + String.format("%.2f", (valorAntiguo - valorActualizado)) + "\n\n");
                                                    showChange = true;
                                                } else if (valorAntiguo < (valorActualizado - diferencia)) {
                                                    textArea.append("Simbolo: " + jTable1.getValueAt(rowCount, 0) + "\t\n");
                                                    textArea.append("Nombre: " + jTable1.getValueAt(rowCount, 1) + "\t\n");
                                                    textArea.append("Ha subido un: " + String.format("%.2f", (valorActualizado - valorAntiguo)) + "\n\n");
                                                    showChange = true;
                                                }
                                            }

                                        }
                                    }
                                    rowData[rowCount][j] = rowItems.get(j).text();
                                    colCount++;
                                }
                                rowCount++;
                            }
                        }
                        if (showChange) {
                            JOptionPane.showMessageDialog(null, scrollPane, "Notificaciones Importantes",
                                    JOptionPane.YES_NO_OPTION);
                        }
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                jTable1.setModel(new Tabla(columnNames, rowData));

                            }
                        });
                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException ex) {
                            System.out.println("Sleep interrumpido");
                        }
                    } catch (IOException e) {
                        e.getCause();
                    }
                }
            }
        });
        th.start();
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
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
