package com.evalutel.primval_desktop.Ex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.AnimationImageNew;
import com.evalutel.primval_desktop.BackgroundColor;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.MyTouchInterface;

import java.util.ArrayList;
import java.util.Arrays;


public class EnonceViewEx1_1 extends AnimationImageNew implements MyDrawInterface, MyTouchInterface
{
    Table table, table2, table3, table4;
    Label label, label2, label3;
    float heightTop, width;
    float topYTablePosition, tableHeight, widthBackgroundImage, heightBackGroundImage, widthImageEnonce;
    public float widthScreen, topSectionposY, heightImageEnonce;
    private float firstY, currentY, widthEnonce;
    ArrayList<String> list;

    String strAux;

    int indexText;


    Texture textureEnonceMilieu, texture2, textureInstructions, textureTextEnonce, titreTop;
    public TextureRegion textureRegionTitreTop;

    TextField textFieldEnonce, textFieldInstructions;

    BitmapFont bitmapFont;

    Sprite spriteEnonceMilieu, sprite2, spriteInstructions, spriteEnonceText;

    String str0, str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11;


    public EnonceViewEx1_1(int positionX, int positionY, float width, String numExercice, String consigneExercice)
    {
        super(positionX, positionY, width);

        widthEnonce = width;
        heightTop = widthEnonce * 100 / 1626;

// Configuration police de l'enonce
        TextField.TextFieldStyle textFieldStyleTest = new TextField.TextFieldStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        textureEnonceMilieu = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_milieu_new.png"));
        spriteEnonceMilieu = new Sprite(textureEnonceMilieu);
        textFieldStyleTest.background = new SpriteDrawable(spriteEnonceMilieu);

        table = new Table();


// Numero exerice/consigne:
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = bitmapFont;
        labelStyle2.fontColor = Color.WHITE;
        label2 = new Label(numExercice, labelStyle2);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.WHITE;
        label = new Label(consigneExercice, labelStyle);
        label.setWrap(true);

// Creation cellule tableau pour numero d'exerice:
        table2 = new Table();
        table2.setBackground(new SpriteDrawable(new Sprite(new Texture("Images/EnonceUIElements/titre_top.png"))));

// Positionnement numero exercice:
        table2.add(label2).align(Align.center).width(100);
        table2.add(label).width(widthEnonce - 300).height(100);
        table2.row();
        table.add(table2).width(widthEnonce);
        table.row();

        textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_text.png"));
        spriteEnonceText = new Sprite(textureTextEnonce);
        TextField.TextFieldStyle textFieldStyleEnonce = new TextField.TextFieldStyle();
        textFieldStyleEnonce.font = bitmapFont;
        textFieldStyleEnonce.background = new SpriteDrawable(spriteEnonceText);
        textFieldEnonce = new TextField("", textFieldStyleEnonce);

// Deroulement enonce dans tableau

        str0 = "Bonjour,\n" +
                "Je suis le professeur Métrologue, on va faire un jeu amusant qui s’appelle Badix.";
        str1 = "Tu veux jouer ?";
        str2 = "Voici la règle du jeu: \n";
        str3 = " Je vois un oiseau\n";
        str4 = "Je saisis une bille du sac et je la depose sur le plateau\n";
        str5 = "Je vois maintenant 2 oiseaux \n";
        str6 = "Je depose encore une bille\n";
        str7 = "Tiens ! Encore un oiseau \n";
        str8 = "Mince,  je crois que je me suis trompe, je clique sur Mademoiselle Validus pour savoir si c’est juste.\n";
        str9 = "Validus: Non, non tu t’es trompé \n";
        str10 = "Metrologue : Pour corriger mon erreur, je retire une bille de la planche puis je demande a Mademoiselle Validus./n";
        str11 = "Validus:  Youpi ! Tu as gagné un diamant\t.\n";


        String[] strs = {str0, str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11};

        list = new ArrayList<>();
        list.addAll(Arrays.asList(strs));



//        Label.LabelStyle labelStyle3 = new Label.LabelStyle();
//        labelStyle3.font = bitmapFont;
//        labelStyle3.fontColor = Color.BLACK;

//        instructionsScrolling strAux = new instructionsScrolling(1000);


//        label3.setWrap(true);

        table4 = new Table();



//        System.out.println(label3);

        Color colorWhite = new Color();
        colorWhite.add(255, 255, 255, 0);

        BackgroundColor backgroundColor = new BackgroundColor("Images/EnonceUIElements/filet_blanc.png");
        backgroundColor.setColor(255, 255, 255, 1); // r, g, b, a
        backgroundColor.setHeight(250);
        backgroundColor.setWidth(widthEnonce);
//        table4.setBackground(backgroundColor);
        table4.add(label3).width(widthEnonce - 100).height(250);
        table4.setColor(Color.WHITE);

        table4.setBackground(new SpriteDrawable(new Sprite(new Texture("Images/EnonceUIElements/filet_blanc.png"))));


        table.add(table4).width(widthImageEnonce).height(150);
        table.row();


// Insertion texte.png dans tableau avec une imageBG.png:
        table3 = new Table();
        heightBackGroundImage = widthEnonce * 59 / 808;
        texture2 = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_bas_bleu.png"));
        sprite2 = new Sprite(texture2);
        table3.setBackground(new SpriteDrawable(sprite2));
        table3.row();
        heightImageEnonce = heightBackGroundImage * 2 / 3;

        widthImageEnonce = heightImageEnonce * 218 / 41;
        table.add(table3).width(widthEnonce).height(heightBackGroundImage);

        table3.add(textFieldEnonce).width(widthImageEnonce).height(heightImageEnonce);
        table.row();


// Positionnement du tableau sur ecran:

        //TODO je n'arrive pas a inserer les variables screenWidth and screenHeight dans la classe parent.
        screenHeight = Gdx.graphics.getHeight();
        widthScreen = Gdx.graphics.getWidth();

        table.pack();
        tableHeight = table.getHeight();

        topYTablePosition = screenHeight - tableHeight;

        titreTop = new Texture("Images/EnonceUIElements/titre_top.png");

        topSectionposY = Gdx.graphics.getHeight() - heightTop;

        table.setPosition(widthScreen / 2 /*- width / 2*/, topYTablePosition /*- heightTop*/);

        table.setTouchable(Touchable.enabled);

        table.setWidth(20);

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


   /* private class instructionsScrolling extends EnonceView.TaskEtape
    {
        private instructionsScrolling(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (indexText < list.size())
            {
                strAux = list.get(indexText);
                indexText++;
//                System.out.println(strAux);

                Label.LabelStyle labelStyle3 = new Label.LabelStyle();
                labelStyle3.font = bitmapFont;
                labelStyle3.fontColor = Color.BLACK;
                label3 = new Label(strAux, labelStyle3);
                label3.setWrap(true);


            }
        }

        public String labelText (){

            String labelText = strAux;


            System.out.println(label3);


            return labelText;
        }
    }
*/
    @Override
    public void myDraw(Batch batch)
    {
        table.draw(batch, 1);

    }


    @Override
    public boolean isTouched(int x, int y)
    {
        return false;
    }

    @Override
    public boolean isActive()
    {
        return false;
    }

    @Override
    public void setActive(boolean active)
    {

    }
}
