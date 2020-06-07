package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.onglets.chapitre1.Screen_All_Chapters;

public class UneArdoise extends Table implements MyCorrectionAndPauseInterface, MyDrawInterface
{
    private final FreeTypeFontGenerator generator;
    private final BitmapFont bitmapFont;

    public UneArdoise(Stage stage, String number, int posX, int posY, int ardoiseSize)
    {

        this.setPosition(posX, posY);
        this.setSize(ardoiseSize, ardoiseSize);

        Texture ardoiseBg = new Texture("Images/Ardoise/ardoise_fond.png");
        ardoiseBg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.setBackground(new TextureRegionDrawable(new TextureRegion(ardoiseBg)));

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/FRHND521_0.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENHEIGHT / 40;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();


        Label.LabelStyle labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFont;
        labelStyleWhite.fontColor = Color.WHITE;

        Label ardoiseNum = new Label(number, labelStyleWhite);


        setTouchable(Touchable.enabled);

        addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.log(" ", "Ardoise clicked!");
            }
        });

        this.add(ardoiseNum);


        stage.addActor(this);
    }


    @Override
    public void myCorrectionStart()
    {

    }

    @Override
    public void myCorrectionStop()
    {

    }

    @Override
    public void myPause()
    {

    }

    @Override
    public void myResume()
    {

    }

    @Override
    public boolean isPause()
    {
        return false;
    }

    @Override
    public void myDraw(Batch batch)
    {

    }
}
