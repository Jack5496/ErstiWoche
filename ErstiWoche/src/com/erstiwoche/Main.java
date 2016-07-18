package com.erstiwoche;

import java.util.UUID;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Inputs.InputHandler;
import com.erstiwoche.entitys.LocalPlayerHandler;
import com.erstiwoche.menu.MenuHandler;
import com.erstiwoche.multiplayer.Multiplayer;

public class Main implements ApplicationListener {

	public static final String gameTitle = "Ersti Woche";

	SpriteBatch batch;
	BitmapFont font;
	Texture img;

	private static Main instance;
	private int width;
	private int height;

	public InputHandler inputHandler;
	public Multiplayer onlineConnector;
	public MenuHandler menuHandler;

	ResourceLoader loader;

	@Override
	public void create() {
		instance = this;
		loader = new ResourceLoader();

		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.BLACK);

		initInputHandler();

		menuHandler = new MenuHandler(batch);
		onlineConnector = new Multiplayer();
		goOnlineRandomName();
	}

	public void goOnlineRandomName() {
		LocalPlayerHandler.userNameWanted = UUID.randomUUID().toString().replace("-", "").substring(0, 7);
		Multiplayer.goOnline(LocalPlayerHandler.userNameWanted);
	}

	private void initInputHandler() {
		inputHandler = new InputHandler();
	}

	public static Main getInstance() {
		return instance;
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		MenuHandler.render();
	}

	public static void log(Class<?> c, String log) {
		System.out.println(c.getSimpleName() + ": " + log);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	boolean first = true;

	@Override
	public void resize(int width, int height) {
		Main.log(getClass(), "Screen resize: " + width + "x" + height);
		this.height = height;
		this.width = width;
		batch = new SpriteBatch();
		MenuHandler.batch = batch;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
