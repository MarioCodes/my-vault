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
 * todo: poner un spinner en la GUI para introducir el porcentaje a partir del cual se quiere dar el aviso.
 * Proyecto de proceso de una pagina web.
 * Clase Principal y unica.
 * @author Mario Codes Sánchez
 * @since 14/02/2017
 */
public class Window extends javax.swing.JFrame {
    private static boolean escanear = true;
    private static final float PORCENTAJE = 0.05f;
    private static final long ESPERA = 3000; //ms de esperas entre escaneos en el programa.
    private static DefaultTableModel model = new DefaultTableModel(); //Model donde cargare los datos e implementare.
    
    private static Document document;
    private static Elements tabla, headersWeb, datos;
    
    private static String[] modelHeaders;
    private static String[][] modelData, oldModelData;
    
    /**
     * Creates new form in
     */
    public Window() {
        initComponents();
        
        this.setTitle("Informacion Relativa al Mercado.");
        this.setResizable(false);
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
        
        return nodo;
    }
    
    /**
     * Quita una etiqueta HTML de la String que se le pasa.
     * @param string String a la cual se quiere quitar una etiqueta.
     * @return String sin esa etiqueta.
     */
    private static String quitarLayerNormal(String string) {
        string = string.substring(string.indexOf('>')+1, string.lastIndexOf('<'));
        return string;
    }
    
    /**
     * Quitado expreso de la etiqueta <img />. Hay algunos valores que no la contienen, por lo que es necesario buscarla expresamente.
     * @param string String a chequear.
     * @return String sin la etiqueta imagen si la contenia.
     */
    private static String quitarLayerImg(String string) {
        if(string.contains("<img")) {
            string = string.substring(string.indexOf('>')+1, string.lastIndexOf('<'));
        }
        return string;
    }
    
    /**
     * Chequea la String para comprobar si es un valor que sube o baja. Si se mantiene se marca como baja.
     * @param string String a chequear.
     * @return True si sube, false si no.
     */
    private static boolean getTipo(String string) {
        return string.contains("\"Sube\"");
    }
    
