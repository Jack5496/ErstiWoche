package com.erstiwoche.Inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.erstiwoche.Main;
import com.erstiwoche.menu.MenuHandler;

public class InputHandler implements InputProcessor {

	public GestureHandler gestureHandler;

	public InputHandler() {
		gestureHandler = new GestureHandler(this); // Touch class

		// After initilize set Input as this
		Gdx.input.setInputProcessor(this); // last
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		int maxY = Main.getInstance().getHeight();
		screenY = maxY - screenY; // Maus wird invertiert angegeben -.-
		
		if (button == Input.Buttons.LEFT) {
			MenuHandler.clicked(screenX, screenY);
		}
		if (button == Input.Buttons.RIGHT) {
			Main.log(getClass(), "Mouse Right");
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int maxY = Main.getInstance().getHeight();
		screenY = maxY - screenY; // Maus wird invertiert angegeben -.-

		MenuHandler.mouseMoved(screenX, screenY);

		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

}
