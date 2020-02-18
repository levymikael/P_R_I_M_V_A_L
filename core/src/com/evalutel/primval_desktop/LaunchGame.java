package com.evalutel.primval_desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Scaling;


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



	@Override
	public void create() {

//		this.setScreen(new ScreenOnglet());
//		this.setScreen(new ScreenEx1_1());
		this.setScreen(new ScreenEx1_2());
		//this.setScreen();

	}


}

