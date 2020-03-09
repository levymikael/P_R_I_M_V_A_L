package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.evalutel.primval_desktop.BackgroundColor;
import com.evalutel.primval_desktop.MrNotes;
import com.evalutel.primval_desktop.MrTemps;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.MyButtonValidus;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnePlancheNew;

import java.util.ArrayList;


public class Screen_Chapitre1 implements Screen, InputProcessor
{
    protected Stage stage;
    int screenWidth;
    int screenHeight;
    private SpriteBatch batch;

    TextureRegion bandeauSuperieur;
    TextureRegion sacDeBilles;


    protected ArrayList<MyDrawInterface> allDrawables;
    protected ArrayList<MyTouchInterface> objectTouchedList;
    private ArrayList<UnePlancheNew> allPlanches = new ArrayList<>();

    MyButtonRetour myButtonRetour;

    public Screen_Chapitre1()
    {
        stage = new Stage();

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        allDrawables = new ArrayList<>();

        batch = new SpriteBatch();

        myButtonRetour = new MyButtonRetour(stage, 300, 300);
        myButtonRetour.setPosition(0, screenHeight / 7);
        allDrawables.add(myButtonRetour);

        bandeauSuperieur = new TextureRegion(new Texture(Gdx.files.internal("Images/Chapitre1/bandeau_haut_resultats.jpg")));
        bandeauSuperieur.setRegionWidth((screenWidth * 1024) / 110);

        sacDeBilles = new TextureRegion(new Texture(Gdx.files.internal("Images/billes_panel_droit.png")));
        sacDeBilles.setRegionHeight(200);
        sacDeBilles.setRegionWidth(200);


        MrNotes mrNotes = new MrNotes(5 * screenWidth / 7, 4 * screenHeight / 5, 200, 200);
        allDrawables.add(mrNotes);

        MrTemps mrTemps = new MrTemps(6 * screenWidth / 7, 4 * screenHeight / 5, 200, 200);
        allDrawables.add(mrTemps);


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
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for (int i = 0; i < allDrawables.size(); i++)
        {
            MyDrawInterface newItem = allDrawables.get(i);
            if (newItem.isVisible())
            {
                newItem.myDraw(batch);
            }
        }

        batch.draw(bandeauSuperieur, 0, screenHeight - bandeauSuperieur.getRegionHeight());
        batch.draw(sacDeBilles, screenWidth / 2 - sacDeBilles.getRegionWidth() / 2, 1600);
        batch.end();


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
