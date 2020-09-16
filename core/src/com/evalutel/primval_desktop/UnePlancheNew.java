package com.evalutel.primval_desktop;

import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class UnePlancheNew extends AnimationImageNew implements MyDrawInterface, MyCorrectionAndPauseInterface, MyTouchInterface
{
    public boolean shouldReturnToReserve = false;
    public boolean shouldReturnToFirstPlanche = false;
    private ArrayList<MyPoint> positionsBilles = new ArrayList<>();
    private ArrayList<UneBille> allBilles = new ArrayList<>();
    boolean isActive = true;


    int spaceBille;


    public UnePlancheNew(float startPositionX, float startPositionY, float plancheWidth, float billeWidth)
    {
        super("Images/Badix/planche_9billes.png", startPositionX, startPositionY, plancheWidth, plancheWidth);

        int startX = (int) animationWidth / 12;

        spaceBille = (int) animationWidth / 24;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                float posX = (startX + j * (spaceBille + billeWidth));
                float posY = (startX + i * (spaceBille + billeWidth));

                positionsBilles.add(new MyPoint(currentPositionX + posX, currentPositionY + posY));
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

    public boolean isInRect(UneBille bille)
    {
        boolean test = (bille.currentPositionX > this.currentPositionX) && (bille.currentPositionX < (this.currentPositionX + this.animationWidth)) && (bille.currentPositionY > this.currentPositionY) && (bille.currentPositionY < (this.currentPositionY + this.animationHeight));

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

    public UneBille getBille(int index)
    {
        return allBilles.get(index);
    }

    public UneBille getLastBille()
    {
        if (allBilles.size() > 0)
        {
            return allBilles.get(allBilles.size() - 1);

        }
        else
        {
            return null;
        }
    }

    public void setAllBillesInactive()
    {
        for (int i = 0; i < allBilles.size(); i++)
        {
            UneBille bille = allBilles.get(i);
            bille.setActive(false);
        }
    }

    public void setAllBillesActive()
    {
        for (int i = 0; i < allBilles.size(); i++)
        {
            UneBille bille = allBilles.get(i);
            bille.setActive(true);
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


    public boolean addBilleAndOrganize(UneBille uneBille)
    {
        boolean retour = false;
        if (this.isActive && this.isVisible)
        {
            retour = addBille(uneBille);

            reorganiseBilles();
        }
        return retour;

    }

    public ArrayList getAllBilles()
    {
        return allBilles;

    }

    public boolean addBille(UneBille uneBille)
    {
        boolean retour = false;

        int nbBillesPresents = allBilles.size();

        if ((nbBillesPresents < 9) && (this.isVisible) && (this.isActive))
        {
            retour = true;
            MyPoint ptAux = positionsBilles.get(nbBillesPresents);

            uneBille.setPosition(ptAux.x, ptAux.y);
            allBilles.add(uneBille);
            uneBille.plancheNew = this;
            uneBille.setVisible(true);
        }
        return retour;
    }

    public void reorganiseBilles()
    {
        if (this.isActive)
        {
            ArrayList<UneBille> arrayBillesAux = new ArrayList<>(allBilles);

            allBilles.clear();

            for (int i = 0; i < arrayBillesAux.size(); i++)
            {
                UneBille bille = arrayBillesAux.get(i);
                addBille(bille);
            }
        }
    }

    public void removeBille(UneBille uneBille)
    {
        allBilles.remove(uneBille);
    }

    public void removeAllBilles(SacDeBilles sacDeBilles)
    {
        for (int i = 0; i < allBilles.size(); i++)
        {
            UneBille bille = allBilles.get(i);
            bille.setPosition(10_000, 10_000);
            sacDeBilles.addBilleToReserve(bille);

        }
        allBilles.clear();
    }


    public int getNumberBilles()
    {
        return allBilles.size();
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

    @Override
    public void setActive(boolean active)
    {
        isActive = active;
    }
}
