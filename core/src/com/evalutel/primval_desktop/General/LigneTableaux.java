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

    static String notes2Implement;
    static long durationPerExercice;

    public static Table getLigne(MyTextButton button, String ongletTitre, Texture texture, String borderColor, int chapitre, int onglet, DatabaseDesktop dataBase)
    {
        Table container = new Table();
        Table table = new Table();
        Table tablebord1 = new Table();
        Table tablebord2 = new Table();


        Pixmap pmRed = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmRed.setColor(MyConstants.redPrimval);
        pmRed.fill();

        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlue.setColor(MyConstants.bluePrimval);
        pmBlue.fill();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/ComicSansMSBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENWIDTH / 60;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyleOnglet = new Label.LabelStyle();
        labelStyleOnglet.font = bitmapFont;

        db = new MyDataBase(dataBase);

        durationPerExercice = db.getMaxDureePageForIdProfil(chapitre, onglet);

        String duration = MillisToDuration(durationPerExercice);

        Label.LabelStyle labelStyleDuration = new Label.LabelStyle();
        labelStyleDuration.fontColor = Color.OLIVE;
        labelStyleDuration.font = bitmapFont;

        Label labelDuration = new Label(duration, labelStyleDuration);
        labelDuration.setWidth(MyConstants.SCREENWIDTH / 40);

        if (borderColor == "red")
        {
            tablebord2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmRed))));
            tablebord1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmRed))));
            labelStyleOnglet.fontColor = MyConstants.redPrimval;
        }
        else if ((borderColor == "blue"))
        {
            tablebord1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue))));
            tablebord2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue))));
            labelStyleOnglet.fontColor = MyConstants.bluePrimval;
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
        labelNotes.setWidth(MyConstants.SCREENWIDTH / 20);

        table.setWidth(MyConstants.SCREENWIDTH);
        table.setHeight(MyConstants.SCREENHEIGHT / 13);

        labelOnglet.setWidth(MyConstants.SCREENWIDTH / 4);

        table.add(button).height(button.getHeight()).width(button.getWidth()).padRight(MyConstants.SCREENWIDTH / 80).padLeft(MyConstants.SCREENWIDTH / 80);
        table.add(labelOnglet).width((MyConstants.SCREENWIDTH * 0.6f));

        if (texture != null)
        {
            table.add(new Image(texture)).width(MyConstants.SCREENWIDTH / 50).height(MyConstants.SCREENWIDTH / 50);
        }
        table.add(labelDuration).width(MyConstants.SCREENWIDTH / 12).padRight(MyConstants.SCREENWIDTH / 22).padLeft(MyConstants.SCREENWIDTH / 20);

        if (borderColor == "red")
        {
            table.add().width(MyConstants.SCREENWIDTH / 12).padRight(MyConstants.SCREENWIDTH / 18);
        }
        else
        {
            table.add(labelNotes).width(MyConstants.SCREENWIDTH / 12).padRight(MyConstants.SCREENWIDTH / 16);
        }

        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));

        container.add(tablebord1).width(MyConstants.SCREENWIDTH).height(2);
        container.row();
        container.add(table).height(MyConstants.SCREENHEIGHT / 17);
        container.row();
        container.add(tablebord2).width(MyConstants.SCREENWIDTH).height(2);

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