    /**
     * Conversion de un element suelto a String[] para aniadirlo a la Tabla.
     * Hago la conversion a mano. Son valores tan irregulares que no puedo automatizarlos.
     * @param element Element a convertir.
     * @return String[] convertida.
     */
    private static String[] convertElement(Element element) {
        String[] datos = new String[5];
        
        try {
            for(int i = 0; i < 2; i++){
                datos[i] = convertNode(element.childNode(i));
            }

            datos[2] = convertNode(element.childNode(2).unwrap()) +"; " +convertNode(element.childNode(4)) +"h";
            
            //No me gusta dejar esto asi pero hay demasiados cambios como para meterlos en metodos independientes, se deberia poder automatizar los recortes pero me da demasiados probremas. Ahora mismo esta manual para cada caso en concreto.
            String s = quitarLayerNormal(element.childNode(5).toString());
            s = quitarLayerNormal(s);
            boolean sube = getTipo(s);
            s = quitarLayerImg(s);
            
            String cambioValor = s.substring(s.indexOf('>')+1, s.indexOf('>')+5);
            String porcentaje = s.substring(s.lastIndexOf('>')+1).trim();
            if(porcentaje.isEmpty()) porcentaje = "(0,00%)"; //Tengo problemas para capturar los 0% porque la pagina lo estructura de otra manera. Solo esta vacio cuando es 0%.

            if(sube) datos[3] = "+" +cambioValor +"; " +porcentaje;
            else datos[3] = "-" +cambioValor +"; " +porcentaje;

            datos[4] = convertNode(element.childNode(6));
        }catch(IndexOutOfBoundsException ex) {
            System.out.println("ArrayIndex en convertElement(Element element) capturado: " +ex.getLocalizedMessage());
        }
        
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
        
        if(oldModelData == null) { //Para la primera iteracion del programa.
            modelData = getData(tabla);
            oldModelData = modelData;
        }
        else {
            oldModelData = modelData;
            modelData = getData(tabla);
        }
        
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
     */
    private static void tratamiento(String url) {
        iniElementos(url);
        adecuacionDatos();
    }
    
    /**
     * Obtencion del numero limite mediante el cual si se sobrepasa habra que dar aviso al usuario.
     * @param porcentaje Porcentaje establecido por el usuario para dar aviso.
     * @param valor Valor el cual chequeamos.
     * @return Valor limite para avisar.
     */
    private static long getLimite(float porcentaje, long valor) {
        return (long) (valor*porcentaje/100);
    }
    
    /**
     * Parse de numero estilo "###.###.###"
     * Java no entiende que los .'s son para separar grupos de numero y se cree que son decimales. No encuentro nada que me solucione la vida, asi que lo creo yo.
     * @param string String a parsear.
     * @return Long correctamente formado.
     */
    private static long parseLong(String string) {
        String parse = "";
        
        try {
            while(string.contains(".")) {
                parse += string.substring(0, string.indexOf('.'));
                string = string.substring(string.indexOf('.')+1);
            }

            parse += string; //Para el ultimo resto y numeros sin punto.
        }catch(NullPointerException ex) {
            System.out.println("Problema con el parseo custom del Long. " +ex.getLocalizedMessage());
        }
        
        return Long.parseLong(parse);
    }
    
    /**
     * Comprobacion de si un valor se pasa del limite establecido por el porcentaje indicado.
     * @param cambio Cambio realizado entre el escaneo viejo y el actual.
     * @param limite Limite del cual si se pasa hay que dar aviso.
     * @return True si el  cambio se pasa de, o iguala el limite.
     */
    private static boolean checkLimite(long cambio, long limite) {
        boolean isOver;
        
        if(cambio >= 0) isOver = cambio >= limite;
        else {
            limite -= limite*2;
            isOver = cambio <= limite;
        }
        
        return isOver;
    }
    
    /**
     * Comparacion de los valores anteriores con los nuevos escaneados. Si hay un cambio por arriba o abajo superior al porcentaje establecido, da aviso.
     * El porcentaje lo tengo en cuenta a traves del valor Viejo.
     * @param datosViejos Datos del escaneo anterior para tener una base sobre la cual comparar.
     * @param datosNuevos Datos del escaneo nuevo.
     */
    private static void comparacion(String[][] datosViejos, String[][] datosNuevos, int comienzoHilo, int limiteHilo) {
        long valorNuevo, valorViejo, cambio, cambioLimite;
        
        for (int indiceEmpresa = comienzoHilo; indiceEmpresa < limiteHilo; indiceEmpresa++) {
            valorNuevo = parseLong(datosNuevos[indiceEmpresa][4]);
            valorViejo = parseLong(datosViejos[indiceEmpresa][4]);
            cambioLimite = getLimite(PORCENTAJE, valorNuevo);
            cambio = valorNuevo-valorViejo;
            boolean over = checkLimite(cambio, cambioLimite);
            if(over) System.out.printf("¡ATENCION! ¡Cambio que ha sobrepasado el limite! Empresa %S. Valor anterior: %d. Cambio de: %d. Valor actual: %d", datosNuevos[indiceEmpresa][0], valorViejo, cambio, valorNuevo);
        }
//        System.out.printf("Valor viejo: %d, Valor Nuevo: %d, Cambio: %d, CambioLimite: %d, Limite superado: %s\n" ,valorViejo, valorNuevo, cambio, cambioLimite, res); //Para testeo.        
    }
    
    /**
     * Ejecucion del escaneo en si mismo. Vuelve a obtener los datos de la pagina y compara los valores totales nuevos contra los anteriores.
     * Divide la carga de trabajo en 4 hilos, aunque no hacia mucha falta ya que es poca cantidad de datos a tratar, algo de tiempo si que gana.
     * @param url url sobre la que esta operando el programa.
     */
    private synchronized static void escanear(String url) {
        escanear = true;
        while(escanear) {
            try {
                tratamiento(url);
                System.out.println("Escaneo realizado sobre: " +url);
                
                new Thread(() -> comparacion(oldModelData, modelData, 0, 25)).start();
                new Thread(() -> comparacion(oldModelData, modelData, 25, 50)).start();
                new Thread(() -> comparacion(oldModelData, modelData, 50, 75)).start();
                new Thread(() -> comparacion(oldModelData, modelData, 75, 100)).start();
                
                Thread.sleep(ESPERA);
            } catch (InterruptedException ex) {
                System.out.println("Problema con Thread.sleep en escanear(String url). " +ex.getLocalizedMessage());
            }
            if(!escanear) System.out.println("Escaneo sobre :" +url +" parado.");
        }
    }
    
    /**
     * Cambio de la fuente de datos junto a la Label que informa al usuario.
     * @param url URL de donde sacar los datos.
     */
    private static void cambioDatos(String url) {
        escanear = false;
        tratamiento(url);
        switch(url) {
            case "https://es.finance.yahoo.com/actives?e=mc":
                Window.jLabelTitulo.setText("Valores mas Activos");
                break;
            case "https://es.finance.yahoo.com/gainers?e=mc":
                Window.jLabelTitulo.setText("Mayores Subidas de Precio");
                break;
            case "https://es.finance.yahoo.com/losers?e=mc":
                Window.jLabelTitulo.setText("Bajan de Precio");
                break;
            default:
                break;
        }
        
        Runnable r = () -> escanear(url);
        new Thread(r).start();
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
        jLabelTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuValores = new javax.swing.JMenu();
        jRadioButtonMenuItemActivos = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemSubidas = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemBajadas = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabelTitulo.setText("Valores mas Activos");

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(jTable);

        jMenuFile.setText("File");

        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItem1);

        jMenuBar1.add(jMenuFile);

        jMenuValores.setText("Valores");

        buttonGroupDatos.add(jRadioButtonMenuItemActivos);
        jRadioButtonMenuItemActivos.setSelected(true);
        jRadioButtonMenuItemActivos.setText("Valores mas Activos");
        jRadioButtonMenuItemActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemActivosActionPerformed(evt);
            }
        });
        jMenuValores.add(jRadioButtonMenuItemActivos);

        buttonGroupDatos.add(jRadioButtonMenuItemSubidas);
        jRadioButtonMenuItemSubidas.setText("Mayores Subidas de Precio");
        jRadioButtonMenuItemSubidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemSubidasActionPerformed(evt);
            }
        });
        jMenuValores.add(jRadioButtonMenuItemSubidas);

        buttonGroupDatos.add(jRadioButtonMenuItemBajadas);
        jRadioButtonMenuItemBajadas.setText("Bajan de Precio");
        jRadioButtonMenuItemBajadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemBajadasActionPerformed(evt);
            }
        });
        jMenuValores.add(jRadioButtonMenuItemBajadas);

        jMenuBar1.add(jMenuValores);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTitulo)
                .addGap(217, 217, 217))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jRadioButtonMenuItemActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemActivosActionPerformed
        cambioDatos("https://es.finance.yahoo.com/actives?e=mc");
    }//GEN-LAST:event_jRadioButtonMenuItemActivosActionPerformed

    private void jRadioButtonMenuItemSubidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemSubidasActionPerformed
        cambioDatos("https://es.finance.yahoo.com/gainers?e=mc");
    }//GEN-LAST:event_jRadioButtonMenuItemSubidasActionPerformed

    private void jRadioButtonMenuItemBajadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemBajadasActionPerformed
        cambioDatos("https://es.finance.yahoo.com/losers?e=mc");
    }//GEN-LAST:event_jRadioButtonMenuItemBajadasActionPerformed

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
    private static javax.swing.JLabel jLabelTitulo;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenu jMenuValores;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemActivos;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemBajadas;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemSubidas;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
}
