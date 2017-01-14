/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import aplicacion.controlador.juego.ResolucionHumana;
import aplicacion.controlador.tablero.Tablero;
import aplicacion.patrones.Singleton;
import java.awt.Color;
import java.awt.Component;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * todo: acordarme de añadir al final de todo hints y demas para cada boton. Ademas algun background e icon.
 * fixme: Arreglar los .enabled de los 2 botones de solucion automatica. Que solo pueda haber uno activo a la vez y que si se pulsa el otro se haga limpieza antes de poner sus datos/modificaciones.
 * Ventana principal y unica del programa. 
 * La Inner Class es muy parecida a lo que utilice en los Filosofos para representar los circulos. La putada de esto es
 *  que las coordenadas a utilizar las tengo que mapear a mano por lo que la ventana no puede ser 'resizable'.
 * Estoy teniendo conflictos con las tablas y las lineas graficas si lo hago de forma grafica por Swing, por eso las aniado a mano.
 * @author Mario Codes Sánchez
 * @since 30/12/2016
 * @version 0.2 Quitada la tabla principal de juego generada por Swing y creado una a mano. Necesario para poder hacer override de los renderer.
 */
public class WindowJuego extends javax.swing.JFrame {
    private final URL ICONO_URL = getClass().getResource("../imagenes/icon.png");
    private JTable jTableJuegoCustom, jTableResolver; //Esta la inicializo mas adelante haciendo override de un par de metodos.
    private LineaGraficaCuadrado[] lineasGraficasExternas = new LineaGraficaCuadrado[4];
    private LineaGraficaCuadrado[] lineasGraficasInternas = new LineaGraficaCuadrado[4];
    private String modoResolucion; //Para saber si se esta en resolucion humana o por fuerza bruta.
    
    /**
     * Creates new form MainWindow
     */
    public WindowJuego() {
        initComponents();

        this.setTitle("¡Sudoku!");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        
        setIcono();
        iniTablaPrincipalJuego(); //Es necesario que este aqui, ya que como es una jTable que hago a mano, solo aparecera como pestania cuando se aniada. Si no al comienzo no apareceria hasta darle al boton.
        iniTablaResolver();
        disablePestaniasIniciales();
        disableMenusIniciales();
    }

    /**
     * Set del icono de la ventana.
     */
    private void setIcono() {
        ImageIcon icon = new ImageIcon(getIconoUrl());
        this.setIconImage(icon.getImage());
    }
    
