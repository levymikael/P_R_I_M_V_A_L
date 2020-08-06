package com.evalutel.primval_desktop.onglets.chapitre2;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.onglets.chapitre1.ListExercicesActiviteViewChap1;
import com.evalutel.primval_desktop.MrNotes;
import com.evalutel.primval_desktop.MrTemps;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;

import java.util.ArrayList;


public class Screen_Chapitre2 extends Game implements Screen, InputProcessor, ApplicationListener
{
    protected Stage stage;
    int screenWidth;
    int screenHeight;
    private SpriteBatch batch;
    private Game game;

    private Camera camera;

    private Viewport viewport;

    Texture logoPageOnglet;

    ListExercicesActiviteViewChap2 listExercicesActiviteViewChap2;
    ScreeenBackgroundImage fondEspaceParent;
    ScreeenBackgroundImage fondSommaire;
    MrNotes mrNotes;
    MrTemps mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables;

    MyButtonRetour myButtonRetour;

    FreeTypeFontGenerator generator;


    public Screen_Chapitre2(Game game)
    {
        this.game = game;

        stage = new Stage();
        batch = new SpriteBatch();
        BitmapFont bitmapFont;

        screenHeight = MyConstants.SCREENHEIGHT;
        screenWidth = MyConstants.SCREENWIDTH;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = screenHeight / 50;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFont;
        labelStyleBlue.fontColor = new Color(Color.valueOf("004ec0"));

        allDrawables = new ArrayList<>();

        fondEspaceParent = new ScreeenBackgroundImage("Images/fond_espaceparent.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Pages onglets/fond.jpg");

        listExercicesActiviteViewChap2 = new ListExercicesActiviteViewChap2(stage, game);

        myButtonRetour = new MyButtonRetour(stage, screenWidth / 15, screenWidth / 15, game, "chapitres");
        myButtonRetour.setPosition(screenWidth / 25, 5 * screenHeight / 6 - myButtonRetour.getHeight() / 2);

        logoPageOnglet = new Texture(Gdx.files.internal("Images/Pages onglets/02.png"));
        logoPageOnglet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Label labelChap1Titre = new Label("Introduction de l'addition", labelStyleBlue);
        labelChap1Titre.setFontScale(1.5f);
        Texture textureNumber1 = new Texture(Gdx.files.internal("Images/IndicesChapitres/chap2.png"));

        Table nomChapitre = new Table();

        nomChapitre.add(new Image(textureNumber1)).width(MyConstants.SCREENWIDTH / 25).height(MyConstants.SCREENWIDTH / 25).padRight(screenWidth / 150);
        nomChapitre.add(labelChap1Titre).width((MyConstants.SCREENWIDTH / 5)).align(Align.left);
        nomChapitre.setPosition(4.5f * screenWidth / 10, 7 * screenHeight / 10);
        stage.addActor(nomChapitre);

        int numChapter = 2;

        mrNotes = new MrNotes(stage, numChapter);
        mrTemps = new MrTemps(stage, numChapter);

//        MyButtonBuyAnotherChapter myButtonBuyAnotherChapter = new MyButtonBuyAnotherChapter(stage, 2 * screenWidth / 7, screenHeight / 14);
//        myButtonBuyAnotherChapter.setPosition(7 * screenWidth / 10, screenHeight / 12);

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

        int logoPageOngletWidth = screenWidth / 4;

        batch.draw(logoPageOnglet, screenWidth / 2 - (logoPageOngletWidth / 2), (9 * screenHeight / 15), logoPageOngletWidth, logoPageOngletWidth * (335f / 308f));

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
