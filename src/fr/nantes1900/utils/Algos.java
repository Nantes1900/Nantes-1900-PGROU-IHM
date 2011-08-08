package fr.nantes1900.utils;

import fr.nantes1900.models.Mesh;
import fr.nantes1900.models.basis.Edge.MoreThanTwoTrianglesPerEdgeException;
import fr.nantes1900.models.basis.Triangle;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implements a class containing some little algorithms used in the other
 * classes.
 * 
 * @author Daniel Lefevre
 */
public final class Algos {

    /** Private constructor. */
    private Algos() {
    }

    /**
     * Divide the mesh in block of neighbours. This method use returnNeighbours
     * to find the neighbours of one triangle, and put it into a new mesh into
     * the arraylist. Thus, it takes another triangle and redo the same
     * operation until there is no more triangle. This method does not destroy
     * the mesh in parameter.
     * 
     * @param m
     *            the mesh to divide
     * @return an array of the blocks-meshs
     * @throws MoreThanTwoTrianglesPerEdgeException
     *             if the edge is bad formed
     */
    public static List<Mesh> blockExtract(final Mesh m)
        throws MoreThanTwoTrianglesPerEdgeException {
        final Set<Mesh> thingsList = new HashSet<Mesh>();
        final Mesh mesh = new Mesh(m);

        while (!mesh.isEmpty()) {

            final Mesh e = new Mesh();
            mesh.getOne().returnNeighbours(e, mesh);
            mesh.remove(e);
            thingsList.add(e);

        }

        return new ArrayList<Mesh>(thingsList);
    }

    /**
     * Divide the mesh in block of neighbours depending on their orientations.
     * This method takes one triangle and use returnNeighbours to find the
     * triangles which are oriented as the first one (with an error) and find
     * into them its neighbours, and put it in a new mesh into the arraylist.
     * Thus, it takes another triangle and redo the same operation until there
     * is no more triangle. This method does not destroy the mesh in parameter.
     * 
     * @param m
     *            the mesh to divide
     * @param angleNormalErrorFactor
     *            the error on the orientation
     * @return an array of the blocks-meshs
     * @throws MoreThanTwoTrianglesPerEdgeException
     *             uf the edge is bad formed
     */
    public static List<Mesh> blockOrientedExtract(final Mesh m,
        final double angleNormalErrorFactor)
        throws MoreThanTwoTrianglesPerEdgeException {
        final List<Mesh> thingsList = new ArrayList<Mesh>();
        final Mesh mesh = new Mesh(m);

        while (!mesh.isEmpty()) {

            final Mesh e = new Mesh();
            final Triangle tri = mesh.getOne();
            final Mesh oriented =
                mesh.orientedAs(tri.getNormal(), angleNormalErrorFactor);
            tri.returnNeighbours(e, oriented);
            mesh.remove(e);
            thingsList.add(e);

        }

        return thingsList;
    }

    /**
     * Treat a list of mesh to add the noise which is part of the mesh. This
     * method try to find a block of noise which complete the mesh (of the list)
     * and which have the same orientation. It thus adds it to the mesh.
     * 
     * @param list
     *            the list of mesh to complete with noise
     * @param noise
     *            the whole noise
     * @param largeAngleNormalErrorFactor
     *            the error on the orientation
     * @throws MoreThanTwoTrianglesPerEdgeException
     *             if the edge is bad formed
     */
    public static void blockTreatOrientedNoise(final List<Mesh> list,
        final Mesh noise, final double largeAngleNormalErrorFactor)
        throws MoreThanTwoTrianglesPerEdgeException {

        final List<Mesh> m = new ArrayList<Mesh>();

        for (Mesh e : list) {
            final Mesh meshAndNoise = new Mesh(e);
            final Mesh noiseOriented =
                noise
                    .orientedAs(e.averageNormal(), largeAngleNormalErrorFactor);
            meshAndNoise.addAll(noiseOriented);
            final Mesh mes = new Mesh();
            e.getOne().returnNeighbours(mes, meshAndNoise);
            m.add(mes);
            noise.remove(mes);
        }

        list.clear();
        list.addAll(m);
    }

    /**
     * Extract the floor in a mesh. Receiving a mesh of triangles oriented as
     * the floor, tt searches the lowest altitude as the lowest z, and take a
     * stripe of triangles that are contained in the lowest altitude and an
     * error. In this stripe, it takes one triangle, and returns all its
     * neighbours. FIXME : find the altitude factor automatically.
     * 
     * @param meshOriented
     *            the floor-oriented triangles that will be treated
     * @param altitudeErrorFactor
     *            the error on the altitude
     * @return the mesh containing the floor extracted
     * @throws MoreThanTwoTrianglesPerEdgeException
     *             if the edge is bad formed
     */
    public static Mesh floorExtract(final Mesh meshOriented,
        final double altitudeErrorFactor)
        throws MoreThanTwoTrianglesPerEdgeException {
        final Mesh floors = new Mesh();

        try {
            Triangle lowestTriangle = meshOriented.zMinFace();
            final double lowestZ = lowestTriangle.zMin();

            final Mesh stripe =
                meshOriented.zBetween(lowestZ, lowestZ + altitudeErrorFactor);

            final Mesh temp = new Mesh();

            while (!stripe.isEmpty()) {

                lowestTriangle = stripe.getOne();
                temp.clear();
                lowestTriangle.returnNeighbours(temp, meshOriented);
                floors.addAll(temp);
                meshOriented.remove(temp);
                stripe.remove(temp);
            }

            return floors;
        } catch (InvalidParameterException e) {
            return null;
        }
    }

    /**
     * Implements an exception when the floor is empty.
     * 
     * @author Daniel Lefevre
     */
    public static final class NoFloorException extends Exception {

        /** Version attribute. */
        private static final long serialVersionUID = 1L;

        /**
         * Private constructor.
         */
        private NoFloorException() {
        }
    }
}
