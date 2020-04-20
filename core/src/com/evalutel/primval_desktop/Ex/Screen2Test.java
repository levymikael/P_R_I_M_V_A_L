package com.evalutel.primval_desktop.Ex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

public class Screen2Test implements Screen {

    private Stage stage;
    SpriteBatch batch;
    TextureRegion[] animationFrames;
    Animation animation;
    float elapsedTime;
    MyTextButton myTextButtonTest;


    public Screen2Test()
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

//        myTextButtonTest = new MyTextButton("test dans le bouton", "Images/calculetteKeys/clavier_egal.png", "Images/calculetteKeys/clavier_multiplication.png", 200, 200,"font/comic_sans_ms.ttf");

//        myTextButtonTest.getStyle().fontColor = Color.BLACK;

//        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
//        buttonStyle.font = new BitmapFont();
//        buttonTest = new TextButton("5", buttonStyle);
//        buttonTest.setText("21");
//        buttonTest.setSize(400, 200);
//
//
//        Texture texture   = new Texture(Gdx.files.internal("calculetteKeys/clavier_fond_bis.png"));
//        Sprite sprite = new Sprite(texture);
//        //sprite.setSize(width, height);
//        SpriteDrawable spriteDrawable = new SpriteDrawable(sprite);
//
//        buttonStyle.up = spriteDrawable;
//        buttonStyle.down = spriteDrawable;

        //buttonTest.setBackground(spriteDrawable);

        Stage stage = new Stage();
        Gdx.input.setInputProcessor(stage);

//        Stage stage2 = new Stage();
        //Gdx.input.setInputProcessor(stage2);

//       stage.addActor(myTextButtonTest);

//        myTextButtonTest.addListener(new ClickListener() {
//
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//
//                int ok = 5;
//                ok++;
//
////                game.setScreen(new Screen3Test());
//
//            }
//        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 1, 1, 1); // center
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//

        Object object = animation.getKeyFrame(elapsedTime, true);


        TextureRegion textureRegion = (TextureRegion)object;

        //batch.draw(textureRegion, 120, 70, 80, 80);

        float positionX = elapsedTime*20.0f;
        float positionY = elapsedTime*20.0f;

        batch.draw(textureRegion, positionX, positionY, 100, 100);
//        myTextButtonTest.draw(batch, 1);


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
