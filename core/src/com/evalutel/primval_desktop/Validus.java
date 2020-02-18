package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evalutel.ui_tools.MyImageButton;


public class Validus extends AnimationImageNew implements MyDrawInterface
{
    public int largeurBille;

    public Validus(int startPositionX, int startpositionY, int animationWidth, int animationHeight) {

        super("Images/vo00000.png",  startPositionX, startpositionY, animationWidth, animationHeight);
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
    public void myDraw(Batch batch) {
        TextureRegion textureRegion2 = new TextureRegion(new Texture(Gdx.files.internal("Images/vo00000.png")));

        batch.draw(textureRegion2, currentPositionX, currentPositionY, animationWidth, animationHeight);

    }
}
