package com.evalutel.primval_desktop.onglets.chapitre1;

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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.BoutonChapitres;
import com.evalutel.primval_desktop.General.LigneTableaux;
import com.evalutel.primval_desktop.General.ResourceManager;
import com.evalutel.primval_desktop.General.TableauxTitreChapitre;
import com.evalutel.primval_desktop.General.UIDesign;
import com.evalutel.primval_desktop.ListExercicesActiviteView;
import com.evalutel.primval_desktop.MrNotes2;
import com.evalutel.primval_desktop.MrTemps2;
import com.evalutel.primval_desktop.MyButtonDemos;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

import java.util.ArrayList;


public class Screen_All_ChaptersNew extends Game implements Screen, InputProcessor, ApplicationListener
{
    private DatabaseDesktop dataBase;
    protected Stage stage;
    int screenWidth;
    int screenHeight;
    private SpriteBatch batch;
    private Game game;

    private Camera camera;

    private Viewport viewport;

    ListExercicesActiviteView listExercicesActiviteView;
    ScreeenBackgroundImage fondEspaceParent;
    ScreeenBackgroundImage fondSommaire;
    MrNotes2 mrNotes;
    MrTemps2 mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();
    MyButtonRetour myButtonRetour;
    MyButtonDemos myButtonDemo;

    FreeTypeFontGenerator generatorFRHND;
    FreeTypeFontGenerator generatorZAP;
    TextureRegionDrawable textureRegionDrawableBg;

    BitmapFont bitmapFontZAP;


