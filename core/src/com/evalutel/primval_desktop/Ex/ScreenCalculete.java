package com.evalutel.primval_desktop.Ex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ScreenCalculete implements Screen {

    SpriteBatch batch;
    Table panel;
    Table image2;
    public ScreenCalculete()
    {

        Texture imgAux = new Texture("oiseau/oiseau1_1_00000" + 1 + ".png");
        TextureRegion textureRegionAux = new TextureRegion(imgAux);
        batch = new SpriteBatch();
        Drawable drawableTest = new TextureRegionDrawable(textureRegionAux);
        panel = new Table();
        panel.setBackground(drawableTest);
        panel.setWidth(400);
        panel.setHeight(100);



        Texture imgAux2 = new Texture("oiseau/oiseau1_1_00000" + 4 + ".png");
        TextureRegion textureRegionAux2 = new TextureRegion(imgAux2);
        image2 =  new Table();
        image2.setBackground(new TextureRegionDrawable(textureRegionAux2));
        image2.setBounds(50, 50, 50, 50);
        //image2.setHeight(50);
        //panel.add(image2);
        //panel.add(panel).top().padTop(35.0f).colspan(4);


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1); // center
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        panel.draw(batch, 1);
        image2.draw(batch, 1);

        batch.end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }


    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
