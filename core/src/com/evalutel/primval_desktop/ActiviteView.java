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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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
    public float widthScreen, topSectionposY, newHeight;
    float topYTablePosition, heightBackGroundImage;

    private float firstY, currentY, widthEnonce;

    Texture texture2, textureTextEnonce, titreTop;

    TextField textFieldEnonce;

    Sprite sprite2, spriteEnonceText;
    BitmapFont bitmapFont;
    private boolean isVisible = true;
    private Texture textureMilieuEnonce;


    public ActiviteView(Stage stage, int positionX, int positionY, float width, String numExercice, String consigneExercice, String exDansChapitre)
    {
        textureMilieuEnonce = new Texture("Images/EnonceUIElements/enonce_milieu_new.png");
        widthEnonce = width;
        heightTop = widthEnonce * 100 / 1626;

// Configuration police de l'enonce

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comici.ttf"));
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
        label.setWrap(true);


// Creation cellule tableau pour numero d'exerice:
        tableTitre = new Table();
        tableTitre.setBackground(new SpriteDrawable(new Sprite(new Texture("Images/EnonceUIElements/titre_top.png"))));

// Positionnement numero exercice:
        tableTitre.add().width(50);
        tableTitre.add(label2).align(Align.center).width(80);
        tableTitre.add(label).width(widthEnonce - 180).height(100);
        tableTitre.add(label3).align(Align.center).width(50);

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

        titreTop = new Texture("Images/EnonceUIElements/titre_top.png");

        topSectionposY = Gdx.graphics.getHeight() - heightTop;

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
                    table.setY(topYTablePosition);
                }
                else if (nextY > (screenHeight - heightBackGroundImage - tableTitre.getHeight())) // si souris depasse bandeau alors on cache texte consigne
                {
                    table.setY(screenHeight - heightBackGroundImage - tableTitre.getHeight());
                }
                else
                {
                    currentY = currentY + moveY;
                    table.setY(currentY);

                    newHeight = screenHeight - currentY;


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
        Color colorWhite = new Color();
        colorWhite.add(255, 255, 255, 0);

        table4.add().width(20).height(50);
        table4.add(label3).width(widthEnonce - 40).height(50);
        table4.add().width(20).height(50);

        table4.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
        table4.row();

        newHeight = 50;
//        tableMilieu.add(table4).height(newHeight);
        tableMilieu.row();

        final ScrollPane scroller = new ScrollPane(table4);
        scroller.setHeight(newHeight);
//        table.setFillParent(true);
        tableMilieu.add(scroller).fill().expand();

//        if (tableMilieu.getChildren().size > 4)
//        {
//            tableMilieu.clearChildren();
//        }

        table.pack();

        topYTablePosition = Gdx.graphics.getHeight() - table.getHeight() - tableTitre.getHeight();

        table.setPosition(widthScreen / 2 - widthEnonce / 2, topYTablePosition);



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
