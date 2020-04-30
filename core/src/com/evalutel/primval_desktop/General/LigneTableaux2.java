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


public class LigneTableaux2
{
    static MyDataBase db;

    static String notes2Implement;

    public static Table getLigne(MyTextButton button, String ongletTitre, Texture texture, String borderColor, int chapitre, int onglet, DatabaseDesktop dataBase)
    {
        Table container = new Table();
        Table table = new Table();
//        Table tablebord1 = new Table();
//        Table tablebord2 = new Table();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Pixmap pmRed = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmRed.setColor(Color.RED);
        pmRed.fill();

        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlue.setColor(Color.BLUE);
        pmBlue.fill();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyleOnglet = new Label.LabelStyle();
        labelStyleOnglet.font = bitmapFont;

        db = new MyDataBase(dataBase);

        long durationPerExercice = 0;
        durationPerExercice = db.getMaxDureePageForIdProfil(chapitre, onglet);

        String duration = MillisToDuration(durationPerExercice);

        Label.LabelStyle labelStyleDuration = new Label.LabelStyle();
        labelStyleDuration.fontColor = Color.GREEN;
        labelStyleDuration.font = bitmapFont;

        Label labelDuration = new Label(duration, labelStyleDuration);
        labelDuration.setWidth(screenWidth / 20);
        labelDuration.setWrap(true);


        if (borderColor == "red")
        {
//            tablebord2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmRed))));
//            tablebord1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmRed))));
            labelStyleOnglet.fontColor = Color.RED;
        }
        else if ((borderColor == "blue"))
        {
//            tablebord1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue))));
//            tablebord2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue))));
            labelStyleOnglet.fontColor = Color.BLUE;
        }

        Label labelOnglet = new Label(ongletTitre, labelStyleOnglet);


        int highestNote, noteMaxPerExercice, notePossiblePerExercice;

        highestNote = db.getHighestNote(chapitre, onglet);

        noteMaxPerExercice = db.getMaxNotePerExercice(chapitre, onglet, 0);

        notePossiblePerExercice = db.getMaxNotePossiblePerExercice(chapitre, onglet, 0);

        notes2Implement = highestNote + " / " + notePossiblePerExercice + " / " + noteMaxPerExercice;

        Label.LabelStyle labelStyleNotes = new Label.LabelStyle();
        labelStyleNotes.fontColor = Color.ORANGE;
        labelStyleNotes.font = bitmapFont;

        Label labelNotes = new Label(notes2Implement, labelStyleNotes);
        labelNotes.setWidth(screenWidth / 20);
        labelNotes.setWrap(true);

        table.setWidth(screenWidth);
        table.setHeight(screenHeight / 30);

        labelOnglet.setWidth(screenWidth / 4);

//        table.debug();
//        labelOnglet.debug();
//        labelDuration.debug();

        table.add().width(screenWidth / 50);
        table.add(button).height(screenHeight / 40).width(screenWidth / 13).align(Align.center);
        table.add(button).align(Align.center);
        table.add().width(screenWidth / 25);
        table.add(labelOnglet).align(Align.center).width((float) (screenWidth * 0.5));

        if (texture == null)
        {
            table.add().width(screenWidth / 70).height(screenHeight / 40).align(Align.center);
        }
        else
        {
            table.add(new Image(texture)).width(screenWidth / 70).height(screenHeight / 40).align(Align.center);
        }
        table.add().width(screenWidth / 20);
        table.add(labelDuration).align(Align.right).width(screenWidth / 12).align(Align.center);
        table.add().width(screenWidth / 25);
        if (borderColor == "red")
        {
            table.add().align(Align.center).width(screenWidth / 12).align(Align.center);
            table.add().width(screenWidth / 20);
        }
        else
        {
            table.add(labelNotes).align(Align.center).width(screenWidth / 12).align(Align.center);
            table.add().width(screenWidth / 20);
        }


//        table.add().width(screenWidth - 200 - (2 * screenWidth / 3) - 30 - 200 - 400);


        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));


//        container.add(tablebord1).width(screenWidth).height(2);
//        container.row();
        container.add(table).height(screenHeight / 15);
        container.row();
//        container.add(tablebord2).width(screenWidth).height(2);

//        table.setTouchable(Touchable.enabled);
////        table.setFillParent(true);
//        container.setTouchable(Touchable.enabled);


        return container;
    }


    public static String MillisToDuration(long seconds)
    {
        int sec = (int) seconds % 60;
        int min = (((int) seconds) / 60) % 60;
        int hours = (((int) seconds) / 60) / 60;

        String hms = String.format("%02d:%02d:%02d", hours, min, sec);

        System.out.println(hms);
        return hms;
    }
}

