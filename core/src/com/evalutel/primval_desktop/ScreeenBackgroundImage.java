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


    public TextureRegion ScreeenBackgroundImage(String bgImagePath)
    {
        bgImageRegion = new TextureRegion(new Texture(Gdx.files.internal(bgImagePath)));

        return bgImageRegion;
    }

    public TextureRegion SetBackGroundSize(ScreeenBackgroundImage screeenBackgroundImage, int width, int height)
    {
        bgImageRegion.setRegionWidth(width);
        bgImageRegion.setRegionHeight(height);

        return bgImageRegion;

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

    public void myDraw2(Batch batch, int width, int height)
    {
        batch.draw(bgImageRegion, 0, 0, width, height);
    }
}
