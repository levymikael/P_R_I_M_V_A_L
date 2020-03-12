package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evalutel.primval_desktop.General.TableauxTitreChapitre;
import com.evalutel.primval_desktop.ListExercicesActiviteView;
import com.evalutel.primval_desktop.MrNotes;
import com.evalutel.primval_desktop.MrTemps;
import com.evalutel.primval_desktop.MyButtonBuyAnotherChapter;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnePlancheNew;

import java.util.ArrayList;


public class Screen_Chapitre1 extends Game implements Screen, InputProcessor, ApplicationListener
{
    protected Stage stage;
    int screenWidth;
    int screenHeight;
    private SpriteBatch batch;
    private Game game;

    TextureRegion bandeauSuperieur;
    TextureRegion sacDeBilles;

    ListExercicesActiviteView listExercicesActiviteView;
    MrNotes mrNotes;
    MrTemps mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();
    protected ArrayList<MyTouchInterface> objectTouchedList;
    private ArrayList<UnePlancheNew> allPlanches = new ArrayList<>();

    MyButtonRetour myButtonRetour;

    public Screen_Chapitre1(Game game)
    {
        this.game = game;
        stage = new Stage();
        batch = new SpriteBatch();
        BitmapFont bitmapFont;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.BLACK;

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFont;
        labelStyleBlue.fontColor = Color.BLUE;

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        allDrawables = new ArrayList<>();

        ScreeenBackgroundImage fondEspaceParent = new ScreeenBackgroundImage();
        fondEspaceParent.ScreeenBackgroundImage("Images/fond_espaceparent.jpg");
        allDrawables.add(fondEspaceParent);

        Table secondBackground = new Table();
        Pixmap pmWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pmWhite.setColor(Color.WHITE);
        pmWhite.fill();

        secondBackground.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pmWhite))));
        secondBackground.setWidth(screenWidth);
        secondBackground.setHeight(5 * screenHeight / 6);
        stage.addActor(secondBackground);

        listExercicesActiviteView = new ListExercicesActiviteView(stage, game, labelStyle);

        myButtonRetour = new MyButtonRetour(stage, 200, 200);
        myButtonRetour.setPosition(0, 4 * screenHeight / 5);

        Table sacDeBilles = new Table();
        sacDeBilles.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Images/chapitre_circle_1.png")))));


        Table sacDeBillesContainer = new Table();
        sacDeBillesContainer.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Images/background_photo.png")))));
        sacDeBillesContainer.setPosition(screenWidth / 2, 9 * screenHeight / 10);
        sacDeBillesContainer.add(sacDeBilles);
        stage.addActor(sacDeBillesContainer);



        Label labelChap1Titre = new Label("Pratique des nombres de 1 a 9", labelStyleBlue);
        Texture textureNumber1 = new Texture(Gdx.files.internal("Images/chap1.png"));

        Table nomChapitre = TableauxTitreChapitre.getLigne(labelChap1Titre, textureNumber1);
        nomChapitre.setPosition(screenWidth / 2 - nomChapitre.getWidth() / 2, 3 * screenHeight / 4);
        stage.addActor(nomChapitre);


        mrNotes = new MrNotes(stage, "Notes");
        mrTemps = new MrTemps(stage, "Temps");


        MyButtonBuyAnotherChapter myButtonBuyAnotherChapter = new MyButtonBuyAnotherChapter(stage, 800, 150);
        myButtonBuyAnotherChapter.setPosition(7 * screenWidth / 10, screenHeight / 12);

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

        for (int i = 0; i < allDrawables.size(); i++)
        {
            MyDrawInterface newItem = allDrawables.get(i);
            if (newItem.isVisible())
            {
                newItem.myDraw(batch);
            }
        }
//        batch.draw(sacDeBilles, screenWidth / 2 - sacDeBilles.getRegionWidth() / 2, 1400);

        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();


    }

    @Override
    public void create()
    {
        this.setScreen(game);
    }

    private void setScreen(Game game)
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

    }
}
