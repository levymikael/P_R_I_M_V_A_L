package com.evalutel.primval_desktop;

import java.util.ArrayList;

public class MyCorrectionGeneral
{
    protected ArrayList<MyCorrectionInterface> allCorrectables = new ArrayList<>();

    public MyCorrectionGeneral()
    {}

    public void addElements(MyCorrectionInterface myCorrrectionInterfaceObject)
    {
        allCorrectables.add(myCorrrectionInterfaceObject);
    }

    public void isCorrected()
    {
        for (int i = 0; i < allCorrectables.size(); i++)
        {
            MyCorrectionInterface myCorrectionInterfaceObject = allCorrectables.get(i);
            myCorrectionInterfaceObject.myCorrection();
        }
    }

    public void resume()
    {
        for (int i = 0; i < allCorrectables.size(); i++)
        {
            MyCorrectionInterface myCorrectionInterfaceObject = allCorrectables.get(i);
            myCorrectionInterfaceObject.myExerciseFlowing();
        }
    }

}
