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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.BoutonChapitres;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.TableauxTitreChapitre;
import com.evalutel.primval_desktop.General.UIDesign;
import com.evalutel.primval_desktop.MrNotes2;
import com.evalutel.primval_desktop.MrTemps2;
import com.evalutel.primval_desktop.MyButtonDemos;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.MyDrawInterface;
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

    private Camera camera;

    ScreeenBackgroundImage bandeauHaut;
    ScreeenBackgroundImage fondSommaire;
    MrNotes2 mrNotes;
    MrTemps2 mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();
    MyButtonRetour myButtonRetour;
    MyButtonDemos myButtonDemo;

    TextureRegionDrawable textureRegionDrawableBg;

    BitmapFont bitmapFontZAP, bitmapFontArialBold, bitmapFontArial, bitmapFontFRHND;


    public Screen_All_Chapters(Game game, DatabaseDesktop dataBase)
    {
        this.game = game;
        this.dataBase = dataBase;

        stage = new Stage();
        batch = new SpriteBatch();

        FreeTypeFontGenerator FONT_FRHND = new FreeTypeFontGenerator(Gdx.files.internal("font/FRHND521_0.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterFRHND = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterFRHND.size = MyConstants.SCREENHEIGHT / 40;
        parameterFRHND.minFilter = Texture.TextureFilter.Linear;
        parameterFRHND.magFilter = Texture.TextureFilter.Linear;
        bitmapFontFRHND = FONT_FRHND.generateFont(parameterFRHND);
        FONT_FRHND.dispose();

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

        myButtonRetour = new MyButtonRetour(stage, MyConstants.SCREENWIDTH / 15, MyConstants.SCREENWIDTH / 15, game, dataBase, "sommaire general");
        myButtonRetour.setPosition(MyConstants.SCREENWIDTH / 25, 5 * MyConstants.SCREENHEIGHT / 6 - myButtonRetour.getHeight() / 2);

//        myButtonDemo = new MyButtonDemos(stage, (float) MyConstants.SCREENWIDTH / 22.0f * (447.0f / 93.0f), (float) MyConstants.SCREENWIDTH / 22.0f, game, dataBase);

        Texture chapter1Title = new Texture(Gdx.files.internal("Images/Pages Chapitres/titre Calcul et géométrie.png"));
        chapter1Title.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        Table allChaptersTitle = new Table();

        float chapterTitleWidth = MyConstants.SCREENWIDTH / 2.5f;
        float chapterTitleHeight = chapterTitleWidth * (55f / 387f);

        allChaptersTitle.setBackground(new SpriteDrawable(new Sprite(chapter1Title)));
        allChaptersTitle.setSize(chapterTitleWidth, chapterTitleHeight);
        allChaptersTitle.setPosition(MyConstants.SCREENWIDTH / 2 - allChaptersTitle.getWidth() / 2, MyConstants.SCREENHEIGHT - ((MyConstants.SCREENWIDTH / 11)));

        stage.addActor(allChaptersTitle);

        mrNotes = new MrNotes2(stage, dataBase, 20.5f * MyConstants.SCREENWIDTH / 25, 4 * MyConstants.SCREENHEIGHT / 5, "all chapters");
        mrTemps = new MrTemps2(stage, dataBase);


        //tableau deroulant pour Evalutel motto et liste de chapitre
        Table container = new Table();
        Table table = new Table();

        float positionButton = myButtonRetour.getY();
        float heightContainer = (positionButton);
        container.setSize(MyConstants.SCREENWIDTH, heightContainer);
        container.setPosition(0, 0);

        Table evalutelMotto = evalutelMotto();

        Label labelChapterTitle = new Label("Chapitres", labelStyleBlueFRHND);
        labelChapterTitle.setFontScale(1.7f);
        Table chapterTitle = new Table();
        chapterTitle.add(labelChapterTitle);

        chapterTitle.padTop(MyConstants.SCREENHEIGHT / 40);

        Table chaptersListView = chaptersListView();

        table.add(evalutelMotto).width(MyConstants.SCREENWIDTH - (MyConstants.SCREENWIDTH / 19)).align(Align.center).padTop(MyConstants.SCREENWIDTH / 90);
        table.row();
        table.add(chapterTitle).width(MyConstants.SCREENWIDTH).align(Align.center).padBottom(MyConstants.SCREENHEIGHT / 60);
        table.row();
        table.add(chaptersListView).width(MyConstants.SCREENWIDTH).align(Align.center).padBottom(MyConstants.SCREENHEIGHT / 20);

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
        labelMottoTitle.setFontScale(1f);

        int widthButton = 1000;
        int heightButton = widthButton / 5;
        int cornerRadius = heightButton / 8;

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, Color.WHITE);
        Pixmap blueRoundedBackground2 = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, new Color(234.0f / 255.0f, 241.0f / 255.0f, 250.0f / 255.0f, 1));
        Pixmap blueRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, 20, Color.ROYAL);


        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.rgb888(234, 241, 250));
        bgPixmap.fill();

        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlue.setColor(Color.NAVY);
        pmBlue.fill();

        textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(blueRoundedBackground2)));

        int paddingMottoDetailsBorder = MyConstants.SCREENWIDTH / 1000;
        int heightlabelTitle = MyConstants.SCREENHEIGHT / 25;
        int tableWidth = MyConstants.SCREENWIDTH / 4;
        float heightTable = MyConstants.SCREENHEIGHT / 6f;
        int paddingManipulerApprendreEvaluer = MyConstants.SCREENWIDTH / 70;
        int paddingCoteEvalutelMotto = MyConstants.SCREENHEIGHT / 50;

        int leftPaddingBorderEvalutelDetails = MyConstants.SCREENWIDTH / 80;

        Table evalutelMotto = new Table();
        evalutelMotto.setBackground((textureRegionDrawableBg));

        evalutelMotto.add(labelMottoTitle).padBottom(leftPaddingBorderEvalutelDetails).align(Align.center);
        evalutelMotto.padTop(paddingCoteEvalutelMotto).padRight(paddingCoteEvalutelMotto);
        evalutelMotto.row();

        Table evalutelMottoDetails = new Table();

        Table manipulerTable = new Table();
        Label labelManipulerTitle = new Label("MANIPULER", labelStyleBlueArialBold);
        Label labelManipulerText = new Label("Des objets interactifs et ludiques sont conçus pour permettre à l'enfant de comprendre la numération , les opérations arithmétiques et les notions de base de géométrie.", labelStyleBlueArial);
        labelManipulerText.setWrap(true);

        manipulerTable.add(labelManipulerTitle).height(heightlabelTitle);
        manipulerTable.row();
        manipulerTable.add(labelManipulerText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).height(heightTable).padRight(paddingManipulerApprendreEvaluer);
        manipulerTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table borderManipuler = new Table();
        borderManipuler.pad(paddingMottoDetailsBorder);
        borderManipuler.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        borderManipuler.add(manipulerTable);

        Table apprendreTable = new Table();
        Label labelApprendreTitle = new Label("APPRENDRE", labelStyleBlueArialBold);
        Label labelApprendreText = new Label("Les notions nouvelles sont introduites par des cours et des exemples. Chaque manipulation est mise en correspondance avec l'opération que l'enfant est invité à faire sur l'ardoise et sur la solution, tout en étant corrigé pas à pas.", labelStyleBlueArial);
        labelApprendreText.setWrap(true);

        apprendreTable.add(labelApprendreTitle).height(heightlabelTitle);
        apprendreTable.row();
        apprendreTable.add(labelApprendreText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).height(heightTable).padRight(paddingManipulerApprendreEvaluer);
        apprendreTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table borderApprendre = new Table();
        borderApprendre.pad(paddingMottoDetailsBorder);
        borderApprendre.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        borderApprendre.add(apprendreTable);

        Table evaluerTable = new Table();
        Label labelEvaluerTitle = new Label("EVALUER", labelStyleBlueArialBold);
        Label labelEvaluerText = new Label("Tous les exercices/problèmes sont notés. L'enfant peut toujours améliorer sa note en refaisant l'exercice. Un tableau récapitule les sujets abordés complètement ou partiellement, les temps passés et les résultats obtenus.", labelStyleBlueArial);
        labelEvaluerText.setWrap(true);

        evaluerTable.add(labelEvaluerTitle).height(heightlabelTitle).padBottom(MyConstants.SCREENHEIGHT / 400);
        evaluerTable.row();
        evaluerTable.add(labelEvaluerText).width(tableWidth).padLeft(paddingManipulerApprendreEvaluer).height(heightTable).padRight(paddingManipulerApprendreEvaluer);
        evaluerTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table borderEvaluer = new Table();
        borderEvaluer.pad(paddingMottoDetailsBorder);
        borderEvaluer.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        borderEvaluer.add(evaluerTable);

        evalutelMottoDetails.add(borderManipuler).padLeft(leftPaddingBorderEvalutelDetails);
        evalutelMottoDetails.add(borderApprendre).padLeft(leftPaddingBorderEvalutelDetails);
        evalutelMottoDetails.add(borderEvaluer).padLeft(leftPaddingBorderEvalutelDetails);
        evalutelMotto.add(evalutelMottoDetails).padBottom(leftPaddingBorderEvalutelDetails);

        Table border = new Table();
        border.pad(2);
        border.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        border.add(evalutelMotto).height(MyConstants.SCREENHEIGHT / 3.5f);

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
        Table tableEx1 = BoutonChapitres.getLigne("Images/Pages Chapitres/chapitre 01.jpg", "Images/IndicesChapitres/chap1.png", chapterLabel1, dataBase);
        Table tableEx2 = BoutonChapitres.getLigne("Images/Pages Chapitres/chapitre 02.jpg", "Images/IndicesChapitres/chap2.png", chapterLabel2, dataBase);
        Table tableEx3 = BoutonChapitres.getLigne("Images/Pages Chapitres/chapitre 03.jpg", "Images/IndicesChapitres/chap3.png", chapterLabel3, dataBase);
        Table tableEx4 = BoutonChapitres.getLigne("Images/Pages onglets/04.png", "Images/IndicesChapitres/chap4.png", chapterLabel4, dataBase);
        Table tableEx5 = BoutonChapitres.getLigne("Images/Pages onglets/05.png", "Images/IndicesChapitres/chap5.png", chapterLabel5, dataBase);
        Table tableEx6 = BoutonChapitres.getLigne("Images/Pages onglets/06.png", "Images/IndicesChapitres/chap6.png", chapterLabel6, dataBase);

        table.add(tableEx1);
