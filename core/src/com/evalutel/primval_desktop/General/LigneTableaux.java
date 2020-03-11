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
import com.evalutel.primval_desktop.ui_tools.MyTextButton;


public class LigneTableaux
{
    public static Table getLigne(MyTextButton button, Label label, Texture texture)
    {
        Table table = new Table();
        int screenWidth = Gdx.graphics.getWidth();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.BLACK;

        Label labelDuration = new Label("Duration to implement", labelStyle);
        labelDuration.setWidth(50);
        labelDuration.setWrap(true);

        Label labelNotes = new Label("Notes to implement", labelStyle);
        labelNotes.setWidth(50);
        labelNotes.setWrap(true);

        table.setWidth(150);
        table.setHeight(150);
        button.setHeight(70);
        button.setWidth(70);
        table.add(button);
        table.add().width(200);
        table.add(label).align(Align.center).width(screenWidth / 3);
        table.add(new Image(texture)).width(30).height(30).align(Align.center);
        table.add().width(200);
        table.add(labelDuration).align(Align.center).width(screenWidth / 6);
        table.add().width(200);
        table.add(labelNotes).align(Align.center).width(screenWidth / 6);

        table.setTouchable(Touchable.enabled);


        return table;
    }
}
