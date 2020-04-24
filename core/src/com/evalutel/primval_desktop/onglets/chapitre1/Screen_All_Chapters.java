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
import com.evalutel.primval_desktop.General.LigneTableaux;
import com.evalutel.primval_desktop.General.TableauxTitreChapitre;
import com.evalutel.primval_desktop.General.UIDesign;
import com.evalutel.primval_desktop.ListExercicesActiviteView;
import com.evalutel.primval_desktop.MrNotes2;
import com.evalutel.primval_desktop.MrTemps2;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

import java.util.ArrayList;


public class Screen_All_Chapters extends Game implements Screen, InputProcessor, ApplicationListener
{
    private DatabaseDesktop dataBase;
    protected Stage stage;
    int screenWidth;
    int screenHeight;
    private SpriteBatch batch;
    private Game game;

    private Camera camera;

    private Viewport viewport;

    Texture sacDebilles;

    ListExercicesActiviteView listExercicesActiviteView;
    ScreeenBackgroundImage fondEspaceParent;
    ScreeenBackgroundImage fondSommaire;
    MrNotes2 mrNotes;
    MrTemps2 mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();
    //
    MyButtonRetour myButtonRetour;

    FreeTypeFontGenerator generatorFRHND;
    FreeTypeFontGenerator generatorZAP;
    TextureRegionDrawable textureRegionDrawableBg;

    BitmapFont bitmapFontZAP;


    public Screen_All_Chapters(Game game, DatabaseDesktop dataBase)
    {
        this.game = game;
        this.dataBase = dataBase;

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
        parameterZAP.size = 40;
        bitmapFontZAP = generatorZAP.generateFont(parameterZAP);
        generatorZAP.dispose();

        Label.LabelStyle labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFontFRHND;
        labelStyleWhite.fontColor = Color.WHITE;

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFontFRHND;
        labelStyleBlue.fontColor = Color.NAVY;


        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        allDrawables = new ArrayList<>();

        fondEspaceParent = new ScreeenBackgroundImage("Images/fond_espaceparent.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Sommaire/fond_onglets_new.jpg");

        myButtonRetour = new MyButtonRetour(stage, screenWidth / 15, screenWidth / 15, game, dataBase, "sommaire general");
        myButtonRetour.setPosition(screenWidth / 25, 5 * screenHeight / 6 - myButtonRetour.getHeight() / 2);


        Label labelChap1Titre = new Label("Calcul et géométrie", labelStyleWhite);

        Table nomChapitre = TableauxTitreChapitre.getLigne(labelChap1Titre, null);
        nomChapitre.setPosition(screenWidth / 2 - nomChapitre.getWidth() / 2, 9 * screenHeight / 10);
        stage.addActor(nomChapitre);

        mrNotes = new MrNotes2(stage, dataBase, 21 * screenWidth / 25, 4 * screenHeight / 5);
        mrTemps = new MrTemps2(stage, dataBase);

        //tableau deroulant pour Evalutel motto et liste de chapitre


        Table container = new Table();
        Table table = new Table();

        Table evalutelMotto = evalutelMotto();

        Label labelChapterTitle = new Label("Chapitres", labelStyleBlue);

        Table chapterTitle = TableauxTitreChapitre.getLigne(labelChapterTitle, null);
//        chapterTitle.setPosition(screenWidth / 2 - chapterTitle.getWidth() / 2,  screenHeight / 40);
//        chapterTitle.padTop(screenHeight / 40);
//        stage.addActor(chapterTitle);

        Table chaptersListView = chaptersListView();

        ScrollPane scroll = new ScrollPane(table);
        scroll.layout();

        container.setSize(screenWidth, 2 * screenHeight / 3);


        container.add(scroll).height(2 * screenHeight / 5);

//        container.setWidth(screenWidth);
        container.debug();
        container.add(evalutelMotto);
        container.row();
        container.add(chapterTitle);
        container.row();
        container.add(chaptersListView);

        stage.addActor(container);

        Gdx.input.setInputProcessor(stage);
    }


