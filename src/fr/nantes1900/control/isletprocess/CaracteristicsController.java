/**
 * 
 */
package fr.nantes1900.control.isletprocess;

import fr.nantes1900.view.isletprocess.CaracteristicsStep2View;
import fr.nantes1900.view.isletprocess.CaracteristicsView;

/**
 * @author Camille
 */
// FIXME : Javadoc
public abstract class CaracteristicsController
{
    protected IsletProcessController parentController;
    protected CaracteristicsView     cView;

    public CaracteristicsController(IsletProcessController parentController)
    {
        this.parentController = parentController;
    }

    public CaracteristicsView getView()
    {
        return cView;
    }
}
