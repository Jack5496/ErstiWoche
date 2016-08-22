package com.uos.erstiwoche;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.erstiwoche.Main;
import com.erstiwoche.helper.Extensions;

public class MainActivity extends AndroidApplication implements Extensions {
	
	public static Music sound;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;

		initialize(new Main(this), cfg);
	}

	public void openURL(String url) {
		Uri marketUri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, marketUri);
		this.startActivity(intent);
	}

	public static String soundFolder = "data/sounds/";
	
	@Override
	public void playSound(String soundName) {
		sound = Gdx.audio.newMusic(Gdx.files.internal(soundFolder+soundName+".mp3"));
		sound.play();
	}
}