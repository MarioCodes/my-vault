/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.commons.net.ftp.FTPFile;

/**
 * Clase encargada de hacer el mapeo de ficheros / directorios del proyecto y transformarlo al JTree.
 * @author Mario Codes Sánchez
 * @since 23/01/2017
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
     * Como sabemos donde va a estar el server, lo hacemos mediante rutas relativas, no me gusta pero no me da tiempo a hacerlo como queria.
     * @param files FTPFile[] con toda la informacion.
     * @return JTree mapeado.
     */
    public JTree mapearServer(FTPFile[] files) {
        File fileRoot = new File("root/"); //Directorio 'root' en el cual meto el resto.
        
        for(FTPFile f: files) {
            
        }
        
        root = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModel = new DefaultTreeModel(root);
        
        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);
        new CreateChildNodes(fileRoot, root).createChildrenStart();
        
        return tree;
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
