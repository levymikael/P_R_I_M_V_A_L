package com.evalutel.primval_desktop.Sommaire;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.General.BoutonChapitres;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MrNotes2;
import com.evalutel.primval_desktop.MrTemps2;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.onglets.chapitre1.Screen_Chapitre1;
import com.evalutel.primval_desktop.onglets.chapitre2.Screen_Chapitre2;

import java.util.ArrayList;


public class Screen_All_Chapters extends Game implements Screen, InputProcessor, ApplicationListener
{
    protected Stage stage;

    private SpriteBatch batch;
    private Game game;

    private ScreeenBackgroundImage bandeauHaut, fondSommaire;

    private float bandeauHautY, paddingMottoDetailsBorder, heightlabelTitle, tableWidth, heightTable, paddingManipulerApprendreEvaluer, paddingCoteEvalutelMotto;


    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();

    private TextureRegionDrawable textureRegionDrawableBg;

    private Label.LabelStyle labelStyleWhite, labelStyleBlueArialBold, labelStyleBlueFRHND, labelStyleBlueArial, labelStyleBlueZap;

    Pixmap pmWhite, pmBlueBorder, bgPixmapLightBlue;


    private BitmapFont bitmapFontZAP, bitmapFontArialBold, bitmapFontArial, bitmapFontFRHND;


