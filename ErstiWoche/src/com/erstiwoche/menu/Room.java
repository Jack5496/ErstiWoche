package com.erstiwoche.menu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.erstiwoche.Main;
import com.erstiwoche.SoundHandler;
import com.erstiwoche.entitys.LocalPlayer;
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
	
	static HashMap<String,String> buttonLabelText = new HashMap<String,String>();
	static{
		buttonLabelText.put(Bier.zuWenig.label, Bier.zuWenig.texture);
		buttonLabelText.put(Bier.zuViel.label, Bier.zuViel.texture);
		buttonLabelText.put(Bier.genug.label, Bier.genug.texture);
		buttonLabelText.put(Bier.aufdemWeg.label, Bier.aufdemWeg.texture);
	}

	GUIButton giveTeamPoints = new GUIButton("Give Team Points", "setPoints", 0, 0, 0, 0).setOnHoverBigger(true);
	GUIButton bier = new GUIButton(Bier.class.getSimpleName(), "bier", 0, 0, 0, 0).setOnHoverBigger(true);
	GUIButton chat = new GUIButton("Chat Room", "chat", 0, 0, 0, 0).setOnHoverBigger(true);
	GUIButton stopuhr = new GUIButton("Stopuhr", "stopuhr", 0, 0, 0, 0).setOnHoverBigger(true);
	GUIButton teamer = new GUIButton("Teamer", "teamerGenug", 0, 0, 0, 0).setOnHoverBigger(true);

	public GUIButton roomStatus = new GUIButton("Station:\n" + name, getRoomStatus(), 0, 0, 0, 0)
			.setOnHoverBigger(true);
	GUIButton back = new GUIButton("Leave", "exit", 0, 0, 0, 0).setOnHoverBigger(true);

	public Room(RoomData data) {
		buttons = new ArrayList<GUIButton>();
		players = new ArrayList<LocalPlayer>();
		porperties = new HashMap<String, Object>();

		roomInformationsFound(data);
		Multiplayer.updateRoomInformations(id);

		buttons.add(chat);
		buttons.add(bier);
		buttons.add(giveTeamPoints);
		buttons.add(stopuhr);
		buttons.add(teamer);

		buttons.add(roomStatus);
		buttons.add(back);
		buttons = MenuHandler.setButtonPositions(buttons);
	}

	public int getPlayerAmount() {
		return players.size();
	}

	public void roomInformationsFound(RoomData data) {
		this.id = data.getId();
		this.name = data.getName();
		roomStatus.label = "Station:\n" + name;
		this.ownerName = data.getRoomOwner();
		this.maxUser = data.getMaxUsers();
	}

	@Override
	public void renderCall() {
		if (Notifications.changed.get(id) != null) {
			if (Notifications.changed.get(id).contains(Notifications.BIERUPDATE)) {
				bier.setChange(true);
			}
			if (Notifications.changed.get(id).contains(Notifications.CHATUPDATE)) {
				chat.setChange(true);
			}
		}

		MenuHandler.renderButtons(this, buttons);

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
				if (Notifications.changed.get(id) != null) {
					Notifications.changed.put(id, null);
				}
				Multiplayer.leaveRoom();
				MenuHandler.setActivMenu(new MainMenu(), false);
			}
			if (activButton == roomStatus) {
				MenuHandler.setActivMenu(new Status(this), true);
			}
			if (activButton == chat) {
				MenuHandler.setActivMenu(new ChatRoom(this), true);
			}
			if (activButton == bier) {
				MenuHandler.setActivMenu(new Bier(this), true);
			}
			if (activButton == teamer) {
				MenuHandler.setActivMenu(new Teamer(this), true);
			}
			if (activButton == stopuhr) {
				MenuHandler.setActivMenu(new Stopuhr(this), true);
			}
			if (activButton == giveTeamPoints) {
				MenuHandler.setActivMenu(new ListeTeamsAuf(), true);
			}

		}
	}

	public static HashMap<String, Object> props = new HashMap<String, Object>();

	public void updateProperties(HashMap<String, Object> properties) {
		
		if(ChatRoom.somethingCahnged(props, properties)){
			SoundHandler.playUpdateSound();
			Notifications.addChange(id, Notifications.CHATUPDATE);
		}
		
		if(Bier.somethingCahnged(props, properties)){
			SoundHandler.playUpdateSound();
			Notifications.addChange(id, Notifications.BIERUPDATE);
		}

		props = properties;
		
		ChatRoom.answer.label = "" + props.get(ChatRoom.ANSWERTAG);
		ChatRoom.question.label = "" + props.get(ChatRoom.QUESTIONTAG);

		bier.label = Bier.class.getSimpleName() + "Status: \n" + props.get(Bier.class.getSimpleName());
		bier.texture = buttonLabelText.get(props.get(Bier.class.getSimpleName()));
		
		teamer.label = Teamer.class.getSimpleName() + "Status: \n" + props.get(Teamer.class.getSimpleName());

		Stopuhr.updateClocks(id, props);
	}

	public String getRoomStatus() {
		String statusTexture = (String) props.get(Status.class.getSimpleName());
		if (statusTexture == null) {
			Multiplayer.updateProp(id, Status.class.getSimpleName(), Status.offen.texture);
			return Status.offen.texture;
		}

		return statusTexture;
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