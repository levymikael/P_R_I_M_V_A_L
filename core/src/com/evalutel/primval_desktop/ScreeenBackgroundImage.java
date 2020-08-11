package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;

public class ScreeenBackgroundImage extends TextureRegion implements MyDrawInterface
{

    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();
    protected boolean isVisible = true;
    protected boolean isActive = true;

    Texture texture;


    public ScreeenBackgroundImage(String bgImagePath)
    {
        texture = new Texture(Gdx.files.internal(bgImagePath));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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

    public void myDraw2(Batch batch, float width, float height, float x, float y)
    {
        batch.draw(texture, x, y, width, height);
    }
}