    /**
     * Set del mode de la tabla principal. Lo copio de la que tenia hecha y creada antes por Swing. No tocar a menos que sea necesario.
     *  Hago Override de isCellEditable() para hacer que las celdas que se consideran fijas, no se puedan modificar.
     * @param tablaPrincipal Tabla a la que le ponemos su mode.
     */
    private void setModeTablaPrincipalJuego(JTable tablaPrincipal) {
        tablaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
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
            
            @Override
            public boolean isCellEditable(int row, int column) { //Esto es lo unico que he aniadido al Mode creado por Swing.
                Tablero tablero = Singleton.getTableroActual(); //Se encarga de filtrar si cada celda debe ser editable de manera individual.
                return !tablero.getFILAS()[row].getCASILLAS()[column].isCasillaFija();
            }
        });
    }
    
    /**
     * Comprobamos si la tabla ya existe, si lo hace la eliminamos.
     * @param tabla Tabla que queremos eliminar del tabbedPane.
     */
    private void removeTablaPrincipalJuego(JTable tabla) {
       if(tabla != null) jTabbedPanePrincipal.remove(tabla);
    }
    
    /**
     * Set de un mode normal para la tabla de resolucion.
     * No puedo usar el otro que tenia creado porque me hace algunas celdas ineditables.
     * @param tabla Tabla a la cual asignarle el mode.
     */
    private void setModeComunTalba(JTable tabla) {
        tabla.setModel(new javax.swing.table.DefaultTableModel(
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
    }
    
    /**
     * Inicializacion de la tabla para Resolver.
     */
    private void iniTablaResolver() {
        removeTablaPrincipalJuego(jTableResolver);
        jTableResolver = new JTable();
        
        setModeComunTalba(jTableResolver);
        jTableResolver.setFont(new java.awt.Font("Tahoma", 1, 12));
        jTableResolver.setRowHeight(41);
        jTableResolver.setRowSelectionAllowed(false);
        jTableResolver.setName("Resolucion");
        jTabbedPanePrincipal.add(jTableResolver, 3);
    }
    
    /**
     * Prepara las lineas de la tabla para resolver un Sudoku.
     * Antes hacia la inicializacion aqui, pero como necesito que se haga al principio para que la pestania este disponible, la
     *  hago en el constructor.
     */
    private void preparacionTablaResolver() {
        creacionLineasCompletasTablero(jTableResolver);
        centrarTextoCellsTabla(jTableResolver);
    }
    
    /**
     * Inicializacion de la tabla principal sobre la que se jugara.
     * Lo tengo que hacer a mano ya que necesito hacer un override de 'prepareRenderer'. De esta forma puedo capturar celdas sueltas
     *  y hacer cambios a las que contengan numeros fijos desde el principio respecto a las que se van cambiando.
     */
    private void iniTablaPrincipalJuego() {
        Tablero tablero = Singleton.getTableroActual();
        removeTablaPrincipalJuego(jTableJuegoCustom);
        jTableJuegoCustom = new JTable() {
          @Override
          public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
              Component component = super.prepareRenderer(renderer, row, col);
              Boolean isVisible = tablero.getFILAS()[row].getCASILLAS()[col].isCasillaFija();
              
              if(isVisible) component.setForeground(Color.GRAY);
              else component.setForeground(Color.BLACK);
              
              return component;
          }
        };
        
        setModeTablaPrincipalJuego(jTableJuegoCustom);
        jTableJuegoCustom.setFont(new java.awt.Font("Tahoma", 1, 12));
        jTableJuegoCustom.setRowHeight(41);
        jTableJuegoCustom.setRowSelectionAllowed(false);
        jTableJuegoCustom.setName("Juego Custom");
        jTabbedPanePrincipal.add(jTableJuegoCustom, 1);
    }
    
    /**
     * Para que la de juego ni la de trampas se puedan seleccionar salvo cuando se haya creado el juego.
     */
    private void disablePestaniasIniciales() {
        jTabbedPanePrincipal.setEnabledAt(1, false);
        jTabbedPanePrincipal.setEnabledAt(2, false); 
        jTabbedPanePrincipal.setEnabledAt(3, false); 
    }
    
    /**
     * Para que los menus iniciales de la barra esten disabled desde el inicio.
     */
    private void disableMenusIniciales() {
        jMenuJuegoNormal.setEnabled(false);
        jMenuResolucionAuto.setEnabled(false);
        jMenuDebug.setEnabled(false);
    }
    
    /**
     * Centrado de todas las labels de la tabla que se pasa como parametro.
     * @param tabla Tabla de la cual queremos centrar sus labels.
     */
    private void centrarTextoCellsTabla(JTable tabla) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    /**
     * Eliminamos una linea grafica de un tablero para poder sobreescribirla, SOLO si esta existe. 
     *  Si no las quito antes de poner otra, da conflicto. Y como no tengo ganas de ponerme a trabajar en plan capas, prefiero esto.
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
        for(LineaGraficaCuadrado linea: lineasGraficasInternas) {
            eliminarLineaTablero(tabla, linea);
        }
    }
    
    /**
     * Metemos a mano las lineas adicionales del tablero.
     */
    private void creacionLineasInternasTablero(Color color, JTable tabla) {
        int[][] parametrosLineasGraficasInternas = new int[][] {{130, 4, 5, 358}, //Mapeado a mano al pixel de precision. Hay que ajustarlo bien ya que si no se solapan con las lineas de color de resultado.
                                                            {259, 4, 5, 358}, 
                                                            {4, 120, 387, 5},
                                                            {4, 242, 387, 5}};
        
        tabla.add(lineasGraficasInternas[0] = new LineaGraficaCuadrado(color, parametrosLineasGraficasInternas[0][0], parametrosLineasGraficasInternas[0][1], parametrosLineasGraficasInternas[0][2], parametrosLineasGraficasInternas[0][3]));
        tabla.add(lineasGraficasInternas[1] = new LineaGraficaCuadrado(color, parametrosLineasGraficasInternas[1][0], parametrosLineasGraficasInternas[1][1], parametrosLineasGraficasInternas[1][2], parametrosLineasGraficasInternas[1][3]));
        tabla.add(lineasGraficasInternas[2] = new LineaGraficaCuadrado(color, parametrosLineasGraficasInternas[2][0], parametrosLineasGraficasInternas[2][1], parametrosLineasGraficasInternas[2][2], parametrosLineasGraficasInternas[2][3]));
        tabla.add(lineasGraficasInternas[3] = new LineaGraficaCuadrado(color, parametrosLineasGraficasInternas[3][0], parametrosLineasGraficasInternas[3][1], parametrosLineasGraficasInternas[3][2], parametrosLineasGraficasInternas[3][3]));
    }
    
    
    /**
     * Eliminacion de las lineas existentes. Si no las elimino y creo, no se cambiar el nivel de capa para poner unas encima de otras.
     * @param tabla Tabla donde se encuentran.
     */
    private void eliminarLineasExternasTablero(JTable tabla) {
        for(LineaGraficaCuadrado linea: lineasGraficasExternas) {
            eliminarLineaTablero(tabla, linea);
        }
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
     * @param tabla tabla donde se colocaran las barras graficas.
     * @param color color del que seran las barras.
     */
    private void creacionLineasExternasTablero(Color color, JTable tabla) {
        int[][] parametrosLineasGraficas = new int[][] {{0, 0, 5, 400}, //Ejes x, y, ancho, alto. //Mapeado a mano, si se modifica la pantalla por cualquier cosa, habra que remapear.
                                                            {0, 0, 400, 5},
                                                            {390, 0, 5, 400},
                                                            {0, 361, 400, 5}};
        
        tabla.add(lineasGraficasExternas[0] = new LineaGraficaCuadrado(color, parametrosLineasGraficas[0][0], parametrosLineasGraficas[0][1], parametrosLineasGraficas[0][2], parametrosLineasGraficas[0][3]));
        tabla.add(lineasGraficasExternas[1] = new LineaGraficaCuadrado(color, parametrosLineasGraficas[1][0], parametrosLineasGraficas[1][1], parametrosLineasGraficas[1][2], parametrosLineasGraficas[1][3]));
        tabla.add(lineasGraficasExternas[2] = new LineaGraficaCuadrado(color, parametrosLineasGraficas[2][0], parametrosLineasGraficas[2][1], parametrosLineasGraficas[2][2], parametrosLineasGraficas[2][3]));
        tabla.add(lineasGraficasExternas[3] = new LineaGraficaCuadrado(color, parametrosLineasGraficas[3][0], parametrosLineasGraficas[3][1], parametrosLineasGraficas[3][2], parametrosLineasGraficas[3][3]));
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
        tabla.repaint(); //Necesario para que se muestren los cambios.
    }
    
    /**
     * Version a utilizar la primera vez que se crea el juego o cada vez que se empieza uno nuevo.
     * @param tabla Tabla sobre la que operar.
     */
    private void creacionLineasCompletasTablero(JTable tabla) {
        creacionLineasCompletasTablero(Color.BLACK, tabla);
    }
    
    /**
     * Gestion del apartado grafico segun el estado de la solucion.
     */
    private void comprobarSolucionJuegoPrincipal() {
        int resultado = Singleton.getFacade().comprobarSolucionTablero(jTableJuegoCustom);

        switch(resultado) {
            case 1: //Correcto.
                creacionLineasCompletasTablero(Color.GREEN, jTableJuegoCustom);
                disablesJuegoGanado();
                JOptionPane.showMessageDialog(null, "¡Sudoku Solucionado Correctamente!", "Sudoku Solucionado", JOptionPane.INFORMATION_MESSAGE);
                break;
            case -1: //Incorrecto.
                creacionLineasCompletasTablero(Color.RED, jTableJuegoCustom);
                JOptionPane.showMessageDialog(null, "Solucion Incorrecta.", "Sudoku no Solucionado", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 0: //Incompleto.
                creacionLineasCompletasTablero(Color.BLUE, jTableJuegoCustom);
                JOptionPane.showMessageDialog(null, "Tablero Incompleto.\n Comprueba que solo haya numeros y no existan casillas vacias.", "Error en el tablero", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                creacionLineasCompletasTablero(Color.MAGENTA, jTableJuegoCustom); //Identificativo, no tendria que poder llegar aqui.
                System.out.println("default comprobarSolucionGrafico()");
                break;
        }
    }
    
    /**
     * Scanner para pedir Integer.
     * @param output Output que se mostrarara al user.
     * @return Integer metido por el usuario.
     */
    private int askInteger(String output) {
        Scanner scanner;
        int number = -1;
        do {
            try {
                scanner = new Scanner(System.in);
                System.out.print(output);
                number = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Dato no valido.");
            }
        } while (number == -1);
        return number;
    }
    
    /**
     * Ocultamos una casilla, tanto su representacion grafica de la tabla como su .isVisible().
     * Se hace mediante 2 coordenadas (0-8).
     */
    private void ocultarCasillaEspecifica() {
        int fila, casilla;
        
        System.out.println("Ocultar Casilla. Coordenadas (0-8)");
        
        fila = askInteger("\tNumero de fila: ");
        casilla = askInteger("\tNumero de casillas segun LA FILA: ");
        
        Singleton.getFacade().ocultacionCasillaEspecifica(jTableJuegoCustom, fila, casilla);
    }
    
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
        this.jMenuItemActivarTrampas.setEnabled(true);
        this.jMenuItemOcultarCasilla.setEnabled(true);
        this.jMenuDebug.setEnabled(true);
        this.jMenuJuegoNormal.setEnabled(true);
        if(!jButtonSolventarBacktrack.isEnabled()) jMenuItemCopiarTableroNormalAResolver.setEnabled(true); //Ha sido pulsado cuando !enabled.
        if(!jButtonSolventarHumana.isEnabled()) jMenuItemCopiarTableroNormalAResolver.setEnabled(true); //
    }
    
    /**
     * Ini de lo necesario para comenzar un juego.
     * Esta puesto tal para que con rellamarlo al crear partida nueva, funcione correctamente y se resetee lo que debe.
     */
    private void iniJuego() {
        creacionLineasCompletasTablero(jTableTrampas); //Esta la meto directamente en el constructor porque seran fijas. No las mareare..
        centrarTextoCellsTabla(jTableTrampas);
        centrarTextoCellsTabla(jTableJuegoCustom);
        
        Singleton.getFacade().rellenoTablaConTablero(this.jTableJuegoCustom, false);
        Singleton.getFacade().rellenoTablaConTablero(this.jTableTrampas, true);
        Singleton.getFacade().ocultarNumerosTablero(jTableJuegoCustom);
        creacionLineasCompletasTablero(jTableJuegoCustom);
        setEnabledsIniJuego();
    }
    
    /**
     * Recopilacion de disables necesarios para cuando se gana un juego de manera correcta.
     */
    private void disablesJuegoGanado() {
        this.jMenuItemComprobarSolucion.setEnabled(false);
    }
        
    /**
     * Recopilacion de disables/enables necesarios al activar la opcion de resolucion por fuerza bruta.
     */
    private void gestionEnabledsFuerzaBruta() {
        this.modoResolucion = "Backtrack";
        jTabbedPanePrincipal.setEnabledAt(3, true);
        jTabbedPanePrincipal.setSelectedIndex(3);
        if(!jButtonJugar.isEnabled()) jMenuItemCopiarTableroNormalAResolver.setEnabled(true);
        if(jMenuJuegoNormal.isEnabled()) jMenuDebug.setEnabled(true);
        jButtonSolventarBacktrack.setEnabled(false);
        jMenuResolucionAuto.setEnabled(true);
        jMenuItemBorrarTableroAutomatico.setEnabled(true);
        jMenuItemSolventarSudoku.setEnabled(true);
        jMenuResolucionAuto.setText("Res. Fuerza Bruta");
        this.jButtonSolventarHumana.setEnabled(true);
    }
    
    /**
     * Recopilacion de disables/enables necesarios al activar la opcion de resolucion por tecnicas humanas.
     */
    private void gestionEnabledsSolucionHumana() {
        this.modoResolucion = "Humana";
        jTabbedPanePrincipal.setEnabledAt(3, true);
        jTabbedPanePrincipal.setSelectedIndex(3);
        if(!jButtonJugar.isEnabled()) jMenuItemCopiarTableroNormalAResolver.setEnabled(true);
        if(jMenuJuegoNormal.isEnabled()) jMenuDebug.setEnabled(true);
        jButtonSolventarHumana.setEnabled(false);
        jMenuResolucionAuto.setEnabled(true);
        jMenuItemBorrarTableroAutomatico.setEnabled(true);
        jMenuItemSolventarSudoku.setEnabled(true);
        jButtonSolventarBacktrack.setEnabled(true);
        jMenuResolucionAuto.setText("Res. Reglas 'Humanas'");
    }
    
    /**
     * Limpieza de la tabla para el caso de que sea no resoluble mediante fuerza bruta.
     *  No se porque me salen todas las casillas vacias a 0.
     */
    private void limpiezaTablaIrresoluble() {
        JTable tabla = this.jTableResolver;
        
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                try {
                    if((int) tabla.getValueAt(row, column) == 0) tabla.setValueAt(null, row, column);
                }catch(ClassCastException ex) {} //Por si acaso pillara una celda sin valor, que no pete.
            }
        }
    }
    
    /**
     * Para distinguir entre cuando se esta intentando mediante fuerza bruta o tecnicas humanas.
     * @return Estado de resolucion, True si se ha resuelto correctamente.
     */
    private boolean gestionModoResolucion() {
        boolean resultado;
        switch(this.modoResolucion) {
            case "Humana":
                resultado = Singleton.getFacade().solucionHumana(jTableResolver);
                if(resultado) {
                    JOptionPane.showMessageDialog(this, "Sudoku resuelto correctamente mediante tecnicas 'Humanas'.");
                    return true;
                } else { //No hago output de mensaje porque mostrare mas adelante un confirm.
                    return false;
                }
            case "Backtrack":
                resultado = Singleton.getFacade().solucionBacktrack(jTableResolver);
                if(resultado) {
                    JOptionPane.showMessageDialog(this, "Sudoku resuelto correctamente mediante Fuerza Bruta.");
                    return true;
                } else {
                    limpiezaTablaIrresoluble();
                    JOptionPane.showMessageDialog(this, "Sudoku no resoluble mediante Fuerza Bruta. \nComprobar que este bien generado.");
                    return false;
                }
            default: //Por si acaso.
                System.out.println("Estado no alcanzable en gestionModoResolucion()");
                return Singleton.getFacade().solucionBacktrack(jTableResolver);
        }
    }
    
    /**
     * Conjunto de metodos encargados de resolver el Sudoku de manera automatica.
     */
    private void resolucionAutomaticaSudoku() {
        if(!gestionModoResolucion() && this.modoResolucion.matches("Humana")) {
            int input = JOptionPane.showConfirmDialog(this, "Sudoku irresoluble mediante tecnicas Humanas.\n ¿Continuar mediante Fuerza Bruta?.");
            
            switch(input) {
                case 0: //En caso de que 'Si', cambiamos el modo de solucion a fuerza bruta y volvemos a intentar resolver.
                    this.modoResolucion = "Backtrack";
                    this.jMenuResolucionAuto.setText("Res. Fuerza Bruta");
                    resolucionAutomaticaSudoku();
                    break;
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

        jTabbedPanePrincipal = new javax.swing.JTabbedPane();
        jPanelMenuOpcionesJuego = new javax.swing.JPanel();
        jButtonJugar = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();
        jButtonPartidaNueva = new javax.swing.JButton();
        jButtonSolventarBacktrack = new javax.swing.JButton();
        jButtonCerrar = new javax.swing.JButton();
        jButtonSolventarHumana = new javax.swing.JButton();
        jTableTrampas = new javax.swing.JTable();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuOpciones = new javax.swing.JMenu();
        jMenuItemAcercaDe = new javax.swing.JMenuItem();
        jMenuItemSalir = new javax.swing.JMenuItem();
        jMenuJuegoNormal = new javax.swing.JMenu();
        jMenuItemComprobarSolucion = new javax.swing.JMenuItem();
        jMenuResolucionAuto = new javax.swing.JMenu();
        jMenuItemSolventarSudoku = new javax.swing.JMenuItem();
        jMenuItemBorrarTableroAutomatico = new javax.swing.JMenuItem();
        jMenuDebug = new javax.swing.JMenu();
        jMenuItemActivarTrampas = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemOcultarCasilla = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemCopiarTableroTrampas = new javax.swing.JMenuItem();
        jMenuItemCopiarTableroNormalAResolver = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPanePrincipal.setFont(new java.awt.Font("sansserif", 2, 12)); // NOI18N

        jButtonJugar.setText("Jugar Partida");
        jButtonJugar.setToolTipText("Inicio de una partida normal");
        jButtonJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJugarActionPerformed(evt);
            }
        });

        jLabelTitulo.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabelTitulo.setText("Sudoku");

        jButtonPartidaNueva.setText("Partida Nueva");
        jButtonPartidaNueva.setToolTipText("Reinicio de un juego nuevo");
        jButtonPartidaNueva.setEnabled(false);
        jButtonPartidaNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPartidaNuevaActionPerformed(evt);
            }
        });

        jButtonSolventarBacktrack.setText("Resolucion Fuerza Bruta");
        jButtonSolventarBacktrack.setToolTipText("Tablero para resolver un Sudoku de manera automatica");
        jButtonSolventarBacktrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSolventarBacktrackActionPerformed(evt);
            }
        });

        jButtonCerrar.setText("Salir");
        jButtonCerrar.setToolTipText("Cerrar el programa");
        jButtonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarActionPerformed(evt);
            }
        });

        jButtonSolventarHumana.setText("Resolución 'Humana'");
        jButtonSolventarHumana.setToolTipText("Técnica de Resolución por Técnicas 'Humanas'");
        jButtonSolventarHumana.setPreferredSize(new java.awt.Dimension(53, 23));
        jButtonSolventarHumana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSolventarHumanaActionPerformed(evt);
            }
        });

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
                        .addGroup(jPanelMenuOpcionesJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonPartidaNueva, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(jButtonJugar, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(jButtonSolventarBacktrack, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(jButtonCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(jButtonSolventarHumana, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanelMenuOpcionesJuegoLayout.setVerticalGroup(
            jPanelMenuOpcionesJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuOpcionesJuegoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonJugar)
                .addGap(18, 18, 18)
                .addComponent(jButtonPartidaNueva)
                .addGap(18, 18, 18)
                .addComponent(jButtonSolventarBacktrack)
                .addGap(18, 18, 18)
                .addComponent(jButtonSolventarHumana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonCerrar)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jTabbedPanePrincipal.addTab("Menu Principal", jPanelMenuOpcionesJuego);

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

        jMenuOpciones.setText("File");
        jMenuOpciones.setToolTipText("Menu del programa");

        jMenuItemAcercaDe.setText("Acerca de...");
        jMenuItemAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAcercaDeActionPerformed(evt);
            }
        });
        jMenuOpciones.add(jMenuItemAcercaDe);

        jMenuItemSalir.setText("Salir");
        jMenuItemSalir.setToolTipText("Cerrar el programa");
        jMenuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalirActionPerformed(evt);
            }
        });
        jMenuOpciones.add(jMenuItemSalir);

        jMenuBar.add(jMenuOpciones);

        jMenuJuegoNormal.setText("Juego Normal");
        jMenuJuegoNormal.setToolTipText("Conjunto de opciones para el juego normal");

        jMenuItemComprobarSolucion.setText("Comprobar Solucion");
        jMenuItemComprobarSolucion.setToolTipText("Comprobar si el Sudoku ha sido resuelto correctamente");
        jMenuItemComprobarSolucion.setEnabled(false);
        jMenuItemComprobarSolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemComprobarSolucionActionPerformed(evt);
            }
        });
        jMenuJuegoNormal.add(jMenuItemComprobarSolucion);

        jMenuBar.add(jMenuJuegoNormal);

        jMenuResolucionAuto.setText("Resolucion");
        jMenuResolucionAuto.setToolTipText("Conjunto de opciones para la resollucion automatica");

        jMenuItemSolventarSudoku.setText("Resolucionar");
        jMenuItemSolventarSudoku.setToolTipText("Solucionar el sudoku introducido a mano");
        jMenuItemSolventarSudoku.setEnabled(false);
        jMenuItemSolventarSudoku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSolventarSudokuActionPerformed(evt);
            }
        });
        jMenuResolucionAuto.add(jMenuItemSolventarSudoku);

        jMenuItemBorrarTableroAutomatico.setText("Borrar Tablero");
        jMenuItemBorrarTableroAutomatico.setToolTipText("Vaciar todas las celdas del tablero");
        jMenuItemBorrarTableroAutomatico.setEnabled(false);
        jMenuItemBorrarTableroAutomatico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBorrarTableroAutomaticoActionPerformed(evt);
            }
        });
        jMenuResolucionAuto.add(jMenuItemBorrarTableroAutomatico);

        jMenuBar.add(jMenuResolucionAuto);

        jMenuDebug.setText("Debug");
        jMenuDebug.setToolTipText("Metodos creados para ayduarme durante el desarrollo");

        jMenuItemActivarTrampas.setText("Activar Tablero Trampas");
        jMenuItemActivarTrampas.setToolTipText("Hacer visible el mismo tablero de juego pero solucionado");
        jMenuItemActivarTrampas.setEnabled(false);
        jMenuItemActivarTrampas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActivarTrampasActionPerformed(evt);
            }
        });
        jMenuDebug.add(jMenuItemActivarTrampas);
        jMenuDebug.add(jSeparator2);

        jMenuItemOcultarCasilla.setText("Ocultar Casilla Especifica");
        jMenuItemOcultarCasilla.setToolTipText("Hacer una casilla no visible en el tablero principal");
        jMenuItemOcultarCasilla.setEnabled(false);
        jMenuItemOcultarCasilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOcultarCasillaActionPerformed(evt);
            }
        });
        jMenuDebug.add(jMenuItemOcultarCasilla);
        jMenuDebug.add(jSeparator1);

        jMenuItemCopiarTableroTrampas.setText("Copiar Tablero Trampas a Normal");
        jMenuItemCopiarTableroTrampas.setToolTipText("Copiar el contenido del tablero de trampas al normal de juego");
        jMenuItemCopiarTableroTrampas.setEnabled(false);
        jMenuItemCopiarTableroTrampas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCopiarTableroTrampasActionPerformed(evt);
            }
        });
        jMenuDebug.add(jMenuItemCopiarTableroTrampas);

        jMenuItemCopiarTableroNormalAResolver.setText("Copiar Tablero Normal a Resolucion");
        jMenuItemCopiarTableroNormalAResolver.setToolTipText("Copiar el contenido actual del tablero normal a resolver");
        jMenuItemCopiarTableroNormalAResolver.setEnabled(false);
        jMenuItemCopiarTableroNormalAResolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCopiarTableroNormalAResolverActionPerformed(evt);
            }
        });
        jMenuDebug.add(jMenuItemCopiarTableroNormalAResolver);

        jMenuBar.add(jMenuDebug);

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
    
    private void jButtonJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJugarActionPerformed
        iniJuego();
    }//GEN-LAST:event_jButtonJugarActionPerformed

    private void jMenuItemActivarTrampasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActivarTrampasActionPerformed
        this.jTabbedPanePrincipal.setEnabledAt(2, true);
    }//GEN-LAST:event_jMenuItemActivarTrampasActionPerformed

    private void jButtonPartidaNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPartidaNuevaActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(this, "Se perdera el juego actual y se comenzara uno nuevo. ¿Seguro?", "Confirmacion", JOptionPane.YES_NO_OPTION);
        if(confirmacion == 0) {
            Singleton.generacionTableroNuevo();
            iniTablaPrincipalJuego();
            iniJuego();
        }
    }//GEN-LAST:event_jButtonPartidaNuevaActionPerformed

    private void jMenuItemSolventarSudokuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSolventarSudokuActionPerformed
        resolucionAutomaticaSudoku();
    }//GEN-LAST:event_jMenuItemSolventarSudokuActionPerformed
  
    private void jMenuItemOcultarCasillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOcultarCasillaActionPerformed
        new Thread(() -> ocultarCasillaEspecifica()).start(); //Para que no se quede la GUI colgada esperando.
    }//GEN-LAST:event_jMenuItemOcultarCasillaActionPerformed
    
    private void jMenuItemComprobarSolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemComprobarSolucionActionPerformed
        comprobarSolucionJuegoPrincipal();
    }//GEN-LAST:event_jMenuItemComprobarSolucionActionPerformed

    private void jMenuItemCopiarTableroTrampasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCopiarTableroTrampasActionPerformed
        Singleton.getFacade().copiarTableros(jTableJuegoCustom, jTableTrampas);
    }//GEN-LAST:event_jMenuItemCopiarTableroTrampasActionPerformed

    private void jButtonSolventarBacktrackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSolventarBacktrackActionPerformed
        preparacionTablaResolver();
        gestionEnabledsFuerzaBruta();
    }//GEN-LAST:event_jButtonSolventarBacktrackActionPerformed

    private void jMenuItemCopiarTableroNormalAResolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCopiarTableroNormalAResolverActionPerformed
        Singleton.getFacade().copiarTableros(jTableResolver, jTableJuegoCustom);
    }//GEN-LAST:event_jMenuItemCopiarTableroNormalAResolverActionPerformed

    private void jButtonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButtonCerrarActionPerformed

    private void jMenuItemBorrarTableroAutomaticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBorrarTableroAutomaticoActionPerformed
        Singleton.getFacade().borrarTablero(jTableResolver);
    }//GEN-LAST:event_jMenuItemBorrarTableroAutomaticoActionPerformed

    private void jMenuItemAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAcercaDeActionPerformed
        new AcercaDe();
    }//GEN-LAST:event_jMenuItemAcercaDeActionPerformed

    private void jButtonSolventarHumanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSolventarHumanaActionPerformed
        preparacionTablaResolver();
        gestionEnabledsSolucionHumana();
    }//GEN-LAST:event_jButtonSolventarHumanaActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCerrar;
    private javax.swing.JButton jButtonJugar;
    private javax.swing.JButton jButtonPartidaNueva;
    private javax.swing.JButton jButtonSolventarBacktrack;
    private javax.swing.JButton jButtonSolventarHumana;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuDebug;
    private javax.swing.JMenuItem jMenuItemAcercaDe;
    private javax.swing.JMenuItem jMenuItemActivarTrampas;
    private javax.swing.JMenuItem jMenuItemBorrarTableroAutomatico;
    private javax.swing.JMenuItem jMenuItemComprobarSolucion;
    private javax.swing.JMenuItem jMenuItemCopiarTableroNormalAResolver;
    private javax.swing.JMenuItem jMenuItemCopiarTableroTrampas;
    private javax.swing.JMenuItem jMenuItemOcultarCasilla;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JMenuItem jMenuItemSolventarSudoku;
    private javax.swing.JMenu jMenuJuegoNormal;
    private javax.swing.JMenu jMenuOpciones;
    private javax.swing.JMenu jMenuResolucionAuto;
    private javax.swing.JPanel jPanelMenuOpcionesJuego;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPanePrincipal;
    private javax.swing.JTable jTableTrampas;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the ICONO_URL
     */
    public URL getIconoUrl() {
        return ICONO_URL;
    }
    
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
