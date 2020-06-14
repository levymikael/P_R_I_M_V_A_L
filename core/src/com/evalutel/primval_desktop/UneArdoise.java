package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

public class UneArdoise extends Table implements MyCorrectionAndPauseInterface, MyDrawInterface
{
    private final FreeTypeFontGenerator generator;
    private final BitmapFont bitmapFont;
    public String number;

    Texture ardoiseBgActive, ardoiseBgInactive;

    boolean isActive = true;

    public float currentPositionX, currentPositionY;

    public UneArdoise(Stage stage, final String number, int posX, int posY, int ardoiseSize)
    {
        this.number = number;
        this.setPosition(posX, posY);
        this.setSize(ardoiseSize, ardoiseSize);

        ardoiseBgActive = new Texture("Images/Ardoise/ardoise_fond.png");
        ardoiseBgActive.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        ardoiseBgInactive = new Texture("Images/Ardoise/ardoise_fond.png");
        ardoiseBgInactive.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.setBackground(new TextureRegionDrawable(new TextureRegion(ardoiseBgActive)));

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/FRHND521_0.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENHEIGHT / 40;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();


        Label.LabelStyle labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFont;
        labelStyleWhite.fontColor = Color.WHITE;

        Label ardoiseNum = new Label(number, labelStyleWhite);
        this.add(ardoiseNum);


        if (isActive)
        {
            setTouchable(Touchable.enabled);
        }

        stage.addActor(this);
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }


    public boolean isActive()
    {
        return isActive;
    }

    public int ardoiseClicked(int number)
    {
        return number;
    }

    @Override
    public void myCorrectionStart()
    {
        isActive = false;
    }

    @Override
    public void myCorrectionStop()
    {
        isActive = !isActive;
    }

    @Override
    public void myPause()
    {
        isActive = false;
    }

    @Override
    public void myResume()
    {
        isActive = !isActive;
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
