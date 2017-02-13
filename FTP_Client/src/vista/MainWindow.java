/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Facade;
import controlador.Mapeador;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.commons.net.ftp.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.util.Base64;

/**
 * Ventana principal del programa. Se encargara de la gestion grafica.
 * Implemento una libreria de apache commons que me soluciona bastante la vida.
 * @author Mario Codes Sánchez
 * @since 06/02/2017
 * @see http://www.codejava.net/java-se/networking/ftp
 */
public class MainWindow extends javax.swing.JFrame {
    private String url, user, pwd;
    private final FTPClient FTP = new FTPClient();
    private boolean conexion;
    private int puerto;
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        this.setTitle("Cliente FTP");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    /**
     * Mapeo del cliente para su arbol de directorios.
     *  Me interesa encapsulamiento 'package' para refrescar cuando cree carpetas / ficheros.
     */
    static void setArbolCliente() {
        JTree mapeoCliente = new Mapeador().mapear();
        jTreeCliente.setModel(mapeoCliente.getModel());
        jTreeCliente.setSelectionRow(0);
        jTreeCliente.setShowsRootHandles(false);
    }
    
    /**
     * Setteo del model del tree de la GUI por el obtenido mediante el mapeo del server.
     * @param treeServer JTree obtenido del server.
     */
    private void setArbolServer() { //todo: estoy arreglando este metodo para hacer el nuevo mapeo con el server FTP.
        try {
            URL url = new URL("ftp://mario: @127.0.0.1:6598");
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            
            JTree tree = new Mapeador().mapearServer(is);
            
            is.close();
            this.jTreeServer.setModel(tree.getModel());
            this.jTreeServer.setSelectionRow(0);
            this.jTreeServer.setShowsRootHandles(false);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
//    /**
//     * Borra el Fichero o Directorio (debe estar vacio) seleccionado en el JTree.
//     */
//    private void accionBorrar(boolean isServer, JTree tree) {
//        try {
//            String rutaSeleccionada = conversionJTreePath.conversion(isServer, tree.getSelectionPath().toString());
//            if(!new File(rutaSeleccionada).delete()) JOptionPane.showMessageDialog(this, "No se puede borrar el elemento. Comprueba que esta vacio.");
//            setArbolCliente(); //Refresco del contenido del JTree hacia el usuario.
//        }catch(NullPointerException ex) {
//            System.out.println("INFO: NullPointerException al borrar sin especificar capturado.");
//        }
//    }
    
//    /**
//     * Metodo principal para borrar.
//     *  Comprueba si el usuario quiere que se pida confirmacion al borrar, si es asi la pide antes de ejecutar la accion propiamente dicha.
//     */
//    private void borrarFile(boolean isServer, JTree tree) {
//        boolean pedirConfirmacion = this.jCheckBoxMenuItemConfBorrar.getState();
//        
//        if(pedirConfirmacion) {
//            if(JOptionPane.showConfirmDialog(this, "¿Seguro?") == 0) accionBorrar(isServer, tree);
//        } else accionBorrar(isServer, tree);
//    }
    
//    /**
//     * Crea un directorio dentro del item seleccionado en el JTree pasado como parametro.
//     */
//    private void crearDirectorio(boolean isServer, JTree jtree) {
//        try {
//            String rutaSeleccionada = conversionJTreePath.conversion(isServer, jtree.getSelectionPath().toString());
//            new NewFolder(rutaSeleccionada);
//        }catch(NullPointerException ex) {
//            System.out.println("INFO: NullPointerException al crear directorio sin ruta capturado.");
//        }
//    }
    
    /**
     * (Des)activacion de los botones segun el estado de la conexion, asi como cambio del icono de estado.
     * @param conexion Estado de la conexion previamente comprobado.
     */
    private void gestionControlesConexion(boolean conexion) {
        if(conexion) {
            this.jLabelEstadoConexion.setIcon(new ImageIcon(getClass().getResource("../imagenes/Tick.png")));
            setArbolServer();
        }
        
        else this.jLabelEstadoConexion.setIcon(new ImageIcon(getClass().getResource("../imagenes/Cross.png")));
        
        this.jButtonBorrarServer.setEnabled(conexion);
        this.jButtonCrearCarpetaServer.setEnabled(conexion);
        this.jButtonRefrescarServer.setEnabled(conexion);
        this.jPanel2.setEnabled(conexion);
        this.jPanel4.setEnabled(conexion);
        this.jTreeServer.setEnabled(conexion);
        this.jButtonPasarACliente.setEnabled(conexion);
        this.jButtonPasarAServer.setEnabled(conexion);
    }
    
    /**
     * Comprobacion de que el servidor esta alcanzable.
     *  Cambia el Icon de la barra principal en funcion de la conexion.
     */
    private void conectar() {
        this.url = this.jTextFieldInputURL.getText();
        this.puerto = Integer.parseInt(this.jTextFieldInputPuerto.getText());
        this.user = this.jTextFieldUser.getText();
        this.pwd = this.jPasswordFieldPassword.getPassword().toString();
        
        System.out.println("Conectando...");
        
        try {
            FTP.connect(url, puerto);
            conexion = FTP.login(user, pwd);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        if(conexion) System.out.println("¡Conexion Realizada!");
        else System.out.println("Credenciales no Correctas.");
        
        gestionControlesConexion(conexion);
    }
    
//    /**
//     * Recoleccion de datos necesarios para el envio de un fichero desde el Cliente al Server.
//     */
//    private void recoleccionDatosEnvioFichero() {
//        String rutaServerSeleccionada = conversionJTreePath.conversion(true, this.jTreeServer.getSelectionPath().toString()) +"\\";
//        String rutaLocalEntera = conversionJTreePath.conversion(false, MainWindow.jTreeCliente.getSelectionPath().toString());
//        String rutaLocalRecortada = rutaLocalEntera.substring(0, rutaLocalEntera.lastIndexOf('\\')+1);
//        String nombreFichero = rutaLocalEntera.substring(rutaLocalEntera.lastIndexOf('\\')+1);
//        
//        Facade.envioFicheroClienteServer(url, puerto, rutaServerSeleccionada, rutaLocalRecortada, nombreFichero);
//    }
    
//    /**
//     * Recoleccion de datos necesarios para recibir un fichero desde el Server al Cliente.
//     */
//    private void recoleccionDatosRecibimientoFichero() {
//        String rutaServerCompleta = conversionJTreePath.conversion(true, this.jTreeServer.getSelectionPath().toString());
//        String rutaServerRecortada = rutaServerCompleta.substring(0, rutaServerCompleta.lastIndexOf('\\')+1);
//        String rutaLocal = conversionJTreePath.conversion(false, MainWindow.jTreeCliente.getSelectionPath().toString()) +'\\';
//        String nombreFichero = rutaServerCompleta.substring(rutaServerCompleta.lastIndexOf('\\')+1);
//
//        Facade.envioFicheroServerCliente(url, puerto, rutaServerRecortada, rutaLocal, nombreFichero);
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelParametros = new javax.swing.JPanel();
        jLabelURL = new javax.swing.JLabel();
        jTextFieldInputURL = new javax.swing.JTextField();
        jLabelPuerto = new javax.swing.JLabel();
        jTextFieldInputPuerto = new javax.swing.JTextField();
        jButtonConectar = new javax.swing.JButton();
        jLabelEstadoConexion = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelUser = new javax.swing.JLabel();
        jLabelPassword = new javax.swing.JLabel();
        jTextFieldUser = new javax.swing.JTextField();
        jPasswordFieldPassword = new javax.swing.JPasswordField();
        jPanelArbolDirectorios = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeCliente = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        jButtonRefrescarCliente = new javax.swing.JButton();
        jButtonCrearCarpeta = new javax.swing.JButton();
        jButtonBorrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeServer = new javax.swing.JTree();
        jPanel4 = new javax.swing.JPanel();
        jButtonRefrescarServer = new javax.swing.JButton();
        jButtonCrearCarpetaServer = new javax.swing.JButton();
        jButtonBorrarServer = new javax.swing.JButton();
        jButtonPasarACliente = new javax.swing.JButton();
        jButtonPasarAServer = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemSalir = new javax.swing.JMenuItem();
        jMenuPreferencias = new javax.swing.JMenu();
        jCheckBoxMenuItemConfBorrar = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelParametros.setBorder(javax.swing.BorderFactory.createTitledBorder("Parametros"));

        jLabelURL.setText("URL");

        jTextFieldInputURL.setText("127.0.0.1");

        jLabelPuerto.setText("Puerto");

        jTextFieldInputPuerto.setText("6598");

        jButtonConectar.setText("<html><i>Iniciar Conexión</i></html>");
        jButtonConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConectarActionPerformed(evt);
            }
        });

