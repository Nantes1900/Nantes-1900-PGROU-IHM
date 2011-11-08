package fr.nantes1900.view.display3d;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.swing.JPanel;

import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * @author Daniel
 */
// TODO by Nicolas and Siju
public class Universe3DView extends JPanel
{
    private static final long serialVersionUID = 1L;

    private List<MeshView>    meshesList       = new ArrayList<>();
    private List<PolygonView> polygonsList     = new ArrayList<>();

    /**
     * The universe.
     */
    private SimpleUniverse    simpleUniverse;

    /**
     * Creates a new universe.
     */
    public Universe3DView()
    {
        this.setLayout(new BorderLayout());

        Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        this.add(c, BorderLayout.CENTER);

        // Setups the SimpleUniverse, attachss the Canvas3D
        this.simpleUniverse = new SimpleUniverse(c);

        this.simpleUniverse.addBranchGraph(this.createSceneGraph());

        // Size to show the panel while there is nothing to show
        this.setMinimumSize(new Dimension(400, 400));
        this.setPreferredSize(new Dimension(400, 400));
    }

    public List<MeshView> getMeshesList()
    {
        return this.meshesList;
    }

    public List<PolygonView> getPolygonsList()
    {
        return this.polygonsList;
    }

    @SuppressWarnings("static-method")
    private BranchGroup createSceneGraph()
    {
        BranchGroup objRoot = new BranchGroup();

        return objRoot;
    }

    /**
     * Removes everything displayed !
     */
    public void clearAllMeshes()
    {
        // TODO Auto-generated method stub

    }

    /**
     * Adds a mesh to the things displayed...
     * @param meshViewer
     */
    public void addMesh(MeshView meshViewer)
    {
        // TODO Auto-generated method stub
    }

    /**
     * Adds a mesh to the things displayed...
     * @param polygonView
     */
    public void addPolygon(PolygonView polygonView)
    {
        // TODO Auto-generated method stub
    }
}