    public Screen_All_ChaptersNew(Game game, DatabaseDesktop dataBase)
    {
        this.game = game;
        this.dataBase = dataBase;

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        stage = new Stage();
        batch = new SpriteBatch();
        BitmapFont bitmapFontFRHND;

        generatorFRHND = new FreeTypeFontGenerator(Gdx.files.internal("font/FRHND521_0.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterFRHND = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterFRHND.size = 50;
        bitmapFontFRHND = generatorFRHND.generateFont(parameterFRHND);
        generatorFRHND.dispose();

        generatorZAP = new FreeTypeFontGenerator(Gdx.files.internal("font/Zapf Humanist 601 BT.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterZAP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterZAP.size = screenWidth / 70;
        bitmapFontZAP = generatorZAP.generateFont(parameterZAP);
        generatorZAP.dispose();

        Label.LabelStyle labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFontFRHND;
        labelStyleWhite.fontColor = Color.WHITE;

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFontFRHND;
        labelStyleBlue.fontColor = Color.NAVY;

        allDrawables = new ArrayList<>();

        fondEspaceParent = new ScreeenBackgroundImage("Images/fond_espaceparent.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Sommaire/fond_onglets_new.jpg");

        myButtonRetour = new MyButtonRetour(stage, screenWidth / 15, screenWidth / 15, game, dataBase, "sommaire general");
        myButtonRetour.setPosition(screenWidth / 25, 5 * screenHeight / 6 - myButtonRetour.getHeight() / 2);

        myButtonDemo = new MyButtonDemos(stage, screenWidth / 6, screenWidth / 20, game, dataBase);
        myButtonDemo.setPosition(4 * screenWidth / 25, 5 * screenHeight / 6 - myButtonDemo.getHeight() / 2);

        Label labelChap1Titre = new Label("Calcul et géométrie", labelStyleWhite);

        labelChap1Titre.setFontScale(3);
        Table allChaptersTitle = new Table();
        allChaptersTitle.add(labelChap1Titre);
        stage.addActor(allChaptersTitle);

        Table nomChapitre = TableauxTitreChapitre.getLigne(labelChap1Titre, null);
        nomChapitre.setPosition(screenWidth / 2 - nomChapitre.getWidth() / 2, 9 * screenHeight / 10);
        stage.addActor(nomChapitre);

        mrNotes = new MrNotes2(stage, dataBase, 21 * screenWidth / 25, 9 * screenHeight / 11);
        mrTemps = new MrTemps2(stage, dataBase);


        //tableau deroulant pour Evalutel motto et liste de chapitre

        Table container = new Table();
        Table table = new Table();

        Table evalutelMotto = evalutelMotto();

        Label labelChapterTitle = new Label("Chapitres", labelStyleBlue);
        labelChapterTitle.setFontScale(2);
        Table chapterTitle = new Table();
        chapterTitle.add(labelChapterTitle);

        chapterTitle.padTop(screenHeight / 40);

        Table chaptersListView = chaptersListView();

        container.setSize(screenWidth, (screenHeight - (myButtonRetour.getY())-screenHeight/20));
        container.setPosition(0, -screenHeight / 12);
//        table.setPosition(0, 0);

        int widthButton = 1000;
        int heightButton = widthButton / 4;
        int cornerRadius = heightButton / 4;

//
        container.debug();
        table.add(evalutelMotto).width(screenWidth - (screenWidth / 19)).align(Align.center).padTop(screenWidth / 30);
        table.row();
        table.add(chapterTitle).width(screenWidth).align(Align.center).padBottom(screenHeight / 20);
        table.row();
        table.add(chaptersListView).width(screenWidth).align(Align.center);
        table.row();

        table.setWidth(screenWidth);

        ScrollPane scroll = new ScrollPane(table);
        scroll.layout();

        container.add(scroll).height(screenHeight - (screenHeight - (myButtonRetour.getY() + myButtonRetour.getHeight() / 2)));
//        container.row();

        stage.addActor(container);
        container.setFillParent(true);

        Gdx.input.setInputProcessor(stage);
    }

    public Table evalutelMotto()
    {
        Label.LabelStyle labelStyleBlue2 = new Label.LabelStyle();
        labelStyleBlue2.font = bitmapFontZAP;
        labelStyleBlue2.fontColor = Color.ROYAL;
        Label labelMottoTitle = new Label("Primval développé par Evalutel, propose un programe complet de Math et de géométrie pour le Primaire basé sur notre devise:", labelStyleBlue2);

        int widthButton = 1000;
        int heightButton = widthButton / 4;
        int cornerRadius = heightButton / 8;

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.WHITE);

        Pixmap blueRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.BLUE);
        Pixmap blueRoundedBackground2 = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, new Color(234.0f / 255.0f, 241.0f / 255.0f, 250.0f / 255.0f, 1));

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.rgb888(234, 241, 250));

        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmBlue.setColor(Color.NAVY);
        pmBlue.fill();

        bgPixmap.fill();
        textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(blueRoundedBackground2)));

        Table evalutelMotto = new Table();
        evalutelMotto.setBackground((textureRegionDrawableBg));

        evalutelMotto.add(labelMottoTitle).padBottom(screenHeight / 80);
        evalutelMotto.padTop(screenHeight / 40).padRight(screenHeight / 40);
        evalutelMotto.row();

        Table evalutelMottoDetails = new Table();

        Table manipulerTable = new Table();
        manipulerTable.setSize(screenWidth / 4, screenHeight / 5);
        Label labelManipulerTitle = new Label("MANIPULER", labelStyleBlue2);
        Label labelManipulerText = new Label("Des objets interactifs et ludiques sont conçus pour permettre à l'enfant de comprendre la numération , les opérations arithmétiques et les notions de base de géométrie.", labelStyleBlue2);
        labelManipulerText.setWrap(true);
        labelManipulerText.setWidth(screenWidth / 5);

        manipulerTable.add(labelManipulerTitle);
        manipulerTable.row();
        manipulerTable.add(labelManipulerText).width(screenWidth / 4).padLeft(screenWidth / 50).height(screenHeight / 5).padRight(screenWidth / 50);
        manipulerTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table borderManipuler = new Table();
        borderManipuler.pad(screenWidth / 1000);
        borderManipuler.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        borderManipuler.add(manipulerTable);

        Table apprendreTable = new Table();
        Label labelApprendreTitle = new Label("APPRENDRE", labelStyleBlue2);
        Label labelApprendreText = new Label("Les notions nouvelles sont introduites par des cours et des exemples. Chaque manipulation est mise en correspondance avec l'opération que l'enfant est invité à faire sur l'ardoise et sur la solution, tout en étant corrigé pas à pas.", labelStyleBlue2);
        labelApprendreText.setWrap(true);
        labelApprendreText.setWidth(screenWidth / 5);

        apprendreTable.add(labelApprendreTitle);
        apprendreTable.row();
        apprendreTable.add(labelApprendreText).width(screenWidth / 4).padLeft(screenWidth / 50).height(screenHeight / 5).padRight(screenWidth / 50);
        apprendreTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table borderApprendre = new Table();
        borderApprendre.pad(screenWidth / 1000);
        borderApprendre.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        borderApprendre.add(apprendreTable);

        Table evaluerTable = new Table();
        Label labelEvaluerTitle = new Label("EVALUER", labelStyleBlue2);
        Label labelEvaluerText = new Label("Tous les exercices/problèmes sont notés. L'enfant peut toujours améliorer sa note en refaisant l'exercice. Un tableau récapitule les sujets abordés complètement ou partiellement, les temps passés et les résultats obtenus.", labelStyleBlue2);
        labelEvaluerText.setWrap(true);
        labelEvaluerText.setWidth(screenWidth / 4);

        evaluerTable.add(labelEvaluerTitle);
        evaluerTable.row();
        evaluerTable.add(labelEvaluerText).width(screenWidth / 4).padLeft(screenWidth / 50).height(screenHeight / 5).padRight(screenWidth / 50);
        evaluerTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table borderEvaluer = new Table();
        borderEvaluer.pad(screenWidth / 1000);
        borderEvaluer.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        borderEvaluer.add(evaluerTable);

        evalutelMottoDetails.add(borderManipuler).padLeft(screenWidth / 80);
        evalutelMottoDetails.add(borderApprendre).padLeft(screenWidth / 80);
        evalutelMottoDetails.add(borderEvaluer).padLeft(screenWidth / 80);
        evalutelMotto.add(evalutelMottoDetails).padBottom(screenWidth / 80);
