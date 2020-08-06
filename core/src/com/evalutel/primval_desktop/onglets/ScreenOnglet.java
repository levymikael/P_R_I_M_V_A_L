package com.evalutel.primval_desktop.onglets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.EcrinDiamantView;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.Metrologue;
import com.evalutel.primval_desktop.MyButtonBackToPreviousMenu;
import com.evalutel.primval_desktop.MyCorrectionAndPauseGeneral;
import com.evalutel.primval_desktop.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.SacDeBilles;
import com.evalutel.primval_desktop.SacDeBougies;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UneMain;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.onglets.chapitre1.Screen_Chapitre1;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;
import com.evalutel.primval_desktop.ui_tools.PauseSingleton;

import java.util.ArrayList;

public class ScreenOnglet implements Screen, InputProcessor
{
    protected int questionCourante = 0;
    protected SacDeBilles sacDeBilles;
    protected SacDeBougies sacDeBougies;
    protected float firstPositionX, firstPositionY;
    protected MyTouchInterface objectTouched;
    protected SpriteBatch batch;
    protected Stage stage;

    protected MyImageButton startPausebutton;
    boolean isVisible = true;
    protected MyTimer timer;

    protected BitmapFont bitmapFontArial, bitmapFontComic;

    boolean isInPause = false;

    protected ArrayList<MyDrawInterface> allDrawables;
    //    protected ArrayList<MyCorrectionAndPauseInterface> allCorrigibles;
    protected ArrayList<MyTouchInterface> objectTouchedList;

    protected ArrayList<UnePlancheNew> allPlanches = new ArrayList<>();

    protected long startTime, endTime, seconds, dateTest;

    private Game game;

    protected ValidusAnimated validusAnimated;

    protected Metrologue metrologue;

    protected int mousePointerX, mousePointerY;

    protected float largeurBilleUnique, largeurBilleMultiple;
    protected float largeurPlancheUnique, largeurPlancheMultiple;
    protected float largeurBougie, largeurGateau;
    protected UneMain uneMain;

    protected MyCorrectionAndPauseGeneral myCorrectionAndPauseGeneral;

    protected Table tableTitre;

    protected UnResultat resultatExercice;

    protected MyButtonBackToPreviousMenu myButtonBackToPreviousMenu;

    protected EcrinDiamantView ecrinDiamantView;

    protected float activiteWidth, heightTop, xTableTitre;

    protected ActiviteView activiteView;
    protected ActiviteView solutionView;

    protected Label.LabelStyle labelStyleArial, labelStyleComic, labelStyle3;

    protected FreeTypeFontGenerator fontArial, fontComic;

    protected String numExercice;

    protected Label exoNumLabel, exoConsigneLabel, highestMarkObtainedLabel;
//    private MyDataBase db;


