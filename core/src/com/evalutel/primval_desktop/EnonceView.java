package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.TimerTask;


public class EnonceView extends Actor
{

    Stage stage;
    Table table, table2, table3;
    Label label2, label;
    float heightTop;
    float topYTablePosition, screenHeight, tableHeight, widthBackgroundImage, heightBackGroundImage, widthImageEnonce;
    public float widthScreen, topSectionposY, heightImageEnonce;
    private float firstY, currentY, widthEnonce;

    Texture textureEnonceMilieu, texture2, textureConsigne, textureTextEnonce, titreTop;
    public TextureRegion textureRegionTitreTop;

    TextField textFieldEnonce;

    Sprite spriteEnonceMilieu, sprite2, spriteConsigne, spriteEnonceText;


    public String addText(String text)
    {
        return text;
    }


    public EnonceView(Stage stage, int positionX, int positionY, float width, String numExercice, String consigneExercice)
    {

        this.stage = stage;
        widthEnonce = width;
        heightTop = widthEnonce * 100 / 1626;

// Configuration police de l'enonce
        TextField.TextFieldStyle textFieldStyleTest = new TextField.TextFieldStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        //parameter.padLeft = 20;
        //parameter.padRight = 20;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        textureEnonceMilieu = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_milieu_new.png"));
        spriteEnonceMilieu = new Sprite(textureEnonceMilieu);
        textFieldStyleTest.background = new SpriteDrawable(spriteEnonceMilieu);

        table = new Table();


// Numero exerice/consigne:
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = bitmapFont;
        labelStyle2.fontColor = Color.BLUE;
        label2 = new Label(numExercice, labelStyle2);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.BLACK;
        label = new Label(consigneExercice, labelStyle);
        label.setWrap(true);

        // Creation cellule tableau pour numero d'exerice:
        table2 = new Table();
        textureConsigne = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_milieu_new.png"));
        spriteConsigne = new Sprite(textureConsigne);
        table2.setBackground(new SpriteDrawable(spriteConsigne));

// Positionnement numero exercice:
        table2.add(label2).align(Align.top).width(100);
        table2.add(label).width(widthEnonce - 300);
        table2.row();
        table.add(table2).width(widthEnonce);
        table.row();
//        table.setColor(Color.BLUE);

        texture2 = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_bas_bleu.png"));
        sprite2 = new Sprite(texture2);

        textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_text.png"));
        spriteEnonceText = new Sprite(textureTextEnonce);
        TextField.TextFieldStyle textFieldStyleEnonce = new TextField.TextFieldStyle();
        //textFieldStyleEnonce.fontColor = fontColor2;
        textFieldStyleEnonce.font = bitmapFont;
        textFieldStyleEnonce.background = new SpriteDrawable(spriteEnonceText);
        textFieldEnonce = new TextField("", textFieldStyleEnonce);


// Insertion texte.png dans tableau avec une imageBG.png:
        table3 = new Table();

        heightBackGroundImage = widthEnonce * 59 / 808;

        table3.setBackground(new SpriteDrawable(sprite2));

        heightImageEnonce = heightBackGroundImage * 2 / 3;

        widthImageEnonce = heightImageEnonce * 218 / 41;
        table3.add(textFieldEnonce).width(widthImageEnonce).height(heightImageEnonce);
        table.add(table3).width(widthBackgroundImage).height(heightBackGroundImage);
        table.row();

// Positionnement du tableau sur ecran:
        screenHeight = Gdx.graphics.getHeight();
        widthScreen = Gdx.graphics.getWidth();

        table.pack();
        tableHeight = table.getHeight();

        topYTablePosition = screenHeight - tableHeight;

        titreTop = new Texture("Images/EnonceUIElements/titre_top.png");
        textureRegionTitreTop = new TextureRegion(titreTop);

        topSectionposY = Gdx.graphics.getHeight() - heightTop;

        table.setPosition(widthScreen / 2 - width / 2, topYTablePosition - heightTop);

        table.setTouchable(Touchable.enabled);

        setWidth(200);
        setHeight(table.getHeight());

        this.stage.addActor(table);

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


                if (nextY < topYTablePosition - heightTop) // si deplacement plus bas que bandeau --> bandeau reste immobile
                {
                    table.setY(topYTablePosition - heightTop);
                } else if (nextY > (topYTablePosition + tableHeight - heightTop - heightBackGroundImage)) // si souris depasse bandeau alors on cache texte consigne
                {
                    table.setY(topYTablePosition + tableHeight - heightTop - heightBackGroundImage);
                } else
                {
                    currentY = currentY + moveY;
                    table.setY(currentY);
                }

            }
        });

    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);

        batch.draw(textureRegionTitreTop, widthScreen / 2 - (widthEnonce / 2), topSectionposY, widthEnonce, heightTop);

    }


    protected static class TaskEtape extends TimerTask
    {
        protected long durationMillis;

        protected TaskEtape(long dT)
        {
            durationMillis = dT;
        }

        @Override
        public void run()
        {

        }
    }
}