    public Screen_All_Chapters(Game game)
    {
        this.game = game;

        stage = new Stage();
        batch = new SpriteBatch();


        int fontSize = MyConstants.SCREENWIDTH / 60;


        paddingMottoDetailsBorder = /*MyConstants.SCREENWIDTH / 1000f*/2;
        heightlabelTitle = MyConstants.SCREENHEIGHT / 20f;
        tableWidth = MyConstants.SCREENWIDTH / 4f;
        heightTable = MyConstants.SCREENHEIGHT / 5.5f;
        paddingManipulerApprendreEvaluer = MyConstants.SCREENWIDTH / 70f;
        paddingCoteEvalutelMotto = MyConstants.SCREENHEIGHT / 50f;


        FreeTypeFontGenerator generatorFONT_FRHND = new FreeTypeFontGenerator(Gdx.files.internal("font/FRHND521_0.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterFRHND = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterFRHND.size = fontSize;
        parameterFRHND.minFilter = Texture.TextureFilter.Linear;
        parameterFRHND.magFilter = Texture.TextureFilter.Linear;
        bitmapFontFRHND = generatorFONT_FRHND.generateFont(parameterFRHND);
        generatorFONT_FRHND.dispose();


        FreeTypeFontGenerator FONT_ZAP = new FreeTypeFontGenerator(Gdx.files.internal("font/Zapf Humanist 601 BT.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterZAP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterZAP.size = MyConstants.SCREENWIDTH / 70;
        parameterZAP.minFilter = Texture.TextureFilter.Linear;
        parameterZAP.magFilter = Texture.TextureFilter.Linear;
        bitmapFontZAP = FONT_ZAP.generateFont(parameterZAP);
        FONT_ZAP.dispose();

        FreeTypeFontGenerator arialBold = new FreeTypeFontGenerator(Gdx.files.internal("font/arial-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterArialBold = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterArialBold.size = MyConstants.SCREENWIDTH / 70;
        parameterArialBold.minFilter = Texture.TextureFilter.Linear;
        parameterArialBold.magFilter = Texture.TextureFilter.Linear;
        bitmapFontArialBold = arialBold.generateFont(parameterArialBold);
        arialBold.dispose();

        FreeTypeFontGenerator arial = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterArial = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterArial.size = MyConstants.SCREENWIDTH / 70;
        parameterArial.minFilter = Texture.TextureFilter.Linear;
        parameterArial.magFilter = Texture.TextureFilter.Linear;
        bitmapFontArial = arial.generateFont(parameterArial);
        arial.dispose();

        labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFontFRHND;
        labelStyleWhite.fontColor = Color.WHITE;

        labelStyleBlueFRHND = new Label.LabelStyle();
        labelStyleBlueFRHND.font = bitmapFontFRHND;
        labelStyleBlueFRHND.fontColor = MyConstants.bluePrimval;


        labelStyleBlueArialBold = new Label.LabelStyle();
        labelStyleBlueArialBold.font = bitmapFontArialBold;
        labelStyleBlueArialBold.fontColor = MyConstants.bluePrimval;

        labelStyleBlueArial = new Label.LabelStyle();
        labelStyleBlueArial.font = bitmapFontArial;
        labelStyleBlueArial.fontColor = MyConstants.bluePrimval;

        labelStyleBlueZap = new Label.LabelStyle();
        labelStyleBlueZap.font = bitmapFontZAP;
        labelStyleBlueZap.fontColor = MyConstants.bluePrimval;

        allDrawables = new ArrayList<>();

        bandeauHaut = new ScreeenBackgroundImage("Images/Pages Chapitres/Bandeau haut.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Backgrounds/web_hi_res_512.png");

        pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();


        pmBlueBorder = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlueBorder.setColor(MyConstants.bluePrimval2);
        pmBlueBorder.fill();


        bgPixmapLightBlue = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmapLightBlue.setColor(Color.rgb888(234, 241, 250));
        bgPixmapLightBlue.fill();

        MyButtonRetour myButtonRetour = new MyButtonRetour(stage, MyConstants.SCREENWIDTH / 15f, MyConstants.SCREENWIDTH / 15f, game, "sommaire general");
        myButtonRetour.setPosition(MyConstants.SCREENWIDTH * .02f, 5 * MyConstants.SCREENHEIGHT / 6f - myButtonRetour.getHeight() / 2f);


        Texture chapter1Title = new Texture(Gdx.files.internal("Images/Pages Chapitres/titre Calcul et géométrie.png"));
        chapter1Title.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        Table allChaptersTitle = new Table();

        float chapterTitleWidth = MyConstants.SCREENWIDTH / 2.5f;
        float chapterTitleHeight = chapterTitleWidth * (55f / 387f);

        bandeauHautY = (MyConstants.SCREENHEIGHT - MyConstants.SCREENHEIGHT / 6f);


        allChaptersTitle.setBackground(new SpriteDrawable(new Sprite(chapter1Title)));
        allChaptersTitle.setSize(chapterTitleWidth, chapterTitleHeight);
        allChaptersTitle.setPosition(MyConstants.SCREENWIDTH / 2f - allChaptersTitle.getWidth() / 2, bandeauHautY + ((MyConstants.SCREENHEIGHT - bandeauHautY) / 4f));
        stage.addActor(allChaptersTitle);

        MrNotes2 mrNotes = new MrNotes2(stage, 22.5f * MyConstants.SCREENWIDTH / 25f, 4 * MyConstants.SCREENHEIGHT / 5f, "all chapters");
        MrTemps2 mrTemps = new MrTemps2(stage);


        //tableau deroulant pour Evalutel motto et liste de chapitre
        Table container = new Table();
        Table table = new Table();

        float heightContainer = (myButtonRetour.getY());
        container.setSize(MyConstants.SCREENWIDTH, heightContainer);
        container.setPosition(0, 0);

        Table evalutelMotto = evalutelMotto();

        Texture chapitresLogo = new Texture(Gdx.files.internal("Images/Sommaire/Chapitres.png"));
        chapitresLogo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Table chapterLogo = new Table();
        chapterLogo.setBackground(new SpriteDrawable(new Sprite(chapitresLogo)));


        float chapterLogoWidth = MyConstants.SCREENWIDTH / 6f;
        float chapterLogoHeight = chapterLogoWidth * 41f / 161f;

        Table chaptersListView = chaptersListView();

        table.add(evalutelMotto).align(Align.center).padTop(MyConstants.SCREENHEIGHT / 50f).width(MyConstants.SCREENWIDTH * .96f).padLeft(MyConstants.SCREENWIDTH * .02f).padRight(MyConstants.SCREENWIDTH * .02f);
        table.row();
        table.add(chapterLogo).width(chapterLogoWidth).height(chapterLogoHeight).align(Align.center).padBottom(MyConstants.SCREENHEIGHT / 60f).padTop(MyConstants.SCREENHEIGHT / 50f);
        table.row();
        table.add(chaptersListView).width(MyConstants.SCREENWIDTH).align(Align.center).padBottom(MyConstants.SCREENHEIGHT / 20f);

        table.setWidth(MyConstants.SCREENWIDTH);

        ScrollPane scroll = new ScrollPane(table);
        scroll.layout();

        container.add(scroll).height(heightContainer);

        stage.addActor(container);

        Gdx.input.setInputProcessor(stage);
    }

    public Table border(Table innerTable)
    {
        Table border = new Table();
        border.pad(2);
        border.setBackground(new SpriteDrawable(new Sprite(new Texture(pmBlueBorder))));
        border.add(innerTable);

        return border;
    }


    public Table innerTitle(String innerTitle)
    {
        Table innerTitleTable = new Table();

        Label labelManipulerTitle = new Label(innerTitle, labelStyleBlueArialBold);

        innerTitleTable.add(labelManipulerTitle).width(tableWidth).align(Align.center);
        innerTitleTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));

        return innerTitleTable;
    }

    public Table innerTable(String content)
    {
        Table innerContentTable = new Table();

        Label labelContentText = new Label(content, labelStyleBlueArial);
        labelContentText.setWrap(true);

        innerContentTable.add(labelContentText).width(tableWidth).height(heightTable);
        innerContentTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));

        return innerContentTable;
    }

    public Table manipulerTable()
    {
        Table manipulerTable = new Table();

        Table manipulerTitleTable = new Table();
        manipulerTitleTable.add(innerTitle("MANIPULER")).align(Align.center);

        String manipulerTableContent = "Des objets interactifs et ludiques sont conçus pour permettre à l'enfant de comprendre la numération , les opérations arithmétiques et les notions de base de géométrie.";
        Table manipulerContentTable = new Table();
        manipulerContentTable.add(innerTable(manipulerTableContent));

        manipulerTable.add(manipulerTitleTable).width(tableWidth).center();
        manipulerTable.row();
        manipulerTable.add(manipulerContentTable).width(tableWidth).height(heightTable)/*.width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).padRight(paddingManipulerApprendreEvaluer).padBottom(MyConstants.SCREENHEIGHT / 40f).align(Align.top).fillY()*/;
//        manipulerTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));


        return manipulerTable;
    }

    public Table apprendreTable()
    {
        Table apprendreTable = new Table();
        Label labelApprendreTitle = new Label("APPRENDRE", labelStyleBlueArialBold);
        Label labelApprendreText = new Label("Les notions nouvelles sont introduites par des cours et des exemples. Chaque manipulation est mise en correspondance avec l'opération que l'enfant est invité à faire sur l'ardoise et sur la solution, tout en étant corrigé pas à pas.", labelStyleBlueArial);
        labelApprendreText.setWrap(true);

        apprendreTable.add(labelApprendreTitle).prefHeight(heightlabelTitle);
        apprendreTable.row();
        apprendreTable.add(labelApprendreText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).padRight(paddingManipulerApprendreEvaluer).padBottom(MyConstants.SCREENHEIGHT / 100f);
        apprendreTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));
        return apprendreTable;
    }

