package com.evalutel.primval_desktop.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.evalutel.primval_desktop.GdxSplashScreenGame;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        //config.width = 2048;
        //config.height = 1536;

        config.useHDPI = true;

//		config.resizable = false;

//        https://stackoverflow.com/questions/23588219/lib	gdx-find-out-if-a-given-screen-resolution-is-supported-on-the-current-devic

//		http://www.java-gaming.org/topics/libgdx-desktoplauncher-set-resolution-to-desktopdisplaymode/35948/view.html


        config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
        //If I want to test windowed
        /*if (!fullscreen)
        {
            config.fullscreen = true;
            config.width /= 1.2f;
            config.height /= 1.2f;
        }*/

        //config.fullscreen = true;

        float screenHeight = config.height;
        float screenWidth = config.width;

        screenHeight /= 1.1f;
        screenWidth /= 1.1f;


        System.out.print("screenheight, screenWidth" + screenHeight + "/" + screenWidth);

//        float ratioTest = 1024f / 768f;

//        float currentRatio =  screenWidth / screenHeight;

        /*
        if (currentRatio > ratioTest)
        {
            screenWidth = (int) (screenHeight * 1024.0f / 768.0f);
            config.width = screenWidth;
            config.height = screenHeight;
        }
*/

        config.width = (int) screenWidth;
        config.height = (int) screenHeight;

        config.forceExit = true;

        config.fullscreen = false;

        config.resizable = false;
        config.samples = 3;
        config.vSyncEnabled = true;

//		config.fullscreen = true;

        new LwjglApplication(new GdxSplashScreenGame(), config);
        Gdx.graphics.setTitle("Primval");



    }
}
