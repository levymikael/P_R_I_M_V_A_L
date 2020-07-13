package com.evalutel.primval_desktop;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UneMain extends AnimationImageNew implements MyDrawInterface, MyCorrectionAndPauseInterface
{
    private int imageWidthInit;
    private TextureRegion textureRegionMain;
    private TextureRegion textureRegionMainClicked;

    public UneMain(int startPositionX, int startPositionY, int animationWidth)
    {
        super("Images/EnonceUIElements/doigt_neww.png", startPositionX, startPositionY, animationWidth, animationWidth * 919 / 702);
        imageWidthInit = animationWidth;


        Texture imgAux = new Texture("Images/EnonceUIElements/doigt_neww.png");
        imgAux.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegionMain = new TextureRegion(imgAux);


        Texture imgAux2 = new Texture("Images/EnonceUIElements/doigt_neww_click.png");
        imgAux2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegionMainClicked = new TextureRegion(imgAux2);
    }

    public void moveTo(long animationDureemillis, float deplacementEnX, float deplacementEnY, MyTimer.TaskEtape taskEtape, long delayNext)
    {
        imageUp();

        float deplacemenNewY = (deplacementEnY - animationHeight * 0.9f);
        //int deplacemenNewX = (int) (deplacementEnX - ecartY);

        animateImage(animationDureemillis, false, deplacementEnX, deplacemenNewY, taskEtape, delayNext, 1f / 6f);
    }

    public void cliqueTo(long animationDureemillis, float deplacementEnX, float deplacementEnY, MyTimer.TaskEtape taskEtape, long delayNext)
    {
        imageDown();

        int deplacemenNewY = (int) ((deplacementEnY) - (animationHeight * 0.9f));

        animateImage(animationDureemillis, false, deplacementEnX, deplacemenNewY, taskEtape, delayNext, 1f / 6f);
    }

    public void imageDown()
    {
        changeImage(textureRegionMainClicked);
    }

    public void imageUp()
    {
        changeImage(textureRegionMain);
    }


    @Override
    public void setPosition(float x, float y)
    {
        currentPositionX = x;
        currentPositionY = y;
    }


    @Override
    public void myCorrectionStart()
    {
        //super.myCorrectionStart();
    }

    @Override
    public void myCorrectionStop()
    {
        //super.myCorrectionStop();
    }

    @Override
    public void myPause()
    {
        super.myPause();
    }

    @Override
    public void myResume()
    {
        super.myResume();
    }
}