    public Table evaluerTable()
    {
        Table evaluerTable = new Table();
        Label labelEvaluerTitle = new Label("EVALUER", labelStyleBlueArialBold);
        Label labelEvaluerText = new Label("Tous les exercices/problèmes sont notés. L'enfant peut toujours améliorer sa note en refaisant l'exercice. Un tableau récapitule les sujets abordés complètement ou partiellement, les temps passés et les résultats obtenus.       ", labelStyleBlueArial);
        labelEvaluerText.setWrap(true);

        evaluerTable.add(labelEvaluerTitle).prefHeight(heightlabelTitle);
        evaluerTable.row();
        evaluerTable.add(labelEvaluerText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).padRight(paddingManipulerApprendreEvaluer).align(Align.top)/*.padBottom(MyConstants.SCREENHEIGHT / 100f)*/;
        evaluerTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));

        evaluerTable.row();

        return evaluerTable;
    }


    public Table evalutelMotto()
    {
        Label.LabelStyle labelStyleBlueArialBold = new Label.LabelStyle();
        labelStyleBlueArialBold.font = bitmapFontArialBold;
        labelStyleBlueArialBold.fontColor = MyConstants.bluePrimval;

        Label.LabelStyle labelStyleBlueArial = new Label.LabelStyle();
        labelStyleBlueArial.font = bitmapFontArial;
        labelStyleBlueArial.fontColor = MyConstants.bluePrimval;

        Label.LabelStyle labelStyleBlueZap = new Label.LabelStyle();
        labelStyleBlueZap.font = bitmapFontZAP;
        labelStyleBlueZap.fontColor = MyConstants.bluePrimval;

        Label labelMottoTitle = new Label("Primval développé par Evalutel, propose un programe complet de Math et de géométrie pour le Primaire basé sur notre devise:", labelStyleBlueArialBold);
        labelMottoTitle.setFontScale(1f);

        int widthButton = 1000;
        int heightButton = widthButton / 5;
        int cornerRadius = heightButton / 8;

//        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, Color.WHITE);
//        Pixmap blueRoundedBackground2 = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, new Color(234.0f / 255.0f, 241.0f / 255.0f, 250.0f / 255.0f, 1));
//        Pixmap blueRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, Color.ROYAL);


        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.rgb888(234, 241, 250));
        bgPixmap.fill();

        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlue.setColor(Color.NAVY);
        pmBlue.fill();

        textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue)));

        int paddingMottoDetailsBorder = MyConstants.SCREENWIDTH / 1000;
        int heightlabelTitle = MyConstants.SCREENHEIGHT / 25;
        int tableWidth = MyConstants.SCREENWIDTH / 4;
        float heightTable = MyConstants.SCREENHEIGHT / 5f;
        int paddingManipulerApprendreEvaluer = MyConstants.SCREENWIDTH / 70;
        int paddingCoteEvalutelMotto = MyConstants.SCREENHEIGHT / 50;

        int leftPaddingBorderEvalutelDetails = MyConstants.SCREENWIDTH / 80;

        Table evalutelMotto = new Table();
        evalutelMotto.setBackground(new SpriteDrawable(new Sprite(new Texture(bgPixmapLightBlue))));

        evalutelMotto.add(labelMottoTitle).padBottom(leftPaddingBorderEvalutelDetails).align(Align.center);
        evalutelMotto.padTop(paddingCoteEvalutelMotto).padRight(paddingCoteEvalutelMotto);
        evalutelMotto.row();

        Table evalutelMottoDetails = new Table();


        Table apprendreTable = new Table();
        Label labelApprendreTitle = new Label("APPRENDRE", labelStyleBlueArialBold);
        Label labelApprendreText = new Label("Les notions nouvelles sont introduites par des cours et des exemples. Chaque manipulation est mise en correspondance avec l'opération que l'enfant est invité à faire sur l'ardoise et sur la solution, tout en étant corrigé pas à pas.", labelStyleBlueArial);
        labelApprendreText.setWrap(true);

        apprendreTable.add(labelApprendreTitle).height(heightlabelTitle);
        apprendreTable.row();
        apprendreTable.add(labelApprendreText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).height(heightTable).padRight(paddingManipulerApprendreEvaluer);
        apprendreTable.setBackground(new SpriteDrawable(new Sprite(new Texture(pmWhite))));

        Table borderApprendre = new Table();
        borderApprendre.pad(paddingMottoDetailsBorder);
        borderApprendre.setBackground(new SpriteDrawable(new Sprite(new Texture(pmBlueBorder))));
        borderApprendre.add(apprendreTable);

        Table manipulerTable = new Table();
        Label labelManipulerTitle = new Label("MANIPULER", labelStyleBlueArialBold);
        Label labelManipulerText = new Label("Des objets interactifs et ludiques sont conçus pour permettre à l'enfant de comprendre la numération , les opérations arithmétiques et les notions de base de géométrie.", labelStyleBlueArial);
        labelManipulerText.setWrap(true);

        manipulerTable.add(labelManipulerTitle).height(heightlabelTitle);
        manipulerTable.row();
        manipulerTable.add(labelManipulerText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).height(heightTable).padRight(paddingManipulerApprendreEvaluer);
        manipulerTable.setBackground(new SpriteDrawable(new Sprite(new Texture(pmWhite))));

        Table borderManipuler = new Table();
        borderManipuler.pad(paddingMottoDetailsBorder);
        borderManipuler.setBackground(new SpriteDrawable(new Sprite(new Texture(pmBlueBorder))));
        borderManipuler.add(manipulerTable);


        Table evaluerTable = new Table();
        Label labelEvaluerTitle = new Label("EVALUER", labelStyleBlueArialBold);
        Label labelEvaluerText = new Label("Tous les exercices/problèmes sont notés. L'enfant peut toujours améliorer sa note en refaisant l'exercice. Un tableau récapitule les sujets abordés complètement ou partiellement, les temps passés et les résultats obtenus.", labelStyleBlueArial);
        labelEvaluerText.setWrap(true);

        evaluerTable.add(labelEvaluerTitle).height(heightlabelTitle).padBottom(MyConstants.SCREENHEIGHT / 400f);
        evaluerTable.row();
        evaluerTable.add(labelEvaluerText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).height(heightTable).padRight(paddingManipulerApprendreEvaluer);
        evaluerTable.setBackground(new SpriteDrawable(new Sprite(new Texture(pmWhite))));

        Table borderEvaluer = new Table();
        borderEvaluer.pad(paddingMottoDetailsBorder);
        borderEvaluer.setBackground(new SpriteDrawable(new Sprite(new Texture(pmBlueBorder))));
        borderEvaluer.add(evaluerTable);

        evalutelMottoDetails.add(borderManipuler).padLeft(leftPaddingBorderEvalutelDetails);
        evalutelMottoDetails.add(borderApprendre).padLeft(leftPaddingBorderEvalutelDetails);
        evalutelMottoDetails.add(borderEvaluer).padLeft(leftPaddingBorderEvalutelDetails);
        evalutelMotto.add(evalutelMottoDetails).padBottom(leftPaddingBorderEvalutelDetails).width(MyConstants.SCREENWIDTH * .94f);

        Table border = new Table();
        border.pad(2);
        border.setBackground(new SpriteDrawable(new Sprite(new Texture(pmBlueBorder))));
        border.add(evalutelMotto).height(MyConstants.SCREENHEIGHT / 3f);

        return border;
    }

