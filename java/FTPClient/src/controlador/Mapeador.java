/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Clase encargada de hacer el mapeo de ficheros / directorios del proyecto y transformarlo al JTree.
 * Modificada para la version FTP.
 * @author Mario Codes Sánchez
 * @since 13/02/2017
 */
public class Mapeador {
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree tree;
    
    /**
     * Metodo Base para el funcionamiento. Llamar a este.
     *  Devuelve un JTree, pero en la ventana habra que hacer un .set() del TreeNode y settearlo al de la ventana.
     * @return JTree mapeado. 
     */
    public JTree mapear() {
        File fileRoot = new File("root/"); //Directorio 'root' a partir del cual se mapeara.
        root = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModel = new DefaultTreeModel(root);
        
        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);
        new CreateChildNodes(fileRoot, root).createChildrenStart();
        
        return tree;
    }
    
    /**
     * Quita datos extra como los permisos, ultima vez usado etc.
     * @param string String (nombre del fichero) a limpiar.
     * @return String con datos extra eliminados.
     */
    private String limpiezaNodo(String string) {
        boolean directory = (string.charAt(0) == 'd');
        string = string.substring(string.indexOf(':')+3).trim();
        if(directory) string += " (Directorio)";
        return string;
    }
    
    /**
     * Construccion de un JTree con la informacion relativa a la estructura del arbol de directorios del Server.
     * @param is InputStream de donde he obtenido la informacion.
     * @return JTree construido para obtener su model e igualarlo.
     */
    public JTree mapearServer(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linea = null;
            int contador = 0;

            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("FTProot");
            DefaultTreeModel modelServer = new DefaultTreeModel(raiz);

            while((linea = br.readLine()) != null) {
                linea = limpiezaNodo(linea);
                DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(new File(linea));
                modelServer.insertNodeInto(nodo, raiz, contador);
            }
            
            return new JTree(modelServer);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Crea los hijos.
     */
    private class CreateChildNodes {
        DefaultMutableTreeNode root;
        File fileRoot;
        
        public CreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
            this.root = root;
            this.fileRoot = fileRoot;
        }
        
        private void createChildren(File fileRoot, DefaultMutableTreeNode node) {
            File[] files = fileRoot.listFiles();
            if(files == null) return;
            
            for(File file: files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
                node.add(childNode);
                if(file.isDirectory()) createChildren(file, childNode);
            }
        }
        
        /**
         * Metodo a invocar para usar.
         */
        private void createChildrenStart() {
            createChildren(fileRoot, root);
        }
    }
    
    
    /**
     * Devuelve el nombre del fichero.
     */
    private class FileNode {
        File file;
        
        public FileNode(File file) {
            this.file = file;
        }
        
        @Override
        public String toString() {
            String name = file.getName();
            if(name.equals("")) return file.getAbsolutePath();
            else return name;
        }
    }
}
