package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.graphics.g2d.Sprite;



public class TextFieldAndTextFieldStyle extends TextField {

    TextField textField;
    SpriteDrawable backGroundPath;
    String text;

    public TextFieldAndTextFieldStyle (String text, TextFieldStyle textFieldStyle, SpriteDrawable backGroundPath){

        super(text, textFieldStyle);


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.padLeft = 20;
        parameter.padRight = 30;
        parameter.padBottom = 50;

        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Color fontColor = new Color(Color.BLACK);

        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = bitmapFont;
        textFieldStyle.fontColor = fontColor;

        TextField textField = new TextField(text,textFieldStyle);

        Texture textureBackGround =  new Texture(Gdx.files.internal("" ));
        Sprite spriteBackGround = new Sprite(textureBackGround);
        textFieldStyle.background = new SpriteDrawable(spriteBackGround);

    }



}
