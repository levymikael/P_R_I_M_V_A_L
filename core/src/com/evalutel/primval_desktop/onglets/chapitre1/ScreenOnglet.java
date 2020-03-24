package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.primval_desktop.CalculetteViewTest;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.Metrologue;
import com.evalutel.primval_desktop.MyButtonBackToPreviousMenu;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.ReserveBilles;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UneMain;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;
import com.evalutel.primval_desktop.ui_tools.PauseSingleton;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

public class ScreenOnglet implements Screen, InputProcessor
{
    protected int questionCourante = 0;
    protected ReserveBilles reserveBilles;
    int firstPositionX, firstPositionY;
    MyTouchInterface objectTouched;
    //    private int appWidth = 2048;
//    private int appHeight = 1536;
    private SpriteBatch batch;
    private CalculetteViewTest calculetteViewTest;
    private UnePlancheNew planche1, planche2, planche3, touchedPlanche;
    protected Stage stage;
    int screenWidth;
    int screenHeight;
    //    TextButton.TextButtonStyle textButtonStyle;
//    TextButton textButtton;
//    Button.ButtonStyle buttonStyle;
    MyImageButton startPausebutton;
    boolean isVisible = true;


    protected ArrayList<MyDrawInterface> allDrawables;
    protected ArrayList<MyTouchInterface> objectTouchedList;
    private ArrayList<UnePlancheNew> allPlanches = new ArrayList<>();

    long startTime, endTime, seconds, dateTest;


    private Game game;
    DatabaseDesktop dataBase;

    ValidusAnimated validusAnimated;

    Metrologue metrologue;

    int mousePointerX, mousePointerY;
    UneMain uneMain;
    public java.util.Timer timer = new java.util.Timer();

    MyDataBase db;

    UnResultat resultatExercice;

    MyButtonBackToPreviousMenu myButtonBackToPreviousMenu;


    public ScreenOnglet(Game game, DatabaseDesktop dataBase)
    {
        batch = new SpriteBatch();

        stage = new Stage();

        this.game = game;
        this.dataBase = dataBase;

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        db = new MyDataBase(dataBase);


        //Garde aspect ratio

//        stage = new Stage(new FitViewport(screenWidth, screenHeight));
//
//        float widthTest = stage.getWidth();
//        float heightTest = stage.getHeight();

        objectTouchedList = new ArrayList<>();
        allDrawables = new ArrayList<>();

        startTime = System.currentTimeMillis();


        myButtonBackToPreviousMenu = new MyButtonBackToPreviousMenu(game, stage, 200, 200, dataBase);
        myButtonBackToPreviousMenu.setPosition(0, 6 * screenHeight / 7);
        myButtonBackToPreviousMenu.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                ScreenOnglet.this.game.dispose();
                System.out.println("click on Back to Menu");


                endTime = System.currentTimeMillis();
                seconds = (endTime - startTime) / 1000L;

//            java.util.Date date = new java.util.Date();

                dateTest = new Date().getTime() / 1000L;
                ScreenOnglet.this.game.setScreen(new Screen_Chapitre1(ScreenOnglet.this.game, ScreenOnglet.this.dataBase));
                ScreenOnglet.this.db.insertResultat(resultatExercice);
            }
        });
        allDrawables.add(myButtonBackToPreviousMenu);

        startPausebutton = new MyImageButton(stage, "Images/StartPause/button_pause.png", "Images/StartPause/button_lecture.png", 200, 200);
        startPausebutton.setPosition(0, 5 * screenHeight / 7);
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
        int posY = screenHeight / 2;
        uneMain = new UneMain(posX, posY, 200);
        uneMain.setVisible(false);


        validusAnimated = new ValidusAnimated(0, screenHeight / 7, 300, 300);
//        validusAnimated.setPosition(0, screenHeight / 7);
        //allDrawables.add(validusAnimated);

        metrologue = new Metrologue(0, 2 * screenHeight / 5, 300, 300);

//
//
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

        validusAnimated.myDraw(batch);
        metrologue.myDraw(batch);

        batch.end();

        stage.act(delta);
        stage.draw();


        batch.begin();
        if (uneMain.isVisible())
        {
            uneMain.myDraw(batch);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
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
        this.dispose();
    }


    @Override
    public void dispose()
    {
//        stage.dispose();
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
            System.out.println("clickedOnReserve");
            UneBille billeAdded = new UneBille(reserveBilles.currentPositionX + (int) reserveBilles.animationWidth / 2, reserveBilles.currentPositionY + (int) reserveBilles.animationHeight / 2, reserveBilles.largeurBille);
            objectTouchedList.add(billeAdded);
            allDrawables.add(billeAdded);
            objectTouched = billeAdded;
//            firstPositionX = mousePointerX;
//            firstPositionY = mousePointerY;
        }

        else /*si bille part de la planche*/
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


    public class TaskEtape extends TimerTask
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
