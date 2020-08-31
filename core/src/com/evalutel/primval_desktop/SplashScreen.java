package com.evalutel.primval_desktop;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen
{
    private SpriteBatch batch;
    private Texture ttrSplash;

    public SplashScreen()
    {
        super();
        batch = new SpriteBatch();
        ttrSplash = new Texture("Images/Fond 01 Format 1024x768.jpg");
        ttrSplash.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        float largeurFond = 1024f;
        float hauteurFond = 768f;

        //backgroundScreen = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        float largeurFondNew = Gdx.graphics.getWidth();
        float hauteurFondNew = largeurFondNew * hauteurFond / largeurFond;

        float positionFondX = 0;
        float positionFondY = -(hauteurFondNew - Gdx.graphics.getHeight()) / 2f;

        if ((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight() < largeurFond / hauteurFond)
        {
            hauteurFondNew = Gdx.graphics.getHeight();
            largeurFondNew = hauteurFondNew * largeurFond / hauteurFond;


            positionFondY = 0;
            positionFondX = -(largeurFondNew - Gdx.graphics.getWidth()) / 2f;
        }


        batch.draw(ttrSplash, positionFondX, positionFondY, largeurFondNew, hauteurFondNew);
        batch.end();
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void show()
    {
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void dispose()
    {
        ttrSplash.dispose();
        batch.dispose();
    }
}
