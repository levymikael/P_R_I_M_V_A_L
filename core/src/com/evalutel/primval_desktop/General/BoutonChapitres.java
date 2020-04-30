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


public class BoutonChapitres
{
    static MyDataBase db;

    public static Table getLigne(String sommaireChapImgPath, String chapterIndexPath, String ongletTitre, String chapterBought, Texture texture, int chapitre, DatabaseDesktop dataBase)
    {
        Table container = new Table();
        Table table = new Table();

        db = new MyDataBase(dataBase);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyleOngletBlue = new Label.LabelStyle();
        labelStyleOngletBlue.font = bitmapFont;
        labelStyleOngletBlue.fontColor = Color.BLUE;

        Label.LabelStyle labelStyleOngletWhite = new Label.LabelStyle();
        labelStyleOngletWhite.font = bitmapFont;
        labelStyleOngletWhite.fontColor = Color.WHITE;

        Label labelOngletBlue = new Label(ongletTitre, labelStyleOngletBlue);
        Label labelOngletBuyChapter = new Label("Acheter ce chapitre", labelStyleOngletWhite);
        Label labelOngletSummary = new Label("Sommaire", labelStyleOngletBlue);

        table.setWidth(screenWidth / 4);
        table.setHeight(screenHeight / 4);

        labelOngletBlue.setWidth(screenWidth / 4);

        Texture textureChapter = new Texture(Gdx.files.internal(sommaireChapImgPath));
        Texture textureChapterIndex = new Texture(Gdx.files.internal(chapterIndexPath));

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(screenWidth / 10, screenHeight / 18, 25, Color.WHITE);
        Pixmap blueRoundedBackground = UIDesign.createRoundedRectangle(screenWidth / 20, screenHeight / 30, 10, Color.BLUE);

        table.debug();
        Table table2 = new Table();
        table2.add(new Image(textureChapterIndex)).width(screenWidth / 40).padRight(screenWidth / 40).padLeft(screenWidth / 40);
        table2.add(labelOngletBlue).width((float) (screenWidth / 6));

        Table table3 = new Table();

        if (chapterBought == "no")
        {

            Table table4 = new Table();
            table4.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(blueRoundedBackground))));
            table4.add(labelOngletBuyChapter).width(screenWidth / 8).align(Align.center);

            table3.add(table4);
        }

        table3.add().width(screenWidth / 70);


        Table table5 = new Table();
        table5.add(labelOngletSummary);
        table5.debug();


        table3.add(table5);


        table.add(new Image(textureChapter)).width(screenWidth / 4);
        table.row();
        table.add(table2).width((float) (screenWidth / 4)).align(Align.center);
        table.row();
        table.add(table3).width((float) (screenWidth / 9)).align(Align.center).padBottom(screenWidth / 80).padTop(screenWidth / 80);

        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(whiteRoundedBackground))));


        container.add(table).height(screenHeight / 4);
        container.row();

        table.setTouchable(Touchable.enabled);

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

