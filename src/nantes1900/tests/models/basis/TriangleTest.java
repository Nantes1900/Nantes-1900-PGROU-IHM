package nantes1900.tests.models.basis;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.vecmath.Vector3d;

import junit.framework.TestCase;

import nantes1900.models.Mesh;
import nantes1900.models.basis.Edge;
import nantes1900.models.basis.Edge.MoreThanTwoTrianglesPerEdgeException;
import nantes1900.models.basis.Point;
import nantes1900.models.basis.Triangle;

import org.junit.Test;

/**
 * A set of tests for the class Triangle.
 * 
 * @author Daniel Lefevre
 */
public class TriangleTest extends TestCase {

	private Point p1 = new Point(1, 0, -1);
	private Point p2 = new Point(0, 1, 0);
	private Point p3 = new Point(-1, 2, 1);
	private Vector3d vect = new Vector3d(0, 0, 1);
	private Edge e1 = new Edge(p1, p2);
	private Edge e2 = new Edge(p2, p3);
	private Edge e3 = new Edge(p3, p1);
	Triangle t;

	public TriangleTest() throws MoreThanTwoTrianglesPerEdgeException {
		t = new Triangle(p1, p2, p3, e1, e2, e3, vect);
	}

	/**
	 * Test method for
	 * {@link nantes1900.models.basis.Triangle#angularTolerance(javax.vecmath.Vector3d, double)}
	 * . Test method for
	 * {@link nantes1900.models.basis.Triangle#angularTolerance(nantes1900.models.basis.Triangle, double)}
	 * .
	 * 
	 * @throws MoreThanTwoTrianglesPerEdgeException
	 */
	@Test
	public void testAngularTolerance()
			throws MoreThanTwoTrianglesPerEdgeException {
		Vector3d vector = new Vector3d(0, 1, 0);
		Triangle tBis = new Triangle(p1, p2, p3, e1, e2, e3, vector);

		assertFalse(t.angularTolerance(vector, 60));
		assertFalse(t.angularTolerance(tBis, 60));

		assertTrue(t.angularTolerance(vector, 100));
		assertTrue(t.angularTolerance(tBis, 100));
	}

	/**
	 * Test method for
	 * {@link nantes1900.models.basis.Triangle#contains(nantes1900.models.basis.Point)}
	 * .
	 */
	@Test
	public void testContainsPoint() {
		assertTrue(t.contains(p1));
		assertTrue(t.contains(p2));
		assertTrue(t.contains(p3));
	}

	/**
	 * Test method for
	 * {@link nantes1900.models.basis.Triangle#contains(nantes1900.models.basis.Edge)}
	 * .
	 */
	@Test
	public void testContainsEdge() {
		assertTrue(t.contains(e1));
		assertTrue(t.contains(e2));
		assertTrue(t.contains(e3));
	}

