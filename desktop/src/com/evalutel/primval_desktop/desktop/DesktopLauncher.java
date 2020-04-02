package com.evalutel.primval_desktop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.evalutel.primval_desktop.LaunchGame;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;


import org.lwjgl.util.glu.Util;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = 2048;
        config.height = 1536;
        config.useHDPI = true;

//		config.resizable = false;

//        https://stackoverflow.com/questions/23588219/lib	gdx-find-out-if-a-given-screen-resolution-is-supported-on-the-current-devic

//		http://www.java-gaming.org/topics/libgdx-desktoplauncher-set-resolution-to-desktopdisplaymode/35948/view.html


        config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
        //If I want to test windowed
        boolean fullscreen = false;
        if (!fullscreen)
        {
            config.fullscreen = false;
            config.width /= 1.2f;
            config.height /= 1.2f;
        }
        config.resizable = false;
        config.samples = 4;
        config.vSyncEnabled = true;
//		config.fullscreen = true;

        new LwjglApplication(new LaunchGame(), config);


    }
}
