/**
 * 
 */
package fr.nantes1900.control.isletprocess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import fr.nantes1900.models.extended.Surface;
import fr.nantes1900.view.isletprocess.CharacteristicsStep6View;

/**
 * Characteristics panel for the sixth step of process of an islet. TODO
 * @author Camille
 * @author Luc
 */
public class CharacteristicsStep6Controller extends
        AbstractCharacteristicsSurfacesController {

    private Surface surfaceLocked;
    private boolean isEnabled;

    /**
     * Constructor.
     * @param parentController
     * @param triangleSelected
     */
    public CharacteristicsStep6Controller(
            IsletProcessController parentController, Surface surfaceLocked,
            ArrayList<Surface> neighbours) {
        super(parentController);

        this.surfaceLocked = surfaceLocked;
        this.surfacesList = neighbours;
        this.cView = new CharacteristicsStep6View(neighbours);
        this.isEnabled = true;
        setEnabled(false);

        this.cView.getValidateButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

            }
        });
    }

    public ArrayList<Surface> getSurfaces() {
        return this.surfacesList;
    }

    @Override
    public void modifyViewCharacteristics() {
        ((CharacteristicsStep6View) this.cView).setList(this.surfacesList);
    }

    public void setEnabled(boolean enabled) {
        if (!this.isEnabled && enabled) {
            this.isEnabled = true;
            ((CharacteristicsStep6View) this.cView)
                    .setModificationsEnabled(true);
        } else if (this.isEnabled && !enabled) {
            this.isEnabled = false;
            this.cView.setToolTipText("Test");
            ((CharacteristicsStep6View) this.cView)
                    .setModificationsEnabled(false);
        }
    }
}