//
//        Pixmap pmBlue = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
//        pmBlue.setColor(Color.NAVY);
//        pmBlue.fill();

        Table border = new Table();
        border.pad(5);
        border.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        border.add(evalutelMotto);

        return border;
    }

    public Table chaptersListView()
    {
        String chapterLabel1 = "Pratique des nombres de 1 à 9";
        String chapterLabel2 = "Introduction de l\'addition ";
        String chapterLabel3 = "Les nombres de 1 à 69 . \n Introduction du zéro et de... ";
        String chapterLabel4 = "Les nombres de 1 à 99 . \n Additions sans retenue ";
        String chapterLabel5 = "Additions avec retenue \n Calcul mental";
        String chapterLabel6 = "Outils de la géométrie. \n Triangle. Points alignés ";

        Table table = new Table();
        Table tableEx1 = BoutonChapitres.getLigne("Sommaire chaps ongs/chapitre_circle_1.png", "Images/IndicesChapitres/chap1.png", chapterLabel1, "yes", null, 1, dataBase);
        Table tableEx2 = BoutonChapitres.getLigne("Sommaire chaps ongs/chapitre_circle_2.png", "Images/IndicesChapitres/chap2.png", chapterLabel2, "no", null, 1, dataBase);
        Table tableEx3 = BoutonChapitres.getLigne("Sommaire chaps ongs/chapitre_circle_3.png", "Images/IndicesChapitres/chap3.png", chapterLabel3, "no", null, 1, dataBase);
        Table tableEx4 = BoutonChapitres.getLigne("Sommaire chaps ongs/chapitre_circle_4.png", "Images/IndicesChapitres/chap4.png", chapterLabel4, "no", null, 1, dataBase);
        Table tableEx5 = BoutonChapitres.getLigne("Sommaire chaps ongs/chapitre_circle_5.png", "Images/IndicesChapitres/chap5.png", chapterLabel5, "no", null, 1, dataBase);
        Table tableEx6 = BoutonChapitres.getLigne("Sommaire chaps ongs/chapitre_circle_6.png", "Images/IndicesChapitres/chap6.png", chapterLabel6, "no", null, 1, dataBase);

        table.add(tableEx1).width(screenWidth / 4).height(screenHeight / 4).align(Align.center);
        table.add(tableEx2).width(screenWidth / 4).height(screenHeight / 4).align(Align.center).padLeft(screenWidth / 20);
        table.add(tableEx3).width(screenWidth / 4).height(screenHeight / 4).align(Align.center).padLeft(screenWidth / 20);
        table.row();
        table.add().height(screenWidth /30);
        table.row();
        table.add(tableEx4).width(screenWidth / 4).height(screenHeight / 4).align(Align.center);
        table.add(tableEx5).width(screenWidth / 4).height(screenHeight / 4).align(Align.center).padLeft(screenWidth / 20);
        table.add(tableEx6).width(screenWidth / 4).height(screenHeight / 4).align(Align.center).padLeft(screenWidth / 20);
        table.row();
//
        table.setWidth(screenWidth);

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
                game.setScreen(new Screen_Chapitre1(game, dataBase));
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

        fondEspaceParent.myDraw(batch);
        fondSommaire.myDraw2(batch, screenWidth, 5 * screenHeight / 6, 0, 0);

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
        viewport = new FitViewport(800, 480, camera);
    }

    private void setScreen()
    {
    }

    @Override
    public void resize(int width, int height)
    {
//        stage.getViewport().update(width, height, true);
//        width = 2400;
//        height = 1350;


//        viewport.update(width, height);
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
