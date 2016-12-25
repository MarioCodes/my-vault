/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import aplicacion.controlador.juego.Resolucion;
import aplicacion.patrones.Singleton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * todo: acordarme de añadir al final de todo hints y demas para cada boton. Ademas algun background e icon.
 * Ventana principal del programa. 
 * La Inner Class es muy parecida al metodo que utilice en los Filosofos para representar los circulos. La putada de todo esto es
 *  que las coordenadas a utilizar las tengo que mapear a mano.
 * @author Mario Codes Sánchez
 * @since 25/12/2016
 */
public class WindowJuego extends javax.swing.JFrame {
    private LineaGraficaCuadrado lineaSolucionNorte, lineaSolucionSur, lineaSolucionEste, lineaSolucionOeste; //Lineas graficas (ver Inner Class). Tienen que ser miembro para poder hacer referencia a ellas para quitarlas / ponerlas.
    private LineaGraficaCuadrado lineaCuadradoHorizontal1, lineaCuadradoHorizontal2, lineaCuadradoVertical1, lineaCuadradoVertical2; //Marcan la separacion de cada cuadrado. No las meto en Coleccion porque no quiero instanciarlas aun.
    
    /**
     * Creates new form MainWindow
     */
    public WindowJuego() {
        initComponents();
        
        this.setTitle("¡Sudoku!");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        
        creacionLineasCompletasTablero(jTableTrampas); //Esta la meto directamente en el constructor porque seran fijas. No las mareare..
        centrarTextoCells();
    }

