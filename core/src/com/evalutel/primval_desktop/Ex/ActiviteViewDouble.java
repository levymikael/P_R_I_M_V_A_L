//package com.evalutel.primval_desktop.Ex;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.Touchable;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextField;
//import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
//import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
//import com.badlogic.gdx.utils.Align;
//import com.evalutel.primval_desktop.General.MyConstants;
//import com.evalutel.primval_desktop.MyCorrectionAndPauseInterface;
//import com.evalutel.primval_desktop.MyDrawInterface;
//
//
//public class ActiviteViewDouble implements MyDrawInterface, MyCorrectionAndPauseInterface
//{
//    private Table table, tableTitre, tableBandeauBasBleu, tableBandeauBasVert;
//    private Table tableMilieuEnonce, tableMilieuSolution, table4, table5;
//    private float heightTop;
//    float topYTablePosition, heightBackGroundImage;
//
//    private float firstY, currentY, widthEnonce;
//
//    Texture texture2, texture3, textureTextEnonce, titreTop;
//
//    TextField textFieldEnonce, textFieldSolution;
//
//    int cptInstructions;
//
//    Sprite sprite2, sprite3, spriteEnonceText, spriteSolutionText;
//    BitmapFont bitmapFontArial;
//    BitmapFont bitmapFontComic;
//    private boolean isVisible = true;
//    private Texture textureMilieuEnonce;
//
//    String activiteType;
//
//    Label lastLabel;
//
//    Sprite flechSprite = new Sprite(new Texture(Gdx.files.internal("Images/EnonceUIElements/black_right_pointing_pointer.png")));
//
//    boolean isPaused;
//
//    Table lastPointerTable, lastPointerTable2;
//
//    public ActiviteViewDouble(Stage stage, float width, String numExercice, String consigneExercice, String exDansChapitre, String activiteType)
//    {
//        textureMilieuEnonce = new Texture("Images/EnonceUIElements/enonce_milieu_new.png");
//        textureMilieuEnonce.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//
//        widthEnonce = width;
//        heightTop = widthEnonce * 42 / 1626;
//
//        this.activiteType = activiteType;
//
//// Configuration police de l'enonce
//        FreeTypeFontGenerator fontArial = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = MyConstants.SCREENWIDTH / 70;
//        bitmapFontArial = fontArial.generateFont(parameter);
//        fontArial.dispose();
//
//        FreeTypeFontGenerator fontComic = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameterComic = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameterComic.size = MyConstants.SCREENWIDTH / 70;
//        bitmapFontComic = fontComic.generateFont(parameter);
//        fontComic.dispose();
//
//// Numero exerice/consigne:
//        Label.LabelStyle labelStyleArial = new Label.LabelStyle();
//        labelStyleArial.font = bitmapFontArial;
//        labelStyleArial.fontColor = Color.YELLOW;
//        Label exoNumLabel = new Label(numExercice, labelStyleArial);
//
//        Label.LabelStyle labelStyleComic = new Label.LabelStyle();
//        labelStyleComic.font = bitmapFontComic;
//        labelStyleComic.fontColor = Color.WHITE;
//        Label exoConsigneLabel = new Label(consigneExercice, labelStyleComic);
//
//        Label.LabelStyle labelStyle3 = new Label.LabelStyle();
//        labelStyle3.font = bitmapFontArial;
//        labelStyle3.fontColor = Color.YELLOW;
//        Label label3 = new Label(exDansChapitre, labelStyle3);
//        label3.setWidth(MyConstants.SCREENWIDTH / 46);
//
//// Creation cellule tableau pour numero d'exerice:
//        tableTitre = new Table();
//
//        Texture textureTitre = new Texture("Images/EnonceUIElements/titre_top.png");
//        textureTitre.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//
//        tableTitre.setBackground(new SpriteDrawable(new Sprite(textureTitre)));
//
//// Positionnement numero exercice:
//        tableTitre.add(exoNumLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 25).padLeft(MyConstants.SCREENWIDTH / 46);
//        tableTitre.add(exoConsigneLabel).width(widthEnonce - MyConstants.SCREENWIDTH / 9);
//        tableTitre.add(label3).align(Align.center).width(MyConstants.SCREENWIDTH / 22);
//
//        table = new Table();
//        stage.addActor(table);
//        stage.addActor(tableTitre);
//
//        tableMilieuEnonce = new Table();
//        tableMilieuSolution = new Table();
//
//        Table paddingTableMilieu = new Table();
//        paddingTableMilieu.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
//
//        Table paddingTableMilieu2 = new Table();
//        paddingTableMilieu2.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
//
//        table.add(paddingTableMilieu).height(MyConstants.SCREENHEIGHT / 200).width(widthEnonce / 2);
//        table.add(paddingTableMilieu2).height(MyConstants.SCREENHEIGHT / 200).width(widthEnonce / 2);
//        table.row();
//
//        table.add(tableMilieuEnonce).width((tableTitre.getWidth() / 2) + MyConstants.SCREENWIDTH / 100);
//        table.add(tableMilieuSolution).width((tableTitre.getWidth() / 2) + MyConstants.SCREENWIDTH / 100);
//        table.row();
//
//        Table paddingTableMilieu3 = new Table();
//        paddingTableMilieu3.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
//        Table paddingTableMilieu4 = new Table();
//        paddingTableMilieu4.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
//
//        table.add(paddingTableMilieu3).height(MyConstants.SCREENHEIGHT / 200).width(widthEnonce / 2);
//        table.add(paddingTableMilieu4).height(MyConstants.SCREENHEIGHT / 200).width(widthEnonce / 2);
//        table.row();
//
//
//        if (activiteType == "activite")
//        {
//            textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/activite.png"));
//        }
//        else if (activiteType == "enonce")
//        {
//            textureTextEnonce = new Texture(Gdx.files.internal("Images/EnonceUIElements/enonce_text.png"));
//        }
//        textureTextEnonce.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//
//
//        spriteEnonceText = new Sprite(textureTextEnonce);
//        TextField.TextFieldStyle textFieldStyleEnonce = new TextField.TextFieldStyle();
//        textFieldStyleEnonce.font = bitmapFontArial;
//        textFieldStyleEnonce.background = new SpriteDrawable(spriteEnonceText);
//        textFieldEnonce = new TextField("", textFieldStyleEnonce);
//
//        Texture txtureSolution = new Texture(Gdx.files.internal("Images/EnonceUIElements/texte_solution.png"));
//        txtureSolution.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        spriteSolutionText = new Sprite(txtureSolution);
//        TextField.TextFieldStyle textFieldStyleSolution = new TextField.TextFieldStyle();
//        textFieldStyleSolution.font = bitmapFontArial;
//        textFieldStyleSolution.background = new SpriteDrawable(spriteSolutionText);
//        textFieldSolution = new TextField("", textFieldStyleSolution);
//
//
//// Insertion texte.png dans tableau avec une imageBG.png:
//        tableBandeauBasBleu = new Table();
//        heightBackGroundImage = widthEnonce * 31 / 809;
//        texture2 = new Texture(Gdx.files.internal("Images/Enoncé-solution/Enoncé-fond.png"));
//        texture2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//
//        sprite2 = new Sprite(texture2);
//        tableBandeauBasBleu.setBackground(new SpriteDrawable(sprite2));
//
//        float heightImageEnonce = heightBackGroundImage * 2 / 3;
//        float widthImageEnonce = heightImageEnonce * 218 / 41;
//
//        tableBandeauBasBleu.add(textFieldEnonce).width(widthImageEnonce).height(heightImageEnonce);
//
//        tableBandeauBasVert = new Table();
//        texture3 = new Texture(Gdx.files.internal("Images/Enoncé-solution/Solution-fond.png"));
//        texture3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//
//        sprite3 = new Sprite(texture3);
//        tableBandeauBasVert.setBackground(new SpriteDrawable(sprite3));
//        tableBandeauBasVert.add(textFieldSolution).width(widthImageEnonce).height(heightImageEnonce);
//
//
//        table.add(tableBandeauBasBleu).width(widthEnonce / 2).height(heightBackGroundImage).align(Align.left);
//
//        table.add(tableBandeauBasVert).width(widthEnonce / 2).height(heightBackGroundImage).align(Align.left);
//
//
//        table.row();
//
//// Positionnement du tableau sur ecran:
//
//        tableTitre.pack();
//
//        float xTableTitre = (MyConstants.SCREENWIDTH / 2 - widthEnonce / 2);
//        tableTitre.setPosition(xTableTitre, MyConstants.SCREENHEIGHT - heightTop);
//
//        table.pack();
//        final float tableHeight = table.getHeight();
//        float temptableHeight = tableHeight;
//
//        topYTablePosition = MyConstants.SCREENHEIGHT - tableHeight - heightTop + (paddingTableMilieu.getHeight() * 2);
//
//        currentY = topYTablePosition;
//        table.setPosition(MyConstants.SCREENWIDTH / 2 - (widthEnonce / 2), topYTablePosition);
//
//        table.setTouchable(Touchable.enabled);
//
////Manipulation bandeau enonce (drag)
//
//        table.addListener(new DragListener()
//        {
//            @Override
//            public void dragStart(InputEvent event, float x, float y, int pointer)
//            {
//                super.dragStart(event, x, y, pointer);
//
//                table.pack();
//                firstY = y;
//                currentY = table.getY();
//            }
//
//            @Override
//            public void drag(InputEvent event, float x, float y, int pointer)
//            {
//                super.drag(event, x, y, pointer);
//
//                float moveY = y - firstY;
//                float nextY = currentY + moveY;
//
//                if (nextY < topYTablePosition) // si deplacement plus bas que bandeau --> bandeau reste immobile
//                {
//                    currentY = topYTablePosition;
//                    table.setY(topYTablePosition);
//                }
//                else if (nextY > (MyConstants.SCREENHEIGHT - heightBackGroundImage - heightTop)) // si souris depasse bandeau alors on cache texte consigne
//                {
//                    currentY = MyConstants.SCREENHEIGHT - heightBackGroundImage - heightTop;
//                    table.setY(MyConstants.SCREENHEIGHT - heightBackGroundImage - heightTop);
//                }
//                else
//                {
//                    currentY = currentY + moveY;
//                    table.setY(currentY);
//                }
//            }
//        });
//
//        tableTitre.setWidth(widthEnonce);
//        tableTitre.setHeight(heightTop);
//    }
//
//
//    public Label setTextActivite(String string)
//    {
//
//        emptyActivite();
//
//        return addTextActivite(string);
//
//    }
//
//    public Label addTextActivite(String string)
//    {
//        table4 = new Table();
//
//        Label.LabelStyle labelStyleBlack = new Label.LabelStyle();
//        labelStyleBlack.font = bitmapFontArial;
//        labelStyleBlack.fontColor = Color.BLACK;
//
//        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
//        labelStyleBlue.font = bitmapFontArial;
//        labelStyleBlue.fontColor = new Color(71.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1);
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
//        lastLabel.setColor(new Color(71.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1));
//
//
//        if (lastPointerTable != null)
//        {
//            lastPointerTable.remove();
//        }
//
//        flechSprite.setSize(MyConstants.SCREENWIDTH / 30, 40);
//
//        SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(flechSprite);
//
//        lastPointerTable = new Table();
//
//        if (activiteType == "activite")
//        {
//            lastPointerTable.setBackground(flecheSpriteDrawable);
//        }
//
//        table4.add(lastPointerTable).width(MyConstants.SCREENWIDTH / 60).height(MyConstants.SCREENHEIGHT / 40).align(Align.left).padLeft(MyConstants.SCREENWIDTH / 70).padRight(MyConstants.SCREENWIDTH / 100)/*.padTop(MyConstants.SCREENHEIGHT / 20)*/;
//        table4.add(label3).width(widthEnonce / 2 - ((MyConstants.SCREENWIDTH / 25) + (MyConstants.SCREENWIDTH / 120))).padRight(MyConstants.SCREENWIDTH / 120)/*.padTop(MyConstants.SCREENHEIGHT / 80).padBottom(MyConstants.SCREENHEIGHT / 200)*/;
//
////        if (cptInstructions == 0)
////        {
////            flechSprite.setSize(MyConstants.SCREENWIDTH / 30, 40);
////
////            SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(flechSprite);
////
////            Table pointerTable = new Table();
////
////            if (activiteType == "activite")
////            {
////                pointerTable.setBackground(flecheSpriteDrawable);
////            }
////            table4.add(pointerTable).width(MyConstants.SCREENWIDTH / 60).height(MyConstants.SCREENHEIGHT / 40).align(Align.center).padLeft(MyConstants.SCREENWIDTH / 70).padRight(MyConstants.SCREENWIDTH / 100)/*.padTop(MyConstants.SCREENHEIGHT / 20)*/;
////            table4.add(label3).width(widthEnonce - ((MyConstants.SCREENWIDTH / 25) + (MyConstants.SCREENWIDTH / 120))).padRight(MyConstants.SCREENWIDTH / 120)/*.padTop(MyConstants.SCREENHEIGHT / 80).padBottom(MyConstants.SCREENHEIGHT / 200)*/;
////        }
////        else
////        {
////            table4.add(new Image()).width(MyConstants.SCREENWIDTH / 27 - MyConstants.SCREENWIDTH / 1000);
////            table4.add(label3).width(widthEnonce - (MyConstants.SCREENWIDTH / 12 + MyConstants.SCREENWIDTH / 450)).padRight(MyConstants.SCREENWIDTH / 20) /*.padBottom(MyConstants.SCREENHEIGHT / 150).padTop(MyConstants.SCREENHEIGHT / 200)*/;
////        }
//
//        table4.setBackground(new SpriteDrawable(new Sprite(textureMilieuEnonce)));
//        tableMilieuEnonce.add(table4);
//        tableMilieuEnonce.row();
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
//    public void emptyActivite()
//    {
//        tableMilieuEnonce.clear();
//    }
//
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
//        labelStyleBlue.fontColor = new Color(71.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1);
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
//        lastLabel.setColor(new Color(71.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1));
//
//
//        if (lastPointerTable != null)
//        {
//            lastPointerTable.remove();
//        }
//
//        flechSprite.setSize(MyConstants.SCREENWIDTH / 30, 40);
//        flechSprite.setColor(new Color(71.0f / 255.0f, 107.0f / 255.0f, 217.0f / 255.0f, 1));
//
//        SpriteDrawable flecheSpriteDrawable = new SpriteDrawable(flechSprite);
//
//        lastPointerTable2 = new Table();
//
//            lastPointerTable2.setBackground(flecheSpriteDrawable);
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
//
//
//    @Override
//    public boolean isVisible()
//    {
//        return isVisible;
//    }
//
//    @Override
//    public void setVisible(boolean visible)
//    {
//        isVisible = visible;
//    }
//
//    @Override
//    public void myDraw(Batch batch)
//    {
//
//    }
//
//    @Override
//    public void myCorrectionStart()
//    {
//
//    }
//
//    @Override
//    public void myCorrectionStop()
//    {
//
//    }
//
//    @Override
//    public void myPause()
//    {
//
//    }
//
//    @Override
//    public void myResume()
//    {
//
//    }
//
//    @Override
//    public boolean isPause()
//    {
//        return isPaused;
//    }
//}
