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
import com.evalutel.primval_desktop.General.UIDesign;

import java.util.concurrent.TimeUnit;

public class MrTemps2 implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;
    BitmapFont bitmapFont;

    MyDataBase db;

    public MrTemps2(Stage stage, DatabaseDesktop dataBase)
    {
        screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();

        db = new MyDataBase(dataBase);

        long totalDuree = db.getTotalDureeAllForIdProfil();

        String duration = MillisToDuration(totalDuree);

        User user = new User();
        //user.setIdProfil(39);
        user.setName("userTest");


        Profil profilTest = new Profil(2, "prenomTest", "nomTest", 6, "CP", 1);

        user.setProfil(2);

        String userName = user.getName();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) screenWidth / 80;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

// Configuration police
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.OLIVE;
        Label labelTemps = new Label(duration, labelStyle);

        Texture textureMrTemps = new Texture(Gdx.files.internal("Images/mr_temps1.png"));

        Table container = new Table();
        stage.addActor(container);
        container.setSize(screenWidth / 8, screenHeight / 25);
        container.setPosition(17 * screenWidth / 25, 9 * screenHeight / 11);

        Table mrTemps = new Table();
        mrTemps.setBackground(new SpriteDrawable(new Sprite(new TextureRegion(textureMrTemps))));

        int widthButton = 500;
        int heightButton = widthButton / 4;
        int cornerRadius = heightButton / 4;

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.WHITE);

        Table temps = new Table();
        temps.add(labelTemps);
        temps.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Pixmap orangeBorder = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.ORANGE);


        Table border = new Table();
        border.pad(screenWidth / 500);
        border.setBackground(new SpriteDrawable(new Sprite(new Texture(orangeBorder))));
        border.add(temps);


        container.add(mrTemps).width(screenWidth / 30).padBottom(-screenHeight / 100).padTop(-screenHeight / 100);
        container.add(border);


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
