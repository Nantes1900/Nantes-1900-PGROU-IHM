package fr.nantes1900.control.display3d;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BranchGroup;

import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickTool;

import fr.nantes1900.control.isletselection.IsletSelectionController;
import fr.nantes1900.models.basis.Triangle;
import fr.nantes1900.view.display3d.MeshView;
import fr.nantes1900.view.display3d.PolygonView;
import fr.nantes1900.view.display3d.TriangleView;
import fr.nantes1900.view.display3d.Universe3DView;

public class Universe3DController implements MouseListener, MouseMotionListener {
	/**
	 * The view of the 3D objets to show.
	 */
	private Universe3DView u3DView;
	private PickCanvas pickCanvas;
	private NewMouseRotate mouseRotate;

	private IsletSelectionController parentController;

	private ArrayList<MeshView> meshesViewSelected;
	private ArrayList<TriangleView> trianglesViewSelected;
	private ArrayList<PolygonView> polygonsViewSelected;

	public Universe3DController(
			IsletSelectionController isletSelectionController) {
		this.parentController = isletSelectionController;
		this.u3DView = new Universe3DView();
	}

	public void setPickCanvas(BranchGroup branchGroup) {
		this.pickCanvas = new PickCanvas(this.u3DView.getSimpleUniverse()
				.getCanvas(), branchGroup);
		pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
		this.u3DView.getSimpleUniverse().getCanvas().addMouseListener(this);
		this.u3DView.getSimpleUniverse().getCanvas().addMouseMotionListener(this);
		
	}

	/**
	 * Returns the list of Triangle associated with the trianglesView contained
	 * in trianglesViewSelected.
	 * 
	 * @return
	 */
	public List<Triangle> getTrianglesSelected() {
		List<Triangle> trianglesList = new ArrayList<>();
		// FIXME by Nicolas and Siju
		return trianglesList;
	}

	public Universe3DView getUniverse3DView() {
		return this.u3DView;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse Pressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}