//    public Table evalutelMotto()
//    {
//        Label labelMottoTitle = new Label("Primval développé par Evalutel, propose un programe complet de Math et de géométrie pour le Primaire basé sur notre devise:", labelStyleBlueArialBold);
//
////        int widthButton = 1000;
////        int heightButton = widthButton / 5;
////        int cornerRadius = heightButton / 20;
////
//////        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, Color.WHITE);
//////        Pixmap lightBlueRoundedBackground2 = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, new Color(234f / 255f, 241f / 255f, 250f / 255f, 1));
//////        Pixmap blueRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, MyConstants.bluePrimval);
//////
//
//        Pixmap bgPixmapLightBlue = new Pixmap(1, 1, Pixmap.Format.RGB565);
//        bgPixmapLightBlue.setColor(Color.rgb888(234, 241, 250));
//        bgPixmapLightBlue.fill();
//
//        float leftPaddingBorderEvalutelDetails = MyConstants.SCREENWIDTH / 80f;
//
//        Table evalutelMotto = new Table();
//        evalutelMotto.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmapLightBlue))));
//
//        evalutelMotto.add(labelMottoTitle).padBottom(leftPaddingBorderEvalutelDetails).align(Align.center).height(heightlabelTitle);
//        evalutelMotto.padTop(paddingCoteEvalutelMotto);
//        evalutelMotto.row();
//
//        Table evalutelMottoDetails = new Table();
//        float MottoDetailsTableHeight = MyConstants.SCREENHEIGHT / 15f;
//
//        Table manipulerTable = border(manipulerTable());
//        Table apprendreTable = border(apprendreTable());
//        Table evaluerTable = border(evaluerTable());
//
//
//        evalutelMottoDetails.add(manipulerTable).padLeft(leftPaddingBorderEvalutelDetails).padBottom(MyConstants.SCREENHEIGHT / 100f).fillY();
//        evalutelMottoDetails.add(apprendreTable).padLeft(leftPaddingBorderEvalutelDetails).padBottom(MyConstants.SCREENHEIGHT / 100f).fillY();
//        evalutelMottoDetails.add(evaluerTable).padLeft(leftPaddingBorderEvalutelDetails).padBottom(MyConstants.SCREENHEIGHT / 100f).fill();
//        evalutelMotto.add(evalutelMottoDetails);
//
//        Table border = new Table();
//        border.pad(2);
//        border.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmBlueBorder))));
//
//        border.add(evalutelMotto);
//
//        return border;
//    }

    public Table chaptersListView()
    {
        String chapterLabel1 = "Pratique des nombres de 1 à 9";
        String chapterLabel2 = "Introduction de l\'addition ";
        String chapterLabel3 = "Les nombres de 1 à 69 . \nIntroduction du zéro et des dizaines";
        String chapterLabel4 = "Les nombres de 1 à 99 . \n Additions sans retenue ";
        String chapterLabel5 = "Additions avec retenue \n Calcul mental";
        String chapterLabel6 = "Outils de la géométrie. \n Triangle. Points alignés ";

        Table table = new Table();
        Table tableEx1 = BoutonChapitres.getLigne("Images/Pages Chapitres/chapitre 01.jpg", "Images/IndicesChapitres/chap1.png", chapterLabel1);
        Table tableEx2 = BoutonChapitres.getLigne("Images/Pages Chapitres/chapitre 02.jpg", "Images/IndicesChapitres/chap2.png", chapterLabel2);
        Table tableEx3 = BoutonChapitres.getLigne("Images/Pages Chapitres/chapitre 03.jpg", "Images/IndicesChapitres/chap3.png", chapterLabel3);
//        Table tableEx4 = BoutonChapitres.getLigne("Images/Pages onglets/04.png", "Images/IndicesChapitres/chap4.png", chapterLabel4);
//        Table tableEx5 = BoutonChapitres.getLigne("Images/Pages onglets/05.png", "Images/IndicesChapitres/chap5.png", chapterLabel5);
//        Table tableEx6 = BoutonChapitres.getLigne("Images/Pages onglets/06.png", "Images/IndicesChapitres/chap6.png", chapterLabel6);

        table.add(tableEx1);
        table.add(tableEx2);//.padLeft(MyConstants.SCREENWIDTH / 100);
//        table.add(tableEx3);//.padLeft(MyConstants.SCREENWIDTH / 100);
//        table.row();
//        table.add().height(MyConstants.SCREENWIDTH / 30);
//        table.row();
//        table.add(tableEx4).width(MyConstants.SCREENWIDTH / 4).height(MyConstants.SCREENHEIGHT / 4).align(Align.center);
//        table.add(tableEx5).width(MyConstants.SCREENWIDTH / 4).height(MyConstants.SCREENHEIGHT / 4).align(Align.center).padLeft(MyConstants.SCREENWIDTH / 20);
//        table.add(tableEx6).width(MyConstants.SCREENWIDTH / 4).height(MyConstants.SCREENHEIGHT / 4).align(Align.center).padLeft(MyConstants.SCREENWIDTH / 20);
//        table.row();
//
        table.setWidth(MyConstants.SCREENWIDTH);

        tableEx1.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new Screen_Chapitre1(game));
                System.out.println("I got clicked!1");
            }
        });
        tableEx2.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!2");
                game.setScreen(new Screen_Chapitre2(game));
            }
        });
