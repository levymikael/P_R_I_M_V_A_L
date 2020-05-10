package com.evalutel.primval_desktop.General;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;


public class BoutonChapitres
{
    static MyDataBase db;

    public static Table getLigne(String sommaireChapImgPath, String chapterIndexPath, String ongletTitre, Texture texture, int chapitre, DatabaseDesktop dataBase)
    {
        Table container = new Table();
        Table table = new Table();

        db = new MyDataBase(dataBase);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = screenWidth / 80;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyleOngletBlue = new Label.LabelStyle();
        labelStyleOngletBlue.font = bitmapFont;
        labelStyleOngletBlue.fontColor = Color.BLUE;

        Label.LabelStyle labelStyleOngletWhite = new Label.LabelStyle();
        labelStyleOngletWhite.font = bitmapFont;
        labelStyleOngletWhite.fontColor = Color.WHITE;

        Label labelOngletBlue = new Label(ongletTitre, labelStyleOngletBlue);
//        Label labelOngletBuyChapter = new Label("Acheter ce chapitre", labelStyleOngletWhite);
//        Label labelOngletSummary = new Label("Sommaire", labelStyleOngletBlue);

        table.setWidth(screenWidth / 4);
        table.setHeight(screenHeight / 4);

        labelOngletBlue.setWidth(screenWidth / 4);

        Texture textureChapter = new Texture(Gdx.files.internal(sommaireChapImgPath));
        Texture textureChapterIndex = new Texture(Gdx.files.internal(chapterIndexPath));

        int widthButton = 1000;
        int heightButton = widthButton / 4;
        int cornerRadius = heightButton / 8;


        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.WHITE);
//        Pixmap blueRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.BLUE);

        Table table2 = new Table();
        table2.add(new Image(textureChapterIndex)).width(screenWidth / 30).height(screenWidth / 30).padRight(screenWidth / 150).padLeft(MyConstants.SCREENWIDTH / 400);
        table2.add(labelOngletBlue).width((float) (screenWidth / 6));

        Table table3 = new Table();

        table3.add().width(screenWidth / 70);

        Pixmap blueBorder = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.BLUE);


//        Table summaryBtn = new Table();
//        summaryBtn.add(labelOngletSummary).padRight(screenWidth / 100).padLeft(screenWidth / 100);
////        summaryBtn.debug();
//        summaryBtn.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));
//
//        border.add(summaryBtn);
//
//        table3.add(border);

        table.add(new Image(textureChapter)).width(((screenHeight / 7) * 521) / 305).height(screenHeight / 7);
        table.row();
        table.add(table2).width((float) (screenWidth / 4)).align(Align.center);
//        table.row();
//        table.add(table3).width((float) (screenWidth / 9)).align(Align.center).padBottom(screenWidth / 80).padTop(screenWidth / 80).height(screenHeight / 30);

        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(whiteRoundedBackground))));

        Pixmap darkGrayRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.DARK_GRAY);

        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(darkGrayRoundedBackground)));

        Table border = new Table();
        border.pad(screenWidth / 1000);
        border.setBackground(new SpriteDrawable(new Sprite(new Texture(blueBorder))));


        table.pad(MyConstants.SCREENWIDTH / 500);
        container.setBackground(textureRegionDrawableBg);
        container.add(table).height(screenHeight / 4);
        container.row();

        container.debug();

        table.setTouchable(Touchable.enabled);

        return container;
    }
}

