package com.evalutel.primval_desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

public class UnePastelle extends Table implements MyCorrectionAndPauseInterface, MyDrawInterface, MyTouchInterface
{
    private int valuePastelle;

    private Texture couleurPastelleTexture, couleurtraceTexture;

    boolean isActive = true;

    public float positionX, positionY, pastelleWidth, pastelleHeight, positionXTrace;

    String couleurPastelle, couleurTrace;

    public boolean isClicked = false;
    public boolean isOut = false;

    public UnePastelle(Stage stage, float pastelleWidth, float pastelleHeight, String couleurPastelle, float positionY)
    {
        this.setSize(pastelleWidth, pastelleHeight);

        positionX = MyConstants.SCREENWIDTH - (pastelleWidth * .2f);

        this.positionY = positionY;
        this.pastelleWidth = pastelleWidth;
        this.pastelleHeight = pastelleHeight;

        this.setPosition(positionX, positionY);

        this.couleurPastelle = couleurPastelle;


        String pastellePathName = "Images/Cases et couleurs pastels/pastel_" + couleurPastelle + ".png";


        couleurPastelleTexture = new Texture(pastellePathName);
        couleurPastelleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.setBackground(new TextureRegionDrawable(new TextureRegion(couleurPastelleTexture)));

//        if (couleurPastelle.equals("bleu"))
//        {
//            couleurTrace = "bleufonce";
//
//        }
//        else if (couleurPastelle.equals("violet_clair"))
//        {
//            couleurTrace = "violet";
//        }
//        else
//        {
//            couleurTrace = couleurPastelle;
//        }
//
//
//        String tracePathName = "Images/Cases et couleurs pastels/trace_" + couleurTrace + ".png";
//
//        couleurtraceTexture = new Texture(tracePathName);
//        couleurtraceTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


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

                            isClicked = !isClicked;
//                            pastelleInAndOut(isClicked);

                            System.out.println("pastelle clicked");
                        }
                    }
        );


        stage.addActor(this);
    }

    public String getPastelleCouleur()
    {
        return couleurPastelle;
    }

    public void pastelleInAndOut(boolean isClicked)
    {
        if (isActive)
        {
            if (isClicked)
            {
                positionX = MyConstants.SCREENWIDTH - (pastelleWidth * .6f);
                isOut = true;
            }
            else if (!isClicked)
            {
                positionX = MyConstants.SCREENWIDTH - (pastelleWidth * .2f);
                isOut = false;
            }

            this.setPosition(positionX, positionY);
        }
    }

    public void setValuePastelle(int value)
    {
        valuePastelle = value;

        if (value != 0)
        {

        }
    }

    public int getPastelleValue()
    {
        return valuePastelle;
    }

    public void setActive(boolean active)
    {
        isActive = active;

    }


    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.positionX <= currentPositionX && this.positionX + this.pastelleWidth >= currentPositionX && this.positionY <= currentPositionY && this.positionY + this.pastelleHeight >= currentPositionY;
    }

    @Override
    public boolean isTouched(float x, float y)
    {
        Rectangle rectangle = new Rectangle(positionX, positionY, pastelleWidth, pastelleHeight);

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
