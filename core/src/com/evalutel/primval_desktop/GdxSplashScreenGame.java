package com.evalutel.primval_desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Sommaire.Screen_Sommaire_General;

public class GdxSplashScreenGame extends Game implements ApplicationListener
{

    private static long SPLASH_MINIMUM_MILLIS = 2000L;

    public GdxSplashScreenGame()
    {
        super();
    }

    @Override
    public void create()
    {

        Gdx.graphics.setTitle("Primval");

        setScreen(new SplashScreen());

        Timer.schedule(
                new Timer.Task()
                {
                    @Override
                    public void run()
                    {
                        GdxSplashScreenGame.this.setScreen(new Screen_Sommaire_General(GdxSplashScreenGame.this, new DatabaseDesktop()));
                    }
                }, 2.0f);

        final long splash_start_time = System.currentTimeMillis();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                Gdx.app.postRunnable(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        // Se muestra el menu principal tras la SpashScreen
                        long splash_elapsed_time = System.currentTimeMillis() - splash_start_time;
                        if (splash_elapsed_time < GdxSplashScreenGame.SPLASH_MINIMUM_MILLIS)
                        {
                            Timer.schedule(
                                    new Timer.Task()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            GdxSplashScreenGame.this.setScreen(new Screen_Sommaire_General(GdxSplashScreenGame.this, new DatabaseDesktop()));
                                        }
                                    }, (float) (GdxSplashScreenGame.SPLASH_MINIMUM_MILLIS - splash_elapsed_time) / 1000f);
                        }
                        else
                        {
                            GdxSplashScreenGame.this.setScreen(new Screen_Sommaire_General(GdxSplashScreenGame.this, new DatabaseDesktop()));
                        }
                    }
                });
            }
        }).start();
    }


    @Override
    public void dispose()
    {
        // DISPOSE ALL RESOURCES
        getScreen().dispose();
        Gdx.app.exit();
    }
}
