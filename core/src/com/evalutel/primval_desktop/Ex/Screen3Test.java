package com.evalutel.primval_desktop.Ex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Screen3Test implements Screen {

    private Stage stage;
    SpriteBatch batch;
    Texture img;
    TextureRegion[] animationFrames;
    Animation animation;
    float elapsedTime;
    private TextButton buttonTest;
    private int direction = 1;
    float positionX;


    public Screen3Test()
    {
        batch = new SpriteBatch();

        animationFrames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            Texture imgAux = new Texture("Images/oiseau/oiseau1_1_00000" + i + ".png");
            TextureRegion textureRegionAux = new TextureRegion(imgAux);
            animationFrames[i] = textureRegionAux;
        }

        // fps vitesse de l'animation --> utiliser des float
        animation = new Animation(1f / 6f, (Object[])animationFrames);

        //Section bouton

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonTest = new TextButton("okokok", buttonStyle);

        buttonTest.setSize(200, 200);

        Stage stage = new Stage();
        Gdx.input.setInputProcessor(stage);



        stage.addActor(buttonTest);


        buttonTest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                int ok = 5;
                ok++;

                direction = -direction;

            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        int widthScreen = Gdx.graphics.getWidth();

        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 1, 1, 1); // center
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        Object object = animation.getKeyFrame(elapsedTime, true);


        TextureRegion textureRegion = (TextureRegion)object;

        //batch.draw(textureRegion, 120, 70, 80, 80);

        int regioX = textureRegion.getRegionX();

        positionX = positionX + direction*10;
        float positionY = elapsedTime*20.0f;

        batch.draw(textureRegion, positionX, 0, 100, 100);
        buttonTest.draw(batch, 1);


        //batch.draw((TextureRegion)animation.getKeyFrame(elapsedTime, true),0, 0);
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
