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
import com.evalutel.primval_desktop.ui_tools.AppSingleton;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;


public class LigneTableauxResultsChapitre
{
    static MyDataBase db;

    static String notes2Implement;

    static String duration = "";

    static int highestNote, noteMaxPerExercice, notePossiblePerExercice;

    static long durationPerChapter, durationPerExercice = 0;


    public static Table getLigne(MyTextButton button, String ongletTitre, int chapitre, int[] arrayBareme, BitmapFont bitmapFontArial)
    {
        Table container = new Table();
        Table table = new Table();
        Table durationTable = new Table();
        Table noteTable = new Table();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

//        Pixmap pixmapBg = new Pixmap(1, 1, Pixmap.Format.RGB565);
//        pixmapBg.setColor(Color.ORANGE);
//        pixmapBg.fill();

        float fontSize = 1.15f;
        float buttonPadding = MyConstants.SCREENWIDTH / 80f;
        int textureSize = MyConstants.SCREENWIDTH / 60;


        Label.LabelStyle labelStyleOnglet = new Label.LabelStyle();
        labelStyleOnglet.font = bitmapFontArial;
        labelStyleOnglet.fontColor = Color.WHITE;
        Label labelOnglet = new Label(ongletTitre, labelStyleOnglet);
        labelOnglet.setWidth(MyConstants.SCREENWIDTH / 4f);
        labelOnglet.setFontScale(fontSize);

        AppSingleton appSingleton = AppSingleton.getInstance();


        db = appSingleton.myDataBase;

        durationPerChapter = db.getTotalDureePageForIdProfilByChapter(chapitre);

        duration = MillisToDuration(durationPerChapter);

        Label.LabelStyle labelStyleDuration = new Label.LabelStyle();
        labelStyleDuration.fontColor = MyConstants.greenresultat;
        labelStyleDuration.font = bitmapFontArial;
        Label labelDuration = new Label(duration, labelStyleDuration);
        labelDuration.setFontScale(fontSize);
        labelDuration.setWidth(MyConstants.SCREENWIDTH / 18f);

        notes2Implement = db.getTotalNotePageForIdProfil(chapitre);

        int totalNotesPossibles = 0;

        for (int i = 0; i < arrayBareme.length; i++)
        {
            totalNotesPossibles += arrayBareme[i];
        }

        String newTotalNotes = notes2Implement.substring(0, notes2Implement.length() - 2) + totalNotesPossibles;

        Label.LabelStyle labelStyleNotes = new Label.LabelStyle();
        labelStyleNotes.font = bitmapFontArial;
        labelStyleNotes.fontColor = MyConstants.redresultat;
        Label labelNotes = new Label(newTotalNotes, labelStyleNotes);
        labelNotes.setFontScale(fontSize);
        labelNotes.setWidth(MyConstants.SCREENWIDTH / 18f);

        Texture orangeBG = new Texture(Gdx.files.internal("Images/Sommaire/Bandeau orange.png"));
        orangeBG.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegionDrawable tableBG = new TextureRegionDrawable(new TextureRegion(orangeBG));
        table.setBackground(tableBG);

        durationTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));
        durationTable.add(labelDuration).height(screenHeight / 25f)/*.width(screenWidth / 10).padLeft(screenWidth / 40).align(Align.center)*/;

        noteTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));
        noteTable.add(labelNotes).height(screenHeight / 25f)/*.width(screenWidth / 10f).padLeft(screenWidth / 20f).align(Align.center)*/;

        Texture triangleDown = new Texture(Gdx.files.internal("Images/Sommaire/sort_down.png"));
        triangleDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

float labelWidth = screenWidth / 9f;

        table.add(new Image(triangleDown)).height(button.getWidth()).width(button.getWidth()).padLeft(screenWidth / 75f).padTop(screenHeight / 100f).padBottom(screenHeight / 100f);
        table.add(button).height(button.getHeight()).width(button.getWidth()).padLeft(screenWidth / 80f);
        table.add(labelOnglet).width((MyConstants.SCREENWIDTH * .62f)).padLeft(screenWidth / 45f)/*.padRight(screenWidth / 25f)*/;
        table.add(durationTable).width(labelWidth).padRight(screenWidth / 40f);
        table.add(noteTable).width(labelWidth).padRight(screenWidth / 40f);


        float lineHeight = MyConstants.SCREENHEIGHT / 20f;
        float buttonSize = lineHeight / 10;
        int fontSizeOnglet = MyConstants.SCREENHEIGHT / 60;
        float paddingInterOnglets = -MyConstants.SCREENHEIGHT / 50f;

        container.add(table).height(MyConstants.SCREENHEIGHT / 20f).width(screenWidth);


        return container;
    }


    private static String MillisToDuration(long seconds)
    {
        int sec = (int) seconds % 60;
        int min = (((int) seconds) / 60) % 60;
        int hours = (((int) seconds) / 60) / 60;

        String hms = String.format("%02d:%02d:%02d", hours, min, sec);

        System.out.println(hms);
        return hms;
    }
}

