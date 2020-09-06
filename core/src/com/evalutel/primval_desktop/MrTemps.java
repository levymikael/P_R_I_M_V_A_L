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
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Ex.User;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.UIDesign;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;

import java.util.concurrent.TimeUnit;

public class MrTemps implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;

    public MrTemps(Stage stage, int chapitre)
    {
        screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();

        AppSingleton appSingleton = AppSingleton.getInstance();
        MyDataBase db = appSingleton.myDataBase;

        long totalDuree = db.getTotalDureePageForIdProfilByChapter(chapitre);

        String duration = MillisToDuration(totalDuree);

        User user = new User();
        //user.setIdProfil(39);
        user.setName("userTest");

        Profil profilTest = new Profil(2, "nomTest", "prenomTest", 6, "CP", 1);

        user.setProfil(2);

//        String userName = user.getName();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/segoeUIsemibold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.size = screenHeight / 40;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

// Configuration police
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = MyConstants.greenresultat;
        Label labelTemps = new Label(duration, labelStyle);

        float itemWidth = MyConstants.SCREENWIDTH / 15f;
        float itemHeight = itemWidth * (223f / 172f);

        Texture textureMrNotes = new Texture(Gdx.files.internal("Images/mr_temps1.png"));
        textureMrNotes.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Table container = new Table();
        Table mrTemps = new Table();
        Table temps = new Table();
        Table border = new Table();

        container.setPosition(MyConstants.SCREENWIDTH - itemWidth * 3f, 21f * screenHeight / 25f);

        int widthButton = 500;
        int heightButton = widthButton / 4;
        int cornerRadius = heightButton / 4;

        mrTemps.setBackground(new SpriteDrawable(new Sprite(new TextureRegion(textureMrNotes))));

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.WHITE);

        temps.add(labelTemps).height(itemHeight / 4f).padLeft(screenWidth / 60f).padRight(screenWidth / 60f);
        temps.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Pixmap greenBorder = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, MyConstants.greenresultat);


        border.pad(screenWidth / 900f);
        border.setBackground(new SpriteDrawable(new Sprite(new Texture(greenBorder))));
        border.add(temps);

        container.add(mrTemps).height(itemHeight).width(itemWidth);
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