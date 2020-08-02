package com.evalutel.primval_desktop.General;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;


public class LigneTableauxResultsChapitre
{
    static MyDataBase db;

    static String notes2Implement;

    static String duration = "";

    static int highestNote, noteMaxPerExercice, notePossiblePerExercice;

    static long durationPerChapter, durationPerExercice = 0;

    static BitmapFont bitmapFontArial;


    public static Table getLigne(MyTextButton button, String ongletTitre, /*Texture texture,*/ /*String borderColor,*/ int chapitre, int totalNotesPossibles, DatabaseDesktop dataBase)
    {
        Table container = new Table();
        Table table = new Table();
        Table durationTable = new Table();
        Table noteTable = new Table();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Pixmap bgOrange = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgOrange.setColor(Color.ORANGE);
        bgOrange.fill();

        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        Pixmap pixmapBg = new Pixmap(1, 1, Pixmap.Format.RGB565);

        float fontSize = 1.4f;
        float buttonPadding = MyConstants.SCREENWIDTH / 80f;
        int textureSize = MyConstants.SCREENWIDTH / 60;


        FreeTypeFontGenerator fontArial = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENWIDTH / 60;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        bitmapFontArial = fontArial.generateFont(parameter);
        fontArial.dispose();

        Label.LabelStyle labelStyleOnglet = new Label.LabelStyle();
        labelStyleOnglet.font = bitmapFontArial;
        labelStyleOnglet.fontColor = Color.WHITE;
        Label labelOnglet = new Label(ongletTitre, labelStyleOnglet);
        labelOnglet.setWidth(MyConstants.SCREENWIDTH / 4f);

        db = new MyDataBase(dataBase);

        durationPerChapter = db.getTotalDureePageForIdProfilByChapter(chapitre);

        duration = MillisToDuration(durationPerChapter);
        pixmapBg.setColor(Color.ORANGE);

        Label.LabelStyle labelStyleDuration = new Label.LabelStyle();
        labelStyleDuration.fontColor = Color.OLIVE;
        labelStyleDuration.font = bitmapFontArial;
        Label labelDuration = new Label(duration, labelStyleDuration);
        labelDuration.setFontScale(fontSize);
        labelDuration.setWidth(MyConstants.SCREENWIDTH / 18f);

        notes2Implement = db.getTotalNotePageForIdProfil(chapitre);

        String newTotalNotes = notes2Implement.substring(0, notes2Implement.length() - 1) + totalNotesPossibles;

        Label.LabelStyle labelStyleNotes = new Label.LabelStyle();
        labelStyleNotes.font = bitmapFontArial;
        labelStyleNotes.fontColor = Color.ORANGE;
        Label labelNotes = new Label(newTotalNotes, labelStyleNotes);
        labelDuration.setFontScale(fontSize);
        labelNotes.setWidth(MyConstants.SCREENWIDTH / 18f);

        pixmapBg.setColor(Color.ORANGE);
        pixmapBg.fill();
        TextureRegionDrawable tableBG = new TextureRegionDrawable(new TextureRegion(new Texture(pixmapBg)));
        table.setBackground(tableBG);


        durationTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));
        durationTable.add(labelDuration).height(screenHeight / 25f)/*.width(screenWidth / 10).padLeft(screenWidth / 40).align(Align.center)*/;

        noteTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));
        noteTable.add(labelNotes).height(screenHeight / 25f)/*.width(screenWidth / 10f).padLeft(screenWidth / 20f).align(Align.center)*/;

        table.add().width(screenWidth / 70f);
        table.add(button).height(button.getHeight()).width(button.getWidth());
        table.add().width(screenWidth / 70f);
        table.add(labelOnglet).width((MyConstants.SCREENWIDTH * 0.6f)).padRight(screenWidth / 25f);
        table.add(durationTable).width(screenWidth / 9f).padRight(screenWidth / 40f);
        table.add(noteTable).width(screenWidth / 8.5f);


        float lineHeight = MyConstants.SCREENHEIGHT / 20;
        float buttonSize = lineHeight / 10;
        int fontSizeOnglet = MyConstants.SCREENHEIGHT / 60;
        float paddingInterOnglets = -MyConstants.SCREENHEIGHT / 50;

        container.add(table).height(MyConstants.SCREENHEIGHT / 20f).width(screenWidth);


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

