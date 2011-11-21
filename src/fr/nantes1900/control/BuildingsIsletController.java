package fr.nantes1900.control;

import java.io.IOException;
import java.util.List;

import javax.vecmath.Vector3d;

import fr.nantes1900.constants.ActionTypes;
import fr.nantes1900.control.display3d.Universe3DController;
import fr.nantes1900.control.isletselection.IsletSelectionController;
import fr.nantes1900.models.basis.Mesh;
import fr.nantes1900.models.basis.Triangle;
import fr.nantes1900.models.extended.Building;
import fr.nantes1900.models.extended.Roof;
import fr.nantes1900.models.extended.Surface;
import fr.nantes1900.models.extended.Wall;
import fr.nantes1900.models.extended.steps.BuildingStep3;
import fr.nantes1900.models.extended.steps.BuildingStep4;
import fr.nantes1900.models.extended.steps.BuildingStep5;
import fr.nantes1900.models.extended.steps.BuildingStep6;
import fr.nantes1900.models.extended.steps.BuildingStep7;
import fr.nantes1900.models.extended.steps.BuildingStep8;
import fr.nantes1900.models.islets.AbstractIslet;
import fr.nantes1900.models.islets.buildings.AbstractBuildingsIslet;
import fr.nantes1900.models.islets.buildings.ResidentialIslet;
import fr.nantes1900.models.islets.buildings.exception.InvalidCaseException;
import fr.nantes1900.models.islets.buildings.exception.UnCompletedParametersException;
import fr.nantes1900.models.islets.buildings.steps.BuildingsIsletStep0;
import fr.nantes1900.utils.ParserSTL;
import fr.nantes1900.view.display3d.MeshView;
import fr.nantes1900.view.display3d.PolygonView;

/**
 * Implements the controller of a building islet. Used to visualize the islets,
 * to launch the treatments.
 * @author Daniel
 */
public class BuildingsIsletController
{

    /**
     * The buildings islet containing the model.
     */
    private AbstractBuildingsIslet   islet;
    /**
     * The islet selection controller, which is the parent of this.
     */
    private IsletSelectionController parentController;
    /**
     * The universe 3D controller to interact with the universe 3D.
     */
    private Universe3DController     u3DController;
    /**
     * The normal of the gravity.
     */
    private Vector3d                 gravityNormal;

    /**
     * Constructor.
     * @param isletSelectionController
     *            the controller of the islet selection
     * @param universe3DControllerIn
     *            the universe 3D controller
     */
    public BuildingsIsletController(final IsletSelectionController isletSelectionController,
            final Universe3DController universe3DControllerIn)
    {
        this.parentController = isletSelectionController;
        this.u3DController = universe3DControllerIn;
        // LOOK : maybe it would be good to choose between industrial islet and
        // residential islet.
        this.islet = new ResidentialIslet();
    }

    /**
     * Changes the type of a list of triangles. To call only during the second
     * step.
     * @param trianglesSelected
     *            the list of triangles
     * @param type
     *            the new type of these triangles
     * @throws InvalidCaseException
     */
    public final void action2(final List<Triangle> trianglesSelected,
            final int type) throws InvalidCaseException
    {
        if (type == ActionTypes.TURN_TO_BUILDING)
        {
            // The user wants these triangles to turn building.
            this.islet.getBiStep2()
                    .getInitialGrounds()
                    .removeAll(trianglesSelected);
            this.islet.getBiStep2()
                    .getInitialBuildings()
                    .addAll(trianglesSelected);
        } else if (type == ActionTypes.TURN_TO_GROUND)
        {
            // The user wants these triangles to turn ground.
            this.islet.getBiStep2()
                    .getInitialBuildings()
                    .removeAll(trianglesSelected);
            this.islet.getBiStep2()
                    .getInitialGrounds()
                    .addAll(trianglesSelected);
        } else
        {
            throw new InvalidCaseException();
        }
    }

