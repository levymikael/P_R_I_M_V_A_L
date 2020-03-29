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

import java.util.concurrent.TimeUnit;


public class LigneTableaux
{
    static MyDataBase db;

    static String notes2Implement;

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

        long durationPerExercice = 0;
        durationPerExercice = db.getMaxDureePageForIdProfil(chapitre, onglet);

        String duration = MillisToDuration(durationPerExercice);

        Label labelDuration = new Label(duration, labelStyle);
        labelDuration.setWidth(50);
        labelDuration.setWrap(true);

        int highestNote = 0;

        highestNote = db.getHighestNote(chapitre, onglet);


        int noteMaxPerExercice = 0;
        noteMaxPerExercice = db.getMaxNotePerExercice(chapitre, onglet, 0);

        int notePossiblePerExercice = 0;
        notePossiblePerExercice = db.getMaxNotePossiblePerExercice(chapitre, onglet, 0);


        notes2Implement = highestNote + " / " + notePossiblePerExercice + " / " + noteMaxPerExercice;


        Label labelNotes = new Label(notes2Implement, labelStyle);
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


    public static String MillisToDuration(long seconds)
    {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.SECONDS.toHours(seconds),
                TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toMinutes(TimeUnit.SECONDS.toHours(seconds)),
                TimeUnit.SECONDS.toSeconds(TimeUnit.SECONDS.toMinutes(seconds)));
        System.out.println(hms);
        return hms;
    }
}

