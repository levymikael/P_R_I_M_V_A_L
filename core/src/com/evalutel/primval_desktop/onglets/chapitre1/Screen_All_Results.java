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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.CollapsibleWidget;
import com.evalutel.primval_desktop.General.LigneTableaux2;
import com.evalutel.primval_desktop.General.TableauxTitreChapitre;
import com.evalutel.primval_desktop.General.VisUI;
import com.evalutel.primval_desktop.ListExercicesActiviteView;
import com.evalutel.primval_desktop.MrNotes;
import com.evalutel.primval_desktop.MrTemps;
import com.evalutel.primval_desktop.MyButtonDemos;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

import java.util.ArrayList;


public class Screen_All_Results extends Game implements Screen, InputProcessor, ApplicationListener
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
    MrNotes mrNotes;
    MrTemps mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();
    MyButtonRetour myButtonRetour;
    MyButtonDemos myButtonDemo;

    FreeTypeFontGenerator generatorFRHND;
    FreeTypeFontGenerator generatorZAP;
    TextureRegionDrawable orangeBg;
    TextureRegionDrawable blueBg;

    BitmapFont bitmapFontZAP;


    public Screen_All_Results(Game game, DatabaseDesktop dataBase)
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

        Label labelChap1Titre = new Label("Résultats obtenus", labelStyleWhite);

        Table nomChapitre = TableauxTitreChapitre.getLigne(labelChap1Titre, null);
        nomChapitre.setPosition(screenWidth / 2 - nomChapitre.getWidth() / 2, 9 * screenHeight / 10);
        stage.addActor(nomChapitre);

        mrNotes = new MrNotes(stage, dataBase);
        mrTemps = new MrTemps(stage, dataBase, 1);


        //tableau deroulant pour Evalutel motto et liste de chapitre

        Table container = new Table();
        Table table = new Table();

        container.setSize(screenWidth, (float) (2 * screenHeight));


        table.setWidth(screenWidth);

        Table table2 = chapter1Results();
        Table table3 = chapter2Results();
        Table tableCollapsible = new Table();

        Pixmap bgOrange = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgOrange.setColor(Color.rgb888(241, 160, 57));
        bgOrange.fill();
        orangeBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgOrange)));

        Pixmap bgBlue = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgBlue.setColor(Color.BLUE);
        bgBlue.fill();
        blueBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgBlue)));
        tableCollapsible.setBackground(blueBg);

//        VisUI.load();
//
//        VisTable visTable = new VisTable();
//        collapsibleWidget = new CollapsibleWidget(visTable);
//
//        visTable.setPosition(0, 0);
//        collapsibleWidget.setPosition(0, 0);
//
//        table.addListener(new ChangeListener()
//        {
//            @Override
//            public void changed(ChangeEvent event, Actor actor)
//            {
//                collapsibleWidget.setCollapsed(!collapsibleWidget.isCollapsed());
//                System.out.println("I got clicked!1");
//
//            }
//        });
//
//        visTable.debug();

//        tableCollapsible.setSize(screenWidth - screenWidth / 50, screenHeight / 20);

//        table2.setBackground(orangeBg);

        table.add(table2).width(screenWidth).align(Align.center).padBottom(screenHeight / 15);
        table.row();
        table.add(table3).width(screenWidth).align(Align.center);

        ScrollPane scroll = new ScrollPane(table);
        scroll.layout();

        container.add(scroll).height(screenHeight - (screenHeight - (myButtonRetour.getY() + myButtonRetour.getHeight() / 2)));
        container.row();


//        table2.debug();

//        stage.addActor(collapsibleWidget);
//        stage.addActor(visTable);
        stage.addActor(container);
        container.setFillParent(true);


