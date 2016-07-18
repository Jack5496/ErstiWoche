package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.List;
import com.erstiwoche.uiElements.GUIButton;

public class LoginWaitMenu implements MenuInterface {

	static List<GUIButton> buttons;
	GUIButton activButton;

	static GUIButton wait = new GUIButton("", "loading", 50, 50, 50, 50).setOnHoverBigger(true);
	static GUIButton error = new GUIButton("Please Wait", "test", 50, 50, 50, 50).setOnHoverBigger(true);

	float timeForCircle = 4;
	
	public LoginWaitMenu() {
		buttons = new ArrayList<GUIButton>();
		buttons.add(wait);
		buttons = MenuHandler.setButtonPositions(buttons);
	}

	@Override
	public void renderCall() {
		for (GUIButton button : buttons) {
			button.setDegree(getDegreeForTime());
		}
		MenuHandler.renderButtons(this,buttons);
	}

	public float getDegreeForTime() {
		long time = System.currentTimeMillis();
		float mult = (time%60000)/1000f%timeForCircle/timeForCircle;
//		float percentOfSecond = time%(1000);
//		percentOfSecond/=1000;
		return -mult*360;
	}

	public GUIButton getActivButton() {
		return activButton;
	}

	public static void error(String errorMessage) {
		buttons.remove(wait);
		error = new GUIButton(errorMessage + "\nReload", "test", 50, 50, 50, 50).setOnHoverBigger(true);
		buttons.add(error);
		buttons = MenuHandler.setButtonPositions(buttons);
	}

	public void enter() {
		if (activButton != null) {
			if (activButton == error) {
				MenuHandler.setActivMenu(new LoginWaitMenu(),true);
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