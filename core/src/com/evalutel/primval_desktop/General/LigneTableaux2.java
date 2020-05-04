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

    static String duration = "";

    static int highestNote, noteMaxPerExercice, notePossiblePerExercice;

    static long durationPerChapter, durationPerExercice = 0;


    public static Table getLigne(MyTextButton button, String ongletTitre, Texture texture, String borderColor, int chapitre, int onglet, DatabaseDesktop dataBase)
    {
        Table container = new Table();
        Table table = new Table();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Pixmap pmRed = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmRed.setColor(new Color(167.0f / 255.0f, 44.0f / 255.0f, 23.0f / 255.0f, 1));
        pmRed.fill();

        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlue.setColor(new Color(111.0f / 255.0f, 148.0f / 255.0f, 212.0f / 255.0f, 1));
        pmBlue.fill();

        Pixmap bgOrange = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgOrange.setColor(Color.ORANGE);
        bgOrange.fill();
        TextureRegionDrawable orangeBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgOrange)));

        Pixmap pixmapBg = new Pixmap(1, 1, Pixmap.Format.RGB565);


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = screenWidth / 90;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyleOnglet = new Label.LabelStyle();
        labelStyleOnglet.font = bitmapFont;

        db = new MyDataBase(dataBase);

        durationPerExercice = db.getMaxDureePageForIdProfil(chapitre, onglet);

        durationPerChapter = db.getTotalDureePageForIdProfil(chapitre);


        if (borderColor == "white")
        {
            duration = MillisToDuration(durationPerChapter);

            pixmapBg.setColor(Color.ORANGE);
        }
        else
        {
            duration = MillisToDuration(durationPerExercice);

        }


        Label.LabelStyle labelStyleDuration = new Label.LabelStyle();
        labelStyleDuration.fontColor = Color.GREEN;
        labelStyleDuration.font = bitmapFont;

        Label labelDuration = new Label(duration, labelStyleDuration);
        labelDuration.setWidth(screenWidth / 20);
        labelDuration.setWrap(true);

//
        Label labelOnglet = new Label(ongletTitre, labelStyleOnglet);

        Label.LabelStyle labelStyleNotes = new Label.LabelStyle();
        labelStyleNotes.font = bitmapFont;


        if (borderColor == "red")
        {
            labelStyleOnglet.fontColor = new Color(167.0f / 255.0f, 44.0f / 255.0f, 23.0f / 255.0f, 1);
            pixmapBg.setColor(Color.WHITE);
            pixmapBg.fill();
        }
        else if (borderColor == "blue")
        {
            labelStyleOnglet.fontColor = new Color(72.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1);
            pixmapBg.setColor(Color.WHITE);
            pixmapBg.fill();

            highestNote = db.getHighestNote(chapitre, onglet);
            noteMaxPerExercice = db.getMaxNotePerExercice(chapitre, onglet, 0);
            notePossiblePerExercice = db.getMaxNotePossiblePerExercice(chapitre, onglet, 0);

            notes2Implement = highestNote + " / " + notePossiblePerExercice + " / " + noteMaxPerExercice;

            labelStyleNotes.fontColor = Color.ORANGE;
        }
        else if (borderColor == "white")
        {
            labelStyleOnglet.fontColor = Color.WHITE;

            labelStyleNotes.font = bitmapFont;
            labelStyleNotes.fontColor = Color.ORANGE;

            int notePossiblePerChapter = db.getMaxNotePossiblePerChapter(chapitre, 0);
            highestNote = db.getHighestNotePerChapter(chapitre);
            int noteMaxPerChapter = db.getMaxNotePerChapter(chapitre, 0);

            notes2Implement = highestNote + " / " + notePossiblePerChapter + " / " + noteMaxPerChapter;
        }

        pixmapBg.fill();
        TextureRegionDrawable tableBG = new TextureRegionDrawable(new TextureRegion(new Texture(pixmapBg)));

        table.setBackground(tableBG);


        Label labelNotes = new Label(notes2Implement, labelStyleNotes);
        labelNotes.setWidth(screenWidth / 20);
        labelNotes.setWrap(true);

        table.setWidth(screenWidth);
        table.setHeight(screenHeight / 40);

        labelOnglet.setWidth(screenWidth / 10);
        table.add().width(screenWidth / 15);
        table.add(button).align(Align.center);
        table.add().width(screenWidth / 15);
        table.add(labelOnglet).align(Align.center).width((float) (screenWidth * 0.5));

        Table whiteTable = new Table();
        Table whiteTable2 = new Table();
        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();
        whiteTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));
        whiteTable2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));

        if (texture == null)
        {
            table.add().width(screenWidth / 70).height(screenHeight / 50).align(Align.center);
        }
        else
        {
            table.add(new Image(texture)).width(screenWidth / 70).height(screenHeight / 50).align(Align.center);
        }
        table.add().width(screenWidth / 20);
        table.add().align(Align.center).width(screenWidth / 17);
        if (borderColor == "red")
        {
            table.add(labelDuration).align(Align.center).width(screenWidth / 9);
            table.add().width(screenWidth / 25);

        }
        else
        {
            whiteTable.add(labelDuration).align(Align.center).width(screenWidth / 12);
            whiteTable2.add(labelNotes).align(Align.center).width(screenWidth / 12);

            table.add(whiteTable).width(screenWidth / 15);

            table.add().width(screenWidth / 25);
            table.add(whiteTable2).width(screenWidth / 15);
        }

        table.add().width(screenWidth / 10);

        container.add(table).height(screenHeight / 20);
        container.row();

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