    /**
     * TODO.
     * @param trianglesSelected
     *            TODO
     * @param actionType
     *            TODO
     * @throws InvalidCaseException
     *             TODO
     */
    public final void action3(final List<Triangle> trianglesSelected,
            final int actionType) throws InvalidCaseException
    {
        if (actionType == ActionTypes.REMOVE)
        {
            for (Building building : this.islet.getBiStep3().getBuildings())
            {
                BuildingStep3 buildingStep = (BuildingStep3) building.getbStep();
                buildingStep.getInitialTotalMesh().removeAll(trianglesSelected);
            }
            this.islet.getBiStep3().getGrounds().removeAll(trianglesSelected);
        } else if (actionType == ActionTypes.TURN_TO_BUILDING)
        {
            this.islet.getBiStep3()
                    .getBuildings()
                    .add(new Building(new Mesh(trianglesSelected)));
            this.islet.getBiStep3().getNoise().removeAll(trianglesSelected);
        } else
        {
            throw new InvalidCaseException();
        }
    }

    /**
     * TODO.
     * @param mesh
     *            TODO
     * @param actionType
     *            TODO
     * @throws InvalidCaseException
     *             TODO
     */
    public final void
            action3(final Mesh mesh, final int actionType) throws InvalidCaseException
    {
        if (actionType == ActionTypes.TURN_TO_NOISE)
        {
            this.islet.getBiStep3()
                    .getBuildings()
                    .remove(this.returnBuildingContaining3(mesh));
            this.islet.getBiStep3().getNoise().addAll(mesh);
        } else if (actionType == ActionTypes.TURN_TO_BUILDING)
        {
            this.islet.getBiStep3().getBuildings().add(new Building(mesh));
            this.islet.getBiStep3().getNoise().removeAll(mesh);
        } else
        {
            throw new InvalidCaseException();
        }
    }

    /**
     * TODO.
     * @param trianglesSelected
     *            TODO
     * @param actionType
     *            TODO
     * @throws InvalidCaseException
     *             TODO
     * @throws UnCompletedParametersException
     *             TODO
     */
    public final void action4(final List<Triangle> trianglesSelected,
            final int actionType) throws InvalidCaseException,
            UnCompletedParametersException
    {
        Building building = this.searchForBuildingContaining4(trianglesSelected);
        BuildingStep4 buildingStep = (BuildingStep4) building.getbStep();

        if (actionType == ActionTypes.TURN_TO_WALL)
        {
            buildingStep.getInitialWall().addAll(trianglesSelected);
            buildingStep.getInitialRoof().remove(trianglesSelected);
        } else if (actionType == ActionTypes.TURN_TO_ROOF)
        {
            buildingStep.getInitialRoof().addAll(trianglesSelected);
            buildingStep.getInitialWall().remove(trianglesSelected);
        } else
        {
            throw new InvalidCaseException();
        }
    }

    /**
     * TODO.
     * @param trianglesSelected
     *            TODO
     * @return TODO
     */
    private Building
            searchForBuildingContaining4(final List<Triangle> trianglesSelected)
    {
        for (Building building : this.islet.getBiStep4().getBuildings())
        {
            BuildingStep4 buildingStep = (BuildingStep4) building.getbStep();
            if (buildingStep.getInitialWall().containsAll(trianglesSelected) || buildingStep.getInitialRoof()
                    .containsAll(trianglesSelected))
            {
                return building;
            }
        }
        return null;
    }

