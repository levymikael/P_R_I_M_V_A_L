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
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;


public class ActiviteView implements MyDrawInterface, MyCorrectionAndPauseInterface
{
    private Table table, tableTitre;
    private Table tableMilieu, tableMilieuSolution, table4, table5;
    private float heightTop;
    float topYTablePosition, heightBackGroundImage;

    private float firstY, currentY, widthEnonce;

    Texture textureTextEnonce;

    TextField textFieldEnonce;

    int cptInstructions;

    Sprite sprite2, sprite3, spriteEnonceText, spriteSolutionText;
    BitmapFont bitmapFontArial, bitmapFontComic;
    private boolean isVisible = true;
    private Texture textureMilieuEnonce;

    String activiteType;

    Label lastLabel, label1;

    Sprite flechSprite = new Sprite(new Texture(Gdx.files.internal("Images/EnonceUIElements/black_right_pointing_pointer.png")));

    Texture fleche = new Texture(Gdx.files.internal("Images/EnonceUIElements/black_right_pointing_pointer.png"));

    boolean isPaused;

    Table lastPointerTable;

    public ActiviteView(Stage stage, float positionX, float positionY, float width, String activiteType)
    {
        textureMilieuEnonce = new Texture("Images/EnonceUIElements/enonce_milieu_new.png");
        textureMilieuEnonce.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        widthEnonce = width;

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

        table = new Table();
        tableMilieu = new Table();
        tableMilieuSolution = new Table();
        Table paddingTableMilieu = new Table();
        Table paddingTableMilieu2 = new Table();
        Table tableBandeauBas = new Table();
        Table tableBandeauBasDroite = new Table();
        Table tableBandeauBasGauche = new Table();
        Table tableBandeauBasCentreVierge = new Table();
        Table tableBandeauBasCentreVierge2 = new Table();
        Table tableBandeauBasCentreEnonce = new Table();

        stage.addActor(table);

        float paddingHeightTableMilieu = MyConstants.SCREENHEIGHT / 200;

        paddingTableMilieu.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
        paddingTableMilieu2.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));

        table.add(paddingTableMilieu).height(paddingHeightTableMilieu).width(widthEnonce);
        table.row();
        table.add(tableMilieu).width(widthEnonce/* + MyConstants.SCREENWIDTH / 100*/);
        table.row();
        table.add(paddingTableMilieu2).height(paddingHeightTableMilieu).width(widthEnonce);
        table.row();


        if (activiteType == "activite")
        {
            textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/activite.png"));
        }
        else if (activiteType == "enonce")
        {
            textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_text.png"));
        }
        else if (activiteType == "solution")
        {
            textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/texte_solution.png"));
        }
        textureTextEnonce.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        TextField.TextFieldStyle textFieldStyleEnonce = new TextField.TextFieldStyle();
        textFieldStyleEnonce.font = bitmapFontArial;
        textFieldStyleEnonce.background = new SpriteDrawable(new Sprite(textureTextEnonce));
        textFieldEnonce = new TextField("", textFieldStyleEnonce);

// Insertion texte.png dans tableau avec une imageBG.png:
        float widthCote = MyConstants.SCREENWIDTH / 40f;
        heightBackGroundImage = widthCote * 29f / 28f;

        heightTop = positionY;


        float widthCentre = 300f;

        float widthVierge = (widthEnonce - 2 * widthCote - widthCentre) / 2f;

        //heightBackGroundImage = widthEnonce * 31 / 809;
        float heightImageEnonce = heightBackGroundImage * 2f / 3f;
        float widthImageEnonce = heightImageEnonce * 218f / 41f;

        String txtureDroitePath = "";
        String txtureGauchePath = "";
        String txtureCentrePath = "";

        if (activiteType == "activite" || activiteType == "enonce")
        {
            txtureDroitePath = "Images/Enoncé-solution/droit bleu.png";
            txtureGauchePath = "Images/Enoncé-solution/gauche bleu.png";
            txtureCentrePath = "Images/Enoncé-solution/centre bleu.png";
        }
        else if (activiteType == "solution")
        {
            txtureDroitePath = "Images/Enoncé-solution/droit vert.png";
            txtureGauchePath = "Images/Enoncé-solution/gauche vert.png";
            txtureCentrePath = "Images/Enoncé-solution/centre vert.png";
        }

        Texture txtureDroite = new Texture(Gdx.files.internal(txtureDroitePath));
        Texture txtureGauche = new Texture(Gdx.files.internal(txtureGauchePath));
        Texture txtureCentre = new Texture(Gdx.files.internal(txtureCentrePath));

        txtureCentre.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        txtureDroite.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        txtureGauche.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        tableBandeauBasDroite.setBackground(new SpriteDrawable(new Sprite(txtureDroite)));
        tableBandeauBasGauche.setBackground(new SpriteDrawable(new Sprite(txtureGauche)));
        tableBandeauBasCentreVierge.setBackground(new SpriteDrawable(new Sprite(txtureCentre)));
        tableBandeauBasCentreVierge2.setBackground(new SpriteDrawable(new Sprite(txtureCentre)));
        tableBandeauBasCentreEnonce.setBackground(new SpriteDrawable(new Sprite(txtureCentre)));

        tableBandeauBasCentreEnonce.add(textFieldEnonce).height(heightImageEnonce).width(widthImageEnonce);


        tableBandeauBas.add(tableBandeauBasGauche).width(widthCote).height(heightBackGroundImage);
        tableBandeauBas.add(tableBandeauBasCentreVierge).width(widthVierge).height(heightBackGroundImage);
        tableBandeauBas.add(tableBandeauBasCentreEnonce).width(widthCentre).height(heightBackGroundImage);
        tableBandeauBas.add(tableBandeauBasCentreVierge2).width(widthVierge).height(heightBackGroundImage);
        tableBandeauBas.add(tableBandeauBasDroite).width(widthCote).height(heightBackGroundImage);

        table.add(tableBandeauBas).width(widthEnonce).height(heightBackGroundImage);

        table.row();


