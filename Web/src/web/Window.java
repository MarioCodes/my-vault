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
 * fixme: hay algunos casos en los que dentro de 'volumen' se cuela grafico. mirar porque es y apañarlo.
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
        
        for (int i = 0; i < elements.size()-1; i++) { //-1 para quitar lo de 'informacion relacionada'.
            headersTmp.add(limpiezaHeaders(elements.get(i)));
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
     * Hago la conversion a mano. Son valores tan irregulares que no puedo automatizarlos.
     * @param element Element a convertir.
     * @return String[] convertida.
     */
    private static String[] convertElement(Element element) {
        String[] datos = new String[5];
        
        for(int i = 0; i < 2; i++){
            datos[i] = convertNode(element.childNode(i));
        }
        
        datos[2] = convertNode(element.childNode(2).unwrap()) +"; " +convertNode(element.childNode(4).unwrap());
        datos[3] = convertNode(element.childNode(5).unwrap()) +"; " +convertNode(element.childNode(7).unwrap()).trim();
        datos[4] = convertNode(element.childNode(8).unwrap());
        
        
        return datos;
    }
    
    /**
     * Conversion de Elements a String[][] que usare directamente como Datos de JTable.mode().
     * @param elements Elements con los datos raw.
     * @return String[][] con los datos convertidos.
     */
    private static String[][] getData(Elements elements) {
        int size = elements.get(0).childNodeSize();
        
        String[][] datos = new String[size][];
        for(int i = 0; i < size; i++) {
            datos[i] = convertElement(elements.get(0).child(i));
        }
            
        return datos;
    }
    
    /**
     * Proceso de adecuacion y obtencion de datos de la web.
     */
    private static void adecuacionDatos() {
        modelHeaders = getHeaders(headersWeb);
        modelData = getData(tabla);
        
        model = new DefaultTableModel(modelData, modelHeaders);
        Window.jTable.setModel(model);
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

        buttonGroupDatos = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jRadioButtonMenuItemActivos = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemSubidas = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemBajadas = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setText("Valores mas Activos");

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(jTable);

        jMenu1.setText("File");

        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Datos");

        buttonGroupDatos.add(jRadioButtonMenuItemActivos);
        jRadioButtonMenuItemActivos.setSelected(true);
        jRadioButtonMenuItemActivos.setText("Valores mas Activos");
        jMenu2.add(jRadioButtonMenuItemActivos);

        buttonGroupDatos.add(jRadioButtonMenuItemSubidas);
        jRadioButtonMenuItemSubidas.setText("Mayores Subidas de Precio");
        jMenu2.add(jRadioButtonMenuItemSubidas);

        buttonGroupDatos.add(jRadioButtonMenuItemBajadas);
        jRadioButtonMenuItemBajadas.setText("Bajan de Precio");
        jMenu2.add(jRadioButtonMenuItemBajadas);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
                new Window().setVisible(true);
                tratamiento("https://es.finance.yahoo.com/actives?e=mc");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupDatos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemActivos;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemBajadas;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemSubidas;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
}
