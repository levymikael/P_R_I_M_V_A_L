package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;


public class EcrinDiamantView implements MyDrawInterface
{
    private Label currentLabel1;
    private Label currentLabel2;
    private Table table, tableTitre;
    public float widthScreen;

    int diamant, pierre;

    private float firstY, currentY, widthEcrin;

    Texture ecrinDiamantTexture;


    BitmapFont bitmapFont;
    private boolean isVisible = true;

    Skin ecrinSkin;
    Cell<Label> cell;
    Label label2;
    private int pointsMax;

    public EcrinDiamantView(Stage stage, float width, int pointsMax)
    {
        ecrinDiamantTexture = new Texture("Images/ecrin2.png");
        this.pointsMax = pointsMax;
        widthEcrin = width;


        // Configuration police de l'enonce

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comici.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        // Numero exerice/consigne:
//        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
//        labelStyle2.font = bitmapFont;
//        labelStyle2.fontColor = Color.WHITE;
//        label2 = new Label(text, ecrinSkin);


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

// Positionnement numero exercice:
        tableTitre.add().width(50);
        tableTitre.add(currentLabel2).align(Align.right).width(200);
        tableTitre.add(currentLabel1).width(widthEcrin - 50).height(200);

        table = new Table();
        stage.addActor(table);
        stage.addActor(tableTitre);

        table.row();


// Positionnement du tableau sur ecran:

        widthScreen = Gdx.graphics.getWidth();
        tableTitre.pack();
        tableTitre.setPosition(10, 50);

        table.pack();

        table.setPosition(50, 0);

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

}
