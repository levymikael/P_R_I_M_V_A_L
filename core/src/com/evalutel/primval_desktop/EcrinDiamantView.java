package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;


public class EcrinDiamantView implements MyDrawInterface, MyCorrectionAndPauseInterface
{
    private Label currentLabel1;
    private Label currentLabel2;
    private Table tableTitre;

    int diamant, pierre;

    private float firstY, currentY, widthEcrin;

    Texture ecrinDiamantTexture;


    BitmapFont bitmapFont;
    private boolean isVisible = true;

//    Skin ecrinSkin;
//    Cell<Label> cell;
//    Label label2;
    private int pointsMax;

    Music music;


    public EcrinDiamantView(Stage stage, float width, int pointsMax)
    {
        ecrinDiamantTexture = new Texture("Images/ecrin2.png");
        ecrinDiamantTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.pointsMax = pointsMax;
        widthEcrin = width;

        // Configuration police de l'enonce

        FreeTypeFontGenerator FONT_COMICI = new FreeTypeFontGenerator(Gdx.files.internal("font/comici.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size =  MyConstants.SCREENWIDTH / 70;
        bitmapFont = FONT_COMICI.generateFont(parameter);
        FONT_COMICI.dispose();

        Label.LabelStyle labelStyle1 = new Label.LabelStyle();
        labelStyle1.font = bitmapFont;
        labelStyle1.fontColor = Color.WHITE;
        currentLabel1 = new Label(Integer.toString(diamant), labelStyle1);
        currentLabel1.setWrap(true);

        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = bitmapFont;
        labelStyle2.fontColor = Color.WHITE;
        currentLabel2 = new Label(Integer.toString(diamant), labelStyle2);
        currentLabel2.setWrap(true);

// Creation cellule tableau pour numero d'exerice:
        tableTitre = new Table();
        tableTitre.setBackground(new SpriteDrawable(new Sprite(ecrinDiamantTexture)));
        tableTitre.setSize((float) MyConstants.SCREENHEIGHT / 20, (float) MyConstants.SCREENHEIGHT / 25);

// Positionnement numero exercice:
        tableTitre.add(currentLabel2).align(Align.right).width(MyConstants.SCREENWIDTH / 40).padLeft(MyConstants.SCREENWIDTH / 120);
        tableTitre.add(currentLabel1).width(widthEcrin - MyConstants.SCREENWIDTH / 120).height(MyConstants.SCREENHEIGHT / 11).padLeft(MyConstants.SCREENWIDTH / 50);

        stage.addActor(tableTitre);

// Positionnement du tableau sur ecran:

        tableTitre.pack();
        tableTitre.setPosition(MyConstants.SCREENWIDTH / 60, MyConstants.SCREENHEIGHT / 20);
        tableTitre.setSize(MyConstants.SCREENWIDTH / 7, (MyConstants.SCREENWIDTH / 7) * (59.0f / 168.0f));
    }

    public void updateText()
    {
        String str1 = " " + diamant;
        currentLabel2.setText(str1);

        String str2 = "  /" + pierre + "/" + pointsMax;
        currentLabel1.setText(str2);
    }


    public void addDiamond(int nbDiamant)
    {
        diamant += nbDiamant;
        pierre += nbDiamant;

        updateText();
    }

    public int getDiamantCount()
    {
        return diamant;
    }


    public void addPierre(int nbReponsesPossibles)
    {
        pierre += nbReponsesPossibles;
        updateText();
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

    public void myResume()
    {
        if (music != null)
        {
            music.play();
        }
    }

    @Override
    public boolean isPause()
    {
        return false;
    }
}
