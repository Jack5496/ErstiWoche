package com.uos.erstiwoche;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.erstiwoche.Main;
import com.erstiwoche.helper.Extensions;

public class DesktopLauncher{	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.title = "ErstiWoche";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 320;

		DesktopURLOpener urlopener = new DesktopURLOpener();
		
		new LwjglApplication(new Main(urlopener), cfg);
	}

	
}
