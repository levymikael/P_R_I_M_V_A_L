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
    public SacDeBougies sacDeBilles;
    //    Animation animation;
    private Timer timer = new Timer();
    public UnGateauAnniversaire unGateauAnniversaire = null;
    //    private Object unePlancheNew;
//    private Object unContainer;
    public SacDeBougies sacDeBougies;
    boolean isActive = true;


    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();


    public UneBougie(int startPositionX, int startPositionY, float animationHeight)
    {
        super("getImageRandom()", startPositionX, startPositionY, animationHeight, animationHeight);

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
    public boolean isTouched(int x, int y)
    {
        Rectangle rectangle = new Rectangle(currentPositionX, currentPositionY, animationWidth, animationHeight);

        return rectangle.contains(x, y);
    }


    @Override
    public void setPosition(int x, int y)
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

    public void touchUp(ArrayList<UnePlancheNew> planches/*, int firstPositionX, int firstPositionY*/)
    {
        boolean isAddedToPlanche = false;
        if (this.isActive)
        {
            for (int i = 0; i < planches.size(); i++)
            {
                UnePlancheNew plancheAux = planches.get(i);
                if (plancheAux.isInRect(this))
                {
                    isAddedToPlanche = true;
                    plancheAux.addBilleAndOrganize(this);
                    break;
                }
            }

            if (!isAddedToPlanche)
            {
                if (this.gateauNew != null)
                {
                    if (this.gateauNew.shouldReturnToReserve)
                    {
                        this.gateauNew = null;
                        this.setPosition(100000, 100000);
                        sacDeBougies.addBougieToReserve(this);
                    }
                    else
                    {
                        this.gateauNew.addBilleAndOrganize(this);
                    }
                }
                else
                {
                    if (this != null)
                    {
                        sacDeBougies.addBougieToReserve(this);
                        this.setPosition(100000, 100000);
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
