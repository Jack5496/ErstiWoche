package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayerHandler;
import com.erstiwoche.helper.Umlaute;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.multiplayer.Notifications;
import com.erstiwoche.uiElements.GUIButton;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

public class MainMenu implements MenuInterface {

	GUIButton activButton;
	static GUIButton adminButton;
	static GUIButton teamViewButton;
	static GUIButton createNewStation;

	static HashMap<String, GUIButton> roomButtons;

	public MainMenu() {
		roomButtons = new HashMap<String, GUIButton>();

		Multiplayer.getAllRooms();
	}

	public static void allRoomsRecieved(List<String> roomIDs) {
		roomButtons = new HashMap<String, GUIButton>();

		roomIDs.remove(Multiplayer.teamViewID);

		int buttonAmount = roomIDs.size() + 2;

		int rowAmount = (int) Math.sqrt(buttonAmount) + 1;
		if (rowAmount == Math.sqrt(buttonAmount) + 1) {
			rowAmount--;
		}

		for (int i = 0; i < buttonAmount; i++) {

			float width = 100f / rowAmount;
			float height = 100f / rowAmount;

			float xpos = i / rowAmount * width + width / 2;
			float ypos = i % rowAmount * height + height / 2;

			if (i < roomIDs.size()) {
				GUIButton playerButton = new GUIButton("Loading", "loading", xpos, ypos, width, height)
						.setOnHoverBigger(true);
				roomButtons.put(roomIDs.get(i), playerButton);
				Multiplayer.updateRoomInformations(roomIDs.get(i));
				if (AdminMenu.isPlayerAdmin()) {
					Multiplayer.subcribeRoom(roomIDs.get(i));
				}

			}
			if (buttonAmount - 2 == i) {
				teamViewButton = new GUIButton("Team View", "listTeams", xpos, ypos, width, height)
						.setOnHoverBigger(true);
				roomButtons.put(Multiplayer.teamViewID, teamViewButton);
			}
			if (buttonAmount - 1 == i) {
				if (AdminMenu.isPlayerAdmin()) {
					createNewStation = new GUIButton("New Station", "newStation", xpos, ypos, width, height)
							.setOnHoverBigger(true);
					roomButtons.put(createNewStation.label, createNewStation);
				} else {
					adminButton = new GUIButton("Admin Menu", "key", xpos, ypos, width, height).setOnHoverBigger(true);
					roomButtons.put(adminButton.label, adminButton);
				}
			}

		}
	}

	public static void roomNameRecieved(String id, String name) {
		if (!id.equals(Multiplayer.teamViewID)) {
			GUIButton button = roomButtons.get(id);
			if (button != null) {
				button.label = Umlaute.rekonstruiere(name);								
				button.texture = "station";

			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		for (String id : new ArrayList<String>(roomButtons.keySet())) {
			GUIButton button = roomButtons.get(id);
			if (LocalPlayerHandler.playerLoggedIn()) {
				if (AdminMenu.isPlayerAdmin()) {
					if (Notifications.changed.containsKey(id)) {
						button.setChange(true);
					}
				}
			}
			button.render(batch);
			button.setChange(false);
		}
	}

	public GUIButton getActivButton() {
		return activButton;
	}

	public void enter() {
		if (activButton != null) {
			if (activButton == adminButton) {
				Multiplayer.goOffline();
				LocalPlayerHandler.openPlayerNameInput();
			} else if (activButton == createNewStation) {
				
			} else {
				for (String roomID : roomButtons.keySet()) {
					if (activButton == roomButtons.get(roomID)) {
						if (roomID.equals(Multiplayer.teamViewID)) {
							MenuHandler.setActivMenu(new ListeTeamsAuf());
						} else {
							Multiplayer.joinRoom(roomID);
						}
					}
				}
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
		for (GUIButton button : roomButtons.values()) {
			button.pressAt(x, y);
			if (button.isPressed()) {
				activButton = button;
				activButton.setHovered(true);
			}
		}
	}

}