package com.evalutel.primval_desktop;

import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class UnGateauAnniversaire extends AnimationImageNew implements MyDrawInterface, MyCorrectionAndPauseInterface
{
    public boolean shouldReturnToReserve = false;
    private ArrayList<MyPoint> positionsBougies = new ArrayList<>();
    private ArrayList<UneBougie> allBougies = new ArrayList<>();
    boolean isActive = true;


    int spaceBougies;


    public UnGateauAnniversaire(float startPositionX, float startPositionY, float gateauWidth, float gateauHeight)
    {
        super("Images/Onglet_1_6/gateau.png", startPositionX, startPositionY, gateauWidth, gateauHeight);


        float startX = animationWidth / 12;
        float startY = startPositionY + (MyConstants.SCREENHEIGHT / 10);

        spaceBougies = (int) animationWidth / 50;
        float posX = 0f;
        float posY = 0;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (i == 2)
                {
                    posX = (startX + j * (spaceBougies + gateauWidth / 6f));
                    posY = (startY + i * (spaceBougies + gateauHeight / 5));
                }
                else
                {
                    posX = (startX + j * (spaceBougies + gateauWidth / 4.5f));
                    posY = (startY + i * (spaceBougies + gateauHeight / 5));
                }
                positionsBougies.add(new MyPoint(currentPositionX + posX, currentPositionY + posY));
            }
            startX += startX + spaceBougies;
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

    }

    @Override
    public void myResume()
    {

    }


    @Override
    public void myCorrectionStart()
    {
    }

    @Override
    public void myCorrectionStop()
    {

    }
}