        jLabelEstadoConexion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cross.png"))); // NOI18N

        jLabelUser.setText("User");

        jLabelPassword.setText("Password");

        jTextFieldUser.setText("mario");
        jTextFieldUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelParametrosLayout = new javax.swing.GroupLayout(jPanelParametros);
        jPanelParametros.setLayout(jPanelParametrosLayout);
        jPanelParametrosLayout.setHorizontalGroup(
            jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelParametrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelParametrosLayout.createSequentialGroup()
                        .addComponent(jLabelUser)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPasswordFieldPassword))
                    .addGroup(jPanelParametrosLayout.createSequentialGroup()
                        .addComponent(jLabelURL)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldInputURL, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelPuerto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldInputPuerto, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelEstadoConexion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConectar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelParametrosLayout.setVerticalGroup(
            jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelParametrosLayout.createSequentialGroup()
                .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelURL)
                    .addComponent(jTextFieldInputURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPuerto)
                    .addComponent(jTextFieldInputPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUser)
                    .addComponent(jLabelPassword)
                    .addComponent(jTextFieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelParametrosLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelEstadoConexion)
                    .addComponent(jButtonConectar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        jPanelArbolDirectorios.setBorder(javax.swing.BorderFactory.createTitledBorder("Gestion Ficheros"));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Arbol del Cliente"));

        jScrollPane1.setViewportView(jTreeCliente);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 2, 10))); // NOI18N

        jButtonRefrescarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/refresh.png"))); // NOI18N
        jButtonRefrescarCliente.setText("Actualizar");
        jButtonRefrescarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefrescarClienteActionPerformed(evt);
            }
        });

        jButtonCrearCarpeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/folder.png"))); // NOI18N
        jButtonCrearCarpeta.setText("Crear Carpeta");
        jButtonCrearCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearCarpetaActionPerformed(evt);
            }
        });

        jButtonBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        jButtonBorrar.setText("Borrar");
        jButtonBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonRefrescarCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jButtonCrearCarpeta)
                .addGap(26, 26, 26)
                .addComponent(jButtonBorrar)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonRefrescarCliente, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButtonBorrar)
                .addComponent(jButtonCrearCarpeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Arbol del Servidor"));
        jPanel2.setEnabled(false);

        jScrollPane2.setEnabled(false);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Sin Conexion");
        jTreeServer.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeServer.setEnabled(false);
        jScrollPane2.setViewportView(jTreeServer);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 2, 10))); // NOI18N
        jPanel4.setEnabled(false);

        jButtonRefrescarServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/refresh.png"))); // NOI18N
        jButtonRefrescarServer.setText("Actualizar");
        jButtonRefrescarServer.setEnabled(false);
        jButtonRefrescarServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefrescarServerActionPerformed(evt);
            }
        });

        jButtonCrearCarpetaServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/folder.png"))); // NOI18N
        jButtonCrearCarpetaServer.setText("Crear Carpeta");
        jButtonCrearCarpetaServer.setEnabled(false);
        jButtonCrearCarpetaServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearCarpetaServerActionPerformed(evt);
            }
        });

        jButtonBorrarServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        jButtonBorrarServer.setText("Borrar");
        jButtonBorrarServer.setEnabled(false);
        jButtonBorrarServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarServerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonRefrescarServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButtonCrearCarpetaServer)
                .addGap(18, 18, 18)
                .addComponent(jButtonBorrarServer)
                .addGap(17, 17, 17))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButtonCrearCarpetaServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonRefrescarServer)
                .addComponent(jButtonBorrarServer))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButtonPasarACliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flecha_forward.png"))); // NOI18N
        jButtonPasarACliente.setEnabled(false);
        jButtonPasarACliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPasarAClienteActionPerformed(evt);
            }
        });

        jButtonPasarAServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flecha_backward.png"))); // NOI18N
        jButtonPasarAServer.setEnabled(false);
        jButtonPasarAServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPasarAServerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelArbolDirectoriosLayout = new javax.swing.GroupLayout(jPanelArbolDirectorios);
        jPanelArbolDirectorios.setLayout(jPanelArbolDirectoriosLayout);
        jPanelArbolDirectoriosLayout.setHorizontalGroup(
            jPanelArbolDirectoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelArbolDirectoriosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelArbolDirectoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonPasarACliente, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(jButtonPasarAServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelArbolDirectoriosLayout.setVerticalGroup(
            jPanelArbolDirectoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelArbolDirectoriosLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jButtonPasarAServer)
                .addGap(36, 36, 36)
                .addComponent(jButtonPasarACliente)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuFile.setText("File");

        jMenuItemSalir.setText("Salir");
        jMenuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalirActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSalir);

        jMenuBar.add(jMenuFile);

        jMenuPreferencias.setText("Preferencias");

        jCheckBoxMenuItemConfBorrar.setText("Pedir Confirmacion al Borrar");
        jMenuPreferencias.add(jCheckBoxMenuItemConfBorrar);

        jMenuBar.add(jMenuPreferencias);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelArbolDirectorios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelParametros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelParametros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelArbolDirectorios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConectarActionPerformed
        conectar();
    }//GEN-LAST:event_jButtonConectarActionPerformed

    private void jButtonRefrescarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefrescarClienteActionPerformed
        setArbolCliente();
    }//GEN-LAST:event_jButtonRefrescarClienteActionPerformed

    private void jButtonCrearCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearCarpetaActionPerformed
