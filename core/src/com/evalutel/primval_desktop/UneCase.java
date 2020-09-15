package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.HashMap;
import java.util.HashSet;

public class UneCase extends Table implements MyCorrectionAndPauseInterface, MyDrawInterface, MyTouchInterface
{
    public String newCaseColor, boutonBlancFond;

    private Texture fondCaseFondBlanc, ardoiseBgInactive;

    boolean isActive = true;

    public float currentPositionX, currentPositionY;

    int value;

    public UneCase(Stage stage, final String number, /*float posX, float posY, */float caseWidth, float caseHeight, int value)
    {

        this.setSize(caseWidth, caseHeight);

        this.value = value;

        boutonBlancFond = "Images/Cases et couleurs pastels/carre_blanc.png";


        fondCaseFondBlanc = new Texture(boutonBlancFond);
        fondCaseFondBlanc.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondBlanc)));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENHEIGHT / 30;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();


        Label.LabelStyle labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFont;
        labelStyleWhite.fontColor = Color.BLACK;

        Label ardoiseNum = new Label(number, labelStyleWhite);
        this.add(ardoiseNum);


        if (isActive)
        {
            setTouchable(Touchable.enabled);
        }

        stage.addActor(this);
    }

    public int getCaseValue()
    {
        return value;
    }

    public void setCaseCouleurFond(String couleur)
    {

        if (isActive)
        {
            newCaseColor = "Images/Cases et couleurs pastels/carre_" + couleur + ".png";

            Texture NewfondCaseColorTexture = new Texture(newCaseColor);

            this.setBackground(new TextureRegionDrawable(new TextureRegion(NewfondCaseColorTexture)));
        }

    }


    public void setBackCaseCouleurFond()
    {

        if (isActive)
        {


            this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondBlanc)));
        }

    }

    public void setActive(boolean active)
    {
        isActive = active;
    }


    @Override
    public boolean isTouched(float x, float y)
    {
        Rectangle rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());

        return rectangle.contains(x, y);
    }

    @Override
    public boolean isDragable()
    {
        return false;
    }

    @Override
    public MyPoint getPosition()
    {
        return null;
    }

    public boolean isActive()
    {
        return isActive;
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
