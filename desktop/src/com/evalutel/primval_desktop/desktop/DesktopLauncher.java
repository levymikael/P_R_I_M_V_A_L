package com.evalutel.primval_desktop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.evalutel.primval_desktop.LaunchGame;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;


import org.lwjgl.util.glu.Util;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 2048;
		config.height = 1536;
		config.useHDPI = true;


		//config.fullscreen = true;

		new LwjglApplication(new LaunchGame(), config);



	}
}
