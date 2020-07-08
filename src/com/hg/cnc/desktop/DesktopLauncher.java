package com.hg.cnc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hg.cnc.CandCgame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Captains and Commanders";
		config.width = 2400;
		config.height = 1200;
		new LwjglApplication(new CandCgame(), config);
	}
}