    /**
     * TODO.
     * @param surfacesSelected
     *            TODO
     * @param type
     *            TODO
     * @throws InvalidCaseException
     *             TODO
     * @throws UnCompletedParametersException
     *             TODO
     */
    public final void action5(final List<Surface> surfacesSelected,
            final int type) throws InvalidCaseException,
            UnCompletedParametersException
    {
        Building building = this.searchForBuildingContaining5(surfacesSelected);
        BuildingStep5 buildingStep = (BuildingStep5) building.getbStep();
        if (type == ActionTypes.MERGE)
        {
            if (buildingStep.getWalls().contains(surfacesSelected.get(0)))
            {
                // It means the meshes selected belong to the walls.
                buildingStep.getWalls().removeAll(surfacesSelected);
                Wall sum = new Wall();
                for (Surface s : surfacesSelected)
                {
                    sum.getMesh().addAll(s.getMesh());
                }
                buildingStep.getWalls().add(sum);
            } else
            {
                // It means the meshes selected belong to the roofs.
                buildingStep.getRoofs().removeAll(surfacesSelected);
                Roof sum = new Roof();
                for (Surface s : surfacesSelected)
                {
                    sum.getMesh().addAll(s.getMesh());
                }
                buildingStep.getRoofs().add(sum);
            }
        } else if (type == ActionTypes.TURN_TO_NOISE)
        {
            buildingStep.getWalls().removeAll(surfacesSelected);
            buildingStep.getRoofs().removeAll(surfacesSelected);
            for (Surface s : surfacesSelected)
            {
                buildingStep.getNoise().addAll(s.getMesh());
            }
        } else
        {
            throw new InvalidCaseException();
        }
    }

    /**
     * TODO.
     * @param surfacesSelected
     *            TODO
     * @param currentSurface
     *            TODO
     * @param actionType
     *            TODO
     * @throws InvalidCaseException
     *             TODO
     */
    public static final void action6(final List<Surface> surfacesSelected,
            final Surface currentSurface,
            final int actionType) throws InvalidCaseException
    {
        if (actionType == ActionTypes.ADD_NEIGHBOURS)
        {
            currentSurface.getNeighbours().addAll(surfacesSelected);
        } else if (actionType == ActionTypes.REMOVE_NEIGHBOURS)
        {
            currentSurface.getNeighbours().removeAll(surfacesSelected);
        } else
        {
            throw new InvalidCaseException();
        }
    }

    /**
     * TODO.
     * @param surfaceToMove
     *            TODO
     * @param currentSurface
     *            TODO
     * @param actionType
     *            TODO
     * @throws InvalidCaseException
     *             TODO
     */
    public static final void action7(final Surface surfaceToMove,
            final Surface currentSurface,
            final int actionType) throws InvalidCaseException
    {
        List<Surface> neighbours = currentSurface.getNeighbours();
        if (actionType == ActionTypes.UP_NEIGHBOUR)
        {
            neighbours.set(neighbours.indexOf(surfaceToMove) - 1, surfaceToMove);
        } else if (actionType == ActionTypes.DOWN_NEIGHBOUR)
        {
            neighbours.set(neighbours.indexOf(surfaceToMove) + 1, surfaceToMove);
        } else
        {
            throw new InvalidCaseException();
        }
    }

    /**
     * TODO.
     * @return TODO.
     */
    public final Vector3d computeNormalWithTrianglesSelected()
    {
        Mesh mesh = new Mesh(this.u3DController.getTrianglesSelected());
        return mesh.averageNormal();
    }

    /**
     * Dsisplays the set of meshes, considering the progression of the
     * treatement.
     */
    public final void display()
    {
        this.u3DController.getUniverse3DView().clearAllMeshes();

        try
        {
            switch (this.islet.getProgression())
            {
                case AbstractBuildingsIslet.ZERO_STEP:
                    this.viewStep0();
                break;
                case AbstractBuildingsIslet.FIRST_STEP:
                    this.viewStep1();
                break;
                case AbstractBuildingsIslet.SECOND_STEP:
                    this.viewStep2();
                break;
                case AbstractBuildingsIslet.THIRD_STEP:
                    this.viewStep3();
                break;
                case AbstractBuildingsIslet.FOURTH_STEP:
                    this.viewStep4();
                break;
                case AbstractBuildingsIslet.FIFTH_STEP:
                    this.viewStep5();
                break;
                case AbstractBuildingsIslet.SIXTH_STEP:
                    this.viewStep6();
                break;
                case AbstractBuildingsIslet.SEVENTH_STEP:
                    this.viewStep7();
                break;
                case AbstractBuildingsIslet.EIGHTH_STEP:
                    this.viewStep8();
                break;
                default:
                    throw new InvalidCaseException();
            }
        } catch (InvalidCaseException e)
        {
            System.out.println("Big problem");
        }
    }