    public Table chaptersListView()
    {
        screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();

//        this.game = game;
//        this.dataBase = dataBase;

        String label1 = "Les nombres de 1 à 9. Badix, Métrologue et Validus";
        String label2 = "Faire correspondre des billes à des oiseaux";
        String label3 = "Écriture des chiffres 1 à 9";
        String label4 = "Prononciation des chiffres 1 à 9";
        String label5 = "Compter des oiseaux et taper leur nombre";
        String label6 = "Un gâteau pour plusieurs anniversaires";

        Texture textureCours = new Texture(Gdx.files.internal("Images/icon_cours.png"));
        Texture textureExercices = new Texture(Gdx.files.internal("Images/icon_exercice.png"));

        Table container = new Table();
        container.setSize(screenWidth, 2 * screenHeight / 5);
//        container.setPosition(screenWidth / 40, screenHeight / 3 - container.getHeight());

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", 70, 70, "font/FRHND521_0.TTF");
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", 50, 50, "font/FRHND521_0.TTF");
        MyTextButton trois_bouton = new MyTextButton("3", "Images/red_circle.png", "Images/red_circle.png", 50, 50, "font/FRHND521_0.TTF");
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", 50, 50, "font/FRHND521_0.TTF");
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", 50, 50, "font/FRHND521_0.TTF");
        MyTextButton six_bouton = new MyTextButton("6", "Images/blue_circle.png", "Images/blue_circle.png", 50, 50, "font/FRHND521_0.TTF");

        Table table = new Table();
        Table tableEx1 = LigneTableaux.getLigne(un_bouton, label1, textureCours, "red", 1, 1, dataBase);
        Table tableEx2 = LigneTableaux.getLigne(deux_bouton, label2, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx3 = LigneTableaux.getLigne(trois_bouton, label3, textureCours, "red", 1, 2, dataBase);
        Table tableEx4 = LigneTableaux.getLigne(quatre_bouton, label4, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx5 = LigneTableaux.getLigne(cinq_bouton, label5, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx6 = LigneTableaux.getLigne(six_bouton, label6, textureExercices, "blue", 1, 2, dataBase);

        table.add(tableEx1).width(screenWidth).height(screenHeight / 11).align(Align.center);
        table.row();
        table.add(tableEx2).width(screenWidth).height(screenHeight / 11).align(Align.center);
        table.row();
        table.add(tableEx3).width(screenWidth).height(screenHeight / 11).align(Align.center);
        table.row();
        table.add(tableEx4).width(screenWidth).height(screenHeight / 11).align(Align.center);
        table.row();
        table.add(tableEx5).width(screenWidth).height(screenHeight / 11).align(Align.center);
        table.row();
        table.add(tableEx6).width(screenWidth).height(screenHeight / 11).align(Align.center);
        table.row();

        table.setWidth(screenWidth);
//
//        ScrollPane scroll = new ScrollPane(table);
//        scroll.layout();

//        container.add(scroll).height(2 * screenHeight / 5);
        container.row();

        tableEx1.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new ScreenEx1_1(game, dataBase));
                System.out.println("I got clicked!1");
            }
        });
        tableEx2.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!2");
                game.setScreen(new ScreenEx1_2(game, dataBase));
            }
        });
        tableEx3.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!3");
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

        return container;
    }

    public Table evalutelMotto()
    {
        Label.LabelStyle labelStyleBlue2 = new Label.LabelStyle();
        labelStyleBlue2.font = bitmapFontZAP;
        labelStyleBlue2.fontColor = Color.NAVY;
        Label labelMottoTitle = new Label("Primval développé par Evalutel, propose un programe complet de Math et de géométrie pour le Primaire basé sur notre devise:", labelStyleBlue2);

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(screenWidth / 10, screenHeight / 18, 25, Color.WHITE);


        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.rgb888(234, 241, 250));
        bgPixmap.fill();
        textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));


        Table evalutelMotto = new Table();
        evalutelMotto.setBackground((textureRegionDrawableBg));
        evalutelMotto.setSize(19 * screenWidth / 20, screenHeight / 3);
//        container.setPosition(screenWidth / 40, /*(myButtonRetour.getY() - container.getHeight() - screenHeight / 40)*/0);

        evalutelMotto.add(labelMottoTitle).padBottom(screenHeight / 40);
        evalutelMotto.padTop(screenHeight/40);
        evalutelMotto.row();
//        evalutelMotto.debug();

        Table evalutelMottoDetails = new Table();

        Table manipulerTable = new Table();
        manipulerTable.setSize(screenWidth / 4, screenHeight / 5);
        Label labelManipulerTitle = new Label("MANIPULER", labelStyleBlue2);
        Label labelManipulerText = new Label("Des objets interactifs et ludiques sont conçus pour permettre à l'enfant de comprendre la numération , les opérations arithmétiques et les notions de base de géométrie.", labelStyleBlue2);
        labelManipulerText.setWrap(true);
        labelManipulerText.setWidth(screenWidth / 5);

        manipulerTable.add(labelManipulerTitle);
        manipulerTable.row();
        manipulerTable.add(labelManipulerText).width(screenWidth / 4).padLeft(screenWidth / 40).height(screenHeight / 5).padRight(screenWidth/40);
        manipulerTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table apprendreTable = new Table();
        Label labelApprendreTitle = new Label("APPRENDRE", labelStyleBlue2);
        Label labelApprendreText = new Label("Les notions nouvelles sont introduites par des ccours et des exemples. Chaque manipulation est mise en correspondance avec l'opération que l'enfant est invité à faire sur l'ardoise et sur la solution, tout en étant corrigé pas à pas.", labelStyleBlue2);
        labelApprendreText.setWrap(true);
        labelApprendreText.setWidth(screenWidth / 5);

        apprendreTable.add(labelApprendreTitle);
        apprendreTable.row();
        apprendreTable.add(labelApprendreText).width(screenWidth / 4).padLeft(screenWidth / 40).height(screenHeight / 5).padRight(screenWidth/40);
        apprendreTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table evaluerTable = new Table();
        Label labelEvaluerTitle = new Label("EVALUER", labelStyleBlue2);
        Label labelEvaluerText = new Label("Tous les exercices/problèmes sont notés. L'enfant peut toujours améliorer sa note en refaisant l'exercice. Un tableau récapitule les sujets abordés complètement ou partiellement, les temps passés et les résultats obtenus.", labelStyleBlue2);
        labelEvaluerText.setWrap(true);
        labelEvaluerText.setWidth(screenWidth / 4);

        evaluerTable.add(labelEvaluerTitle);
        evaluerTable.row();
        evaluerTable.add(labelEvaluerText).width(screenWidth / 5).padLeft(screenWidth / 40).height(screenHeight / 5).padRight(screenWidth / 40);
        evaluerTable.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        evalutelMottoDetails.add(manipulerTable).padLeft(screenWidth / 80);
        evalutelMottoDetails.add(apprendreTable).padLeft(screenWidth / 80);
        evalutelMottoDetails.add(evaluerTable).padLeft(screenWidth / 80);
        evalutelMotto.add(evalutelMottoDetails).padBottom(screenWidth/80);


        return evalutelMotto;
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
        stage.getViewport().update(width, height, true);
        width = 2400;
        height = 1350;


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
