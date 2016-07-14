package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayer;
import com.erstiwoche.helper.Message;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.multiplayer.Notifications;
import com.erstiwoche.uiElements.GUIButton;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

public class Room implements MenuInterface {

	public String id;
	public String name;
	public int maxUser;
	public String ownerName;
	public HashMap<String, Object> porperties;

	List<GUIButton> buttons;
	GUIButton activButton;

	public List<LocalPlayer> players;

	GUIButton giveTeamPoints = new GUIButton("Give Team Points", "setPoints", 80, 80, 20, 20).setOnHoverBigger(true);
	GUIButton bier = new GUIButton(Bier.class.getSimpleName(), "bier", 80, 80, 20, 20).setOnHoverBigger(true);
	GUIButton chat = new GUIButton("Chat Room", "chat", 70, 15, 20, 20).setOnHoverBigger(true);
	
	GUIButton roomName;
	GUIButton back = new GUIButton("Leave", "exit", 30, 15, 20, 20).setOnHoverBigger(true);

	public Room(RoomData data) {
		buttons = new ArrayList<GUIButton>();
		players = new ArrayList<LocalPlayer>();
		porperties = new HashMap<String, Object>();

		roomInformationsFound(data);
		roomName = new GUIButton("Station:\n"+name, "station", 80, 80, 20, 20).setOnHoverBigger(true);
		Multiplayer.updateRoomInformations(id);

		buttons.add(chat);
		buttons.add(bier);
		buttons.add(giveTeamPoints);

		buttons.add(roomName);
		buttons.add(back);
		buttons = MenuHandler.setButtonPositions(buttons);
	}

	public int getPlayerAmount() {
		return players.size();
	}

	public void roomInformationsFound(RoomData data) {
		this.id = data.getId();
		this.name = data.getName();
		this.ownerName = data.getRoomOwner();
		this.maxUser = data.getMaxUsers();
	}

	@Override
	public void render(SpriteBatch batch) {
		if (Notifications.changed.get(id) != null) {
			if (Notifications.changed.get(id).contains(Notifications.BIERUPDATE)) {
				bier.setChange(true);
			}
			if (Notifications.changed.get(id).contains(Notifications.CHATUPDATE)) {
				chat.setChange(true);
			}
		}

		for (GUIButton button : buttons) {
			button.render(batch);
		}

		bier.setChange(false);
		chat.setChange(false);
	}

	public void setJoinedUsers(String[] joinedUsers) {
		players = new ArrayList<LocalPlayer>();
		for (String userName : joinedUsers) {
			playerJoined(new LocalPlayer(userName));
		}
	}

	public void playerJoined(LocalPlayer p) {
		players.add(p);
	}

	public void playerLeft(LocalPlayer p) {
		players.remove(p);
	}

	public GUIButton getActivButton() {
		return activButton;
	}

	public void enter() {
		if (activButton != null) {
			if (activButton == back) {
				// Main.log(getClass(), "Switching to Room Listning");
				if (Notifications.changed.get(id) != null) {
					Notifications.changed.remove(id);
				}
				Multiplayer.leaveRoom();
				MenuHandler.setActivMenu(new MainMenu());
			}
			if (activButton == chat) {
				MenuHandler.setActivMenu(new ChatRoom(this));
			}
			if (activButton == bier) {
				MenuHandler.setActivMenu(new Bier(this));
			}
			if (activButton == giveTeamPoints) {
				MenuHandler.setActivMenu(new ListeTeamsAuf());
			}

		}
	}

	public static HashMap<String, Object> props = new HashMap<String, Object>();

	public void updateProperties(String string, HashMap<String, Object> properties) {
		if (string.equals(Multiplayer.activRoom.id)) {
			props = properties;

			ChatRoom.answer.label = "" + props.get(ChatRoom.ANSWERTAG);
			ChatRoom.question.label = "" + props.get(ChatRoom.QUESTIONTAG);

			bier.label = Bier.class.getSimpleName() + "Status: \n" + props.get(Bier.class.getSimpleName());
		} else if (string.equals(Multiplayer.teamViewID)) {
			Main.log(getClass(), "New Properties found");
			Main.log(getClass(), properties.toString());
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