    /**
     * Getter.
     * @return the gravity normal
     */
    public final Vector3d getGravityNormal()
    {
        return this.gravityNormal;
    }

    /**
     * Getter.
     * @return the buildings islet
     */
    public final AbstractBuildingsIslet getIslet()
    {
        return this.islet;
    }

    /**
     * Getter.
     * @return the controller of the islet selection
     */
    public final IsletSelectionController getIsletSelectionController()
    {
        return this.parentController;
    }

    /**
     * Getter.
     * @return the parent controller
     */
    public final IsletSelectionController getParentController()
    {
        return this.parentController;
    }

    /**
     * Getter.
     * @return the universe 3D controller
     */
    public final Universe3DController getU3DController()
    {
        return this.u3DController;
    }

    /**
     * Progression incrementation.
     */
    private void incProgression()
    {
        this.islet.incProgression();
    }

    /**
     * Launch the treatment, considering the progression.
     */
    public final void launchTreatment()
    {
        try
        {
            switch (this.islet.getProgression())
            {
                case AbstractBuildingsIslet.ZERO_STEP:
                    this.launchTreatment0();
                break;
                case AbstractBuildingsIslet.FIRST_STEP:
                    this.launchTreatment1();
                break;
                case AbstractBuildingsIslet.SECOND_STEP:
                    this.launchTreatment2();
                break;
                case AbstractBuildingsIslet.THIRD_STEP:
                    this.launchTreatment3();
                break;
                case AbstractBuildingsIslet.FOURTH_STEP:
                    this.launchTreatment4();
                break;
                case AbstractBuildingsIslet.FIFTH_STEP:
                    this.launchTreatment5();
                break;
                case AbstractBuildingsIslet.SIXTH_STEP:
                    this.launchTreatment6();
                break;
                case AbstractBuildingsIslet.SEVENTH_STEP:
                    this.launchTreatment7();
                break;
                default:
                    throw new InvalidCaseException();
            }

            this.incProgression();
            this.display();
        } catch (InvalidCaseException e)
        {
            System.out.println("Big problem");
        }
    }

    /**
     * Launches the first treatment.
     */
    private void launchTreatment0()
    {
        this.islet.getBiStep0().setArguments(this.gravityNormal);
        this.islet.setBiStep1(this.islet.getBiStep0().launchTreatment());
    }

    /**
     * Launches the first treatment.
     */
    private void launchTreatment1()
    {
        this.islet.setBiStep2(this.islet.getBiStep1().launchTreatment());
    }

    /**
     * Launches the second treatment.
     */
    private void launchTreatment2()
    {
        this.islet.setBiStep3(this.islet.getBiStep2().launchTreatment());
    }

    /**
     * Launches the third treatment.
     */
    private void launchTreatment3()
    {
        this.islet.setBiStep4(this.islet.getBiStep3().launchTreatment());
    }

    /**
     * Launches the fourth treatment.
     */
    private void launchTreatment4()
    {
        this.islet.setBiStep5(this.islet.getBiStep4().launchTreatment());
    }

    /**
     * Launches the fifth treatment.
     */
    private void launchTreatment5()
    {
        this.islet.setBiStep6(this.islet.getBiStep5().launchTreatment());
    }

    /**
     * Launches the sixth treatment.
     */
    private void launchTreatment6()
    {
        this.islet.setBiStep7(this.islet.getBiStep6().launchTreatment());
    }

    /**
     * Launches the seventh treatment.
     */
    private void launchTreatment7()
    {
        this.islet.setBiStep8(this.islet.getBiStep7().launchTreatment());
    }

    /**
     * TODO.
     */
    public final void putGravityNormal()
    {
        this.islet.setGravityNormal(this.gravityNormal);
    }

