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
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.MrNotes;
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

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Pixmap pmRed = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmRed.setColor(new Color(235f / 50f, 44f / 255f, 35f / 255f, 1));
        pmRed.fill();

        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlue.setColor(new Color(71f / 255f, 107f / 255f, 217f / 255f, 1));
        pmBlue.fill();

        Pixmap bgOrange = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgOrange.setColor(Color.ORANGE);
        bgOrange.fill();

        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        Pixmap pixmapBg = new Pixmap(1, 1, Pixmap.Format.RGB565);

        FreeTypeFontGenerator fontArial = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENWIDTH / 70;
        bitmapFontArial = fontArial.generateFont(parameter);
        fontArial.dispose();

        Label.LabelStyle labelStyleOnglet = new Label.LabelStyle();
        labelStyleOnglet.font = bitmapFontArial;

        db = new MyDataBase(dataBase);

        durationPerExercice = db.getMaxDureePageForIdProfil(chapitre, onglet);

        durationPerChapter = db.getTotalDureePageForIdProfilByChapter(chapitre);

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
        labelStyleDuration.fontColor = Color.OLIVE;
        labelStyleDuration.font = bitmapFontArial;

        Label labelDuration = new Label(duration, labelStyleDuration);

        Label exerciseTitleLabel = new Label(ongletTitre, labelStyleOnglet);

        Label.LabelStyle labelStyleNotes = new Label.LabelStyle();
        labelStyleNotes.font = bitmapFontArial;

        if (borderColor == "red")
        {
            labelStyleOnglet.fontColor = new Color(167f / 255f, 44f / 255f, 23f / 255f, 1);
            pixmapBg.setColor(Color.WHITE);
            pixmapBg.fill();
        }
        else if (borderColor == "blue")
        {
            labelStyleOnglet.fontColor = new Color(72f / 255f, 107f / 255f, 217f / 255f, 1);
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

            pixmapBg.setColor(Color.ORANGE);
            pixmapBg.fill();

            labelStyleNotes.font = bitmapFontArial;
            labelStyleNotes.fontColor = Color.ORANGE;

            notes2Implement = db.getTotalNotePageForIdProfil(chapitre);

//            int notePossiblePerChapter = db.getMaxNotePossiblePerChapter(chapitre, 0);
//            highestNote = db.getHighestNotePerChapter(chapitre);
//            int noteMaxPerChapter = db.getMaxNotePerChapter(chapitre, 0);
//
////            notes2Implement = highestNote + " / " + notePossiblePerChapter + " / " + noteMaxPerChapter;
        }

        TextureRegionDrawable tableBG = new TextureRegionDrawable(new TextureRegion(new Texture(pixmapBg)));

        table.setBackground(tableBG);

        Label labelNotes = new Label(notes2Implement, labelStyleNotes);

        Table durationTable = new Table();
        Table noteTable = new Table();

        durationTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));

        noteTable.add(labelNotes).height(screenHeight / 25).width(screenWidth / 10).padLeft(screenWidth / 20);
        noteTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));

        table.add().width(screenWidth / 40);

        table.add(button);

        table.add().width(screenWidth / 80);

        table.add(exerciseTitleLabel).width((float) (screenWidth * 0.55));

        if (texture != null)
        {
            table.add().width(screenWidth / 100);

            table.add(new Image(texture)).width(screenWidth / 80).height(screenWidth / 70);
        }
        else
        {
            table.add().width(screenWidth / 80);

            table.add().width(screenWidth / 100).height(screenWidth / 70);
        }

        if (borderColor == "red")
        {
            durationTable.add(labelDuration).height(screenHeight / 25).width(screenWidth / 10).padLeft(screenWidth / 20);

            table.add().width(screenWidth / 15);

            table.add(durationTable).width(screenWidth / 12);
            table.add().width(2 * screenWidth / 10);
        }
        else
        {
            if (borderColor == "blue")
            {
                durationTable.add(labelDuration).height(screenHeight / 25).width(screenWidth / 10).padLeft(screenWidth / 20);

                table.add().width(screenWidth / 15);

                table.add(durationTable).width(screenWidth / 12);
                table.add().width(screenWidth / 35);

                table.add(noteTable);
//                table.add().width(screenWidth / 50);
            }
            else
            {
                durationTable.add(labelDuration).height(screenHeight / 25).width(screenWidth / 10).padLeft(screenWidth / 40);

                table.add().width(screenWidth / 30);
                labelDuration.setFontScale((float) 1.5);

                table.add(durationTable).width(screenWidth / 10);

                table.add().width(screenWidth / 25);
                labelNotes.setFontScale((float) 1.5);
                table.add(noteTable).width(screenWidth / 10);
//                table.add().width(screenWidth / 40);
            }
        }


        float lineHeight = MyConstants.SCREENHEIGHT / 20;
        float buttonSize = lineHeight /10;
        int fontSizeOnglet = MyConstants.SCREENHEIGHT / 60;
        float paddingInterOnglets = -MyConstants.SCREENHEIGHT / 50;


        container.add(table).height(screenHeight / 20).width(screenWidth).height(lineHeight);

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
