package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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
import com.evalutel.primval_desktop.General.UIDesign;

import java.util.ArrayList;


public class ActiviteView implements MyDrawInterface, MyCorrectionAndPauseInterface
{
    private Table table, tableTitre;
    private Table tableMilieu, tableMilieuSolution, table4, table5;
    private float heightTop;
    float topYTablePosition, heightBackGroundImage;

    private float firstY, currentY, widthEnonce;

    Texture texture2, textureTextEnonce, txtureDroiteBleu, txtureGaucheBleu, txtureCentreBleu, txtureDroiteVert, txtureCentreVert, txtureGaucheVert;

    TextField textFieldEnonce;

    int cptInstructions;

    Sprite sprite2, sprite3, spriteEnonceText, spriteSolutionText;
    BitmapFont bitmapFontArial, bitmapFontComic;
    private boolean isVisible = true;
    private Texture textureMilieuEnonce;

    String activiteType;

    Label lastLabel;

    Sprite flechSprite = new Sprite(new Texture(Gdx.files.internal("Images/EnonceUIElements/black_right_pointing_pointer.png")));

    boolean isPaused;

    Table lastPointerTable, lastPointerTable2;

    public ActiviteView(Stage stage, float width, String numExercice, String consigneExercice, String exDansChapitre, String activiteType)
    {
        textureMilieuEnonce = new Texture("Images/EnonceUIElements/enonce_milieu_new.png");
        textureMilieuEnonce.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        widthEnonce = (MyConstants.SCREENWIDTH / 4) * 3;
        heightTop = widthEnonce * 42 / 1626;

        this.activiteType = activiteType;

// Configuration police de l'enonce
        FreeTypeFontGenerator fontArial = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENWIDTH / 70;
        bitmapFontArial = fontArial.generateFont(parameter);
        fontArial.dispose();

        FreeTypeFontGenerator fontComic = new FreeTypeFontGenerator(Gdx.files.internal("font/ComicSansMSBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterComic = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterComic.size = MyConstants.SCREENWIDTH / 70;
        bitmapFontComic = fontComic.generateFont(parameter);
        fontComic.dispose();

        Label.LabelStyle labelStyle3 = new Label.LabelStyle();
        labelStyle3.font = bitmapFontArial;
        labelStyle3.fontColor = Color.YELLOW;
        Label label3 = new Label(exDansChapitre, labelStyle3);
        label3.setWidth(MyConstants.SCREENWIDTH / 46);

        table = new Table();
        stage.addActor(table);

        tableMilieu = new Table();
        tableMilieuSolution = new Table();

        Table paddingTableMilieu = new Table();
        paddingTableMilieu.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));


        table.add(paddingTableMilieu).height(MyConstants.SCREENHEIGHT / 200).width(widthEnonce);
        table.row();

        table.add(tableMilieu).width(widthEnonce + MyConstants.SCREENWIDTH / 100);
        table.row();

        Table paddingTableMilieu2 = new Table();
        paddingTableMilieu2.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));

        table.add(paddingTableMilieu2).height(MyConstants.SCREENHEIGHT / 200).width(widthEnonce);
        table.row();


        if (activiteType == "activite")
        {
            textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/activite.png"));
        }
        else if (activiteType == "enonce")
        {
            textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_text.png"));
        }
        textureTextEnonce.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        spriteEnonceText = new Sprite(textureTextEnonce);
        TextField.TextFieldStyle textFieldStyleEnonce = new TextField.TextFieldStyle();
        textFieldStyleEnonce.font = bitmapFontArial;
        textFieldStyleEnonce.background = new SpriteDrawable(spriteEnonceText);
        textFieldEnonce = new TextField("", textFieldStyleEnonce);

// Insertion texte.png dans tableau avec une imageBG.png:

        Table tableBandeauBasBleu = new Table();
        heightBackGroundImage = widthEnonce * 31 / 809;
        float heightImageEnonce = heightBackGroundImage * 2 / 3;
        float widthImageEnonce = heightImageEnonce * 218 / 41;


