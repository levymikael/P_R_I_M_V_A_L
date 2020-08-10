package com.evalutel.primval_desktop;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;


public class UneBille extends AnimationImageNew implements MyTouchInterface, MyDrawInterface, MyCorrectionAndPauseInterface
{
    //    Animation animation;
    private Timer timer = new Timer();
    public UnePlancheNew plancheNew = null;
    //    private Object unePlancheNew;
//    private Object unContainer;
    public SacDeBilles sacDeBilles;
    boolean isActive = true;


    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();


    public UneBille(float startPositionX, float startPositionY, float animationHeight)
    {
        super(getImageRandom(), startPositionX, startPositionY, animationHeight, animationHeight);

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
        if (plancheNew != null)
        {
            plancheNew.removeBille(this);
        }
    }

    public void touchUp(ArrayList<UnePlancheNew> planches)
    {
        boolean isAddedToPlanche = false;
        if (this.isActive )
        {
            for (int i = 0; i < planches.size(); i++)
            {
                UnePlancheNew plancheAux = planches.get(i);
                if (plancheAux.isInRect(this) && plancheAux.isVisible && plancheAux.isActive)
                {
                    isAddedToPlanche = true;
                    plancheAux.addBilleAndOrganize(this);
                    break;
                }
            }

            if (!isAddedToPlanche)
            {
                if (this.plancheNew != null && this.plancheNew.isActive)
                {
                    if (this.plancheNew.shouldReturnToReserve)
                    {
                        this.plancheNew = null;
                        this.setPosition(100000, 100000);
                        sacDeBilles.addBilleToReserve(this);
                    }
                    else
                    {
                        this.plancheNew.addBilleAndOrganize(this);
                    }
                }
                else
                {
                    if (this != null)
                    {
                        sacDeBilles.addBilleToReserve(this);
                        this.setPosition(100000, 100000);
                    }
                }
            }
        }
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


    @Override
    public boolean isPause()
    {
        return isPaused;
    }


}
