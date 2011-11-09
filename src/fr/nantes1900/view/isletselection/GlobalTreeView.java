/**
 * 
 */
package fr.nantes1900.view.isletselection;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Camille
 */
public class GlobalTreeView extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private JTree tree;
    private JScrollPane spTree;

    /**
     * Empty constructor.
     */
    public GlobalTreeView()
    {
        this.setLayout(new BorderLayout());
        spTree = new JScrollPane();
        this.add(spTree, BorderLayout.CENTER);
    }

    public void displayDirectory(File newDirectory)
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(
                newDirectory.getName());
        File[] childrenFiles = newDirectory.listFiles();
        for (File file : childrenFiles)
        {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);
            root.add(child);
        }
        
        this.tree = new JTree(root);
        spTree.setViewportView(tree);
        
    }

    public JTree getTree()
    {
        return this.tree;
    }
}