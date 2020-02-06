package com.evalutel.primval_desktop.Ex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class UnePlanche extends Actor {

    Texture objetTexture;

    TextureRegion objetRegion;

//    private String nomObjet;
     float posStartX, posStartY;


     float posEndX, posEndY, objetRegionWidth, objetRegionHeight;


    public UnePlanche(float posStartX, float posStartY)
    {

        super();
        this.posStartX = posStartX;
        this.posStartY = posStartY;

        objetTexture = new Texture(Gdx.files.internal("Images/Badix/planche_9billes.png"));
        objetRegion = new TextureRegion(objetTexture);

        objetRegionWidth = objetRegion.getRegionWidth();
        objetRegionHeight = objetRegion.getRegionHeight();

        posEndX = posStartX + objetRegionWidth;
        posEndY = posStartY + objetRegionHeight;
    }

    public float getPosStartX() {
        return posStartX;
    }

    public void setPosStartX(float PosStartX) {
        this.posStartX = posStartX;
    }

    public float getPosStartY() {
        return posStartY;
    }

    public void setPosStartY(float PosStartY) {
        this.posStartY = posStartY;
    }

    public float getPosEndX() {
        return posEndX;
    }

    public void setPosEndX(float posEndX) {
        this.posEndX = posEndX;
    }

    public float getPosEndY() {
        return posEndY;
    }

    public void setPosEndY(float posEndY) {
        this.posEndY = posEndY;
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);

        batch.draw(objetRegion, posStartX, posStartY, objetRegionWidth, objetRegionHeight);

    }
}