//        table.add(tableEx2);//.padLeft(MyConstants.SCREENWIDTH / 100);
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
                game.setScreen(new Screen_Chapitre1(game, dataBase));
                System.out.println("I got clicked!1");
            }
        });
        tableEx2.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!2");
                game.setScreen(new Screen_Chapitre2(game, dataBase));
            }
        });
        tableEx3.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!3");
                game.setScreen(new Screen_Chapitre1(game, dataBase));
            }
        });
        tableEx4.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!4");
            }
        });
        tableEx5.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!5");
            }
        });
        tableEx6.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!6");
            }
        });

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

        fondSommaire.myDraw2(batch, MyConstants.SCREENWIDTH, MyConstants.SCREENHEIGHT, 0, 0);
        bandeauHaut.myDraw2(batch, MyConstants.SCREENWIDTH, MyConstants.SCREENHEIGHT / 6, 0, (MyConstants.SCREENHEIGHT - MyConstants.SCREENHEIGHT / 6));
        for (int i = 0; i < allDrawables.size(); i++)
        {
            MyDrawInterface newItem = allDrawables.get(i);
            if (newItem.isVisible())
            {
                newItem.myDraw(batch);
            }
        }

        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void create()
    {
        camera = new PerspectiveCamera();
        Viewport viewport = new FitViewport(800, 480, camera);
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

    }
}
