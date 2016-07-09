package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayerHandler;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.uiElements.GUIButton;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

public class LoginWaitMenu implements MenuInterface {

	static List<GUIButton> buttons;
	GUIButton activButton;

	static GUIButton wait = new GUIButton("Please Wait", "test", 50, 50, 50, 50).setOnHoverBigger(true);
	static GUIButton error = new GUIButton("Please Wait", "test", 50, 50, 50, 50).setOnHoverBigger(true);

	public LoginWaitMenu() {
		buttons = new ArrayList<GUIButton>();
		buttons.add(wait);
	}

	@Override
	public void render(SpriteBatch batch) {
		for (GUIButton button : buttons) {
			button.render(batch);
		}

	}

	public GUIButton getActivButton() {
		return activButton;
	}

	public static void error(String errorMessage) {
		buttons.remove(wait);
		error = new GUIButton(errorMessage + "\nReload", "test", 50, 50, 50, 50).setOnHoverBigger(true);
		buttons.add(error);
	}

	public void enter() {
		if (activButton != null) {
			if (activButton == error) {
				MenuHandler.setActivMenu(new LoginWaitMenu());
			}
		}
	}

	@Override
	public void clicked(int x, int y) {
		mouseMoved(x, y);
		enter();
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (activButton != null) {
			activButton.setHovered(false);
		}
		activButton = null;
		for (GUIButton button : buttons) {
			button.pressAt(x, y);
			if (button.isPressed()) {
				activButton = button;
				activButton.setHovered(true);
			}
		}
	}

}