package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.ArrayList;


public class ReserveBilles extends AnimationImageNew implements MyDrawInterface, MyTouchInterface, MyPauseInterface
{
    public int largeurBille;
    boolean isActive = true;
    ArrayList<UneBille> arrrayBilles = new ArrayList<>();

    public ReserveBilles(int startPositionX, int startpositionY, int animationWidth, int animationHeight)
    {
        super("Images/Badix/boite900_vide.png", startPositionX, startpositionY, animationWidth, animationHeight);


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


    public UneBille getBilleAndRemove()
    {
        UneBille retour = arrrayBilles.get(arrrayBilles.size() - 1);
        arrrayBilles.remove(retour);

        return retour;
    }

    public void addBilleToReserve(UneBille bille)
    {
        bille.isVisible = false;
        arrrayBilles.add(bille);
        bille.reserveBilles = this;
    }

    @Override
    public boolean isDragable()
    {
        return false;
    }

    @Override
    public boolean isTouched(int x, int y)
    {
        return false;
    }

    @Override
    public boolean isActive()
    {
        return true;
    }


    public void setActive(boolean active)
    {
        isActive = active;
    }


    @Override
    public void myDraw(Batch batch)
    {
        TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("Images/Badix/boite900_vide.png")));
        batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
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
