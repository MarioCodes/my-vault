/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Clase Mapeador del Servidor. Esta informacion se enviara  por red al Cliente.
 * @author Mario Codes Sánchez
 * @since 04/02/2017
 */
public class Mapeador {
    DefaultMutableTreeNode root;
    DefaultTreeModel treeModel;
    JTree tree;
    
    public JTree mapear() {
//        JFrame frame = new JFrame("File Browser: ");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        File fileRoot = new File("root/");
        root = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModel = new DefaultTreeModel(root);
        
        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);
        
//        frame.add(scrollPane);
//        frame.setLocationByPlatform(true);
//        frame.setSize(640, 480);
//        frame.setVisible(true);
        
        CreateChildNodes createChildNodes = new CreateChildNodes(fileRoot, root);
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