//        texture2 = new Texture(Gdx.files.internal("Images/Enoncé-solution/Enoncé-Grand-fond.png"));
//        texture2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        sprite2 = new Sprite(texture2);
//        tableBandeauBasBleu.setBackground(new SpriteDrawable(sprite2));

        Table tableBandeauBasBleuDroite = new Table();
        Texture txtureDroiteBleu = new Texture(Gdx.files.internal("Images/Enoncé-solution/droit bleu.png"));
        txtureDroiteBleu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite spriteDroiteBleu = new Sprite(txtureDroiteBleu);
        tableBandeauBasBleuDroite.setBackground(new SpriteDrawable(spriteDroiteBleu));

        Table tableBandeauBasBleuGauche = new Table();
        Texture txtureGaucheBleu = new Texture(Gdx.files.internal("Images/Enoncé-solution/gauche bleu.png"));
        txtureGaucheBleu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite spriteGaucheBleu = new Sprite(txtureGaucheBleu);
        tableBandeauBasBleuGauche.setBackground(new SpriteDrawable(spriteGaucheBleu));

        float demiBandeauBas = ((widthEnonce - (textFieldEnonce.getWidth() + MyConstants.SCREENWIDTH / 40)) / 2);

        int ok = 5;
        ok++;


        Table tableBandeauBasBleuCentreVierge = new Table();
        Table tableBandeauBasBleuCentreVierge2 = new Table();
        Texture txtureCentreBleu = new Texture(Gdx.files.internal("Images/Enoncé-solution/centre bleu.png"));
        txtureCentreBleu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite spriteCentreBleu = new Sprite(txtureCentreBleu);
        tableBandeauBasBleuCentreVierge.setBackground(new SpriteDrawable(spriteCentreBleu));
        tableBandeauBasBleuCentreVierge2.setBackground(new SpriteDrawable(spriteCentreBleu));

        Table tableBandeauBasBleuCentreEnonce = new Table();
        tableBandeauBasBleuCentreEnonce.setBackground(new SpriteDrawable(spriteCentreBleu));
        tableBandeauBasBleuCentreEnonce.add(textFieldEnonce).height(heightImageEnonce).width(widthImageEnonce);


        tableBandeauBasBleu.add(tableBandeauBasBleuGauche).height(heightImageEnonce);
        tableBandeauBasBleu.add(tableBandeauBasBleuCentreVierge).width(demiBandeauBas).height(heightImageEnonce);
        tableBandeauBasBleu.add(tableBandeauBasBleuCentreEnonce).height(heightImageEnonce);
        tableBandeauBasBleu.add(tableBandeauBasBleuCentreVierge2).width(demiBandeauBas).height(heightImageEnonce);
        tableBandeauBasBleu.add(tableBandeauBasBleuDroite).height(heightImageEnonce);

        table.add(tableBandeauBasBleu).height(heightBackGroundImage);
        table.row();


        Table tableBandeauBasVert = new Table();

        Table tableBandeauBasVertDroite = new Table();
        Texture txtureDroiteVert = new Texture(Gdx.files.internal("Images/Enoncé-solution/droit vert.png"));
        txtureDroiteVert.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite spriteDroiteVert = new Sprite(txtureDroiteVert);
        tableBandeauBasVertDroite.setBackground(new SpriteDrawable(spriteDroiteVert));

        Table tableBandeauBasVertGauche = new Table();
        Texture txtureGaucheVert = new Texture(Gdx.files.internal("Images/Enoncé-solution/gauche vert.png"));
        txtureGaucheVert.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite spriteGaucheVert = new Sprite(txtureGaucheVert);
        tableBandeauBasVertGauche.setBackground(new SpriteDrawable(spriteGaucheVert));

        Table tableBandeauBasVertCentre = new Table();
        Texture txtureCentreVert = new Texture(Gdx.files.internal("Images/Enoncé-solution/centre vert.png"));
        txtureCentreVert.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite spriteCentreVert = new Sprite(txtureCentreVert);
        tableBandeauBasVertCentre.setBackground(new SpriteDrawable(spriteCentreVert));


        tableBandeauBasVert.add(tableBandeauBasVertCentre);
        tableBandeauBasVert.add(tableBandeauBasVertGauche);
        tableBandeauBasVert.add(tableBandeauBasVertDroite);


