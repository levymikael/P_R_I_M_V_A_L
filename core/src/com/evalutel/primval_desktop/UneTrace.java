package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

public class UneTrace extends Table implements MyCorrectionAndPauseInterface, MyDrawInterface, MyTouchInterface
{
    public int traceValue;

    private Texture couleurtraceTexture;

    boolean isActive = true;

    public float positionX, positionY, traceWidth, traceHeight;

    String couleur;

    boolean isClicked = false;

    public UneTrace(Stage stage, float traceWidth, float traceHeight, String couleur, float positionY)
    {

        this.setSize(traceWidth, traceHeight);

        positionX = MyConstants.SCREENWIDTH - (traceWidth * .6f);
        this.positionY = positionY;
        this.traceWidth = traceWidth;
        this.traceHeight = traceHeight;

        this.setPosition(positionX, positionY);

        this.couleur = couleur;

        String tracePathName = "Images/Cases et couleurs pastels/trace_" + couleur + ".png";


        couleurtraceTexture = new Texture(tracePathName);
        couleurtraceTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        this.setBackground(new TextureRegionDrawable(new TextureRegion(couleurtraceTexture)));


        if (isActive)
        {
            setTouchable(Touchable.enabled);
        }

        addListener(new ClickListener()
                    {
                        @Override
                        public void clicked(InputEvent event, float x, float y)
                        {
                            super.clicked(event, x, y);

                            isClicked = true;

                            System.out.println("trace clicked");
                        }
                    }
        );


        stage.addActor(this);
    }

    public String getTraceCouleur()
    {
        return couleur;
    }

    public void setTraceValue(int value)
    {
        traceValue = value;
    }

    public int getTraceValue()
    {
        return traceValue;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }


    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.positionX <= currentPositionX && this.positionX + this.traceWidth >= currentPositionX && this.positionY <= currentPositionY && this.positionY + this.traceHeight >= currentPositionY;
    }

    @Override
    public boolean isTouched(float x, float y)
    {
        Rectangle rectangle = new Rectangle(positionX, positionY, traceWidth, traceHeight);

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