//        VisUI.dispose();

        Gdx.input.setInputProcessor(stage);

    }

    public Table chapter1Results()
    {
        Table table = new Table();
        Table container = new Table();

        String labelChapterTitle = "Pratique des nombres de 1 à 9";
        String label1 = "Les nombres de 1 à 9. Badix, Métrologue et Validus";
        String label2 = "Faire correspondre des billes à des oiseaux";
        String label3 = "Écriture des chiffres 1 à 9";
        String label4 = "Prononciation des chiffres 1 à 9";
        String label5 = "Compter des oiseaux et taper leur nombre";
        String label6 = "Un gâteau pour plusieurs anniversaires";

        Texture textureCours = new Texture(Gdx.files.internal("Images/icon_cours.png"));
        Texture textureExercices = new Texture(Gdx.files.internal("Images/icon_exercice.png"));

//        stage.addActor(container);
        container.setSize(screenWidth, 2 * screenHeight / 5);
//        container.setPosition(0, screenHeight / 7);

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap1.png", "Images/IndicesChapitres/chap1.png", screenWidth / 40, "font/FRHND521_0.TTF", 20);
        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/red_circle.png", "Images/red_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);
        MyTextButton six_bouton = new MyTextButton("6", "Images/blue_circle.png", "Images/blue_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);


        Table tableChapTitle = LigneTableaux2.getLigne(chapter_bouton, labelChapterTitle, null, "white", 1, 1, dataBase);
        Table tableEx1 = LigneTableaux2.getLigne(un_bouton, label1, textureCours, "red", 1, 1, dataBase);
        Table tableEx2 = LigneTableaux2.getLigne(deux_bouton, label2, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx3 = LigneTableaux2.getLigne(trois_bouton, label3, textureCours, "red", 1, 2, dataBase);
        Table tableEx4 = LigneTableaux2.getLigne(quatre_bouton, label4, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx5 = LigneTableaux2.getLigne(cinq_bouton, label5, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx6 = LigneTableaux2.getLigne(six_bouton, label6, textureExercices, "blue", 1, 2, dataBase);
        tableEx6.debug();

        tableChapTitle.setBackground(orangeBg);

        table.add(tableChapTitle).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx1).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx2).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx3).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx4).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx5).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx6).width(screenWidth).height(screenHeight / 20).align(Align.center);
        table.row();

        table.setWidth(screenWidth);
//
//        ScrollPane scroll = new ScrollPane(table);
//        scroll.layout();

        container.add(table).height(4 * screenHeight / 5);
//        container.row();


        return container;
    }

    public Table chapter2Results()
    {
        Table table = new Table();
        Table container = new Table();

        String labelChapterTitle = "Pratique des nombres de 1 à 9";
        String label1 = "Les nombres de 1 à 9. Badix, Métrologue et Validus";
        String label2 = "Faire correspondre des billes à des oiseaux";
        String label3 = "Écriture des chiffres 1 à 9";
        String label4 = "Prononciation des chiffres 1 à 9";
        String label5 = "Compter des oiseaux et taper leur nombre";
        String label6 = "Un gâteau pour plusieurs anniversaires";

        Texture textureCours = new Texture(Gdx.files.internal("Images/icon_cours.png"));
        Texture textureExercices = new Texture(Gdx.files.internal("Images/icon_exercice.png"));

//        stage.addActor(container);
        container.setSize(screenWidth, 2 * screenHeight / 5);
        container.setPosition(0, screenHeight / 7);

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap1.png", "Images/IndicesChapitres/chap1.png", screenWidth / 40, "font/FRHND521_0.TTF", 20);
        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/red_circle.png", "Images/red_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);
        MyTextButton six_bouton = new MyTextButton("6", "Images/blue_circle.png", "Images/blue_circle.png", screenWidth / 40, "font/FRHND521_0.TTF", 40);


        Table tableChapTitle = LigneTableaux2.getLigne(chapter_bouton, labelChapterTitle, null, "white", 1, 1, dataBase);
        Table tableEx1 = LigneTableaux2.getLigne(un_bouton, label1, textureCours, "red", 1, 1, dataBase);
        Table tableEx2 = LigneTableaux2.getLigne(deux_bouton, label2, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx3 = LigneTableaux2.getLigne(trois_bouton, label3, textureCours, "red", 1, 2, dataBase);
        Table tableEx4 = LigneTableaux2.getLigne(quatre_bouton, label4, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx5 = LigneTableaux2.getLigne(cinq_bouton, label5, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx6 = LigneTableaux2.getLigne(six_bouton, label6, textureExercices, "blue", 1, 2, dataBase);
        tableEx6.debug();

        tableChapTitle.setBackground(orangeBg);

        table.add(tableChapTitle).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx1).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx2).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx3).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx4).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx5).width(screenWidth).height(screenHeight / 25).align(Align.center);
        table.row();
        table.add(tableEx6).width(screenWidth).height(screenHeight / 20).align(Align.center);
        table.row();

        table.setWidth(screenWidth);
//
//        ScrollPane scroll = new ScrollPane(table);
//        scroll.layout();

        container.add(table).height(4 * screenHeight / 5);
        container.row();


        return container;
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

//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
