package com.evalutel.primval_desktop;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;


public class UneBougie extends AnimationImageNew implements MyTouchInterface, MyDrawInterface, MyCorrectionAndPauseInterface
{
    public SacDeBougies sacDeBougies;
    //    Animation animation;
    private Timer timer = new Timer();
    public UnGateauAnniversaire gateauNew = null;
    //    private Object unePlancheNew;
//    private Object unContainer;
    boolean isActive = true;




    public UneBougie(float startPositionX, float startPositionY, float animationHeight)
    {
        super("Images/Onglet_1_6/bougie_bleu.png", startPositionX, startPositionY, animationHeight, animationHeight);

//        addSprites();
    }

//    private void addSprites()
//    {
//        Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
//
//        for (TextureAtlas.AtlasRegion region : regions)
//        {
//            Sprite sprite = textureAtlas.createSprite(region.name);
//
//            sprites.put(region.name, sprite);
//        }
//    }


    @Override
    public boolean isTouched(float x, float y)
    {
        Rectangle rectangle = new Rectangle(currentPositionX, currentPositionY, animationWidth, animationHeight);

        return rectangle.contains(x, y);
    }


    @Override
    public void setPosition(float x, float y)
    {
        currentPositionX = x;
        currentPositionY = y;
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

    @Override
    public boolean isDragable()
    {
        return true;
    }

    private static String getImageRandom()
    {
        Random rand = new Random();

        String billeFolder = "Images/Badix/Billes/";
        int billeFolderSize = 17;
        int rand_int = rand.nextInt(billeFolderSize);
        String rand_bille = billeFolder + "bille" + rand_int + ".png";

        return rand_bille;
    }

    public void touchDown()
    {
        if (gateauNew != null)
        {
            gateauNew.removeBougie(this);
        }
    }

    public void touchUp(ArrayList<UnGateauAnniversaire> gateaux/*, int firstPositionX, int firstPositionY*/)
    {
        boolean isAddedToGateau = false;
        if (this.isActive)
        {
            for (int i = 0; i < gateaux.size(); i++)
            {
                UnGateauAnniversaire gateauAux = gateaux.get(i);
                if (gateauAux.isInRect(this))
                {
                    isAddedToGateau = true;
                    //this.gateauNew = gateauAux
                    gateauAux.addBougieAndOrganize(this);
                    break;
                }
            }

            if (!isAddedToGateau)
            {
                if (this.gateauNew != null)
                {
                    if (this.gateauNew.shouldReturnToReserve)
                    {
                        //this.gateauNew.removeBougie(this);
                        this.gateauNew = null;
                        this.setPosition(100_000, 100_000);
                        sacDeBougies.addBougieToReserve(this);
                    }
                    else
                    {
                        this.gateauNew.addBougieAndOrganize(this);
                    }
                }
                else
                {
                    if (this != null)
                    {
                        sacDeBougies.addBougieToReserve(this);
                        this.setPosition(100_000, 100_000);
                    }
                }
            }
        }
    }

    @Override
    public void myPause()
    {
        super.myPause();
        this.setActive(false);
    }

    @Override
    public void myResume()
    {
        super.myResume();
        this.setActive(true);
    }

    @Override
    public boolean isPause()
    {
        return isPaused;
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
