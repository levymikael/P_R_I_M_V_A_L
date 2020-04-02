package com.evalutel.primval_desktop;


public class UneMain extends AnimationImageNew implements MyDrawInterface, MyPauseInterface
{

//    public UnePlancheNew plancheNew = null;

    private int imageWidthInit;

    public UneMain(int startPositionX, int startPositionY, int animationWidth)
    {
        super("Images/EnonceUIElements/doigt_new.png", startPositionX, startPositionY, animationWidth, animationWidth * 919 / 702);
        imageWidthInit = animationWidth;
    }

    public void moveTo(long animationDureemillis, int deplacementEnX, int deplacementEnY, MyTimer.TaskEtape taskEtape, long delayNext)
    {
        imageUp();

        int deplacemenNewY = (int) (deplacementEnY - animationHeight * 0.9f);
        //int deplacemenNewX = (int) (deplacementEnX - ecartY);


        animateImage(animationDureemillis, false, deplacementEnX, deplacemenNewY, taskEtape, delayNext, 1f / 6f);
    }

    public void cliqueTo(long animationDureemillis, int deplacementEnX, int deplacementEnY, MyTimer.TaskEtape taskEtape, long delayNext)
    {
        imageDown();

        int deplacemenNewY = (int) ((deplacementEnY) - (animationHeight * 0.9f));

        animateImage(animationDureemillis, false, deplacementEnX, deplacemenNewY, taskEtape, delayNext, 1f / 6f);
    }

    public void imageDown()
    {
        float exHeight = animationHeight;
        animationWidth = (float) imageWidthInit * 3.0f / 4.0f;
        animationHeight = animationWidth * 919.0f / 702.0f;


        int ecartY = (int) (exHeight - animationHeight);

        currentPositionY = currentPositionY + ecartY;


    }

    public void imageUp()
    {
        float exHeight = animationHeight;
        animationWidth = imageWidthInit;
        animationHeight = animationWidth * 919.0f / 702.0f;


        int ecartY = (int) (animationHeight - exHeight);

        int deplacemenNewX = (int) (deplacementEnX + ecartY);

        currentPositionY = currentPositionY - ecartY;

        int ok = 5;
        ok++;
    }


    @Override
    public void setPosition(int x, int y)
    {
        currentPositionX = x;
        currentPositionY = y;
    }

//
//    public void touchDown()
//    {
//        if (plancheNew != null)
//        {
//            plancheNew.removeMain(this);
//        }
//    }
//
//
//    public void touchUp(UnePlancheNew planche, int firstPositionX, int firstPositionY)
//    {
//        boolean isAddedToPlanche = false;
//
////        for (int i = 0; i < planche.size(); i++)
////        {
//        //  UnePlancheNew plancheAux = planche.get(i);
//        if (planche.isInRect(this))
//        {
//            isAddedToPlanche = true;
////                break;
//        }
//    }

    @Override
    public void myPause()
    {

    }

    @Override
    public void myResume()
    {

    }

}

