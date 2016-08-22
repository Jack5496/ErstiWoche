package com.uos.erstiwoche;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.openal.Mp3.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.erstiwoche.Main;
import com.erstiwoche.helper.Extensions;

import javazoom.jl.player.Player;

public class DesktopURLOpener implements Extensions {

	@Override
	public void openURL(String url) {
		// TODO Auto-generated method stub
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static Music sound;
	public static String soundFolder = "data/sounds/";

	@Override
	public void playSound(String soundName) {
		try {
			Main.log(getClass(), "Try Play Sound: " + soundName);

			FileHandle file = Gdx.files.internal(soundFolder + soundName + ".mp3");
			Main.log(getClass(), "FileHandle exsist?: " + file.exists());
			sound = Gdx.audio.newMusic(file);
			sound.play();

			File newTextFile = file.file();
			try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(newTextFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			} catch (Exception ex) {
				Main.log(getClass(), ex.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