//        crearDirectorio(false, MainWindow.jTreeCliente);
    }//GEN-LAST:event_jButtonCrearCarpetaActionPerformed

    private void jButtonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarActionPerformed
//        borrarFile(false, MainWindow.jTreeCliente);
    }//GEN-LAST:event_jButtonBorrarActionPerformed

    private void jMenuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemSalirActionPerformed

    private void jButtonRefrescarServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefrescarServerActionPerformed
//        setArbolServer();
    }//GEN-LAST:event_jButtonRefrescarServerActionPerformed

    private void jButtonCrearCarpetaServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearCarpetaServerActionPerformed
//        crearDirectorio(true, this.jTreeServer);
    }//GEN-LAST:event_jButtonCrearCarpetaServerActionPerformed

    private void jButtonBorrarServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarServerActionPerformed
//        borrarFile(true, this.jTreeServer);
//        setArbolServer();
    }//GEN-LAST:event_jButtonBorrarServerActionPerformed

    private void jButtonPasarAServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPasarAServerActionPerformed
//        Runnable r = () -> recoleccionDatosEnvioFichero();
//        new Thread(r).start();
    }//GEN-LAST:event_jButtonPasarAServerActionPerformed

    private void jButtonPasarAClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPasarAClienteActionPerformed
