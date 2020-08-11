package com.evalutel.primval_desktop;

import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;

import java.util.ArrayList;

public class MyCorrectionAndPauseGeneral
{
    protected ArrayList<MyCorrectionAndPauseInterface> allCorrigibles = new ArrayList<>();

    public MyCorrectionAndPauseGeneral()
    {
    }

    public void addElements(com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface myCorrectionAndPauseInterface)
    {
        allCorrigibles.add(myCorrectionAndPauseInterface);
    }

    public void correctionStart()
    {
        for (int i = 0; i < allCorrigibles.size(); i++)
        {
            com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface myCorrectionAndPauseInterfaceObject = allCorrigibles.get(i);
            myCorrectionAndPauseInterfaceObject.myCorrectionStart();
        }
    }

    public void correctionStop()
    {
        for (int i = 0; i < allCorrigibles.size(); i++)
        {
            com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface myCorrectionAndPauseInterfaceObject = allCorrigibles.get(i);
            myCorrectionAndPauseInterfaceObject.myCorrectionStop();
        }
    }

    public void pause()
    {
        for (int i = 0; i < allCorrigibles.size(); i++)
        {
            com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface myCorrectionAndPauseInterfaceObject = allCorrigibles.get(i);
            myCorrectionAndPauseInterfaceObject.myPause();

//            myPauseInterfaceObject.setActive(false);
        }
    }

    public void resume()
    {
        for (int i = 0; i < allCorrigibles.size(); i++)
        {
            com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface myCorrectionAndPauseInterfaceObject = allCorrigibles.get(i);
            myCorrectionAndPauseInterfaceObject.myResume();
//            myPauseInterfaceObject.setActive(false);
        }
    }
}
