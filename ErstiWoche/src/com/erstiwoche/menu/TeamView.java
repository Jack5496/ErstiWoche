package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayer;
import com.erstiwoche.helper.Message;
import com.erstiwoche.menu.ListeTeamsAuf.CreateTeam;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.multiplayer.Notifications;
import com.erstiwoche.uiElements.GUIButton;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

public class TeamView implements MenuInterface {

	static List<GUIButton> buttons;
	static GUIButton back = new GUIButton("Back", "test", 0, 0, 0, 0);
	static GUIButton setPoints = new GUIButton("Setze Punkte", "test", 0, 0, 0, 0);
	GUIButton activButton;

	static String team = "";

	public TeamView(String teamName) {
		buttons = new ArrayList<GUIButton>();
		team = teamName;

		updateButtons();
	}

	static HashMap<GUIButton, String> stationen = new HashMap<GUIButton, String>();

	public static void updateButtons() {
		buttons = new ArrayList<GUIButton>();
		HashMap<String, Object> hashMap = ListeTeamsAuf.props;

		for (String key : hashMap.keySet()) {
			if (key.contains(team)) {
				String station = key.substring(team.length());
				GUIButton b = new GUIButton(station + "\nPunkte: " + hashMap.get(key), "test", 0, 0, 0, 0);
				buttons.add(b);
				stationen.put(b, key);
			}
		}

		buttons.add(back);
		if (Multiplayer.activRoom != null) {
			buttons.add(setPoints);
		}

		buttons = MenuHandler.setButtonPositions(buttons);
	}

	@Override
	public void render(SpriteBatch batch) {
		for (GUIButton button : buttons) {
			button.render(batch);
		}
	}

	public void enter() {
		if (activButton != null) {
			if (activButton == back) {
				MenuHandler.setActivMenu(new ListeTeamsAuf());
			}
			if (activButton == setPoints) {
				Gdx.input.getTextInput(new InputName(team + Multiplayer.activRoom.name), "Trage Punkte Ein", "");
			}
			if (stationen.containsKey(activButton)) {
				Gdx.input.getTextInput(new InputName(stationen.get(activButton)), "Trage Punkte Ein", "");
			}
		}
	}

	public static class InputName implements TextInputListener {

		public InputName(String stationKey) {
			this.stationKey = stationKey;
		}

		String stationKey;

		@Override
		public void input(String text) {
			Multiplayer.updateProp(Multiplayer.teamViewID, stationKey, text);
		}

		@Override
		public void canceled() {

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