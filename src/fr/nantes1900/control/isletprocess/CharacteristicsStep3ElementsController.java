/**
 * 
 */
package fr.nantes1900.control.isletprocess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import fr.nantes1900.constants.ActionTypes;
import fr.nantes1900.constants.Characteristics;
import fr.nantes1900.constants.TextsKeys;
import fr.nantes1900.models.extended.Surface;
import fr.nantes1900.models.islets.buildings.exceptions.InvalidCaseException;
import fr.nantes1900.utils.FileTools;
import fr.nantes1900.view.isletprocess.CharacteristicsStep2View;
import fr.nantes1900.view.isletprocess.CharacteristicsStep3ElementsView;

/**
 * Characteristics panel for the second step of process of an islet. User can
 * select one or more triangles and modifies the type they belong to : building
 * or ground.
 * @author Camille
 * @author Luc
 */
public class CharacteristicsStep3ElementsController extends
        AbstractCharacteristicsSurfacesController
{
    private ArrayList<Surface> surfacesList;

    /**
     * Constructor.
     * @param parentController
     * @param triangleSelected
     */
    public CharacteristicsStep3ElementsController(
            IsletProcessController parentController, Surface elementSelected)
    {
        super(parentController, elementSelected);

        this.cView = new CharacteristicsStep3ElementsView();
        ((CharacteristicsStep3ElementsView) this.cView).setType("");
        this.cView.getValidateButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                String typeChosen = ((CharacteristicsStep3ElementsView) CharacteristicsStep3ElementsController.this.cView)
                        .getTypeSelected();

                int actionType = -1;
                switch (typeChosen)
                {
                    case Characteristics.TYPE_NOISE:
                        actionType = ActionTypes.TURN_TO_NOISE;
                    break;

                    case Characteristics.TYPE_BUILDING:
                        actionType = ActionTypes.TURN_TO_BUILDING;
                    break;
                }
                for (Surface surface : surfacesList)
                {
                    try
                    {
                        CharacteristicsStep3ElementsController.this.parentController
                                .getBiController().action3(surface, actionType);
                    } catch (InvalidCaseException e)
                    {
                        JOptionPane
                                .showMessageDialog(
                                        CharacteristicsStep3ElementsController.this.cView,
                                        FileTools
                                                .readErrorMessage(
                                                        TextsKeys.KEY_ERROR_INCORRECTTYPE,
                                                        TextsKeys.MESSAGETYPE_MESSAGE),
                                        FileTools
                                                .readErrorMessage(
                                                        TextsKeys.KEY_ERROR_INCORRECTTYPE,
                                                        TextsKeys.MESSAGETYPE_TITLE),
                                        JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public void addSurfaceSelected(Surface surfaceSelected)
    {
        surfacesList.add(surfaceSelected);
        ((CharacteristicsStep3ElementsView) this.cView).setType("");
    }

    public boolean removeSurfaceSelected(Surface surfaceSelected)
    {
        surfacesList.remove(surfaceSelected);

        return surfacesList.isEmpty();
    }

    @Override
    public void modifyViewCharacteristics()
    {
        // TODO Auto-generated method stub
        
    }
}
