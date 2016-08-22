package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayerHandler;
import com.erstiwoche.helper.Auth;
import com.erstiwoche.helper.Umlaute;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.multiplayer.Notifications;
import com.erstiwoche.uiElements.GUIButton;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;

public class MainMenu implements MenuInterface {

	GUIButton activButton;
	static GUIButton adminButton;
	static GUIButton teamViewButton;
	static GUIButton logout;
	static GUIButton broadcast;
	static GUIButton rateApp;

	static HashMap<String, GUIButton> roomButtons;
	public static HashMap<String, Room> rooms;

	public MainMenu() {
		roomButtons = new HashMap<String, GUIButton>();
		rooms = new HashMap<String, Room>();

		Multiplayer.getAllRooms();
	}

	public static void allRoomsRecieved(List<String> roomIDs) {
		roomButtons = new HashMap<String, GUIButton>();

		roomIDs.remove(Multiplayer.teamViewID);

		int buttonAmount = roomIDs.size() + 3;
		if (Auth.isPlayerAdmin()) {
			buttonAmount += 1;
		}

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
				if (Auth.isPlayerAdmin()) {
					Multiplayer.subcribeRoom(roomIDs.get(i));
				}

			}
			if (Auth.isPlayerAdmin()) {
				if (buttonAmount - 4 == i) {
					broadcast = new GUIButton("Broadcast", "broadcast", xpos, ypos, width, height)
							.setOnHoverBigger(true);
					roomButtons.put(broadcast.label, broadcast);
				}
			}
			if (buttonAmount - 3 == i) {
				rateApp = new GUIButton("Like it?", "like", xpos, ypos, width, height).setOnHoverBigger(true);
				roomButtons.put(rateApp.label, rateApp);
			}
			if (buttonAmount - 2 == i) {
				teamViewButton = new GUIButton("Team View", "listTeams", xpos, ypos, width, height)
						.setOnHoverBigger(true);
				roomButtons.put(Multiplayer.teamViewID, teamViewButton);
			}
			if (buttonAmount - 1 == i) {
				if (Auth.isPlayerAdmin()) {
					logout = new GUIButton("Log Out", "logout", xpos, ypos, width, height).setOnHoverBigger(true);
					roomButtons.put(logout.label, logout);
				} else {
					adminButton = new GUIButton("Admin Menu", "key", xpos, ypos, width, height).setOnHoverBigger(true);
					roomButtons.put(adminButton.label, adminButton);
				}
			}

		}
	}

	public static void updateRoomInformation(LiveRoomInfoEvent arg0) {
		if (arg0.getData() != null) {
			Room room = rooms.get(arg0.getData().getId());
			if (room == null) {
				rooms.put(arg0.getData().getId(), new Room(arg0.getData()));
				room = rooms.get(arg0.getData().getId());
			}

			if (arg0.getJoinedUsers() != null) {
				room.setJoinedUsers(arg0.getJoinedUsers());
			}
			HashMap<String, Object> prop = arg0.getProperties();
			if (prop != null) {
				room.updateProperties(prop);

			}

			String roomID = arg0.getData().getId();
			String roomName = arg0.getData().getName();

			if (!roomID.equals(Multiplayer.teamViewID)) {
				GUIButton button = roomButtons.get(roomID);
				if (button != null) {
					Main.log(MainMenu.class, "Update: " + room.getRoomStatus());
					button.label = Umlaute.rekonstruiere(roomName);
					button.texture = room.getRoomStatus();
					room.roomStatus.texture = button.texture;
				}
			}
		}
	}

	@Override
	public void renderCall() {
		List<String> walkThrough = new ArrayList<String>(roomButtons.keySet());
		List<GUIButton> toRender = new ArrayList<GUIButton>();

		for (String id : walkThrough) {
			GUIButton button = roomButtons.get(id);

			if (LocalPlayerHandler.playerLoggedIn()) {
				if (Auth.isPlayerAdmin()) {
					if (Notifications.changed.containsKey(id)) {
						button.setChange(true);
					}
				}
			}

			toRender.add(button);
		}

		MenuHandler.renderButtons(this, toRender);

		for (GUIButton button : toRender) {
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
			} else if (activButton == logout) {
				Multiplayer.goOffline();
				Main.getInstance().goOnlineRandomName();
			} else if (activButton == rateApp) {
				Main.extensions.openURL(Main.appURL);
			} else if (activButton == broadcast) {
				Gdx.input.getTextInput(new BroadCastInput(), "Broadcast Message", "");
			} else {
				for (String roomID : roomButtons.keySet()) {
					if (activButton == roomButtons.get(roomID)) {
						if (roomID.equals(Multiplayer.teamViewID)) {
							MenuHandler.setActivMenu(new ListeTeamsAuf(), true);
						} else {
							Multiplayer.joinRoom(roomID);
						}
					}
				}
			}
		}
	}

	public class BroadCastInput implements TextInputListener {

		@Override
		public void input(String text) {
			for (String roomID : MainMenu.roomButtons.keySet()) {
				Multiplayer.updateProp(roomID, ChatRoom.ANSWERTAG, ChatRoom.ANSWERTAG + ": " + text);
			}
//			Multiplayer.sendMessage(Notifications.SYSTEM + Notifications.REGEX + Notifications.UPDATE
//					+ Notifications.REGEX + Notifications.CHATUPDATE);
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
		for (GUIButton button : roomButtons.values()) {
			button.pressAt(x, y);
			if (button.isPressed()) {
				activButton = button;
				activButton.setHovered(true);
			}
		}
	}

}