// Positionnement du tableau sur ecran:
        table.pack();
        final float tableHeight = table.getHeight();
        float temptableHeight = tableHeight;

        topYTablePosition = MyConstants.SCREENHEIGHT - tableHeight - heightTop + (paddingTableMilieu.getHeight() * 2);

        currentY = topYTablePosition;

        table.setPosition(positionX, topYTablePosition);

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
        labelStyleBlue.fontColor = new Color(71f / 255f, 107f / 255f, 217f / 255f, 1);

        if (cptInstructions != 0)
        {
            lastLabel.setStyle(labelStyleBlack);
        }

        Label label3 = new Label(string, labelStyleBlue);
        label3.setWrap(true);

        lastLabel = label3;

        lastLabel.setColor(new Color(71f / 255f, 107f / 255f, 217f / 255f, 1));

        if (lastPointerTable != null)
        {
            lastPointerTable.remove();
        }

        flechSprite.setSize(MyConstants.SCREENWIDTH / 30f, MyConstants.SCREENWIDTH / 30f);

        SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(flechSprite);

        lastPointerTable = new Table();


        if (activiteType == "activite")
        {
//            lastPointerTable.setBackground(flecheSpriteDrawable);
            lastPointerTable.add(new Image(fleche));
        }


        table4.add(lastPointerTable).top().center().height(MyConstants.SCREENHEIGHT / 50f).width(MyConstants.SCREENWIDTH / 100f).align(Align.top).padRight(MyConstants.SCREENWIDTH/100f).padTop(MyConstants.SCREENHEIGHT/200f);

//        if (activiteType == "enonce" || activiteType == "activite")
//        {
//             label1 = new Label("1.", labelStyleBlue);
//            table4.add(label1).width((MyConstants.SCREENWIDTH / 35));
//
//        }

        table4.add(label3).width(widthEnonce - ((MyConstants.SCREENWIDTH / 25f) + (MyConstants.SCREENWIDTH / 110f))).padRight(MyConstants.SCREENWIDTH / 120f)/*.padTop(MyConstants.SCREENHEIGHT / 80).padBottom(MyConstants.SCREENHEIGHT / 200)*/;

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
        tableMilieu.add(table4).width(widthEnonce);
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

//    public Label setTextSolution(String string)
//    {
//
//        emptySolution();
//
//        return addTextActivite(string);
//
//    }
//
//    public Label addTextSolution(String string)
//    {
//        table5 = new Table();
//
//        Label.LabelStyle labelStyleBlack = new Label.LabelStyle();
//        labelStyleBlack.font = bitmapFontArial;
//        labelStyleBlack.fontColor = Color.BLACK;
//
//        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
//        labelStyleBlue.font = bitmapFontArial;
//        labelStyleBlue.fontColor = new Color(Color.valueOf("004ec0"));
//
//        if (cptInstructions != 0)
//        {
//            lastLabel.setStyle(labelStyleBlack);
//        }
//
//        Label label3 = new Label(string, labelStyleBlue);
//        label3.setWrap(true);
//
//        lastLabel = label3;
//
//        lastLabel.setColor(new Color(Color.valueOf("004ec0")));
//
//
//        if (lastPointerTable != null)
//        {
//            lastPointerTable.remove();
//        }
//
//        flechSprite.setSize(MyConstants.SCREENWIDTH / 30, 40);
//        flechSprite.setColor(new Color(71f / 255f, 107f / 255f, 217f / 255f, 1));
//
//        SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(flechSprite);
//
//        lastPointerTable2 = new Table();
//
//        lastPointerTable2.setBackground(flecheSpriteDrawable);
//
//
//        table5.add(lastPointerTable2).width(MyConstants.SCREENWIDTH / 60).height(MyConstants.SCREENHEIGHT / 40).align(Align.left).padLeft(MyConstants.SCREENWIDTH / 70).padRight(MyConstants.SCREENWIDTH / 100)/*.padTop(MyConstants.SCREENHEIGHT / 20)*/;
//        table5.add(label3).width(widthEnonce / 2 - ((MyConstants.SCREENWIDTH / 25) + (MyConstants.SCREENWIDTH / 120))).padRight(MyConstants.SCREENWIDTH / 120)/*.padTop(MyConstants.SCREENHEIGHT / 80).padBottom(MyConstants.SCREENHEIGHT / 200)*/;
//
//
//        table5.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
//        tableMilieuSolution.add(table5);
//        tableMilieuSolution.row();
//
//        table.pack();
//
//        cptInstructions++;
//
//        float labelHeight = label3.getHeight() + MyConstants.SCREENHEIGHT / 100;
//
//        topYTablePosition = MyConstants.SCREENHEIGHT - table.getHeight() - heightTop;
//
//
//        float nextTestY = currentY - labelHeight;
//        if (nextTestY > topYTablePosition)
//        {
//            currentY = currentY - labelHeight;
//            table.setY(currentY);
//        }
//        else
//        {
//            currentY = topYTablePosition;
//            table.setY(topYTablePosition);
//        }
//
//        return label3;
//    }
//
//
//    public void emptySolution()
//    {
//        tableMilieuSolution.clear();
//    }
////    public Label addText(String str)
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
//////        labelStyleBlue.fontColor = new Color(71f / 255f, 107f / 255f, 217f / 255f, 1);
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
//////        lastLabel.setColor(new Color(71f / 255f, 107f / 255f, 217f / 255f, 1));
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
