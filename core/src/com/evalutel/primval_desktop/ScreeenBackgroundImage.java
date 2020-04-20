package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ScreeenBackgroundImage extends TextureRegion implements MyDrawInterface
{

    TextureRegion bgImageRegion;
    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();
    protected boolean isVisible = true;
    protected boolean isActive = true;


    public ScreeenBackgroundImage(String bgImagePath)
    {
        bgImageRegion = new TextureRegion(new Texture(Gdx.files.internal(bgImagePath)));
    }


    @Override
    public boolean isVisible()
    {
        return isVisible;
    }

    @Override
    public void setVisible(boolean visible)
    {
        isVisible = visible;
    }


    @Override
    public void myDraw(Batch batch)
    {
        batch.draw(bgImageRegion, 0, 0, screenWidth, screenHeight);
    }

    public void myDraw2(Batch batch, int width, int height, int x, int y)
    {
        batch.draw(bgImageRegion, x, x, width, height);
    }
}
