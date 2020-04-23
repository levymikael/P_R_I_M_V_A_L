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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.TableauxTitreChapitre;
import com.evalutel.primval_desktop.General.UIDesign;
import com.evalutel.primval_desktop.ListExercicesActiviteView;
import com.evalutel.primval_desktop.MrNotes;
import com.evalutel.primval_desktop.MrNotes2;
import com.evalutel.primval_desktop.MrTemps;
import com.evalutel.primval_desktop.MyButtonBuyAnotherChapter;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnePlancheNew;

import java.util.ArrayList;


public class Screen_Sommaire_General extends Game implements Screen, InputProcessor, ApplicationListener
{
    private DatabaseDesktop dataBase;
    protected Stage stage;
    int screenWidth;
    int screenHeight;
    private SpriteBatch batch;
    private Game game;

    private Camera camera;

    private Viewport viewport;


    ScreeenBackgroundImage fondSommairee;
    ScreeenBackgroundImage fondSommaire;
    ScreeenBackgroundImage imgSommaire;

    MrNotes2 mrNotes2;
    MrTemps mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();

//    Table chapitresOnglet;

    FreeTypeFontGenerator generator;

    Texture logoTitre;


    public Screen_Sommaire_General(final Game game, final DatabaseDesktop dataBase)
    {
        this.game = game;
        this.dataBase = dataBase;

        stage = new Stage();
        batch = new SpriteBatch();
        BitmapFont bitmapFont;


        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/FRHND521_0.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFont;
        labelStyleBlue.fontColor = Color.BLUE;

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        allDrawables = new ArrayList<>();

        fondSommairee = new ScreeenBackgroundImage("Images/Sommaire/fond_sommaire_color.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Sommaire/sommaire_bandeau_gauche.jpg");

        imgSommaire = new ScreeenBackgroundImage("Images/Sommaire/image_sommaire.png");


        logoTitre = new Texture(Gdx.files.internal("Images/Sommaire/titre_sommaire.png"));


        mrNotes2 = new MrNotes2(stage, dataBase,screenWidth / 25, 5 * screenHeight / 10);


        Label labelChapitres = new Label("Chapitres", labelStyleBlue);
        Label labelResultats = new Label("Résultats", labelStyleBlue);
        Label labelEspaceParents = new Label("Espace Parents", labelStyleBlue);
        Label labelPresentation = new Label("Présentation", labelStyleBlue);


        Table container = new Table();
        container.setPosition(screenWidth / 30, 2 * screenHeight / 7);
        container.setWidth(screenWidth / 7);
        container.setHeight(screenHeight / 6);

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(screenWidth / 10, screenHeight / 18, 25, Color.WHITE);


        Table chaptersButton = new Table();
        chaptersButton.add(labelChapitres);
        chaptersButton.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));
        chaptersButton.setSize(screenWidth / 10, screenHeight / 12);

        Table resultsButton = new Table();
        resultsButton.add(labelResultats);
        resultsButton.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));


        Table espaceParentsButton = new Table();
        espaceParentsButton.add(labelEspaceParents);
        espaceParentsButton.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));


        Table presentation = new Table();
        presentation.add(labelPresentation);
        presentation.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));


        container.add(chaptersButton).pad(20).align(Align.center);
        container.row();
        container.add(resultsButton).pad(20).align(Align.center);
        container.row();
        container.add(espaceParentsButton).pad(20).align(Align.center);
        container.row();
        container.add(presentation).pad(20).align(Align.center);


        container.debug();


        stage.addActor(container);

        chaptersButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new Screen_Chapitre1(game, dataBase));

                Gdx.app.log("chapitres button", "I got clicked!");
            }
        });

        Gdx.input.setInputProcessor(stage);
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

        fondSommairee.myDraw(batch);
        fondSommaire.myDraw2(batch, screenWidth / 5, screenHeight, 0, 0);
        imgSommaire.myDraw2(batch, 680*2, 600*2 , screenWidth / 3, screenHeight/5);


        for (int i = 0; i < allDrawables.size(); i++)
        {
            MyDrawInterface newItem = allDrawables.get(i);
            if (newItem.isVisible())
            {
                newItem.myDraw(batch);
            }
        }

        batch.draw(logoTitre, screenWidth / 30, 14 * screenHeight / 15 - logoTitre.getHeight());


        batch.end();


        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void create()
    {
//
        camera = new PerspectiveCamera();
        viewport = new FitViewport(800, 480, camera);

//        stage = new Stage(new StretchViewport(width, height));

    }

    private void setScreen()
    {
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
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
