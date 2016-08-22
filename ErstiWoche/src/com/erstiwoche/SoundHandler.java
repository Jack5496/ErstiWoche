package com.erstiwoche;

public class SoundHandler {

	public static void playSound(String soundName) {		
		 Main.extensions.playSound(soundName);
	}

	public static void playUpdateSound() {
		playSound("update");
	}

	public static void playMenuSwitchSound() {
		playSound("tick");
	}

}