// Positionnement du tableau sur ecran:


        table.pack();
        final float tableHeight = table.getHeight();
        float temptableHeight = tableHeight;

        topYTablePosition = MyConstants.SCREENHEIGHT - tableHeight - heightTop + (paddingTableMilieu.getHeight() * 2);

        currentY = topYTablePosition;

        int activiteWidth = (MyConstants.SCREENWIDTH / 4) * 3;
        heightTop = activiteWidth * 42 / 1626;
        float xTableTitre = (MyConstants.SCREENWIDTH / 2 - activiteWidth / 2);
        tableMilieu.setX(xTableTitre + MyConstants.SCREENWIDTH / 200);


        table.setPosition((MyConstants.SCREENWIDTH / 2 - activiteWidth / 2), topYTablePosition);

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
                else if (nextY > (MyConstants.SCREENHEIGHT - heightBackGroundImage - heightTop)) // si souris depasse bandeau alors on cache texte consigne
                {
                    currentY = MyConstants.SCREENHEIGHT - heightBackGroundImage - heightTop;
                    table.setY(MyConstants.SCREENHEIGHT - heightBackGroundImage - heightTop);
                }
                else
                {
                    currentY = currentY + moveY;
                    table.setY(currentY);
                }
            }
        });

    }

    public Label setTextActivite(String string)
    {

        emptyActivite();

        return addTextActivite(string);
    }

    public Label addTextActivite(String string)
    {
        table4 = new Table();

        Label.LabelStyle labelStyleBlack = new Label.LabelStyle();
        labelStyleBlack.font = bitmapFontArial;
        labelStyleBlack.fontColor = Color.BLACK;

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFontArial;
        labelStyleBlue.fontColor = new Color(71.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1);

        if (cptInstructions != 0)
        {
            lastLabel.setStyle(labelStyleBlack);
        }

        Label label3 = new Label(string, labelStyleBlue);
        label3.setWrap(true);

        lastLabel = label3;

        lastLabel.setColor(new Color(71.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1));


        if (lastPointerTable != null)
        {
            lastPointerTable.remove();
        }

        flechSprite.setSize(MyConstants.SCREENWIDTH / 30, 40);

        SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(flechSprite);

        lastPointerTable = new Table();

        if (activiteType == "activite")
        {
            lastPointerTable.setBackground(flecheSpriteDrawable);
        }

        table4.add(lastPointerTable).width(MyConstants.SCREENWIDTH / 60).height(MyConstants.SCREENHEIGHT / 40).align(Align.center).padLeft(MyConstants.SCREENWIDTH / 70).padRight(MyConstants.SCREENWIDTH / 100)/*.padTop(MyConstants.SCREENHEIGHT / 20)*/;
        table4.add(label3).width(widthEnonce - ((MyConstants.SCREENWIDTH / 25) + (MyConstants.SCREENWIDTH / 120))).padRight(MyConstants.SCREENWIDTH / 120)/*.padTop(MyConstants.SCREENHEIGHT / 80).padBottom(MyConstants.SCREENHEIGHT / 200)*/;

//        if (cptInstructions == 0)
//        {
//            flechSprite.setSize(MyConstants.SCREENWIDTH / 30, 40);
//
//            SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(flechSprite);
//
//            Table pointerTable = new Table();
//
//            if (activiteType == "activite")
//            {
//                pointerTable.setBackground(flecheSpriteDrawable);
//            }
//            table4.add(pointerTable).width(MyConstants.SCREENWIDTH / 60).height(MyConstants.SCREENHEIGHT / 40).align(Align.center).padLeft(MyConstants.SCREENWIDTH / 70).padRight(MyConstants.SCREENWIDTH / 100)/*.padTop(MyConstants.SCREENHEIGHT / 20)*/;
//            table4.add(label3).width(widthEnonce - ((MyConstants.SCREENWIDTH / 25) + (MyConstants.SCREENWIDTH / 120))).padRight(MyConstants.SCREENWIDTH / 120)/*.padTop(MyConstants.SCREENHEIGHT / 80).padBottom(MyConstants.SCREENHEIGHT / 200)*/;
//        }
//        else
//        {
//            table4.add(new Image()).width(MyConstants.SCREENWIDTH / 27 - MyConstants.SCREENWIDTH / 1000);
//            table4.add(label3).width(widthEnonce - (MyConstants.SCREENWIDTH / 12 + MyConstants.SCREENWIDTH / 450)).padRight(MyConstants.SCREENWIDTH / 20) /*.padBottom(MyConstants.SCREENHEIGHT / 150).padTop(MyConstants.SCREENHEIGHT / 200)*/;
//        }

        table4.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
        tableMilieu.add(table4);
        tableMilieu.row();

        table.pack();

        cptInstructions++;

        float labelHeight = label3.getHeight() + MyConstants.SCREENHEIGHT / 100;

        topYTablePosition = MyConstants.SCREENHEIGHT - table.getHeight() - heightTop;


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

        return label3;
    }

    public void emptyActivite()
    {
        tableMilieu.clear();
    }

    public Label setTextSolution(String string)
    {

        emptySolution();

        return addTextActivite(string);

    }

    public Label addTextSolution(String string)
    {
        table5 = new Table();

        Label.LabelStyle labelStyleBlack = new Label.LabelStyle();
        labelStyleBlack.font = bitmapFontArial;
        labelStyleBlack.fontColor = Color.BLACK;

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFontArial;
        labelStyleBlue.fontColor = new Color(Color.valueOf("004ec0"));

        if (cptInstructions != 0)
        {
            lastLabel.setStyle(labelStyleBlack);
        }

        Label label3 = new Label(string, labelStyleBlue);
        label3.setWrap(true);

        lastLabel = label3;

        lastLabel.setColor(new Color(Color.valueOf("004ec0")));


        if (lastPointerTable != null)
        {
            lastPointerTable.remove();
        }

        flechSprite.setSize(MyConstants.SCREENWIDTH / 30, 40);
        flechSprite.setColor(new Color(71.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1));

        SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(flechSprite);

        lastPointerTable2 = new Table();

        lastPointerTable2.setBackground(flecheSpriteDrawable);


        table5.add(lastPointerTable2).width(MyConstants.SCREENWIDTH / 60).height(MyConstants.SCREENHEIGHT / 40).align(Align.left).padLeft(MyConstants.SCREENWIDTH / 70).padRight(MyConstants.SCREENWIDTH / 100)/*.padTop(MyConstants.SCREENHEIGHT / 20)*/;
        table5.add(label3).width(widthEnonce / 2 - ((MyConstants.SCREENWIDTH / 25) + (MyConstants.SCREENWIDTH / 120))).padRight(MyConstants.SCREENWIDTH / 120)/*.padTop(MyConstants.SCREENHEIGHT / 80).padBottom(MyConstants.SCREENHEIGHT / 200)*/;


        table5.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
        tableMilieuSolution.add(table5);
        tableMilieuSolution.row();

        table.pack();

        cptInstructions++;

        float labelHeight = label3.getHeight() + MyConstants.SCREENHEIGHT / 100;

        topYTablePosition = MyConstants.SCREENHEIGHT - table.getHeight() - heightTop;


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

        return label3;
    }


    public void emptySolution()
    {
        tableMilieuSolution.clear();
    }
