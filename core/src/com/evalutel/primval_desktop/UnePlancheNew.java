package com.evalutel.primval_desktop;

import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class UnePlancheNew extends AnimationImageNew implements MyDrawInterface, MyPauseInterface
{
    public boolean shouldReturnToReserve = false;
    private ArrayList<MyPoint> positionsBilles = new ArrayList<>();
    private ArrayList<UneBille> allBilles = new ArrayList<>();
    public boolean isActif;


    int spaceBille;


    public UnePlancheNew(int startPositionX, int startPositionY, int plancheWidth, int billeWidth)
    {
        super("Images/Badix/planche_9billes.png", startPositionX, startPositionY, (float) plancheWidth, plancheWidth);

        int startX = (int) animationWidth / 12;

        spaceBille = (int) animationWidth / 24;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                int posX = (startX + j * (spaceBille + billeWidth));
                int posY = (startX + i * (spaceBille + billeWidth));

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

    public boolean isInRect(UneMain uneMain)
    {
        boolean isInPlanche = (uneMain.currentPositionX > this.currentPositionX) && (uneMain.currentPositionX < (this.currentPositionX + this.animationWidth)) && (uneMain.currentPositionY > this.currentPositionY) && (uneMain.currentPositionY < (this.currentPositionY + this.animationHeight));

        return isInPlanche;
    }

    public MyPoint getPositionBille(int index)
    {
        return positionsBilles.get(index);
    }

    public UneBille getBille(int index)
    {
        return allBilles.get(index);
    }

    public UneBille getLastBille()
    {
        if(allBilles.size() > 0)
        {
            return allBilles.get(allBilles.size()-1);
        }
        else
        {
            return null;
        }

    }

    public void SetAllBillesInactive()
    {
        for (int i = 0 ; i < allBilles.size(); i ++)
        {
            UneBille bille = allBilles.get(i);
            bille.setActive(false);
        }
    }

    public void SetAllBillesActive()
    {
        for (int i = 0 ; i < allBilles.size(); i ++)
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
        boolean retour = addBille(uneBille);

        reorganiseBilles();


        return retour;
    }

    public boolean addBille(UneBille uneBille)
    {
        boolean retour = false;

        int nbBillesPresents = allBilles.size();

        if (nbBillesPresents < 9)
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
        ArrayList<UneBille> arrayBillesAux = new ArrayList<>(allBilles);

        allBilles.clear();

        for (int i = 0; i < arrayBillesAux.size(); i++)
        {
            UneBille bille = arrayBillesAux.get(i);
            addBille(bille);
        }
    }

    public void removeBille(UneBille uneBille)
    {
        allBilles.remove(uneBille);
    }

//    public void removeBilleToReserve(UneBille uneBille)
//    {
//        allBilles.remove(uneBille);
//        uneBille.reserveBilles.addBilleToReserve(uneBille);
//    }

    public void removeMain(UneMain uneMain)
    {
        allBilles.remove(uneMain);
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
}
