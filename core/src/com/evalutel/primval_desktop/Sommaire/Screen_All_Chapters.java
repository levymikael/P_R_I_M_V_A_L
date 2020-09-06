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
import com.badlogic.gdx.graphics.g2d.NinePatch;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.BoutonChapitres;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.UIDesign;
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
    private DatabaseDesktop dataBase;
    protected Stage stage;

    private SpriteBatch batch;
    private Game game;

    private ScreeenBackgroundImage bandeauHaut, fondSommaire;

    float bandeauHautY;

    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();
    private MyButtonRetour myButtonRetour;
//    MyButtonDemos myButtonDemo;

    private TextureRegionDrawable textureRegionDrawableBg;
//    NinePatch ninePatch;
//    NinePatchDrawable ninePatchDrawable;


    private BitmapFont bitmapFontZAP, bitmapFontArialBold, bitmapFontArial, bitmapFontFRHND;


    public Screen_All_Chapters(Game game)
    {
        this.game = game;

        stage = new Stage();
        batch = new SpriteBatch();


        int fontSize = MyConstants.SCREENWIDTH / 60;

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

        Label.LabelStyle labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFontFRHND;
        labelStyleWhite.fontColor = Color.WHITE;

        Label.LabelStyle labelStyleBlueArialBold = new Label.LabelStyle();
        labelStyleBlueArialBold.font = bitmapFontArialBold;
        labelStyleBlueArialBold.fontColor = new Color((Color.valueOf("004ec0")));

        Label.LabelStyle labelStyleBlueFRHND = new Label.LabelStyle();
        labelStyleBlueFRHND.font = bitmapFontFRHND;
        labelStyleBlueFRHND.fontColor = new Color((Color.valueOf("004ec0")));

        allDrawables = new ArrayList<>();

        bandeauHaut = new ScreeenBackgroundImage("Images/Pages Chapitres/Bandeau haut.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Backgrounds/web_hi_res_512.png");

        myButtonRetour = new MyButtonRetour(stage, MyConstants.SCREENWIDTH / 15f, MyConstants.SCREENWIDTH / 15f, game, "sommaire general");
        myButtonRetour.setPosition(MyConstants.SCREENWIDTH / 25f, 5 * MyConstants.SCREENHEIGHT / 6f - myButtonRetour.getHeight() / 2f);

//        myButtonDemo = new MyButtonDemos(stage, (float) MyConstants.SCREENWIDTH / 22.0f * (447.0f / 93.0f), (float) MyConstants.SCREENWIDTH / 22.0f, game);

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


        Label labelChapterTitle = new Label("Chapitres", labelStyleBlueFRHND);
        labelChapterTitle.setFontScale(2.5f);
        Table chapterTitle = new Table();
        chapterTitle.add(labelChapterTitle);

        chapterTitle.padTop(MyConstants.SCREENHEIGHT / 50f);

        Table chaptersListView = chaptersListView();

        table.add(evalutelMotto).width(MyConstants.SCREENWIDTH - (MyConstants.SCREENWIDTH / 19f)).align(Align.center).padTop(MyConstants.SCREENWIDTH / 80f);
        table.row();
        table.add(chapterTitle).width(MyConstants.SCREENWIDTH).align(Align.center).padBottom(MyConstants.SCREENHEIGHT / 60f);
        table.row();
        table.add(chaptersListView).width(MyConstants.SCREENWIDTH).align(Align.center).padBottom(MyConstants.SCREENHEIGHT / 20f);

        table.setWidth(MyConstants.SCREENWIDTH);

        ScrollPane scroll = new ScrollPane(table);
        scroll.layout();

        container.add(scroll).height(heightContainer);

        stage.addActor(container);

        Gdx.input.setInputProcessor(stage);
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

        int widthButton = 1000;
        int heightButton = widthButton / 5;
        int cornerRadius = heightButton / 20;

//        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, Color.WHITE);
//        Pixmap lightBlueRoundedBackground2 = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, new Color(234f / 255f, 241f / 255f, 250f / 255f, 1));
//        Pixmap blueRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, MyConstants.bluePrimval);
//

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.rgb888(234, 241, 250));
        bgPixmap.fill();

        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlue.setColor(Color.NAVY);
        pmBlue.fill();

        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