    /**
     * TODO.
     * @param fileName
     *            TODO.
     * @throws IOException
     *             TODO.
     */
    public final void
            readGravityNormal(final String fileName) throws IOException
    {
        ParserSTL parser = new ParserSTL(fileName);
        Mesh mesh = parser.read();
        this.setGravityNormal(mesh.averageNormal());
    }

    /**
     * TODO.
     * @param mesh
     *            TODO
     * @return TODO
     */
    public final Building returnBuildingContaining3(final Mesh mesh)
    {
        for (Building building : this.islet.getBiStep3().getBuildings())
        {
            BuildingStep3 buildingStep = (BuildingStep3) building.getbStep();
            if (buildingStep.getInitialTotalMesh() == mesh)
            {
                return null;
            }
        }
        return null;
    }

    /**
     * TODO.
     * @param surfacesSelected
     *            TODO
     * @return TODO
     * @throws UnCompletedParametersException
     *             TODO
     */
    private Building
            searchForBuildingContaining5(final List<Surface> surfacesSelected) throws UnCompletedParametersException
    {
        for (Building building : this.islet.getBiStep5().getBuildings())
        {
            BuildingStep5 buildingStep = (BuildingStep5) building.getbStep();
            if (buildingStep.getWalls().containsAll(surfacesSelected) || buildingStep.getRoofs()
                    .containsAll(surfacesSelected))
            {
                return building;
            }
        }
        throw new UnCompletedParametersException();
    }

    /**
     * Setter.
     * @param gravityNormalIn
     *            the gravity normal
     */
    public final void setGravityNormal(final Vector3d gravityNormalIn)
    {
        this.gravityNormal = gravityNormalIn;
    }

    /**
     * Setter.
     * @param groundNormal
     *            the ground normal
     */
    public final void setGroundNormal(final Vector3d groundNormal)
    {
        this.islet.setGroundNormal(groundNormal);
    }

    /**
     * Setter.
     * @param isletIn
     *            the new buildings islet
     */
    public final void setIslet(final AbstractBuildingsIslet isletIn)
    {
        this.islet = isletIn;
    }

    /**
     * Setter.
     * @param isletSelectionControllerIn
     *            the controller of the islet selection
     */
    public final void
            setIsletSelectionController(final IsletSelectionController isletSelectionControllerIn)
    {
        this.parentController = isletSelectionControllerIn;
    }

    /**
     * Setter.
     * @param parentControllerIn
     *            the parent controller
     */
    public final void
            setParentController(final IsletSelectionController parentControllerIn)
    {
        this.parentController = parentControllerIn;
    }

    /**
     * Setter.
     * @param u3dcontrollerIn
     *            the universe 3D controller
     */
    public final void
            setUniverse3DController(final Universe3DController u3dcontrollerIn)
    {
        this.u3DController = u3dcontrollerIn;
    }

    /**
     * TODO.
     */
    public final void useGravityNormalAsGroundNormal()
    {
        this.islet.setGroundNormal(this.islet.getGravityNormal());
    }

    /**
     * Displays the zero step.
     */
    public final void viewStep0()
    {
        this.getU3DController()
                .getUniverse3DView()
                .addMesh(new MeshView(this.islet.getBiStep0()
                        .getInitialTotalMesh()));
    }

    /**
     * Displays the first step.
     */
    public final void viewStep1()
    {
        this.getU3DController()
                .getUniverse3DView()
                .addMesh(new MeshView(this.islet.getBiStep1()
                        .getInitialTotalMeshAfterBaseChange()));
    }

    /**
     * Displays the second step.
     */
    public final void viewStep2()
    {
        this.getU3DController()
                .getUniverse3DView()
                .addMesh(new MeshView(this.islet.getBiStep2()
                        .getInitialBuildings()));
        this.getU3DController()
                .getUniverse3DView()
                .addMesh(new MeshView(this.islet.getBiStep2()
                        .getInitialGrounds()));

        // TODO : Also display the noise.
    }

