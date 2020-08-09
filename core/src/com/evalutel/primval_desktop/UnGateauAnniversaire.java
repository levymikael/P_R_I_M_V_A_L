package com.evalutel.primval_desktop;

import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class UnGateauAnniversaire extends AnimationImageNew implements MyDrawInterface, MyCorrectionAndPauseInterface, MyTouchInterface
{
    public boolean shouldReturnToReserve = false;
    private ArrayList<MyPoint> positionsBougies = new ArrayList<>();
    private ArrayList<UneBougie> allBougies = new ArrayList<>();
    boolean isActive = true;


    float spaceBougies;


    public UnGateauAnniversaire(float startPositionX, float startPositionY, float gateauWidth, float gateauHeight)
    {
        super("Images/Onglet_1_6/gateau.png", startPositionX, startPositionY, gateauWidth, gateauHeight);

        float startX = startPositionX + (MyConstants.SCREENWIDTH / 12f);
        float startY = startPositionY + (MyConstants.SCREENHEIGHT / 6f);

        spaceBougies = animationWidth / 60f;
        float posX = 0, posY = 0;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (i == 2)
                {
                    posX = startX + (j * (spaceBougies + gateauWidth / 5f) + MyConstants.SCREENWIDTH / 30f + MyConstants.SCREENWIDTH / 30f);
                    posY = startY + (MyConstants.SCREENHEIGHT / 11f) + (MyConstants.SCREENHEIGHT / 17f);
                }
                else if (i == 1)
                {
                    posX = startX + (j * (spaceBougies + gateauWidth / 5f) + MyConstants.SCREENWIDTH / 30f);
                    posY = startY + (MyConstants.SCREENHEIGHT / 11f);
                }
                else if (i == 0)
                {
                    posX = (startX + (j * (spaceBougies + gateauWidth / 5f)));
                    posY = (startY);
                }
                System.out.println("posX = " + posX + "i: " + i + " j: " + j);
                System.out.println("posY = " + posY + "i: " + i + " j: " + j);
                positionsBougies.add(new MyPoint(/*currentPositionX +*/ posX, /*currentPositionY +*/ posY));
            }
        }
    }

    /**
     * @param currentPositionX point x coordinate
     * @param currentPositionY point y coordinate
     * @return whether the point is contained in the planche (rectangle)
     */
    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.animationWidth >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.animationHeight >= currentPositionY;
    }

    public boolean isInRect(UneBougie bougie)
    {
        boolean test = (bougie.currentPositionX > this.currentPositionX) && (bougie.currentPositionX < (this.currentPositionX + this.animationWidth)) && (bougie.currentPositionY > this.currentPositionY) && (bougie.currentPositionY < (this.currentPositionY + this.animationHeight));

        return test;
    }

/*   public boolean isInRect(UneMain uneMain)
    {
        boolean isInPlanche = (uneMain.currentPositionX > this.currentPositionX) && (uneMain.currentPositionX < (this.currentPositionX + this.animationWidth)) && (uneMain.currentPositionY > this.currentPositionY) && (uneMain.currentPositionY < (this.currentPositionY + this.animationHeight));

        return isInPlanche;
    }*/

/*   public MyPoint getPositionBille(int index)
    {
        return positionsBilles.get(index);
    }*/

    public UneBougie getBougie(int index)
    {
        return allBougies.get(index);
    }

    public UneBougie getLastBougie()
    {
        if (allBougies.size() > 0)
        {
            return allBougies.get(allBougies.size() - 1);
        }
        else
        {
            return null;
        }
    }

    public void setAllBougiesInactive()
    {
        for (int i = 0; i < allBougies.size(); i++)
        {
            UneBougie bougie = allBougies.get(i);
            bougie.setActive(false);
        }
    }

    public void setAllBougiesActive()
    {
        for (int i = 0; i < allBougies.size(); i++)
        {
            UneBougie bougie = allBougies.get(i);
            bougie.setActive(true);
        }
    }

    /**
     * @param currentPositionX point x coordinate
     * @param currentPositionY point y coordinate
     * @return whether the point is out of the planche
     */
    public boolean outOfPlanche(float currentPositionX, float currentPositionY)
    {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.animationWidth >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.animationHeight >= currentPositionY;
    }


    public boolean addBougieAndOrganize(UneBougie uneBougie)
    {
        boolean retour = addBougie(uneBougie);

        reorganiseBougies();


        return retour;
    }

    public boolean addBougie(UneBougie uneBougie)
    {
        boolean retour = false;

        int nbBougiesPresents = allBougies.size();

        if (nbBougiesPresents < 9)
        {
            retour = true;
            MyPoint ptAux = positionsBougies.get(nbBougiesPresents);

            uneBougie.setPosition(ptAux.x, ptAux.y);
            allBougies.add(uneBougie);
            uneBougie.gateauNew = this;
            uneBougie.setVisible(true);
        }
        return retour;
    }

    public void reorganiseBougies()
    {
        ArrayList<UneBougie> arrayBougiesAux = new ArrayList<>(allBougies);

        allBougies.clear();

        for (int i = 0; i < arrayBougiesAux.size(); i++)
        {
            UneBougie bougie = arrayBougiesAux.get(i);
            addBougie(bougie);
        }
    }

    @Override
    public boolean isTouched(float x, float y)
    {
        return false;
    }

    @Override
    public boolean isDragable()
    {
        return false;
    }

    @Override
    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public void removeBougie(UneBougie uneBougie)
    {
        allBougies.remove(uneBougie);
    }


    public void removeMain(UneMain uneMain)
    {
        allBougies.remove(uneMain);
    }

    public int getNumberBougies()
    {
        return allBougies.size();
    }


    @Override
    public void myPause()
    {
        this.isActive = false;
    }

    @Override
    public void myResume()
    {
        this.isActive = true;
    }


    @Override
    public void myCorrectionStart()
    {
        this.setActive(false);
    }

    @Override
    public void myCorrectionStop()
    {
        this.setActive(true);
    }
}
