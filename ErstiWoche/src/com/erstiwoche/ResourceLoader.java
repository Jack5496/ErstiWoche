package com.erstiwoche;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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

	public static String fasictCard = "gesetztFaschist";
	public static String liberalCard = "gesetztLiberal";

	public void addToLoad(String name) {
		assets.load(name, Texture.class);
	}

	private void loadAsset(String name) {
		addToLoad(name);
		float progress = 0;
		Main.log(getClass(), "Loading: " + Gdx.files.internal(name).path());
		while (!assets.update()) {
			if (progress != assets.getProgress()) {
				progress = assets.getProgress();
				Main.log(getClass(), "Loaded: " + assets.getProgress() * 100 + "%");
			}
		}
		Main.log(getClass(), "Loaded");
	}

	public Texture getTexture(String name) {
		if (!assets.isLoaded(name)) {
			loadAsset(name);
		}
		return assets.get(name, Texture.class);
	}

	public Texture getButton(String name) {
		// Texture texture = getTexture(buttons + name + ".png");
		
		
		if (!textures.containsKey(name)) {
			Main.log(getClass(), name);
			Texture texture = new Texture(Gdx.files.internal(buttons + name + ".png"));
			textures.put(name, texture);
		}

		return textures.get(name);

		//Böse jedes mal eine neue Texture zu erstellen
//		return new Texture(Gdx.files.internal(buttons + name + ".png"));	
	}

}
