package com.evalutel.primval_desktop.General;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;


public class BoutonChapitres implements Screen
{
    static MyDataBase db;

    static TextureAtlas textureAtlas;

    static Sprite chap1;

    SpriteBatch batch;

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
        textureChapter.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture textureChapterIndex = new Texture(Gdx.files.internal(chapterIndexPath));
        textureChapterIndex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        int widthButton = 1000;
        int heightButton = widthButton / 4;
        int cornerRadius = heightButton / 8;


        Table table2 = new Table();
        table2.add(new Image(textureChapterIndex)).width(screenWidth / 30).height(screenWidth / 30).padRight(screenWidth / 150).padLeft(MyConstants.SCREENWIDTH / 400);
        table2.add(labelOngletBlue).width((float) (screenWidth / 6));


        table.add(new Image(textureChapter)).width(((screenHeight / 5) * 338) / 305).height(screenHeight / 5);
        table.row();
        table.add(table2).width((float) (screenWidth / 10)).align(Align.center);

        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        Texture bgTable = new Texture("Sans titre.png");
        bgTable.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        table.pad(MyConstants.SCREENWIDTH / 500);
        container.add(table).height(screenHeight / 4);
        container.row();


        table.setTouchable(Touchable.enabled);

        return container;
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        chap1.draw(batch);
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        textureAtlas.dispose();
    }

}