    public ScreenOnglet(Game game, int chapitre, int onglet, boolean ecrin, int maxNotePossible)
    {
        batch = new SpriteBatch();

        stage = new Stage();

        this.game = game;

        AppSingleton appSingleton = AppSingleton.getInstance();
        MyDataBase db = appSingleton.myDataBase;

        tableTitre = new Table();

        Texture textureTitre = new Texture("Images/EnonceUIElements/titre_top.png");
        textureTitre.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        tableTitre.setBackground(new SpriteDrawable(new Sprite(textureTitre)));

// Positionnement numero exercice:
        tableTitre.pack();
        activiteWidth = (MyConstants.SCREENWIDTH / 4f) * 3f;

        heightTop = (activiteWidth * 42 / 1626) + 5;
        xTableTitre = (MyConstants.SCREENWIDTH / 2.0f - activiteWidth / 2.0f);
        tableTitre.setPosition(xTableTitre /*+ MyConstants.SCREENWIDTH / 200*/, MyConstants.SCREENHEIGHT - heightTop);

        tableTitre.setWidth(activiteWidth);
        tableTitre.setHeight(heightTop);

        fontArial = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENWIDTH / 70;
        bitmapFontArial = fontArial.generateFont(parameter);
        fontArial.dispose();

        fontComic = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterComic = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterComic.size = MyConstants.SCREENWIDTH / 80;
        bitmapFontComic = fontComic.generateFont(parameter);
        fontComic.dispose();

        labelStyleComic = new Label.LabelStyle();
        labelStyleComic.font = bitmapFontComic;
        labelStyleComic.fontColor = Color.WHITE;

        labelStyleArial = new Label.LabelStyle();
        labelStyleArial.font = bitmapFontArial;
        labelStyleArial.fontColor = Color.YELLOW;

        labelStyle3 = new Label.LabelStyle();
        labelStyle3.font = bitmapFontArial;
        labelStyle3.fontColor = Color.YELLOW;

        largeurBilleUnique = MyConstants.SCREENWIDTH / 15;
        largeurPlancheUnique = largeurBilleUnique * 4;
        largeurBilleMultiple = MyConstants.SCREENWIDTH / 25;
        largeurPlancheMultiple = largeurBilleMultiple * 4;

        largeurBougie = MyConstants.SCREENWIDTH / 15;
        largeurGateau = largeurBougie * 4;

        objectTouchedList = new ArrayList<>();
        allDrawables = new ArrayList<>();
//        allCorrigibles = new ArrayList<>();

        startTime = System.currentTimeMillis();

        myCorrectionAndPauseGeneral = new MyCorrectionAndPauseGeneral();

        resultatExercice = new UnResultat("", chapitre, onglet, 0, "", 0, 0, 0, 0, 0, 0, 0);

        myButtonBackToPreviousMenu = new MyButtonBackToPreviousMenu(game, stage, MyConstants.SCREENWIDTH / 15, MyConstants.SCREENWIDTH / 15);
        myButtonBackToPreviousMenu.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
//                ScreenOnglet.this.game.dispose();
                Gdx.app.log("button click", "click!");

                endTime = System.currentTimeMillis();
                seconds = (endTime - startTime) / 1_000L;

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
                AppSingleton appSingleton = AppSingleton.getInstance();
                MyDataBase db =  appSingleton.myDataBase;

                db.insertResultat(resultatExercice);

                ScreenOnglet.this.game.setScreen(new Screen_Chapitre1(ScreenOnglet.this.game /*,ScreenOnglet.this.dataBase*/));

                ScreenOnglet.this.dispose();
            }
        });
        allDrawables.add(myButtonBackToPreviousMenu);

        startPausebutton = new MyImageButton(stage, "Images/StartPause/button_pause.png", MyConstants.SCREENWIDTH / 15, MyConstants.SCREENWIDTH / 15);
        startPausebutton.setPosition(MyConstants.SCREENWIDTH / 60, 5 * MyConstants.SCREENHEIGHT / 7);
        stage.addActor(startPausebutton);

        startPausebutton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                String pausePlayButtonPath;

                if (!isInPause)
                {
                    pausePlayButtonPath = "Images/StartPause/button_lecture.png";
                    myCorrectionAndPauseGeneral.pause();

                    isInPause = !isInPause;
                }
                else
                {
                    pausePlayButtonPath = "Images/StartPause/button_pause.png";

                    isInPause = !isInPause;
                    myCorrectionAndPauseGeneral.resume();
                }

                Texture texture = new Texture(Gdx.files.internal(pausePlayButtonPath));
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

                Sprite sprite = new Sprite(texture);
                sprite.setSize(MyConstants.SCREENWIDTH / 15, MyConstants.SCREENWIDTH / 15);

                startPausebutton.getStyle().imageUp = new SpriteDrawable(sprite);
                PauseSingleton pauseSingleton = PauseSingleton.getInstance();
                pauseSingleton.isPause = !pauseSingleton.isPause;
            }
        });


        int posX = 6 * MyConstants.SCREENWIDTH / 7;
        int posY = MyConstants.SCREENHEIGHT / 2;

        uneMain = new UneMain(posX, posY, MyConstants.SCREENWIDTH / 6);
        uneMain.setVisible(false);
        myCorrectionAndPauseGeneral.addElements(uneMain);

        timer = new MyTimer();

        validusAnimated = new ValidusAnimated(MyConstants.SCREENWIDTH / 60, MyConstants.SCREENHEIGHT / 7, MyConstants.SCREENHEIGHT / 5, MyConstants.SCREENHEIGHT / 5, timer);
        myCorrectionAndPauseGeneral.addElements(validusAnimated);

        if (ecrin)
        {
            ecrinDiamantView = new EcrinDiamantView(stage, (MyConstants.SCREENWIDTH / 30) * (168f / 59f), maxNotePossible);
            ecrinDiamantView.updateText();
            allDrawables.add(ecrinDiamantView);
            myCorrectionAndPauseGeneral.addElements(ecrinDiamantView);
        }

        metrologue = new Metrologue(MyConstants.SCREENWIDTH / 60, 2 * MyConstants.SCREENHEIGHT / 5, MyConstants.SCREENHEIGHT / 5, MyConstants.SCREENHEIGHT / 5, timer);
        myCorrectionAndPauseGeneral.addElements(metrologue);

 /*
        reserveBilles = new ReserveBilles(MyConstants.SCREENWIDTH - 300, MyConstants.SCREENHeight - 300, 200, 200);
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


    public class FinOnglet extends MyTimer.TaskEtape
    {
        public FinOnglet(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            Music music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/fin_ong.ogg"));
            music.play();

            music.setOnCompletionListener(new Music.OnCompletionListener()
            {
                @Override
                public void onCompletion(Music music)
                {
                    music.dispose();
                }
            });
//            timer.cancel();
        }
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
//        Gdx.gl.glClearColor(1, 1, 1, 1); // center
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for (int i = 0; i < allDrawables.size(); i++)
        {
            MyDrawInterface newItem = allDrawables.get(i);
            if (newItem.isVisible())
            {
                newItem.myDraw(batch);
            }
        }

        if (validusAnimated.isVisible())
        {
            validusAnimated.myDraw(batch);
        }

        if (metrologue.isVisible())
        {
            metrologue.myDraw(batch);
        }
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
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;
        if (sacDeBilles != null)
        {
            if (sacDeBilles.contains(screenX, reversedScreenY) && sacDeBilles.isActive()) /*si bille part de la reserve*/
            {
                System.out.println("clickedOnReserve");
                UneBille billeAdded = new UneBille(sacDeBilles.currentPositionX + sacDeBilles.animationWidth / 2, sacDeBilles.currentPositionY + sacDeBilles.animationHeight / 2, sacDeBilles.largeurBille);
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
        }
        return true;

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        if (objectTouched != null)
        {
            objectTouched.setPosition((int) (screenX - objectTouched.getWidth() / 2), (int) (MyConstants.SCREENHEIGHT - screenY - objectTouched.getHeight() / 2));
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
                billeAux.touchUp(allPlanches/*, screenX, MyConstants.SCREENHeight - screenY*/);
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