//        textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(lightBlueRoundedBackground2)));

        float paddingMottoDetailsBorder = /*MyConstants.SCREENWIDTH / 1000f*/2;
        float heightlabelTitle = 100;
        float tableWidth = MyConstants.SCREENWIDTH / 4f;
        float heightTable = MyConstants.SCREENHEIGHT / 5.5f;
        float paddingManipulerApprendreEvaluer = MyConstants.SCREENWIDTH / 70f;
        float paddingCoteEvalutelMotto = MyConstants.SCREENHEIGHT / 50f;

        float leftPaddingBorderEvalutelDetails = MyConstants.SCREENWIDTH / 80f;


        // TODO : check 9-patch to avoid anti-aliasing
//        ninePatch = new NinePatch(new Texture(Gdx.files.internal("Images/cellule Primaire.9.png")));
//
//        ninePatchDrawable = new NinePatchDrawable(ninePatch);


        Table evalutelMotto = new Table();
        evalutelMotto.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap))));

        evalutelMotto.add(labelMottoTitle).padBottom(leftPaddingBorderEvalutelDetails).align(Align.center).height(heightlabelTitle);
        evalutelMotto.padTop(paddingCoteEvalutelMotto).padRight(paddingCoteEvalutelMotto);
        evalutelMotto.row();

        Table evalutelMottoDetails = new Table();
        float MottoDetailsTableHeight = MyConstants.SCREENHEIGHT / 15f;

        Table manipulerTable = new Table();
        Label labelManipulerTitle = new Label("MANIPULER", labelStyleBlueArialBold);
        Label labelManipulerText = new Label("Des objets interactifs et ludiques sont conçus pour permettre à l'enfant de comprendre la numération , les opérations arithmétiques et les notions de base de géométrie.", labelStyleBlueArial);
        labelManipulerText.setWrap(true);

        manipulerTable.add(labelManipulerTitle).height(heightlabelTitle).padBottom(-MyConstants.SCREENHEIGHT / 70f);
        manipulerTable.row();
        //manipulerTable.add(labelManipulerText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).height(heightTable).padRight(paddingManipulerApprendreEvaluer).padBottom(MyConstants.SCREENHEIGHT / 40f).align(Align.top);
        manipulerTable.add(labelManipulerText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).padRight(paddingManipulerApprendreEvaluer).padBottom(MyConstants.SCREENHEIGHT / 40f).align(Align.top).fillY();
//        manipulerTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));
        manipulerTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));
//
//        Table borderManipuler = new Table();
//        borderManipuler.pad(paddingMottoDetailsBorder);
//        borderManipuler.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
//        borderManipuler.add(manipulerTable).fillY();
//        borderManipuler.pad(2);


        Table apprendreTable = new Table();
        Label labelApprendreTitle = new Label("APPRENDRE", labelStyleBlueArialBold);
        Label labelApprendreText = new Label("Les notions nouvelles sont introduites par des cours et des exemples. Chaque manipulation est mise en correspondance avec l'opération que l'enfant est invité à faire sur l'ardoise et sur la solution, tout en étant corrigé pas à pas.", labelStyleBlueArial);
        labelApprendreText.setWrap(true);

        apprendreTable.add(labelApprendreTitle).prefHeight(heightlabelTitle);
        apprendreTable.row();
        //apprendreTable.add(labelApprendreText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).height(heightTable).padRight(paddingManipulerApprendreEvaluer).padBottom(MyConstants.SCREENHEIGHT / 100f);
        apprendreTable.add(labelApprendreText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).padRight(paddingManipulerApprendreEvaluer).padBottom(MyConstants.SCREENHEIGHT / 100f);