//    public Label addText(String str)
//    {
//        ArrayList<String> textToAdd = new ArrayList<>();
//
//        textToAdd.add(str);
//
//        String string = "";
//
//        Label.LabelStyle labelStyleBlack = new Label.LabelStyle();
//        labelStyleBlack.font = bitmapFontArial;
//        labelStyleBlack.fontColor = Color.BLACK;
//
//        Label label = new Label(string, labelStyleBlack);
//
//        for (int i = 0; i < textToAdd.size(); i++)
//        {
//            String test = textToAdd.get(i);
//
//            string = string + test;
//
//            label.setText(string);
//        }
//        return label;
//    }
//
////    public Label addTextActivite2(String string)
////    {
////        Table table4 = new Table();
////
////        Label.LabelStyle labelStyleBlack = new Label.LabelStyle();
////        labelStyleBlack.font = bitmapFontArial;
////        labelStyleBlack.fontColor = Color.BLACK;
////
////
//////
//////        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
//////        labelStyleBlue.font = bitmapFontArial;
//////        labelStyleBlue.fontColor = new Color(71.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1);
////
//////        if (cptInstructions != 0)
//////        {
//////            lastLabel.setStyle(labelStyleBlack);
//////        }
////
////        Label textToAdd = addText(string);
////
////
//////        Label label3 = new Label(string, labelStyleBlack);
//////        label3.setWrap(true);
////
//////        lastLabel = label3;
////
//////        lastLabel.setColor(new Color(71.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1));
////
//////        label3.setText(textToAdd);
////
////        flechSprite.setSize(MyConstants.SCREENWIDTH / 30, 40);
////
////        SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(flechSprite);
////
////        Table pointerTable = new Table();
//////        if (cptInstructions == 0)
//////        {
////        pointerTable.setBackground(flecheSpriteDrawable);
//////        }
////        table4.add(pointerTable).width(MyConstants.SCREENWIDTH / 60).height(MyConstants.SCREENHEIGHT / 40).align(Align.center).padLeft(MyConstants.SCREENWIDTH / 70).padRight(MyConstants.SCREENWIDTH / 100);
////        table4.add(textToAdd).width(((MyConstants.SCREENWIDTH / 25) + (MyConstants.SCREENWIDTH / 120))).padRight(MyConstants.SCREENWIDTH / 120);
//////        }
//////        else
//////        {
//////            table4.add(new Image()).width(MyConstants.SCREENWIDTH / 27 - MyConstants.SCREENWIDTH / 1000);
//////            table4.add(label3).width(/*widthEnonce -*/ (MyConstants.SCREENWIDTH / 15)).padRight(MyConstants.SCREENWIDTH / 20);
//////        }
////
////
//////        int widthButton = 500;
//////        int heightButton = widthButton / 4;
//////        int cornerRadius = heightButton / 2;
//////        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, 0, Color.WHITE);
//////
//////        table4.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));
////
////
////        table4.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
////        //tableMilieu.removeActor()
////
////
////        tableMilieu.add(table4);
//////        tableMilieu.row();
////
////        table.pack();
////
//////        cptInstructions++;
////
////        float labelHeight = textToAdd.getHeight() + MyConstants.SCREENHEIGHT / 200;
////
////        topYTablePosition = MyConstants.SCREENHEIGHT - table.getHeight() - heightTop;
////
////        float nextTestY = currentY - labelHeight;
////        if (nextTestY > topYTablePosition)
////        {
////            currentY = currentY - labelHeight;
////            table.setY(currentY);
////        }
////        else
////        {
////            currentY = topYTablePosition;
////            table.setY(topYTablePosition);
////        }
////
////        return textToAdd;
////    }


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

    @Override
    public void myResume()
    {

    }

    @Override
    public boolean isPause()
    {
        return isPaused;
    }
}
