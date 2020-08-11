package com.evalutel.primval_desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;


public class MyButtonBackToPreviousMenu extends MyImageButton implements MyDrawInterface
{
    private boolean isActif;

    Game game;
    Screen previousScreen;


    public MyButtonBackToPreviousMenu(final Game game, Stage stage, final Metrologue metrologue, final ValidusAnimated validus, final Screen currentScreen, final long startTime, final UnResultat resultatExercice, final MyTimer timer)
    {
        super(stage, "Images/button_menu.png", MyConstants.SCREENWIDTH / 15f, MyConstants.SCREENWIDTH / 15f);
        setTouchable(Touchable.enabled);

        this.game = game;


        this.setPosition(MyConstants.SCREENWIDTH / 60f, 6 * MyConstants.SCREENHEIGHT / 7f);

//
//        addListener(new ClickListener()
//        {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
////                ScreenOnglet.this.game.dispose();
//                Gdx.app.log("button click", "click!");
//
//                long endTime = System.currentTimeMillis();
//                long seconds = (endTime - startTime) / 1_000L;
//
//                resultatExercice.setDuree(seconds);
//                resultatExercice.setDate(endTime);
//
//                if ((metrologue != null) && (metrologue.isSpeaking))
//                {
//                    metrologue.stopMusic();
//                }
//                else if ((validus.isSpeaking))
//                {
//                    validus.stopMusic();
//                }
//
//                timer.cancel();
//                AppSingleton appSingleton = AppSingleton.getInstance();
//                MyDataBase db = appSingleton.myDataBase;
//
//                db.insertResultat(resultatExercice);
//
////               game.setScreen(new previousScreen(game));
////
//                currentScreen.dispose();
//            }
//        });

    }


    public void getPreviousScren(Screen previousScreen)
    {
        this.previousScreen = previousScreen;
    }

    @Override
    public void myDraw(Batch batch)
    {
    }
}