//        apprendreTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));
        apprendreTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));


//        Table borderApprendre = new Table();
//        borderApprendre.pad(paddingMottoDetailsBorder);
//        borderApprendre.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
//        borderApprendre.add(apprendreTable);

        Table evaluerTable = new Table();
        Label labelEvaluerTitle = new Label("EVALUER", labelStyleBlueArialBold);
        Label labelEvaluerText = new Label("Tous les exercices/problèmes sont notés. L'enfant peut toujours améliorer sa note en refaisant l'exercice. Un tableau récapitule les sujets abordés complètement ou partiellement, les temps passés et les résultats obtenus.       ", labelStyleBlueArial);
        labelEvaluerText.setWrap(true);

        evaluerTable.add(labelEvaluerTitle).height(heightlabelTitle)/*.padBottom(-MyConstants.SCREENHEIGHT / 70f)*/;
        evaluerTable.row();
        //evaluerTable.add(labelEvaluerText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).height(heightTable).padRight(paddingManipulerApprendreEvaluer).align(Align.top)/*.padBottom(MyConstants.SCREENHEIGHT / 100f)*/;
        evaluerTable.add(labelEvaluerText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).padRight(paddingManipulerApprendreEvaluer).align(Align.top)/*.padBottom(MyConstants.SCREENHEIGHT / 100f)*/;
//        evaluerTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));
        evaluerTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));

        evaluerTable.row();
        //evaluerTable.add().height(MyConstants.SCREENHEIGHT / 100f);

//        Table borderEvaluer = new Table();
//        borderEvaluer.pad(paddingMottoDetailsBorder);
//        borderEvaluer.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
//        borderEvaluer.add(evaluerTable);

//        evalutelMottoDetails.add(borderManipuler).padLeft(leftPaddingBorderEvalutelDetails).padBottom(MyConstants.SCREENHEIGHT / 100f).fillY();
//        evalutelMottoDetails.add(borderApprendre).padLeft(leftPaddingBorderEvalutelDetails).padBottom(MyConstants.SCREENHEIGHT / 100f).fillY();
//        evalutelMottoDetails.add(borderEvaluer).padLeft(leftPaddingBorderEvalutelDetails).padBottom(MyConstants.SCREENHEIGHT / 100f).fillY();  evalutelMottoDetails.add(borderManipuler).padLeft(leftPaddingBorderEvalutelDetails).padBottom(MyConstants.SCREENHEIGHT / 100f).fillY();
        evalutelMottoDetails.add(manipulerTable).padLeft(leftPaddingBorderEvalutelDetails).padBottom(MyConstants.SCREENHEIGHT / 100f).fillY();
        evalutelMottoDetails.add(apprendreTable).padLeft(leftPaddingBorderEvalutelDetails).padBottom(MyConstants.SCREENHEIGHT / 100f).fillY();
        evalutelMottoDetails.add(evaluerTable).padLeft(leftPaddingBorderEvalutelDetails).padBottom(MyConstants.SCREENHEIGHT / 100f).fillY();
        evalutelMotto.add(evalutelMottoDetails).center();

        Table border = new Table();
        border.pad(2);
//        border.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
//        border.setBackground( new TextureRegionDrawable(new TextureRegion(new Texture(pmBlue))));

        border.add(evalutelMotto);//.height(MyConstants.SCREENHEIGHT / 3.2f);

        return border;
    }

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
        Table tableEx4 = BoutonChapitres.getLigne("Images/Pages onglets/04.png", "Images/IndicesChapitres/chap4.png", chapterLabel4);
        Table tableEx5 = BoutonChapitres.getLigne("Images/Pages onglets/05.png", "Images/IndicesChapitres/chap5.png", chapterLabel5);
        Table tableEx6 = BoutonChapitres.getLigne("Images/Pages onglets/06.png", "Images/IndicesChapitres/chap6.png", chapterLabel6);

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
