package com.erstiwoche;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundHandler {
	
	public static String soundFolder = "data/sounds/";
	public static Music sound;

	public static void playSound(String soundName){
		sound = Gdx.audio.newMusic(Gdx.files.internal(soundFolder+soundName+".mp3"));
		sound.play();
	}
	
	public static void playUpdateSound(){
		playSound("update");
	}
	
	public static void playMenuSwitchSound(){
		playSound("tick");
	}

}
