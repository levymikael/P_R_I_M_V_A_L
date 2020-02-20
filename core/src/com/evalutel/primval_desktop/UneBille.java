package com.evalutel.primval_desktop;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;


public class UneBille extends AnimationImageNew implements MyTouchInterface, MyDrawInterface
{
    TextureRegion[] animationFrames;
    Animation animation;
    private Timer timer = new Timer();
    public UnePlancheNew plancheNew = null;
    ArrayList<Object> billeCanBeDropedOn;
    private Object unePlancheNew;
    private Object unContainer;
    public ReserveBilles reserveBilles;


    public UneBille(int startPositionX, int startPositionY, float animationHeight)
    {
        super(getImageRandom(), startPositionX, startPositionY, animationHeight, animationHeight);

    }


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
        return false;
    }

    @Override
    public void setActive(boolean active)
    {

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

    public void touchUp(ArrayList<UnePlancheNew> planches, int firstPositionX, int firstPositionY)
    {

        boolean isAddedToPlanche = false;

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

        if ( ! isAddedToPlanche)
        {
            if (this.plancheNew != null)
            {
                if (this.plancheNew.shouldReturnToReserve)
                {
                    this.plancheNew = null;
                    this.setPosition(100000, 100000);
                    reserveBilles.addBilleToReserve(this);
                }
                else
                {
                    this.plancheNew.addBilleAndOrganize(this);
                }
            }
            else
            {
                reserveBilles.addBilleToReserve(this);
                this.setPosition(100000, 100000);
            }
        }
    }

}
