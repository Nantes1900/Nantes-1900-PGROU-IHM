package fr.nantes1900.models.islets.buildings;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Vector3d;

import fr.nantes1900.models.basis.Mesh;
import fr.nantes1900.models.islets.AbstractIslet;
import fr.nantes1900.models.islets.buildings.steps.BuildingsIsletStep1;
import fr.nantes1900.models.islets.buildings.steps.BuildingsIsletStep2;
import fr.nantes1900.models.islets.buildings.steps.BuildingsIsletStep3;
import fr.nantes1900.models.islets.buildings.steps.BuildingsIsletStep4;
import fr.nantes1900.models.islets.buildings.steps.BuildingsIsletStep5;
import fr.nantes1900.models.islets.buildings.steps.BuildingsIsletStep6;
import fr.nantes1900.models.islets.buildings.steps.BuildingsIsletStep7;
import fr.nantes1900.models.islets.buildings.steps.BuildingsIsletStep8;

/**
 * Abstracts a building islet : residential or industrial. This class contains
 * all the methods to apply the treatments on the meshes.
 * @author Daniel Lef�vre
 */
public abstract class AbstractBuildingsIslet extends AbstractIslet
{

    private BuildingsIsletStep1 biStep1;
    private BuildingsIsletStep2 biStep2;
    private BuildingsIsletStep3 biStep3;
    private BuildingsIsletStep4 biStep4;
    private BuildingsIsletStep5 biStep5;
    private BuildingsIsletStep6 biStep6;
    private BuildingsIsletStep7 biStep7;
    private BuildingsIsletStep8 biStep8;

    /**
     * The number of the current step.
     */
    private int                 progression = 0;

    /**
     * The normal to the ground. Used to extract the grounds.
     */
    private Vector3d            groundNormal;

    /**
     * Constructor. Stocks the mesh in the initialTotalMesh variable.
     * @param initialMesh
     *            the mesh representing the islet
     */
    public AbstractBuildingsIslet(final Mesh initialMesh)
    {
        super(initialMesh);
    }

    /**
     * Getter.
     * @return the normal to the ground
     */
    public final Vector3d getGroundNormal()
    {
        return this.groundNormal;
    }

    /**
     * Getter.
     * @return the progression of the treatment
     */
    public final int getProgression()
    {
        return this.progression;
    }

    /**
     * Incrementer. Used to increment the progression of the treamtent.
     */
    public final void incProgression()
    {
        this.progression++;
    }

    /**
     * Return a node containing the tree depending of the progression of the
     * treatment.
     * @return the node
     */
    public final DefaultMutableTreeNode returnNode()
    {
        switch (this.getProgression())
        {
            case 0:
                // TODO : error
                return null;
            case 1:
                return this.biStep1.returnNode();
            case 2:
                return this.biStep2.returnNode();
            case 3:
                return this.biStep3.returnNode();
            case 4:
                return this.biStep4.returnNode();
            case 5:
                return this.biStep5.returnNode();
            case 6:
                return this.biStep6.returnNode();
            case 7:
                return this.biStep7.returnNode();
            case 8:
                return this.biStep8.returnNode();
            default:
                return null;
        }
    }

    /**
     * Setter.
     * @param groundNormalIn
     *            the normal to set as ground normal
     */
    public final void setGroundNormal(final Vector3d groundNormalIn)
    {
        this.groundNormal = groundNormalIn;
    }

    /**
     * Exception class used when an attribute has not been defined whereas the
     * algorithm has been launched.
     * @author Daniel Lefevre
     */
    public final class VoidParameterException extends Exception
    {

        /**
         * Version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Private constructor.
         */
        public VoidParameterException()
        {
        }
    }

    public BuildingsIsletStep1 getBiStep1()
    {
        return this.biStep1;
    }

    public void setBiStep1(BuildingsIsletStep1 biStep1In)
    {
        this.biStep1 = biStep1In;
    }

    public BuildingsIsletStep2 getBiStep2()
    {
        return this.biStep2;
    }

    public void setBiStep2(BuildingsIsletStep2 biStep2In)
    {
        this.biStep2 = biStep2In;
    }

    public BuildingsIsletStep3 getBiStep3()
    {
        return this.biStep3;
    }

    public void setBiStep3(BuildingsIsletStep3 biStep3)
    {
        this.biStep3 = biStep3;
    }

    public BuildingsIsletStep4 getBiStep4()
    {
        return this.biStep4;
    }

    public void setBiStep4(BuildingsIsletStep4 biStep4)
    {
        this.biStep4 = biStep4;
    }

    public BuildingsIsletStep5 getBiStep5()
    {
        return this.biStep5;
    }

    public void setBiStep5(BuildingsIsletStep5 biStep5)
    {
        this.biStep5 = biStep5;
    }

    public BuildingsIsletStep6 getBiStep6()
    {
        return this.biStep6;
    }

    public void setBiStep6(BuildingsIsletStep6 biStep6)
    {
        this.biStep6 = biStep6;
    }

    public BuildingsIsletStep7 getBiStep7()
    {
        return this.biStep7;
    }

    public void setBiStep7(BuildingsIsletStep7 biStep7)
    {
        this.biStep7 = biStep7;
    }

    public BuildingsIsletStep8 getBiStep8()
    {
        return this.biStep8;
    }

    public void setBiStep8(BuildingsIsletStep8 biStep8)
    {
        this.biStep8 = biStep8;
    }

    public void setProgression(int progression)
    {
        this.progression = progression;
    }

    public void setBIStep2(final BuildingsIsletStep2 biStepIn)
    {
        this.biStep2 = biStepIn;
    }
}
