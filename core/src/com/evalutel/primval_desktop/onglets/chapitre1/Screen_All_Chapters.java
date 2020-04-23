package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.TableauxTitreChapitre;
import com.evalutel.primval_desktop.ListExercicesActiviteView;
import com.evalutel.primval_desktop.MrNotes;
import com.evalutel.primval_desktop.MrNotes2;
import com.evalutel.primval_desktop.MrTemps;
import com.evalutel.primval_desktop.MrTemps2;
import com.evalutel.primval_desktop.MyButtonBuyAnotherChapter;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;

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

    FreeTypeFontGenerator generator;


    public Screen_All_Chapters(Game game, DatabaseDesktop dataBase)
    {
        this.game = game;
        this.dataBase = dataBase;

        stage = new Stage();
        batch = new SpriteBatch();
        BitmapFont bitmapFont;


        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/FRHND521_0.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

//        Label.LabelStyle labelStyle = new Label.LabelStyle();
//        labelStyle.font = bitmapFont;
//        labelStyle.fontColor = Color.BLACK;
//
        Label.LabelStyle labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFont;
        labelStyleWhite.fontColor = Color.WHITE;

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFont;
        labelStyleBlue.fontColor = Color.NAVY;

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        allDrawables = new ArrayList<>();

        fondEspaceParent = new ScreeenBackgroundImage("Images/fond_espaceparent.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Sommaire/fond_onglets_new.jpg");


        myButtonRetour = new MyButtonRetour(stage, screenWidth / 15, screenWidth / 15, game, dataBase);
        myButtonRetour.setPosition(screenWidth / 25, 5 * screenHeight / 6 - myButtonRetour.getHeight() / 2);


        Label labelChap1Titre = new Label("Calcul et géométrie", labelStyleWhite);

        Table nomChapitre = TableauxTitreChapitre.getLigne(labelChap1Titre, null);
        nomChapitre.setPosition(screenWidth / 2 - nomChapitre.getWidth() / 2, 9 * screenHeight / 10);
        stage.addActor(nomChapitre);


        int chapritreNum = 1;

        mrNotes = new MrNotes2(stage, dataBase, 21 * screenWidth / 25, 4 * screenHeight / 5);
        mrTemps = new MrTemps2(stage, dataBase, chapritreNum);


        Table evalutelMotto = new Table();
        evalutelMotto.



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
        this.setScreen(game);
        camera = new PerspectiveCamera();
        viewport = new FitViewport(800, 480, camera);
    }

    private void setScreen(Game game)
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
