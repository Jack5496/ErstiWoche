package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.uiElements.GUIButton;

public class ListeTeamsAuf implements MenuInterface {

	public static final String TEAMLISTTAG = "TeamListe";
	public static List<String> teams;

	static List<GUIButton> buttons;
	static GUIButton back = new GUIButton("Back", "test", 0, 0, 0, 0);
	GUIButton activButton;

	public static GUIButton addTeam = new GUIButton("Create Team", "test", 0, 0, 0, 0);
	public static GUIButton deleteTeam = new GUIButton("Delete Team", "test", 0, 0, 0, 0);

	public ListeTeamsAuf() {
		buttons = new ArrayList<GUIButton>();
		teams = new ArrayList<String>();

		updateButtons();
		Multiplayer.updateRoomInformations(Multiplayer.teamViewID);
	}
	
	public static HashMap<String, Object> props;

	public static void recieveTeamListProperties(HashMap<String, Object> hashMap) {
		teams = new ArrayList<String>();
		props = hashMap;
		JSONArray teamsJSON = (JSONArray) props.get(TEAMLISTTAG);
		Main.log(ListeTeamsAuf.class, "Erhalte Teams");
		if (teamsJSON != null) {
			for (int i = 0; i < teamsJSON.length(); i++) {
				try {
					teams.add(teamsJSON.get(i).toString());

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		updateButtons();
		TeamView.updateButtons();
	}
	
	public static HashMap<GUIButton,String> teamButtons = new HashMap<GUIButton, String>();

	public static void updateButtons() {
		buttons = new ArrayList<GUIButton>();
		teamButtons = new HashMap<GUIButton, String>();

		for (String team : teams) {
			GUIButton b = new GUIButton(team+"\nTotal: "+getTotalTeamPoints(team), "test", 0, 0, 0, 0);
			teamButtons.put(b, team);
			buttons.add(b);
		}

		buttons.add(back);

		if (AdminMenu.isPlayerAdmin()) {
			buttons.add(addTeam);
			buttons.add(deleteTeam);
		}
		buttons = MenuHandler.setButtonPositions(buttons);
	}
	
	public static int getTotalTeamPoints(String teamName) {
		HashMap<String, Object> hashMap = ListeTeamsAuf.props;
		int points = 0;
		for (String key : hashMap.keySet()) {
			if (key.contains(teamName)) {
				int statPoint = Integer.parseInt((String) hashMap.get(key));
				points+=statPoint;
			}
		}
		return points;
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
				if (Multiplayer.activRoom != null) {
					MenuHandler.setActivMenu(Multiplayer.activRoom);
				} else {
					MenuHandler.setActivMenu(new MainMenu());
				}
			}
			else if (activButton == addTeam) {
				Gdx.input.getTextInput(new CreateTeam(), "Enter Team Name", "");
			}
			else if (activButton == deleteTeam) {
				Gdx.input.getTextInput(new DeleteTeam(), "Enter Team Name", "");
			}
			else{
				MenuHandler.setActivMenu(new TeamView(teamButtons.get(activButton)));
			}
			
		}
	}
	
	public static void deleteTeamEntrys(String team) {
		HashMap<String, Object> hashMap = ListeTeamsAuf.props;

		Multiplayer.updateProp(Multiplayer.teamViewID, ListeTeamsAuf.TEAMLISTTAG, teams);
		
		List<String> keysToDelete = new ArrayList<String>();
		for (String key : hashMap.keySet()) {
			if (key.contains(team)) {
				keysToDelete.add(key);
			}
		}
		Multiplayer.deleteProp(Multiplayer.teamViewID, keysToDelete);
	}
	
	public static class DeleteTeam implements TextInputListener {

		@Override
		public void input(String text) {
			teams.remove(text);
			deleteTeamEntrys(text);
		}

		@Override
		public void canceled() {

		}
	}

	public static class CreateTeam implements TextInputListener {

		@Override
		public void input(String text) {
			teams.remove(text);
			teams.add(text);
			Multiplayer.updateProp(Multiplayer.teamViewID, ListeTeamsAuf.TEAMLISTTAG, teams);
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