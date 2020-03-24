package com.evalutel.primval_desktop;

import com.evalutel.primval_desktop.onglets.chapitre1.ScreenOnglet;


public class UneMain extends AnimationImageNew implements MyDrawInterface
{

    public UnePlancheNew plancheNew = null;

    public UneMain(int startPositionX, int startPositionY, int animationWidth)
    {
        super("Images/EnonceUIElements/doigt_new.png", startPositionX, startPositionY, animationWidth, animationWidth);
    }

    public void moveTo(long animationDureemillis, int deplacementEnX, int deplacementEnY, ScreenOnglet.TaskEtape taskEtape, long delayNext)
    {
        imageUp();

        int deplacemenNewY = (int) (deplacementEnY - animationHeight * 0.9f);
        int deplacemenNewX = (int) (deplacementEnX - animationWidth * 0.1f);

        animateImage(animationDureemillis, false, deplacemenNewX, deplacemenNewY, taskEtape, delayNext, 1f / 6f);
    }

    public void cliqueTo(long animationDureemillis, int deplacementEnX, int deplacementEnY, ScreenOnglet.TaskEtape taskEtape, long delayNext)
    {
        imageDown();

        int deplacemenNewY = (int) (deplacementEnY - animationHeight * 0.9f);
        int deplacemenNewX = (int) (deplacementEnX - animationWidth * 0.1f);

        animateImage(animationDureemillis, false, deplacemenNewX, deplacemenNewY, taskEtape, delayNext, 1f / 6f);
    }

    public void imageDown()
    {
        animationWidth = screenWidth / 9;
        animationHeight = screenWidth / 9;
    }

    public void imageUp()
    {
        animationWidth = screenWidth / 8;
        animationHeight = screenWidth / 8;
    }



    @Override
    public void setPosition(int x, int y)
    {
        currentPositionX = x;
        currentPositionY = y;
    }


    public void touchDown()
    {
        if (plancheNew != null)
        {
            plancheNew.removeMain(this);
        }
    }


    public void touchUp(UnePlancheNew planche, int firstPositionX, int firstPositionY)
    {

        boolean isAddedToPlanche = false;

//        for (int i = 0; i < planche.size(); i++)
//        {
        //  UnePlancheNew plancheAux = planche.get(i);
        if (planche.isInRect(this))
        {
            isAddedToPlanche = true;
//                break;
        }
    }
//        if (!isAddedToPlanche)
//        {
//            if (this.planche != null)
//            {
//                if (this.plancheNew.shouldReturnToReserve)
//                {
//                    this.plancheNew = null;
//                    this.setPosition(100000, 100000);
//                } else
//                {
//                    this.plancheNew.addBilleAndOrganize(this);
//                }
//            } else
//            {
//                this.setPosition(100000, 100000);
//            }
//        }
}

