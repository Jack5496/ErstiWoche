package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.uiElements.GUIButton;

public class TeamView implements MenuInterface {

	static List<GUIButton> buttons;
	static GUIButton back = new GUIButton("Back", "exit", 0, 0, 0, 0);
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
				GUIButton b = new GUIButton(station + "\nPunkte: " + hashMap.get(key), "station", 0, 0, 0, 0);
				buttons.add(b);
				stationen.put(b, key);
			}
		}

		buttons.add(back);

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
			if (stationen.containsKey(activButton)) {
				Gdx.input.getTextInput(new InputPoints(stationen.get(activButton)), "Trage Punkte Ein", "");
			}
		}
	}

	public static class InputPoints implements TextInputListener {

		public InputPoints(String stationKey) {
			this.stationKey = stationKey;
		}

		String stationKey;

		@Override
		public void input(String text) {
			int punkte = 0;
			try{
				punkte = Integer.parseInt(text);
			}
			catch(NumberFormatException e){
				punkte = 0;
			}
			
			Multiplayer.updateProp(Multiplayer.teamViewID, stationKey, ""+punkte);
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