	/**
	 * Test method for
	 * {@link nantes1900.models.basis.Triangle#equals(java.lang.Object)}.
	 * 
	 * @throws MoreThanTwoTrianglesPerEdgeException
	 */
	@Test
	public void testEqualsObject() throws MoreThanTwoTrianglesPerEdgeException {
		Triangle tBis = new Triangle(p1, p2, p3, e1, e2, e3, vect);
		assertTrue(t.equals(tBis));
		assertTrue(tBis.equals(t));
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#getNeighbours()}.
	 */
	@Test
	public void testGetNeighbours() {
		Point p4 = new Point(3, 4, 5);
		Point p5 = new Point(-3, -4, -5);
		Point p6 = new Point(-3.5, -1.2, 5.9);
		Edge e4 = new Edge(p1, p4);
		Edge e5 = new Edge(p2, p4);
		Edge e6 = new Edge(p1, p5);
		Edge e7 = new Edge(p2, p5);
		Edge e8 = new Edge(p1, p6);
		Edge e9 = new Edge(p2, p6);
		try {

			Triangle t2 = new Triangle(p1, p2, p4, e1, e4, e5, vect);
			Triangle t3 = new Triangle(p1, p3, p5, e3, e6, e7, vect);
			Triangle t4 = new Triangle(p2, p3, p6, e2, e8, e9, vect);

			ArrayList<Triangle> l = t.getNeighbours();

			assertFalse(l.contains(t));
			assertTrue(l.contains(t2));
			assertTrue(l.contains(t3));
			assertTrue(l.contains(t4));
		} catch (MoreThanTwoTrianglesPerEdgeException e) {
			fail();
		}
	}

	/**
	 * Test method for
	 * {@link nantes1900.models.basis.Triangle#getNumNeighbours()}.
	 */
	@Test
	public void testGetNumNeighbours() {
		Point p4 = new Point(3, 4, 5);
		Point p5 = new Point(-3, -4, -5);
		Point p6 = new Point(-3.5, -1.2, 5.9);
		Edge e4 = new Edge(p1, p4);
		Edge e5 = new Edge(p2, p4);
		Edge e6 = new Edge(p1, p5);
		Edge e7 = new Edge(p2, p5);
		Edge e8 = new Edge(p1, p6);
		Edge e9 = new Edge(p2, p6);

		try {
			new Triangle(p1, p2, p4, e1, e4, e5, vect);
			new Triangle(p1, p3, p5, e3, e6, e7, vect);
			new Triangle(p2, p3, p6, e2, e8, e9, vect);

			assertTrue(t.getNumNeighbours() == 3);
		} catch (MoreThanTwoTrianglesPerEdgeException e) {
			fail();
		}
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#getPoints()}.
	 */
	@Test
	public void testGetPoints() {
		ArrayList<Point> pointList = new ArrayList<Point>(t.getPoints());
		assertTrue(pointList.get(0) == p1);
		assertTrue(pointList.get(1) == p2);
		assertTrue(pointList.get(2) == p3);
	}

	/**
	 * Test method for
	 * {@link nantes1900.models.basis.Triangle#isNeighboor(nantes1900.models.basis.Triangle)}
	 * .
	 * 
	 * @throws MoreThanTwoTrianglesPerEdgeException
	 */
	public void testIsNeighboor() throws MoreThanTwoTrianglesPerEdgeException {

		Point p4 = new Point(3, 4, 5);
		Point p5 = new Point(-3, -4, -5);
		Point p6 = new Point(-3.5, -1.2, 5.9);
		Edge e4 = new Edge(p1, p4);
		Edge e5 = new Edge(p2, p4);
		Edge e6 = new Edge(p1, p5);
		Edge e7 = new Edge(p2, p5);
		Edge e8 = new Edge(p1, p6);
		Edge e9 = new Edge(p2, p6);
		Triangle t2 = new Triangle(p1, p2, p4, e1, e4, e5, vect);
		Triangle t3 = new Triangle(p1, p3, p5, e3, e6, e7, vect);
		Triangle t4 = new Triangle(p2, p3, p6, e2, e8, e9, vect);

		assertFalse(t.isNeighboor(t));
		assertTrue(t.isNeighboor(t2));
		assertTrue(t.isNeighboor(t3));
		assertTrue(t.isNeighboor(t4));
	}

	/**
	 * Test method for
	 * {@link nantes1900.models.basis.Triangle#isNormalTo(javax.vecmath.Vector3d, double)}
	 * .
	 */
	@Test
	public void testIsNormalTo() {
		Vector3d vector = new Vector3d(0, 1, 0);
		Vector3d vector2 = new Vector3d(0, 0, 1);

		assertTrue(t.isNormalTo(vector, 1));
		assertFalse(t.isNormalTo(vector2, 0.2));
	}

	/**
	 * Test method for
	 * {@link nantes1900.models.basis.Triangle#returnNeighbours(nantes1900.models.Mesh, nantes1900.models.Mesh)}
	 * .
	 * 
	 * @throws MoreThanTwoTrianglesPerEdgeException
	 */
	@Test
	public void testReturnNeighbours()
			throws MoreThanTwoTrianglesPerEdgeException {
		Point p4 = new Point(3, 4, 5);
		Point p5 = new Point(-3, -4, -5);
		Point p6 = new Point(-3.5, -1.2, 5.9);
		Edge e4 = new Edge(p1, p4);
		Edge e5 = new Edge(p2, p4);
		Edge e6 = new Edge(p1, p5);
		Edge e7 = new Edge(p2, p5);
		Edge e8 = new Edge(p1, p6);
		Edge e9 = new Edge(p2, p6);
		Edge e10 = new Edge(p5, p6);
		Triangle t2 = new Triangle(p1, p2, p4, e1, e4, e5, vect);
		Triangle t3 = new Triangle(p1, p3, p5, e3, e6, e7, vect);
		Triangle t4 = new Triangle(p2, p3, p6, e2, e8, e9, vect);
		Triangle t5 = new Triangle(p1, p5, p6, e6, e8, e10, vect);

		Mesh contain = new Mesh();
		contain.add(t);
		contain.add(t2);
		contain.add(t3);
		contain.add(t4);
		contain.add(t5);

		Triangle t6 = new Triangle(new Point(0.05, 0.05, 0.05), new Point(0.05,
				0.05, 0.05), new Point(0.05, 0.05, 0.05), new Edge(new Point(
				0.05, 0.05, 0.05), new Point(0.05, 0.05, 0.05)), new Edge(
				new Point(0.05, 0.05, 0.05), new Point(0.05, 0.05, 0.05)),
				new Edge(new Point(0.05, 0.05, 0.05), new Point(0.05, 0.05,
						0.05)), vect);

		Mesh ret = new Mesh();
		t.returnNeighbours(ret, contain);

		assertTrue(ret.contains(t));
		assertTrue(ret.contains(t2));
		assertTrue(ret.contains(t3));
		assertTrue(ret.contains(t4));
		assertTrue(ret.contains(t5));
		assertFalse(ret.contains(t6));
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#xAverage()}.
	 */
	@Test
	public void testXAverage() {
		assertTrue(t.xAverage() == 0);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#xMax()}.
	 */
	@Test
	public void testXMax() {
		assertTrue(t.xMax() == 1);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#xMaxPoint()}.
	 */
	@Test
	public void testXMaxPoint() {
		assertTrue(t.xMaxPoint() == p1);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#xMin()}.
	 */
	@Test
	public void testXMin() {
		assertTrue(t.xMin() == -1);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#xMinPoint()}.
	 */
	@Test
	public void testXMinPoint() {
		assertTrue(t.xMinPoint() == p3);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#yAverage()}.
	 */
	@Test
	public void testYAverage() {
		assertTrue(t.yAverage() == 1);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#yMax()}.
	 */
	@Test
	public void testYMax() {
		assertTrue(t.yMax() == 2);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#yMaxPoint()}.
	 */
	@Test
	public void testYMaxPoint() {
		assertTrue(t.yMaxPoint() == p3);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#yMin()}.
	 */
	@Test
	public void testYMin() {
		assertTrue(t.yMin() == 0);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#yMinPoint()}.
	 */
	@Test
	public void testYMinPoint() {
		assertTrue(t.yMinPoint() == p1);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#zAverage()}.
	 */
	@Test
	public void testZAverage() {
		assertTrue(t.zAverage() == 0);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#zMax()}.
	 */
	@Test
	public void testZMax() {
		assertTrue(t.zMax() == 1);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#zMaxPoint()}.
	 */
	@Test
	public void testZMaxPoint() {
		assertTrue(t.zMaxPoint() == p3);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#zMin()}.
	 */
	@Test
	public void testZMin() {
		assertTrue(t.zMin() == -1);
	}

	/**
	 * Test method for {@link nantes1900.models.basis.Triangle#zMinPoint()}.
	 */
	@Test
	public void testZMinPoint() {
		assertTrue(t.zMinPoint() == p1);
	}
}