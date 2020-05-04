package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evalutel.primval_desktop.CalculetteViewTest;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.EcrinDiamantView;
import com.evalutel.primval_desktop.Metrologue;
import com.evalutel.primval_desktop.MyButtonBackToPreviousMenu;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.MyPauseGeneral;
import com.evalutel.primval_desktop.MyTimer;
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

public class ScreenOnglet implements Screen, InputProcessor
{
    protected int questionCourante = 0;
    protected ReserveBilles reserveBilles;
    int firstPositionX, firstPositionY;
    MyTouchInterface objectTouched;
    private SpriteBatch batch;
    private CalculetteViewTest calculetteViewTest;
    protected Stage stage;
    int screenWidth;
    int screenHeight;
    //    TextButton.TextButtonStyle textButtonStyle;
//    TextButton textButtton;
//    Button.ButtonStyle buttonStyle;
    MyImageButton startPausebutton;
    boolean isVisible = true;
    protected MyTimer timer;

    boolean isInPause = true;

    protected ArrayList<MyDrawInterface> allDrawables;
    protected ArrayList<MyTouchInterface> objectTouchedList;
    private ArrayList<UnePlancheNew> allPlanches = new ArrayList<>();
//    private ArrayList<MyPauseInterface> allPauseables = new ArrayList<>();

    long startTime, endTime, seconds, dateTest;

    private Game game;
    DatabaseDesktop dataBase;

    ValidusAnimated validusAnimated;

    Metrologue metrologue;

    int mousePointerX, mousePointerY;

    int largeurBille, largeurPlanche;
    UneMain uneMain;

    MyPauseGeneral myPauseGeneral;

    MyDataBase db;

    UnResultat resultatExercice;

    MyButtonBackToPreviousMenu myButtonBackToPreviousMenu;

    EcrinDiamantView ecrinDiamantView;


    public ScreenOnglet(Game game, DatabaseDesktop dataBase, int chapitre, int onglet, boolean ecrin)
    {
        batch = new SpriteBatch();

        stage = new Stage();

        this.game = game;
        this.dataBase = dataBase;

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        db = new MyDataBase(dataBase);

        largeurBille = screenWidth / 15;
        largeurPlanche = largeurBille * 4;

        //Garde aspect ratio

//        stage = new Stage(new FitViewport(screenWidth, screenHeight));
//
//        float widthTest = stage.getWidth();
//        float heightTest = stage.getHeight();

        objectTouchedList = new ArrayList<>();
        allDrawables = new ArrayList<>();

        startTime = System.currentTimeMillis();

        resultatExercice = new UnResultat("", chapitre, onglet, 0, "", 0, 0, 0, 0, 0, 0, 0);

        myButtonBackToPreviousMenu = new MyButtonBackToPreviousMenu(game, stage, screenWidth / 15, screenWidth / 15, dataBase);
        myButtonBackToPreviousMenu.setPosition(0, 6 * screenHeight / 7);
        myButtonBackToPreviousMenu.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                ScreenOnglet.this.game.dispose();
                Gdx.app.log("button click", "click!");

                endTime = System.currentTimeMillis();
                seconds = (endTime - startTime) / 1000L;

                long dateEnd = new Date().getTime() / 1000L;
                resultatExercice.setDuree(seconds);
                resultatExercice.setDate(endTime);

                if ((metrologue.isSpeaking))
                {
                    metrologue.stopMusic();
                }
                else if ((validusAnimated.isSpeaking))
                {
                    validusAnimated.stopMusic();
                }

                timer.cancel();

                ScreenOnglet.this.db.insertResultat(resultatExercice);

                ScreenOnglet.this.game.setScreen(new Screen_Chapitre1(ScreenOnglet.this.game, ScreenOnglet.this.dataBase));
            }
        });
        allDrawables.add(myButtonBackToPreviousMenu);

        startPausebutton = new MyImageButton(stage, "Images/StartPause/button_pause.png", screenWidth / 15, screenWidth / 15);
        startPausebutton.setPosition(0, 5 * screenHeight / 7);
        stage.addActor(startPausebutton);

        myPauseGeneral = new MyPauseGeneral();

        startPausebutton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                String pausePlayButtonPath;

                if (isInPause)
                {
                    pausePlayButtonPath = "Images/StartPause/button_lecture.png";

                    myPauseGeneral.pause();

                    isInPause = !isInPause;
                }
                else
                {
                    pausePlayButtonPath = "Images/StartPause/button_pause.png";

                    isInPause = !isInPause;
                    myPauseGeneral.resume();
                }


                Texture texture = new Texture(Gdx.files.internal(pausePlayButtonPath));
                Sprite sprite = new Sprite(texture);
                sprite.setSize(screenWidth / 15, screenWidth / 15);

                startPausebutton.getStyle().imageUp = new SpriteDrawable(sprite);
                ;//new TextureRegionDrawable(new Texture(pausePlayButtonPath));
                PauseSingleton pauseSingleton = PauseSingleton.getInstance();
                pauseSingleton.isPause = !pauseSingleton.isPause;

                int ok = 5;
                ok++;

            }
        });


        int posX = 6 * screenWidth / 7;
        int posY = screenHeight / 2;
        uneMain = new UneMain(posX, posY, screenWidth / 6);
        uneMain.setVisible(false);


        validusAnimated = new ValidusAnimated(0, screenHeight / 7, screenHeight / 5, screenHeight / 5);
        myPauseGeneral.addElements(validusAnimated);

        if (ecrin)
        {
            ecrinDiamantView = new EcrinDiamantView(stage, screenWidth / 8, 9);
            ecrinDiamantView.updateText();
            allDrawables.add(ecrinDiamantView);

        }

        metrologue = new Metrologue(0, 2 * screenHeight / 5, screenHeight / 5, screenHeight / 5);
        myPauseGeneral.addElements(metrologue);


        timer = new MyTimer();

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
//        stage.getViewport().update(width, height, true);
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
                billeAux.touchUp(allPlanches/*, screenX, screenHeight - screenY*/);
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

    protected void addDiamonds(int nbDiamant)
    {
        ecrinDiamantView.addDiamond(nbDiamant);
        resultatExercice.setmPointsObtenus(resultatExercice.getPointsObtenus() + nbDiamant);
        resultatExercice.setmPointsPossibles(resultatExercice.getPointsPossibles() + nbDiamant);
    }

    protected void addPierres(int nbPierres)
    {
        ecrinDiamantView.addPierre(nbPierres);
        resultatExercice.setmPointsPossibles(resultatExercice.getPointsPossibles() + nbPierres);
    }
}
