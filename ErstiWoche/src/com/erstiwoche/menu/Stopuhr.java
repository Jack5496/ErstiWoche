package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayer;
import com.erstiwoche.helper.Message;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.multiplayer.Notifications;
import com.erstiwoche.uiElements.GUIButton;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

public class Stopuhr implements MenuInterface {

	List<GUIButton> buttons;
	GUIButton back = new GUIButton("Back", "exit", 0, 0, 0, 0);
	GUIButton activButton;

	public GUIButton resetAll = new GUIButton("Reset All", "stopuhrResetAll", 0, 0, 0, 0);

	public static String propStopped = "Stopped";
	public static String propStart = "Start";
	public static String propRunTime = "Run";

	public static String uhr1Name = "Uhr1";
	public static GUIButton uhr1 = new GUIButton(uhr1Name, "stopuhrResetAll", 0, 0, 0, 0);
	public GUIButton uhr1Reset = new GUIButton("Reset " + uhr1Name, "stopuhrReset", 0, 0, 0, 0);
	static boolean uhr1stopped = true;
	public static long uhr1StartTime = 0;
	public static int uhr1runTime = 0;

	public static String uhr2Name = "Uhr2";
	public static GUIButton uhr2 = new GUIButton(uhr2Name, "stopuhrResetAll", 0, 0, 0, 0);
	public GUIButton uhr2Reset = new GUIButton("Reset " + uhr2Name, "stopuhrReset", 0, 0, 0, 0);
	static boolean uhr2stopped = true;
	public static long uhr2StartTime = 0;
	public static int uhr2runTime = 0;

	public Room room;

	public Stopuhr(Room room) {
		this.room = room;

		buttons = new ArrayList<GUIButton>();

		buttons.add(uhr1Reset);
		buttons.add(uhr1);

		buttons.add(resetAll);

		buttons.add(uhr2Reset);
		buttons.add(uhr2);

		buttons.add(back);
		buttons = MenuHandler.setButtonPositions(buttons);
	}

	@Override
	public void render(SpriteBatch batch) {
		calcUhrLabel();
		for (GUIButton button : buttons) {
			button.render(batch);
		}
	}

	public void enter() {
		if (activButton != null) {
			if (activButton == back) {
				MenuHandler.setActivMenu(room);
			}
			if (activButton == resetAll) {
				resetClock(uhr1Name);
				resetClock(uhr2Name);
			}
			if (activButton == uhr1Reset) {
				resetClock(uhr1Name);
			}
			if (activButton == uhr2Reset) {
				resetClock(uhr2Name);
			}
			if (activButton == uhr1) {
				if (uhr1stopped) {
					unpauseClock1();
				} else {
					pauseClock(uhr1Name, uhr1runTime);
				}
			}
			if (activButton == uhr2) {
				if (uhr2stopped) {
					unpauseClock2();
				} else {
					pauseClock(uhr2Name, uhr2runTime);
				}
			}
		}
	}

	private void calcUhrLabel() {
		calcUhr1Label();
		calcUhr2Label();
	}

	private void calcUhr1Label() {
		float secondsUhr1 = 0;

		if (uhr1stopped) {
			secondsUhr1 = ((float) (uhr1runTime / 10) / 100);
		} else {
			long time = System.currentTimeMillis() - uhr1StartTime;
			uhr1runTime = (int) time;
			secondsUhr1 = ((float) (uhr1runTime / 10) / 100);
		}

		int minutesUhr1 = (int) (secondsUhr1 / 60);
		String seconds = String.format("%.02f", secondsUhr1%60);

		uhr1.label = minutesUhr1 + "m " + seconds+" s";
	}

	private void calcUhr2Label() {
		float secondsUhr1 = 0;

		if (uhr2stopped) {
			secondsUhr1 = ((float) (uhr2runTime / 10) / 100);
		} else {
			long time = System.currentTimeMillis() - uhr2StartTime;
			uhr2runTime = (int) time;
			secondsUhr1 = ((float) (uhr2runTime / 10) / 100);
		}

		int minutesUhr1 = (int) (secondsUhr1 / 60);
		String seconds = String.format("%.02f", secondsUhr1%60);

		uhr2.label = minutesUhr1 + "m " + seconds+" s";
	}

	private void resetClock(String clockName) {
		Multiplayer.updateProp(clockName + propStart, System.currentTimeMillis());
		Multiplayer.updateProp(clockName + propRunTime, 0);
	}

	private void pauseClock(String clockName, long runTime) {
		Multiplayer.updateProp(clockName + propStopped, "true");
		Multiplayer.updateProp(clockName + propRunTime, runTime);

		updateClockTextures();
	}

	private void unpauseClock1() {
		Multiplayer.updateProp(uhr1Name + propStart, System.currentTimeMillis() - uhr1runTime);
		Multiplayer.updateProp(uhr1Name + propStopped, "false");

		updateClockTextures();
	}

	private void unpauseClock2() {
		Multiplayer.updateProp(uhr2Name + propStart, System.currentTimeMillis() - uhr2runTime);
		Multiplayer.updateProp(uhr2Name + propStopped, "false");

		updateClockTextures();
	}

	public static void updateClocks(HashMap<String, Object> props) {
		uhr1stopped = ("" + props.get(uhr1Name + propStopped)).equals("true");
		uhr2stopped = ("" + props.get(uhr2Name + propStopped)).equals("true");

		if (props.get(uhr1Name + propStart) == null) {
			Multiplayer.updateProp(uhr1Name + propStart, System.currentTimeMillis());
		} else {
			uhr1StartTime = (long) props.get(uhr1Name + propStart);
		}

		if (props.get(uhr2Name + propStart) == null) {
			Multiplayer.updateProp(uhr2Name + propStart, System.currentTimeMillis());
		} else {
			uhr2StartTime = (long) props.get(uhr2Name + propStart);
		}

		if (props.get(uhr1Name + propRunTime) == null) {
			Multiplayer.updateProp(uhr1Name + propRunTime, 0L);
		} else {
			uhr1runTime = (int) props.get(uhr1Name + propRunTime);
		}

		if (props.get(uhr2Name + propRunTime) == null) {
			Multiplayer.updateProp(uhr2Name + propRunTime, 0L);
		} else {
			uhr2runTime = (int) props.get(uhr2Name + propRunTime);
		}
		
		updateClockTextures();
	}

	private static void updateClockTextures() {
		if (uhr1stopped) {
			uhr1.texture = "stopuhrPause";
		} else {
			uhr1.texture = "stopuhrActiv";
		}

		if (uhr2stopped) {
			uhr2.texture = "stopuhrPause";
		} else {
			uhr2.texture = "stopuhrActiv";
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