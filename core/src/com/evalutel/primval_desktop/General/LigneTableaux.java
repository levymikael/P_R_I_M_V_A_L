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
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;


public class LigneTableaux
{

    static MyDataBase db;

    public static Table getLigne(MyTextButton button, Label label, Texture texture, String borderColor, int chapitre, int onglet, DatabaseDesktop dataBase)
    {


        Table container = new Table();
        Table table = new Table();
        Table tablebord1 = new Table();
        Table tablebord2 = new Table();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.BLACK;

        db = new MyDataBase(dataBase);

        int durationPerExercice = 0;
        durationPerExercice = db.getMaxDureePageForIdProfil(chapitre, onglet)/1000;

        int ok = 5;
        ok++;


        Label labelDuration = new Label(Integer.toString(durationPerExercice), labelStyle);
        labelDuration.setWidth(50);
        labelDuration.setWrap(true);

        Label labelNotes = new Label("Notes to implement", labelStyle);
        labelNotes.setWidth(50);
        labelNotes.setWrap(true);

        table.setWidth(screenWidth);
        table.setHeight(screenHeight / 10);

        label.setWidth(screenWidth / 4);

        table.add().width(100);
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
        table.add().width(screenWidth - 200 - (2 * screenWidth / 3) - 30 - 200 - 400);

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
            tablebord2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmRed))));
            tablebord1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmRed))));
        }
        else if ((borderColor == "blue"))
        {
            tablebord1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue))));
            tablebord2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue))));
        }


        container.add(tablebord1).width(screenWidth).height(2);
        container.row();
        container.add(table).height(screenHeight / 15);
        container.row();
        container.add(tablebord2).width(screenWidth).height(2);

        table.setTouchable(Touchable.enabled);
//        table.setFillParent(true);
        container.setTouchable(Touchable.enabled);


        return container;
    }
}
