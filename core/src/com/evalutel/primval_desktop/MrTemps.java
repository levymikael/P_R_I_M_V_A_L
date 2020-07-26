package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Ex.User;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.UIDesign;

import java.util.concurrent.TimeUnit;

public class MrTemps implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;
    BitmapFont bitmapFont;

    MyDataBase db;

    public MrTemps(Stage stage, DatabaseDesktop dataBase, int chapitre)
    {
        screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();

        db = new MyDataBase(dataBase);

        long totalDuree = db.getTotalDureePageForIdProfilByChapter(chapitre);

        String duration = MillisToDuration(totalDuree);

        User user = new User();
        //user.setIdProfil(39);
        user.setName("userTest");

        Profil profilTest = new Profil(2, "prenomTest", "nomTest", 6, "CP", 1);

        user.setProfil(2);

        String userName = user.getName();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/arial-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        parameter.size = screenHeight / 40;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

// Configuration police
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.OLIVE;
        Label labelTemps = new Label(duration, labelStyle);

        Texture textureMrNotes = new Texture(Gdx.files.internal("Images/mr_temps1.png"));
        textureMrNotes.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Table container = new Table();
        container.setPosition(12.5f * screenWidth / 16, 21.5f * screenHeight / 25f);

        int widthButton = 500;
        int heightButton = widthButton / 4;
        int cornerRadius = heightButton / 4;


        Table mrTemps = new Table();
        mrTemps.setBackground(new SpriteDrawable(new Sprite(new TextureRegion(textureMrNotes))));

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.WHITE);

        Table temps = new Table();
        temps.add(labelTemps).height(screenHeight / 30).padLeft(screenWidth / 60).padRight(screenWidth / 60);
        temps.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Pixmap greenBorder = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.OLIVE);

        Table border = new Table();
        border.pad(screenWidth / 1_000);
        border.setBackground(new SpriteDrawable(new Sprite(new Texture(greenBorder))));
        border.add(temps);

        container.add(mrTemps).height(screenHeight / 10).width((MyConstants.SCREENHEIGHT / 10) * (172f / 223f));
        container.row();
        container.add(border);

        stage.addActor(container);
    }


    public static String MillisToDuration(long seconds)
    {
        String hms = String.format("%02dh%02dmn", TimeUnit.SECONDS.toHours(seconds),
                TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(seconds)));
        System.out.println(hms);


        return hms;
    }


    @Override
    public boolean isVisible()
    {
        return isVisible;
    }

    @Override
    public void setVisible(boolean visible)
    {
        isVisible = visible;
    }

    @Override
    public void myDraw(Batch batch)
    {

    }
}