//        tableEx3.addListener(new ClickListener()
//        {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                System.out.println("I got clicked!3");
//                game.setScreen(new Screen_Chapitre1(game));
//            }
//        });
//        tableEx4.addListener(new ClickListener()
//        {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                System.out.println("I got clicked!4");
//            }
//        });
//        tableEx5.addListener(new ClickListener()
//        {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                System.out.println("I got clicked!5");
//            }
//        });
//        tableEx6.addListener(new ClickListener()
//        {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                System.out.println("I got clicked!6");
//            }
//        });

        return table;
    }


    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }

    @Override
    public void show()
    {
    }

    @Override
    public void render(float delta)
    {
        batch.begin();
        batch.setTransformMatrix(new Matrix4());


        fondSommaire.myDraw2(batch, 0, 0, MyConstants.SCREENWIDTH, MyConstants.SCREENHEIGHT);
        bandeauHaut.myDraw2(batch, 0, bandeauHautY, stage.getWidth(), MyConstants.SCREENHEIGHT / 6f);
        for (int i = 0; i < allDrawables.size(); i++)
        {
            MyDrawInterface newItem = allDrawables.get(i);
            if (newItem.isVisible())
            {
                newItem.myDraw(batch);
            }
        }


//        ninePatch.draw(batch, 10, 100, 50, 200);

        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void create()
    {
        Camera camera = new PerspectiveCamera();
//        Viewport viewport = new FitViewport(800, 480, camera);
    }
//
//    private void setScreen()
//    {
//    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