    /**
     * Displays the third step.
     */
    public final void viewStep3()
    {
        this.getU3DController()
                .getUniverse3DView()
                .addMesh(new MeshView(this.islet.getBiStep3().getGrounds()));

        for (Building building : this.islet.getBiStep3().getBuildings())
        {
            BuildingStep3 buildingStep = (BuildingStep3) building.getbStep();
            this.getU3DController()
                    .getUniverse3DView()
                    .addMesh(new MeshView(buildingStep.getInitialTotalMesh()));
        }
    }

    /**
     * Displays the fourth step.
     */
    public final void viewStep4()
    {
        for (Building building : this.islet.getBiStep4().getBuildings())
        {
            BuildingStep4 buildingStep = (BuildingStep4) building.getbStep();
            this.getU3DController()
                    .getUniverse3DView()
                    .addMesh(new MeshView(buildingStep.getInitialWall()));
            this.getU3DController()
                    .getUniverse3DView()
                    .addMesh(new MeshView(buildingStep.getInitialRoof()));
        }
    }

    /**
     * Displays the fifth step.
     */
    public final void viewStep5()
    {
        for (Building building : this.islet.getBiStep5().getBuildings())
        {
            BuildingStep5 buildingStep = (BuildingStep5) building.getbStep();
            for (Surface wall : buildingStep.getWalls())
            {
                this.getU3DController()
                        .getUniverse3DView()
                        .addMesh(new MeshView(wall.getMesh()));
            }
            for (Surface roof : buildingStep.getRoofs())
            {
                this.getU3DController()
                        .getUniverse3DView()
                        .addMesh(new MeshView(roof.getMesh()));
            }
        }
    }

    /**
     * Displays the sixth step.
     */
    public final void viewStep6()
    {
        for (Building building : this.islet.getBiStep6().getBuildings())
        {
            BuildingStep6 buildingStep = (BuildingStep6) building.getbStep();
            for (Surface wall : buildingStep.getWalls())
            {
                this.getU3DController()
                        .getUniverse3DView()
                        .addMesh(new MeshView(wall.getMesh()));
            }
            for (Surface roof : buildingStep.getRoofs())
            {
                this.getU3DController()
                        .getUniverse3DView()
                        .addMesh(new MeshView(roof.getMesh()));
            }
        }
    }

    /**
     * Displays the seventh step.
     */
    public final void viewStep7()
    {
        for (Building building : this.islet.getBiStep7().getBuildings())
        {
            BuildingStep7 buildingStep = (BuildingStep7) building.getbStep();
            for (Surface wall : buildingStep.getWalls())
            {
                this.getU3DController()
                        .getUniverse3DView()
                        .addMesh(new MeshView(wall.getMesh()));
            }
            for (Surface roof : buildingStep.getRoofs())
            {
                this.getU3DController()
                        .getUniverse3DView()
                        .addMesh(new MeshView(roof.getMesh()));
            }
        }
    }

    /**
     * Displays the eighth step.
     */
    public final void viewStep8()
    {
        for (Building building : this.islet.getBiStep8().getBuildings())
        {
            BuildingStep8 buildingStep = (BuildingStep8) building.getbStep();
            for (Surface wall : buildingStep.getWalls())
            {
                this.getU3DController()
                        .getUniverse3DView()
                        .addPolygonView(new PolygonView(wall.getPolygone()));
            }
            for (Surface roof : buildingStep.getRoofs())
            {
                this.getU3DController()
                        .getUniverse3DView()
                        .addPolygonView(new PolygonView(roof.getPolygone()));
            }
        }
    }

    /**
     * Parses the file and builds the first step of the BuildingIslet.
     * @param fileName
     *            the name of the file
     */
    public final void readFile(final String fileName)
    {
        this.islet.setBiStep0(new BuildingsIsletStep0(AbstractIslet.parseFile(fileName)));
        this.islet.getBiStep0().setArguments(this.gravityNormal);
    }
}
