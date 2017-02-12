/*
 * To change this license header, choose License Headers Window Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template Window the editor.
 */
package web;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 * Proyecto de proceso de una pagina web.
 * Clase Principal y unica.
 * @author Mario Codes Sánchez
 * @since 11/02/2017
 */
public class Window extends javax.swing.JFrame {
    private static DefaultTableModel model = new DefaultTableModel(); //Model donde cargare los datos e implementare.
    
    private static Document document;
    private static Elements tabla, headersWeb, datos;
    
    private static String[] modelHeaders;
    private static String[][] modelData;
    
    /**
     * Creates new form in
     */
    public Window() {
        initComponents();
        
        this.setLocationRelativeTo(null);
    }
    
    /**
     * Limpieza de los headers para obtener solo texto y no codigo.
     * @param element Element actual a tratar.
     * @return String con el nombre de la cabecera limpio.
     */
    private static String limpiezaHeaders(Element element) {
        String header = element.toString();
        header = header.substring(header.indexOf('>')+1);
        header = header.substring(0, header.indexOf('<'));
        
        return header;
    }
    
    /**
     * Conversion de Elements a String[] que se puede usar directamente como headers en el JTable.mode() al instanciarlo.
     * @param elements Elementos a tratar.
     * @return String[] a utilizar directamente como header para table.model().
     */
    private static String[] getHeaders(Elements elements) {
        ArrayList<String> headersTmp = new ArrayList<>();
        String[] headers;
        
        for(Element element: elements) {
            headersTmp.add(limpiezaHeaders(element));
        }
        
        headers = new String[headersTmp.size()];
        for(int i = 0; i < headersTmp.size(); i++) {
            headers[i] = headersTmp.get(i);
        }
        
        return headers;
    }
    
    /**
     * Comprueba si queda alguna tag en al string que eliminar.
     * @param string String a chequear.
     * @return True si aun quedan tags por eliminar.
     */
    private static boolean tagsLeft(String string) {
        return string.contains("</") || string.contains(">");
    }
    
    /**
     * Le quitamos una tag a la String pasada.
     * @param string String sobre la que operar.
     * @return String con una tag menos.
     */
    private static String quitarTag(String string) {
        try {
            string = string.substring(string.indexOf('>')+1, string.lastIndexOf('<'));
        }catch(StringIndexOutOfBoundsException ex) {
            string = string.substring(string.indexOf('>')+1);
        }
        
            
        
        return string;
    }
    
    /**
     * Conversion de un Nodo para obtener la String que nos interesa.
     * @param node Nodo del cual extraemos la informacion.
     * @return String con la informacion limpia.
     */
    private static String convertNode(Node node) {
        String nodo = node.toString();
        
        while(tagsLeft(nodo)) {
            nodo = quitarTag(nodo);
        }
        
//        System.out.println(nodo);
        return nodo;
    }
    
    /**
     * Conversion de un element suelto a String[] para aniadirlo a la Tabla.
     * @param element Element a convertir.
     * @return String[] convertida.
     */
    private static String[] convertElement(Element element) {
        String[] datos = new String[5];
        Node node = null;
        
//        for(int i = 0; i < datos.length; i++){ //Hasta 4, el resto de elementos me sobra.
            node = element.childNode(5);
            datos[0] = convertNode(node.unwrap());
            System.out.println(datos[0]);
//        }
        
//          System.out.println(datos[4]);
//        for(String s: datos) System.out.println(s);
        
        return null;
    }
    
    /**
     * Conversion de Elements a String[][] que usare directamente como Datos de JTable.mode().
     * @param elements Elements con los datos raw.
     * @return String[][] con los datos convertidos.
     */
    private static String[][] getData(Elements elements) {
        String[][] datos;
        
        convertElement(elements.first().child(0));
//        System.out.println(elements);
        
        return null;
    }
    
    /**
     * Proceso de adecuacion y obtencion de datos de la web.
     */
    private static void adecuacionDatos() {
        modelHeaders = getHeaders(headersWeb);
        modelData = getData(tabla);
    }
    
    /**
     * Get de los elementos necesarios para el parse.
     */
    private static void iniElementos(String url) {
        try {
            document = Jsoup.connect(url).get(); //Selecciona el documento entero.
            tabla = document.select("table tbody"); //De ese documento, pilla la tabla. Contiene los datos que nos interesan.
            for(int i = 0; i < 4; i++) { //Eliminacion de paja que hay por enmedio, con esto lo dejo listo para tratar.
                tabla.remove(tabla.get(0));
            }
            headersWeb = document.select("thead tr th");
            datos = document.select("tr");
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Proceso entero de recoleccion y filtrado de los datos.
     * @param url Url a la que nos conectamos.
     * todo: que al final devuelva 
     */
    private static void tratamiento(String url) {
        iniElementos(url);
        adecuacionDatos();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("jLabel1");

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable);

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 167, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(164, 164, 164))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(173, 173, 173))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced Window Java SE 6) is not available, stay with the default look and feel.
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
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Window().setVisible(true);
                tratamiento("https://es.finance.yahoo.com/actives?e=mc");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
}
