package com.evalutel.primval_desktop.ui_tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;

public class MyTextButton extends TextButton implements MyDrawInterface, MyCorrectionAndPauseInterface
{

//    static int screenHeight = Gdx.graphics.getHeight();

    private boolean isPaused = false;


    public MyTextButton(String text, String backgroundImagePathUp, String backgroundImagePathDown, float size, String font, int fontSize)
    {
        super(text, getStyle(backgroundImagePathUp, backgroundImagePathDown, font, fontSize));

        this.setSize(size, size);
//        this.setPosition(getWidth() / 2, getHeight() / 2);

    }


    public MyTextButton(String text, float size, TextButtonStyle style)
    {
        super(text, style);

        this.setSize(size, size);

    }

    public MyTextButton(String text, String backgroundImagePathUp, float size, String font, int fontSize)
    {
        super(text, getStyle2(backgroundImagePathUp, font, fontSize));

        this.setSize(size, size);
    }

    private static TextButtonStyle getStyle(String imagePathUp, String imagePathDown, String fontPath, int fontSize)
    {
        TextButtonStyle buttonStyle = new TextButtonStyle();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        BitmapFont bitmapFont = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();

        buttonStyle.font = bitmapFont;

        Texture textureUp = new Texture(Gdx.files.internal(imagePathUp));
        textureUp.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        buttonStyle.up = new SpriteDrawable(new Sprite(textureUp));

        Texture textureDown = new Texture(Gdx.files.internal(imagePathDown));
        textureDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        buttonStyle.down = new SpriteDrawable(new Sprite(textureDown));

        return buttonStyle;
    }

    private static TextButtonStyle getStyle2(String imagePathUp, String fontPath, int fontSize)
    {
        TextButtonStyle buttonStyle = new TextButtonStyle();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        BitmapFont bitmapFont = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();

        buttonStyle.font = bitmapFont;

        Texture textureUp = new Texture(Gdx.files.internal(imagePathUp));
        textureUp.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        buttonStyle.up = new SpriteDrawable(new Sprite(textureUp));

        return buttonStyle;
    }


    @Override
    public void myDraw(Batch batch)
    {

//        TextureRegion textureRegion = new TextureRegion(new Texture(backgroundImagePathUp));
//
//        batch.draw(textureRegion, 0, 0, getWidth(), getHeight());

    }

    @Override
    public void myCorrectionStart()
    {

    }

    @Override
    public void myCorrectionStop()
    {

    }

    @Override
    public void myPause()
    {
        isPaused = true;
    }

    @Override
    public void myResume()
    {
        isPaused = false;
    }

    @Override
    public boolean isPause()
    {
        return isPaused;
    }

}
