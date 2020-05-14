package com.evalutel.primval_desktop;

import java.util.ArrayList;

public class MyPauseGeneral
{
    protected ArrayList<MyPauseInterface> allPauseables = new ArrayList<>();

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
            MyPauseInterface myPauseInterfaceObject = allPauseables.get(i);
            myPauseInterfaceObject.myPause();

//            myPauseInterfaceObject.setActive(false);
        }
    }

    public void resume()
    {
        for (int i = 0; i < allPauseables.size(); i++)
        {
            MyPauseInterface myPauseInterfaceObject = allPauseables.get(i);
            myPauseInterfaceObject.myResume();

//            myPauseInterfaceObject.setActive(false);

        }
    }
}
