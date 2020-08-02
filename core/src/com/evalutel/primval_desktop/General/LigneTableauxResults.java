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


public class LigneTableauxResults
{
    static MyDataBase db;

    static String notes2Implement;

    static String duration = "";

    static int highestNote, noteMaxPerExercice, notePossiblePerExercice;

    static long durationPerChapter, durationPerExercice = 0;

    static BitmapFont bitmapFontArial;


    public static Table getLigne(MyTextButton button, String ongletTitre, Texture texture, String borderColor, int chapitre, int onglet, DatabaseDesktop dataBase)
    {
        Table container = new Table();
        Table table = new Table();
        Table durationTable = new Table();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Pixmap pmRed = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmRed.setColor(MyConstants.redPrimval);
        pmRed.fill();

        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        Pixmap pixmapBg = new Pixmap(1, 1, Pixmap.Format.RGB565);

        int fontSize = MyConstants.SCREENWIDTH / 60;
        float buttonPadding = MyConstants.SCREENWIDTH / 80f;
        int textureSize = MyConstants.SCREENWIDTH / 60;


        FreeTypeFontGenerator fontArial = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENWIDTH / 60;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        bitmapFontArial = fontArial.generateFont(parameter);
        fontArial.dispose();


        db = new MyDataBase(dataBase);

        durationPerExercice = db.getMaxDureePageForIdProfil(chapitre, onglet);

        duration = MillisToDuration(durationPerExercice);

        Label.LabelStyle labelStyleOnglet = new Label.LabelStyle();
        labelStyleOnglet.font = bitmapFontArial;
        labelStyleOnglet.fontColor = MyConstants.redPrimval;
        pixmapBg.setColor(Color.WHITE);
        pixmapBg.fill();
        Label labelOnglet = new Label(ongletTitre, labelStyleOnglet);
        labelOnglet.setWidth(MyConstants.SCREENWIDTH / 4f);


        Label.LabelStyle labelStyleDuration = new Label.LabelStyle();
        labelStyleDuration.fontColor = Color.OLIVE;
        labelStyleDuration.font = bitmapFontArial;
        Label labelDuration = new Label(duration, labelStyleDuration);

        if (borderColor == "blue")
        {
            highestNote = db.getHighestNote(chapitre, onglet);
            noteMaxPerExercice = db.getMaxNotePerExercice(chapitre, onglet, 0);
            notePossiblePerExercice = db.getMaxNotePossiblePerExercice(chapitre, onglet, 0);

            notes2Implement = highestNote + " / " + notePossiblePerExercice + " / " + noteMaxPerExercice;
        }
        else
        {
            notes2Implement = "";
        }

        Label.LabelStyle labelStyleNotes = new Label.LabelStyle();
        labelStyleNotes.font = bitmapFontArial;
        labelStyleNotes.fontColor = Color.ORANGE;
        Label labelNotes = new Label(notes2Implement, labelStyleNotes);
        labelNotes.setWidth(MyConstants.SCREENWIDTH / 4f);

        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmapBg))));

        table.add(button).height(MyConstants.SCREENHEIGHT / 30f).width(MyConstants.SCREENHEIGHT / 30f).padLeft(screenWidth / 50f).padRight(screenWidth / 80f);
        table.add(labelOnglet).width((screenWidth * 0.61f)).padRight(screenWidth / 20f);
        table.add(new Image(texture)).width(screenWidth / 70f).height(screenWidth / 70f).padRight(screenWidth / 30f);
        table.add(labelDuration).width(screenWidth / 11f).padRight( screenWidth / 18f);
        table.add(labelNotes).width(screenWidth / 8f);


        float lineHeight = MyConstants.SCREENHEIGHT / 20;
        float buttonSize = lineHeight / 10;
        int fontSizeOnglet = MyConstants.SCREENHEIGHT / 60;
        float paddingInterOnglets = -MyConstants.SCREENHEIGHT / 50;

        container.add(table).height(MyConstants.SCREENHEIGHT / 21f).width(screenWidth);


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

