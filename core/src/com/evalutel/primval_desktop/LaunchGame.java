package com.evalutel.primval_desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.onglets.chapitre1.Screen_Sommaire_General;


public class LaunchGame extends Game implements ApplicationListener
{
    SpriteBatch batch;
    Texture img;
    TextureRegion[] animationFrames;
    Animation animation;
    float elapsedTime;
    private TextButton buttonTest;
    private Stage stage;
    private Skin skin;
    BitmapFont font;
    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

    private Viewport viewport;
    private Camera camera;

    @Override
    public void create()
    {
        int screenHeight = MyConstants.SCREENHEIGHT;
        int screenWidth = MyConstants.SCREENWIDTH;


        float ratioTest = 1024.0f / 768.0f;

        float currentRatio = (float) screenWidth / (float) screenHeight;

        if (currentRatio > ratioTest)
        {
            screenWidth = (int) (screenHeight * 1024.0f / 768.0f);
        }

        Camera camera = new OrthographicCamera();
        //((OrthographicCamera) camera).setToOrtho(false, screenWidth, screenHeight);

        Gdx.app.log("screenheight, screenWidth", screenHeight + "/" + screenWidth);

        //camera = new PerspectiveCamera();
        //viewport = new FitViewport(800, 480, camera);

        this.setScreen(new Screen_Sommaire_General(this, new DatabaseDesktop()));
    }


    @Override
    public void pause()
    {
        super.pause();
    }
}

