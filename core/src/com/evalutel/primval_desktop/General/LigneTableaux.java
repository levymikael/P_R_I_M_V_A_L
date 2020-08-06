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
import com.evalutel.primval_desktop.ui_tools.AppSingleton;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;


public class LigneTableaux
{

    static String notes2Implement;
    static long durationPerExercice;

    public static Table getLigne(MyTextButton button, String ongletTitre, Texture texture, String borderColor, int chapitre, int onglet)
    {
        Table container = new Table();
        Table table = new Table();
        Table borderTable1 = new Table();
        Table borderTable2 = new Table();

        Pixmap pmRed = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmRed.setColor(MyConstants.redPrimval);
        pmRed.fill();

        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlue.setColor(MyConstants.bluePrimval);
        pmBlue.fill();

        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        int fontSize = MyConstants.SCREENWIDTH / 60;
        float buttonPadding = MyConstants.SCREENWIDTH / 80f;
        int textureSize = MyConstants.SCREENWIDTH / 60;


        FreeTypeFontGenerator generatorComicSansMSBold = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterComicSansMSBold = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterComicSansMSBold.size = fontSize;
        parameterComicSansMSBold.minFilter = Texture.TextureFilter.Linear;
        parameterComicSansMSBold.magFilter = Texture.TextureFilter.Linear;
        BitmapFont bitmapFontComicSansMSBold = generatorComicSansMSBold.generateFont(parameterComicSansMSBold);
        generatorComicSansMSBold.dispose();

        FreeTypeFontGenerator generatorArial = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterArial = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterArial.size = fontSize;
        parameterArial.minFilter = Texture.TextureFilter.Linear;
        parameterArial.magFilter = Texture.TextureFilter.Linear;
        BitmapFont bitmapFontArial = generatorArial.generateFont(parameterArial);
        generatorArial.dispose();

        Label.LabelStyle labelStyleOnglet = new Label.LabelStyle();
        labelStyleOnglet.font = bitmapFontComicSansMSBold;

        AppSingleton appSingleton = AppSingleton.getInstance();
        MyDataBase db = appSingleton.myDataBase;

        durationPerExercice = db.getMaxDureePageForIdProfil(chapitre, onglet);

        String duration = MillisToDuration(durationPerExercice);

        Label.LabelStyle labelStyleDuration = new Label.LabelStyle();
        labelStyleDuration.fontColor = Color.OLIVE;
        labelStyleDuration.font = bitmapFontArial;

        Label labelDuration = new Label(duration, labelStyleDuration);
        labelDuration.setWidth(MyConstants.SCREENWIDTH / 40f);

        if (borderColor == "red")
        {
            borderTable2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmRed))));
            borderTable1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmRed))));
            labelStyleOnglet.fontColor = MyConstants.redPrimval;
        }
        else if ((borderColor == "blue"))
        {
            borderTable1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue))));
            borderTable2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue))));
            labelStyleOnglet.fontColor = MyConstants.bluePrimval;
        }

        Label labelOnglet = new Label(ongletTitre, labelStyleOnglet);

        int highestNote, noteMaxPerExercice, notePossiblePerExercice;

        highestNote = db.getHighestNote(chapitre, onglet);

        noteMaxPerExercice = db.getMaxNotePerExercice(chapitre, onglet, 0);

        notePossiblePerExercice = db.getMaxNotePossiblePerExercice(chapitre, onglet, 0);

        notes2Implement = highestNote + "/" + notePossiblePerExercice + "/" + noteMaxPerExercice;

        Label.LabelStyle labelStyleNotes = new Label.LabelStyle();
        labelStyleNotes.fontColor = Color.ORANGE;
        labelStyleNotes.font = bitmapFontArial;

        Label labelNotes = new Label(notes2Implement, labelStyleNotes);
        labelNotes.setWidth(MyConstants.SCREENWIDTH / 20f);

        table.setWidth(MyConstants.SCREENWIDTH);
        table.setHeight(MyConstants.SCREENHEIGHT / 13f);

        labelOnglet.setWidth(MyConstants.SCREENWIDTH / 4f);

        table.add(button).height(button.getHeight()).width(button.getWidth()).padRight(buttonPadding).padLeft(buttonPadding);
        table.add(labelOnglet).width((MyConstants.SCREENWIDTH * 0.68f));


        if (texture != null)
        {
            table.add(new Image(texture)).width(textureSize).height(textureSize);
        }
        table.add(labelDuration).width(MyConstants.SCREENWIDTH / 12f).padRight(MyConstants.SCREENWIDTH / 17f).padLeft(MyConstants.SCREENWIDTH / 50);

        if (borderColor == "red")
        {
            table.add().width(MyConstants.SCREENWIDTH / 12f);
        }
        else
        {
            table.add(labelNotes).width(MyConstants.SCREENWIDTH / 12f);
        }


        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));

        container.add(borderTable1).width(MyConstants.SCREENWIDTH).height(2);
        container.row();
        container.add(table).height(MyConstants.SCREENHEIGHT / 17f);
        container.row();
        container.add(borderTable2).width(MyConstants.SCREENWIDTH).height(2);

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

