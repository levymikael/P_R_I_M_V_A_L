package com.evalutel.primval_desktop.General;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;


public class LigneTableaux
{
    public static Table getLigne(MyTextButton button, Label label, Texture texture, String borderColor)
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

//        table.setWidth(150);
//        table.setHeight(150);
        table.setWidth(screenWidth);

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

        Table container = new Table();

        container.add(table);
        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));

        Pixmap pmRed = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmRed.setColor(Color.RED);
        pmRed.fill();

        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlue.setColor(Color.BLUE);
        pmBlue.fill();

        if (borderColor == "red")
        {
            container.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmRed))));
        }
        else if ((borderColor == "blue"))
        {
            container.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue))));
        }
        table.setTouchable(Touchable.enabled);
//        table.setFillParent(true);
        container.setTouchable(Touchable.enabled);



        return container;
    }
}
