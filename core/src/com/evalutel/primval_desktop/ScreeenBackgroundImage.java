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

    Texture texture;


    public ScreeenBackgroundImage(String bgImagePath)
    {
        texture = new Texture(Gdx.files.internal(bgImagePath));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

//        bgImageRegion = new TextureRegion(texture);
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
        batch.draw(texture, 0, 0, screenWidth, screenHeight);
    }

    public void myDraw2(Batch batch, int width, int height, int x, int y)
    {
        batch.draw(texture, x, y, width, height);
    }
}