//        Runnable r = () -> recoleccionDatosRecibimientoFichero();
//        new Thread(r).start();
    }//GEN-LAST:event_jButtonPasarAClienteActionPerformed

    private void jTextFieldUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUserActionPerformed

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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        new MainWindow().setVisible(true);
        setArbolCliente();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBorrar;
    private javax.swing.JButton jButtonBorrarServer;
    private javax.swing.JButton jButtonConectar;
    private javax.swing.JButton jButtonCrearCarpeta;
    private javax.swing.JButton jButtonCrearCarpetaServer;
    private javax.swing.JButton jButtonPasarACliente;
    private javax.swing.JButton jButtonPasarAServer;
    private javax.swing.JButton jButtonRefrescarCliente;
    private javax.swing.JButton jButtonRefrescarServer;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemConfBorrar;
    private javax.swing.JLabel jLabelEstadoConexion;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelPuerto;
    private javax.swing.JLabel jLabelURL;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JMenu jMenuPreferencias;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelArbolDirectorios;
    private javax.swing.JPanel jPanelParametros;
    private javax.swing.JPasswordField jPasswordFieldPassword;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldInputPuerto;
    private javax.swing.JTextField jTextFieldInputURL;
    private javax.swing.JTextField jTextFieldUser;
    private static javax.swing.JTree jTreeCliente;
    private javax.swing.JTree jTreeServer;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Conversion del selected Path que da un JTree a Path 'correcto' como lo entiende Windows.
     */
    private static class conversionJTreePath {
        
        /**
         * Le damos un Path en estilo JTree y lo devolvemos en estilo Windows.
         * @param pathEstiloJTree Path a convertir [root, dirx].
         * @param isServer Si es server habra que aniadir el resto de la ruta para que opere.
         * @return Path convertido \root\dir.
         */
        private static String conversion(boolean isServer, String pathEstiloJTree) {
            String convertida = "";
            if(isServer) convertida += "..\\Server_FTP\\";
            String conversion = pathEstiloJTree.substring(1, pathEstiloJTree.length()-1); //Quitamos los dos [ ]. Inicial y final.
            
            conversion = conversion.replace(',', '\\'); //Cambio de comas por  \.
            
            for(char c : conversion.toCharArray()) { //Y quito espacios que no se porque JTree los pone por defecto en su ruta.
                if(c != ' ') convertida += c;
            }
            
            return convertida;
        }
    }

}
