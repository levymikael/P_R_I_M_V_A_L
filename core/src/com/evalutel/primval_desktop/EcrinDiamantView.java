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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;


public class EcrinDiamantView implements MyDrawInterface
{

    private Table table, tableTitre;
    private Table tableMilieu;
    private float heightTop;
    public float widthScreen, topSectionposY;
    float topYTablePosition;

    private float firstY, currentY, widthEcrin;

    Texture ecrinDiamantTexture, textureTextEnonce, titreTop;

    TextField textFieldEnonce;

    Sprite sprite2, spriteEnonceText;
    BitmapFont bitmapFont;
    private boolean isVisible = true;
    private Texture textureMilieuEnonce;


    public EcrinDiamantView(Stage stage, float width, String ecrinGauche, String ecrinDroite)
    {
        ecrinDiamantTexture = new Texture("Images/ecrin2.png");
        widthEcrin = width;
        heightTop = widthEcrin * 100 / 1626;

// Configuration police de l'enonce
        TextField.TextFieldStyle textFieldStyleTest = new TextField.TextFieldStyle();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comici.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        // Numero exerice/consigne:
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = bitmapFont;
        labelStyle2.fontColor = Color.WHITE;
        Label label2 = new Label(ecrinDroite, labelStyle2);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.WHITE;
        Label label = new Label(ecrinDroite, labelStyle);
        label.setWrap(true);

// Creation cellule tableau pour numero d'exerice:
        tableTitre = new Table();
        tableTitre.setBackground(new SpriteDrawable(new Sprite(ecrinDiamantTexture)));

// Positionnement numero exercice:
        tableTitre.add().width(50);
        tableTitre.add(label2).align(Align.right).width(100);
        tableTitre.add(label).width(widthEcrin - 150).height(100);

        table = new Table();
        stage.addActor(table);
        stage.addActor(tableTitre);

        tableMilieu = new Table();
        table.add(tableMilieu);
        table.row();


// Positionnement du tableau sur ecran:

        final int screenHeight = Gdx.graphics.getHeight();
        widthScreen = Gdx.graphics.getWidth();
        tableTitre.pack();
        tableTitre.setPosition(100, 50);

        table.pack();
        final float tableHeight = table.getHeight();

        topYTablePosition = screenHeight - tableHeight - tableTitre.getHeight();

        topSectionposY = Gdx.graphics.getHeight() - heightTop;

        table.setPosition(100, 50);

    }


    public void addTextEcrin(String string)
    {

        Table table4 = new Table();

        Label.LabelStyle labelStyle3 = new Label.LabelStyle();
        labelStyle3.font = bitmapFont;
        labelStyle3.fontColor = Color.WHITE;
        Label label3 = new Label(string, labelStyle3);

        table4.add().width(20).height(50);
        table4.add(label3).width(widthEcrin - 40).height(50);
        table4.add().width(20).height(50);

        table4.row();

        tableMilieu.add(table4);
        tableMilieu.row();

        table.pack();


        topYTablePosition = Gdx.graphics.getHeight() - table.getHeight() - tableTitre.getHeight();

        table.setPosition(100, 50);
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
