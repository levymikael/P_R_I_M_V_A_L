package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MrNotes;
import com.evalutel.primval_desktop.MrTemps;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;

import java.util.ArrayList;


public class Screen_Chapitre1 extends Game implements Screen, InputProcessor, ApplicationListener
{
    //    private DatabaseDesktop dataBase;
    protected Stage stage;
    int screenWidth;
    int screenHeight;
    private SpriteBatch batch;
    private Game game;

    private Camera camera;

    private Viewport viewport;

    Texture logoChapitre;

    ListExercicesActiviteViewChap1 listExercicesActiviteViewChap1;
    ScreeenBackgroundImage bandeauHaut;
    ScreeenBackgroundImage fondSommaire;
    MrNotes mrNotes;
    MrTemps mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables;

    MyButtonRetour myButtonRetour;

    FreeTypeFontGenerator generator;


    public Screen_Chapitre1(Game game)
    {
        this.game = game;
        stage = new Stage();
        batch = new SpriteBatch();

        screenHeight = MyConstants.SCREENHEIGHT;
        screenWidth = MyConstants.SCREENWIDTH;


        float fontSize = MyConstants.SCREENWIDTH / 55f;


        FreeTypeFontGenerator generatorComicSansMSBold = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterComicSansMSBold = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterComicSansMSBold.size = (int) fontSize;
        parameterComicSansMSBold.minFilter = Texture.TextureFilter.Linear;
        parameterComicSansMSBold.magFilter = Texture.TextureFilter.Linear;
        BitmapFont bitmapFontComicSansMSBold = generatorComicSansMSBold.generateFont(parameterComicSansMSBold);
        generatorComicSansMSBold.dispose();


        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFontComicSansMSBold;
        labelStyleBlue.fontColor = MyConstants.bluePrimval;

        allDrawables = new ArrayList<>();

        bandeauHaut = new ScreeenBackgroundImage("Images/Pages Chapitres/Bandeau haut.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Pages onglets/fond.jpg");

        listExercicesActiviteViewChap1 = new ListExercicesActiviteViewChap1(stage, game);

        myButtonRetour = new MyButtonRetour(stage, screenWidth / 15f, screenWidth / 15f, game, "chapitres");
        myButtonRetour.setPosition(screenWidth / 25f, 5 * screenHeight / 6f - myButtonRetour.getHeight() / 2f);

        logoChapitre = new Texture(Gdx.files.internal("Images/chapitre_circle_1.png"));
        logoChapitre.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Label labelChap1Titre = new Label("Pratique des nombres de 1 à 9", labelStyleBlue);
//        labelChap1Titre.setFontScale(1.5f);
        Texture textureNumber1 = new Texture(Gdx.files.internal("Images/chap1.png"));

        Table nomChapitre = new Table();

        float nomChapitreWidth = MyConstants.SCREENWIDTH / 5f;


        nomChapitre.add(new Image(textureNumber1)).width(MyConstants.SCREENWIDTH / 25f).height(MyConstants.SCREENWIDTH / 25f).padRight(screenWidth / 150f);
        nomChapitre.add(labelChap1Titre).width(MyConstants.SCREENWIDTH / 4f).align(Align.left);
        nomChapitre.setWidth(nomChapitreWidth);
        nomChapitre.setPosition(screenWidth / 2f - (nomChapitreWidth / 2), 4f * screenHeight / 6f);
        stage.addActor(nomChapitre);

        int numChapter = 1;

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

        float bandeauHautY = (MyConstants.SCREENHEIGHT - MyConstants.SCREENHEIGHT / 6f);

        fondSommaire.myDraw2(batch, 0, 0, MyConstants.SCREENWIDTH, 5.05f * screenHeight / 6f);

        bandeauHaut.myDraw2(batch, 0, bandeauHautY, MyConstants.SCREENWIDTH, MyConstants.SCREENHEIGHT / 6f);

        for (int i = 0; i < allDrawables.size(); i++)
        {
            MyDrawInterface newItem = allDrawables.get(i);
            if (newItem.isVisible())
            {
                newItem.myDraw(batch);
            }
        }

        float logoChapitreWidth = screenWidth / 3.8f;

        float logoChapitreHeight = logoChapitreWidth * (305f / 521f);

        float positionYLogoChapitre = bandeauHautY - (logoChapitreHeight / 2f);

        batch.draw(logoChapitre, screenWidth / 2f - (logoChapitreWidth / 2f), positionYLogoChapitre, logoChapitreWidth, logoChapitreHeight);

        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void create()
    {

    }


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
