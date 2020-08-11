package com.evalutel.primval_desktop;

import com.evalutel.primval_desktop.Interfaces.MyPauseInterface;

import java.util.ArrayList;

public class MyPauseGeneral
{
    protected ArrayList<com.evalutel.primval_desktop.Interfaces.MyPauseInterface> allPauseables = new ArrayList<>();

    public MyPauseGeneral()
    {
    }

    public void addElements(MyPauseInterface myPauseInterfaceObject)
    {
        allPauseables.add(myPauseInterfaceObject);
    }


    public void pause()
    {
        for (int i = 0; i < allPauseables.size(); i++)
        {
            com.evalutel.primval_desktop.Interfaces.MyPauseInterface myPauseInterfaceObject = allPauseables.get(i);
            myPauseInterfaceObject.myPause();

//            myPauseInterfaceObject.setActive(false);
        }
    }

    public void resume()
    {
        for (int i = 0; i < allPauseables.size(); i++)
        {
            com.evalutel.primval_desktop.Interfaces.MyPauseInterface myPauseInterfaceObject = allPauseables.get(i);
            myPauseInterfaceObject.myResume();

//            myPauseInterfaceObject.setActive(false);

        }
    }
}
