package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.Team;
import com.erstiwoche.helper.Auth;
import com.erstiwoche.helper.Umlaute;
import com.erstiwoche.menu.TeamView.InputPoints;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.uiElements.GUIButton;

public class ListeTeamsAuf implements MenuInterface {

	public static final String TEAMLISTTAG = "TeamListe";
	public static List<String> teams;

	static List<GUIButton> buttons;
	static GUIButton back = new GUIButton("Back", "exit", 0, 0, 0, 0);
	GUIButton activButton;

	public static GUIButton addTeam = new GUIButton("Create Team", "createTeam", 0, 0, 0, 0);

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

	public static HashMap<GUIButton, Team> teamButtons = new HashMap<GUIButton, Team>();
	public static List<Team> teamEntitys = new ArrayList<Team>();
	
	public static void updateButtons() {
		buttons = new ArrayList<GUIButton>();
		teamButtons = new HashMap<GUIButton, Team>();
		teamEntitys = new ArrayList<Team>();

		String zusatzInfo = "";
		if (Multiplayer.activRoom != null) {
			zusatzInfo = "\n";
		} else if (Auth.isPlayerAdmin()) {
			zusatzInfo = "\nTotal: ";
		} else{
			zusatzInfo = "\nTotal: ";
		}
		
		for (String team : teams) {

			String teamName = Umlaute.rekonstruiere(team);
			int points = 0;
			

			GUIButton b = new GUIButton(teamName, "teamError", 0, 0, 0, 0);
			if (Multiplayer.activRoom != null) {
				points = getPoints(team);
				b = new GUIButton(teamName, "teamSetPoints", 0, 0, 0, 0);
			} else if (Auth.isPlayerAdmin()) {
				points = getTotalTeamPoints(team);
				b = new GUIButton(teamName, "teamView", 0, 0, 0, 0);
			} else{
				points = getTotalTeamPoints(team);
				b = new GUIButton(teamName, "team", 0, 0, 0, 0);
			}
			Team t = new Team(team,teamName,points,b);
			teamEntitys.add(t);
			
			teamButtons.put(b, t);
		}
		
		Collections.sort(teamEntitys);
		
		int platz = 1;
		for(Team t : teamEntitys){
			t.b.label = "Nr."+platz+"\n"+t.b.label+zusatzInfo+t.points;
			buttons.add(t.b);
			platz++;
		}		

		buttons.add(back);

		if (Multiplayer.activRoom == null) {
			if (Auth.isPlayerAdmin()) {
				buttons.add(addTeam);
			}
		}

		buttons = MenuHandler.setButtonPositions(buttons);
	}

	public static int getPoints(String teamName) {
		HashMap<String, Object> hashMap = ListeTeamsAuf.props;
		int points = 0;
		for (String key : hashMap.keySet()) {
			if (key.contains(teamName) && key.contains(Multiplayer.activRoom.name)) {
				int statPoint = Integer.parseInt((String) hashMap.get(key));
				points += statPoint;
			}
		}
		return points;
	}

	public static int getTotalTeamPoints(String teamName) {
		HashMap<String, Object> hashMap = ListeTeamsAuf.props;
		int points = 0;
		for (String key : hashMap.keySet()) {
			if (key.contains(teamName)) {
				int statPoint = Integer.parseInt((String) hashMap.get(key));
				points += statPoint;
			}
		}
		return points;
	}

	@Override
	public void renderCall() {
		MenuHandler.renderButtons(this,buttons);
	}

	public void enter() {
		if (activButton != null) {
			if (activButton == back) {
				if (Multiplayer.activRoom != null) {
					MenuHandler.setActivMenu(Multiplayer.activRoom,false);
				} else {
					MenuHandler.setActivMenu(new MainMenu(),false);
				}
			} else if (activButton == addTeam) {
				Gdx.input.getTextInput(new CreateTeam(), "Enter Team Name", "");
			} else {
				if (Multiplayer.activRoom != null) {
					Gdx.input.getTextInput(new InputPoints(teamButtons.get(activButton) + Multiplayer.activRoom.name),
							"Trage Punkte Ein", "");
				} else if (Auth.isPlayerAdmin()) {
					MenuHandler.setActivMenu(new TeamView(teamButtons.get(activButton).name),true);
				}
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

	public static class CreateTeam implements TextInputListener {

		@Override
		public void input(String text) {
			text = Umlaute.macheSafe(text);

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