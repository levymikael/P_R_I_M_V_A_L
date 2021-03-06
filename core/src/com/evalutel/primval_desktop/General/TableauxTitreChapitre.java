package com.evalutel.primval_desktop.General;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class TableauxTitreChapitre
{
    static FreeTypeFontGenerator generator;

    public static Table getLigne(Label label, Texture texture)
    {
        Table table = new Table();
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/ComicSansMSBold.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = screenWidth / 20;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.BLACK;

        if (texture == null)
        {
            table.add().width(screenWidth / 12).height(screenHeight / 30).align(Align.center);
        }
        else
        {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            table.add(new Image(texture)).width(screenWidth / 20).height(screenWidth / 20).align(Align.center);
            table.add().width(50).height(screenHeight / 8);
        }
        label.setFontScale(2);
        table.add(label).align(Align.center).width(screenWidth / 3);

        return table;
    }
}
