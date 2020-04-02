package com.evalutel.primval_desktop;

import com.badlogic.gdx.ApplicationListener;
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
import com.evalutel.primval_desktop.onglets.chapitre1.Screen_Chapitre1;


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
    private OrthographicCamera camera;

    @Override
    public void create()
    {

//		this.setScreen(new ScreenOnglet());
//		this.setScreen(new ScreenEx1_1());
//		this.setScreen(new ScreenEx1_2());
        this.setScreen(new Screen_Chapitre1(this, new DatabaseDesktop()));

    }


    @Override
    public void pause()
    {
        super.pause();
    }
}

