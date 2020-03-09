package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;


public class ActiviteView implements MyDrawInterface
{
    private Table table, tableTitre, tableBandeauBas;
    private Table tableMilieu;
    private float heightTop;
    public float widthScreen, topSectionposY;
    float topYTablePosition, heightBackGroundImage;

    private float firstY, currentY, widthEnonce;

    Texture texture2, textureTextEnonce, titreTop;

    TextField textFieldEnonce;

    int cptInstructions;

    Sprite sprite2, spriteEnonceText;
    BitmapFont bitmapFont;
    private boolean isVisible = true;
    private Texture textureMilieuEnonce;
    Texture textureFleche;


    public ActiviteView(Stage stage, float width, String numExercice, String consigneExercice, String exDansChapitre)
    {
        textureFleche = new Texture(Gdx.files.internal("Images/EnonceUIElements/icons8-arrow-100.png"));
        textureMilieuEnonce = new Texture("Images/EnonceUIElements/enonce_milieu_new.png");
        widthEnonce = width;
        heightTop = widthEnonce * 100 / 1626;

// Configuration police de l'enonce

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

// Numero exerice/consigne:
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = bitmapFont;
        labelStyle2.fontColor = Color.WHITE;
        Label label2 = new Label(numExercice, labelStyle2);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.WHITE;
        Label label = new Label(consigneExercice, labelStyle);
        label.setWrap(true);

        Label.LabelStyle labelStyle3 = new Label.LabelStyle();
        labelStyle3.font = bitmapFont;
        labelStyle3.fontColor = Color.YELLOW;
        Label label3 = new Label(exDansChapitre, labelStyle3);
        label3.setWidth(50);
        label3.setWrap(true);


// Creation cellule tableau pour numero d'exerice:
        tableTitre = new Table();
        tableTitre.setBackground(new SpriteDrawable(new Sprite(new Texture("Images/EnonceUIElements/titre_top.png"))));

// Positionnement numero exercice:
        tableTitre.add().width(50);
        tableTitre.add(label2).align(Align.center).width(80);
        tableTitre.add(label).width(widthEnonce - 210).height(100);
        tableTitre.add(label3).align(Align.center).width(80);

        table = new Table();
        stage.addActor(table);
        stage.addActor(tableTitre);

        tableMilieu = new Table();

        table.add(tableMilieu);
        table.row();

        textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/activite.png"));
        spriteEnonceText = new Sprite(textureTextEnonce);
        TextField.TextFieldStyle textFieldStyleEnonce = new TextField.TextFieldStyle();
        textFieldStyleEnonce.font = bitmapFont;
        textFieldStyleEnonce.background = new SpriteDrawable(spriteEnonceText);
        textFieldEnonce = new TextField("", textFieldStyleEnonce);

// Insertion texte.png dans tableau avec une imageBG.png:
        tableBandeauBas = new Table();
        heightBackGroundImage = widthEnonce * 59 / 808;
        texture2 = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_bas_bleu.png"));
        sprite2 = new Sprite(texture2);
        tableBandeauBas.setBackground(new SpriteDrawable(sprite2));
        float heightImageEnonce = heightBackGroundImage * 2 / 3;

        float widthImageEnonce = heightImageEnonce * 218 / 41;
        tableBandeauBas.add(textFieldEnonce).width(widthImageEnonce).height(heightImageEnonce);

        table.add(tableBandeauBas).width(widthEnonce).height(heightBackGroundImage);
        table.row();


// Positionnement du tableau sur ecran:

        final int screenHeight = Gdx.graphics.getHeight();
        widthScreen = Gdx.graphics.getWidth();
        tableTitre.pack();
        tableTitre.setPosition(widthScreen / 2 - widthEnonce / 2, screenHeight - tableTitre.getHeight());

        table.pack();
        final float tableHeight = table.getHeight();
        float temptableHeight = tableHeight;

        topYTablePosition = screenHeight - tableHeight - tableTitre.getHeight();

//        titreTop = new Texture("Images/EnonceUIElements/titre_top.png");

        topSectionposY = Gdx.graphics.getHeight() - heightTop;

        currentY = topYTablePosition;
        table.setPosition(widthScreen / 2 - widthEnonce / 2, topYTablePosition /*- heightTop*/);

        table.setTouchable(Touchable.enabled);


//Manipulation bandeau enonce (drag)

        table.addListener(new DragListener()
        {
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer)
            {
                super.dragStart(event, x, y, pointer);

                table.pack();
                firstY = y;
                currentY = table.getY();
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer)
            {
                super.drag(event, x, y, pointer);

                float moveY = y - firstY;
                float nextY = currentY + moveY;

                if (nextY < topYTablePosition) // si deplacement plus bas que bandeau --> bandeau reste immobile
                {
                    currentY = topYTablePosition;
                    table.setY(topYTablePosition);
                }
                else if (nextY > (screenHeight - heightBackGroundImage - tableTitre.getHeight())) // si souris depasse bandeau alors on cache texte consigne
                {
                    currentY = screenHeight - heightBackGroundImage - tableTitre.getHeight();
                    table.setY(screenHeight - heightBackGroundImage - tableTitre.getHeight());
                }
                else
                {
                    currentY = currentY + moveY;
                    table.setY(currentY);

                }
            }
        });
    }

    public void addTextActivite(String string)
    {
        Table table4 = new Table();

        Label.LabelStyle labelStyle3 = new Label.LabelStyle();
        labelStyle3.font = bitmapFont;
        labelStyle3.fontColor = Color.BLACK;
        Label label3 = new Label(string, labelStyle3);
        label3.setWrap(true);

        Color colorWhite = new Color();
        colorWhite.add(255, 255, 255, 0);


        Label.LabelStyle labelStyle4 = new Label.LabelStyle();
        labelStyle4.font = bitmapFont;
        labelStyle4.fontColor = Color.BLACK;
        Label label4 = new Label("", labelStyle3);

        if (cptInstructions == 0)

        {
            Sprite sprite = new Sprite(textureFleche);
            sprite.setSize(30,40);
            SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(sprite);


            Table table5 = new Table();
            table5.setBackground(flecheSpriteDrawable);

//            table5.setHeight(50);

            table4.add().width(20);
            table4.add(table5).width(50);
            table4.add(label3).width(widthEnonce - 90);
            table4.add().width(20);
        }
        else
        {

            table4.add().width(20);
            table4.add(label4).width(50);
            table4.add(label3).width(widthEnonce - 90);
            table4.add().width(20);
        }

        table4.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
        table4.row();

        tableMilieu.add(table4);
        tableMilieu.row();

        table.pack();


        cptInstructions++;

        float labelHeight = label3.getHeight();

        topYTablePosition = Gdx.graphics.getHeight() - table.getHeight() - tableTitre.getHeight();

        //table.setPosition(widthScreen / 2 - widthEnonce / 2, topYTablePosition);
        float nextTestY = currentY - labelHeight;
        if (nextTestY > topYTablePosition)
        {
            currentY = currentY - labelHeight;
            table.setY(currentY);
        }
        else
        {
            currentY = topYTablePosition;
            table.setY(topYTablePosition);
        }


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