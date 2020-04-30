package com.evalutel.primval_desktop.ui_tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class MyTextButton extends TextButton

{

    public MyTextButton(String text, String backgroundImagePathUp, String backgroundImagePathDown, float size, String font, int fontSize)
    {
        super(text, getStyle(backgroundImagePathUp, backgroundImagePathDown, font, fontSize));

        this.setSize(size, size);
        this.setPosition(getWidth() / 2, getHeight() / 2);
    }

    private static TextButtonStyle getStyle(String imagePathUp, String imagePathDown, String fontPath, int fontSize)
    {
        TextButtonStyle buttonStyle = new TextButtonStyle();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        BitmapFont bitmapFont = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();

        buttonStyle.font = bitmapFont;

        Texture texture = new Texture(Gdx.files.internal(imagePathUp));
        Sprite sprite = new Sprite(texture);
        SpriteDrawable spriteDrawable = new SpriteDrawable(sprite);

        buttonStyle.up = spriteDrawable;

        texture = new Texture(Gdx.files.internal(imagePathDown));
        sprite = new Sprite(texture);
        //sprite.setSize(width, height);
        spriteDrawable = new SpriteDrawable(sprite);


        buttonStyle.down = spriteDrawable;


        return buttonStyle;
    }
}
