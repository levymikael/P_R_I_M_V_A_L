package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pools;
import com.evalutel.ui_tools.MyImageButton;
import com.evalutel.ui_tools.PauseSingleton;

import java.util.ArrayList;
import java.util.TimerTask;


public class ScreenOnglet implements Screen, InputProcessor
{

    ReserveBilles reserveBilles;
    int firstPositionX, firstPositionY;
    MyTouchInterface objectTouched;
    private int appWidth = 2048;
    private int appHeight = 1536;
    private SpriteBatch batch;
    private CalculetteViewTest calculetteViewTest;
    private UnePlancheNew planche1, planche2, planche3, touchedPlanche;
    private Stage stage;
    int screenWidth;
    int screenHeight;
    TextButton.TextButtonStyle textButtonStyle;
    TextButton textButtton;
    Button.ButtonStyle buttonStyle;
    MyImageButton startPausebutton;
    boolean isVisible = true;


    protected ArrayList<MyDrawInterface> allDrawables;
    protected ArrayList<MyTouchInterface> objectTouchedList;
    private ArrayList<UnePlancheNew> allPlanches = new ArrayList<>();

    int mousePointerX, mousePointerY;
    UneMain uneMain;
    public java.util.Timer timer = new java.util.Timer();


    public ScreenOnglet()
    {

        stage = new Stage();

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        objectTouchedList = new ArrayList<>();
        allDrawables = new ArrayList<>();

        batch = new SpriteBatch();

        startPausebutton = new MyImageButton(stage, "Images/StartPause/button_pause.png", "Images/StartPause/button_lecture.png", 200, 200);

        startPausebutton.setPosition(0, 4 * screenHeight / 5);

        stage.addActor(startPausebutton);


        startPausebutton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                PauseSingleton pauseSingleton = PauseSingleton.getInstance();
                pauseSingleton.isPause = !pauseSingleton.isPause;
            }
        });


        int posX = 6 * screenWidth / 7;
        int posY = 5 * screenHeight / 7;
        uneMain = new UneMain( posX, posY, 200);
        uneMain.setVisible(false);

        /*
        calculetteViewTest = new CalculetteViewTest(stage, 200, 200, 700, 600);

        allDrawables.add(calculetteViewTest);

        reserveBilles = new ReserveBilles(screenWidth - 300, screenHeight - 300, 200, 200);
        allDrawables.add(reserveBilles);

        largeurBille = 100;
        int largeurPlanche = largeurBille * 4;

        planche1 = new UnePlancheNew(1000, 200, largeurPlanche, largeurBille);
        planche2 = new UnePlancheNew(1400, 200, largeurPlanche, largeurBille);
        planche3 = new UnePlancheNew(1800, 200, largeurPlanche, largeurBille);

        allPlanches.add(planche1);
        allPlanches.add(planche2);
        allPlanches.add(planche3);

        planche1.shouldReturnToReserve = false;
        allDrawables.add(planche1);
        planche2.shouldReturnToReserve = false;
        allDrawables.add(planche2);
        planche3.shouldReturnToReserve = true;
        allDrawables.add(planche3);*/

    }

    @Override
    public void show()
    {
        InputProcessor inputProcessorTwo = this;
        InputProcessor inputProcessorOne = stage;
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1); // center
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
        if (uneMain.isVisible())
        {
            uneMain.myDraw(batch);
        }

        batch.end();
        stage.draw();
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = screenHeight - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (reserveBilles.contains(screenX, reversedScreenY) && reserveBilles.isActive()) /*si bille part de la reserve*/
        {
            System.out.println("clickedOnContainer");
            UneBille billeAdded = new UneBille(reserveBilles.currentPositionX + (int) reserveBilles.animationWidth / 2, reserveBilles.currentPositionY + (int) reserveBilles.animationHeight / 2, reserveBilles.largeurBille, reserveBilles.largeurBille);
            objectTouchedList.add(billeAdded);
            allDrawables.add(billeAdded);
            objectTouched = billeAdded;
//            firstPositionX = mousePointerX;
//            firstPositionY = mousePointerY;
        } else /*si bille part de la planche*/
        {
            for (int i = 0; i < objectTouchedList.size(); i++)
            {
                MyTouchInterface objetAux = objectTouchedList.get(i);

                if (objetAux.isTouched(screenX, reversedScreenY))
                {
                    objectTouched = objetAux;
                    firstPositionX = objectTouched.getPosition().x;
                    firstPositionY = objectTouched.getPosition().y;

                    if (objectTouched instanceof UneBille)
                    {
                        UneBille billeAux = (UneBille) objectTouched;
                        billeAux.touchDown();
                        break;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        if (objectTouched != null)
        {
            objectTouched.setPosition((int) (screenX - objectTouched.getWidth() / 2), (int) (screenHeight - screenY - objectTouched.getHeight() / 2));
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        if (objectTouched != null)
        {
            //AnimationImageNew animationImageNewAux = (AnimationImageNew) objectTouched;
            if (objectTouched instanceof UneBille)
            {
                UneBille billeAux = (UneBille) objectTouched;
                billeAux.touchUp(allPlanches, screenX, screenHeight - screenY);
//
//                else /*si bille pas deposee dans planche*/
//                    {
//                    objectTouched.setPosition(firstPositionX, firstPositionY);
//                    if (billeAux.plancheNew != null) {
//                        if (billeAux.plancheNew.shouldReturnToReserve)
//                        {
//                            billeAux.setPosition(100000, 100000);
//                            allDrawables.remove(billeAux);
//                            billeAux.plancheNew = null;
//                        }
//                        else {
//                            planche1.addBilleAndOrganize(billeAux);
//                            planche2.addBilleAndOrganize(billeAux);
//                            planche3.addBilleAndOrganize(billeAux);
//                        }
//                    } else {
//                        allDrawables.remove(billeAux);
//                        billeAux.setPosition(100000, 100000);
//                    }
//                }
            }

        }
        objectTouched = null;
        return false;
    }

    protected class TaskEtape extends TimerTask
    {
        protected long durationMillis;

        protected TaskEtape(long dT)
        {
            durationMillis = dT;
        }

        @Override
        public void run()
        {

        }
    }
}
