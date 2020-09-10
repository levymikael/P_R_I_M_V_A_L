package com.evalutel.primval_desktop;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;

public class UneSouris extends AnimationImageNew implements MyDrawInterface, MyCorrectionAndPauseInterface
{
    private float imageWidthInit;
    private TextureRegion textureRegionMain;
    private TextureRegion textureRegionMainClicked;

    public UneSouris(int startPositionX, int startPositionY, float animationWidth)
    {
        super("Images/EnonceUIElements/curseur_souris_inactif.png", startPositionX, startPositionY, animationWidth, animationWidth);
        imageWidthInit = animationWidth;

        Texture imgAux = new Texture("Images/EnonceUIElements/curseur_souris_inactif.png");
        imgAux.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegionMain = new TextureRegion(imgAux);

        Texture imgAux2 = new Texture("Images/EnonceUIElements/curseur_souris_actif.png");
        imgAux2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegionMainClicked = new TextureRegion(imgAux2);
    }

    public void moveTo(long animationDureemillis, float deplacementEnX, float deplacementEnY, MyTimer.TaskEtape taskEtape, long delayNext)
    {
        imageUp();

        float deplacemenNewY = deplacementEnY - animationHeight + 45f * animationHeight / 183f;
        float deplacemenNewX = deplacementEnX - 45f * animationHeight / 183f;

        animateImage(animationDureemillis, false, deplacemenNewX, deplacemenNewY, taskEtape, delayNext, 1f / 6f);
    }

    public void cliqueTo(long animationDureemillis, float deplacementEnX, float deplacementEnY, MyTimer.TaskEtape taskEtape, long delayNext)
    {
        imageDown();
        
        float deplacemenNewY = deplacementEnY - animationHeight + 45f * animationHeight / 183f;
        float deplacemenNewX = deplacementEnX - 45f * animationHeight / 183f;

        animateImage(animationDureemillis, false, deplacemenNewX, deplacemenNewY, taskEtape, delayNext, 1f / 6f);
    }

    public void imageDown()
    {
//        textureRegionMainClicked.setRegionX((int) (currentPositionX - animationWidth * .3f));
//        textureRegionMainClicked.setRegionY((int) (currentPositionX - animationWidth * .8f));

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

