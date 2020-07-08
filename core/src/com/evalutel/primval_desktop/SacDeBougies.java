package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;


public class SacDeBougies extends AnimationImageNew implements MyDrawInterface, MyTouchInterface, MyCorrectionAndPauseInterface
{
    public int largeurBille;
    boolean isActive = true;
    ArrayList<UneBougie> arrrayBougies = new ArrayList<>();

    public SacDeBougies(float startPositionX, float startpositionY, float animationWidth, float animationHeight)
    {
        super("Images/éléments_outils/Bougies_actif.png", startPositionX, startpositionY, animationWidth, animationHeight);
    }

    /**
     * @param currentPositionX point x coordinate
     * @param currentPositionY point y coordinate
     * @return whether the point is contained in the rectangle
     */
    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.animationWidth >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.animationHeight >= currentPositionY;
    }


    public UneBougie getBougieAndRemove()
    {
        UneBougie retour = arrrayBougies.get(arrrayBougies.size() - 1);
        arrrayBougies.remove(retour);

        return retour;
    }

    public void addBougieToReserve(UneBougie bougie)
    {
        bougie.isVisible = false;
        arrrayBougies.add(bougie);
        bougie.sacDeBougies = this;
    }


    public int getBillesCount()
    {
        return arrrayBougies.size();
    }

    @Override
    public boolean isDragable()
    {
        return false;
    }

    @Override
    public boolean isTouched(float x, float y)
    {
        return false;
    }

    @Override
    public boolean isActive()
    {
        return isActive;
    }


    public void setActive(boolean active)
    {
        isActive = active;
    }


    @Override
    public void myDraw(Batch batch)
    {
        TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("Images/éléments_outils/Bougies_actif.png")));
        batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
    }

    @Override
    public void myPause()
    {
        this.isActive = false;
    }

    @Override
    public void myResume()
    {
        this.isActive = true;
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
