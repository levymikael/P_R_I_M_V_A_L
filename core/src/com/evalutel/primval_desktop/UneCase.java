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
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

public class UneCase extends Table implements MyCorrectionAndPauseInterface, MyDrawInterface, MyTouchInterface
{
    public String newCaseColorPath, newCaseColor, boutonBlancFond, content;

    private Texture fondCaseFondBlancTexture, fondCaseFondBleuTexture, fondCaseFondGrisTexture, fondCaseFondJauneTexture, fondCaseFondMarronTexture, fondCaseFondRougeTexture, fondCaseFondVertTexture, fondCaseFondVioletTexture;

    boolean alreadyColored = false;
    boolean isActive = true;

    public float currentPositionX, currentPositionY;

    int value, caseIndex, casePosition;
    float caseWidth, caseHeight;

    public UneCase(Stage stage, final String content, float caseWidth, float caseHeight, int value)
    {

        this.setSize(caseWidth, caseHeight);

        this.value = value;
        this.content = content;
        this.caseIndex = caseIndex;
        this.caseWidth = caseWidth;
        this.caseHeight = caseHeight;

        boutonBlancFond = "Images/Cases et couleurs pastels/carre_blanc.png";
        fondCaseFondBlancTexture = new Texture(boutonBlancFond);
        fondCaseFondBlancTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fondCaseFondBleuTexture = new Texture("Images/Cases et couleurs pastels/carre_bleu.png");
        fondCaseFondBleuTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fondCaseFondGrisTexture = new Texture("Images/Cases et couleurs pastels/carre_gris.png");
        fondCaseFondGrisTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fondCaseFondJauneTexture = new Texture("Images/Cases et couleurs pastels/carre_jaune.png");
        fondCaseFondJauneTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        fondCaseFondMarronTexture = new Texture("Images/Cases et couleurs pastels/carre_marron.png");
        fondCaseFondMarronTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fondCaseFondRougeTexture = new Texture("Images/Cases et couleurs pastels/carre_rouge.png");
        fondCaseFondRougeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fondCaseFondVertTexture = new Texture("Images/Cases et couleurs pastels/carre_vert.png");
        fondCaseFondVertTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fondCaseFondVioletTexture = new Texture("Images/Cases et couleurs pastels/carre_violet_clair.png");
        fondCaseFondVioletTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondBlancTexture)));

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

        Label ardoiseNum = new Label(content, labelStyleWhite);
        this.add(ardoiseNum);


        if (isActive)
        {
            setTouchable(Touchable.enabled);
        }

        stage.addActor(this);
    }

    public void setCasePositon(int position)
    {
        casePosition = position;
    }


    public int getCasePositionList()
    {
        return casePosition;
    }

    public MyPoint getCasePositionXY()
    {
        return new MyPoint((getX() + (caseWidth / 2)), (getY() + caseHeight / 2));

    }

    public String getCaseContent()
    {
        return content;
    }

    public int getCaseValue()
    {
        return value;
    }

    public String getCaseCouleur()
    {
        if (newCaseColor != null)
        {
            return newCaseColor;
        }
        else
        {
            return null;
        }
    }

    public void setCaseCouleurFond(String couleur)
    {
        if (isActive)
        {


//            newCaseColor = couleur;
//            newCaseColorPath = "Images/Cases et couleurs pastels/carre_" + couleur + ".png";
//
//            Texture NewfondCaseColorTexture = new Texture(newCaseColorPath);
//
//            NewfondCaseColorTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            switch (couleur)
            {
                case "bleu":
                    this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondBleuTexture)));
                    break;
                case "gris":
                    this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondGrisTexture)));
                    break;
                case "jaune":
                    this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondJauneTexture)));
                    break;
                case "marron":
                    this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondMarronTexture)));
                    break;
                case "rouge":
                    this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondRougeTexture)));
                    break;
                case "vert":
                    this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondVertTexture)));
                    break;
                case "violet":
                    this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondVioletTexture)));
                    break;
                default:
                    this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondBlancTexture)));
            }

        }

    }


//    public void setBackCaseCouleurFond()
//    {
//
//        if (isActive)
//        {
//
//
//            this.setBackground(new TextureRegionDrawable(new TextureRegion(fondCaseFondBlanc)));
//        }
//
//    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public void setAlreadyColored()
    {
        alreadyColored = true;
    }

    public boolean getAlreadyColored()
    {
        return alreadyColored;
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
