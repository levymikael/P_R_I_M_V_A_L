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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.General.MyConstants;


public class ActiviteView implements MyDrawInterface, MyPauseInterface
{
    private Table table, tableTitre, tableBandeauBas;
    private Table tableMilieu;
    private float heightTop;
    public float topSectionposY;
    float topYTablePosition, heightBackGroundImage;

    private float firstY, currentY, widthEnonce;

    Texture texture2, textureTextEnonce, titreTop;

    TextField textFieldEnonce;

    int cptInstructions;

    Sprite sprite2, spriteEnonceText;
    BitmapFont bitmapFont;
    private boolean isVisible = true;
    private Texture textureMilieuEnonce;

    String activiteType;

    Label lastLabel;

    Sprite flechSprite = new Sprite(new Texture(Gdx.files.internal("Images/EnonceUIElements/black_right_pointing_pointer.png")));


    public ActiviteView(Stage stage, float width, String numExercice, String consigneExercice, String exDansChapitre, String activiteType)
    {
        textureMilieuEnonce = new Texture("Images/EnonceUIElements/enonce_milieu_new.png");
        widthEnonce = width;
        heightTop = widthEnonce * 100 / 1626;

        this.activiteType = activiteType;


// Configuration police de l'enonce
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENWIDTH / 70;


        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

// Numero exerice/consigne:
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = bitmapFont;
        labelStyle2.fontColor = Color.YELLOW;
        Label exoNumLabel = new Label(numExercice, labelStyle2);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.WHITE;
        Label exoConsigneLabel = new Label(consigneExercice, labelStyle);
        exoConsigneLabel.setWrap(true);

        Label.LabelStyle labelStyle3 = new Label.LabelStyle();
        labelStyle3.font = bitmapFont;
        labelStyle3.fontColor = Color.YELLOW;
        Label label3 = new Label(exDansChapitre, labelStyle3);
        label3.setWidth(MyConstants.SCREENWIDTH / 46);
        label3.setWrap(true);


// Creation cellule tableau pour numero d'exerice:
        tableTitre = new Table();
        tableTitre.setBackground(new SpriteDrawable(new Sprite(new Texture("Images/EnonceUIElements/titre_top.png"))));

// Positionnement numero exercice:
        tableTitre.add(exoNumLabel).align(Align.center).width(80).padLeft(MyConstants.SCREENWIDTH / 46);
        tableTitre.add(exoConsigneLabel).width(widthEnonce - 210).height(100);
        tableTitre.add(label3).align(Align.center).width(80);

        table = new Table();
        stage.addActor(table);
        stage.addActor(tableTitre);

        tableMilieu = new Table();

        table.add(tableMilieu);
        table.row();

        if (activiteType == "activite")
        {
            textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/activite.png"));
        }
        else if (activiteType == "enonce")
        {
            textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_text.png"));
        }

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

        tableTitre.pack();
        tableTitre.setPosition(MyConstants.SCREENWIDTH / 2 - widthEnonce / 2, MyConstants.SCREENHEIGHT - tableTitre.getHeight());

        table.pack();
        final float tableHeight = table.getHeight();
        float temptableHeight = tableHeight;

        topYTablePosition = MyConstants.SCREENHEIGHT - tableHeight - tableTitre.getHeight();

        topSectionposY = MyConstants.SCREENHEIGHT - heightTop;

        currentY = topYTablePosition;
        table.setPosition(MyConstants.SCREENWIDTH / 2 - widthEnonce / 2, topYTablePosition /*- heightTop*/);

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
                else if (nextY > (MyConstants.SCREENHEIGHT - heightBackGroundImage - tableTitre.getHeight())) // si souris depasse bandeau alors on cache texte consigne
                {
                    currentY = MyConstants.SCREENHEIGHT - heightBackGroundImage - tableTitre.getHeight();
                    table.setY(MyConstants.SCREENHEIGHT - heightBackGroundImage - tableTitre.getHeight());
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

        Label.LabelStyle labelStyleBlack = new Label.LabelStyle();
        labelStyleBlack.font = bitmapFont;
        labelStyleBlack.fontColor = Color.BLACK;

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFont;
        labelStyleBlue.fontColor = Color.BLUE;

        if (cptInstructions != 0)
        {
            lastLabel.setStyle(labelStyleBlack);
        }

        Label label3 = new Label(string, labelStyleBlue);
        label3.setWrap(true);

        lastLabel = label3;

        lastLabel.setColor(Color.BLUE);

        Label.LabelStyle labelStyle4 = new Label.LabelStyle();
        labelStyle4.font = bitmapFont;
        labelStyle4.fontColor = Color.BLACK;
        Label label4 = new Label("", labelStyleBlack);


        if (cptInstructions == 0)
        {
            flechSprite.setSize(30, 40);

            SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(flechSprite);

            Table pointerTable = new Table();

            if (activiteType == "activite")
            {
                pointerTable.setBackground(flecheSpriteDrawable);
            }
            else
            {
                pointerTable.setWidth(30);
            }

            table4.add(new Image()).height(MyConstants.SCREENHEIGHT / 200);
            table4.row();
            table4.add().width(20);
            table4.add(pointerTable).width(MyConstants.SCREENWIDTH / 46).height(MyConstants.SCREENHEIGHT / 40).align(Align.top);
            table4.add(label3).width(widthEnonce - 90);
            table4.add().width(20);
        }
        else
        {
            table4.add().width(20);
            table4.add(label4).width(50);
            table4.add(label3).width(widthEnonce - 90);
            table4.add().width(20);
            table4.row();
            table4.add(new Image()).height(MyConstants.SCREENHEIGHT / 200);
        }

        table4.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
        table4.row();

        tableMilieu.add(table4);
        tableMilieu.row();

        table.pack();

        cptInstructions++;

//        System.out.println(cptInstructions);

        float labelHeight = label3.getHeight() + MyConstants.SCREENHEIGHT / 200;

        topYTablePosition = MyConstants.SCREENHEIGHT - table.getHeight() - tableTitre.getHeight();


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

    @Override
    public void myPause()
    {

    }

    @Override
    public void myResume()
    {

    }
}
