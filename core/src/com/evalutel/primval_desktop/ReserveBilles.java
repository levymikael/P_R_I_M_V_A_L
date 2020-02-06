package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class ReserveBilles extends AnimationImageNew implements MyDrawInterface, MyTouchInterface
{
    public int largeurBille;
    boolean isActive = true;

    public ReserveBilles(int startPositionX, int startpositionY, int animationWidth, int animationHeight) {

        super("Images/Badix/boite900_vide.png",  startPositionX, startpositionY, animationWidth, animationHeight);
    }

    /**
     * @param currentPositionX point x coordinate
     * @param currentPositionY point y coordinate
     * @return whether the point is contained in the rectangle
     */
    public boolean contains(float currentPositionX, float currentPositionY) {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.animationWidth >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.animationHeight >= currentPositionY;
    }


    @Override
    public boolean isTouched(int x, int y)
    {
        return false;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }




    @Override
    public void myDraw(Batch batch) {
        TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("Images/Badix/boite900_vide.png")));
        batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
    }
}