    /**
     * Centrado de las labels de las cells.
     */
    private void centrarTextoCells() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < this.jTableTrampas.getColumnCount(); i++) {
            this.jTableJuego.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
            this.jTableTrampas.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
        
        jTabbedPanePrincipal.setEnabledAt(1, false); //Para que la de juego ni la de trampas se puedan seleccionar salvo cuando se haya creado el juego.
        jTabbedPanePrincipal.setEnabledAt(2, false); 
        jTabbedPanePrincipal.setEnabledAt(3, false); 
    }
    
    /**
     * Eliminamos una linea grafica de un tablero para poder sobreescribir. Si no las quito antes de poner otra, da conflicto.
     * @param tabla Tabla de la cual queremos eliminar la linea.
     * @param linea Linea la cual queremos quitar.
     */
    private void eliminarLineaTablero(JTable tabla, LineaGraficaCuadrado linea) {
        if(linea != null) tabla.remove(linea);
    }
    
    /**
     * Borramos las lineas internas si ya existian.
     * @param tabla Tabla de la cual eliminar las lineas internas.
     */
    private void eliminarLineasInternasTablero(JTable tabla) {
        eliminarLineaTablero(tabla, lineaCuadradoHorizontal1);
        eliminarLineaTablero(tabla, lineaCuadradoHorizontal2);
        eliminarLineaTablero(tabla, lineaCuadradoVertical1);
        eliminarLineaTablero(tabla, lineaCuadradoVertical2);
    }
    
    /**
     * Metemos a mano las lineas adicionales del tablero.
     */
    private void creacionLineasInternasTablero(Color color, JTable tabla) {
        int[][] parametrosLineasGraficasInternas = new int[][] {{130, 4, 5, 355}, //Mapeado a mano al pixel de precision. Hay que ajustarlo bien ya que si no se solapan con las lineas de color de resultado.
                                                            {259, 4, 5, 355}, 
                                                            {4, 120, 387, 5},
                                                            {4, 242, 387, 5}};
        
        tabla.add(lineaCuadradoVertical1 = new LineaGraficaCuadrado(color, parametrosLineasGraficasInternas[0][0], parametrosLineasGraficasInternas[0][1], parametrosLineasGraficasInternas[0][2], parametrosLineasGraficasInternas[0][3]));
        tabla.add(lineaCuadradoVertical2 = new LineaGraficaCuadrado(color, parametrosLineasGraficasInternas[1][0], parametrosLineasGraficasInternas[1][1], parametrosLineasGraficasInternas[1][2], parametrosLineasGraficasInternas[1][3]));
        tabla.add(lineaCuadradoHorizontal1 = new LineaGraficaCuadrado(color, parametrosLineasGraficasInternas[2][0], parametrosLineasGraficasInternas[2][1], parametrosLineasGraficasInternas[2][2], parametrosLineasGraficasInternas[2][3]));
        tabla.add(lineaCuadradoHorizontal2 = new LineaGraficaCuadrado(color, parametrosLineasGraficasInternas[3][0], parametrosLineasGraficasInternas[3][1], parametrosLineasGraficasInternas[3][2], parametrosLineasGraficasInternas[3][3]));
    }
    
    
    /**
     * Eliminacion de las lineas existentes. Si no las elimino y creo, no se cambiar el nivel de capa para poner unas encima de otras.
     * @param tabla Tabla donde se encuentran.
     */
    private void eliminarLineasExternasTablero(JTable tabla) {
        eliminarLineaTablero(tabla, lineaSolucionNorte);
        eliminarLineaTablero(tabla, lineaSolucionSur);
        eliminarLineaTablero(tabla, lineaSolucionEste);
        eliminarLineaTablero(tabla, lineaSolucionOeste);
    }
    
    /**
     * Eliminamos todas las lineas existentes que hemos metido y asignado previamente para poder meter nuevas.
     * @param tabla Tabla de la cual eliminar las lineas.
     */
    private void eliminarLineasCompletasTablero(JTable tabla) {
        eliminarLineasExternasTablero(tabla);
        eliminarLineasInternasTablero(tabla);
    }
    
    /**
     * Creacion y asignacion de las lineas de colores segun el resultado de la comprobacion.
     *  Verde: Solucionado.
     *  Rojo: No correcto.
     * todo: que al recomenzar una nueva partida desaparezcan o sean negras para que no se vean.
     * @param tabla tabla donde se colocaran las barras graficas.
     * @param color color del que seran las barras.
     */
    private void creacionLineasExternasTablero(Color color, JTable tabla) {
        int[][] parametrosLineasGraficas = new int[][] {{0, 0, 5, 400}, //Ejes x, y, ancho, alto. //Mapeado a mano, si se modifica la pantalla por cualquier cosa, habra que remapear.
                                                            {0, 0, 400, 5},
                                                            {390, 0, 5, 400},
                                                            {0, 358, 400, 5}};
        
        tabla.add(lineaSolucionNorte = new LineaGraficaCuadrado(color, parametrosLineasGraficas[0][0], parametrosLineasGraficas[0][1], parametrosLineasGraficas[0][2], parametrosLineasGraficas[0][3]));
        tabla.add(lineaSolucionSur = new LineaGraficaCuadrado(color, parametrosLineasGraficas[1][0], parametrosLineasGraficas[1][1], parametrosLineasGraficas[1][2], parametrosLineasGraficas[1][3]));
        tabla.add(lineaSolucionEste = new LineaGraficaCuadrado(color, parametrosLineasGraficas[2][0], parametrosLineasGraficas[2][1], parametrosLineasGraficas[2][2], parametrosLineasGraficas[2][3]));
        tabla.add(lineaSolucionOeste = new LineaGraficaCuadrado(color, parametrosLineasGraficas[3][0], parametrosLineasGraficas[3][1], parametrosLineasGraficas[3][2], parametrosLineasGraficas[3][3]));
    }
    
    /**
     * Agrupamiento de todas las operaciones necesarias para crear todas las lineas necesarias del tablero o 'cambiar' su color.
     *  Comprueba si existen lineas previas, si las hay se borran.
     *  Creamos nuevas lineas con el nuevo color indicado.
     *  Hacemos un tabla.repaint() para que se muestren los cambios.
     * @param color Nuevo color del que queremos las nuevas lineas.
     * @param tabla Tabla sobre la que queremos operar.
     */
    private void creacionLineasCompletasTablero(Color color, JTable tabla) {
        eliminarLineasCompletasTablero(tabla);
        creacionLineasExternasTablero(color, tabla);
        creacionLineasInternasTablero(color, tabla);
        tabla.repaint();
    }
    
    /**
     * Version a utilizar la primera vez que se crea el juego o cada vez que se empieza uno nuevo.
     * @param tabla Tabla sobre la que operar.
     */
    private void creacionLineasCompletasTablero(JTable tabla) {
        creacionLineasCompletasTablero(Color.BLACK, tabla);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPanePrincipal = new javax.swing.JTabbedPane();
        jPanelMenuOpcionesJuego = new javax.swing.JPanel();
        jButtonJugar = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();
        jButtonPartidaNueva = new javax.swing.JButton();
        jButtonSolventar = new javax.swing.JButton();
        jTableJuego = new javax.swing.JTable();
        jTableTrampas = new javax.swing.JTable();
        jPanelSolventar = new javax.swing.JPanel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuOpciones = new javax.swing.JMenu();
        jMenuItemAcercaDe = new javax.swing.JMenuItem();
        jMenuItemSalir = new javax.swing.JMenuItem();
        jMenuJuego = new javax.swing.JMenu();
        jMenuItemComprobarSolucion = new javax.swing.JMenuItem();
        jMenuItemSolventarSudoku = new javax.swing.JMenuItem();
        jMenuTesteo = new javax.swing.JMenu();
        jMenuItemActivarTrampas = new javax.swing.JMenuItem();
        jMenuItemTesteoTablero = new javax.swing.JMenuItem();
        jMenuItemCopiarTableroTrampas = new javax.swing.JMenuItem();
        jMenuItemOcultarCasilla = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPanePrincipal.setFont(new java.awt.Font("sansserif", 2, 12)); // NOI18N

        jButtonJugar.setText("Jugar Partida");
        jButtonJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJugarActionPerformed(evt);
            }
        });

        jLabelTitulo.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabelTitulo.setText("Sudoku");

        jButtonPartidaNueva.setText("Partida Nueva");
        jButtonPartidaNueva.setEnabled(false);
        jButtonPartidaNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPartidaNuevaActionPerformed(evt);
            }
        });

        jButtonSolventar.setText("Solucionar Sudoku 'Custom'");
        jButtonSolventar.setEnabled(false);

        javax.swing.GroupLayout jPanelMenuOpcionesJuegoLayout = new javax.swing.GroupLayout(jPanelMenuOpcionesJuego);
        jPanelMenuOpcionesJuego.setLayout(jPanelMenuOpcionesJuegoLayout);
        jPanelMenuOpcionesJuegoLayout.setHorizontalGroup(
            jPanelMenuOpcionesJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuOpcionesJuegoLayout.createSequentialGroup()
                .addGroup(jPanelMenuOpcionesJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMenuOpcionesJuegoLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabelTitulo))
                    .addGroup(jPanelMenuOpcionesJuegoLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanelMenuOpcionesJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonPartidaNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonJugar, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonSolventar, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanelMenuOpcionesJuegoLayout.setVerticalGroup(
            jPanelMenuOpcionesJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuOpcionesJuegoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonJugar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPartidaNueva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSolventar)
                .addContainerGap(206, Short.MAX_VALUE))
        );

        jTabbedPanePrincipal.addTab("Menu Principal", jPanelMenuOpcionesJuego);

        jTableJuego.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jTableJuego.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTableJuego.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", "", "", "", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableJuego.setMinimumSize(new java.awt.Dimension(135, 333));
        jTableJuego.setPreferredSize(new java.awt.Dimension(400, 390));
        jTableJuego.setRowHeight(41);
        jTableJuego.setRowSelectionAllowed(false);
        jTableJuego.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTabbedPanePrincipal.addTab("Juego", jTableJuego);

        jTableTrampas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jTableTrampas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTableTrampas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", "", "", "", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableTrampas.setColumnSelectionAllowed(true);
        jTableTrampas.setEnabled(false);
        jTableTrampas.setMinimumSize(new java.awt.Dimension(135, 351));
        jTableTrampas.setPreferredSize(new java.awt.Dimension(400, 390));
        jTableTrampas.setRowHeight(41);
        jTableTrampas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTabbedPanePrincipal.addTab("Trampas", jTableTrampas);

        javax.swing.GroupLayout jPanelSolventarLayout = new javax.swing.GroupLayout(jPanelSolventar);
        jPanelSolventar.setLayout(jPanelSolventarLayout);
        jPanelSolventarLayout.setHorizontalGroup(
            jPanelSolventarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
        );
        jPanelSolventarLayout.setVerticalGroup(
            jPanelSolventarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 351, Short.MAX_VALUE)
        );

        jTabbedPanePrincipal.addTab("Solventar", jPanelSolventar);

        jMenuOpciones.setText("File");

        jMenuItemAcercaDe.setText("Acerca De...");
        jMenuOpciones.add(jMenuItemAcercaDe);

        jMenuItemSalir.setText("Salir");
        jMenuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalirActionPerformed(evt);
            }
        });
        jMenuOpciones.add(jMenuItemSalir);

        jMenuBar.add(jMenuOpciones);

        jMenuJuego.setText("Juego");

        jMenuItemComprobarSolucion.setText("Comprobar Solucion");
        jMenuItemComprobarSolucion.setEnabled(false);
        jMenuItemComprobarSolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemComprobarSolucionActionPerformed(evt);
            }
        });
        jMenuJuego.add(jMenuItemComprobarSolucion);

        jMenuItemSolventarSudoku.setText("Solventar Sudoku Actual");
        jMenuItemSolventarSudoku.setEnabled(false);
        jMenuItemSolventarSudoku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSolventarSudokuActionPerformed(evt);
            }
        });
        jMenuJuego.add(jMenuItemSolventarSudoku);

        jMenuBar.add(jMenuJuego);

        jMenuTesteo.setText("Testeo");

        jMenuItemActivarTrampas.setText("Activar Tablero Trampas");
        jMenuItemActivarTrampas.setEnabled(false);
        jMenuItemActivarTrampas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActivarTrampasActionPerformed(evt);
            }
        });
        jMenuTesteo.add(jMenuItemActivarTrampas);

        jMenuItemTesteoTablero.setText("Print Tablero");
        jMenuItemTesteoTablero.setEnabled(false);
        jMenuItemTesteoTablero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTesteoTableroActionPerformed(evt);
            }
        });
        jMenuTesteo.add(jMenuItemTesteoTablero);

        jMenuItemCopiarTableroTrampas.setText("Copiar Tablero Trampas a Normal");
        jMenuItemCopiarTableroTrampas.setEnabled(false);
        jMenuItemCopiarTableroTrampas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCopiarTableroTrampasActionPerformed(evt);
            }
        });
        jMenuTesteo.add(jMenuItemCopiarTableroTrampas);

        jMenuItemOcultarCasilla.setText("Ocultar Casilla Especifica");
        jMenuItemOcultarCasilla.setEnabled(false);
        jMenuItemOcultarCasilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOcultarCasillaActionPerformed(evt);
            }
        });
        jMenuTesteo.add(jMenuItemOcultarCasilla);

        jMenuBar.add(jMenuTesteo);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPanePrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPanePrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemSalirActionPerformed
       
    /**
     * Recopilacion de todos los enableds necesarios al comenzar un juego.
     */
    private void setEnabledsIniJuego() {
        this.jTabbedPanePrincipal.setEnabledAt(2, false);
        this.jTabbedPanePrincipal.setEnabledAt(1, true);
        this.jMenuItemCopiarTableroTrampas.setEnabled(true);
        this.jMenuItemComprobarSolucion.setEnabled(true);
        this.jTabbedPanePrincipal.setSelectedIndex(1);
        this.jButtonJugar.setEnabled(false);
        this.jButtonPartidaNueva.setEnabled(true);
        this.jMenuItemTesteoTablero.setEnabled(true);
        this.jMenuItemActivarTrampas.setEnabled(true);
        this.jMenuItemOcultarCasilla.setEnabled(true);
    }
    
    /**
     * Ini de todo lo necesario para comenzar un juego.
     * Esta puesto tal para que con rellamar a este metodo al crear partida nueva, funcione correctamente y se resetee todo lo que debe.
     */
    private void iniJuego() {
        Singleton.getFacadeSingleton().generacionTablero(this.jTableJuego, false);
        Singleton.getFacadeSingleton().generacionTablero(this.jTableTrampas, true);
        Singleton.getFacadeSingleton().ocultarNumerosTablero(jTableJuego);
        creacionLineasCompletasTablero(jTableJuego);
        setEnabledsIniJuego();
    }
    
    private void jButtonJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJugarActionPerformed
        iniJuego();
    }//GEN-LAST:event_jButtonJugarActionPerformed

    private void jMenuItemActivarTrampasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActivarTrampasActionPerformed
        this.jTabbedPanePrincipal.setEnabledAt(2, true);
    }//GEN-LAST:event_jMenuItemActivarTrampasActionPerformed

    private void jButtonPartidaNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPartidaNuevaActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(this, "Se perdera el juego actual y se comenzara uno nuevo. ¿Seguro?", "Confirmacion", JOptionPane.YES_NO_OPTION);
        if(confirmacion == 0) {
            Singleton.getTableroNuevoSingleton();
            iniJuego();
        }
    }//GEN-LAST:event_jButtonPartidaNuevaActionPerformed

    private void jMenuItemTesteoTableroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTesteoTableroActionPerformed
        System.out.println(Singleton.getTableroSingleton());
    }//GEN-LAST:event_jMenuItemTesteoTableroActionPerformed

    private void jMenuItemSolventarSudokuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSolventarSudokuActionPerformed
        Resolucion.solucionFuerzaBruta(this.jTableJuego);
    }//GEN-LAST:event_jMenuItemSolventarSudokuActionPerformed

    
    /**
     * Preguntamos por 2 coordenadas (0-8).
     */
    private void ocultarCasillaEspecifica() {
        int fila, casilla;
        
        System.out.println("Ocultar Casilla. Coordenadas (0-8)");
        
        fila = GestionGrafica.askInteger("\tNumero de fila: ");
        casilla = GestionGrafica.askInteger("\tNumero de casillas segun LA FILA: ");
        
        Singleton.getFacadeSingleton().ocultarCasilla(jTableJuego, fila, casilla);
    }
    
    private void jMenuItemOcultarCasillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOcultarCasillaActionPerformed
        new Thread(() -> ocultarCasillaEspecifica()).start(); //Para que no se quede la GUI colgada esperando.
    }//GEN-LAST:event_jMenuItemOcultarCasillaActionPerformed
    
    /**
     * Gestion del apartado grafico con la solucion.
     */
    private void comprobarSolucionGrafico() {
        int resultado = Singleton.getFacadeSingleton().comprobarSolucionTablero(jTableJuego, jTableTrampas);

        switch(resultado) {
            case 1: //Correcto.
                creacionLineasCompletasTablero(Color.GREEN, jTableJuego);
                JOptionPane.showMessageDialog(null, "¡Sudoku Solucionado Correctamente!", "Sudoku Solucionado", JOptionPane.INFORMATION_MESSAGE);
                break;
            case -1: //Incorrecto.
                creacionLineasCompletasTablero(Color.RED, jTableJuego);
                JOptionPane.showMessageDialog(null, "Solucion Incorrecta.", "Sudoku no Solucionado", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 0: //Incompleto.
                creacionLineasCompletasTablero(Color.BLUE, jTableJuego);
                JOptionPane.showMessageDialog(null, "Tablero Incompleto.\n Comprueba que solo haya numeros y no existan casillas vacias.", "Error en el tablero", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                creacionLineasCompletasTablero(Color.MAGENTA, jTableJuego); //Identificativo, no tendria que poder llegar aqui.
                System.out.println("default comprobarSolucionGrafico()");
                break;
        }
    }
    
    private void jMenuItemComprobarSolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemComprobarSolucionActionPerformed
        comprobarSolucionGrafico();
    }//GEN-LAST:event_jMenuItemComprobarSolucionActionPerformed

    private void jMenuItemCopiarTableroTrampasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCopiarTableroTrampasActionPerformed
        Singleton.getFacadeSingleton().copiarTableroTrampasAlNormal(jTableJuego, jTableTrampas);
    }//GEN-LAST:event_jMenuItemCopiarTableroTrampasActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonJugar;
    private javax.swing.JButton jButtonPartidaNueva;
    private javax.swing.JButton jButtonSolventar;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItemAcercaDe;
    private javax.swing.JMenuItem jMenuItemActivarTrampas;
    private javax.swing.JMenuItem jMenuItemComprobarSolucion;
    private javax.swing.JMenuItem jMenuItemCopiarTableroTrampas;
    private javax.swing.JMenuItem jMenuItemOcultarCasilla;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JMenuItem jMenuItemSolventarSudoku;
    private javax.swing.JMenuItem jMenuItemTesteoTablero;
    private javax.swing.JMenu jMenuJuego;
    private javax.swing.JMenu jMenuOpciones;
    private javax.swing.JMenu jMenuTesteo;
    private javax.swing.JPanel jPanelMenuOpcionesJuego;
    private javax.swing.JPanel jPanelSolventar;
    private javax.swing.JTabbedPane jTabbedPanePrincipal;
    private javax.swing.JTable jTableJuego;
    private javax.swing.JTable jTableTrampas;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Inner Class hecha unicamente para la representacion de las lineas graficas que utilizare en el tablero.
     * Leyenda:
     *  Negra - Estado inicial.
     *  Azul - Tablero incompleto (tiene casillas vacias).
     *  Rojo - Tablero completo pero solucion no valida.
     *  Verde - Tablero resuelto. Habra que hacer disables oportunos.
     */
    private class LineaGraficaCuadrado extends JPanel {        
        /**
         * Constructor por defecto.
         * @param ejeX Coordenadas del eje x.
         * @param ejeY Coordenadas del eje y.
         * @param ancho Ancho que ocupara la linea.
         * @param alto Alto de la linea.
         */
        public LineaGraficaCuadrado(int ejeX, int ejeY, int ancho, int alto) {
            setBackground(Color.black);
            this.setBounds(ejeX, ejeY, ancho, alto);
        }
        
        /**
         * Constructor para crearla con color custom.
         * @param color Color que le asignamos a la barra.
         * @param ejeX Coordenadas del eje x.
         * @param ejeY Coordenadas del eje y.
         * @param ancho Ancho que ocupara la linea.
         * @param alto Alto de la linea.
         */
        public LineaGraficaCuadrado(Color color, int ejeX, int ejeY, int ancho, int alto) {
            setBackground(color);
            this.setBounds(ejeX, ejeY, ancho, alto);
        }
    }

}
