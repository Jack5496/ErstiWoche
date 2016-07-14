package com.erstiwoche;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ResourceLoader {

	public AssetManager assets;

	public static ResourceLoader instance;

	public static HashMap<String, Texture> textures;

	public ResourceLoader() {
		instance = this;
		assets = new AssetManager();
		Texture.setEnforcePotImages(false); // Ignore Power of 2 Limitations
		textures = new HashMap<String, Texture>();
	}

	public static ResourceLoader getInstance() {
		return instance;
	}

	public static String data = "data/";

	public static String buttons = data + "buttons/";

	public Texture getButton(String name) {
		if (!textures.containsKey(name)) {
			Main.log(getClass(), name);
			Texture texture = new Texture(Gdx.files.internal(buttons + name + ".png"));
			textures.put(name, texture);
		}

		return textures.get(name);
	}

}
